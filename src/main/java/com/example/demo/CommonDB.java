package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class CommonDB {
	
	final String URL = "jdbc:mysql://localhost/bihindb";
	final String USER = "root";
	final String PASS = "pass";
	
	Connection con = null;
	
	// DBを繋げ、コネクション変数を返す
	public Connection databaseConnection(){
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	
	// DBを閉じる
	public void databaseClose() {
		try {
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 備品一覧を生成してそれを返却する
	public List<BihinData> bihinList() {
			
		//　IDと名前と状態と使用回数が格納されるリストを生成
		ArrayList<BihinData> datalist = new ArrayList<>();
			
		try{
				Connection con = databaseConnection();
					
				PreparedStatement tb = con.prepareStatement(SqlConst.SQL_INNER_JOIN);
					
				ResultSet rs = tb.executeQuery();
					
				// データがあるまでdatalistにデータを格納する
				while(rs.next()) {
						int wkID = rs.getInt("id");
						String wkName = rs.getString("name");
						Boolean wkRental = rs.getBoolean("rental");
						int wkNum = rs.getInt("num");
						BihinData data = new BihinData(wkID, wkName, wkRental, wkNum);
						datalist.add(data);
						};
		}catch(Exception e) {
			e.printStackTrace();
		}
		return datalist;
	}

	// 文字数チェック
	public int nameCheck(String name) {
		int length = name.length();
		int nameCheck = BihinConst.RESULT_NG;
		
		try {
			if(length > BihinConst.NAME_LENGTH) {
				nameCheck = BihinConst.RESULT_NUM;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return nameCheck;
	}
}

