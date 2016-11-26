package blackAndWhite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class Draw extends JFrame implements ActionListener  {
	private BufferedReader reader; // 创建BufferedReader对象
	private ServerSocket server; // 创建ServerSocket对象
	private Socket soocket; // 创建Socket对象socket
	private static final long serialVersionUID = 1L;
	private static PrintWriter writer;
	private Socket socket;//客户端的套接字
	public static int dnx;//电脑所下的棋子的x
	public static int dny;//电脑所下的棋子的y
	public static int ljx;//联机别人发过来的x
	public static int ljy;//联机别人发过来的y
	public static int x;//屏幕上获得的x
	public static int y;//屏幕上获得的y
	public static int x1;//所下棋子的x
	public static int y1;//所下棋子的y
	public static int black = 1;//黑棋为1
	public static int white = -1;//白棋为-1
	public static int hqx;
	public static int hqy;
	static int [][]qizi=new int[8][8];//棋盘每一格对应二维数组的一个地址
	static int [][]diannaoxia=new int[8][8];//电脑检索棋盘上可以下的地方
	static int [][]qiziwz = new int [8][8];
	static int [][]shunxu = new int [8][8];
	static int sx =0;
	public static int dangQian = black ;//当前的棋子为（默认黑棋先下）
	public  static JButton start;//开始按钮
	public static JLabel xiaqifang;//当前下棋方是谁
	public static JButton computerStart;//人机开始按钮
	public static JButton heiqi;
	public static JButton baiqi;
	public static JButton huiqi;
	public static   MyPanel mypanel;//画板
	public static JButton lianji;//联机按钮
	public static String iip;//对方的ip地址
	public static int WJ;//玩家自己的棋子颜色
	public static int DN;//电脑的棋子颜色
	public static int DF=0;//判断对方下棋没
	public static int WJ2;
	public static boolean GB;
	public static int chxs=1;
	public static int hq;
	public static boolean  zz;
	public static boolean  zzdn;
	public static int n=0;
	public static int m=0;
	//构造方法
	public Draw(){
		super("黑白棋");
		qizi[3][3]=-1;
		qizi[4][4]=-1;
		qizi[3][4]=1;
		qizi[4][3]=1;
		x1=5;
		y1=4;
		setVisible(true);
		setSize(1200, 1050);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		shuru();
	}


	//按钮动作捕捉
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			n++;
			zzdn=true;
			if(n==1){zz=true;}
			cxyx();
			zz=false;
			dangQian =1;
			xiaqifang.setText("当前下棋方为黑方");
			renren();
			System.out.println("m的值为"+m);
		}//开始按钮
		if (e.getSource()== computerStart){
			n++;
			cxyx();
			dangQian =1;
			xiaqifang.setText("当前下棋方为黑方");
			xuanzerj xz = new xuanzerj();
			renji();
		}//人机按钮
		if(e.getSource()==lianji){
			Ip ip= new Ip();
			lianji();
			this.mypanel.qipan();
		}//联机按钮
		if(e.getSource()==huiqi){
			hqch();
		}

		//主方法
	}
	public static void main(String[] args){
		Draw draw = new Draw();
	}
	//主界面
	private void shuru() {
		this.setLayout(new BorderLayout());//布局管理器
		JPanel panel = new JPanel(new FlowLayout());//创建画板对象
		this.mypanel = new MyPanel();//mypanel为类Mypanel的对象
		mypanel.setBackground(Color.lightGray);//为mypanel添加背景
		this.add(panel, BorderLayout.NORTH);//添加画板在窗体的北面
		this.add(this.mypanel, BorderLayout.CENTER);//添加画板在窗体的中央
		computerStart = new JButton("人机PK");//按钮实例化，并赋名
		start = new JButton("开始游戏");//按钮实例化并赋名
		xiaqifang = new JLabel("未确定下棋方");//标签实例化并赋名
		lianji = new JButton("联机游戏");//按钮实例化并赋名
		huiqi=new JButton("悔棋");
		panel.add(xiaqifang);//添加按钮至panel
		panel.add(start);//添加按钮至pannel
		panel.add(computerStart);//添加按钮至pannel
		panel.add(lianji);//添加按钮至pannel
		panel.add(huiqi);
		huiqi.addActionListener(this);
		start.addActionListener(this);//为开始按钮添加动作监听器
		lianji.addActionListener(this);//为开始按钮添加动作监听器
		computerStart.addActionListener(this);//为开始按钮添加动作监听器
		getserver();
	}
	//终止方法
	public boolean zhongzhi(boolean zz){
		return zz;
	}
	public static boolean zhongzhidn(boolean zzdn){
		return zzdn;
	}
	//人人
	public void renren(){

		addMouseListener(new MouseAdapter() {   //鼠标监听器
			public void mouseClicked(MouseEvent e){
				if(zz){return;}
				int i = e.getButton();//按了鼠标哪个键
				x =e.getX()-11;//屏幕上的x 并本地初始化
				y=e.getY()-83;//屏幕上的x 并本地初始化
				x1=x/100;//为x取正为棋盘上的坐标
				y1=y/100;//为y取正为棋盘上的坐标
				System.out.println(x1+","+y1);//略
				System.out.println(x+","+y);//略
				if (i == MouseEvent.BUTTON1&&x1>=1&&x1<=8&&y1>=1&&y1<=8){ //确认鼠标点击在棋盘内并为鼠标左键
					if (qizi[x1-1][y1-1]==0){//如果放的是空位
						if (check(dangQian,x1,y1)==false){if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
							if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}	System.out.println("你不能这样下");}
						if(nengxiafou(dangQian)==false){shengfuqizi();x1=-1;y1=-1;dangQian=-dangQian;if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
							if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}}

						else{if (check(dangQian,x1,y1)&&nengxiafou(dangQian)){
							if (toleft(dangQian,x1,y1)){Change.changeleft(dangQian);}
							if (toright(dangQian,x1,y1)){Change.changeright(dangQian);}
							if (todown(dangQian,x1,y1)){Change.changedown(dangQian);}
							if (toup(dangQian,x1,y1)){Change.changeup(dangQian);}
							if (toleftdown(dangQian,x1,y1)){Change.changeleftdown(dangQian);}
							if (toleftup(dangQian,x1,y1)){Change.changeleftup(dangQian);}
							if (torightup(dangQian,x1,y1)){Change.changerightup(dangQian);}
							if (torightdown(dangQian,x1,y1)){Change.changerightdown(dangQian);}
							qizi[x1-1][y1-1]=dangQian;
							huiqi(dangQian,x1,y1);
							mypanel.qizi();
							dangQian = - dangQian;
							shengfu();
							if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
							if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}

						}
						}
					}
					else{
						System.out.println("这里已经有棋子了");
					}
				}
			}
		});

	}
	//联机
	public void lianji(){
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				int i = e.getButton();
				x =e.getX()-11;
				y=e.getY()-83;
				x1=x/100;
				y1=y/100;

				if (i == MouseEvent.BUTTON1&&x1>=1&&x1<=8&&y1>=1&&y1<=8){
					if(dangQian==WJ&&DF==0){//这里要想好怎么设计黑白下顺序
						if (qizi[x1-1][y1-1]==0){
							if (check(dangQian,x1,y1)==false){if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
								if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}	System.out.println("你不能这样下");}


							else{if (check(dangQian,x1,y1)&&nengxiafou(dangQian)){
								if (toleft(dangQian,x1,y1)){Change.changeleft(dangQian);}
								if (toright(dangQian,x1,y1)){Change.changeright(dangQian);}
								if (todown(dangQian,x1,y1)){Change.changedown(dangQian);}
								if (toup(dangQian,x1,y1)){Change.changeup(dangQian);}
								if (toleftdown(dangQian,x1,y1)){Change.changeleftdown(dangQian);}
								if (toleftup(dangQian,x1,y1)){Change.changeleftup(dangQian);}
								if (torightup(dangQian,x1,y1)){Change.changerightup(dangQian);}
								if (torightdown(dangQian,x1,y1)){Change.changerightdown(dangQian);}
								writer.println(x1+","+y1);
								qizi[x1-1][y1-1]=dangQian;
								mypanel.qizi();
								dangQian = - dangQian;
								shengfu();
								if(nengxiafou(dangQian)==false){shengfuqizi();x1=-1;y1=-1;dangQian=-dangQian;if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
									if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");DF=0;}}
								if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
								if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}
								DF=1;
							}
							}
						}
						else{
							System.out.println("这里已经有棋子了");
						}
					}
				}
			}
		});
	}
	//接受信息后下棋的方法
	public void DFxiaqi(){
		if (qizi[ljx-1][ljy-1]==0){
			if (check(dangQian,ljx,ljy)==false){if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
				if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}	System.out.println("你不能这样下");}



			else{if (check(dangQian,ljx,ljy)&&nengxiafou(dangQian)){
				if (ljtoleft(dangQian,ljx,ljy)){ljChange.changeleft(dangQian);}
				if (ljtoright(dangQian,ljx,ljy)){ljChange.changeright(dangQian);System.out.println("我执行了");}
				if (ljtodown(dangQian,ljx,ljy)){ljChange.changedown(dangQian);}
				if (ljtoup(dangQian,ljx,ljy)){ljChange.changeup(dangQian);}
				if (ljtoleftdown(dangQian,ljx,ljy)){ljChange.changeleftdown(dangQian);}
				if (ljtoleftup(dangQian,ljx,ljy)){ljChange.changeleftup(dangQian);}
				if (ljtorightup(dangQian,ljx,ljy)){ljChange.changerightup(dangQian);}
				if (ljtorightdown(dangQian,ljx,ljy)){ljChange.changerightdown(dangQian);}
				qizi[ljx-1][ljy-1]=dangQian;
				mypanel.qizi();
				dangQian = - dangQian;
				shengfu();
				if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
				if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}
				if(nengxiafou(dangQian)==false){shengfuqizi();ljx=-1;ljy=-1;dangQian=-dangQian;if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
					if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");DF=1;}}
				DF=0;

			}
			}
		}
		else{
			System.out.println("这里已经有棋子了");
		}
	}
	//人机
	public void renji(){
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				int i = e.getButton();
				x =e.getX()-11;
				y=e.getY()-83;
				x1=x/100;
				y1=y/100;
				System.out.println(x1+","+y1);
				System.out.println(x+","+y);
				if (i == MouseEvent.BUTTON1&&x1>=1&&x1<=8&&y1>=1&&y1<=8){
					if (qizi[x1-1][y1-1]==0&&dangQian==WJ){
						if (check(dangQian,x1,y1)==false){if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
							if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}	System.out.println("你不能这样下");}
						else{if (check(dangQian,x1,y1)&&nengxiafou(dangQian)){
							if (toleft(dangQian,x1,y1)){Change.changeleft(dangQian);}
							if (toright(dangQian,x1,y1)){Change.changeright(dangQian);}
							if (todown(dangQian,x1,y1)){Change.changedown(dangQian);}
							if (toup(dangQian,x1,y1)){Change.changeup(dangQian);}
							if (toleftdown(dangQian,x1,y1)){Change.changeleftdown(dangQian);}
							if (toleftup(dangQian,x1,y1)){Change.changeleftup(dangQian);}
							if (torightup(dangQian,x1,y1)){Change.changerightup(dangQian);}
							if (torightdown(dangQian,x1,y1)){Change.changerightdown(dangQian);}
							qizi[x1-1][y1-1]=dangQian;
							mypanel.qizi();
							dangQian = - dangQian;
							shengfu();
							if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
							if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}
							if(nengxiafou(dangQian)==false){shengfuqizi();x1=-1;y1=-1;dangQian=-dangQian;if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
								if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}}
							else diannao();
						}
						}
					}
					else{
						System.out.println("这里已经有棋子了");
					}
				}
			}
		});
	}
	//电脑下棋后检索

	//电脑下的棋子向左检索
	public static boolean dtoleft(int dangqian,int i,int j){
		if(i==1||i==2){return false;}
		if (qizi[i-2][j-1]==0){ return false;

		}
		else {
			if(qizi[i-2][j-1]==dangqian){
				return false ;
			}
			else
			{ for (;i>1;i--){
				if (qizi[i-2][j-1]==-dangqian){
					if(i-2==1){if (qizi[i-3][j-1]==dangqian){DNChange.dlastx1=i-2;return true;}else return false;}
					else  continue;
				}
				if(qizi[i-2][j-1]==dangqian){
					DNChange.dlastx1 = i-1;
					return true;
				}
				if (qizi[i-2][j-1]==0){
					return false;
				}
			}
			}

		}
		return false;
	}

	//电脑下的棋子向右检索
	public static boolean dtoright(int dangqian,int i,int j){
		if (i==7||i==8){return false;}
		if (qizi[i][j-1]==0){ return false;

		}
		else {
			if(qizi[i][j-1]==dangqian){
				return false ;
			}
			else
			{ for (;i<8;i++){
				if (qizi[i][j-1]==-dangqian){
					if(i+2==8){if (qizi[i+1][j-1]==dangqian){DNChange.dlastx2=i+2;return true;}else return false;}
					else continue;
				}
				if(qizi[i][j-1]==dangqian){
					DNChange.dlastx2 = i+1;
					return true;
				}
				if (qizi[i][j-1]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//电脑下的棋子向上检索
	public static boolean dtodown(int dangqian,int i ,int j){
		if (j==7||j==8){return false;}
		if (qizi[i-1][j]==0){ return false;

		}
		else {
			if(qizi[i-1][j]==dangqian){
				return false ;
			}
			else
			{ for
					(;j<8;j++){//这里有bug 不能取到8
				if (qizi[i-1][j]==-dangqian){
					if (j+2==8){if (qizi[i-1][j+1]==dangqian){DNChange.dlasty1=j+2;return true;}else return false;
					}
					else continue;
				}
				if (qizi[i-1][j]==dangqian){
					DNChange.dlasty1=j+1;
					return true;
				}
				if (qizi[i-1][j]==0){
					return false;
				}
				//设想方法1 判断第八个为当前棋子则返回ture;
			}
			}

		}
		return false;
	}
	//电脑下的棋子向下检索
	public static boolean dtoup(int dangqian,int i ,int j){
		if(j==1||j==2){return false;}
		if (qizi[i-1][j-2]==0){ return false;

		}
		else {
			if(qizi[i-1][j-2]==dangqian){
				return false ;
			}
			else
			{ for
					(;j>1;j--){//这里有bug 不能取到8
				if (qizi[i-1][j-2]==-dangqian){
					if(j-2==1){if (qizi[i-1][j-3]==dangqian){DNChange.dlasty2=j-2;return true;}else return false;}
					else  continue;
				}
				if(qizi[i-1][j-2]==dangqian){
					DNChange.dlasty2=j-1;
					return true;
				}
				if (qizi[i-1][j-2]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//电脑下的棋子向左下检索
	public static boolean dtoleftdown(int dangqian,int i ,int j){
		if (j==7||j==8){return false;}
		if (i==1||i==2){return false;}
		if (qizi[i-2][j]==0){ return false;

		}
		else {
			if(qizi[i-2][j]==dangqian){
				return false ;
			}
			else
			{ for
					(;i>1||j<8;i--,j++){
				if (qizi[i-2][j]==-dangqian){
					if(i-2==1||j+2==8){if(qizi[i-3][j+1]==dangqian){DNChange.dlastleftdownx=i-2;DNChange.dlastleftdowny=j+2;return true;}else return false;}
					else  continue;
				}
				if(qizi[i-2][j]==dangqian){
					DNChange.dlastleftdownx=i-1;
					DNChange.dlastleftdowny=j+1;
					return true;
				}
				if (qizi[i-2][j]==0){
					return false;
				}
			}
			}

		}

		return false;
	}

	//电脑下的棋子向左上检索
	public static boolean  dtoleftup(int dangqian,int i ,int j){
		if (i==1||i==2){return false;}
		if(j==1||j==2){return false;}
		if (qizi[i-2][j-2]==0){ return false;

		}
		else {
			if(qizi[i-2][j-2]==dangqian){
				return false ;
			}
			else
			{ for
					(;i>1||j>1;i--,j--){
				if (qizi[i-2][j-2]==-dangqian){
					if(i-2==1||j-2==1){if(qizi[i-3][j-3]==dangqian){DNChange.dlastleftupx=i-2;DNChange.dlastleftupy=j-2;return true;}else return false;}
					else continue;
				}
				if(qizi[i-2][j-2]==dangqian){
					DNChange.dlastleftupx=i-1;
					DNChange.dlastleftupy=j-1;
					return true;
				}
				if (qizi[i-2][j-2]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//电脑下的棋子向右上检索
	public static boolean dtorightup(int dangqian,int i ,int j){
		if (i==7||i==8){return false;}
		if (j==1||j==2){return false;}
		if (qizi[i][j-2]==0){ return false;

		}
		else {
			if(qizi[i][j-2]==dangqian){
				return false ;
			}
			else
			{ for
					(;i<8||j>1;i++,j--){
				if (qizi[i][j-2]==-dangqian){
					if(i+2==8||j-2==1){if(qizi[i+1][j-3]==dangqian){DNChange.dlastrightupx=i+2;DNChange.dlastrightupy=j-2;return true;}else return false;}
					else continue;
				}
				if(qizi[i][j-2]==dangqian){
					DNChange.dlastrightupx=i+1;
					DNChange.dlastrightupy=j-1;
					return true;
				}
				if (qizi[i][j-2]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//电脑下的棋子向右下检索
	public static boolean dtorightdown(int dangqian,int i ,int j){
		if(j==8||j==7){return false;}
		if(i==8||i==7){return false;}
		if (qizi[i][j]==0){ return false;

		}
		else {
			if(qizi[i][j]==dangqian){
				return false ;
			}
			else
			{ for
					(;i<8||j<8;i++,j++){
				if (qizi[i][j]==-dangqian){
					if(i+2==8||j+2==8){if (qizi[i+1][j+1]==dangqian){DNChange.dlastrightdownx=i+2;DNChange.dlastrightdowny=j+2;return true;}else return false;}
					else continue;
				}
				if(qizi[i][j]==dangqian){
					DNChange.dlastrightdownx=i+1;
					DNChange.dlastrightdowny=j+1;
					return true;
				}
				if (qizi[i][j]==0){
					return false;
				}
			}
			}

		}
		return false;
	}

	//电脑变色类
	static  class DNChange {
		static int dlastx1;
		static int dlastx2;
		static int dlasty1;
		static int dlasty2;
		static int dlastrightdownx;
		static int dlastrightdowny;
		static int dlastrightupx;
		static int dlastrightupy;
		static int dlastleftdownx;
		static int dlastleftdowny;
		static int dlastleftupx;
		static int dlastleftupy;
		static public void changeleft(int dangqian){
			for (int i=dnx;i>dlastx1;i--){
				if (i==1){
					qizi[0][dny-1]=dangqian;
					break;
				}
				qizi[i-2][dny-1]=dangqian;
			}

		}
		static public void changeright(int dangqian){
			for (int i=dnx;i<dlastx2;i++){
				if(i==8){
					qizi[7][dny-1]=dangqian;
					break;
				}
				qizi[i][dny-1]=dangqian;
			}

		}
		static public void changedown(int dangqian){
			for (int j=dny;j<dlasty1;j++){
				if(j==8){
					qizi[dnx-1][7]=dangqian;
					break;
				}
				qizi[dnx-1][j]=dangqian;
			}

		}
		static public void changeup(int dangqian){
			for (int j=dny;j>dlasty2;j--){
				if(j==1){
					qizi[dnx-1][0]=dangqian;
					break;
				}
				qizi[dnx-1][j-2]=dangqian;
			}

		}
		static public void changeleftdown(int dangqian){
			for (int i=dnx,j=dny;i>dlastleftdownx;i--,j++){
				if (i==1){
					qizi[0][j]=dangqian;
					break;
				}
				if(j==8){
					qizi[i-2][7]=dangqian;
					break;
				}

				qizi[i-2][j]=dangqian;
			}

		}
		static public void changeleftup(int dangqian){
			for (int i=dnx,j=dny;i>dlastleftupx;i--,j--){
				if(i==1){
					qizi[0][j-2]=dangqian;
					break;
				}
				if(j==1){
					qizi[i-2][j]=dangqian;
					break;
				}
				qizi[i-2][j-2]=dangqian;
			}

		}
		static public void changerightup(int dangqian){
			for (int i=dnx,j=dny;i<dlastrightupx;i++,j--){
				qizi[i][j-2]=dangqian;
			}

		}
		static public void changerightdown(int dangqian){
			for (int i=dnx,j=dny;i<dlastrightdownx;i++,j++){
				qizi[i][j]=dangqian;
			}

		}

	}
	//总检索
	public static boolean Dcheck(){
		for(int i=1;i<=8;i++){
			for(int j=1;j<=8;j++){
				if(diannaoxia[i-1][j-1]==-1){
					return true;
				}
			}
		}
		return false;
	}

	//电脑主方法
	public static void diannao(){
		int ii;
		int jj;
		if(zhongzhidn(zzdn)){return;}
		if(dangQian!=-WJ)return;
		for( ii=1 ;ii<=8;ii++){
			for ( jj=1;jj<=8;jj++){
				if(qizi[ii-1][jj-1]==-WJ){
					if(Dtoleft(dangQian, ii, jj));
					if(Dtoright(dangQian, ii, jj));
					if(Dtoup(dangQian, ii, jj));
					if(Dtodown(dangQian, ii, jj));
					if(Dtoleftup(dangQian, ii, jj));
					if(Dtoleftdown(dangQian, ii, jj));
					if(Dtorightup(dangQian, ii, jj));
					if(Dtorightdown(dangQian, ii, jj));
				}
			}
		}
		if(Dcheck()==false){shengfu();dangQian=-dangQian;if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
			if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}diannao();}
		else{
			for ( ii=1;ii<=8;ii++){
				for (jj=1;jj<=8;jj++){

					if(diannaoxia[ii-1][jj-1]==-1){
						YX5(ii,jj);mypanel.chonghua();
					}
				}
			}
			for ( ii=1;ii<=8;ii++){
				for (jj=1;jj<=8;jj++){

					if(diannaoxia[ii-1][jj-1]==-1){
						YX4(ii,jj);mypanel.chonghua();
					}
				}
			}
			for ( ii=1;ii<=8;ii++){
				for (jj=1;jj<=8;jj++){

					if(diannaoxia[ii-1][jj-1]==-1){
						YX3(ii,jj);mypanel.chonghua();
					}
				}
			}
			for ( ii=1;ii<=8;ii++){
				for (jj=1;jj<=8;jj++){

					if(diannaoxia[ii-1][jj-1]==-1){
						YX2(ii,jj);mypanel.chonghua();
					}
				}
			}
			for ( ii=1;ii<=8;ii++){
				for (jj=1;jj<=8;jj++){

					if(diannaoxia[ii-1][jj-1]==-1){
						YX1(ii,jj);mypanel.chonghua();
					}
				}
			}




			for ( ii=1;ii<=8;ii++){
				for (jj=1;jj<=8;jj++){

					if(diannaoxia[ii-1][jj-1]==-1){
						YX0(ii,jj);mypanel.chonghua();
					}
				}
			}
		}
	}


	//电脑对棋子进行判断
	//电脑判断下棋的优先级
	public static void YX0(int i,int j){
		if(i==2&&j==2||i==7&&j==2||i==2&&j==7||i==7&&j==7){
			dnx=i;
			dny=j;
			if(dtoleft(dangQian, dnx, dny)){DNChange.changeleft(dangQian);}
			if(dtoright(dangQian, dnx, dny)){DNChange.changeright(dangQian);}
			if(dtoup(dangQian, dnx, dny)){DNChange.changeup(dangQian);}
			if(dtodown(dangQian, dnx, dny)){DNChange.changedown(dangQian);}
			if(dtorightup(dangQian, dnx, dny)){DNChange.changerightup(dangQian);}
			if(dtorightdown(dangQian, dnx, dny)){DNChange.changerightdown(dangQian);}
			if(dtoleftup(dangQian, dnx, dny)){DNChange.changeleftup(dangQian);}
			if(dtoleftdown(dangQian, dnx, dny)){DNChange.changeleftdown(dangQian);}
			qizi[i-1][j-1]=dangQian;
			mypanel.chonghua();
			dangQian=-dangQian;
			if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
			if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}
			if(nengxiafou(dangQian)==false){shengfuqizi();x1=-1;y1=-1;dangQian=-dangQian;if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");diannao();}
				if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}}
			shengfu();
			for (int ii=1;ii<=8;ii++){
				for (int jj=1;jj<=8;jj++){
					diannaoxia[ii-1][jj-1]=0;
				}
			}
		}
	}
	public static void YX1(int i,int j){
		if(i==2&&j==1||i==1&&j==2||i==7&&j==1||i==8&&j==2||i==1&&j==7||i==2&&j==8
				||i==7&&j==8||i==8&&j==7){
			dnx=i;
			dny=j;
			if(dtoleft(dangQian, dnx, dny)){DNChange.changeleft(dangQian);}
			if(dtoright(dangQian, dnx, dny)){DNChange.changeright(dangQian);}
			if(dtoup(dangQian, dnx, dny)){DNChange.changeup(dangQian);}
			if(dtodown(dangQian, dnx, dny)){DNChange.changedown(dangQian);}
			if(dtorightup(dangQian, dnx, dny)){DNChange.changerightup(dangQian);}
			if(dtorightdown(dangQian, dnx, dny)){DNChange.changerightdown(dangQian);}
			if(dtoleftup(dangQian, dnx, dny)){DNChange.changeleftup(dangQian);}
			if(dtoleftdown(dangQian, dnx, dny)){DNChange.changeleftdown(dangQian);}
			qizi[i-1][j-1]=dangQian;
			mypanel.chonghua();
			dangQian=-dangQian;
			if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
			if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}
			if(nengxiafou(dangQian)==false){shengfuqizi();x1=-1;y1=-1;dangQian=-dangQian;if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");diannao();}
				if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}}
			shengfu();
			for (int ii=1;ii<=8;ii++){
				for (int jj=1;jj<=8;jj++){
					diannaoxia[ii-1][jj-1]=0;
				}
			}
		}
	}
	public static void YX2(int i,int j){
		if (i==3&&j==2||i==4&&j==2||i==5&&j==2||i==6&&j==2||i==2&&j==3||i==2&&j==4||i==2&&j==5||i==2&&j==6
				||i==7&&j==3||i==7&&j==4||i==7&&j==5||i==7&&j==6||i==3&&j==7||i==4&&j==7||i==5&&j==7||i==6&&j==7){
			dnx=i;
			dny=j;
			if(dtoleft(dangQian, dnx, dny)){DNChange.changeleft(dangQian);}
			if(dtoright(dangQian, dnx, dny)){DNChange.changeright(dangQian);}
			if(dtoup(dangQian, dnx, dny)){DNChange.changeup(dangQian);}
			if(dtodown(dangQian, dnx, dny)){DNChange.changedown(dangQian);}
			if(dtorightup(dangQian, dnx, dny)){DNChange.changerightup(dangQian);}
			if(dtorightdown(dangQian, dnx, dny)){DNChange.changerightdown(dangQian);}
			if(dtoleftup(dangQian, dnx, dny)){DNChange.changeleftup(dangQian);}
			if(dtoleftdown(dangQian, dnx, dny)){DNChange.changeleftdown(dangQian);}
			qizi[i-1][j-1]=dangQian;
			mypanel.chonghua();
			dangQian=-dangQian;
			if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
			if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}
			if(nengxiafou(dangQian)==false){shengfuqizi();x1=-1;y1=-1;dangQian=-dangQian;if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");diannao();}
				if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}}
			shengfu();
			for (int ii=1;ii<=8;ii++){
				for (int jj=1;jj<=8;jj++){
					diannaoxia[ii-1][jj-1]=0;
				}
			}
		}
	}
	public static void YX3(int i,int j){
		if(i==3&&j==3||i==4&&j==3||i==5&&j==3||i==6&&j==3||i==3&&j==4||i==3&&j==5||i==3&&j==6||i==4&&j==6||i==5&&j==6||
				i==6&&j==6||i==6&&j==4||i==6&&j==5
				){
			dnx=i;
			dny=j;
			if(dtoleft(dangQian, dnx, dny)){DNChange.changeleft(dangQian);}
			if(dtoright(dangQian, dnx, dny)){DNChange.changeright(dangQian);}
			if(dtoup(dangQian, dnx, dny)){DNChange.changeup(dangQian);}
			if(dtodown(dangQian, dnx, dny)){DNChange.changedown(dangQian);}
			if(dtorightup(dangQian, dnx, dny)){DNChange.changerightup(dangQian);}
			if(dtorightdown(dangQian, dnx, dny)){DNChange.changerightdown(dangQian);}
			if(dtoleftup(dangQian, dnx, dny)){DNChange.changeleftup(dangQian);}
			if(dtoleftdown(dangQian, dnx, dny)){DNChange.changeleftdown(dangQian);}
			qizi[i-1][j-1]=dangQian;
			mypanel.qizi();
			dangQian=-dangQian;
			if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
			if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}
			if(nengxiafou(dangQian)==false){shengfuqizi();x1=-1;y1=-1;dangQian=-dangQian;if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");diannao();}
				if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}}
			shengfu();
			for (int ii=1;ii<=8;ii++){
				for (int jj=1;jj<=8;jj++){
					diannaoxia[ii-1][jj-1]=0;
				}
			}

		}
	}
	public static void YX4(int i,int j){
		if (i==1&&j==3||i==1&&j==4||i==1&&j==5||i==1&&j==6||i==3&&j==1||i==4&&j==1||i==5&&j==1
				||i==6&&j==1||i==8&&j==3||i==8&&j==4||i==8&&j==5||i==8&&j==6||i==3&&j==8||i==4&&j==8||
				i==5&&j==8||i==6&&j==8){
			dnx=i;
			dny=j;
			if(dtoleft(dangQian, dnx, dny)){DNChange.changeleft(dangQian);}
			if(dtoright(dangQian, dnx, dny)){DNChange.changeright(dangQian);}
			if(dtoup(dangQian, dnx, dny)){DNChange.changeup(dangQian);}
			if(dtodown(dangQian, dnx, dny)){DNChange.changedown(dangQian);}
			if(dtorightup(dangQian, dnx, dny)){DNChange.changerightup(dangQian);}
			if(dtorightdown(dangQian, dnx, dny)){DNChange.changerightdown(dangQian);}
			if(dtoleftup(dangQian, dnx, dny)){DNChange.changeleftup(dangQian);}
			if(dtoleftdown(dangQian, dnx, dny)){DNChange.changeleftdown(dangQian);}
			qizi[i-1][j-1]=dangQian;
			mypanel.chonghua();
			dangQian=-dangQian;
			if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
			if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}
			if(nengxiafou(dangQian)==false){shengfuqizi();x1=-1;y1=-1;dangQian=-dangQian;if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");diannao();}
				if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}}
			shengfu();
			for (int ii=1;ii<=8;ii++){
				for (int jj=1;jj<=8;jj++){
					diannaoxia[ii-1][jj-1]=0;
				}
			}
		}
	}
	public static void YX5(int i,int j){
		if (i==1&&j==1||i==8&&j==1||i==1&&j==8||i==8&&j==8){
			dnx=i;
			dny=j;
			if(dtoleft(dangQian, dnx, dny)){DNChange.changeleft(dangQian);}
			if(dtoright(dangQian, dnx, dny)){DNChange.changeright(dangQian);}
			if(dtoup(dangQian, dnx, dny)){DNChange.changeup(dangQian);}
			if(dtodown(dangQian, dnx, dny)){DNChange.changedown(dangQian);}
			if(dtorightup(dangQian, dnx, dny)){DNChange.changerightup(dangQian);}
			if(dtorightdown(dangQian, dnx, dny)){DNChange.changerightdown(dangQian);}
			if(dtoleftup(dangQian, dnx, dny)){DNChange.changeleftup(dangQian);}
			if(dtoleftdown(dangQian, dnx, dny)){DNChange.changeleftdown(dangQian);}
			qizi[i-1][j-1]=dangQian;
			mypanel.chonghua();
			dangQian=-dangQian;
			if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
			if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}
			if(nengxiafou(dangQian)==false){shengfuqizi();x1=-1;y1=-1;dangQian=-dangQian;if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");diannao();}
				if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}}
			shengfu();
			for (int ii=1;ii<=8;ii++){
				for (int jj=1;jj<=8;jj++){
					diannaoxia[ii-1][jj-1]=0;
				}
			}
		}
	}


	//向左检索
	public static boolean Dtoleft(int dangqian,int i,int j){
		if (i==1||i==2){  return false;    }
		if (qizi[i-2][j-1]==dangqian){return false;}
		if (qizi[i-2][j-1]==0){return false;}
		if (qizi[i-2][j-1]==-dangqian){
			for (;i>1;i--){
				if (qizi[i-2][j-1]==-dangqian){if(i-2==1){if(qizi[i-3][j-1]==0){diannaoxia[i-3][j-1]=-1;return true;}else return false;}
				else continue;}
				if(qizi[i-2][j-1]==0){diannaoxia[i-2][j-1]=-1;return true;}
				if (qizi[i-2][j-1]==dangqian){return false;}
			}
		}
		return false;
	}
	//向右检索
	public static boolean Dtoright(int dangqian,int i,int j){
		if (i==7||i==8){  return false;    }
		if (qizi[i][j-1]==dangqian){return false;}
		if (qizi[i][j-1]==0){return false;}
		if (qizi[i][j-1]==-dangqian){
			for (;i<8;i++){
				if (qizi[i][j-1]==-dangqian){if(i+2==8){if(qizi[i+1][j-1]==0){diannaoxia[i+1][j-1]=-1;return true;}else return false;}
				else continue;}
				if(qizi[i][j-1]==0){diannaoxia[i][j-1]=-1;return true;}
				if (qizi[i][j-1]==dangqian){return false;}
			}
		}
		return false;
	}
	//向下检索
	public static  boolean Dtodown(int dangqian,int i,int j){
		if (j==7||j==8){  return false;    }
		if (qizi[i-1][j]==dangqian){return false;}
		if (qizi[i-1][j]==0){return false;}
		if (qizi[i-1][j]==-dangqian){
			for (;j<8;j++){
				if (qizi[i-1][j]==-dangqian){if(j+2==8){if(qizi[i-1][j+1]==0){diannaoxia[i-1][j+1]=-1;return true;}else return false;}
				else continue;}
				if(qizi[i-1][j]==0){diannaoxia[i-1][j]=-1;return true;}
				if (qizi[i-1][j]==dangqian){return false;}
			}
		}
		return false;
	}
	//向上检索
	public static  boolean Dtoup(int dangqian,int i,int j){
		if (j==1||j==2){  return false;    }
		if (qizi[i-1][j-2]==dangqian){return false;}
		if (qizi[i-1][j-2]==0){return false;}
		if (qizi[i-1][j-2]==-dangqian){
			for (;j>1;j--){
				if (qizi[i-1][j-2]==-dangqian){if(j-2==1){if(qizi[i-1][j-3]==0){diannaoxia[i-1][j-3]=-1;return true;}else return false;}
				else continue;}
				if(qizi[i-1][j-2]==0){diannaoxia[i-1][j-2]=-1;return true;}
				if (qizi[i-1][j-2]==dangqian){return false;}
			}
		}
		return false;
	}
	//向左下检索
	public static boolean Dtoleftdown(int dangqian,int i,int j){
		if (j==7||j==8||i==1||i==2){  return false;    }
		if (qizi[i-2][j]==dangqian){return false;}
		if (qizi[i-2][j]==0){return false;}
		if (qizi[i-2][j]==-dangqian){
			for (;j<8||i>1;j++,i--){
				if (qizi[i-2][j]==-dangqian){if(i-2==1||j+2==8){if(qizi[i-3][j+1]==0){diannaoxia[i-3][j+1]=-1;return true;}else return false;}
				else continue;}
				if(qizi[i-2][j]==0){diannaoxia[i-2][j]=-1;return true;}
				if (qizi[i-2][j]==dangqian){return false;}
			}
		}
		return false;
	}
	//向左上检索
	public static boolean Dtoleftup(int dangqian,int i,int j){
		if (j==1||j==2||i==1||i==2){  return false;    }
		if (qizi[i-2][j-2]==dangqian){return false;}
		if (qizi[i-2][j-2]==0){return false;}
		if (qizi[i-2][j-2]==-dangqian){
			for (;j>1||i>1;j--,i--){
				if (qizi[i-2][j-2]==-dangqian){if(i-2==1||j-2==1){if(qizi[i-3][j-3]==0){diannaoxia[i-3][j-3]=-1;return true;}else return false;}
				else continue;}
				if(qizi[i-2][j-2]==0){diannaoxia[i-2][j-2]=-1;return true;}
				if (qizi[i-2][j-2]==dangqian){return false;}
			}
		}
		return false;
	}
	//向右上检索
	public static boolean Dtorightup(int dangqian,int i,int j){
		if (j==1||j==2||i==7||i==8){  return false;    }
		if (qizi[i][j-2]==dangqian){return false;}
		if (qizi[i][j-2]==0){return false;}
		if (qizi[i][j-2]==-dangqian){
			for (;j>1||i<8;j--,i++){
				if (qizi[i][j-2]==-dangqian){if(i+2==8||j-2==1){if(qizi[i+1][j-3]==0){diannaoxia[i+1][j-3]=-1;return true;}else return false;}
				else continue;}
				if(qizi[i][j-2]==0){diannaoxia[i][j-2]=-1;return true;}
				if (qizi[i][j-2]==dangqian){return false;}
			}
		}
		return false;
	}
	//向右下检索
	public static boolean Dtorightdown(int dangqian,int i,int j){
		if (j==7||j==8||i==7||i==8){  return false;    }
		if (qizi[i][j]==dangqian){return false;}
		if (qizi[i][j]==0){return false;}
		if (qizi[i][j]==-dangqian){
			for (;j<8||i<8;j++,i++){
				if (qizi[i][j]==-dangqian){if(i+2==8||j+2==8){if(qizi[i+1][j+1]==0){diannaoxia[i+1][j+1]=-1;return true;}else return false;}
				else continue;}
				if(qizi[i][j]==0){diannaoxia[i][j]=-1;return true;}
				if (qizi[i][j]==dangqian){return false;}

			}
		}
		return false;
	}



	//对全局的棋子进行判断,没有空格就返回游戏结束
	public static boolean zongpanduan(){
		for (int i=1;i<=8;i++){
			for (int j=1;j<=8;j++){
				if (qizi[i-1][j-1]==0){return false;}
			}
		}

		return true;

	}
	//结束后对胜负进行判断
	public static  void shengfu(){
		int black = 0;
		int white = 0;
		if (zongpanduan()){
			for (int i=1;i<=8;i++){
				for (int j=1;j<=8;j++){
					if (qizi[i-1][j-1]==1){black++;}
					else white++;
				}
			}
			if (black>white){shengli sl = new shengli();sl.heiqiying();}
			if (black<white){shengli sl = new shengli();sl.baiqiying();}
			if (black==white){shengli sl = new shengli();sl.pingju();}
		}


	}
	//对没有棋子的判断
	public static boolean youwuqizi(int dangqian){
		int you=0;
		for (int i=1;i<=8;i++){
			for (int j=1;j<=8;j++){
				if (qizi[i-1][j-1]==dangqian){you++;}
			}
		}
		if(you>0){return true;}
		else return false;
	}
	//没有棋子的时候胜负判断
	public static void shengfuqizi(){
		if(youwuqizi(dangQian)==false){
			if(dangQian==1){shengli sl = new shengli();sl.heiqiying();}
			else {shengli sl = new shengli();sl.baiqiying();}
		}
	}
	//总检索
	public boolean check(int dangqian,int x,int y){
		return toleft(dangqian,x,y)||toright(dangqian,x,y)||todown(dangqian,x,y)||toup(dangqian,x,y)
				||toleftdown(dangqian,x,y)||toleftup(dangqian,x,y)||torightup(dangqian,x,y)||torightdown(dangqian,x,y);
	}
	//能否下的判断
	public static boolean nengxiafou(int dangqian){
		for (int i=1;i<=8;i++){
			for (int j=1;j<=8;j++){
				if(qizi[i-1][j-1]==dangqian){if (Pcheck(dangQian,i,j))
				{ return true;}
				}
			}
		}

		return false;

	}


	// 检测棋子是否可以下
	//向左检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean toleft(int dangqian,int i,int j){
		if(i==1||i==2){return false;}
		if (qizi[i-2][j-1]==0){ return false;

		}
		else {
			if(qizi[i-2][j-1]==dangqian){
				return false ;
			}
			else
			{ for (;i>1;i--){
				if (qizi[i-2][j-1]==-dangqian){
					if(i-2==1){if (qizi[i-3][j-1]==dangqian){Change.lastx1=i-2;return true;}else return false;}
					else  continue;
				}
				if(qizi[i-2][j-1]==dangqian){
					Change.lastx1 = i-1;
					return true;
				}
				if (qizi[i-2][j-1]==0){
					return false;
				}
			}
			}

		}
		return false;
	}

	//向右检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean toright(int dangqian,int i,int j){
		if (i==7||i==8){return false;}
		if (qizi[i][j-1]==0){ return false;

		}
		else {
			if(qizi[i][j-1]==dangqian){
				return false ;
			}
			else
			{ for (;i<8;i++){
				if (qizi[i][j-1]==-dangqian){
					if(i+2==8){if (qizi[i+1][j-1]==dangqian){Change.lastx2=i+2;return true;}else return false;}
					else continue;
				}
				if(qizi[i][j-1]==dangqian){
					Change.lastx2 = i+1;
					return true;
				}
				if (qizi[i][j-1]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//向下检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean todown(int dangqian,int i ,int j){
		if (j==7||j==8){return false;}
		if (qizi[i-1][j]==0){ return false;

		}
		else {
			if(qizi[i-1][j]==dangqian){
				return false ;
			}
			else
			{ for
					(;j<8;j++){//这里有bug 不能取到8
				if (qizi[i-1][j]==-dangqian){
					if (j+2==8){if (qizi[i-1][j+1]==dangqian){Change.lasty1=j+2;return true;}else return false;
					}
					else continue;
				}
				if (qizi[i-1][j]==dangqian){
					Change.lasty1=j+1;
					return true;
				}
				if (qizi[i-1][j]==0){
					return false;
				}
				//设想方法1 判断第八个为当前棋子则返回ture;
			}
			}

		}
		return false;
	}
	//向上检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean toup(int dangqian,int i ,int j){
		if(j==1||j==2){return false;}
		if (qizi[i-1][j-2]==0){ return false;

		}
		else {
			if(qizi[i-1][j-2]==dangqian){
				return false ;
			}
			else
			{ for
					(;j>1;j--){//这里有bug 不能取到8
				if (qizi[i-1][j-2]==-dangqian){
					if(j-2==1){if (qizi[i-1][j-3]==dangqian){Change.lasty2=j-2;return true;}else return false;}
					else  continue;
				}
				if(qizi[i-1][j-2]==dangqian){
					Change.lasty2=j-1;
					return true;
				}
				if (qizi[i-1][j-2]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//向左下检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean toleftdown(int dangqian,int i ,int j){
		if (j==7||j==8){return false;}
		if (i==1||i==2){return false;}
		if (qizi[i-2][j]==0){ return false;

		}
		else {
			if(qizi[i-2][j]==dangqian){
				return false ;
			}
			else
			{ for
					(;i>1||j<8;i--,j++){
				if (qizi[i-2][j]==-dangqian){
					if(i-2==1||j+2==8){if(qizi[i-3][j+1]==dangqian){Change.lastleftdownx=i-2;Change.lastleftdowny=j+2;return true;}else return false;}
					else  continue;
				}
				if(qizi[i-2][j]==dangqian){
					Change.lastleftdownx=i-1;
					Change.lastleftdowny=j+1;
					return true;
				}
				if (qizi[i-2][j]==0){
					return false;
				}
			}
			}

		}

		return false;
	}

	//向左上检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean toleftup(int dangqian,int i ,int j){
		if (i==1||i==2){return false;}
		if(j==1||j==2){return false;}
		if (qizi[i-2][j-2]==0){ return false;

		}
		else {
			if(qizi[i-2][j-2]==dangqian){
				return false ;
			}
			else
			{ for
					(;i>1||j>1;i--,j--){
				if (qizi[i-2][j-2]==-dangqian){
					if(i-2==1||j-2==1){if(qizi[i-3][j-3]==dangqian){Change.lastleftupx=i-2;Change.lastleftupy=j-2;return true;}else return false;}
					else continue;
				}
				if(qizi[i-2][j-2]==dangqian){
					Change.lastleftupx=i-1;
					Change.lastleftupy=j-1;
					return true;
				}
				if (qizi[i-2][j-2]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//向右上检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean torightup(int dangqian,int i ,int j){
		if (i==7||i==8){return false;}
		if (j==1||j==2){return false;}
		if (qizi[i][j-2]==0){ return false;

		}
		else {
			if(qizi[i][j-2]==dangqian){
				return false ;
			}
			else
			{ for
					(;i<8||j>1;i++,j--){
				if (qizi[i][j-2]==-dangqian){
					if(i+2==8||j-2==1){if(qizi[i+1][j-3]==dangqian){Change.lastrightupx=i+2;Change.lastrightupy=j-2;return true;}else return false;}
					else continue;
				}
				if(qizi[i][j-2]==dangqian){
					Change.lastrightupx=i+1;
					Change.lastrightupy=j-1;
					return true;
				}
				if (qizi[i][j-2]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//向右下检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean torightdown(int dangqian,int i ,int j){
		if(j==8||j==7){return false;}
		if(i==8||i==7){return false;}
		if (qizi[i][j]==0){ return false;

		}
		else {
			if(qizi[i][j]==dangqian){
				return false ;
			}
			else
			{ for
					(;i<8||j<8;i++,j++){
				if (qizi[i][j]==-dangqian){
					if(i+2==8||j+2==8){if (qizi[i+1][j+1]==dangqian){Change.lastrightdownx=i+2;Change.lastrightdowny=j+2;return true;}else return false;}
					else continue;
				}
				if(qizi[i][j]==dangqian){
					Change.lastrightdownx=i+1;
					Change.lastrightdowny=j+1;
					return true;
				}
				if (qizi[i][j]==0){
					return false;
				}
			}
			}

		}
		return false;
	}













	// 已有棋子进行判断
	public static boolean Pcheck(int dangqian,int i,int j){
		return Ptoleft(dangqian,i,j)||Ptoright(dangqian,i,j)||Ptoup(dangqian,i,j)
				||Ptodown(dangqian,i,j)||Ptoleftdown(dangqian,i,j)||Ptoleftup(dangqian,i,j)
				||Ptorightdown(dangqian,i,j)||Ptorightup(dangqian,i,j);
	}
	//向左检索
	public static boolean Ptoleft(int dangqian,int i,int j){
		if (i==1||i==2){  return false;    }
		if (qizi[i-2][j-1]==dangqian){return false;}
		if (qizi[i-2][j-1]==0){return false;}
		if (qizi[i-2][j-1]==-dangqian){
			for (;i>1;i--){
				if (qizi[i-2][j-1]==-dangqian){if(i-2==1){if(qizi[i-3][j-1]==0){return true;}else return false;}
				else continue;}
				if(qizi[i-2][j-1]==0){return true;}
				if (qizi[i-2][j-1]==dangqian){return false;}
			}
		}
		return false;
	}
	//向右检索
	public static boolean Ptoright(int dangqian,int i,int j){
		if (i==7||i==8){  return false;    }
		if (qizi[i][j-1]==dangqian){return false;}
		if (qizi[i][j-1]==0){return false;}
		if (qizi[i][j-1]==-dangqian){
			for (;i<8;i++){
				if (qizi[i][j-1]==-dangqian){if(i+2==8){if(qizi[i+1][j-1]==0){return true;}else return false;}
				else continue;}
				if(qizi[i][j-1]==0){return true;}
				if (qizi[i][j-1]==dangqian){return false;}
			}
		}
		return false;
	}
	//向下检索
	public static boolean Ptodown(int dangqian,int i,int j){
		if (j==7||j==8){  return false;    }
		if (qizi[i-1][j]==dangqian){return false;}
		if (qizi[i-1][j]==0){return false;}
		if (qizi[i-1][j]==-dangqian){
			for (;j<8;j++){
				if (qizi[i-1][j]==-dangqian){if(j+2==8){if(qizi[i-1][j+1]==0){return true;}else return false;}
				else continue;}
				if(qizi[i-1][j]==0){return true;}
				if (qizi[i-1][j]==dangqian){return false;}
			}
		}
		return false;
	}
	//向上检索
	public static boolean Ptoup(int dangqian,int i,int j){
		if (j==1||j==2){  return false;    }
		if (qizi[i-1][j-2]==dangqian){return false;}
		if (qizi[i-1][j-2]==0){return false;}
		if (qizi[i-1][j-2]==-dangqian){
			for (;j>1;j--){
				if (qizi[i-1][j-2]==-dangqian){if(j-2==1){if(qizi[i-1][j-3]==0){return true;}else return false;}
				else continue;}
				if(qizi[i-1][j-2]==0){return true;}
				if (qizi[i-1][j-2]==dangqian){return false;}
			}
		}
		return false;
	}
	//向左下检索
	public static boolean Ptoleftdown(int dangqian,int i,int j){
		if (j==7||j==8||i==1||i==2){  return false;    }
		if (qizi[i-2][j]==dangqian){return false;}
		if (qizi[i-2][j]==0){return false;}
		if (qizi[i-2][j]==-dangqian){
			for (;j<8||i>1;j++,i--){
				if (qizi[i-2][j]==-dangqian){if(i-2==1||j+2==8){if(qizi[i-3][j+1]==0){return true;}else return false;}
				else continue;}
				if(qizi[i-2][j]==0){return true;}
				if (qizi[i-2][j]==dangqian){return false;}
			}
		}
		return false;
	}
	//向左上检索
	public static boolean Ptoleftup(int dangqian,int i,int j){
		if (j==1||j==2||i==1||i==2){  return false;    }
		if (qizi[i-2][j-2]==dangqian){return false;}
		if (qizi[i-2][j-2]==0){return false;}
		if (qizi[i-2][j-2]==-dangqian){
			for (;j>1||i>1;j--,i--){
				if (qizi[i-2][j-2]==-dangqian){if(i-2==1||j-2==1){if(qizi[i-3][j-3]==0){return true;}else return false;}
				else continue;}
				if(qizi[i-2][j-2]==0){return true;}
				if (qizi[i-2][j-2]==dangqian){return false;}
			}
		}
		return false;
	}
	//向右上检索
	public static boolean Ptorightup(int dangqian,int i,int j){
		if (j==1||j==2||i==7||i==8){  return false;    }
		if (qizi[i][j-2]==dangqian){return false;}
		if (qizi[i][j-2]==0){return false;}
		if (qizi[i][j-2]==-dangqian){
			for (;j>1||i<8;j--,i++){
				if (qizi[i][j-2]==-dangqian){if(i+2==8||j-2==1){if(qizi[i+1][j-3]==0){return true;}else return false;}
				else continue;}
				if(qizi[i][j-2]==0){return true;}
				if (qizi[i][j-2]==dangqian){return false;}
			}
		}
		return false;
	}
	//向右下检索
	public static boolean Ptorightdown(int dangqian,int i,int j){
		if (j==7||j==8||i==7||i==8){  return false;    }
		if (qizi[i][j]==dangqian){return false;}
		if (qizi[i][j]==0){return false;}
		if (qizi[i][j]==-dangqian){
			for (;j<8||i<8;j++,i++){
				if (qizi[i][j]==-dangqian){if(i+2==8||j+2==8){if(qizi[i+1][j+1]==0){return true;}else return false;}
				else continue;}
				if(qizi[i][j]==0){return true;}
				if (qizi[i][j]==dangqian){return false;}

			}
		}
		return false;
	}



	//判断先有棋子还是有空格
	static public boolean panduan(int x){
		if (x==1) return true;
		else return false;
	}
	//变色类 (遇到空位)
	static  class Change {
		static int lastx1;
		static int lastx2;
		static int lasty1;
		static int lasty2;
		static int lastrightdownx;
		static int lastrightdowny;
		static int lastrightupx;
		static int lastrightupy;
		static int lastleftdownx;
		static int lastleftdowny;
		static int lastleftupx;
		static int lastleftupy;
		static public void changeleft(int dangqian){
			for (int i=x1;i>lastx1;i--){
				if (i==1){
					qizi[0][y1-1]=dangqian;
					break;
				}
				qizi[i-2][y1-1]=dangqian;
				mypanel.changeqizix(i-1);
			}

		}
		static public void changeright(int dangqian){
			for (int i=x1;i<lastx2;i++){
				if(i==8){
					qizi[7][y1-1]=dangqian;
					break;
				}
				qizi[i][y1-1]=dangqian;
				mypanel.changeqizix(i+1);
			}

		}
		static public void changedown(int dangqian){
			for (int j=y1;j<lasty1;j++){
				if(j==8){
					qizi[x1-1][7]=dangqian;
					break;
				}
				qizi[x1-1][j]=dangqian;
				mypanel.changeqiziy(j+1);
			}

		}
		static public void changeup(int dangqian){
			for (int j=y1;j>lasty2;j--){
				if(j==1){
					qizi[x1-1][0]=dangqian;
					break;
				}
				qizi[x1-1][j-2]=dangqian;
				mypanel.changeqiziy(j-1);
			}

		}
		static public void changeleftdown(int dangqian){
			for (int i=x1,j=y1;i>lastleftdownx;i--,j++){
				if (i==1){
					qizi[0][j]=dangqian;
					break;
				}
				if(j==8){
					qizi[i-2][7]=dangqian;
					break;
				}

				qizi[i-2][j]=dangqian;
				mypanel.changeqizixy(i-1, j+1);
			}

		}
		static public void changeleftup(int dangqian){
			for (int i=x1,j=y1;i>lastleftupx;i--,j--){
				if(i==1){
					qizi[0][j-2]=dangqian;
					break;
				}
				if(j==1){
					qizi[i-2][j]=dangqian;
					break;
				}
				qizi[i-2][j-2]=dangqian;
				mypanel.changeqizixy(i-1, j-1);
			}

		}
		static public void changerightup(int dangqian){
			for (int i=x1,j=y1;i<lastrightupx;i++,j--){
				qizi[i][j-2]=dangqian;
				mypanel.changeqizixy(i+1, j-1);
			}

		}
		static public void changerightdown(int dangqian){
			for (int i=x1,j=y1;i<lastrightdownx;i++,j++){
				qizi[i][j]=dangqian;
				mypanel.changeqizixy(i+1, j+1);
			}

		}

	}
	//判断棋子是否可下（联机）
	//向左检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean ljtoleft(int dangqian,int i,int j){
		if(i==1||i==2){return false;}
		if (qizi[i-2][j-1]==0){ return false;

		}
		else {
			if(qizi[i-2][j-1]==dangqian){
				return false ;
			}
			else
			{ for (;i>1;i--){
				if (qizi[i-2][j-1]==-dangqian){
					if(i-2==1){if (qizi[i-3][j-1]==dangqian){ljChange.ljlastx1=i-2;return true;}else return false;}
					else  continue;
				}
				if(qizi[i-2][j-1]==dangqian){
					ljChange.ljlastx1 = i-1;
					return true;
				}
				if (qizi[i-2][j-1]==0){
					return false;
				}
			}
			}

		}
		return false;
	}

	//向右检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean ljtoright(int dangqian,int i,int j){
		if (i==7||i==8){return false;}
		if (qizi[i][j-1]==0){ return false;

		}
		else {
			if(qizi[i][j-1]==dangqian){
				return false ;
			}
			else
			{ for (;i<8;i++){
				if (qizi[i][j-1]==-dangqian){
					if(i+2==8){if (qizi[i+1][j-1]==dangqian){ljChange.ljlastx2=i+2;return true;}else return false;}
					else continue;
				}
				if(qizi[i][j-1]==dangqian){
					ljChange.ljlastx2 = i+1;
					return true;
				}
				if (qizi[i][j-1]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//向下检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean ljtodown(int dangqian,int i ,int j){
		if (j==7||j==8){return false;}
		if (qizi[i-1][j]==0){ return false;

		}
		else {
			if(qizi[i-1][j]==dangqian){
				return false ;
			}
			else
			{ for
					(;j<8;j++){//这里有bug 不能取到8
				if (qizi[i-1][j]==-dangqian){
					if (j+2==8){if (qizi[i-1][j+1]==dangqian){ljChange.ljlasty1=j+2;return true;}else return false;
					}
					else continue;
				}
				if (qizi[i-1][j]==dangqian){
					ljChange.ljlasty1=j+1;
					return true;
				}
				if (qizi[i-1][j]==0){
					return false;
				}
				//设想方法1 判断第八个为当前棋子则返回ture;
			}
			}

		}
		return false;
	}
	//向上检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean ljtoup(int dangqian,int i ,int j){
		if(j==1||j==2){return false;}
		if (qizi[i-1][j-2]==0){ return false;

		}
		else {
			if(qizi[i-1][j-2]==dangqian){
				return false ;
			}
			else
			{ for
					(;j>1;j--){//这里有bug 不能取到8
				if (qizi[i-1][j-2]==-dangqian){
					if(j-2==1){if (qizi[i-1][j-3]==dangqian){ljChange.ljlasty2=j-2;return true;}else return false;}
					else  continue;
				}
				if(qizi[i-1][j-2]==dangqian){
					ljChange.ljlasty2=j-1;
					return true;
				}
				if (qizi[i-1][j-2]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//向左下检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean ljtoleftdown(int dangqian,int i ,int j){
		if (j==7||j==8){return false;}
		if (i==1||i==2){return false;}
		if (qizi[i-2][j]==0){ return false;

		}
		else {
			if(qizi[i-2][j]==dangqian){
				return false ;
			}
			else
			{ for
					(;i>1||j<8;i--,j++){
				if (qizi[i-2][j]==-dangqian){
					if(i-2==1||j+2==8){if(qizi[i-3][j+1]==dangqian){ljChange.ljlastleftdownx=i-2;ljChange.ljlastleftdowny=j+2;return true;}else return false;}
					else  continue;
				}
				if(qizi[i-2][j]==dangqian){
					ljChange.ljlastleftdownx=i-1;
					ljChange.ljlastleftdowny=j+1;
					return true;
				}
				if (qizi[i-2][j]==0){
					return false;
				}
			}
			}

		}

		return false;
	}

	//向左上检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean ljtoleftup(int dangqian,int i ,int j){
		if (i==1||i==2){return false;}
		if(j==1||j==2){return false;}
		if (qizi[i-2][j-2]==0){ return false;

		}
		else {
			if(qizi[i-2][j-2]==dangqian){
				return false ;
			}
			else
			{ for
					(;i>1||j>1;i--,j--){
				if (qizi[i-2][j-2]==-dangqian){
					if(i-2==1||j-2==1){if(qizi[i-3][j-3]==dangqian){ljChange.ljlastleftupx=i-2;ljChange.ljlastleftupy=j-2;return true;}else return false;}
					else continue;
				}
				if(qizi[i-2][j-2]==dangqian){
					ljChange.ljlastleftupx=i-1;
					ljChange.ljlastleftupy=j-1;
					return true;
				}
				if (qizi[i-2][j-2]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//向右上检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean ljtorightup(int dangqian,int i ,int j){
		if (i==7||i==8){return false;}
		if (j==1||j==2){return false;}
		if (qizi[i][j-2]==0){ return false;

		}
		else {
			if(qizi[i][j-2]==dangqian){
				return false ;
			}
			else
			{ for
					(;i<8||j>1;i++,j--){
				if (qizi[i][j-2]==-dangqian){
					if(i+2==8||j-2==1){if(qizi[i+1][j-3]==dangqian){ljChange.ljlastrightupx=i+2;ljChange.ljlastrightupy=j-2;return true;}else return false;}
					else continue;
				}
				if(qizi[i][j-2]==dangqian){
					ljChange.ljlastrightupx=i+1;
					ljChange.ljlastrightupy=j-1;
					return true;
				}
				if (qizi[i][j-2]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//向右下检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean ljtorightdown(int dangqian,int i ,int j){
		if(j==8||j==7){return false;}
		if(i==8||i==7){return false;}
		if (qizi[i][j]==0){ return false;

		}
		else {
			if(qizi[i][j]==dangqian){
				return false ;
			}
			else
			{ for
					(;i<8||j<8;i++,j++){
				if (qizi[i][j]==-dangqian){
					if(i+2==8||j+2==8){if (qizi[i+1][j+1]==dangqian){ljChange.ljlastrightdownx=i+2;ljChange.ljlastrightdowny=j+2;return true;}else return false;}
					else continue;
				}
				if(qizi[i][j]==dangqian){
					ljChange.ljlastrightdownx=i+1;
					ljChange.ljlastrightdowny=j+1;
					return true;
				}
				if (qizi[i][j]==0){
					return false;
				}
			}
			}

		}
		return false;
	}









	//变色类（联机）
	static  class ljChange {
		static int ljlastx1;
		static int ljlastx2;
		static int ljlasty1;
		static int ljlasty2;
		static int ljlastrightdownx;
		static int ljlastrightdowny;
		static int ljlastrightupx;
		static int ljlastrightupy;
		static int ljlastleftdownx;
		static int ljlastleftdowny;
		static int ljlastleftupx;
		static int ljlastleftupy;
		static public void changeleft(int dangqian){
			for (int i=ljx;i>ljlastx1;i--){
				if (i==1){
					qizi[0][ljy-1]=dangqian;
					break;
				}
				qizi[i-2][ljy-1]=dangqian;
				mypanel.changeqizix(i-1);
			}

		}
		static public void changeright(int dangqian){
			for (int i=ljx;i<ljlastx2;i++){
				if(i==8){
					qizi[7][ljy-1]=dangqian;
					break;
				}
				qizi[i][ljy-1]=dangqian;
				mypanel.changeqizix(i+1);
			}

		}
		static public void changedown(int dangqian){
			for (int j=ljy;j<ljlasty1;j++){
				if(j==8){
					qizi[ljx-1][7]=dangqian;
					break;
				}
				qizi[ljx-1][j]=dangqian;
				mypanel.changeqiziy(j+1);
			}

		}
		static public void changeup(int dangqian){
			for (int j=ljy;j>ljlasty2;j--){
				if(j==1){
					qizi[ljx-1][0]=dangqian;
					break;
				}
				qizi[ljx-1][j-2]=dangqian;
				mypanel.changeqiziy(j-1);
			}

		}
		static public void changeleftdown(int dangqian){
			for (int i=ljx,j=ljy;i>ljlastleftdownx;i--,j++){
				if (i==1){
					qizi[0][j]=dangqian;
					break;
				}
				if(j==8){
					qizi[i-2][7]=dangqian;
					break;
				}

				qizi[i-2][j]=dangqian;
				mypanel.changeqizixy(i-1, j+1);
			}

		}
		static public void changeleftup(int dangqian){
			for (int i=ljx,j=ljy;i>ljlastleftupx;i--,j--){
				if(i==1){
					qizi[0][j-2]=dangqian;
					break;
				}
				if(j==1){
					qizi[i-2][j]=dangqian;
					break;
				}
				qizi[i-2][j-2]=dangqian;
				mypanel.changeqizixy(i-1, j-1);
			}

		}
		static public void changerightup(int dangqian){
			for (int i=ljx,j=ljy;i<ljlastrightupx;i++,j--){
				qizi[i][j-2]=dangqian;
				mypanel.changeqizixy(i+1, j-1);
			}

		}
		static public void changerightdown(int dangqian){
			for (int i=ljx,j=ljy;i<ljlastrightdownx;i++,j++){
				qizi[i][j]=dangqian;
				mypanel.changeqizixy(i+1, j+1);
			}

		}

	}









	//画板
	private class MyPanel extends JPanel {
		public void paint(Graphics g) {
			super.paint(g);
			chonghua();
		}
		public void qipan(){
			Graphics g = this.getGraphics();
			for (int i =1;i<=9;i++){
				g.drawLine(100, i*100, 900, i*100);
				g.drawLine(i*100, 100,i*100, 900);
				g.setColor(Color.WHITE);
				g.fillOval(400, 400, 100, 100);
				g.fillOval(500, 500, 100, 100);
				g.drawOval(400,400, 100, 100);
				g.drawOval(500,500, 100, 100);
				g.setColor(Color.black);
				g.fillOval(400, 500, 100, 100);
				g.fillOval(500, 400, 100, 100);
				g.drawOval(400,500, 100, 100);
				g.drawOval(500,400, 100, 100);
			}
		}
		public void qizi() {
			Graphics g = this.getGraphics();
			if (dangQian == black){g.setColor(Color.BLACK);}
			else g.setColor(Color.WHITE);
			g.fillOval(x1*100, y1*100, 100, 100);
			g.drawOval(x1*100,y1*100, 100, 100);
			g.toString();
		}
		public void changeqizix(int i){
			Graphics g = this.getGraphics();
			if (dangQian == black){g.setColor(Color.BLACK);}
			else g.setColor(Color.WHITE);
			g.fillOval(i*100, y1*100, 100, 100);
			g.drawOval(i*100,y1*100, 100, 100);
		}
		public void changeqiziy(int j){
			Graphics g = this.getGraphics();
			if (dangQian == black){g.setColor(Color.BLACK);}
			else g.setColor(Color.WHITE);
			g.fillOval(x1*100, j*100, 100, 100);
			g.drawOval(x1*100,j*100, 100, 100);
		}
		public void changeqizixy(int i,int j){
			Graphics g = this.getGraphics();
			if (dangQian == black){g.setColor(Color.BLACK);}
			else g.setColor(Color.WHITE);
			g.fillOval(i*100,j*100, 100, 100);
			g.drawOval(i*100,j*100, 100, 100);
		}
		public void chonghua(){
			Graphics g = this.getGraphics();
			for (int i =1;i<=9;i++){
				g.drawLine(100, i*100, 900, i*100);
				g.drawLine(i*100, 100,i*100, 900);
				for (int h=1;h<=8;h++){
					for (int j=1;j<=8;j++){
						if (qizi[h-1][j-1]==1){
							g.setColor(Color.black);
							g.drawOval(h*100, j*100, 100, 100);
							g.fillOval(h*100,j*100, 100, 100);
						}
						if (qizi[h-1][j-1]==-1){
							g.setColor(Color.white);
							g.drawOval(h*100, j*100, 100, 100);
							g.fillOval(h*100,j*100, 100, 100);
						}
					}
				}
				g.setColor(Color.black);
			}
		}
		public void tuqu(){
			Graphics g = this.getGraphics();
			g.setColor(Color.lightGray);
			g.fillRect(100, 100, 900, 900);
		}

	}
	//客户端连接至别人电脑
	public void connect() {
		try {
			socket = new Socket("192.168.2.118",7777);
			writer = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("连接成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}







	//重新游戏
	public void cxyx(){
		for (int i=1;i<=8;i++){
			for (int j=1;j<=8;j++){
				qizi[i-1][j-1]=0;
			}
		}
		qizi[3][3]=-1;
		qizi[4][4]=-1;
		qizi[3][4]=1;
		qizi[4][3]=1;

		mypanel.tuqu();
		mypanel.chonghua();
	}











	//悔棋
	public void huiqi(int dangqian,int i,int j){
		qiziwz[i-1][j-1]=dangqian;
		sx++;
		shunxu[i-1][j-1]=sx;
		System.out.println("sx:"+sx);
		System.out.println("shun"+shunxu[i-1][j-1]);
	}
	public void hqch(){
		for (int i=1;i<=8;i++){
			for (int j=1;j<=8;j++){
				if(qizi[3][3]==-1||qizi[4][4]==-1||qizi[3][4]==1|| qizi[4][3]==1){
					continue;
				}
				else qizi[i-1][j-1]=0;
			}
		}

		mypanel.tuqu();
		mypanel.chonghua();
		hqch2();
	}
	public void hqch2(){
		for (int i=1;i<=8;i++){
			for (int j=1,dangqian=0;j<=8;j++){
				if(shunxu[i-1][j-1]==chxs){
					if(chxs==sx){qiziwz[i-1][j-1]=0;shunxu[i-1][j-1]=0;qizi[i-1][j-1]=0;sx=chxs-1;chxs=1;System.out.println("悔棋执行了");mypanel.tuqu();mypanel.chonghua();return;}
					qizi[i-1][j-1]=qiziwz[i-1][j-1];
					if(qiziwz[i-1][j-1]==1){dangqian=-1;}
					if(qiziwz[i-1][j-1]==-1){dangqian=1;}
					System.out.println("chxs:"+chxs);
					chxs++;
					System.out.println("chxs"+chxs);
					hqx=i;
					hqy=j;
					if (hqtoleft(dangqian,hqx,hqy)){hqChange.hqchangeleft(dangQian);}
					if (hqtoright(dangqian,hqx,hqy)){hqChange.hqchangeright(dangQian);System.out.println("我执行了");}
					if (hqtodown(dangqian,hqx,hqy)){hqChange.hqchangedown(dangQian);}
					if (hqtoup(dangqian,hqx,hqy)){hqChange.hqchangeup(dangQian);}
					if (hqtoleftdown(dangqian,hqx,hqy)){hqChange.hqchangeleftdown(dangQian);}
					if (hqtoleftup(dangqian,hqx,hqy)){hqChange.hqchangeleftup(dangQian);}
					if (hqtorightup(dangqian,hqx,hqy)){hqChange.hqchangerightup(dangQian);}
					if (hqtorightdown(dangqian,hqx,hqy)){hqChange.hqchangerightdown(dangQian);}
					if(dangQian==1){xiaqifang.setText("当前下棋方为黑方");}
					if(dangQian==-1){xiaqifang.setText("当前下棋方为白方");}
					mypanel.tuqu();
					mypanel.chonghua();
					if(chxs<sx){hqch2();}



				}
			}
		}
	}





	public boolean hqtoleft(int dangqian,int i,int j){
		if(i==1||i==2){return false;}
		if (qizi[i-2][j-1]==0){ return false;

		}
		else {
			if(qizi[i-2][j-1]==dangqian){
				return false ;
			}
			else
			{ for (;i>1;i--){
				if (qizi[i-2][j-1]==-dangqian){
					if(i-2==1){if (qizi[i-3][j-1]==dangqian){hqChange.hqlastx1=i-2;return true;}else return false;}
					else  continue;
				}
				if(qizi[i-2][j-1]==dangqian){
					hqChange.hqlastx1 = i-1;
					return true;
				}
				if (qizi[i-2][j-1]==0){
					return false;
				}
			}
			}

		}
		return false;
	}

	//向右检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean hqtoright(int dangqian,int i,int j){
		if (i==7||i==8){return false;}
		if (qizi[i][j-1]==0){ return false;

		}
		else {
			if(qizi[i][j-1]==dangqian){
				return false ;
			}
			else
			{ for (;i<8;i++){
				if (qizi[i][j-1]==-dangqian){
					if(i+2==8){if (qizi[i+1][j-1]==dangqian){hqChange.hqlastx2=i+2;return true;}else return false;}
					else continue;
				}
				if(qizi[i][j-1]==dangqian){
					hqChange.hqlastx2 = i+1;
					return true;
				}
				if (qizi[i][j-1]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//向下检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean hqtodown(int dangqian,int i ,int j){
		if (j==7||j==8){return false;}
		if (qizi[i-1][j]==0){ return false;

		}
		else {
			if(qizi[i-1][j]==dangqian){
				return false ;
			}
			else
			{ for
					(;j<8;j++){//这里有bug 不能取到8
				if (qizi[i-1][j]==-dangqian){
					if (j+2==8){if (qizi[i-1][j+1]==dangqian){hqChange.hqlasty1=j+2;return true;}else return false;
					}
					else continue;
				}
				if (qizi[i-1][j]==dangqian){
					hqChange.hqlasty1=j+1;
					return true;
				}
				if (qizi[i-1][j]==0){
					return false;
				}
				//设想方法1 判断第八个为当前棋子则返回ture;
			}
			}

		}
		return false;
	}
	//向上检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean hqtoup(int dangqian,int i ,int j){
		if(j==1||j==2){return false;}
		if (qizi[i-1][j-2]==0){ return false;

		}
		else {
			if(qizi[i-1][j-2]==dangqian){
				return false ;
			}
			else
			{ for
					(;j>1;j--){//这里有bug 不能取到8
				if (qizi[i-1][j-2]==-dangqian){
					if(j-2==1){if (qizi[i-1][j-3]==dangqian){hqChange.hqlasty2=j-2;return true;}else return false;}
					else  continue;
				}
				if(qizi[i-1][j-2]==dangqian){
					hqChange.hqlasty2=j-1;
					return true;
				}
				if (qizi[i-1][j-2]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//向左下检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean hqtoleftdown(int dangqian,int i ,int j){
		if (j==7||j==8){return false;}
		if (i==1||i==2){return false;}
		if (qizi[i-2][j]==0){ return false;

		}
		else {
			if(qizi[i-2][j]==dangqian){
				return false ;
			}
			else
			{ for
					(;i>1||j<8;i--,j++){
				if (qizi[i-2][j]==-dangqian){
					if(i-2==1||j+2==8){if(qizi[i-3][j+1]==dangqian){hqChange.hqlastleftdownx=i-2;hqChange.hqlastleftdowny=j+2;return true;}else return false;}
					else  continue;
				}
				if(qizi[i-2][j]==dangqian){
					hqChange.hqlastleftdownx=i-1;
					hqChange.hqlastleftdowny=j+1;
					return true;
				}
				if (qizi[i-2][j]==0){
					return false;
				}
			}
			}

		}

		return false;
	}

	//向左上检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean hqtoleftup(int dangqian,int i ,int j){
		if (i==1||i==2){return false;}
		if(j==1||j==2){return false;}
		if (qizi[i-2][j-2]==0){ return false;

		}
		else {
			if(qizi[i-2][j-2]==dangqian){
				return false ;
			}
			else
			{ for
					(;i>1||j>1;i--,j--){
				if (qizi[i-2][j-2]==-dangqian){
					if(i-2==1||j-2==1){if(qizi[i-3][j-3]==dangqian){hqChange.hqlastleftupx=i-2;hqChange.hqlastleftupy=j-2;return true;}else return false;}
					else continue;
				}
				if(qizi[i-2][j-2]==dangqian){
					hqChange.hqlastleftupx=i-1;
					hqChange.hqlastleftupy=j-1;
					return true;
				}
				if (qizi[i-2][j-2]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//向右上检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean hqtorightup(int dangqian,int i ,int j){
		if (i==7||i==8){return false;}
		if (j==1||j==2){return false;}
		if (qizi[i][j-2]==0){ return false;

		}
		else {
			if(qizi[i][j-2]==dangqian){
				return false ;
			}
			else
			{ for
					(;i<8||j>1;i++,j--){
				if (qizi[i][j-2]==-dangqian){
					if(i+2==8||j-2==1){if(qizi[i+1][j-3]==dangqian){hqChange.hqlastrightupx=i+2;hqChange.hqlastrightupy=j-2;return true;}else return false;}
					else continue;
				}
				if(qizi[i][j-2]==dangqian){
					hqChange.hqlastrightupx=i+1;
					hqChange.hqlastrightupy=j-1;
					return true;
				}
				if (qizi[i][j-2]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//向右下检索qizi[x1-1][y1-1]是当前所下的棋子
	public boolean hqtorightdown(int dangqian,int i ,int j){
		if(j==8||j==7){return false;}
		if(i==8||i==7){return false;}
		if (qizi[i][j]==0){ return false;

		}
		else {
			if(qizi[i][j]==dangqian){
				return false ;
			}
			else
			{ for
					(;i<8||j<8;i++,j++){
				if (qizi[i][j]==-dangqian){
					if(i+2==8||j+2==8){if (qizi[i+1][j+1]==dangqian){hqChange.hqlastrightdownx=i+2;hqChange.hqlastrightdowny=j+2;return true;}else return false;}
					else continue;
				}
				if(qizi[i][j]==dangqian){
					hqChange.hqlastrightdownx=i+1;
					hqChange.hqlastrightdowny=j+1;
					return true;
				}
				if (qizi[i][j]==0){
					return false;
				}
			}
			}

		}
		return false;
	}
	//变色类 (悔棋重画用)
	static  class hqChange {
		static int hqlastx1;
		static int hqlastx2;
		static int hqlasty1;
		static int hqlasty2;
		static int hqlastrightdownx;
		static int hqlastrightdowny;
		static int hqlastrightupx;
		static int hqlastrightupy;
		static int hqlastleftdownx;
		static int hqlastleftdowny;
		static int hqlastleftupx;
		static int hqlastleftupy;
		static public void hqchangeleft(int dangqian){
			for (int i=hqx;i>hqlastx1;i--){
				if (i==1){
					qizi[0][hqy-1]=dangqian;
					break;
				}
				qizi[i-2][hqy-1]=dangqian;
				mypanel.changeqizix(i-1);
			}

		}
		static public void hqchangeright(int dangqian){
			for (int i=hqx;i<hqlastx2;i++){
				if(i==8){
					qizi[7][hqy-1]=dangqian;
					break;
				}
				qizi[i][hqy-1]=dangqian;
				mypanel.changeqizix(i+1);
			}

		}
		static public void hqchangedown(int dangqian){
			for (int j=hqy;j<hqlasty1;j++){
				if(j==8){
					qizi[hqx-1][7]=dangqian;
					break;
				}
				qizi[hqx-1][j]=dangqian;
				mypanel.changeqiziy(j+1);
			}

		}
		static public void hqchangeup(int dangqian){
			for (int j=hqy;j>hqlasty2;j--){
				if(j==1){
					qizi[hqx-1][0]=dangqian;
					break;
				}
				qizi[hqx-1][j-2]=dangqian;
				mypanel.changeqiziy(j-1);
			}

		}
		static public void hqchangeleftdown(int dangqian){
			for (int i=hqx,j=hqy;i>hqlastleftdownx;i--,j++){
				if (i==1){
					qizi[0][j]=dangqian;
					break;
				}
				if(j==8){
					qizi[i-2][7]=dangqian;
					break;
				}

				qizi[i-2][j]=dangqian;
				mypanel.changeqizixy(i-1, j+1);
			}

		}
		static public void hqchangeleftup(int dangqian){
			for (int i=hqx,j=hqy;i>hqlastleftupx;i--,j--){
				if(i==1){
					qizi[0][j-2]=dangqian;
					break;
				}
				if(j==1){
					qizi[i-2][j]=dangqian;
					break;
				}
				qizi[i-2][j-2]=dangqian;
				mypanel.changeqizixy(i-1, j-1);
			}

		}
		static public void hqchangerightup(int dangqian){
			for (int i=hqx,j=hqy;i<hqlastrightupx;i++,j--){
				qizi[i][j-2]=dangqian;
				mypanel.changeqizixy(i+1, j-1);
			}

		}
		static public void hqchangerightdown(int dangqian){
			for (int i=hqx,j=hqy;i<hqlastrightdownx;i++,j++){
				qizi[i][j]=dangqian;
				mypanel.changeqizixy(i+1, j+1);
			}

		}

	}

	//联机时输入ip地址的框框
	class Ip extends JFrame implements ActionListener{
		public JButton queding;
		public void wenben(){
			final JTextField  ip = new JTextField();
			queding = new JButton("连接");
			queding.addActionListener(this);
			this.add(ip,BorderLayout.NORTH);
			this.add(queding,BorderLayout.SOUTH);
			ip.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					iip=ip.getText();
					ip.setText("");

				}
			});
		}
		public Ip(){
			super("请输入对方的ip地址");
			setLocationRelativeTo(null);
			setVisible(true);
			setSize(200, 200);
			setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			wenben();

		}
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==queding){
				connect();
				xuanze xz=new xuanze();
				this.setVisible(false);
			}
		}
	}
	//服务器
	void getserver() {
		try {
			server = new ServerSocket(8888);
			System.out.println("服务器套接字已经创建成功"); // 输出信息
			while (true) {
				System.out.println("等待客户机的连接"); // 输出信息
				soocket = server.accept();
				reader = new BufferedReader(new InputStreamReader(soocket
						.getInputStream()));
				getClientMessage(); // 调用getClientMessage()方法
			}
		} catch (Exception e) {
			e.printStackTrace(); // 输出异常信息
		}
	}

	private void getClientMessage() {
		try {
			while (true) { // 如果套接字是连接状态
				if (reader.ready()) {
					// 获得客户端信息
					String message = reader.readLine();
					char X = message.charAt(0);
					char Y = message.charAt(2);
					String xx=String.valueOf(X);
					String yy=String.valueOf(Y);
					int x =Integer.valueOf(xx);
					int y =Integer.valueOf(yy);
					System.out.println(x+","+y);
					Draw.ljx=x;
					Draw.ljy=y;
					Draw.DF=1;
					if(DF==1)this.DFxiaqi();
					mypanel.chonghua();
					System.out.println(DF);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); // 输出异常信息
		}
		try {
			if (reader != null) {
				reader.close(); // 关闭流
			}
			if (soocket != null) {
				soocket.close(); // 关闭套接字
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}

//玩家选择哪种棋
class xuanze extends JFrame implements ActionListener{
	JButton hq;
	JButton bq;
	xuanze(){
		super("选择棋子的颜色");
		setLocationRelativeTo(null);
		setVisible(true);
		setSize(200, 200);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		hq=new JButton("黑棋");
		this.add(hq,BorderLayout.SOUTH);
		hq.addActionListener(this);
		bq=new JButton("白棋");
		this.add(bq,BorderLayout.NORTH);
		bq.addActionListener(this);


	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==hq){
			Draw.WJ=1;
			Draw.WJ2=-1;
			Draw.DF=0;
			this.setVisible(false);
		}
		if(e.getSource()==bq){
			Draw.WJ =-1;
			Draw.WJ2=1;
			this.setVisible(false);
		}

	}
}
//玩家选择哪种棋(人机)
class xuanzerj extends JFrame implements ActionListener{
	JButton hq;
	JButton bq;
	xuanzerj(){
		super("选择棋子的颜色");
		setLocationRelativeTo(null);
		setVisible(true);
		setSize(200, 200);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		hq=new JButton("黑棋");
		this.add(hq,BorderLayout.SOUTH);
		hq.addActionListener(this);
		bq=new JButton("白棋");
		this.add(bq,BorderLayout.NORTH);
		bq.addActionListener(this);


	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==hq){
			Draw.WJ=1;

			this.setVisible(false);
		}
		if(e.getSource()==bq){
			Draw.WJ =-1;
			Draw.diannao();

			this.setVisible(false);
		}

	}
}


//胜利面板
class shengli extends JFrame{
	JTextArea jt;
	shengli(){
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("胜负已定");
		this.setSize(200, 200);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		jt = new JTextArea();
		this.add(jt);

	}
	public void baiqiying(){
		jt.append("恭喜白棋获得胜利");
	}
	public void heiqiying(){
		jt.append("恭喜黑棋获得胜利");

	}
	public void pingju(){
		jt.append("这是一场平局");
	}
}

