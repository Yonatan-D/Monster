package mybean.data;

import com.sun.rowset.CachedRowSetImpl;

import exam.config.ExamConfig;

public class ShowByPage implements ExamConfig{
	
	private CachedRowSetImpl rowSet; //存储记录的行集对象
	private int pageSize = PAGESIZE; //每页显示的记录数:默认15
	private int pageAllCount; //总页数
	private int presentPage;  //当前页
	private int pageAllSize; //总记录数
	private StringBuffer presentPageResult; //当前页内容
	
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
