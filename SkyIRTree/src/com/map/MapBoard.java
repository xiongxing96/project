package com.map;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import query.LkT;
import query.Skyline;

public class MapBoard extends JPanel implements MouseListener {
	public static int MARGIN=30;//边距 
	public static int GRID_SPAN=35;//网格间距 
	public static int ROWS=20;//坐标行数 
	public static int COLS=20;//坐标列数 
	Point[] pointsList=new Point[(ROWS+1)*(COLS+1)];//初始化每个数组元素为null 
	int pointsCount=0;//当前坐标网格的目标点个数 
	int xIndex,yIndex;//当前刚下目标点的索引 
	int flag=0;
	public JTextArea jTextArea;
	public MapBoard(JTextArea jt){
		jTextArea = jt;
		setBackground(Color.LIGHT_GRAY);//设置背景颜色为黄色 
		addMouseListener(this);//添加事件监听器 
		addMouseMotionListener(new MouseMotionListener(){
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				int x1=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN; 
			    int y1=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;//将鼠标单击的坐标位置转化为网格索引 
			    if(x1<0||x1>ROWS||y1<0||y1>COLS)//||gameOver||findChess(x1,y1)){//游戏已经结束，不能下；落在棋盘外，不能下；x，y位置已经有目标点存在，不能下 
			     {setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//设置成默认形状 
			    }else{ 
			     setCursor(new Cursor(Cursor.HAND_CURSOR));//设置成手型 
			    }
			}
				@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
					
			}     
		});
	}
	
	/*绘制*/ 
	public void paintComponent(Graphics g){ 
		System.out.println("坐标轴已画好。。。。。。。。。。。。");
		super.paintComponent(g);//画坐标网格
		for(int i=0;i<=ROWS;i++){//画横线 
			g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN); 
		} 
		for(int i=0;i<=COLS;i++){//画直线 
			g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN,MARGIN+ROWS*GRID_SPAN); 
		} 
		/*画棋子*/ 
		
		for(int i=0;i<pointsCount;i++){ 
			int xPos=pointsList[i].getX()*GRID_SPAN+MARGIN;//网格交叉的x坐标 
			int yPos=pointsList[i].getY()*GRID_SPAN+MARGIN;//网格交叉的y坐标 
			g.setColor(pointsList[i].getColor());//设置颜色 
			g.fillOval(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, Point.DIAMETER, Point.DIAMETER); 
			//if(i==pointsCount-1){ 
			if(flag==1&&!pointsList[i].getColor().equals(Color.BLACK)) {
				g.setColor(Color.red);//标记最后一个目标点为红色 
				g.drawRect(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, Point.DIAMETER, Point.DIAMETER); 
				
				g.setColor(Color.red);//标记最后一个目标点为红色 
				g.drawRect(35+Point.DIAMETER/2, 35+Point.DIAMETER/2, 35*2-5, 35*7+Point.DIAMETER);	
				
				g.setColor(Color.red);//标记最后一个目标点为红色 
				g.drawRect(30+35*2+Point.DIAMETER/2+5, 30+Point.DIAMETER/2+5, 35*4+Point.DIAMETER, 35*3+Point.DIAMETER);	
				
				g.setColor(Color.WHITE);//标记最后一个目标点为红色 
				g.drawRect(30+35*3+Point.DIAMETER/2+5, 30+35*4+Point.DIAMETER/2+5, 35*4-5, 35*3+Point.DIAMETER);	
				
				g.setColor(Color.red);//标记最后一个目标点为红色 
				g.drawRect(40, 40, 35*8-Point.DIAMETER+10, 35*8+Point.DIAMETER/2);	
			} 
			
		} 
	} 
	public void paintPoints() throws NumberFormatException, IOException{
/*
 * IRTreeskyline的输入信息		
 */
		//商家点信息及文本
		FileReader fr=new FileReader("fifteendocument.txt");
		BufferedReader br=new BufferedReader(fr);
		String line="";
		String[] arrs=null;
		while((line=br.readLine())!=null) {
			arrs=line.split(",");
			Point p=new Point(Integer.valueOf(arrs[0]), Integer.valueOf(arrs[1]), Integer.valueOf(arrs[2]),Color.WHITE);
			//pointsList[pointsCount].setMessager();
			pointsList[pointsCount]=p;
			pointsList[pointsCount].setWord(arrs[3]);
			pointsList[pointsCount].setMessager();
			pointsCount++;
		}
		br.close();
		fr.close();
		
		//查询点信息
		FileReader fr1=new FileReader("querydata.txt");
		BufferedReader br1=new BufferedReader(fr1);
		String line1="";
		String[] arrs1=null;
		while((line1=br1.readLine())!=null) {
			arrs1=line1.split(",");
			Point p=new Point(Integer.valueOf(arrs1[0]), Integer.valueOf(arrs1[1]), Integer.valueOf(arrs1[2]),Color.BLACK);
			//pointsList[pointsCount].setMessager();
			pointsList[pointsCount]=p;
			pointsCount++;
		}
		br1.close();
		fr1.close();
		
/*
 * 以下为skyline线性扫描的输入点信息		
 */
//		FileReader fr1=new FileReader("D:/object.txt");
//		BufferedReader br1=new BufferedReader(fr1);
//		String line1="";
//		String[] arrs1=null;
//		while((line1=br1.readLine())!=null) {
//			arrs1=line1.split(",");
//			Point p=new Point(Integer.valueOf(arrs1[0]), Integer.valueOf(arrs1[1]), Integer.valueOf(arrs1[2]),Color.WHITE);
//			//pointsList[pointsCount].setMessager();
//			pointsList[pointsCount]=p;
//			pointsCount++;
//		}
//		br1.close();
//		fr1.close();
//		
//		
//		//读取object点的word文本
//		FileReader fr2=new FileReader("D:/word.txt");
//		BufferedReader br2=new BufferedReader(fr2);
//		String line2="";
//		String[] arrs2=null;
//		int t=0;
//		while((line2=br2.readLine())!=null) {
//			pointsList[t].setWord(line2);
//			pointsList[t].setMessager();
//			t++;
//		}
//		br2.close();
//		fr2.close();
//		
//		
//		//读取查询点
//		FileReader fr3=new FileReader("D:/query.txt");
//		BufferedReader br3=new BufferedReader(fr3);
//		String line3="";
//		String[] arrs3=null;
//		while((line3=br3.readLine())!=null) {
//			arrs3=line3.split(",");
//			Point q=new Point(Integer.valueOf(arrs3[0]),Integer.valueOf(arrs3[1]), Integer.valueOf(arrs3[2]),Color.BLACK);
//			pointsList[pointsCount]=q;
//			pointsCount++;
//		}
//		br3.close();
//		fr3.close();
//		Point p1=new Point(1, 1, 1, Color.BLACK);
//		Point p2=new Point(2, 2, 2, Color.BLACK);
//		Point p3=new Point(3, 7, 8, Color.BLACK);
//		Point p4=new Point(4, 4, 5, Color.BLACK);
//		pointsList[0]=p1;
//		pointsList[1]=p2;
//		pointsList[2]=p3;
//		pointsList[3]=p4;
//		pointsCount=4;
    	repaint();
	}
	public void clearPoints() {
		System.out.println("调用清空。。。。。。。");
		for(int i=0;i<pointsList.length;i++)
			pointsList[i]=null;
		pointsCount=0;
		jTextArea.setText("");
		flag=0;
		repaint();
		
	}
	
	private boolean findPoints(int x,int y){ 
		  for(Point c:pointsList){ 
		   if(c!=null&&c.getX()==x&&c.getY()==y) 
		    return true; 
		  } 
		  return false; 
	}
	private int getPointID(int x,int y) {
		for(int i=0;i<pointsList.length;i++) {
			Point c=pointsList[i];
			if(c!=null&&c.getX()==x&&c.getY()==y) 
			return i; 
		}
		return 1000000;
	}
	public void paintskylinepoints() {
		Skyline skyline=new Skyline();
		jTextArea.append("querywordID="+skyline.getWordId()[0]+","+skyline.getWordId()[1]+","+skyline.getWordId()[2]+"\n");
		int []p=skyline.getSkypoint();
		
		for(int i=0;i<p.length;i++) {
			System.out.println(p[i]);
			for(int j=0;j<pointsList.length;j++) {
				Point c=pointsList[j];
				if(c!=null&&c.getId()==p[i]&&!c.getColor().equals(Color.BLACK)) 
					c.setColor(Color.RED);
			}}
		
		flag=1;
		repaint();
	}
	public void painttopkpoints() {
		LkT lkt=new LkT();
		jTextArea.append("querywordID="+lkt.getWordID()+"\n");
		int []p=lkt.getLkt();
		for(int i=0;i<p.length;i++) {
			System.out.println(p[i]);
		for(int j=0;j<pointsList.length;j++) {
			Point c=pointsList[j];
			if(c!=null&&c.getId()==p[i]&&c.getColor().equals(Color.WHITE)) 
				c.setColor(Color.ORANGE);
		}}
		flag=0;
		repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		xIndex=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN; 
		yIndex=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;//将鼠标单击的坐标位置转化为网格索引 
		if(xIndex<0||xIndex>ROWS||yIndex<0||yIndex>COLS)//棋子落在棋盘外，不能下 
		   return ; 
		if(findPoints(xIndex,yIndex))//x,y位置已经有棋子存在，不能下 
			{System.out.println(xIndex+"   "+yIndex+"  "+pointsList[getPointID(xIndex, yIndex)].getColor()+"    "+pointsCount);
			System.out.println(getPointID(xIndex, yIndex));
				if(pointsList[getPointID(xIndex, yIndex)].getColor().equals(Color.BLACK))
					jTextArea.append("This is a QueryPoint. || "+pointsList[getPointID(xIndex, yIndex)].getMessager()+"\n");
				else
					jTextArea.append("This is a ObjectPoint. || "+pointsList[getPointID(xIndex, yIndex)].getMessager()+"\n");
			}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
