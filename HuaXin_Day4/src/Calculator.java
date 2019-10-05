
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Calculator extends JFrame{
    private Container con;
    private JPanel frm1,frm2;
    private JButton[] command;
    private JTextField text1;
    private String tempValue="";
    private int flag1=0;
    private int flag2=0;
    private double oldValue=0;
    private int optionType=0;
    private JRadioButton radio1;
    private ButtonGroup group1;
    private JComboBox combo1;
    
    public Calculator(){
        super("计算器");
        command=new JButton[30];
        Font myfont=new Font("宋体",Font.PLAIN,35);
        con=getContentPane();
        con.setLayout(new FlowLayout());
        frm1=new JPanel();
        
        frm1.setBackground(Color.red);
        frm1.setPreferredSize(new Dimension(390,150));
        text1=new JTextField(20);        
        text1.setFont(myfont);
        text1.setHorizontalAlignment(JTextField.RIGHT);
        text1.setEditable(false);
        frm1.add(text1,BorderLayout.CENTER);
        con.add(frm1);
        
        
        frm2=new JPanel();
        frm2.setBackground(Color.blue);
        frm2.setPreferredSize(new Dimension(390,420));
        GridLayout gly=new GridLayout(6,5,10,10);        
        frm2.setLayout(gly);
        
        command[10]=new JButton("<-");
        command[10].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(tempValue.length()>0)
                   tempValue=tempValue.substring(0,tempValue.length()-1);
                text1.setText(tempValue);
            }
        });
        frm2.add(command[10]);
        
        command[11]=new JButton("CE");
        frm2.add(command[11]);
        
        command[12]=new JButton("C");
        command[12].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tempValue="";
                flag1=0;
                text1.setText("0");
            }
        });
        frm2.add(command[12]);
        
        command[13]=new JButton("+/-");
        command[13].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(tempValue.indexOf("-")<0)
                    tempValue="-"+tempValue;
                else
                    tempValue=tempValue.substring(1);
                text1.setText(tempValue);
            }
            
        });
        frm2.add(command[13]);
        command[14]=new JButton("sqrt");
        command[14].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                oldValue=Double.parseDouble(tempValue);
                tempValue="";
                optionType=5;
            }
        });
        frm2.add(command[14]);
        
        command[7]=new JButton("7");
        command[7].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tempValue=tempValue+"7";
                flag1=1;
                text1.setText(tempValue);
            }
            
        });
        frm2.add(command[7]);
        
        command[8]=new JButton("8");
        command[8].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tempValue=tempValue+"8";
                flag1=1;
                text1.setText(tempValue);
            }
            
        });
        frm2.add(command[8]);
        
         command[9]=new JButton("9");
         command[9].addActionListener(new ActionListener(){//匿名内部类
             @Override
             public void actionPerformed(ActionEvent e) {
                 //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 tempValue=tempValue+"9";
                 flag1=1;
                 text1.setText(tempValue);
             }
             
         });
        frm2.add(command[9]);
        
        command[15]=new JButton("/");
        command[15].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                oldValue=Double.parseDouble(tempValue);
                tempValue="";
                optionType=4;
            }
            
        });
        frm2.add(command[15]);
        
        command[16]=new JButton("%");
        command[16].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            	oldValue=Double.parseDouble(tempValue);
                optionType=6;
            }
        });
        frm2.add(command[16]);
        
        command[4]=new JButton("4");
        command[4].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tempValue=tempValue+"4";
                flag1=1;
                text1.setText(tempValue);
            }
        });
        frm2.add(command[4]);
        
        command[5]=new JButton("5");
        command[5].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tempValue=tempValue+"5";
                flag1=1;
                text1.setText(tempValue);
            }
            
        });
        frm2.add(command[5]);
        
        command[6]=new JButton("6");
        command[6].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tempValue=tempValue+"6";
                flag1=1;
                text1.setText(tempValue);
            }
            
        });
        frm2.add(command[6]);
        
        command[17]=new JButton("*");
        command[17].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                oldValue=Double.parseDouble(tempValue);
                tempValue="";
                optionType=3;
            }
        });
        frm2.add(command[17]);
        
        command[18]=new JButton("1/x");
        frm2.add(command[18]);
        
        command[1]=new JButton("1");
        command[1].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tempValue=tempValue+"1";
                flag1=1;
                text1.setText(tempValue);
            }
        });
        frm2.add(command[1]);
        
        command[2]=new JButton("2");
        command[2].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tempValue=tempValue+"2";
                flag1=1;
                text1.setText(tempValue);
            }    
        });
        frm2.add(command[2]);
        
        command[3]=new JButton("3");
        command[3].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                tempValue=tempValue+"3";
                flag1=1;
                text1.setText(tempValue);
            }
        });
        frm2.add(command[3]);
        
        command[19]=new JButton("-");
        command[19].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                oldValue=Double.parseDouble(tempValue);
                tempValue="";
                optionType=2;
            }    
        });
        frm2.add(command[19]);
        
        command[20]=new JButton("=");
        command[20].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(optionType==1){
                    double result=oldValue+Double.parseDouble(tempValue);
                    text1.setText(String.valueOf(result));
                }
                else if(optionType==2){
                	double result=oldValue-Double.parseDouble(tempValue);
                	text1.setText(String.valueOf(result));
                }
                else if(optionType==3){
                	double result=oldValue*Double.parseDouble(tempValue);
                	text1.setText(String.valueOf(result));
                }
                else if(optionType==4){
                	if(tempValue.compareTo("0")==1)
                		text1.setText("不合法");
                	else{	
                		double result=oldValue/Double.parseDouble(tempValue);
                		text1.setText(String.valueOf(result));
                	}
                }
                else if(optionType==5){
                	double result=Math.sqrt(oldValue);
                	text1.setText(String.valueOf(result));
                }
                else if(optionType==6){
                	double result=oldValue*(Double.parseDouble(tempValue)/100.0);
                	text1.setText(String.valueOf(result));
                }
            }
        });
        frm2.add(command[20]);
        
        command[0]=new JButton("0");
        frm2.add(command[0]);
        command[0].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(flag1==1){
                   tempValue=tempValue+"0";
                   text1.setText(tempValue);
                }else
                    text1.setText("0");
                
            }
            
        });
        command[21]=new JButton(".");
        command[21].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(tempValue.indexOf(".")<0)
                    tempValue=tempValue+".";
                text1.setText(tempValue);
            }
            
        });
        frm2.add(command[21]);
        command[22]=new JButton(" ");
        frm2.add(command[22]);        
        
        command[23]=new JButton("+");
        command[23].addActionListener(new ActionListener(){//匿名内部类
            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                oldValue=Double.parseDouble(tempValue);
                tempValue="";
                optionType=1;
            }
        });
        frm2.add(command[23]);
        command[24]=new JButton(" ");
        frm2.add(command[24]);
        con.add(frm2);
   
        setSize(400,600);
        setVisible(true);        
    }
    public static void main(String[] args) {
		Calculator c=new Calculator();
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

