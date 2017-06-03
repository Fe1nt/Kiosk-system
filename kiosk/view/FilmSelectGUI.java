package kiosk.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import kiosk.controller.InfoReader;
import kiosk.controller.TimeClock;
import kiosk.model.MovieInfo;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * Singleton pattern.
 */
public class FilmSelectGUI {
//	private static FilmSelectGUI filmSelectGUI = new FilmSelectGUI();
	private double divide = 1;
	private JFrame frmFilmSelect;
	private JLabel timeDisplayLabel;
	private int screenWidth;
	private int screenHeight;

	public JLabel getTimeDisplayLabel() {
		return timeDisplayLabel;
	}

	public JFrame getFrmFilmSelect() {
		return frmFilmSelect;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initializeGUI() {
		frmFilmSelect = new JFrame();
		frmFilmSelect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFilmSelect.setTitle("Self-service Ticketing Kiosk");
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		screenWidth = (int)(screenSize.width/divide);
		screenHeight = (int)(screenSize.height/divide);
		frmFilmSelect.setBounds(0, 0, screenWidth, screenHeight);
		frmFilmSelect.setUndecorated(true);
		
		Image bgImage = new ImageIcon("D:/ticket-kiosk-1-7/img/background.jpg").getImage();
		bgImage = bgImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_DEFAULT);
		
		Image leftImage = new ImageIcon("D:/ticket-kiosk-1-7/img/left.png").getImage();
		leftImage = leftImage.getScaledInstance((int)(screenWidth/20), (int)(screenWidth/20), Image.SCALE_DEFAULT);
		
		frmFilmSelect.getContentPane().setLayout(null);
		
		JButton leftBTN = new JButton();
		leftBTN.setContentAreaFilled(false);	//make button transparent.
		leftBTN.setBorderPainted(false);		//make button has no frame after click it.
		leftBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WelcomeGUI.getWelcomeGUI().getFrmWelcome().setVisible(true);
				frmFilmSelect.dispose();
			}
		});
		
		showAllFilm();
		
		leftBTN.setIcon(new ImageIcon(leftImage));
//		leftBTN.setBounds(10, (int)(screenHeight-screenWidth/20+10), (int)(screenWidth/20), (int)(screenWidth/20));
		leftBTN.setBounds(20, (int)(20), (int)(screenWidth/20), (int)(screenWidth/20));
		//need to be deleted lately.
//		leftBTN.setIcon(new ImageIcon(FilmSelectGUI.class.getResource("/img/config.png")));
		frmFilmSelect.getContentPane().add(leftBTN);
		
		JLabel TitleLabel = new JLabel("Select a favourite film:");
		TitleLabel.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, (int)(screenWidth/30)));
		TitleLabel.setForeground(Color.WHITE);
//		TitleLabel.setBounds((int)(50 + screenWidth/20), (int)(screenWidth/55), (int)(screenWidth/2), (int)(screenWidth/25));
		TitleLabel.setBounds((int)(50 + screenWidth/20), (int)(screenWidth/55), (int)(screenWidth/2), (int)(screenWidth/25));
		frmFilmSelect.getContentPane().add(TitleLabel);
		
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
		frmFilmSelect.getContentPane().add(timeDisplayLabel);
		
		JLabel bgImageLabel = new JLabel();
		
//		bgImageLabel.setBounds(0, 0, screenWidth, screenHeight); 
		bgImageLabel.setBounds(0, 0, screenWidth, screenHeight);
		bgImageLabel.setIcon(new ImageIcon(bgImage));
		//need to be deleted.
//		bgImageLabel.setIcon(new ImageIcon(FilmSelectGUI.class.getResource("/img/background3.jpg")));
		frmFilmSelect.getContentPane().add(bgImageLabel);
	}
	public void showAllFilm(){
		JPanel tablePanel = new JPanel();
		tablePanel.setBackground(new Color(147, 112, 219));
//		tablePanel.setBounds(0, (int)(screenWidth/20 + 50), screenWidth, (int)(screenHeight-(screenWidth/20 + 50)));
		tablePanel.setBounds(0, (int)(screenWidth/20 + 50), screenWidth, (int)(screenHeight-(screenWidth/20 + 50)));
		frmFilmSelect.getContentPane().add(tablePanel);
		tablePanel.setOpaque(false);
		tablePanel.setLayout(null);
		
		
		DefaultTableModel model=new DefaultTableModel(){
			public Class<?> getColumnClass(int col) {
				Vector<?> v = (Vector<?>) dataVector.elementAt(col);
				return v.elementAt(col).getClass();
			}
			public boolean isCellEditable(int row, int column) {
				//rewrite isCellEditable method to make sure that the cell can't be edited. 
				return false; 
			}
		}; 
		
		
		final JTable filmSelectTable = new JTable(model);
		filmSelectTable.setEnabled(true);
		filmSelectTable.setForeground(Color.WHITE);
		filmSelectTable.setFont(new Font("Microsoft JhengHei", Font.PLAIN, (int)(screenWidth/48)));
		filmSelectTable.setBackground(Color.WHITE);
		filmSelectTable.setSelectionForeground(Color.LIGHT_GRAY);
		filmSelectTable.setOpaque(false);
//		filmSelectTable.setShowVerticalLines(false);
		JScrollPane scrollPane = new JScrollPane(filmSelectTable);
//		scrollPane.setBounds(0, 0, screenWidth, (int)(screenHeight-(screenWidth/20 + 50)));
		scrollPane.setBounds(0, 0, screenWidth, (int)(screenHeight-(screenWidth/20 + 50)));
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		tablePanel.add(scrollPane);
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		renderer.setOpaque(false);
		
		
		model.addColumn("NAME");
		model.addColumn("POSTER");
		model.addColumn("DURATION");
		
		
		Object[] bufArr = new Object[3];

		for(MovieInfo movieInfoList : InfoReader.getInfoReader().getMovieInfoArrayList()){
			bufArr[0] = movieInfoList.getMovieName();
			bufArr[1] = new ImageIcon(movieInfoList.getMoviePoster());
			bufArr[2] = movieInfoList.getMovieDuration()+" Minutes";
			model.addRow(bufArr);
		}
		
		filmSelectTable.getColumn("NAME").setCellRenderer(renderer);
		filmSelectTable.getColumn("DURATION").setCellRenderer(renderer);
//		filmSelectTable.getColumn("POSTER").setCellRenderer(renderer);
		filmSelectTable.getTableHeader().setBackground(new Color(147, 112, 219));
		filmSelectTable.getTableHeader().setFont(new Font("Microsoft JhengHei", Font.BOLD, (int)(screenWidth/64)));
		filmSelectTable.getTableHeader().setForeground(Color.WHITE);
		filmSelectTable.setRowHeight((int)(290));
		filmSelectTable.getColumn("POSTER").setMaxWidth(200);
		filmSelectTable.getColumn("POSTER").setMinWidth(200);
		filmSelectTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(filmSelectTable.getValueAt(filmSelectTable.getSelectedRow(), 0) != null){
					WelcomeGUI.getWelcomeGUI().getTimeTableGUI().startTimeTableGUI((String)filmSelectTable.getValueAt(filmSelectTable.getSelectedRow(), 0));
					frmFilmSelect.setVisible(false);
//					System.out.println((String)filmSelectTable.getValueAt(filmSelectTable.getSelectedRow(), 0));
				}
//				System.out.println(filmSelectTable.getSelectedRow());
				
			}
		});
	}
	public void startFilmSelectGUI(){
		try {
			WelcomeGUI.getWelcomeGUI().getFilmSelectGUI().initializeGUI();
			WelcomeGUI.getWelcomeGUI().getFilmSelectGUI().getFrmFilmSelect().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
