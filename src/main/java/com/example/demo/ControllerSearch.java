package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ControllerSearch{
	
	// 共通処理インスタンス化
    @Autowired
    private CommonDB db;
    
    // Service側インスタンス化
 	@Autowired
     private ServiceSearch service;
	
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
		
		// IDの桁数チェック
		int idCheck = db.idCheck(searchData.getSearchId());
	
		if(idCheck == BihinConst.INPUT_NG) {
			String message = "IDは3桁以内で入力してください";
			model.addAttribute("message_id", message);
			input = BihinConst.INPUT_NG;
		}
		
		// IDが数字のみかチェック
		int idNumCheck = db.idNumCheck(searchData.getSearchId());
		if(idNumCheck == BihinConst.INPUT_NG ) {
			String message = "IDは全て数字で入力してください";
			model.addAttribute("message_id_num", message);
			input = BihinConst.INPUT_NG;
		}
	
		// 備品名の文字数チェック
		int nameCheck = db.nameCheck(searchData.getSearchName());
		
		if(nameCheck == BihinConst.INPUT_NG) {
			String message = "備品名は30文字以内で入力してください";
			model.addAttribute("message_name", message);
			input = BihinConst.INPUT_NG;
		}
		
		// 使用回数の桁数チェック
		int useCheck = db.useCheck(searchData.getSearchUseNum());
		
		if(useCheck == BihinConst.INPUT_NG) {
			String message = "使用回数は3桁以内で入力してください";
			model.addAttribute("message_use", message);
			input = BihinConst.INPUT_NG;
		}
		
		// 使用回数が数字のみかチェック
		int useNumCheck = db.useNumCheck(searchData.getSearchUseNum());
		
		if(useNumCheck == BihinConst.INPUT_NG) {
			String message = "使用回数は全て数字で入力してください";
			model.addAttribute("message_use_num", message);
			input = BihinConst.INPUT_NG;
		}
		
		// ラジオボタンが押されていない場合
		if(!(searchData.getSearchUseNum() == "") && searchData.getSearchUseRadio() == null) {
			String message = "「以上」か「以下」か選んでください";
			model.addAttribute("message_use_nopush", message);
			input = BihinConst.INPUT_NG;	
		}
		
		// ラジオボタンのみ押されている場合
		if(searchData.getSearchUseNum() == "" && !(searchData.getSearchUseRadio() == null)) {
			String message = "使用回数を入力してください";
			model.addAttribute("message_use_push", message);
			input = BihinConst.INPUT_NG;	
		}
		
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
				String message = "該当する備品がありませんでした";
				model.addAttribute("list_message", message);
			}else {
				// 該当する備品一覧を設定する
				model.addAttribute("datalist", searchedList);
			}	
		}else {
			// メッセージを設定する
			String message = "検索条件に不備があります";
			model.addAttribute("search_message", message);
			model.addAttribute("datalist", new ArrayList<BihinData>());
		}
	return "search";
	}
}
	