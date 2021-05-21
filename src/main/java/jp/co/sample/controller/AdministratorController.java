package jp.co.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.form.InsertAdministratorForm;

/**
 * 管理者情報用コントローラー.
 * 
 * @author ryotaro.seya
 *
 */
@Controller
@RequestMapping("")
public class AdministratorController {
	/**
	 * フォームからオブジェクトへの変換.
	 * 
	 * @return InsertAdministratorFormオブジェクト
	 */
	@ModelAttribute
	private InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	/**
	 * @return insert.htmlへフォワード
	 */
	@RequestMapping("toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
}
