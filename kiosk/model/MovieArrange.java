package kiosk.model;

public class MovieArrange {
	private ScreenInfo screenInfo;
	private MovieInfo movieInfo;
	private int timeHour;
	private int timeMinute;
	private double totalMoney = 0;
	private int sitSoldNum = 0;
	private int childTypeNum = 0;
	private int adultTypeNum = 0;
	private int seniorTypeNum = 0;
	private int studentTypeNum = 0;
	
	public ScreenInfo getScreenInfo() {
		return screenInfo;
	}
	public void setScreenInfo(ScreenInfo screenInfo) {
		this.screenInfo = screenInfo;
	}
	public MovieInfo getMovieInfo() {
		return movieInfo;
	}
	public void setMovieInfo(MovieInfo movieInfo) {
		this.movieInfo = movieInfo;
	}
	public int getTimeHour() {
		return timeHour;
	}
	public void setTimeHour(int timeHour) {
		this.timeHour = timeHour;
	}
	public int getTimeMinute() {
		return timeMinute;
	}
	public void setTimeMinute(int timeMinute) {
		this.timeMinute = timeMinute;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public void addTotalMoney(double plusMoney) {
		this.totalMoney += plusMoney;
	}
	public int getSitSoldNum() {
		return sitSoldNum;
	}
	public void addSitSoldNum() {
		this.sitSoldNum++;
	}
	public int getChildTypeNum() {
		return childTypeNum;
	}
	public void addChildTypeNum() {
		this.childTypeNum++;
	}
	public int getAdultTypeNum() {
		return adultTypeNum;
	}
	public void addAdultTypeNum() {
		this.adultTypeNum++;
	}
	public int getSeniorTypeNum() {
		return seniorTypeNum;
	}
	public void addSeniorTypeNum() {
		this.seniorTypeNum++;
	}
	public int getStudentTypeNum() {
		return studentTypeNum;
	}
	public void addStudentTypeNum() {
		this.studentTypeNum++;
	}
}
