package mybean.data;

import java.util.ArrayList;

public class Test {

	private String startTime; //��ʼʱ��
	private long spareTime; //ʣ��ʱ��
	private StringBuffer testContent; //�洢Test����
	private ArrayList<Integer> testID; //�洢Test��ż���
	private ArrayList<String> testAnswer; //�洢Test�𰸼���
	
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
