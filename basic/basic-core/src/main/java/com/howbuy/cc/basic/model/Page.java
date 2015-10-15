package com.howbuy.cc.basic.model;

import java.util.List;

public class Page<T>{

	/**
	 * 
	 */
	public final static int PAGE_NUMBER = 25;
	private List<T> pageList;
	private int pageNo;
	private int totalPage;
	private long totalCount = 0;
	private int pageSize = PAGE_NUMBER;
	
	public int getBeginNum() {
		return (pageNo - 1) * pageSize + 1;
	}

	public int getEndNum() {
		return pageNo * pageSize + 1;
	}
	
	public Page(Integer pageSize, Integer pageNo, Long totalCount) {
		if(pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}
		if(pageSize !=null && pageSize >0 ){
			this.pageSize = pageSize;
		}
		
		this.totalCount = totalCount;
		
		if(pageNo > getTotalPage()){
			this.pageNo = getTotalPage();
		}else{
			this.pageNo = pageNo;
		}
	}

	public List<T> getPageList() {
		return pageList;
	}

	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalPage() {
		if(getTotalCount() == 0){
			setTotalPage(1);
		}else{
			if (getTotalCount() % getPageSize() == 0) {
				setTotalPage((int)getTotalCount() / getPageSize());
			} else {
				setTotalPage((int)(getTotalCount() / getPageSize()) + 1);
			}
		}
		return totalPage;
	}

	private void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	

	public int getNextPage() {
		if (isLastPage()) {
			return getTotalPage();
		} else {
			return getPageNo() + 1;
		}
	}

	public int getPrePage() {
		if (isFirstPage()) {
			return 1;
		} else {
			return getPageNo() - 1;
		}
	}

	public boolean isLastPage() {
		if (getTotalPage() <= 0) {
			return true;
		} else {
			return getPageNo() >= getTotalPage();
		}
	}

	public boolean isFirstPage() {
		return getPageNo() <= 1;
	}

	
}
