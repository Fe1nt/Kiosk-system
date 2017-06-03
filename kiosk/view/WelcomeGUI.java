package kiosk.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import kiosk.controller.CreateReport;
import kiosk.controller.InfoReader;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * Singleton pattern.
 */
public class WelcomeGUI {
	private static WelcomeGUI welcomeGUI = new WelcomeGUI();
	private double divide = 1;
	private JFrame frmWelcome;
	private JLabel timeDisplayLabel;
	private FilmSelectGUI filmSelectGUI;
	private TimeTableGUI timeTableGUI;
	private TicketSelectGUI ticketSelectGUI;
	private String password = "1";
//	private SitConfirmGUI sitConfirmGUI;
	
	public static WelcomeGUI getWelcomeGUI() {
		return welcomeGUI;
	}

	public JLabel getTimeDisplayLabel() {
		return timeDisplayLabel;
	}

	public JFrame getFrmWelcome() {
		return frmWelcome;
	}

	public FilmSelectGUI getFilmSelectGUI() {
		return filmSelectGUI;
	}

	public TimeTableGUI getTimeTableGUI() {
		return timeTableGUI;
	}

	public TicketSelectGUI getTicketSelectGUI() {
		return ticketSelectGUI;
	}

//	public SitConfirmGUI getSitConfirmGUI() {
//		return sitConfirmGUI;
//	}

	//Make sure that the class can't be instanced.
	private WelcomeGUI() {
		//need to be deleted later.
//		initializeGUI();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initializeGUI() {
		frmWelcome = new JFrame();
		frmWelcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWelcome.setTitle("Self-service Ticketing Kiosk");
		InfoReader.getInfoReader().movieArrangeReader();
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = (int)(screenSize.width/divide);
		int screenHeight = (int)(screenSize.height/divide);
		frmWelcome.setBounds(0, 0, screenWidth, screenHeight);
		frmWelcome.setUndecorated(true);
		
		Image bgImage = new ImageIcon("D:/ticket-kiosk-1-7/img/background3.jpg").getImage();
		bgImage = bgImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_DEFAULT);
		
		Image switchImage = new ImageIcon("D:/ticket-kiosk-1-7/img/switch.png").getImage();
		switchImage = switchImage.getScaledInstance((int)(screenWidth/20), (int)(screenWidth/20), Image.SCALE_DEFAULT);
		
		JButton comeinBTN = new JButton();
		comeinBTN.setContentAreaFilled(false);	//make button transparent.
		comeinBTN.setBorderPainted(false);		//make button has no frame after click it.
		comeinBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filmSelectGUI = new FilmSelectGUI();
				filmSelectGUI.startFilmSelectGUI();
				timeTableGUI = new TimeTableGUI();
				ticketSelectGUI = new TicketSelectGUI();
				frmWelcome.setVisible(false);
//				sitConfirmGUI = new SitConfirmGUI();
			}
		});
		frmWelcome.getContentPane().setLayout(null);
//		comeinBTN.setBounds((int)(screenWidth/4.3146), (int)(screenHeight/2), (int)(screenWidth/1.85866), (int)(screenHeight/3.95604));
		comeinBTN.setBounds((int)(screenWidth/4.3146), (int)(screenHeight/2), (int)(screenWidth/1.85866), (int)(screenHeight/3.95604));
		frmWelcome.getContentPane().add(comeinBTN);
		
		JButton switchBTN = new JButton();
		switchBTN.setContentAreaFilled(false);	//make button transparent.
		switchBTN.setBorderPainted(false);		//make button has no frame after click it.
		switchBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//close the software.
				String inputBuf = JOptionPane.showInputDialog("Please input the management password:");
				if(inputBuf == null || inputBuf.equals("")){}
				else{
					if(inputBuf.equals(password)){
						CreateReport createReport = new CreateReport();
						JOptionPane.showMessageDialog(null, "An email has been sent!");
						System.exit(0);
					}
					else{
						JOptionPane.showMessageDialog(null, "Wrong password!");
					}
				}
			}
		});
		
		switchBTN.setIcon(new ImageIcon(switchImage));
//		switchBTN.setBounds((int)(screenWidth-screenWidth/20-10), (int)(screenHeight-screenWidth/20-10), (int)(screenWidth/20), (int)(screenWidth/20));
		switchBTN.setBounds((int)(screenWidth-screenWidth/20-10), (int)(screenHeight-screenWidth/20-10), (int)(screenWidth/20), (int)(screenWidth/20));
		//need to be deleted lately.
//		switchBTN.setIcon(new ImageIcon(WelcomeGUI.class.getResource("/img/switch.png")));
		frmWelcome.getContentPane().add(switchBTN);
		
		timeDisplayLabel = new JLabel();
		timeDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeDisplayLabel.setForeground(Color.WHITE);
//		timeDisplayLabel.setFont(new Font("Myanmar Text", Font.PLAIN, 39));
//		timeDisplayLabel.setFont(new Font("Myanmar Text", Font.PLAIN, (int)(screenWidth/49.231)));
		timeDisplayLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, (int)(screenWidth/49.231)));
		
//		timeDisplayLabel.setBounds(1490, 40, 400, 68);
//		timeDisplayLabel.setBounds((int)(screenWidth/1.2886), (int)(screenWidth/48), (int)(screenWidth/4.8), (int)(screenWidth/28.235));
		timeDisplayLabel.setBounds((int)(screenWidth/1.2886), (int)(screenWidth/48), (int)(screenWidth/4.8), (int)(screenWidth/28.235));
		frmWelcome.getContentPane().add(timeDisplayLabel);
		
		JLabel bgImageLabel = new JLabel();
		
//		bgImageLabel.setBounds(0, 0, screenWidth, screenHeight); 
		bgImageLabel.setBounds(0, 0, screenWidth, screenHeight);
		bgImageLabel.setIcon(new ImageIcon(bgImage));
		//need to be deleted.
//		bgImageLabel.setIcon(new ImageIcon(WelcomeGUI.class.getResource("/img/background3.jpg")));
		frmWelcome.getContentPane().add(bgImageLabel);
	}
	
	public void startWelcomeGUI(){
		try {
			welcomeGUI.initializeGUI();
			welcomeGUI.getFrmWelcome().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
