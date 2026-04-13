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
	@PostMapping(path = "/home", params = "register")
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
	@PostMapping(path = "/home", params = "rental")
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
	@PostMapping(path = "/home", params = "bihinReturn")
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
	@PostMapping(path = "/home", params = "delete")
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
			
		// 初回idボタンだった場合
		}else if(sort.equals("id") && nowSort.equals("")) {
			nowSort = BihinConst.SORT_ASC;
		
		// 初回id以外の初回ボタンだった場合	
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

