package mybean.data;

import com.sun.rowset.CachedRowSetImpl;

import exam.config.ExamConfig;

public class ShowByPage implements ExamConfig{
	
	private CachedRowSetImpl rowSet; //�洢��¼���м�����
	private int pageSize = PAGESIZE; //ÿҳ��ʾ�ļ�¼��:Ĭ��15
	private int pageAllCount; //��ҳ��
	private int presentPage;  //��ǰҳ
	private int pageAllSize; //�ܼ�¼��
	private StringBuffer presentPageResult; //��ǰҳ����
	
	public CachedRowSetImpl getRowSet() {
		return rowSet;
	}
	public void setRowSet(CachedRowSetImpl rowSet) {
		this.rowSet = rowSet;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageAllCount() {
		return pageAllCount;
	}
	public void setPageAllCount(int pageAllCount) {
		this.pageAllCount = pageAllCount;
	}
	public int getPresentPage() {
		return presentPage;
	}
	public void setPresentPage(int presentPage) {
		this.presentPage = presentPage;
	}
	public int getPageAllSize() {
		return pageAllSize;
	}
	public void setPageAllSize(int pageAllSize) {
		this.pageAllSize = pageAllSize;
	}
	public StringBuffer getPresentPageResult() {
		return presentPageResult;
	}
	public void setPresentPageResult(StringBuffer presentPageResult) {
		this.presentPageResult = presentPageResult;
	}
	
}
