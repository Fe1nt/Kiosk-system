package kiosk.model;

import java.util.ArrayList;

public class ScreenInfo{	
	private String screenName;
	private int row;
	private int column;
	private ArrayList<SitInfo> allSitsArrayList;
	
	public ScreenInfo(){
		allSitsArrayList = new ArrayList<SitInfo>();
	}
	
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public ArrayList<SitInfo> getAllSitsArrayList() {
		return allSitsArrayList;
	}
	public void setAllSitsArrayList(ArrayList<SitInfo> allSitsArrayList) {
		this.allSitsArrayList = allSitsArrayList;
	}
}
