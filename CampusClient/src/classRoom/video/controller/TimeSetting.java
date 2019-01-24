package classRoom.video.controller;

public class TimeSetting {
	private int hour;
	private int minute;
	private int second;
	
	public void setHour(int hour){
		this.hour = hour%24;
	}
	
	public int getHour(){
		return hour;
	}

	public int getMinute() {
		return minute;
	}
	
	public void setMinute(int minute) {
		this.minute = minute%60;
		setHour(minute/60);		
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second%60;
		setMinute(second/60); //166
	}
}
