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

	// 備品名文字数チェック
	public int nameCheck(String name) {
		int nameCheck = BihinConst.INPUT_OK;
		
		try {
			// 文字数を比較してフラグを変える
			if(name.length() > BihinConst.NAME_LENGTH) {
				nameCheck = BihinConst.INPUT_NG;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return nameCheck;
	}
	
	// ID文字数チェック
	public int idCheck(String id) {
		int idCheck = BihinConst.INPUT_OK;
		
		try {
			// 文字数を比較してフラグを変える
			if(id.length() > BihinConst.ID_LENGTH) {
				idCheck = BihinConst.INPUT_NG;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return idCheck;
	}
	
	// 使用回数文字数チェック
	public int useCheck(String num) {
		int useCheck = BihinConst.INPUT_OK;
		
		try {
			// 文字数を比較してフラグを変える
			if(num.length() > BihinConst.USE_LENGTH) {
				useCheck = BihinConst.INPUT_NG;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return useCheck;
	}
	
	// ID.数字チェック
	public int idNumCheck(String id) {
		int idNumCheck = BihinConst.INPUT_OK;
		
		try {
			// 一文字ずつ確認し、数字ではなかったら、フラグを変える
			for(int i= 0; i < id.length(); i++) {
				if(!Character.isDigit(id.charAt(i))) {
					idNumCheck = BihinConst.INPUT_NG;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return idNumCheck;
	}
	
	// 使用回数.数字チェック
	public int useNumCheck(String num) {
		int useNumCheck = BihinConst.INPUT_OK;
		
		try {
			// 一文字ずつ確認し、数字ではなかったら、フラグを変える
			for(int i= 0; i < num.length(); i++) {
				if(!Character.isDigit(num.charAt(i))) {
					useNumCheck = BihinConst.INPUT_NG;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return useNumCheck;
	}
	
	// 備品登録されているかのチェック
	public int registerCheck(String name) {
		int registerCheck = BihinConst.REGISTER_OK;
		try {
			// DBを繋げる
			Connection con = databaseConnection();
			
			// 備品登録されているかSQLで調べる
			PreparedStatement ps = con.prepareStatement(SqlConst.SQL_REGISTER);
			ps.setString(SqlConst.NAME_COLUMN, name);
			ResultSet rs = ps.executeQuery();
			
			// 登録されていなかったら、フラグを変える
			if (!rs.next()) {
				registerCheck = BihinConst.REGISTER_NG;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return registerCheck;
	}
	
	
	
}

