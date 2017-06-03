package kiosk.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import kiosk.model.MovieArrange;
import kiosk.model.MovieInfo;
import kiosk.model.SitInfo;

public class CreateTicket {
	private MovieArrange selectSitMovieArrage;
	private JTable sitConfirmTable;
	private int selectSitNum;
	private double totalMoney;
	
	public CreateTicket(MovieArrange selectSitMovieArrage, JTable sitConfirmTable, int selectSitNum, double totalMoney){
		this.selectSitMovieArrage = selectSitMovieArrage;
		this.sitConfirmTable = sitConfirmTable;
		this.selectSitNum = selectSitNum;
		this.totalMoney = totalMoney;
		
		createTicket();
	}
	
	public void createTicket(){
		String[] timeBuf = ((String) sitConfirmTable.getValueAt(0, 1)).split(":");
		int hourBuf = Integer.parseInt(timeBuf[0]);
		int minuteBuf = Integer.parseInt(timeBuf[1]);
		if((TimeClock.getTimeClock().getHour() < hourBuf) || (TimeClock.getTimeClock().getHour() == hourBuf && TimeClock.getTimeClock().getMinute() < minuteBuf)){
			for(int i=0; i<selectSitNum; i++){
				String rowChar = (String) sitConfirmTable.getValueAt(i, 3);
				int row = ((String) sitConfirmTable.getValueAt(i, 3)).toCharArray()[0];
				row = selectSitMovieArrage.getScreenInfo().getRow()-row+65;	//char->int
//				System.out.println(selectSitMovieArrage.getScreenInfo().getRow()-(row-65) + "   " + sitConfirmTable.getValueAt(i, 3) + "    " + selectSitMovieArrage.getScreenInfo().getRow());
//				System.out.println(selectSitMovieArrage.getScreenInfo().getColumn() + "<-column");
				int column = Integer.parseInt((String) sitConfirmTable.getValueAt(i, 4));
				String type = (String) sitConfirmTable.getValueAt(i, 5);
//				System.out.println(row + "," + column + "," + type + "," + selectSitNum);
				
				for(SitInfo sitInfoList : selectSitMovieArrage.getScreenInfo().getAllSitsArrayList()){
//					System.out.println(sitInfoList.getRow() + "   "  + sitInfoList.getColumn() + "    " + sitInfoList.getSitState());
//					System.out.println(row+","+column+","+"2");
					if(sitInfoList.getRow() == row && sitInfoList.getCode() == column && sitInfoList.getSitState() == 2){
						sitInfoList.setSitState(1);
						selectSitMovieArrage.addSitSoldNum();
						
						if(type.equals("Child")){
							sitInfoList.setTicketType("Child");
							selectSitMovieArrage.addTotalMoney(8);
							selectSitMovieArrage.addChildTypeNum();
						}
						else if(type.equals("Adult")){
							sitInfoList.setTicketType("Adult");
							selectSitMovieArrage.addTotalMoney(16);
							selectSitMovieArrage.addAdultTypeNum();
						}
						else if(type.equals("Senior")){
							sitInfoList.setTicketType("Senior");
							selectSitMovieArrage.addTotalMoney(12.8);
							selectSitMovieArrage.addSeniorTypeNum();
						}
						else if(type.equals("Student")){
							sitInfoList.setTicketType("Student");
							selectSitMovieArrage.addTotalMoney(13.6);
							selectSitMovieArrage.addStudentTypeNum();
							
							String studentID = (String) sitConfirmTable.getValueAt(i, 6);
							sitInfoList.setStudentID(studentID);
//							System.out.println(studentID);
						}
						sitInfoList.setTicketID(randomIDCreator());
						InfoReader.getInfoReader().getTicketIdArrayList().add(sitInfoList.getTicketID());
						
						File ticketFile = new File("D:/ticket-kiosk-1-7/ticket/" + sitInfoList.getTicketID() + ".txt");
						if(ticketFile.exists()){
							ticketFile.delete();
						}
						try{
							FileWriter ticketFileWriter = new FileWriter(ticketFile);
							BufferedWriter out = new BufferedWriter(ticketFileWriter);
							out.write("Movie Name:   " + selectSitMovieArrage.getMovieInfo().getMovieName() + "\n");
							out.write("Start time:   " + new DecimalFormat("00").format(selectSitMovieArrage.getTimeHour()) + ":" + new DecimalFormat("00").format(selectSitMovieArrage.getTimeMinute()) + "\n");
							out.write("Screen NO.:   " + selectSitMovieArrage.getScreenInfo().getScreenName() + "\n");
							out.write("Ticket Type:  " + sitInfoList.getTicketType() + "\n");
							out.write("Sit Position: " + rowChar + " row & " + sitInfoList.getCode() + " column\n");
							out.write("Ticket ID:    " + sitInfoList.getTicketID() + "\n");
							if(sitInfoList.getTicketType().equals("Student")){
								out.write("Student ID:   " + sitInfoList.getStudentID() + "\n");
							}
							out.flush();
							out.close();
							ticketFileWriter.close();
						}
						catch(IOException e){
							
						}
					}
				}
			}
			JOptionPane.showMessageDialog(null,"Buy ticket(s) successfully!","Information",JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(null,"Sorry, you are late to buy the ticket.\nPlease choose another showing.","Information",JOptionPane.INFORMATION_MESSAGE);
		}

		for(MovieInfo movieInfoList : InfoReader.getInfoReader().getMovieInfoArrayList()){
			if(movieInfoList.getMovieName().equals((String) sitConfirmTable.getValueAt(0, 0))){
				movieInfoList.setMovieTotalSold(totalMoney);
				movieInfoList.plusMovieTotalSitNum(selectSitNum);
//				System.out.println(movieInfoList.getMovieTotalSold());
			}
		}
	}
	
	public String randomIDCreator(){
		String randomID = "";
		for(int j=0; j<8; j++){
			int randomBuf = (int)(Math.random()*4+1);
			randomID += randomBuf;
		}
		for(String randomIDList : InfoReader.getInfoReader().getTicketIdArrayList()){
			if(randomID.equals(randomIDList)){
				randomID = randomIDCreator();
			}
		}
		return randomID;
	}
}
