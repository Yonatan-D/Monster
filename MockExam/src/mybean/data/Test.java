package mybean.data;

import java.util.ArrayList;

public class Test {

	private String startTime; //开始时间
	private long spareTime; //剩余时间
	private StringBuffer testContent; //存储Test集合
	private ArrayList<Integer> testID; //存储Test题号集合
	private ArrayList<String> testAnswer; //存储Test答案集合
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public long getSpareTime() {
		return spareTime;
	}
	public void setSpareTime(long spareTime) {
		this.spareTime = spareTime;
	}
	public StringBuffer getTestContent() {
		return testContent;
	}
	public void setTestContent(StringBuffer testContent) {
		this.testContent = testContent;
	}
	public ArrayList<Integer> getTestID() {
		return testID;
	}
	public void setTestID(ArrayList<Integer> testID) {
		this.testID = testID;
	}
	public ArrayList<String> getTestAnswer() {
		return testAnswer;
	}
	public void setTestAnswer(ArrayList<String> testAnswer) {
		this.testAnswer = testAnswer;
	}
	
}
