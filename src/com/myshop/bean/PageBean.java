package com.myshop.bean;

import java.util.List;

public class PageBean<E> {
	
	private Integer curPage;
	private Long totalSize;
	private Integer totalPage;
	private Integer pageSize;
	private List<E> list;
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<E> getList() {
		return list;
	}
	public void setList(List<E> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PageBean [curPage=" + curPage + ", totalSize=" + totalSize + ", totalPage=" + totalPage + ", pageSize="
				+ pageSize + ", list=" + list + "]";
	}
	 
}
