package kiosk.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import kiosk.model.MovieArrange;
import kiosk.model.MovieInfo;

public class CreateReport {
	public CreateReport(){
		readTicketInfo();
	}
	
	public void readTicketInfo(){
		File emailFile = new File("D:/ticket-kiosk-1-7/report/email.txt");
		if(emailFile.exists()){
			emailFile.delete();
		}
		try{
			FileWriter emailFileWriter = new FileWriter(emailFile);
			BufferedWriter out = new BufferedWriter(emailFileWriter);
			SimpleDateFormat todayDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			out.write("Information of " + todayDateFormat.format(new Date()) + ":\n");
			double totalMoney = 0;
			int sitSoldNum = 0;
			int childTypeNum = 0;
			int adultTypeNum = 0;
			int seniorTypeNum = 0;
			int studentTypeNum = 0;
			for(MovieArrange movieArrangeList : InfoReader.getInfoReader().getMovieArrangeArrayList()){
				totalMoney += movieArrangeList.getTotalMoney();
				sitSoldNum += movieArrangeList.getSitSoldNum();
				childTypeNum += movieArrangeList.getChildTypeNum();
				adultTypeNum += movieArrangeList.getAdultTypeNum();
				seniorTypeNum += movieArrangeList.getSeniorTypeNum();
				studentTypeNum += movieArrangeList.getStudentTypeNum();
			}
			out.write("Total money sale of the day: " + totalMoney + "\n");
			out.write("Total sits sale of the day: " + sitSoldNum + "\n");
			out.write("Total child sits sale of the day: " + childTypeNum + "\n");
			out.write("Total adult sits sale of the day: " + adultTypeNum + "\n");
			out.write("Total senior sits sale of the day: " + seniorTypeNum + "\n");
			out.write("Total student sits sale of the day: " + studentTypeNum + "\n");
			
			for(MovieInfo movieInfoList : InfoReader.getInfoReader().getMovieInfoArrayList()){
				out.write("Movie: " + movieInfoList.getMovieName() + " total money sale of the day: " + movieInfoList.getMovieTotalSold() + "\n");
				out.write("Movie: " + movieInfoList.getMovieName() + " total sits sale of the day: " + movieInfoList.getMovieTotalSitNum() + "\n");
			}
			out.flush();
			out.close();
			emailFileWriter.close();
		}
		catch(Exception e){
			
		}
	}
}
