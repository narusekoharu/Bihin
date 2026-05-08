package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// 検索画面
@Controller
public class ControllerSearch{
    
    // Service側インスタンス化
 	@Autowired
     private ServiceSearch service;
 	
 	// 共通化処理インスタンス化
    @Autowired
    private  CommonDB db;
	
	 // 初期画面
	@GetMapping("/search")
	public String start(Model model) {
		
		model.addAttribute("searchData", new SearchData());
		
		return "search";
	}
	
	// 検索処理
	@PostMapping(path = "/search", params ="searchButton")
	public String searchBihin(@ModelAttribute("searchData") SearchData searchData,
							  Model model) {

		// 入力チェックを示す変数
		int input = BihinConst.INPUT_OK;
		
		// エラーメッセージ用
		List<String> errorMessage = new ArrayList<String>();

		// IDの桁数チェック
		int idCheck = service.lengthCheck(searchData.getSearchId(), BihinConst.ID_LENGTH);
		if(idCheck == BihinConst.INPUT_NG) {
			errorMessage.add(MessageConst.ID_CHECK);
			input = BihinConst.INPUT_NG;
		}
		
		// IDが数字のみかチェック
		int idNumCheck = service.numCheck(searchData.getSearchId());
		if(idNumCheck == BihinConst.INPUT_NG ) {
			errorMessage.add(MessageConst.ID_NUM_CHECK);
			input = BihinConst.INPUT_NG;
		}
	
		// 備品名の文字数チェック
		int nameCheck = service.lengthCheck(searchData.getSearchName(), BihinConst.NAME_LENGTH);
		if(nameCheck == BihinConst.INPUT_NG) {
			errorMessage.add(MessageConst.NAME_CHECK);
			input = BihinConst.INPUT_NG;
		}
		
		// 使用回数の桁数チェック
		int useCheck = service.lengthCheck(searchData.getSearchUseNum(), BihinConst.USE_LENGTH);
		if(useCheck == BihinConst.INPUT_NG) {
			errorMessage.add(MessageConst.USE_CHECK);
			input = BihinConst.INPUT_NG;
		}
		
		// 使用回数が数字のみかチェック
		int useNumCheck = service.numCheck(searchData.getSearchUseNum());	
		if(useNumCheck == BihinConst.INPUT_NG) {
			errorMessage.add(MessageConst.USE_NUM_CHECK);
			input = BihinConst.INPUT_NG;
		}
		
		// ラジオボタンが押されていない場合
		if(!(searchData.getSearchUseNum() == "") && searchData.getSearchUseRadio() == null) {
			errorMessage.add(MessageConst.USE_RADIO_CHECK);
			input = BihinConst.INPUT_NG;	
		}
		
		// ラジオボタンのみ押されている場合
		if(searchData.getSearchUseNum() == "" && !(searchData.getSearchUseRadio() == null)) {
			errorMessage.add(MessageConst.USE_NO_NUM_CHECK);
			input = BihinConst.INPUT_NG;	
		}
		
		// エラーメッセージを設定する
		model.addAttribute("error_message", errorMessage);
		
		// 検索条件が正しい場合、リストを受け取る。
		if(input == BihinConst.INPUT_OK) {
			// リストを受け取る
			List<BihinData> searchedList = service.searchBihin(searchData.getSearchId(),
															   searchData.getSearchName(), 
															   searchData.getSearchRentalTrue(), 
															   searchData.getSearchRentalFalse(), 
															   searchData.getSearchUseNum(), 
															   searchData.getSearchUseRadio());
			model.addAttribute("datalist", searchedList);
			
			// リストが空か判別する
			if(searchedList.isEmpty()) {
				// 空の場合メッセージを設定する
				String message = MessageConst.BIHIN_EMPTY;
				model.addAttribute("list_message", message);
			}else {
				// 該当する備品一覧を設定する
				model.addAttribute("datalist", searchedList);
			}	
		}else {
			// メッセージを設定する
			String message = MessageConst.BIHIN_NG;
			model.addAttribute("search_message", message);
			model.addAttribute("datalist", new ArrayList<BihinData>());
		}
		
		// DBを閉じる
		db.databaseClose();	
		
		return "search";
	}
}
	