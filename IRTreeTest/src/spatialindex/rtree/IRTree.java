package spatialindex.rtree;

import invertedindex.InvertedIndex;
import javafx.scene.layout.Region;
import jdbm.btree.BTree;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import spatialindex.rtree.SQpoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import javax.management.Query;

import com.sun.org.apache.bcel.internal.generic.NEW;

import neustore.base.LRUBuffer;

import spatialindex.rtree.RTree.NNComparator;
import spatialindex.spatialindex.IData;
import spatialindex.spatialindex.IEntry;
import spatialindex.spatialindex.Point;
import spatialindex.storagemanager.DiskStorageManager;
import spatialindex.storagemanager.IBuffer;
import spatialindex.storagemanager.IStorageManager;
import spatialindex.storagemanager.PropertySet;
import spatialindex.storagemanager.TreeLRUBuffer;

public class IRTree extends RTree {
	protected InvertedIndex iindex;
	protected int count;

	public IRTree(PropertySet ps, IStorageManager sm, boolean isCreate) throws IOException {
		super(ps, sm);
		int buffersize = (Integer) ps.getProperty("BufferSize");
		int pagesize = (Integer) ps.getProperty("PageSize");
		String file = (String) ps.getProperty("FileName");
		LRUBuffer buffer = new LRUBuffer(buffersize, pagesize);
		iindex = new InvertedIndex(buffer, file + ".iindex", isCreate, pagesize, buffersize);
	}

	// 创建到排序文件索引( 在第一次创建IR树的时候进行处理一次 )
	public void buildInvertedIndex(BtreeStore docstore) throws Exception {
		count = 0;

		Node n = readNode(m_rootID);
		post_traversal_iindex(n, docstore);
	}

	// 下面后序索引建立一个索引( 由上面的函数buildInvertedIndex调用 )
	private Vector post_traversal_iindex(Node n, BtreeStore docstore) throws Exception {
		// 如果n是叶子
		if (n.isLeaf()) {
			Hashtable invertedindex = new Hashtable();
			iindex.create(n.m_identifier);// 创建一个B树缓冲区
			int child;
			for (child = 0; child < n.m_children; child++) {
				int docID = n.m_pIdentifier[child];
				Vector document = docstore.getDoc(docID);
				if (document == null) {
					System.out.println("Can't find document " + docID);
					System.exit(-1);
				}
				// 将所有的倒排条目插入
				iindex.insertDocument(docID, document, invertedindex);
			}
			return iindex.store(n.m_identifier, invertedindex);// 保存到缓冲区中
		}
		// 如果不是叶子
		else {
			Hashtable invertedindex = new Hashtable();
			iindex.create(n.m_identifier);
			System.out.println("processing index node " + n.m_identifier);
			System.out.println("level " + n.m_level);
			int child;
			for (child = 0; child < n.m_children; child++) {
				Node nn = readNode(n.m_pIdentifier[child]);
				// 下面递归进行建立索引
				Vector pseudoDoc = post_traversal_iindex(nn, docstore);
				int docID = n.m_pIdentifier[child];
				iindex.insertDocument(docID, pseudoDoc, invertedindex);
				count++;
				System.out.println(count + "/" + m_stats.getNumberOfNodes());
			}
			return iindex.store(n.m_identifier, invertedindex);
		}
	}

	public void close() throws IOException {
		flush();
		iindex.close();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void lkt(Vector qwords, Point qpoint, int topk, double alpha) throws Exception {
		// 下面创建一个新的优先级队列（ 队列的初始化容量为100 ）
		// 优先级的比较规则是由NNEntryComparatorForEncrypt决定
		PriorityQueue queue = new PriorityQueue(100, new NNEntryComparator());
		NNComparator nnc = new NNComparator();// 计算查询点和索引条目的最小距离
		double knearest = Double.MAX_VALUE;
		int count = 0;

		Node n = null;
		// m_id = id; m_shape = mbr; m_pData = pData;
		Data nd = new Data(null, null, m_rootID);//// 封装根节点信息
		Node root = readNode(m_rootID);// 获得根节点
		System.out.println("cccccccccccccc根节点的层=" + root.m_level + "  " + root.m_totalDataLength + " " + root.getShape()
				+ "  " + root.m_capacity + " " + root.m_identifier);
		// m_pEntry = e; m_minDist = f; level = l;
		queue.add(new NNEntry(nd, 0.0, root.m_level));// 将根节点加入队列

		while (queue.size() != 0) {
			NNEntry first = (NNEntry) queue.poll();// 获得第一个队列元素并将其从队列中删除（没有则返回null）

			if (count >= topk && first.m_minDist >= knearest)
				break;

			if (first.level > 0)// 即非叶子节点
			{
				IData fd = (IData) first.m_pEntry;// 获得这个entry(一个分支 )
				n = readNode(fd.getIdentifier());// 获得这个节点n的信息(这是一个分支节点 )
				iindex.load(n.m_identifier);// 加载IRTree中该节点n的ID所有的倒排文件
				Hashtable trscore = iindex.textRelevancy(qwords);//// 得到该倒排文件的文本相关性的hashtable
				// System.out.println("n.m_children==="+n.m_children);
				for (int cChild = 0; cChild < n.m_children; cChild++)// 对节点n的孩子节点进行遍历
				{
					Object var = trscore.get(n.m_pIdentifier[cChild]);// 得到儿子节点id的对应相关性
					//////////////////////////////
					if (var == null) {

						continue;
					}
					float trs = (Float) var;
					//////////////////////////////
					IEntry e = new Data(trs, n.m_pMBR[cChild], n.m_pIdentifier[cChild]);// 为节点n所在的矩形，节点n的id，构造成一个区域矩形
					double dist = nnc.getMinimumDistance(qpoint, e);// 得到点与矩形的最小距离
					double score = alpha * dist + (1 - alpha) * trs;// 得到分数
					NNEntry e2 = new NNEntry(e, score, n.m_level);
					queue.add(e2);
				}
			} else {
				System.out.println(first.m_pEntry.getIdentifier() + ":" + first.m_minDist);
				count++;
				knearest = first.m_minDist;// 最小距离
			}
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public double getD(SQpoint pointA, Point pointB) {
		// System.out.println("AID:"+pointA.getId()+";A经："+pointA.getLongitude()+";A纬："+pointA.getLatitude()+"BID:"+pointB.getId()+";B经："+pointB.getLongitude()+";B纬："+pointB.getLatitude());
		// System.out.println("欧式距离："+Math.sqrt(Math.pow(pointA.getLongitude()-pointB.getLongitude(),2)+Math.pow(pointA.getLatitude()-pointB.getLatitude(),2)));
		return Math.sqrt(Math.pow(pointA.getLongitude() - pointB.m_pCoords[0], 2)
				+ Math.pow(pointA.getLatitude() - pointB.m_pCoords[1], 2));
	}

	public void skyline(Vector qwords, ArrayList<SQpoint> queryXY, float alpha, int ObjectNumbers) throws Exception {
		// 下面创建一个新的优先级队列（ 队列的初始化容量为100 ）
		PriorityQueue textnum = new PriorityQueue(100, new NNEntryComparator());
		// 优先级的比较规则是由NNEntryComparatorForEncrypt决定
		PriorityQueue queue = new PriorityQueue(100, new NNEntryComparator());
		NNComparator nnc = new NNComparator();
		double knearest = Double.MAX_VALUE;
		int count = 0;

		Node n = null;
		Node ntext = null;
		// m_id = id; m_shape = mbr; m_pData = pData; // m_rootID是R树根id
		Data nd = new Data(null, null, m_rootID);// node的data数据结构
		Node root = readNode(m_rootID);// 获得根节点
		// System.out.println(root.getChildrenCount()+"为矩形数量;;（左下点和右上点）区域坐标"+root.getChildShape(0)+";;区域坐标"+root.getChildShape(1)+";;区域坐标"+root.getChildShape(2));
		// System.out.println("根节点的层="+root.m_level);

		queue.add(new NNEntry(nd, 0.0, root.m_level)); // 队列中增加一个entry
		// m_pEntry（id和shape） = e; m_minDist = f; level = l;
		// level = 4是什么个情况？ 注意：只有她的数据才是OK的！
		// 所以在建树的时候需要统计：获得这个信息才OK
		// m_pEntry = e; m_minDist = f; level = l;
		// queue.add(new NNEntry(nd, 0.0, 4));(原程序中的代码！！)
		Hashtable ptable = new Hashtable();
		Query queryleaf = new Query();

		// System.out.println(ptable.size());
		while (queue.size() != 0) {
			NNEntry first = (NNEntry) queue.poll();// 获得第一个队列元素
			// 如果不是叶子
			if (first.level > 0) {
				IData fd = (IData) first.m_pEntry;// 获得这个entry( 一个分支 ) // m_pEntry是一个entry( id， shape )
				n = readNode(fd.getIdentifier());// 获得这个节点的信息( 这是一个分支节点 )
				// System.out.println("fd.getIdentifier()该区域节点的入口ID=="+fd.getIdentifier());

				//////// 打印该区域的孩子id
//				for (int r = 0; r < n.getChildrenCount(); r++) {
//					System.out.println(
//							"第" + r + "个儿子::::;;儿子的坐标=" + n.getChildShape(r) + ";;;儿子的id=" + n.getChildIdentifier(r));
//				}

				ArrayList<NNE> arr = new ArrayList<NNE>();// 封装成对象数组
				ArrayList<NNE> parr = new ArrayList<NNE>();
				for (int cChild = 0; cChild < n.m_children; cChild++) {
					// for(int dim = 0;dim < n.getChildShape(cChild).getDimension();dim++) {
					/////////////////////////////////////////////////////////////
					// 如果该节点的儿子节点是叶子节点，怎直接处理上面的儿子
					if ((n.getChildShape(cChild).getMBR().m_pHigh[0] == n.getChildShape(cChild).getMBR().m_pLow[0])
							&& (n.getChildShape(cChild).getMBR().m_pHigh[1] == n.getChildShape(cChild)
									.getMBR().m_pLow[1])) {
						// System.out.println("该层到达儿子是叶子节点的处理入口!!!");//break;
						// System.out.println(n.getChildIdentifier(cChild));
						// 加载文本相关性哈希表，计算叶子节点的文本相关性
						float pscore = 0;
						float nums[][] = new float[n.getChildrenCount()][2];
						for (int loc = 0; loc < n.getChildrenCount(); loc++) {
							nums[loc][0] = n.getChildIdentifier(loc);
							nums[loc][1] = 0;
							if (ptable.get(n.getChildIdentifier(loc)) == null) {
								nums[loc][1] = 0;
							}
							if (ptable.get(n.getChildIdentifier(loc)) != null) {
								nums[loc][1] = (float) ptable.get(n.getChildIdentifier(loc));
							}
						}

						for (int leaf = 0; leaf < n.getChildrenCount(); leaf++) {
							float leafscore = nums[leaf][1];
							if (leafscore != 0) {
								IEntry pe = new Data(leafscore, n.m_pMBR[leaf], n.m_pIdentifier[leaf]);
								ArrayList<NNEntry> pQ = new ArrayList<>();
								for (int t = 0; t < queryXY.size(); t++) {

									////// ====对每个查询对象进行排查====================================================
									double[] f = new double[2];
									f[0] = queryXY.get(t).getLongitude();
									f[1] = queryXY.get(t).getLatitude();
									Point qpoint = new Point(f);

									double pdist = nnc.getMinimumDistance(qpoint, pe);// 获得距离
									// System.out.println("dist=" + pdist);
									// 计算支配比较
									float ptrs = (float) (pdist / Math.pow(leafscore, 1.0 / qwords.size()));
									NNEntry pe2 = new NNEntry(pe, ptrs, n.m_level);

									pQ.add(t, pe2);
									// queue.add(e2);
								} // for
									// query=============================================================================
									// float trs=nnc.getMinimumDistance(query, e)
								NNE pe3 = new NNE(pQ, leaf);// 为每一个孩子节点所对应的所有查询对象封装成一个对象
								parr.add(pe3);
								// System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@");
							}
						}
						break;
						// SQpoint tt = new
						// SQpoint((int)n.getIdentifier(cChild),n.getChildShape(cChild).getMBR().m_pLow[0],n.getChildShape(cChild).getMBR().m_pLow[0]);
					} ///// 该节点的儿子不是叶子节点则要将他们的分数加入队列
					else {
						Data ndtext = new Data(null, null, n.getChildIdentifier(cChild));// 将孩子封装成node的data数据结构
						Node roottext = readNode(n.getChildIdentifier(cChild));// 获得根节点 的文本
						iindex.load(n.getChildIdentifier(cChild));
						// System.out.println("节点id="+n.getIdentifier()+"的儿子个数：："+n.getChildrenCount()+"&&&&&其儿子id为：：");
						// for(int i=0;i<n.m_children;i++)
						// {
						// System.out.print(n.getChildIdentifier(i)+" ");
						// }
						// System.out.println();
						Hashtable trscore = iindex.skytextRelevancy(qwords, roottext.m_children, alpha);// 1\2\8\15
						ptable.putAll(trscore);

						// 计算得分
						float score = 0;// 该区域的得分
						int cout = 0;
						for (int calscore = 1; calscore <= ObjectNumbers; calscore++) {
							if (trscore.get(calscore) != null) {
//								System.out.println((float) trscore.get(calscore));
								if ((float) trscore.get(calscore) > score) {
									score = (float) trscore.get(calscore);
								}
							}
						}

						/*
						 * Object var = trscore.get(n.m_pIdentifier[cChild]);// 看看有没有在相关文本队列之中的
						 * ////////////////////////////// if(var == null){
						 * 
						 * continue; } float trs = (Float)var;//相关性
						 */ ////////////////////////////// public Data(double w, Region mbr, int id) { m_id
							////////////////////////////// = id; m_shape = mbr; weight = w; }
						IEntry e = new Data(score, n.m_pMBR[cChild], n.m_pIdentifier[cChild]);
						// 计算距离
						ArrayList<NNEntry> Q = new ArrayList<>();
						for (int t = 0; t < queryXY.size(); t++) {
							////// ====对每个查询对象进行排查====================================================
							double[] f = new double[2];
							f[0] = queryXY.get(t).getLongitude();
							f[1] = queryXY.get(t).getLatitude();
							Point qpoint = new Point(f);

							double dist = nnc.getMinimumDistance(qpoint, e);// 获得距离
							// System.out.println("dist=" + dist);
							// 计算支配比较
							float trs = 0;
							if (score != 0) {
								// trs = (float) (dist / Math.pow(score, 1.0 / qwords.size()));
								trs = (float) (dist / score);
							}

							// double score = trs;//alpha*dist+(1-alpha)*trs;// 计算的得分
							NNEntry e2 = new NNEntry(e, trs, n.m_level);

							Q.add(t, e2);
							// queue.add(e2);
						} // for
							// query=============================================================================
						NNE e3 = new NNE(Q, cChild);// 为每一个孩子节点所对应的所有查询对象封装成一个对象
						arr.add(e3);
					}
				} // for Child

				//////////////////// 对叶子节点找支配点加入队列
				if (!parr.isEmpty()) {
					ArrayList List = new ArrayList();
					ArrayList List1 = new ArrayList();
					for (int i = 0; i < parr.size(); i++) {
						int delete = 0;
						if (List.size() != 0) {
							for (int t = 0; t < List.size(); t++) {
								if (i == (int) List.get(t)) {
									delete = 1;
									break;
								}
							}
						}
						for (int j = i + 1; j < parr.size(); j++) {
							if (delete == 1) {
								break;
							}
							int delete2 = 0;
							if (List.size() != 0) {
								for (int t2 = 0; t2 < List.size(); t2++) {
									if (j == (int) List.get(t2)) {
										delete2 = 1;
										break;
									}
								}
							}
							if (delete2 == 1) {
								break;
							}
							int c = 0;
							for (int k = 0; k < queryXY.size(); k++) {
//								System.out.println("x--" + parr.get(i).getEntry().get(k).m_minDist);
//								System.out.println("y--" + parr.get(j).getEntry().get(k).m_minDist);
								if ((parr.get(i).getEntry().get(k).m_minDist) <= (parr.get(j).getEntry()
										.get(k).m_minDist)) {
									c++;// System.out.println("c===="+c);
									continue;
								} else {
									break;
								}
							}
							if (c == queryXY.size()) {
								List.add(j);
							}
						}
						// parr_count = parr_count - List.size();
					}

//					for (int lxt = 0; lxt < parr.size(); lxt++) {
//						System.out.println("xx--" + parr.get(lxt).getEntry().get(0).m_minDist);
//						System.out.println("yy--" + parr.get(lxt).getEntry().get(1).m_minDist);
//					}

					for (int tt = 0; tt < List.size(); tt++) {
						parr.remove(((int) List.get(tt) - tt));
						// System.out.println(parr.size());
					}

//					for (int lxt1 = 0; lxt1 < parr.size(); lxt1++) {
//						System.out.println("xx--" + parr.get(lxt1).getEntry().get(0).m_minDist);
//						System.out.println("yy--" + parr.get(lxt1).getEntry().get(1).m_minDist);
//					}

					for (int i = 0; i < parr.size(); i++) {
						int delete = 0;
						if (List1.size() != 0) {
							for (int t = 0; t < List1.size(); t++) {
								if (i == (int) List1.get(t)) {
									delete = 1;
									break;
								}
							}
						}
						for (int j = i + 1; j < parr.size(); j++) {
							if (delete == 1) {
								break;
							}
							int delete2 = 0;
							if (List1.size() != 0) {
								for (int t2 = 0; t2 < List1.size(); t2++) {
									if (j == (int) List1.get(t2)) {
										delete2 = 1;
										break;
									}
									if (i == (int) List1.get(t2)) {
										delete = 1;
										delete2 = 1;
										break;
									}
								}
							}
							if (delete2 == 1) {
								break;
							}
							int c = 0;
							for (int k = 0; k < queryXY.size(); k++) {
//								System.out.println("x11--" + parr.get(i).getEntry().get(k).m_minDist);
//								System.out.println("y22--" + parr.get(j).getEntry().get(k).m_minDist);
								if (parr.get(i).getEntry().get(k).m_minDist >= parr.get(j).getEntry()
										.get(k).m_minDist) {
									c++;
									continue;
								} else {
									break;
								}
							}
							if (c == queryXY.size()) {// System.out.println(c+"and"+queryXY.size());
								List1.add(i);
							} // 删除对象列，行不变
						}
					}
					for (int tt1 = 0; tt1 < List1.size(); tt1++) {
						parr.remove(((int) List1.get(tt1) - tt1));
						// System.out.println(parr.size());
					}
					// System.out.println("n.mchild="+n.m_children);
					for (int i = 0; i < parr.size(); i++) {
						System.out.print("Skylinepoint:");
						System.out.println(parr.get(i).getEntry().get(0).m_pEntry.getIdentifier());
					} // 将支配节点加入queue//按列查找去第一个样本对象值
						// System.out.println("queue.size()="+queue.size());
					parr.clear();
				}
				//////////////////// 对各个儿子区域找出支配区域加入队列
				if (!arr.isEmpty()) {
					ArrayList PList = new ArrayList();
					ArrayList PList1 = new ArrayList();
					for (int i = 0; i < arr.size(); i++) {
						int delete = 0;
						if (PList.size() != 0) {
							for (int t = 0; t < PList.size(); t++) {
								if (i == (int) PList.get(t)) {
									delete = 1;
									break;
								}
							}
						}
						for (int j = i + 1; j < arr.size(); j++) {
							if (delete == 1) {
								break;
							}
							int delete2 = 0;
							if (PList.size() != 0) {
								for (int t2 = 0; t2 < PList.size(); t2++) {
									if (j == (int) PList.get(t2)) {
										delete2 = 1;
										break;
									}
								}
							}
							if (delete2 == 1) {
								break;
							}
							int c = 0;
							for (int k = 0; k < queryXY.size(); k++) {
//								System.out.println("x--" + arr.get(i).getEntry().get(k).m_minDist);
//								System.out.println("y--" + arr.get(j).getEntry().get(k).m_minDist);
								if ((arr.get(i).getEntry().get(k).m_minDist) <= (arr.get(j).getEntry()
										.get(k).m_minDist)) {
									c++;// System.out.println("c===="+c);
									continue;
								} else {
									break;
								}
							}
							if (c == queryXY.size()) {
								PList.add(j);
							}
						}
						// arr_count = arr_count - PList.size();
					}

//					for (int lxt = 0; lxt < arr.size(); lxt++) {
//						System.out.println("xx--" + arr.get(lxt).getEntry().get(0).m_minDist);
//						System.out.println("yy--" + arr.get(lxt).getEntry().get(1).m_minDist);
//					}

					for (int tt = 0; tt < PList.size(); tt++) {
						arr.remove(((int) PList.get(tt) - tt));
						// System.out.println(arr.size());
					}

//					for (int lxt1 = 0; lxt1 < arr.size(); lxt1++) {
//						System.out.println("xx--" + arr.get(lxt1).getEntry().get(0).m_minDist);
//						System.out.println("yy--" + arr.get(lxt1).getEntry().get(1).m_minDist);
//					}

					for (int i = 0; i < arr.size(); i++) {
						int delete = 0;
						if (PList1.size() != 0) {
							for (int t = 0; t < PList1.size(); t++) {
								if (i == (int) PList1.get(t)) {
									delete = 1;
									break;
								}
							}
						}
						for (int j = i + 1; j < arr.size(); j++) {
							if (delete == 1) {
								break;
							}
							int delete2 = 0;
							if (PList1.size() != 0) {
								for (int t2 = 0; t2 < PList1.size(); t2++) {
									if (j == (int) PList1.get(t2)) {
										delete2 = 1;
										break;
									}
									if (i == (int) PList1.get(t2)) {
										delete = 1;
										delete2 = 1;
										break;
									}
								}
							}
							if (delete2 == 1) {
								break;
							}
							int c = 0;
							for (int k = 0; k < queryXY.size(); k++) {
//								System.out.println("x11--" + arr.get(i).getEntry().get(k).m_minDist);
//								System.out.println("y22--" + arr.get(j).getEntry().get(k).m_minDist);
								if (arr.get(i).getEntry().get(k).m_minDist >= arr.get(j).getEntry().get(k).m_minDist) {
									c++;
									continue;
								} else {
									break;
								}
							}
							if (c == queryXY.size()) {// System.out.println(c+"and"+queryXY.size());
								PList1.add(i);
							} // 删除对象列，行不变
						}
					}
					for (int tt1 = 0; tt1 < PList1.size(); tt1++) {
						arr.remove(((int) PList1.get(tt1) - tt1));
						// System.out.println(arr.size());
					}
					// System.out.println("n.mchild="+n.m_children);
//					for (int i = 0; i < arr.size(); i++) {
//						System.out.print("Skylinepoint:");
//						System.out.println(arr.get(i).getEntry().get(0).m_pEntry.getIdentifier());
//					} // 将支配节点加入queue//按列查找去第一个样本对象值
//						// System.out.println("queue.size()="+queue.size());
//					arr.clear();
				}
				// if (!arr.isEmpty()) {
				// for (int i = 0; i < arr.size(); i++) {
				// for (int j = i + 1; j < arr.size(); j++) {
				// int c = 0;
				// for (int k = 0; k < queryXY.size(); k++) {
				//// System.out.println("x--"+arr.get(i).getEntry().get(k).m_minDist);
				//// System.out.println("y--"+arr.get(j).getEntry().get(k).m_minDist);
				// if ((arr.get(i).getEntry().get(k).m_minDist) <= (arr.get(j).getEntry()
				// .get(k).m_minDist)) {
				// c++;// System.out.println("c===="+c);
				// continue;
				// } else {
				// break;
				// }
				// }
				// // System.out.println(c+"orand"+queryXY.size());
				// if (c == queryXY.size()) {
				// // System.out.println(c+"==and"+queryXY.size());
				// arr.remove(j);
				// }
				// }
				// }
				// // System.out.println("n.mchild="+n.m_children);
				// for (int i = 0; i < arr.size(); i++) {
				// for (int j = i + 1; j < arr.size(); j++) {
				// int c = 0;
				// for (int k = 0; k < queryXY.size(); k++) {
				//// System.out.println("x11--"+arr.get(i).getEntry().get(k).m_minDist);
				//// System.out.println("y22--"+arr.get(j).getEntry().get(k).m_minDist);
				// if (arr.get(i).getEntry().get(k).m_minDist >=
				// arr.get(j).getEntry().get(k).m_minDist) {
				// c++;
				// continue;
				// } else {
				// break;
				// }
				// }
				// if (c == queryXY.size()) {// System.out.println(c+"and"+queryXY.size());
				// arr.remove(i);
				// } // 删除对象列，行不变
				// }
				// }

				for (int i = 0; i < arr.size(); i++) {
					queue.add(arr.get(i).getEntry().get(0));
					//System.out.println("支配点入口=" + arr.get(i).getEntry().get(0).m_pEntry.getIdentifier());
				} // 将支配节点加入queue//按列查找去第一个样本对象值
					// System.out.println("queue.size()="+queue.size());
				arr.clear();
			} else// 叶子节点
			{
				System.out.println("Skylinepoint:");
				System.out.println(first.m_pEntry.getIdentifier() + ":" + first.m_minDist);
				// count++;
				// knearest = first.m_minDist;
			}

		}
		// }//=======================================================================
	}

	public long getIO() {
		return m_stats.getReads() + iindex.buffer.getIOs()[0];
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 4) {
			System.err.println("Usage: IRTree docstore tree_file fanout buffersize.");
			System.exit(-1);
		}
		String docfile = args[0];
		String treefile = args[1];
		int fanout = Integer.parseInt(args[2]);
		int buffersize = Integer.parseInt(args[3]);

		BtreeStore docstore = new BtreeStore(docfile, false);

		// Create a disk based storage manager.
		PropertySet ps = new PropertySet();

		ps.setProperty("FileName", treefile);
		// .idx and .dat extensions will be added.

		Integer i = new Integer(4096 * fanout / 100);
		ps.setProperty("PageSize", i);
		// specify the page size. Since the index may also contain user defined data
		// there is no way to know how big a single node may become. The storage manager
		// will use multiple pages per node if needed. Off course this will slow down
		// performance.

		ps.setProperty("BufferSize", buffersize);

		IStorageManager diskfile = new DiskStorageManager(ps);

		IBuffer file = new TreeLRUBuffer(diskfile, buffersize, false);
		// applies a main memory random buffer on top of the persistent storage manager
		// (LRU buffer, etc can be created the same way).

		i = new Integer(1); // INDEX_IDENTIFIER_GOES_HERE (suppose I know that in this case it is equal to
							// 1);
		ps.setProperty("IndexIdentifier", i);

		IRTree irtree = new IRTree(ps, file, true);

		long start = System.currentTimeMillis();
		// 注意在构建IR树的同时需要构建倒排序索引
		// 它的构建完全是根据之前的R树的节点来处理的！因为每一个R树的节点都会有一个自己的倒排序文件
		irtree.buildInvertedIndex(docstore);

		long end = System.currentTimeMillis();
		boolean ret = irtree.isIndexValid();
		if (ret == false)
			System.err.println("Structure is INVALID!");
		irtree.close();

		System.out.println("Minutes: " + ((end - start) / 1000.0f) / 60.0f);
		System.out.println("Finish!!");

	}
}
