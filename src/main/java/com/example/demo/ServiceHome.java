package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceHome {
	
	// 共通処理インスタンス化
    @Autowired
    private CommonDB db;
    
    private int NAME_COLUMN = 1;
    private int RENTAL_COLUMN = 2;
    private int ID_COLUMN = 1;
	
	// 登録処理
	public int register(String name) {
		
		String sqlBihininfo = "INSERT INTO bihininfo (name, rental) VALUES (?, ?)";
		String sqlUsenum = "INSERT INTO usenum (id, num) VALUES (?, 0)";
		
		int EXIST = BihinConst.RESULT_NG;
		
		// 文字数チェック
		EXIST = db.nameCheck(name);
		
		// 文字数が３０文字以内だった場合
		if(EXIST == BihinConst.RESULT_NG) {
			try {
				// DBを繋げる
				Connection con = db.databaseConnection();
				
				
				PreparedStatement ps;
				// bihinnnameに名前と状態をINSERTして、自動生成されたIDを取得
				ps = con.prepareStatement(sqlBihininfo, Statement.RETURN_GENERATED_KEYS);
				ps.setString(NAME_COLUMN, name);
				ps.setBoolean(RENTAL_COLUMN, true);
				ps.executeUpdate();
				
				// IDをint型で取得
				ResultSet rs = ps.getGeneratedKeys();
				
				if(rs.next()) {
					int id = rs.getInt(ID_COLUMN);
				
					// usenumに、同じIDと使用回数0をINSERTする
					PreparedStatement ps2;
				
					ps2 = con.prepareStatement(sqlUsenum);
					ps2.setInt(ID_COLUMN, id);
					ps2.executeUpdate();
				
					EXIST = BihinConst.RESULT_OK;
				}
					
			}catch(Exception e) {
				e.printStackTrace();
				EXIST = BihinConst.RESULT_ERROR;
			}
		}
		return EXIST;
	}
	
	// 貸出処理
	public int rental(String name) {
		
		String sqlBihininfo1 = "SELECT id FROM bihininfo WHERE name = ? AND rental = true";
		String sqlBihininfo2 = "UPDATE bihininfo SET rental = false WHERE id = ?";
		String sqlUsenum = "UPDATE usenum SET num = num + 1 WHERE id = ?";
		
		int EXIST = BihinConst.RESULT_NG;
		
		// 文字数チェック
		EXIST = db.nameCheck(name);
				
		// 文字数が３０文字以内だった場合
		if(EXIST == BihinConst.RESULT_NG) {
			try {
				// DBを繋げる
				Connection con = db.databaseConnection();
				
				PreparedStatement ps1;
				// bihininfoから、同名で貸出可能(true)な備品を探し、IDを取得
				ps1 = con.prepareStatement(sqlBihininfo1);
				ps1.setString(NAME_COLUMN, name);
				ResultSet rs = ps1.executeQuery();
				
				// その備品のIDから更新処理をする
				if(rs.next()) {
					//IDをint型で取得する
					int id = rs.getInt("id");
					
					// bihininfoの状態を貸出中(false)に更新
					PreparedStatement ps2;
					ps2 = con.prepareStatement(sqlBihininfo2);
					ps2.setInt(ID_COLUMN, id);
					ps2.executeUpdate();
					
					// usenumの使用回数を１増やして更新
					PreparedStatement ps3;
					ps3 = con.prepareStatement(sqlUsenum);
					ps3.setInt(ID_COLUMN, id);
					ps3.executeUpdate();
					
					// 貸し出せる備品があったのでEXISTを変更する
					EXIST = BihinConst.RESULT_OK;
				}
			}catch(Exception e) {
				e.printStackTrace();
				EXIST = BihinConst.RESULT_ERROR;
			}
		}
		return EXIST;
	}
	
	// 返却処理
	public int bihinReturn(String name) {
			
		String sqlBihininfo1 = "SELECT id FROM bihininfo WHERE name = ? AND rental = false";
		String sqlBihininfo2 = "UPDATE bihininfo SET rental = true WHERE id = ?";
		
		int EXIST = BihinConst.RESULT_NG;
		
		// 文字数チェック
		EXIST = db.nameCheck(name);
				
		// 文字数が３０文字以内だった場合
		if(EXIST == BihinConst.RESULT_NG) {	
			try {
				// DBを繋げる
				Connection con = db.databaseConnection();
					
				PreparedStatement ps1;
				// bihininfoから、同名で返却可能(false)な備品を探し、IDを取得
				ps1 = con.prepareStatement(sqlBihininfo1);
				ps1.setString(NAME_COLUMN, name);
				ResultSet rs = ps1.executeQuery();
					
				// その備品のIDから更新処理をする
				if(rs.next()) {
					//IDをint型で取得する
					int id = rs.getInt("id");
						
					// bihininfoの状態を貸出可能(true)に更新
					PreparedStatement ps2;
					ps2 = con.prepareStatement(sqlBihininfo2);
					ps2.setInt(ID_COLUMN, id);
					ps2.executeUpdate();
					
					// 返却できる備品があったのでEXISTを1にする
					EXIST = BihinConst.RESULT_OK;
				}
			}catch(Exception e) {
				e.printStackTrace();
				EXIST = BihinConst.RESULT_ERROR;
			}
		}
		return EXIST;
	}
		
	// 削除処理
	public int delete(String name) {
					
		String sqlBihininfo1 = "SELECT id FROM bihininfo WHERE name = ? AND rental = true";
		String sqlBihininfo2 = "DELETE FROM bihininfo WHERE id = ?";
		String sqlUsenum = "DELETE FROM usenum WHERE id = ?";
		
		int EXIST = BihinConst.RESULT_NG;
		
		// 文字数チェック
		EXIST = db.nameCheck(name);
				
		// 文字数が３０文字以内だった場合
		if(EXIST == BihinConst.RESULT_NG) {		
			try {
				// DBを繋げる
				Connection con = db.databaseConnection();
							
				PreparedStatement ps1;
				// bihininfoから、同名で貸出可能(true)な備品を探し、IDを取得
				ps1 = con.prepareStatement(sqlBihininfo1);
				ps1.setString(NAME_COLUMN, name);
				ResultSet rs = ps1.executeQuery();
						
				// その備品のIDから削除処理をする
				if(rs.next()) {
					//IDをint型で取得する
					int id = rs.getInt("id");
								
					// bihininfoからその備品を削除する
					PreparedStatement ps2;
					ps2 = con.prepareStatement(sqlBihininfo2);
					ps2.setInt(ID_COLUMN, id);
					ps2.executeUpdate();
					
					// usenumからその備品を削除する
					PreparedStatement ps3;
					ps3 = con.prepareStatement(sqlUsenum);
					ps3.setInt(ID_COLUMN, id);
					ps3.executeUpdate();
					
					// 削除できる備品があったのでEXISTを1にする
					EXIST = BihinConst.RESULT_OK;
					}
			}catch(Exception e) {
				e.printStackTrace();
				EXIST = BihinConst.RESULT_ERROR;
			}
		}
		return EXIST;
	}
}

