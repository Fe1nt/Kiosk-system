package kiosk.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import kiosk.model.MovieArrange;
import kiosk.model.MovieInfo;
import kiosk.model.ScreenInfo;
import kiosk.model.SitInfo;

public class InfoReader {
	private static InfoReader infoReader = new InfoReader();
	private ArrayList<MovieInfo> movieInfoArrayList = new ArrayList<MovieInfo>();
	private ArrayList<ScreenInfo> screenInfoArrayList = new ArrayList<ScreenInfo>();
	private ArrayList<MovieArrange> movieArrangeArrayList = new ArrayList<MovieArrange>();
	private ArrayList<String> ticketIdArrayList = new ArrayList<String>();
	
	//Make sure that the class can't be instanced.
	private InfoReader(){
		screenInfoArrayList.add(screenLayoutReader("Screen1"));
		screenInfoArrayList.add(screenLayoutReader("Screen2"));
		screenInfoArrayList.add(screenLayoutReader("Screen3"));
		
		movieInfoReader();
	}
	
	public static InfoReader getInfoReader() {
		return infoReader;
	}
	
	public ArrayList<MovieInfo> getMovieInfoArrayList() {
		return movieInfoArrayList;
	}
	public ArrayList<ScreenInfo> getScreenInfoArrayList() {
		return screenInfoArrayList;
	}
	public ArrayList<MovieArrange> getMovieArrangeArrayList() {
		return movieArrangeArrayList;
	}
	public ArrayList<String> getTicketIdArrayList() {
		return ticketIdArrayList;
	}
	
	public void movieArrangeReader(){
		try{
			File timeTableFile = new File("D:/ticket-kiosk-1-7/content/timetable.txt");
			FileReader timeTableFileReader = new FileReader(timeTableFile);
			BufferedReader reader = new BufferedReader(timeTableFileReader);
			String line = null;
			while((line = reader.readLine()) != null){
				String[] buf = line.split(",");
				MovieInfo movieInfo = new MovieInfo();
				ScreenInfo screenInfo = new ScreenInfo();
				MovieArrange movieArrange = new MovieArrange();
				for(MovieInfo movieInfoList : movieInfoArrayList){
					if(movieInfoList.getMovieName().equals(buf[0])){
						movieInfo = movieInfoList;
					}
				}
				for(ScreenInfo screenInfoList : screenInfoArrayList){
					if(screenInfoList.getScreenName().equals(buf[1])){
						screenInfo = screenInfoList;
						screenInfo = screenLayoutReader(buf[1]);
					}
				}
				movieArrange.setMovieInfo(movieInfo);
				movieArrange.setScreenInfo(screenInfo);
				movieArrange.setTimeHour(Integer.parseInt(buf[2]));
				movieArrange.setTimeMinute(Integer.parseInt(buf[3]));
				movieArrangeArrayList.add(movieArrange);

			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public ScreenInfo screenLayoutReader(String fileName){
		ScreenInfo screenInfo = new ScreenInfo();
		try {
			String path = "D:/ticket-kiosk-1-7/content/" + fileName + "Layout.txt";
			File screenLayoutFile = new File(path);
			FileReader screenLayoutFileReader = new FileReader(screenLayoutFile);
			BufferedReader reader = new BufferedReader(screenLayoutFileReader);
			String line = null;
			int row;
			int column;
			int i = 1;
			while((line = reader.readLine()) != null){
				if(i == 1){
					screenInfo.setScreenName(line);
				}
				else if(i == 2){
					String[] buf = line.split(",");
					screenInfo.setRow((row = Integer.parseInt(buf[0])));
					screenInfo.setColumn((column = Integer.parseInt(buf[1])));
				}
				else{
					String[] buf = line.split(",");
					int code = 1;
					for(int j=0; j< buf.length; j++){
						if(buf[j].equals("x")){
							SitInfo sitInfo = new SitInfo();
							sitInfo.setRow(i-2);
							sitInfo.setColumn(j+1);
							sitInfo.setCode(code);
							sitInfo.setSitState(0);
							code ++;
							screenInfo.getAllSitsArrayList().add(sitInfo);
//							System.out.println("Row: " + (i-2) + " & Column: " + (j+1) + " is a sit.");
						}
						else if(buf[j].equals("o")){
//							System.out.println("Row: " + (i-2) + " & Column: " + (j+1) + " is corridor.");
						}
					}
				}
				i++;
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return screenInfo;
	}
	
	public void movieInfoReader(){
		File movieInfoFile = new File("D:/ticket-kiosk-1-7/content/MovieInfo.txt");
		FileReader movieInfoFileReader;
		try {
			movieInfoFileReader = new FileReader(movieInfoFile);
			BufferedReader reader = new BufferedReader(movieInfoFileReader);
			String line = null;
			String path = "D:/ticket-kiosk-1-7/img/";
			while((line = reader.readLine()) != null){
				String[] buf = line.split(",");
				MovieInfo movieInfo = new MovieInfo();
				movieInfo.setMovieName(buf[0]);
				movieInfo.setMovieDuration(Integer.parseInt(buf[1]));
				movieInfo.setMoviePoster(path + buf[2] + ".png");
				movieInfoArrayList.add(movieInfo);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
