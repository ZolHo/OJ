import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class hbq3 extends JFrame {

	private JPanel contentPane;
	public JButton[][] button= new JButton[8][8];         //创建64个按钮对象 保存在button[]数组中
	public int[][] button_num={{10,0,8,7,7,8,0,10},
			{0,0,0,0,0,0,0,0},
			{8,0,9,9,9,9,0,8},
			{7,0,9,-2,-1,9,0,7},
			{7,0,9,-1,-2,9,0,7},
			{8,0,9,9,9,9,0,8},
			{0,0,0,0,0,0,0,0},
			{10,0,8,7,7,8,0,10}};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					hbq3 frame = new hbq3();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void change(){
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{
				if(button_num[i][j]==-1){
					button[i][j].setBackground(Color.BLACK);
				}else if(button_num[i][j]==-2){
					button[i][j].setBackground(Color.WHITE);
				}
			}
	}

	public void click(){
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				final int x = i,y = j;
				button[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// TODO 自动生成的方法存根
						if(button_num[x][y]>=0){

							int win = 0;

							for(int i=x-1;i>=0;i--){
								if(button_num[i][y]==-1){
									for(int q=i+1;q<x;q++){
										if(button_num[q][y]!=-2){
											break;
										}else if(q==x-1){
											for(int p=i+1;p<=x;p++){
												button[p][y].setBackground(Color.BLACK);
												button_num[p][y]=-1;
												win++;
											}
										}
									}
								}
							}

							for(int i=x+1;i<=7;i++){
								if(button_num[i][y]==-1){
									for(int q=i-1;q>x;q--){
										if(button_num[q][y]!=-2){
											break;
										}else if(q==x+1){
											for(int p=i-1;p>=x;p--){
												button[p][y].setBackground(Color.BLACK);
												button_num[p][y]=-1;
												win++;
											}
										}
									}
								}
							}

							for(int i=y+1;i<=7;i++){
								if(button_num[x][i]==-1){
									for(int q=i-1;q>y;q--){
										if(button_num[x][q]!=-2){
											break;
										}else if(q==y+1){
											for(int p=i-1;p>=y;p--){
												button[x][p].setBackground(Color.BLACK);
												button_num[x][p]=-1;
												win++;
											}
										}
									}
								}
							}

							for(int i=y-1;i>=0;i--){
								if(button_num[x][i]==-1){
									for(int q=i+1;q<y;q++){
										if(button_num[x][q]!=-2){
											break;
										}else if(q==y-1){
											for(int p=i+1;p<=y;p++){
												button[x][p].setBackground(Color.BLACK);
												button_num[x][p]=-1;
												win++;
											}
										}
									}
								}
							}

							for(int i=x-1,j=y+1;i>=0&&j<=7;i--,j++){
								if(button_num[i][j]==-1){
									for(int q=i+1,q2=j-1;q<x;q++,q2--){
										if(button_num[q][q2]!=-2){
											break;
										}else if(q==x-1){
											for(int p=i+1,p2=j-1;p<=x;p++,p2--){
												button[p][p2].setBackground(Color.BLACK);
												button_num[p][p2]=-1;
												win++;
											}
										}
									}
								}
							}

							for(int i=x-1,j=y-1;i>=0&&j>=0;i--,j--){
								if(button_num[i][j]==-1){
									for(int q=i+1,q2=j+1;q<x;q++,q2++){
										if(button_num[q][q2]!=-2){
											break;
										}else if(q==x-1){
											for(int p=i+1,p2=j+1;p<=x;p++,p2++){
												button[p][p2].setBackground(Color.BLACK);
												button_num[p][p2]=-1;
												win++;
											}
										}
									}
								}
							}

							for(int i=x+1,j=y-1;i<=7&&j>=0;i++,j--){
								if(button_num[i][j]==-1){
									for(int q=i-1,q2=j+1;q>x;q--,q2++){
										if(button_num[q][q2]!=-2){
											break;
										}else if(q==x+1){
											for(int p=i-1,p2=j+1;p>=x;p--,p2++){
												button[p][p2].setBackground(Color.BLACK);
												button_num[p][p2]=-1;
												win++;
											}
										}
									}
								}
							}

							for(int i=x+1,j=y+1;i<=7&&j<=7;i++,j++){
								if(button_num[i][j]==-1){
									for(int q=i-1,q2=j-1;q>x;q--,q2--){
										if(button_num[q][q2]!=-2){
											break;
										}else if(q==x+1){
											for(int p=i-1,p2=j-1;p>=x;p--,p2--){
												button[p][p2].setBackground(Color.BLACK);
												button_num[p][p2]=-1;
												win++;
											}
										}
									}
								}
							}
							isfull();
							if(win>0){
								renji();
								isfull();
							}
						}
					}
				});
			}
	}

	public void renji(){
		int  gx=3,gy=3;
		for(int x=0;x<8;x++)
			for(int y=0;y<8;y++)
			{
				int win = 0;
				if(button_num[x][y]>=0)
				{
					for(int i=x-1;i>=0;i--){
						if(button_num[i][y]==-2){
							for(int q=i+1;q<x;q++){
								if(button_num[q][y]!=-1){
									break;
								}else if(q==x-1){
									win++;
								}
							}
						}
					}

					for(int i=x+1;i<=7;i++){
						if(button_num[i][y]==-2){
							for(int q=i-1;q>x;q--){
								if(button_num[q][y]!=-1){
									break;
								}else if(q==x+1){
									win++;
								}
							}
						}
					}

					for(int i=y+1;i<=7;i++){
						if(button_num[x][i]==-2){
							for(int q=i-1;q>y;q--){
								if(button_num[x][q]!=-1){
									break;
								}else if(q==y+1){
									win++;
								}
							}
						}
					}

					for(int i=y-1;i>=0;i--){
						if(button_num[x][i]==-2){
							for(int q=i+1;q<y;q++){
								if(button_num[x][q]!=-1){
									break;
								}else if(q==y-1){
									win++;
								}
							}
						}
					}

					for(int i=x-1,j=y+1;i>=0&&j<=7;i--,j++){
						if(button_num[i][j]==-2){
							for(int q=i+1,q2=j-1;q<x;q++,q2--){
								if(button_num[q][q2]!=-1){
									break;
								}else if(q==x-1){
									win++;
								}
							}
						}
					}

					for(int i=x-1,j=y-1;i>=0&&j>=0;i--,j--){
						if(button_num[i][j]==-2){
							for(int q=i+1,q2=j+1;q<x;q++,q2++){
								if(button_num[q][q2]!=-1){
									break;
								}else if(q==x-1){
									win++;
								}
							}
						}
					}

					for(int i=x+1,j=y-1;i<=7&&j>=0;i++,j--){
						if(button_num[i][j]==-2){
							for(int q=i-1,q2=j+1;q>x;q--,q2++){
								if(button_num[q][q2]!=-1){
									break;
								}else if(q==x+1){
									win++;
								}
							}
						}
					}

					for(int i=x+1,j=y+1;i<=7&&j<=7;i++,j++){
						if(button_num[i][j]==-2){
							for(int q=i-1,q2=j-1;q>x;q--,q2--){
								if(button_num[q][q2]!=-1){
									break;
								}else if(q==x+1){
									win++;
								}
							}
						}
					}
					if(win>0&&button_num[gx][gy]<button_num[x][y]){
						gx = x;
						gy = y;
					}
				}
			}
		if(button_num[gx][gy]>=0){
			renji_FZ(gx,gy);
			System.out.println(gx+" "+gy);
		}else{
			System.out.println("白方赢！");
		}

	}

	public void renji_FZ(int x,int y){
		for(int i=x-1;i>=0;i--){
			if(button_num[i][y]==-2){
				for(int q=i+1;q<x;q++){
					if(button_num[q][y]!=-1){
						break;
					}else if(q==x-1){
						for(int p=i+1;p<=x;p++){
							button[p][y].setBackground(Color.WHITE);
							button_num[p][y]=-2;
						}
					}
				}
			}
		}

		for(int i=x+1;i<=7;i++){
			if(button_num[i][y]==-2){
				for(int q=i-1;q>x;q--){
					if(button_num[q][y]!=-1){
						break;
					}else if(q==x+1){
						for(int p=i-1;p>=x;p--){
							button[p][y].setBackground(Color.WHITE);
							button_num[p][y]=-2;
						}
					}
				}
			}
		}

		for(int i=y+1;i<=7;i++){
			if(button_num[x][i]==-2){
				for(int q=i-1;q>y;q--){
					if(button_num[x][q]!=-1){
						break;
					}else if(q==y+1){
						for(int p=i-1;p>=y;p--){
							button[x][p].setBackground(Color.WHITE);
							button_num[x][p]=-2;
						}
					}
				}
			}
		}

		for(int i=y-1;i>=0;i--){
			if(button_num[x][i]==-2){
				for(int q=i+1;q<y;q++){
					if(button_num[x][q]!=-1){
						break;
					}else if(q==y-1){
						for(int p=i+1;p<=y;p++){
							button[x][p].setBackground(Color.WHITE);
							button_num[x][p]=-2;
						}
					}
				}
			}
		}

		for(int i=x-1,j=y+1;i>=0&&j<=7;i--,j++){
			if(button_num[i][j]==-2){
				for(int q=i+1,q2=j-1;q<x;q++,q2--){
					if(button_num[q][q2]!=-1){
						break;
					}else if(q==x-1){
						for(int p=i+1,p2=j-1;p<=x;p++,p2--){
							button[p][p2].setBackground(Color.WHITE);
							button_num[p][p2]=-2;
						}
					}
				}
			}
		}

		for(int i=x-1,j=y-1;i>=0&&j>=0;i--,j--){
			if(button_num[i][j]==-2){
				for(int q=i+1,q2=j+1;q<x;q++,q2++){
					if(button_num[q][q2]!=-1){
						break;
					}else if(q==x-1){
						for(int p=i+1,p2=j+1;p<=x;p++,p2++){
							button[p][p2].setBackground(Color.WHITE);
							button_num[p][p2]=-2;
						}
					}
				}
			}
		}

		for(int i=x+1,j=y-1;i<=7&&j>=0;i++,j--){
			if(button_num[i][j]==-2){
				for(int q=i-1,q2=j+1;q>x;q--,q2++){
					if(button_num[q][q2]!=-1){
						break;
					}else if(q==x+1){
						for(int p=i-1,p2=j+1;p>=x;p--,p2++){
							button[p][p2].setBackground(Color.WHITE);
							button_num[p][p2]=-2;
						}
					}
				}
			}
		}

		for(int i=x+1,j=y+1;i<=7&&j<=7;i++,j++){
			if(button_num[i][j]==-2){
				for(int q=i-1,q2=j-1;q>x;q--,q2--){
					if(button_num[q][q2]!=-1){
						break;
					}else if(q==x+1){
						for(int p=i-1,p2=j-1;p>=x;p--,p2--){
							button[p][p2].setBackground(Color.WHITE);
							button_num[p][p2]=-2;
						}
					}
				}
			}
		}
		can();
	}
	/**
	 * Create the frame.
	 */
	public hbq3(){
		super();
		setSize(400,400);                                //设置布局大小
		setLocationRelativeTo(null);                     //使程序位于屏幕中间
		getContentPane().setLayout(new GridLayout(8,8));                  //设置一个8X8的网格布局
		setDefaultCloseOperation(EXIT_ON_CLOSE);         //点击关闭按钮退出程序

		//设置头部菜单
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("\u83DC\u5355");
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("\u4E3B\u9875");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				main a = new main();
				setVisible(false);
				a.setVisible(true);
			}
		});

		JMenuItem menuItem_1 = new JMenuItem("\u7B80\u5355");
		menu.add(menuItem_1);
		menuItem_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				hbq1 a= new hbq1();
				setVisible(false);
				a.setVisible(true);
			}
		});

		JMenuItem menuItem_2 = new JMenuItem("\u4E2D\u7B49");
		menu.add(menuItem_2);
		menuItem_2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				hbq2 a= new hbq2();
				setVisible(false);
				a.setVisible(true);
			}
		});
		JMenuItem menuItem_3 = new JMenuItem("\u56F0\u96BE");
		menu.add(menuItem_3);
		menuItem_3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				hbq3 a= new hbq3();
				setVisible(false);
				a.setVisible(true);
			}
		});

		for(int i=0;i<8;i++)                 //遍历数组button[]
			for(int j=0;j<8;j++)
			{
				button[i][j] = new JButton();                   //为每一个button数组中的元素创建一个按钮
				if((i+j)%2==0){                                  //button所在位置的（列数  +行数）为偶数时，该button的颜色为浅灰色
					button[i][j].setBackground(Color.LIGHT_GRAY);
					getContentPane().add(button[i][j]);
				}
				else{
					button[i][j].setBackground(Color.gray);    //button所在位置的（列数  +行数）为奇数时，该button的颜色为灰色
					getContentPane().add(button[i][j]);
				}
			}
		change();
		click();

	}

	public void can(){
		int win = 0;
		for(int x=0;x<8;x++)
			for(int y=0;y<8;y++){
				for(int i=x-1;i>=0;i--){
					if(button_num[i][y]==-1){
						for(int q=i+1;q<x;q++){
							if(button_num[q][y]!=-2){
								break;
							}else if(q==x-1){
								win++;
							}
						}
					}
				}

				for(int i=x+1;i<=7;i++){
					if(button_num[i][y]==-1){
						for(int q=i-1;q>x;q--){
							if(button_num[q][y]!=-2){
								break;
							}else if(q==x+1){
								win++;
							}
						}
					}
				}

				for(int i=y+1;i<=7;i++){
					if(button_num[x][i]==-1){
						for(int q=i-1;q>y;q--){
							if(button_num[x][q]!=-2){
								break;
							}else if(q==y+1){
								win++;
							}
						}
					}
				}

				for(int i=y-1;i>=0;i--){
					if(button_num[x][i]==-1){
						for(int q=i+1;q<y;q++){
							if(button_num[x][q]!=-2){
								break;
							}else if(q==y-1){
								win++;
							}
						}
					}
				}

				for(int i=x-1,j=y+1;i>=0&&j<=7;i--,j++){
					if(button_num[i][j]==-1){
						for(int q=i+1,q2=j-1;q<x;q++,q2--){
							if(button_num[q][q2]!=-2){
								break;
							}else if(q==x-1){
								win++;
							}
						}
					}
				}

				for(int i=x-1,j=y-1;i>=0&&j>=0;i--,j--){
					if(button_num[i][j]==-1){
						for(int q=i+1,q2=j+1;q<x;q++,q2++){
							if(button_num[q][q2]!=-2){
								break;
							}else if(q==x-1){
								win++;
							}
						}
					}
				}

				for(int i=x+1,j=y-1;i<=7&&j>=0;i++,j--){
					if(button_num[i][j]==-1){
						for(int q=i-1,q2=j+1;q>x;q--,q2++){
							if(button_num[q][q2]!=-2){
								break;
							}else if(q==x+1){
								win++;
							}
						}
					}
				}

				for(int i=x+1,j=y+1;i<=7&&j<=7;i++,j++){
					if(button_num[i][j]==-1){
						for(int q=i-1,q2=j-1;q>x;q--,q2--){
							if(button_num[q][q2]!=-2){
								break;
							}else if(q==x+1){
								win++;
							}
						}
					}
				}
			}
		if(win == 0){
			renji();
		}
	}


	public void isfull(){
		int black=0,white=0;
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{
				if(button_num[i][j]==-1){
					black++;
				}else if(button_num[i][j]==-2){
					white++;
				}
			}
		if(black+white==64){
			if(black>white){
				black_win a =new black_win();
				a.setVisible(true);
			}else if(white>black){
				white_win a =new white_win();
				a.setVisible(true);
			}else if(white==black){
				win a =new win();
				a.setVisible(true);
			}
		}
	}
}