package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者情報用コントローラー.
 * 
 * @author ryotaro.seya
 *
 */
@Controller
@RequestMapping("")
public class AdministratorController {
	@Autowired
	private AdministratorService administratorService;

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

	/**
	 * 管理者情報を登録する.
	 * 
	 * @param form 管理者情報フォーム
	 * @return /ログイン画面へリダイレクト
	 */
	@RequestMapping("insert")
	public String insert(InsertAdministratorForm form) {
		Administrator admin = new Administrator();
		BeanUtils.copyProperties(form, admin);
		administratorService.insert(admin);
		return "redirect:/login";
	}

	@RequestMapping("login")
	public String login() {
		return "administrator/login";
	}
}
