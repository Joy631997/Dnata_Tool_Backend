package com.teleapps.DnataReportingTool.Dto;

public class DnataDto {

	private String date;
	private int offered;
	private int answered;
	private int transferred;
	private int avgHandlingTime;

	public DnataDto() {
		super();
	}

	public DnataDto(String date, int offered, int answered, int transferred, int avgHandlingTime) {
		super();
		this.date = date;
		this.offered = offered;
		this.answered = answered;
		this.transferred = transferred;
		this.avgHandlingTime = avgHandlingTime;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getOffered() {
		return offered;
	}
	public void setOffered(int offered) {
		this.offered = offered;
	}
	public int getAnswered() {
		return answered;
	}
	public void setAnswered(int answered) {
		this.answered = answered;
	}
	public int getTransferred() {
		return transferred;
	}
	public void setTransferred(int transferred) {
		this.transferred = transferred;
	}
	public int getAvgHandlingTime() {
		return avgHandlingTime;
	}
	public void setAvgHandlingTime(int avgHandlingTime) {
		this.avgHandlingTime = avgHandlingTime;
	}

	@Override
	public String toString() {
		return "DnataDto [date=" + date + ", offered=" + offered + ", answered=" + answered + ", transferred="
				+ transferred + ", avgHandlingTime=" + avgHandlingTime + "]";
	}

	
}
