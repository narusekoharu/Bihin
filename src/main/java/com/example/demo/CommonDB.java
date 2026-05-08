package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;

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
}

