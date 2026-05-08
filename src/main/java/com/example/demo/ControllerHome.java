package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// ホーム画面
@Controller
public class ControllerHome {
	
	// Service側インスタンス化
	@Autowired
    private ServiceHome service;

	// 共通処理インスタンス化
    @Autowired
    private CommonDB db;

    // 初期画面
	@GetMapping("/home")
	public String start(@RequestParam(value = "hidden_nowSort", defaultValue = "1") String nowSort,
						@RequestParam(value = "hidden_beforeSort", defaultValue = "id") String beforeSort,
						Model model) {
		
	    model.addAttribute("nowSort", nowSort);
	    model.addAttribute("beforeSort", beforeSort);
	    
	    model.addAttribute("datalist", service.bihinList());
	
		return "home";
	}
	
	// 登録処理
	@PostMapping(path = "/home", params = "register")
	public String register(@RequestParam("name") String name,
						   Model model) {
		
		String message = "";
		
		// 文字数バリデーション
		int validationResult = service.lengthCheck(name);
		
		// 31文字以上ならメッセージを定める
		if(validationResult == BihinConst.CHECK_INPUT_NG) {
			message = MessageConst.NAME_NUM_NG;
		}

		// バリデーションが通れば、登録処理を呼び出す
		if(validationResult == BihinConst.CHECK_OK) {
			int result = service.register(name);
			
			// 状況に応じてメッセージを定める
			switch(result) {
				case BihinConst.RESULT_OK:
					message = MessageConst.RESULT_OK_REGISTER;
					break;
				case BihinConst.RESULT_ERROR:
					message = MessageConst.RESULT_ERROR_MESSAGE;
					break;
			}
		}
		
		// メッセージ設定
		model.addAttribute("message", message);	
		
		// 備品一覧を設定
		model.addAttribute("datalist", service.bihinList());
					
		// DBを閉じる
		db.databaseClose();
		
		return "home";
		}
	
	// 貸出処理
	@PostMapping(path = "/home", params = "rental")
	public String rental(@RequestParam("name") String name,
						 Model model) {
		
		String message = "";
		
		// バリデーションチェック
		int validationResult = service.registerCheck(name);
		
		// 状況に応じてメッセージを定める
		switch(validationResult) {
			case BihinConst.CHECK_INPUT_NG:
				message = MessageConst.NAME_NUM_NG;
				break;
			case BihinConst.CHECK_REGISTER_NG:
				message = MessageConst.REGISTER_NG;
				break;			
		}
		
		// バリデーションが通れば、貸出処理を呼び出す
		if(validationResult == BihinConst.CHECK_OK) {
			int result = service.rental(name);
			
			// 状況に応じてメッセージを定める
			switch(result) {
				case BihinConst.RESULT_OK:
					message = MessageConst.RESULT_OK_RENTAL;
					break;
				case BihinConst.RESULT_NG:
					message = MessageConst.RESULT_NG_RENTAL;
					break;
				case BihinConst.RESULT_ERROR:
					message = MessageConst.RESULT_ERROR_MESSAGE;
			}
		}
		
		// メッセージ設定
		model.addAttribute("message", message);
		
		// 備品一覧を設定
		model.addAttribute("datalist", service.bihinList());
		
		// DBを閉じる
		db.databaseClose();
		
		return "home";
	}
	
	// 返却処理
	@PostMapping(path = "/home", params = "bihinReturn")
	public String bihinReturn(@RequestParam("name") String name,
							  Model model) {

		String message = "";
		
		// バリデーションチェック
		int validationResult = service.registerCheck(name);
		
		// 状況に応じてメッセージを定める
		switch(validationResult) {
			case BihinConst.CHECK_INPUT_NG:
				message = MessageConst.NAME_NUM_NG;
				break;
			case BihinConst.CHECK_REGISTER_NG:
				message = MessageConst.REGISTER_NG;
				break;			
		}
		
		// バリデーションが通れば、返却処理を呼び出す
		if(validationResult == BihinConst.CHECK_OK) {
			int result = service.bihinReturn(name);
			
			// 状況に応じてメッセージを定める
			switch(result) {
				case BihinConst.RESULT_OK:
					message = MessageConst.RESULT_OK_RETURN;
					break;
				case BihinConst.RESULT_NG:
					message = MessageConst.RESULT_NG_RETURN;
					break;
				case BihinConst.RESULT_ERROR:
					message = MessageConst.RESULT_ERROR_MESSAGE;
			}
		}
		
		// メッセージ設定
		model.addAttribute("message", message);
		
		// 備品一覧を設定
		model.addAttribute("datalist", service.bihinList());
		
		// DBを閉じる
		db.databaseClose();
		
		return "home";
	}
	
	// 削除処理
	@PostMapping(path = "/home", params = "delete")
	public String delete(@RequestParam("name") String name,
						 Model model) {
		
		String message = "";
		
		// バリデーションチェック
		int validationResult = service.registerCheck(name);
		
		// 状況に応じてメッセージを定める
		switch(validationResult) {
			case BihinConst.CHECK_INPUT_NG:
				message = MessageConst.NAME_NUM_NG;
				break;
			case BihinConst.CHECK_REGISTER_NG:
				message = MessageConst.REGISTER_NG;
				break;			
		}
		
		// バリデーションが通れば、削除処理を呼び出す
		if(validationResult == BihinConst.CHECK_OK) {
			int result = service.delete(name);
			
			// 状況に応じてメッセージを定める
			switch(result) {
				case BihinConst.RESULT_OK:
					message = MessageConst.RESULT_OK_DELETE;
					break;
				case BihinConst.RESULT_NG:
					message = MessageConst.RESULT_NG_DELETE;
					break;
				case BihinConst.RESULT_ERROR:
					message = MessageConst.RESULT_ERROR_MESSAGE;
			}
		}
		
		// メッセージ設定
		model.addAttribute("message", message);
				
		// 備品一覧を設定
		model.addAttribute("datalist", service.bihinList());
			
		// DBを閉じる
		db.databaseClose();
				
		return "home";
	}
	
	// 並び替え処理
	@PostMapping(path = "/home", params = "sort")
	public String sortBihin(@RequestParam("hidden_nowSort") String nowSort,
							@RequestParam("hidden_beforeSort") String beforeSort,
							@RequestParam("sort") String sort, 
							Model model) {
		
		// 前回と同じボタンだった場合
		if(sort.equals(beforeSort)) {
					
			// nowSortの状態を変える
			if(BihinConst.SORT_ASC.equals(nowSort)) {
				nowSort = BihinConst.SORT_DESC;
			}else if(BihinConst.SORT_DESC.equals(nowSort)) {
				nowSort = BihinConst.SORT_ASC;
			}
		//  違うボタンだった場合
		}else {
			nowSort = BihinConst.SORT_DESC;
		}
		
		// 次回用にnowSortを設定する
		model.addAttribute("nowSort", nowSort);
		
		// 次回用にbeforSortを設定する
		model.addAttribute("beforeSort", beforeSort = sort);
		
		//並び替え処理を呼び出し、リストを受け取り設定する
		model.addAttribute("datalist", service.sortColumn(sort, nowSort));
		
		// DBを閉じる
		db.databaseClose();	
		return "home";
	}
}

