package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 検索画面
@Service
public class ServiceSearch {
	
	// 共通処理インスタンス化
    @Autowired
    private CommonDB db;
    
  
    // 検索機能
    public List<BihinData> searchBihin(String searchedId, 
    								   String searchedName, 
    								   Boolean searchRentalTrue, 
    								   Boolean searchRentalFalse, 
    								   String searchUseNum, 
    								   String searchUseRadio){
    	
    	List<BihinData> searchedList = new ArrayList<>();

    	try {
    		// DBを繋げる
    		Connection con = db.databaseConnection();
    		
    		String search_sql = SqlConst.SQL_INNER_JOIN + SqlConst.SQL_WHERE;
    		
    		// ID検索がある場合
    		if(!(searchedId == "")) {
    			search_sql = search_sql + " AND " + SqlConst.SQL_ID + "= ?";
    		}
    		
    		// 備品検索がある場合
    		if(!(searchedName == "")) {
    			search_sql = search_sql + " AND " + SqlConst.SQL_NAME + " LIKE ? ";
    		}

    		// 貸出可能も貸出中も状態検索がある場合
    		if((searchRentalTrue == true)&&(searchRentalFalse == true)) {
    			search_sql = SqlConst.SQL_INNER_JOIN + SqlConst.SQL_WHERE;
    		
    		// 貸出可能状態検索がある場合	
    		}else if(searchRentalTrue == true) {
    			search_sql = search_sql + " AND " + SqlConst.SQL_RENTAL + " = " + SqlConst.SQL_RENTAL_TRUE;
    			
    		// 貸出中状態検索がある場合
    		}else if(searchRentalFalse == true) {
    			search_sql = search_sql + " AND " + SqlConst.SQL_RENTAL + " = " + SqlConst.SQL_RENTAL_FALSE;
    		}
    		
    		// 使用回数検索がある場合（以上）
    		if((!(searchUseNum == "")) && searchUseRadio.equals("up")) {
    			search_sql = search_sql + " AND " + SqlConst.SQL_USE + SqlConst.SQL_USE_UP + " ? ";
    		}
    		
    		// 使用回数検索がある場合（以下）
    		if((!(searchUseNum == "")) && searchUseRadio.equals("down")) {
    			search_sql = search_sql + " AND " + SqlConst.SQL_USE + SqlConst.SQL_USE_DOWN + " ? ";
    		}
    		
    		PreparedStatement ps = con.prepareStatement(search_sql);
    		
    		// プレースホルダにいれていく
    		int i = 1;
    		
    		if(!(searchedId == "")) {
    			ps.setInt(i++, Integer.parseInt(searchedId));
    		}
    		if(!(searchedName == "")) {
    			ps.setString(i++, "%" + searchedName + "%");
    		}
    		if((!(searchUseNum == "")) && searchUseRadio.equals("up")) {
    			ps.setInt(i++, Integer.parseInt(searchUseNum));
    		}
    		if((!(searchUseNum == "")) && searchUseRadio.equals("down")) {
    			ps.setInt(i++, Integer.parseInt(searchUseNum));
    		}
			
			// 検索実行する
			ResultSet rs = ps.executeQuery();
			
			// データがあるまでsearchedListに格納する
			while(rs.next()) {
				int wkID = rs.getInt("id");
				String wkName = rs.getString("name");
				Boolean wkRental = rs.getBoolean("rental");
				int wkNum = rs.getInt("num");
				BihinData data = new BihinData(wkID, wkName, wkRental, wkNum);
				searchedList.add(data);
			}
			
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return searchedList;
    }
    
    // 文字数チェック
    public int lengthCheck(String some, int maxlength) {
    	int lengthCheck = BihinConst.INPUT_OK;
    	
    	// 文字数を比較してフラグを変える
    	if(some.length() > maxlength) {
    		lengthCheck = BihinConst.INPUT_NG;
    	}
    	return lengthCheck;
    }
    
    // 数字のみかチェック
    public int numCheck(String some) {
    	int numCheck = BihinConst.INPUT_OK;
    	
    	// 一文字ずつ確認し、数字ではなかったら、フラグを変える
    	for(int i = 0; i < some.length(); i++) {
    		if(!Character.isDigit(some.charAt(i))) {
    			numCheck = BihinConst.INPUT_NG;
    		}
    	}
    	return numCheck;
    }
}


