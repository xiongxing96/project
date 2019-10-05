package invertedindex;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import jdbm.RecordManagerFactory;
import jdbm.btree.BTree;
import jdbm.helper.ComparableComparator;
import jdbm.helper.DefaultSerializer;
import jdbm.helper.Tuple;
import jdbm.helper.TupleBrowser;
import jdbm.recman.CacheRecordManager;
import neustore.base.ByteArray;
import neustore.base.DBBuffer;
import neustore.base.DBBufferReturnElement;
import neustore.base.DBIndex;
import neustore.base.Data;
import neustore.base.FloatData;
import neustore.base.FloatDataComparatorDes;
import neustore.base.IntKey;
import neustore.base.Key;
import neustore.base.KeyData;
import neustore.base.LRUBuffer;
import neustore.heapfile.HeapFilePage;



public class InvertedIndex extends DBIndex {

	protected CacheRecordManager cacheRecordManager;

	protected long          recid;

	protected BTree         btree;

	protected int 		  pageSize;

	private int 		  numRecs;

	protected Key sampleKey;

	protected Data sampleData;

	protected Hashtable invertedlists;

	protected int count;

	public InvertedIndex(DBBuffer _buffer, String filename, boolean isCreate, int pagesize, int buffersize, Key _sampleKey, Data _sampleData) throws IOException {
		super( _buffer, filename, isCreate );
		sampleKey = _sampleKey;
		sampleData = _sampleData;

		int cachesize = buffersize;
		cacheRecordManager = new CacheRecordManager(RecordManagerFactory.createRecordManager(filename), cachesize, true);
		pageSize = pagesize;

		if ( !isCreate ) {
			recid = cacheRecordManager.getNamedObject( "0" );
			btree = BTree.load( cacheRecordManager, recid );
			//System.out.println("loading btree: " + btree.size());
		} 
		else {
			btree = BTree.createInstance( cacheRecordManager, ComparableComparator.INSTANCE, DefaultSerializer.INSTANCE, DefaultSerializer.INSTANCE, 1000 );
			cacheRecordManager.setNamedObject( "0", btree.getRecid() );	          
		}

		invertedlists = new Hashtable();
		count = 0;
	}

	public InvertedIndex(DBBuffer _buffer, String filename, boolean isCreate, int pagesize, int buffersize)throws IOException{
		super( _buffer, filename, isCreate );
		sampleKey = new IntKey(0);
		sampleData = new FloatData(0);

		int cachesize = buffersize;
		cacheRecordManager = new CacheRecordManager(RecordManagerFactory.createRecordManager(filename), cachesize, true);
		pageSize = pagesize;


	}

	

	protected void readIndexHead (byte[] indexHead) {
		ByteArray ba = new ByteArray( indexHead, true );
		try {
			numRecs = ba.readInt();

		} catch (IOException e) {}
	}
	protected void writeIndexHead (byte[] indexHead) {
		ByteArray ba = new ByteArray( indexHead, false );
		try {
			ba.writeInt(numRecs);

		} catch (IOException e) {}
	}
	protected void initIndexHead() {
		numRecs = 0;

	}


	public int numRecs() { return numRecs; }

	protected HeapFilePage readPostingListPage( long pageID ) throws IOException {
		DBBufferReturnElement ret = buffer.readPage(file, pageID);
		HeapFilePage thePage = null;
		if ( ret.parsed ) {
			thePage = (HeapFilePage)ret.object;
		}
		else {
			thePage = new HeapFilePage(pageSize, sampleKey, sampleData);
			thePage.read((byte[])ret.object);
		}
		return thePage;
	}

	public void insertDocument(int docID, Vector document) throws IOException{
		IntKey key = new IntKey(docID);
		for(int i = 0; i < document.size(); i++){
			KeyData keydata = (KeyData)document.get(i);
			IntKey wordID = (IntKey)keydata.key;
			FloatData weight = (FloatData)keydata.data;

			KeyData rec = new KeyData(key, weight);

			if(invertedlists.containsKey(wordID.key)){
				ArrayList list = (ArrayList)invertedlists.get(wordID.key);
				list.add(rec);

			}
			else{
				ArrayList list = new ArrayList();
				list.add(rec);
				invertedlists.put(wordID.key, list);
			}
		}

		count++;
		if(count == 1000){
			store();
			count = 0;
		}

	}

	public Vector insertDocument(int nodeid, int docID, Vector document) throws IOException{
		load(nodeid);
		IntKey key = new IntKey(docID);
		Vector pseudoDoc = new Vector();
		for(int i = 0; i < document.size(); i++){
			KeyData keydata = (KeyData)document.get(i);
			IntKey wordID = (IntKey)keydata.key;
			FloatData weight = (FloatData)keydata.data;
			KeyData rec = new KeyData(key, weight);
			boolean upward = insertPosting(wordID.key, rec);
			if(upward)
				pseudoDoc.add(rec);
		}
		return pseudoDoc;
	}

	public void insertDocument(int docID, Vector document, Hashtable invertedindex) throws IOException{
		IntKey key = new IntKey(docID);
		for(int i = 0; i < document.size(); i++){
			KeyData keydata = (KeyData)document.get(i);
			IntKey wordID = (IntKey)keydata.key;
			FloatData weight = (FloatData)keydata.data;

			KeyData rec = new KeyData(key, weight);

			if(invertedindex.containsKey(wordID.key)){
				ArrayList list = (ArrayList)invertedindex.get(wordID.key);
				list.add(rec);
			}
			else{
				ArrayList list = new ArrayList();
				list.add(rec);
				invertedindex.put(wordID.key, list);
			}
		}
	}

	public Vector store(int treeid, Hashtable invertedindex)throws IOException{
		Vector pseudoDoc = new Vector();
		load(treeid);
		Iterator iter = invertedindex.keySet().iterator();
		while(iter.hasNext()){
			int wordID = (Integer)iter.next();
			ArrayList list = (ArrayList)invertedindex.get(wordID);
			long newPageID = allocate();
			Object var = btree.insert(wordID, newPageID, false);
			if(var != null){
				System.out.println("Btree insertion error: duplicate keys.");
				System.exit(-1);
			}
			HeapFilePage newPage = new HeapFilePage(pageSize, sampleKey, sampleData);

			float weight = storelist(list, newPage, newPageID);
			IntKey key = new IntKey(wordID);
			FloatData data = new FloatData(weight);
			KeyData rec = new KeyData(key, data);
			pseudoDoc.add(rec);
		}
		cacheRecordManager.commit();
		return pseudoDoc;
	}

	public void store() throws IOException{
		Iterator iter = invertedlists.keySet().iterator();
		while(iter.hasNext()){
			int wordID = (Integer)iter.next();
			ArrayList list = (ArrayList)invertedlists.get(wordID);
			Object var = btree.find(wordID);
			if(var == null){
				long newPageID = allocate();
				var = btree.insert(wordID, newPageID, false);
				if(var != null){
					System.out.println("Btree insertion error: duplicate keys.");
					System.exit(-1);
				}
				HeapFilePage newPage = new HeapFilePage(pageSize, sampleKey, sampleData);

				storelist(list, newPage, newPageID);

			}
			else{
				long pageID = (Long)var;
				HeapFilePage thePage = readPostingListPage(pageID);
				while(!thePage.isLastPage()){
					pageID = thePage.getNextPageID();
					thePage = readPostingListPage(pageID);
				}
				storelist(list, thePage, pageID);
			}
		}
		cacheRecordManager.commit();
		invertedlists.clear();
	}

	public void commit()throws IOException{
		cacheRecordManager.commit();
	}

	public void storelist(int id, ArrayList list)throws IOException{
		Object var = btree.find(id);
		if(var == null){
			long newPageID = allocate();
			var = btree.insert(id, newPageID, false);
			if(var != null){
				System.out.println("Btree insertion error: duplicate keys.");
				System.exit(-1);
			}
			HeapFilePage newPage = new HeapFilePage(pageSize, sampleKey, sampleData);

			storelist(list, newPage, newPageID);

		}
		else{
			System.out.println("Btree insertion error: duplicate keys again.");
			System.exit(-1);
		}
	}

	private float storelist(ArrayList list, HeapFilePage newPage, long newPageID) throws IOException{
		float weight = Float.NEGATIVE_INFINITY;
		for(int j = 0; j < list.size(); j++){
			KeyData rec = (KeyData)list.get(j);
			IntKey key = (IntKey)rec.key;
			FloatData data = (FloatData)rec.data;
			weight = Math.max(weight, data.data);
			int availableByte = newPage.getAvailableBytes();
			if(availableByte < key.size() + data.size()){

				long nextPageID = allocate();
				newPage.setNextPageID(nextPageID);
				buffer.writePage(file, newPageID, newPage);
				newPageID = nextPageID;
				newPage = new HeapFilePage(pageSize, sampleKey, sampleData);
			}
			newPage.insert(key, data);
		}
		buffer.writePage(file, newPageID, newPage);
		return weight;
	}

	public Hashtable textRelevancy(Vector qwords) throws IOException{
		Hashtable filter = new Hashtable();		
		for(int j = 0; j < qwords.size(); j++){
			int word = (Integer)qwords.get(j);// 获得一个单词
			ArrayList list = readPostingList(word);// 读取这个单词( 获得包含这个单词所有的(商家-权值)对 )
			// 下面处理这个list
			for(int i = 0; i < list.size(); i++){
				// 构造数据
				KeyData rec = (KeyData)list.get(i);
				IntKey docID = (IntKey)rec.key;
				FloatData weight = (FloatData)rec.data;
				// 如果已经有了这个商家的id( 也就是说明这个商家的相关性更大 )
				if(filter.containsKey(docID.key)){
					float w = (Float)filter.get(docID.key);//获得之前已经存在的权值
					w = w * weight.data;// 权值相乘，那么也就说值越小越好
					filter.put(docID.key, w);//再次放进去
				}
				else			
					filter.put(docID.key, weight.data);// 创建一个新的商家而已
			}
		}
		return filter;// 最终返回的是所有的相关性的商家的hash表
	}
	
	public Hashtable skytextRelevancy(Vector qwords,int number,float alpha) throws IOException{
		int ps=0;
		int nums[][] = new int[number][2];
		Hashtable filter = new Hashtable();	
		// 下面处理所有的关键字
		for(int j = 0; j < qwords.size(); j++){//3//5//12
			ps=ps+1;
			int word = (Integer)qwords.get(j);// 获得一个单词
			ArrayList list = readPostingList(word);// 读取这个单词( 获得包含这个单词所有的(商家-权值)对 )key和data对，文本id和权重
			// 下面处理这个list//1,8//2,8,15//15
			for(int i = 0; i < list.size(); i++){
				// 构造数据
				KeyData rec = (KeyData)list.get(i);
				IntKey docID = (IntKey)rec.key;
				FloatData weight = (FloatData)rec.data;
				for(int cout = 0;cout < nums.length;cout++) {
					if(nums[cout][0] == 0 ||nums[cout][0] == docID.key) {
						nums[cout][0] = docID.key;
						nums[cout][1] = nums[cout][1] + 1;
						break;
					}
				}
				// 如果已经有了这个商家的id( 也就是说明这个商家的相关性更大 )
				if(filter.containsKey(docID.key)){
					//ps++;//存储包含该word的商家个数
					float w = (Float)filter.get(docID.key);//获得之前已经存在的权值
					w = w * weight.data;// 权值相乘，那么也就说值越小越好
					filter.put(docID.key, w);//再次放进去
				}
				else {filter.put(docID.key, weight.data);}// 创建一个新的商家而已
			}
			if(ps == qwords.size()) {
				for(int ip = 0;ip < number;ip++) {
					if(nums[ip][1] == 0) {break;}
					if(nums[ip][1] < qwords.size()) {
						float finw = (Float)filter.get(nums[ip][0]);
						finw = finw * (float)Math.pow(alpha, (qwords.size() - nums[ip][1]));
						filter.put(nums[ip][0], finw);
					}
				}
			}
		}
		
		return filter;// 最终返回的是所有的相关性的商家的hash表
	}
	public ArrayList readPostingList(int wordID) throws IOException{
		ArrayList list = new ArrayList();
		Object var = btree.find(wordID);// 在B树中找到这个单词
		if(var == null){
			//System.out.println("Posting List not found " + wordID);
			//System.exit(-1);
			return list;
		}
		// 找到处理
		else{
			long firstPageID = (Long)var;// 获得第一页的id

			while(firstPageID != -1){
				//System.out.println(" page " + firstPageID);
				HeapFilePage thePage = readPostingListPage(firstPageID);// 获得buf中这个page
				for(int i = 0; i < thePage.numRecs(); i++){// 下面将所有的记录插入进去
					KeyData rec = thePage.get(i);// key-data是保存  Key and Data 对的class
					list.add(rec);
				}
				//System.out.println("size " + thePage.numRecs());
				firstPageID = thePage.getNextPageID();	// 可能还有其他page
			}

		}
		return list;
	}
	
	public ArrayList readPostingListWithDeletion(int wordID) throws IOException{
		ArrayList list = new ArrayList();
		Object var = btree.find(wordID);
		if(var == null){
			//System.out.println("Posting List not found " + wordID);
			//System.exit(-1);
			return list;
		}
		else{
			long thePageID = (Long)var;
			long nextPageID = thePageID;
			while(nextPageID != -1){
				//System.out.println(" page " + firstPageID);
				thePageID = nextPageID;
				HeapFilePage thePage = readPostingListPage(thePageID);
				for(int i = 0; i < thePage.numRecs(); i++){
					KeyData rec = thePage.get(i);
					list.add(rec);
				}
				//System.out.println("size " + thePage.numRecs());
				nextPageID = thePage.getNextPageID();
				freePage(thePageID);
			}

		}
		return list;
	}

	private boolean insertPosting(int wordID, KeyData rec)throws IOException{
		boolean upward = true;
		boolean replace = false;
		IntKey key = (IntKey)rec.key;
		FloatData data = (FloatData)rec.data;
		HeapFilePage thePage = null;
		long thePageID;
		Object var = btree.find(wordID);
		if(var == null){
			thePageID = allocate();
			Object var2 = btree.insert(wordID, thePageID, false);
			if(var2 != null){
				System.out.println("Btree insertion error: duplicate keys.");
				System.exit(-1);
			}
			thePage = new HeapFilePage(pageSize, sampleKey, sampleData);
		}
		else{
			thePageID = (Long)var;

			while(thePageID != -1){
				thePage = readPostingListPage(thePageID);
				if(upward || !replace){
					for(int i = 0; i < thePage.numRecs(); i++){
						KeyData rec2 = thePage.get(i);
						IntKey key2 = (IntKey)rec2.key;
						FloatData data2 = (FloatData)rec2.data;
						if(data2.data >= data.data){
							upward = false;
						}
						if(key2.key == key.key){
							replace = true;
							data2.data = Math.max(data2.data, data.data);
							thePage.delete(key2);
							thePage.insert(key2, data2);
							buffer.writePage(file, thePageID, thePage);
						}
					}
				}
				thePageID = thePage.getNextPageID();
			}

		}
		if(!replace){
			int availableByte = thePage.getAvailableBytes();
			if(availableByte < rec.key.size() + rec.data.size()){		
				long nextPageID = allocate();
				thePage.setNextPageID(nextPageID);
				buffer.writePage(file, thePageID, thePage);
				thePageID = nextPageID;
				thePage = new HeapFilePage(pageSize, sampleKey, sampleData);
			}
			thePage.insert(rec.key, rec.data);

			buffer.writePage(file, thePageID, thePage);
		}
		return upward;
	}

	public int[] getIOs(){
		return buffer.getIOs();
	}

	

	public BTree getBtree(){
		return btree;
	}
	public void create(int treeid)throws IOException{

		String BTREE_NAME = String.valueOf(treeid);
		recid = cacheRecordManager.getNamedObject( BTREE_NAME );
		if ( recid != 0 ) {
			System.out.println("Creating an existing btree: " + treeid);
			System.exit(-1);
		} 
		else {
			btree = BTree.createInstance( cacheRecordManager, ComparableComparator.INSTANCE, DefaultSerializer.INSTANCE, DefaultSerializer.INSTANCE, 1000 );
			cacheRecordManager.setNamedObject( BTREE_NAME, btree.getRecid() );	          
		}
	}

	public void load(int treeid)throws IOException{
		String BTREE_NAME = String.valueOf(treeid);
		recid = cacheRecordManager.getNamedObject( BTREE_NAME );
		if ( recid != 0 ) {
			recid = cacheRecordManager.getNamedObject( BTREE_NAME );
			btree = BTree.load( cacheRecordManager, recid );
			//System.out.println("loading btree: " + btree.size());
		} 
		else {
			System.out.println("Failed loading btree: " + treeid);
			System.exit(-1);          
		}
	}

	public int getBtreeSize(){
		return btree.size();
	}

	public void clear(InvertedIndex iindex) {
		iindex =null;
	}


}
