package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    		
    		PreparedStatement ps = null;
    		
    		String search_sql = SqlConst.SQL_INNER_JOIN + SqlConst.SQL_WHERE;
    		
    		// ID検索がある場合
    		if(!(searchedId == "")) {
    			search_sql = search_sql + " AND " + SqlConst.SQL_ID + "= " + Integer.parseInt(searchedId);
    		}
    		
    		// 備品検索がある場合
    		if(!(searchedName == "")) {
    			search_sql = search_sql + " AND " + SqlConst.SQL_NAME + " LIKE '%"+ searchedName + "%' ";
    		}

    		// 貸出可能も貸出中も状態検索がある場合
    		if((!(searchRentalTrue == null))&&(!(searchRentalFalse == null))) {
    			search_sql = SqlConst.SQL_INNER_JOIN + SqlConst.SQL_WHERE;
    		
    		// 貸出可能状態検索がある場合	
    		}else if(!(searchRentalTrue == null)) {
    			search_sql = search_sql + " AND " + SqlConst.SQL_RENTAL + " = " + SqlConst.SQL_RENTAL_TRUE;
    			
    		// 貸出中状態検索がある場合
    		}else if(!(searchRentalFalse == null)) {
    			search_sql = search_sql + " AND " + SqlConst.SQL_RENTAL + " = " + SqlConst.SQL_RENTAL_FALSE;
    		}
    		
    		// 使用回数検索がある場合（以上）
    		if((!(searchUseNum == "")) && searchUseRadio.equals("up")) {
    			search_sql = search_sql + " AND " + SqlConst.SQL_NUM + SqlConst.SQL_USE_UP + Integer.parseInt(searchUseNum);
    		}
    		
    		// 使用回数検索がある場合（以下）
    		if((!(searchUseNum == "")) && searchUseRadio.equals("down")) {
    			search_sql = search_sql + " AND " + SqlConst.SQL_NUM + SqlConst.SQL_USE_DOWN + Integer.parseInt(searchUseNum);
    		}
    		
    		System.out.println(search_sql);
    		
    		ps = con.prepareStatement(search_sql);
			
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


}
