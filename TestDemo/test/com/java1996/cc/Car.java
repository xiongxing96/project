package com.java1996.cc;

public class Car {
	private String leixing;
	private String chepai;
	private String yanse;
	private int sudu;  

	public void drive(int n){
	     this.sudu=this.sudu+n;
	}
	public String getleixing(){
		return leixing;
	}
	public String getchepai(){
		return chepai;
	}
	public String getyanse(){
		return yanse;
	}
	public int getsudu(){
		return sudu;
	}
	public void showInfo(){
		System.out.println("leixing:"+getleixing()+";chepai:"+getchepai()+";yanse:"+getyanse()+";sudu:"+getsudu());
	}

	     public Car(String leixing,String chepai,String yanse,int sudu){
	     this.leixing=leixing;
	     this.chepai=chepai;
	     this.yanse=yanse;
	     this.sudu=sudu;
	}


	     public static void main(String agrs[]){
	         Car c=new Car("Jeep","A12345","gray",70);
	         c.drive(10);
	         c.showInfo();
	      }
	  }
