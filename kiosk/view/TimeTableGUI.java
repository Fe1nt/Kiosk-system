package kiosk.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


import kiosk.controller.InfoReader;
import kiosk.controller.TimeClock;
import kiosk.model.MovieArrange;
import kiosk.model.MovieInfo;
import kiosk.model.SitInfo;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TimeTableGUI {
//	private static TimeTableGUI timeTableGUI = new TimeTableGUI();
	private double divide = 1;
	private JFrame frmTimeTable;
	private JLabel timeDisplayLabel;
	private String movieName;
	private final JLabel postLabel = new JLabel();
	private int screenWidth;
	private int screenHeight;

	public JLabel getTimeDisplayLabel() {
		return timeDisplayLabel;
	}

	public JFrame getFrmTimeTable() {
		return frmTimeTable;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initializeGUI() {
		frmTimeTable = new JFrame();
		frmTimeTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTimeTable.setTitle("Self-service Ticketing Kiosk");
		
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		screenWidth = (int)(screenSize.width/divide);
		screenHeight = (int)(screenSize.height/divide);
		frmTimeTable.setBounds(0, 0, screenWidth, screenHeight);
		frmTimeTable.setUndecorated(true);
		
		Image bgImage = new ImageIcon("D:/ticket-kiosk-1-7/img/background.jpg").getImage();
		bgImage = bgImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_DEFAULT);
		
		Image leftImage = new ImageIcon("D:/ticket-kiosk-1-7/img/left.png").getImage();
		leftImage = leftImage.getScaledInstance((int)(screenWidth/20), (int)(screenWidth/20), Image.SCALE_DEFAULT);
		
		frmTimeTable.getContentPane().setLayout(null);
		
		JButton leftBTN = new JButton();
		leftBTN.setContentAreaFilled(false);	//make button transparent.
		leftBTN.setBorderPainted(false);		//make button has no frame after click it.
		leftBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WelcomeGUI.getWelcomeGUI().getFilmSelectGUI().getFrmFilmSelect().setVisible(true);
				frmTimeTable.dispose();
			}
		});
		
		showFilmTimeTable();
		
		
		
		leftBTN.setIcon(new ImageIcon(leftImage));
//		leftBTN.setBounds(10, (int)(screenHeight-screenWidth/20+10), (int)(screenWidth/20), (int)(screenWidth/20));
		leftBTN.setBounds(20, (int)(20), (int)(screenWidth/20), (int)(screenWidth/20));
		//need to be deleted lately.
//		leftBTN.setIcon(new ImageIcon(TimeTableGUI.class.getResource("/img/config.png")));
		frmTimeTable.getContentPane().add(leftBTN);
		
		JLabel TitleLabel = new JLabel(movieName);
		TitleLabel.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, (int)(screenWidth/30)));
		TitleLabel.setForeground(Color.WHITE);
//		TitleLabel.setBounds((int)(50 + screenWidth/20), (int)(screenWidth/55), (int)(screenWidth/2), (int)(screenWidth/25));
		TitleLabel.setBounds((int)(50 + screenWidth/20), (int)(screenWidth/55), (int)(screenWidth/2), (int)(screenWidth/25));
		frmTimeTable.getContentPane().add(TitleLabel);
		
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
		frmTimeTable.getContentPane().add(timeDisplayLabel);
		
		JLabel bgImageLabel = new JLabel();
		
//		bgImageLabel.setBounds(0, 0, screenWidth, screenHeight); 
		bgImageLabel.setBounds(0, 0, screenWidth, screenHeight);
		bgImageLabel.setIcon(new ImageIcon(bgImage));
		//need to be deleted.
//		bgImageLabel.setIcon(new ImageIcon(TimeTableGUI.class.getResource("/img/background3.jpg")));
		frmTimeTable.getContentPane().add(bgImageLabel);
	}
	
	public void showFilmTimeTable(){
		JPanel tablePanel = new JPanel();
		Image postImage;
		tablePanel.setBackground(new Color(147, 112, 219));
//		tablePanel.setBounds(0, (int)(screenWidth/20 + 50), screenWidth, (int)(screenHeight-(screenWidth/20 + 50)));
		tablePanel.setBounds(0, (int)(screenWidth/20 + 50), screenWidth, (int)(screenHeight-(screenWidth/20 + 50)));
		frmTimeTable.getContentPane().add(tablePanel);
		tablePanel.setOpaque(false);
		tablePanel.setLayout(null);
		
		
		DefaultTableModel model=new DefaultTableModel(){ 
			public boolean isCellEditable(int row, int column) {
				//rewrite isCellEditable method to make sure that the cell can't be edited. 
				return false; 
			} 
		}; 
		
		
		final JTable timetbTable = new JTable(model);
		timetbTable.setEnabled(true);
		timetbTable.setForeground(Color.WHITE);
		timetbTable.setFont(new Font("Microsoft JhengHei", Font.PLAIN, (int)(screenWidth/64)));
		timetbTable.setBackground(Color.WHITE);
		timetbTable.setOpaque(false);
//		timetbTable.setShowVerticalLines(false);
		JScrollPane scrollPane = new JScrollPane(timetbTable);
//		scrollPane.setBounds((int)(screenWidth/3.2), 0, (int)(screenWidth-screenWidth/3.2), (int)(screenHeight-(screenWidth/20 + 50)));
		scrollPane.setBounds((int)(screenWidth/3.2), 0, (int)(screenWidth-screenWidth/3.2), (int)(screenHeight-(screenWidth/20 + 50)));
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		
		for(MovieInfo movieInfoList : InfoReader.getInfoReader().getMovieInfoArrayList()){
			if(movieInfoList.getMovieName().equals(movieName)){
				postImage = new ImageIcon(movieInfoList.getMoviePoster().split(".png")[0] + " big.png").getImage();
				postImage = postImage.getScaledInstance((int)(screenWidth/4), (int)(screenWidth/3), Image.SCALE_DEFAULT);
				postLabel.setIcon(new ImageIcon(postImage));
				break ;
			}
		}
		
		
		postLabel.setBounds((int)(screenWidth/38.4), 0, (int)(screenWidth/3.2), (int)(screenWidth/2.7586));
		tablePanel.add(postLabel);
		tablePanel.add(scrollPane);
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		renderer.setOpaque(false);
		
		DefaultTableModel tableModel = (DefaultTableModel) timetbTable.getModel();
//		tableModel.addColumn("NAME");
		tableModel.addColumn("SCREEN NO.");
		tableModel.addColumn("START TIME");
		tableModel.addColumn("DURATION");
		
		String[] bufArr = new String[3];
		for(MovieArrange movieArrangeList : InfoReader.getInfoReader().getMovieArrangeArrayList()){
			if(((movieArrangeList.getTimeHour() > TimeClock.getTimeClock().getHour()) || (movieArrangeList.getTimeHour() == TimeClock.getTimeClock().getHour() && movieArrangeList.getTimeMinute() > TimeClock.getTimeClock().getMinute())) && movieArrangeList.getMovieInfo().getMovieName().equals(movieName)){
//				System.out.println(movieArrangeList.getMovieInfo().getMovieName());
//				bufArr[0] = movieArrangeList.getMovieInfo().getMovieName();
				bufArr[0] = movieArrangeList.getScreenInfo().getScreenName();
				bufArr[1] = new DecimalFormat("00").format(movieArrangeList.getTimeHour()) + ":" + new DecimalFormat("00").format(movieArrangeList.getTimeMinute());
				bufArr[2] = movieArrangeList.getMovieInfo().getMovieDuration() + " min";
				tableModel.addRow(bufArr);
			}
		}
		
//		timetbTable.getColumn("NAME").setCellRenderer(renderer);
		timetbTable.getColumn("SCREEN NO.").setCellRenderer(renderer);
		timetbTable.getColumn("START TIME").setCellRenderer(renderer);
		timetbTable.getColumn("DURATION").setCellRenderer(renderer);
		timetbTable.getTableHeader().setBackground(new Color(147, 112, 219));
		timetbTable.getTableHeader().setFont(new Font("Microsoft JhengHei", Font.BOLD, (int)(screenWidth/64)));
		timetbTable.getTableHeader().setForeground(Color.WHITE);
		timetbTable.setRowHeight((int)(screenWidth/64*4));
		timetbTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				for(MovieArrange movieArrangeList : InfoReader.getInfoReader().getMovieArrangeArrayList()){
					String timeBuf = new DecimalFormat("00").format(movieArrangeList.getTimeHour())+":"+ new DecimalFormat("00").format(movieArrangeList.getTimeMinute());
					if(movieArrangeList.getMovieInfo().getMovieName().equals(movieName) && movieArrangeList.getScreenInfo().getScreenName().equals((String)timetbTable.getValueAt(timetbTable.getSelectedRow(), 0)) && timeBuf.equals((String)timetbTable.getValueAt(timetbTable.getSelectedRow(), 1))){
						boolean ifSoldOut = true;
						for(SitInfo sitInfoList : movieArrangeList.getScreenInfo().getAllSitsArrayList()){
							if(sitInfoList.getSitState() == 0){
								ifSoldOut = false;
								WelcomeGUI.getWelcomeGUI().getTicketSelectGUI().startTicketSelectGUI(movieName, (String)timetbTable.getValueAt(timetbTable.getSelectedRow(), 0), (String)timetbTable.getValueAt(timetbTable.getSelectedRow(), 1));
								frmTimeTable.setVisible(false);
								break;
							}
						}
						
						if(ifSoldOut){
							JOptionPane.showMessageDialog(null,"The show is sold out!","Information!",JOptionPane.INFORMATION_MESSAGE);
						}
						break ;
					}
				}
//				if(timetbTable.getValueAt(timetbTable.getSelectedRow(), 0) != null){
//					System.out.println((String)timetbTable.getValueAt(timetbTable.getSelectedRow(), 0));
					
//					System.out.println("timetablegui" + movieName);
//					System.out.println((String)timetbTable.getValueAt(timetbTable.getSelectedRow(), 0));
//					System.out.println((String)timetbTable.getValueAt(timetbTable.getSelectedRow(), 1));
//				}
//				System.out.println(timetbTable.getSelectedRow());
			}
		});
	}
	public void startTimeTableGUI(String movieName){
		try {
			this.movieName = movieName;
//			System.out.println(this.movieName);
			WelcomeGUI.getWelcomeGUI().getTimeTableGUI().initializeGUI();
			WelcomeGUI.getWelcomeGUI().getTimeTableGUI().getFrmTimeTable().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
