package com.example.demo;

// 定数一覧クラス
public class BihinConst {
	
	// 処理可能
	public static final int RESULT_OK = １;
	
	// 処理不可能
	public static final int RESULT_NG = 0;
	
	// 入力チェック成功フラグ
	public static final int INPUT_OK = 2;
	
	// 入力チェック失敗フラグ
	public static final int INPUT_NG = 3;
	
	// 登録チェック成功フラグ
	public static final int REGISTER_OK = 4;
	
	// 登録チェック成功フラグ
	public static final int REGISTER_NG = 5;
	
	// 備品名30文字
	public static final int NAME_LENGTH = 30;
	
	// ID3桁
	public static final int ID_LENGTH = 3;
	
	// 使用回数3桁
	public static final int USE_LENGTH = 3;
	
	// 例外エラーが発生した場合
	public static final int RESULT_ERROR = 9;
	
	
	// 昇順の場合
	public static final String SORT_ASC = "0";
	
	// 降順の場合
	public static final String SORT_DESC = "1";
	
}