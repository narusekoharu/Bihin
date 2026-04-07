package com.example.demo;

//備品一覧情報用データクラス
public class BihinData {
	// ID
	private int id;
	// 名前
	private String name;
	// 状態
	private Boolean rental;
	// 使用回数
	private int num;
	
	public BihinData(int id, String name, Boolean rental, int num) {
		this.id = id;
        this.name = name;
        this.rental = rental;
        this.num = num;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Boolean getRental() {
		return rental;
	}
		
	public int getNum() {
		return num;
	}

}