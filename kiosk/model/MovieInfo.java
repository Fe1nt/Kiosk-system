package kiosk.model;


public class MovieInfo {
	private String movieName;
	private int movieDuration;
	private String moviePoster;
	private double movieTotalSold = 0;
	private int movieTotalSitNum = 0;
	
	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getMovieDuration() {
		return movieDuration;
	}

	public void setMovieDuration(int movieDuration) {
		this.movieDuration = movieDuration;
	}

	public String getMoviePoster() {
		return moviePoster;
	}

	public void setMoviePoster(String moviePoster) {
		this.moviePoster = moviePoster;
	}
	
	public double getMovieTotalSold() {
		return movieTotalSold;
	}

	public void setMovieTotalSold(double plusMovieSold) {
		this.movieTotalSold += plusMovieSold;
	}

	public int getMovieTotalSitNum() {
		return movieTotalSitNum;
	}

	public void plusMovieTotalSitNum(int plusTotalSitNum) {
		this.movieTotalSitNum += plusTotalSitNum;
	}
	
}
