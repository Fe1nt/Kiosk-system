package kiosk.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import kiosk.view.FilmSelectGUI;
import kiosk.view.TicketSelectGUI;
import kiosk.view.TimeTableGUI;
import kiosk.view.WelcomeGUI;

public class TimeClock extends TimerTask{
	private static TimeClock timeClock = new TimeClock(9, 0);//(set hour set min)
	private int hour;
	private int minute;
	private SimpleDateFormat todayDateFormat = new SimpleDateFormat("yyyy-MM-dd  ");
	
	private TimeClock(int setHour, int setMinute){
		this.hour = setHour;
		this.minute = setMinute;
	}
	
	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public static TimeClock getTimeClock() {
		return timeClock;
	}
	
	public void timePlus(int plusMinute){
		minute += plusMinute;
		if(minute == 60) {
			if(hour == 23){
				hour = 0;
			}
			else{
				hour++;
			}
			minute = 0;
		}
//		System.out.println(hour + " : " + minute);
	}
	

	@Override
	public void run() {
		timePlus(1);
		
		String buftime =todayDateFormat.format(new Date()) + new DecimalFormat("00").format(hour) + " : " + new DecimalFormat("00").format(minute);
		try{
			WelcomeGUI.getWelcomeGUI().getTimeDisplayLabel().setText(buftime);
			WelcomeGUI.getWelcomeGUI().getFilmSelectGUI().getTimeDisplayLabel().setText(buftime);
			WelcomeGUI.getWelcomeGUI().getTimeTableGUI().getTimeDisplayLabel().setText(buftime);
			WelcomeGUI.getWelcomeGUI().getTicketSelectGUI().getTimeDisplayLabel().setText(buftime);
			WelcomeGUI.getWelcomeGUI().getTicketSelectGUI().getSitConfirmGUI().getTimeDisplayLabel().setText(buftime);
		}
		catch(Exception e){}
	}
}
