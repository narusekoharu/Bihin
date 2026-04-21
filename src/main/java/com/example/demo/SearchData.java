package com.example.demo;

// 検索用データクラス
public class SearchData {
	private String searchId;
	private String searchName;
	private Boolean searchRentalTrue;
	private Boolean searchRentalFalse;
	private String searchUseNum;
	private String searchUseRadio;
	
	
	// ゲッター
	
	public String getSearchId() {
		return searchId;
	}
	
	public String getSearchName() {
		return searchName;
	}
	
	public Boolean getSearchRentalTrue() {
		return searchRentalTrue;
	}
	
	public Boolean getSearchRentalFalse() {
		return searchRentalFalse;
	}
	
	public String getSearchUseNum() {
		return searchUseNum;
	}
	
	public String getSearchUseRadio() {
		return searchUseRadio;
	}
	
	// セッター
	
	public void setSearchId(String serachId) {
		this.searchId = serachId;
	}
	
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	
	public void setSearchRentalTrue(Boolean searchRentalTrue) {
		this.searchRentalTrue = searchRentalTrue;
	}
	
	public void setSearchRentalFalse(Boolean searchRentalFalse) {
		this.searchRentalFalse = searchRentalFalse;
	}
	
	public void setSearchUseNum(String searchUseNum) {
		this.searchUseNum = searchUseNum;
	}
	
	public void setSearchUseRadio(String searchUseRadio) {
		this.searchUseRadio = searchUseRadio;
	}

}
