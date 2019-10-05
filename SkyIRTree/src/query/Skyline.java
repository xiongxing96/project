package query;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;


import spatialindex.rtree.IRTree;
import spatialindex.rtree.SQpoint;
import spatialindex.spatialindex.Point;
import spatialindex.spatialindex.Region;
import spatialindex.storagemanager.DiskStorageManager;
import spatialindex.storagemanager.IBuffer;
import spatialindex.storagemanager.IStorageManager;
import spatialindex.storagemanager.PropertySet;
import spatialindex.storagemanager.TreeLRUBuffer;

public class Skyline {
	private int []skypoint= {3,8};//已知答案点
	static int ObjectNumbers = 0;
	private static int []wordId= {3,5,12}; //存放查询文本
	
	public int[] getSkypoint() {
		return skypoint;
	}

	public void setSkypoint(int[] skypoint) {
		this.skypoint = skypoint;
	}

	public static int[] getWordId() {
		return wordId;
	}

	public static void setWordId(int[] wordId) {
		Skyline.wordId = wordId;
	}

	public static void main(String[] args) throws Exception{
		if(args.length != 5){
			System.out.println("Usage: Skyline tree_file query_file fanout buffersize alpha");
			System.exit(-1);
		}

		String tree_file = args[0];			
		String query_file = args[1];		
		int fanout = Integer.parseInt(args[2]);
		int buffersize = Integer.parseInt(args[3]);
		float alpha = Float.parseFloat(args[4]);
		
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
		//读取查询点的经纬度
		ArrayList<SQpoint>Querys=new ArrayList<SQpoint>();

		//计算数据对象个数
		FileInputStream objectfile = new FileInputStream("inputdata.txt");
		BufferedReader objectnums = new BufferedReader(new InputStreamReader(objectfile));
		String lines;
		int counts = 0;
		while ( (lines=objectnums.readLine()) != null){
 			counts++;
		}
		ObjectNumbers = counts;
		
		FileInputStream queryfis = new FileInputStream(query_file);
		BufferedReader querybr = new BufferedReader(new InputStreamReader(queryfis));
		String line;
		String[] temp;
		Double[] f = new Double[2];
		Vector qwords = new Vector();//用于存储wordId
		int count = 1;//处理次数
		long start = System.currentTimeMillis();
		while((line = querybr.readLine()) != null){
			//在原基础上修改三行
			//System.out.println("query " + count);
			//line = querybr.readLine();
			//System.out.println("line:"+line);
			temp = line.split(",");
			//到这修改结束，就可以运行了
			//这里的query文件有多行，每行都有id ,x , y, wordId
			qwords.clear();
			f[0] = Double.valueOf(temp[1]);
			f[1] = Double.valueOf(temp[2]);
			SQpoint qpoint = new SQpoint(Integer.valueOf(temp[0]),f[0],f[1]);//仅用坐标构建点对象
			Querys.add(qpoint);
//			for(int j = 3; j < temp.length; j++){
//				qwords.add(Integer.parseInt(temp[j])); 
		
		}
		for(int m = 0;m < wordId.length;m++) {
			qwords.add((int)wordId[m]);
		}
		irtree.skyline(qwords, Querys, alpha,ObjectNumbers);//对于多个查询点找出它们 共同的skyline点
		
		long end = System.currentTimeMillis();//结束执行时间
		System.out.println("average time millisecond: " + (end-start)*1.0/count+" (ms)");
		System.out.println("average IO: " + (irtree.getIO()-file.getHits())*1.0/count);
		System.out.print("Finish!!!");
		
	} 
}
