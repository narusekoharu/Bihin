package com.example.demo;

public class SqlConst {
	
	// 登録処理
	public static final String SQL_REGISTER_BIHININFO = "INSERT INTO bihininfo (name, rental) VALUES (?, ?)";
	public static final String SQL_REGISTER_USENUM= "INSERT INTO usenum (id, num) VALUES (?, 0)";
	
	// 貸出処理
	public static final String SQL_RENTAL_BIHININFO_SELECT = "SELECT id FROM bihininfo WHERE name = ? AND rental = true";
	public static final String SQL_RENTAL_BIHININFO_UPDATE = "UPDATE bihininfo SET rental = false WHERE id = ?";
	public static final String SQL_RENTAL_USENUM = "UPDATE usenum SET num = num + 1 WHERE id = ?";
	
	// 返却処理
	public static final String SQL_BIHINRETURN_BIHININFO_SELECT = "SELECT id FROM bihininfo WHERE name = ? AND rental = false";
	public static final String SQL_BIHINRETURN_BIHININFO_UPDATE = "UPDATE bihininfo SET rental = true WHERE id = ?";
	
	// 削除処理
	public static final String SQL_DELETE_BIHININFO_SELECT  = "SELECT id FROM bihininfo WHERE name = ? AND rental = true";
	public static final String SQL_DELETE_BIHININFO_UPDATE = "DELETE FROM bihininfo WHERE id = ?";
	public static final String SQL_DELETE_USENUM = "DELETE FROM usenum WHERE id = ?";
	
	
	// idで内部結合
	public static final String SQL_INNER_JOIN = "SELECT bihininfo.id, bihininfo.name, bihininfo.rental, usenum.id, usenum.num "
											+ "FROM bihininfo "
											+ "INNER JOIN usenum "
											+ "ON bihininfo.id = usenum.id ";
	
	// 備品が登録されているかどうか
	public static final String SQL_REGISTER = "SELECT 1 FROM bihininfo WHERE name = ?";
	
	// プレースホルダ
	public static final int NAME_COLUMN = 1;
    public static final int RENTAL_COLUMN = 2;
    public static final int ID_COLUMN = 1;
	
	// ソート
	public static final String SQL_SORT = "ORDER BY ";
	
	// 検索
	public static final String SQL_WHERE = "WHERE 1=1 ";
	
	// 検索用チェックボックス（貸出可能）
	public static final String SQL_RENTAL_TRUE = "1";
	
	// 検索用チェックボックス（貸出中）
	public static final String SQL_RENTAL_FALSE = "0";
	
	// 検索用（使用回数.以上
	public static final String SQL_USE_UP = ">=";
	
	// 検索用（使用回数.以上
	public static final String SQL_USE_DOWN = "<=";
	
	// id
	public static final String SQL_ID = "bihininfo.id ";
	
	// 備品名
	public static final String SQL_NAME = "bihininfo.name ";
	
	// 状態
	public static final String SQL_RENTAL = "bihininfo.rental ";
	
	// 使用回数
	public static final String SQL_NUM = "usenum.num ";
	
	// 昇順
	public static final String SQL_ASC = "ASC";
	
	// 降順
	public static final String SQL_DESC = "DESC";
}