package kiosk.controller;

import java.util.Timer;

import kiosk.view.WelcomeGUI;

/*
 * Singleton pattern.
 */
public class Main {
	private static Main main = new Main();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	}
	
	//Make sure that the class can't be instanced.
	private Main(){
		WelcomeGUI.getWelcomeGUI().startWelcomeGUI();
		startTimer();
	}
	
	public static Main getMain() {
		return main;
	}
	
	public void startTimer(){
		Timer timer = new Timer();
		TimeClock timeClock = TimeClock.getTimeClock();
		timer.schedule(timeClock, 0, 1000);//(long time1 long time2 )
	}
}
