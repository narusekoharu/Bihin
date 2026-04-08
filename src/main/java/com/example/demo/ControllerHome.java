package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String start(Model model) {
		
		model.addAttribute("datalist", db.bihinList());

		return "home";
	}
	
	// 登録処理
	@PostMapping(path = "/home", params = "action=register")
	public String register(@RequestParam("name") String name,
						   Model model) {
		
		//登録処理を呼び出して、戻り値EXISTを受け取る
		int result = service.register(name);
			
		// 状況に応じてメッセージを設定する
		if(result == BihinConst.RESULT_OK) {
			String message = "登録が完了しました";
			model.addAttribute("message", message);
		
		} else if(result == BihinConst.RESULT_NUM) {
			String message = "30文字以内で入力してください";
			model.addAttribute("message", message);
			
		} else if(result == BihinConst.RESULT_ERROR) {
			String message = "予期せぬエラーが発生しました";
			model.addAttribute("message", message);
		}
		
		// 備品一覧を設定
		model.addAttribute("datalist", db.bihinList());
					
		// DBを閉じる
		db.databaseClose();
		
		return "home";
		}
	
	// 貸出処理
	@PostMapping(path = "/home", params = "action=rental")
	public String rental(@RequestParam("name") String name,
						 Model model) {
		
		// 貸出処理を呼び出して、戻り値EXISTを受け取る
		int result = service.rental(name);
		
		// 状況に応じてメッセージを設定する
		if(result == BihinConst.RESULT_OK) {
			String message = "貸出しました";
			model.addAttribute("message", message);
			
		} else if(result == BihinConst.RESULT_NG) {
			String message = "貸出せません";
			model.addAttribute("message", message);
		
		} else if(result == BihinConst.RESULT_NUM) {
			String message = "30文字以内で入力してください";
			model.addAttribute("message", message);
			
		} else if(result == BihinConst.RESULT_ERROR) {
			String message = "予期せぬエラーが発生しました";
			model.addAttribute("message", message);
		}
		
		// 備品一覧を設定
		model.addAttribute("datalist", db.bihinList());
		
		// DBを閉じる
		db.databaseClose();
		
		return "home";
	}
	
	// 返却処理
	@PostMapping(path = "/home", params = "action=bihinReturn")
	public String bihinReturn(@RequestParam("name") String name,
							  Model model) {
		
		// 返却処理を呼び出して、戻り値EXISTを受け取る
		int result = service.bihinReturn(name);
		
		// 状況に応じてメッセージを設定する
		if(result == BihinConst.RESULT_OK) {
			String message = "返却しました";
			model.addAttribute("message", message);
			
		} else if(result == BihinConst.RESULT_NG) {
			String message = "返却できません";
			model.addAttribute("message", message);
			
		} else if(result == BihinConst.RESULT_NUM) {
			String message = "30文字以内で入力してください";
			model.addAttribute("message", message);
			
		} else if(result == BihinConst.RESULT_ERROR) {
			String message = "予期せぬエラーが発生しました";
			model.addAttribute("message", message);
		}
		
		// 備品一覧を設定
		model.addAttribute("datalist", db.bihinList());
		
		// DBを閉じる
		db.databaseClose();
		
		return "home";
	}
	
	// 削除処理
	@PostMapping(path = "/home", params = "action=delete")
	public String delete(@RequestParam("name") String name,
						 Model model) {
		
		// 返却処理を呼び出して、戻り値EXISTを受け取る
		int result = service.delete(name);
		
		// 状況に応じてメッセージを設定する
		if(result == BihinConst.RESULT_OK) {
			String message = "削除しました";
			model.addAttribute("message", message);
			
		} else if(result == BihinConst.RESULT_NG) {
			String message = "削除できません";
			model.addAttribute("message", message);
			
		} else if(result == BihinConst.RESULT_NUM) {
			String message = "30文字以内で入力してください";
			model.addAttribute("message", message);
			
		} else if(result == BihinConst.RESULT_ERROR) {
			String message = "予期せぬエラーが発生しました";
			model.addAttribute("message", message);
		}
				
		// 備品一覧を設定
		model.addAttribute("datalist", db.bihinList());
			
		// DBを閉じる
		db.databaseClose();
				
		return "home";
		
	}
}

