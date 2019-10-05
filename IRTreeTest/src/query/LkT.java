package query;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import spatialindex.rtree.IRTree;
import spatialindex.spatialindex.Point;
import spatialindex.storagemanager.DiskStorageManager;
import spatialindex.storagemanager.IBuffer;
import spatialindex.storagemanager.IStorageManager;
import spatialindex.storagemanager.PropertySet;
import spatialindex.storagemanager.TreeLRUBuffer;

public class LkT {
	private int []lkt= {12,11};//已知答案点
	private static String WordID="3,5,12,10,6";
	public String  getWordID() {
		return WordID;
	}
	public static void setWordID(Vector qVector) {
		for (int i=0; i<qVector.size();i++) {
		WordID+=qVector.get(i);
		}
	}
	public int[] getLkt() {
		return lkt;
	}
	public void setLkt(int[] lkt) {
		this.lkt = lkt;
	}
	public static void main(String[] args) throws Exception{
		if(args.length != 6){
			System.out.println("Usage: LkT tree_file query_file topk fanout buffersize alpha");
			System.exit(-1);
		}

		String tree_file = args[0];			
		String query_file = args[1];		
		int topk = Integer.parseInt(args[2]);
		int fanout = Integer.parseInt(args[3]);
		int buffersize = Integer.parseInt(args[4]);
		double alpha = Double.parseDouble(args[5]);
		
		///////////////////////////////////////////////////////////////
		// Create a disk based storage manager.
		PropertySet ps = new PropertySet();

		ps.setProperty("FileName", tree_file);
		// .idx and .dat extensions will be added.

		Integer pagesize = new Integer(4096*fanout/100);
		ps.setProperty("PageSize", pagesize);
		// specify the page size. Since the index may also contain user defined data
		// there is no way to know how big a single node may become. The storage manager
		// will use multiple pages per node if needed. Off course this will slow down performance.

		ps.setProperty("BufferSize", buffersize);

		IStorageManager diskfile = new DiskStorageManager(ps);

		IBuffer file = new TreeLRUBuffer(diskfile, buffersize, false);
		// applies a main memory random buffer on top of the persistent storage manager
		// (LRU buffer, etc can be created the same way).

		// INDEX_IDENTIFIER_GOES_HERE (suppose I know that in this case it is equal to 1);
		ps.setProperty("IndexIdentifier", new Integer(1));
		//System.out.println("open IRtree.");
		IRTree irtree = new IRTree(ps, file, false);
		
		FileInputStream queryfis = new FileInputStream(query_file);
		BufferedReader querybr = new BufferedReader(new InputStreamReader(queryfis));
		String line;
		String[] temp;
		
		double[] f = new double[2];
		Vector qwords = new Vector();//用于存储wordId
		int count = 0;
		long start = System.currentTimeMillis();
		while((line = querybr.readLine()) != null){
			//在原基础上修改三行
			System.out.println("query " + count);
			//line = querybr.readLine();
			//System.out.println("line:"+line);
			temp = line.split(",");
			//到这修改结束，就可以运行了
			//这里的query文件有多行，每行都有id ,x , y, wordId
			qwords.clear();
			
			f[0] = Double.parseDouble(temp[1]);
			f[1] = Double.parseDouble(temp[2]);
			Point qpoint = new Point(f);//仅用坐标构建点对象
			for(int j = 3; j < temp.length; j++){
				qwords.add(Integer.parseInt(temp[j])); 
			}
			//topk = 2;
			irtree.lkt(qwords, qpoint, topk, alpha);//对每个查询点都找出其top-k个优秀点
			count++;
		}
	
		long end = System.currentTimeMillis();
		System.out.println("average time millisecond: " + (end-start)*1.0/count);
		System.out.println("average IO: " + (irtree.getIO()-file.getHits())*1.0/count);
		System.out.print("Finish!!!");
	}

}
