package kiosk.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * Singleton pattern.
 */
public class TicketSelectGUI {
//	private static TicketSelectGUI ticketSelectGUI = new TicketSelectGUI();
	private double divide = 1;
	private JFrame frmTicketSelect;
	private JLabel timeDisplayLabel;
	private JPanel tablePanel;
	private String movieName;
	private String screenName;
	private String startTime;
	private int screenWidth;
	private int screenHeight;
	private SitConfirmGUI sitConfirmGUI;
	private MovieArrange selectSitMovieArrage = new MovieArrange();
	private final JLabel postLabel = new JLabel();

	public JLabel getTimeDisplayLabel() {
		return timeDisplayLabel;
	}

	public JFrame getFrmTicketSelect() {
		return frmTicketSelect;
	}

	public SitConfirmGUI getSitConfirmGUI() {
		return sitConfirmGUI;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initializeGUI() {
		frmTicketSelect = new JFrame();
		frmTicketSelect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTicketSelect.setTitle("Self-service Ticketing Kiosk");
		Image postImage;
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		screenWidth = (int)(screenSize.width/divide);
		screenHeight = (int)(screenSize.height/divide);
		frmTicketSelect.setBounds(0, 0, screenWidth, screenHeight);
		frmTicketSelect.setUndecorated(true);
		
		Image bgImage = new ImageIcon("D:/ticket-kiosk-1-7/img/background.jpg").getImage();
		bgImage = bgImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_DEFAULT);
		
		Image leftImage = new ImageIcon("D:/ticket-kiosk-1-7/img/left.png").getImage();
		leftImage = leftImage.getScaledInstance((int)(screenWidth/20), (int)(screenWidth/20), Image.SCALE_DEFAULT);
		
		Image rightImage = new ImageIcon("D:/ticket-kiosk-1-7/img/right.png").getImage();
		rightImage = rightImage.getScaledInstance((int)(screenWidth/20), (int)(screenWidth/20), Image.SCALE_DEFAULT);
		
		frmTicketSelect.getContentPane().setLayout(null);
		
		JButton leftBTN = new JButton();
		leftBTN.setContentAreaFilled(false);	//make button transparent.
		leftBTN.setBorderPainted(false);		//make button has no frame after click it.
		leftBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(SitInfo sitInfo : selectSitMovieArrage.getScreenInfo().getAllSitsArrayList()){
					if(sitInfo.getSitState() == 2){
						sitInfo.setSitState(0);
					}
				}
				WelcomeGUI.getWelcomeGUI().getTimeTableGUI().getFrmTimeTable().setVisible(true);
				frmTicketSelect.dispose();
			}
		});
		
		tablePanel = new JPanel();
		tablePanel.setBackground(new Color(147, 112, 219));
//		tablePanel.setBounds(0, (int)(screenWidth/20 + 50), screenWidth, (int)(screenHeight-(screenWidth/20 + 50)));
		tablePanel.setBounds(0, (int)(screenWidth/20 + 50), screenWidth, (int)(screenHeight-(screenWidth/20 + 50)));
		frmTicketSelect.getContentPane().add(tablePanel);
		tablePanel.setOpaque(false);
		tablePanel.setLayout(null);
		
		JLabel screenLabel = new JLabel("Screen", SwingConstants.CENTER);
		screenLabel.setBackground(Color.GRAY);
		screenLabel.setOpaque(true);
		screenLabel.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 20));
		screenLabel.setForeground(Color.WHITE);
		screenLabel.setBounds((int)(screenWidth/2),(int)(screenHeight-(screenWidth/20 + 120)),(int)(screenWidth/3),40);
		tablePanel.add(screenLabel);
		
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
		
		for(MovieArrange movieArrangeList : InfoReader.getInfoReader().getMovieArrangeArrayList()){
			String timeBuf = new DecimalFormat("00").format(movieArrangeList.getTimeHour())+":"+ new DecimalFormat("00").format(movieArrangeList.getTimeMinute());
//			System.out.println(timeBuf + startTime);
			if(movieArrangeList.getMovieInfo().getMovieName().equals(movieName) && movieArrangeList.getScreenInfo().getScreenName().equals(screenName) && startTime.equals(timeBuf)){

//				System.out.println("hello");
				this.selectSitMovieArrage = movieArrangeList;
				for(SitInfo sitInfoList : movieArrangeList.getScreenInfo().getAllSitsArrayList()){
					createSit(sitInfoList);
				}
				for(int row=0 ; row < movieArrangeList.getScreenInfo().getRow(); row++){
					char buf = (char)(65+movieArrangeList.getScreenInfo().getRow()-row-1);
					JLabel rowCodeLabel = new JLabel(String.valueOf(buf));
					rowCodeLabel.setLayout(null);
					rowCodeLabel.setOpaque(false);
					rowCodeLabel.setBounds((int)(screenWidth/2.742), (int)(screenWidth/12.8 + row*screenWidth/16), (int)(screenWidth/19.2), (int)(screenWidth/19.2));
					rowCodeLabel.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 25));
					rowCodeLabel.setForeground(Color.WHITE);
					tablePanel.add(rowCodeLabel);
				}
				break ;
			}
		}
		
		leftBTN.setIcon(new ImageIcon(leftImage));
//		leftBTN.setBounds(10, (int)(screenHeight-screenWidth/20+10), (int)(screenWidth/20), (int)(screenWidth/20));
		leftBTN.setBounds(20, (int)(20), (int)(screenWidth/20), (int)(screenWidth/20));
		//need to be deleted lately.
//		leftBTN.setIcon(new ImageIcon(TicketSelectGUI.class.getResource("/img/config.png")));
		frmTicketSelect.getContentPane().add(leftBTN);
		
		JButton rightBTN = new JButton();
		rightBTN.setContentAreaFilled(false);	//make button transparent.
		rightBTN.setBorderPainted(false);		//make button has no frame after click it.
		rightBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(SitInfo sitInfoList : selectSitMovieArrage.getScreenInfo().getAllSitsArrayList()){
					if(sitInfoList.getSitState() == 2){
						sitConfirmGUI = new SitConfirmGUI();
						sitConfirmGUI.startSitConfirmGUI(sitConfirmGUI, selectSitMovieArrage);
						frmTicketSelect.setVisible(false);
						return ;
					}
				}
				JOptionPane.showMessageDialog(null,"You did not select any ticket.","Information",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		rightBTN.setIcon(new ImageIcon(rightImage));
//		rightBTN.setBounds((int)(screenWidth-screenWidth/20-10), (int)(screenHeight-screenWidth/20-10), (int)(screenWidth/20), (int)(screenWidth/20));
		rightBTN.setBounds((int)(screenWidth-screenWidth/20-10), (int)(screenHeight-screenWidth/20-10), (int)(screenWidth/20), (int)(screenWidth/20));
		//need to be deleted lately.
//		rightBTN.setIcon(new ImageIcon(WelcomeGUI.class.getResource("/img/switch.png")));
		frmTicketSelect.getContentPane().add(rightBTN);
		
		JLabel TitleLabel = new JLabel("Select sits: " + startTime + " starts.");
		TitleLabel.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, (int)(screenWidth/30)));
		TitleLabel.setForeground(Color.WHITE);
//		TitleLabel.setBounds((int)(50 + screenWidth/20), (int)(screenWidth/55), (int)(screenWidth/2), (int)(screenWidth/25));
		TitleLabel.setBounds((int)(50 + screenWidth/20), (int)(screenWidth/55), (int)(screenWidth/2), (int)(screenWidth/25));
		frmTicketSelect.getContentPane().add(TitleLabel);
		
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
		frmTicketSelect.getContentPane().add(timeDisplayLabel);
		
		JLabel bgImageLabel = new JLabel();
		
//		bgImageLabel.setBounds(0, 0, screenWidth, screenHeight); 
		bgImageLabel.setBounds(0, 0, screenWidth, screenHeight);
		bgImageLabel.setIcon(new ImageIcon(bgImage));
		//need to be deleted.
//		bgImageLabel.setIcon(new ImageIcon(TicketSelectGUI.class.getResource("/img/background3.jpg")));
		frmTicketSelect.getContentPane().add(bgImageLabel);
	}
	
	public void createSit(final SitInfo sitInfo){
		int row = sitInfo.getRow();
		int column = sitInfo.getColumn();
		String sitCode = sitInfo.getCode() + "";
		int sitState = sitInfo.getSitState();
		JPanel sitPanel = new JPanel();
		final JLabel sitImgLabel = new JLabel();
		sitPanel.setLayout(null);
		sitPanel.setOpaque(false);
		sitPanel.setBounds((int)(screenWidth/2.313+(column-1)*screenWidth/19.2), (int)(screenWidth/12.8 + (row-1)*screenWidth/16), (int)(screenWidth/19.2), (int)(screenWidth/19.2));
		tablePanel.add(sitPanel);
		sitPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
//				System.out.println("clicked!");
				if(sitInfo.getSitState() == 0){
					sitInfo.setSitState(2);
					Image p = new ImageIcon("D:/ticket-kiosk-1-7/img/sitSelecting.png").getImage();
					p = p.getScaledInstance((int)(screenWidth/19.2), (int)(screenWidth/19.2), Image.SCALE_DEFAULT);
					sitImgLabel.setIcon(new ImageIcon(p));
				}
				else if(sitInfo.getSitState() == 2){
					sitInfo.setSitState(0);
					Image p = new ImageIcon("D:/ticket-kiosk-1-7/img/sitFree.png").getImage();
					p = p.getScaledInstance((int)(screenWidth/19.2), (int)(screenWidth/19.2), Image.SCALE_DEFAULT);
					sitImgLabel.setIcon(new ImageIcon(p));
				}
			}
		});
		JLabel sitNumLabel = new JLabel(sitCode);
		sitNumLabel.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, screenWidth/96));
		sitNumLabel.setOpaque(false);
		sitNumLabel.setVerticalAlignment(SwingConstants.TOP);
		sitNumLabel.setHorizontalAlignment(SwingConstants.LEADING);
		sitNumLabel.setBounds((int)(screenWidth/42.6667), (int)(screenWidth/38.4), (int)(screenWidth/96), (int)(screenWidth/48));
		sitPanel.add(sitNumLabel);
		
		sitImgLabel.setOpaque(false);
		sitImgLabel.setBounds(0,0,(int)(screenWidth/19.2), (int)(screenWidth/19.2));
		if(sitState == 0){
			Image p = new ImageIcon("D:/ticket-kiosk-1-7/img/sitFree.png").getImage();
			p = p.getScaledInstance((int)(screenWidth/19.2), (int)(screenWidth/19.2), Image.SCALE_DEFAULT);
			sitImgLabel.setIcon(new ImageIcon(p));
		}
		else if(sitState == 1){
			Image p = new ImageIcon("D:/ticket-kiosk-1-7/img/sitOccupied.png").getImage();
			p = p.getScaledInstance((int)(screenWidth/19.2), (int)(screenWidth/19.2), Image.SCALE_DEFAULT);
			sitImgLabel.setIcon(new ImageIcon(p));
		}
		else if(sitState == 2){
			Image p = new ImageIcon("D:/ticket-kiosk-1-7/img/sitSelecting.png").getImage();
			p = p.getScaledInstance((int)(screenWidth/19.2), (int)(screenWidth/19.2), Image.SCALE_DEFAULT);
			sitImgLabel.setIcon(new ImageIcon(p));
		}
		sitPanel.add(sitImgLabel);
	}
	
	public void startTicketSelectGUI(String movieName, String screenName, String startTime){
		try {
			this.movieName = movieName;
			this.screenName = screenName;
			this.startTime = startTime;
//			System.out.println("ticket select ! : " +movieName + " " + screenName + " " + startTime);
			WelcomeGUI.getWelcomeGUI().getTicketSelectGUI().initializeGUI();
			WelcomeGUI.getWelcomeGUI().getTicketSelectGUI().getFrmTicketSelect().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
