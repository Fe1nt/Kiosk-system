package kiosk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.TableColumn;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kiosk.controller.Bank;
import kiosk.controller.CreateTicket;
import kiosk.controller.InfoReader;
import kiosk.controller.TimeClock;
import kiosk.model.MovieArrange;
import kiosk.model.MovieInfo;
import kiosk.model.SitInfo;


public class SitConfirmGUI {
//	private SitConfirmGUI SitConfirmGUI = new SitConfirmGUI();
	private double divide = 1;
	private JFrame frmSitConfirm;
	private JLabel timeDisplayLabel;
	private JPanel tablePanel;
	private int screenWidth;
	private int screenHeight;
	private MovieArrange selectSitMovieArrage;
	private int selectSitNum = 0;
	private final JLabel postLabel = new JLabel();
	private JTable sitConfirmTable;

	public JLabel getTimeDisplayLabel() {
		return timeDisplayLabel;
	}

	public JFrame getFrmSitConfirm() {
		return frmSitConfirm;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initializeGUI() {
		frmSitConfirm = new JFrame();
		frmSitConfirm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSitConfirm.setTitle("Self-service Ticketing Kiosk");
		
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		screenWidth = (int)(screenSize.width/divide);
		screenHeight = (int)(screenSize.height/divide);
		frmSitConfirm.setBounds(0, 0, screenWidth, screenHeight);
		frmSitConfirm.setUndecorated(true);
		
		Image bgImage = new ImageIcon("D:/ticket-kiosk-1-7/img/background.jpg").getImage();
		bgImage = bgImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_DEFAULT);
		
		Image leftImage = new ImageIcon("D:/ticket-kiosk-1-7/img/left.png").getImage();
		leftImage = leftImage.getScaledInstance((int)(screenWidth/20), (int)(screenWidth/20), Image.SCALE_DEFAULT);
		
		Image rightImage = new ImageIcon("D:/ticket-kiosk-1-7/img/right.png").getImage();
		rightImage = rightImage.getScaledInstance((int)(screenWidth/20), (int)(screenWidth/20), Image.SCALE_DEFAULT);
		
		frmSitConfirm.getContentPane().setLayout(null);
		
		JButton leftBTN = new JButton();
		leftBTN.setContentAreaFilled(false);	//make button transparent.
		leftBTN.setBorderPainted(false);		//make button has no frame after click it.
		leftBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				WelcomeGUI.getWelcomeGUI().getTicketSelectGUI().getFrmTicketSelect().dispose();
				WelcomeGUI.getWelcomeGUI().getTicketSelectGUI().getFrmTicketSelect().setVisible(true);
				frmSitConfirm.dispose();
//				WelcomeGUI.getWelcomeGUI().getTicketSelectGUI().startTicketSelectGUI(selectSitMovieArrage.getMovieInfo().getMovieName(), selectSitMovieArrage.getScreenInfo().getScreenName(), new DecimalFormat("00").format(selectSitMovieArrage.getTimeHour())+":"+ new DecimalFormat("00").format(selectSitMovieArrage.getTimeMinute()));
			}
		});
		
		showSitConfirmTable();
		
		leftBTN.setIcon(new ImageIcon(leftImage));
//		leftBTN.setBounds(10, (int)(screenHeight-screenWidth/20+10), (int)(screenWidth/20), (int)(screenWidth/20));
		leftBTN.setBounds(20, (int)(20), (int)(screenWidth/20), (int)(screenWidth/20));
		//need to be deleted lately.
//		leftBTN.setIcon(new ImageIcon(SitConfirmGUI.class.getResource("/img/config.png")));
		frmSitConfirm.getContentPane().add(leftBTN);
		
		JButton rightBTN = new JButton();
		rightBTN.setContentAreaFilled(false);	//make button transparent.
		rightBTN.setBorderPainted(false);		//make button has no frame after click it.
		rightBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double totalMoney = 0;
				for(int i=0; i<selectSitNum; i++){
					String type = (String) sitConfirmTable.getValueAt(i, 5);
					if(type.equals("Child")){
						totalMoney += 8;
					}
					else if(type.equals("Adult")){
						totalMoney += 16;
					}
					else if(type.equals("Senior")){
						totalMoney += 12.8;
					}
					else if(type.equals("Student")){
						if(((String) sitConfirmTable.getValueAt(i, 6))== null || ((String) sitConfirmTable.getValueAt(i, 6)).equals("")){
							JOptionPane.showMessageDialog(null,"Please input student ID!","Error!",JOptionPane.ERROR_MESSAGE);
//							frmSitConfirm.dispose();
							totalMoney = 0;
							return ;
						}
						totalMoney += 13.6;
					}
				}
				
				Object[] options = {"Yes", "No"};
				String buf = "You will buy " + selectSitNum + " ticket(s).\nThe totalMoney is " + totalMoney + ".\nPlease confirm the order.";
				int response = JOptionPane.showOptionDialog(null, buf, "Information", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				if(response == 0){	//yes
					Bank bank = new Bank();	//Payment interface
					if(bank.paymentSate()){	//validation of payment interface (return true)
						
						CreateTicket createTicket = new CreateTicket(selectSitMovieArrage, sitConfirmTable, selectSitNum, totalMoney);
						WelcomeGUI.getWelcomeGUI().getFrmWelcome().setVisible(true);
						WelcomeGUI.getWelcomeGUI().getTicketSelectGUI().getFrmTicketSelect().dispose();
						WelcomeGUI.getWelcomeGUI().getTimeTableGUI().getFrmTimeTable().dispose();
						WelcomeGUI.getWelcomeGUI().getFilmSelectGUI().getFrmFilmSelect().dispose();
						frmSitConfirm.dispose();
						
					}
				}
			}
		});
		
		rightBTN.setIcon(new ImageIcon(rightImage));
//		rightBTN.setBounds((int)(screenWidth-screenWidth/20-10), (int)(screenHeight-screenWidth/20-10), (int)(screenWidth/20), (int)(screenWidth/20));
		rightBTN.setBounds((int)(screenWidth-screenWidth/20-10), (int)(screenHeight-screenWidth/20-10), (int)(screenWidth/20), (int)(screenWidth/20));
		//need to be deleted lately.
//		rightBTN.setIcon(new ImageIcon(WelcomeGUI.class.getResource("/img/switch.png")));
		frmSitConfirm.getContentPane().add(rightBTN);
		frmSitConfirm.getContentPane().add(tablePanel);
		JLabel TitleLabel = new JLabel("Sit Confirm:");
		TitleLabel.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, (int)(screenWidth/30)));
		TitleLabel.setForeground(Color.WHITE);
//		TitleLabel.setBounds((int)(50 + screenWidth/20), (int)(screenWidth/55), (int)(screenWidth/2), (int)(screenWidth/25));
		TitleLabel.setBounds((int)(50 + screenWidth/20), (int)(screenWidth/55), (int)(screenWidth/2), (int)(screenWidth/25));
		frmSitConfirm.getContentPane().add(TitleLabel);
		
		timeDisplayLabel = new JLabel();
		timeDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeDisplayLabel.setForeground(Color.WHITE);
//		timeDisplayLabel.setFont(new Font("Myanmar Text", Font.PLAIN, 39));
//		timeDisplayLabel.setFont(new Font("Myanmar Text", Font.PLAIN, (int)(screenWidth/49.231)));
		timeDisplayLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, (int)(screenWidth/49.231)));
		SimpleDateFormat todayDateFormat = new SimpleDateFormat("yyyy-MM-dd  ");
		
		timeDisplayLabel.setText(todayDateFormat.format(new Date()) +  new DecimalFormat("00").format(TimeClock.getTimeClock().getHour()) + " : " +  new DecimalFormat("00").format(TimeClock.getTimeClock().getMinute()));
//		timeDisplayLabel.setBounds(1490, 40, 400, 68);
//		timeDisplayLabel.setBounds((int)(screenWidth/1.2886), (int)(screenWidth/48), (int)(screenWidth/4.8), (int)(screenWidth/28.235));
		timeDisplayLabel.setBounds((int)(screenWidth/1.2886), (int)(screenWidth/48), (int)(screenWidth/4.8), (int)(screenWidth/28.235));
		frmSitConfirm.getContentPane().add(timeDisplayLabel);
		
		JLabel bgImageLabel = new JLabel();
		
//		bgImageLabel.setBounds(0, 0, screenWidth, screenHeight); 
		bgImageLabel.setBounds(0, 0, screenWidth, screenHeight);
		bgImageLabel.setIcon(new ImageIcon(bgImage));
		//need to be deleted.
//		bgImageLabel.setIcon(new ImageIcon(SitConfirmGUI.class.getResource("/img/background3.jpg")));
		frmSitConfirm.getContentPane().add(bgImageLabel);
	}
	
	public void showSitConfirmTable(){
		tablePanel = new JPanel();
		tablePanel.setBackground(new Color(147, 112, 219));
//		tablePanel.setBounds(0, (int)(screenWidth/20 + 50), screenWidth, (int)(screenHeight-(screenWidth/20 + 50)));
		tablePanel.setBounds(0, (int)(screenWidth/20 + 50), screenWidth, (int)(screenHeight-(screenWidth/20 + 50)));//+screenWidth/20+10
		
		tablePanel.setOpaque(false);
		tablePanel.setLayout(null);
		
		Image postImage = new ImageIcon(selectSitMovieArrage.getMovieInfo().getMoviePoster().split(".png")[0] + " big.png").getImage();
		postImage = postImage.getScaledInstance((int)(screenWidth/4), (int)(screenWidth/3), Image.SCALE_DEFAULT);
		postLabel.setIcon(new ImageIcon(postImage));

		postLabel.setBounds((int)(screenWidth/38.4), 0, (int)(screenWidth/3.2), (int)(screenWidth/2.7586));
		tablePanel.add(postLabel);
		
		DefaultTableModel model=new DefaultTableModel();
		
		
		sitConfirmTable = new JTable(model);
		sitConfirmTable.setEnabled(true);
		sitConfirmTable.setForeground(Color.WHITE);
		sitConfirmTable.setFont(new Font("Microsoft JhengHei", Font.PLAIN, (int)(screenWidth/81)));
		sitConfirmTable.setBackground(Color.WHITE);
		sitConfirmTable.setSelectionForeground(Color.LIGHT_GRAY);
		sitConfirmTable.setOpaque(false);
//		sitConfirmTable.setShowVerticalLines(false);
		

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		renderer.setOpaque(false);
		
		model.addColumn("MOVIE");
		model.addColumn("START TIME");
		model.addColumn("SCREEN");
		model.addColumn("ROW");
		model.addColumn("COLUMN");
		model.addColumn("TYPE");
		model.addColumn("ID");
		sitConfirmTable.getColumn("MOVIE").setCellRenderer(renderer);
		sitConfirmTable.getColumn("START TIME").setCellRenderer(renderer);
		sitConfirmTable.getColumn("SCREEN").setCellRenderer(renderer);
		sitConfirmTable.getColumn("ROW").setCellRenderer(renderer);
		sitConfirmTable.getColumn("COLUMN").setCellRenderer(renderer);
		sitConfirmTable.getColumn("TYPE").setCellRenderer(renderer);
		sitConfirmTable.getColumn("ID").setCellRenderer(renderer);
		sitConfirmTable.getTableHeader().setBackground(new Color(147, 112, 219));
		sitConfirmTable.getTableHeader().setFont(new Font("Microsoft JhengHei", Font.BOLD, (int)(screenWidth/81)));
		sitConfirmTable.getTableHeader().setForeground(Color.WHITE);
		sitConfirmTable.setRowHeight((int)(screenWidth/64*4));
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Child");
		comboBox.addItem("Adult");
		comboBox.addItem("Senior");
		comboBox.addItem("Student");
		comboBox.setFont(new Font("Microsoft JhengHei", Font.PLAIN, (int)(screenWidth/81)));
		
		DefaultCellEditor cellEditor = new DefaultCellEditor(comboBox);
		TableColumn comboboxColumn = sitConfirmTable.getColumn("TYPE");
		comboboxColumn.setCellEditor(cellEditor);

		String[] bufArr = new String[6];
		for(SitInfo sitInfoList : selectSitMovieArrage.getScreenInfo().getAllSitsArrayList()){
			if(sitInfoList.getSitState() == 2){
				bufArr[0] = selectSitMovieArrage.getMovieInfo().getMovieName();
				bufArr[1] = new DecimalFormat("00").format(selectSitMovieArrage.getTimeHour())+":"+new DecimalFormat("00").format(selectSitMovieArrage.getTimeMinute());
				bufArr[2] = selectSitMovieArrage.getScreenInfo().getScreenName();
				char buf = (char)(65+selectSitMovieArrage.getScreenInfo().getRow()-sitInfoList.getRow());
				bufArr[3] = String.valueOf(buf);
				bufArr[4] = sitInfoList.getCode()+"";
				bufArr[5] = "Adult";
				selectSitNum++;
				model.addRow(bufArr);
			}
		}
		
		JScrollPane scrollPane = new JScrollPane(sitConfirmTable);
//		scrollPane.setBounds((int)(screenWidth/3.2), 0, (int)(screenWidth-screenWidth/3.2), (int)(screenHeight-(screenWidth/20 + 50)));
		scrollPane.setBounds((int)(screenWidth/3.2), 0, (int)(screenWidth-screenWidth/3.2), (int)(screenHeight-(screenWidth/20 + 50)));
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		tablePanel.add(scrollPane);
	}
	
	public void startSitConfirmGUI(SitConfirmGUI sitConfirmGUI, MovieArrange movieArrange){
		try {
			this.selectSitMovieArrage = movieArrange;
			sitConfirmGUI.initializeGUI();
			sitConfirmGUI.getFrmSitConfirm().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
