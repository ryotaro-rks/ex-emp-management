package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
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

	@Autowired
	private HttpSession session;

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
	 * フォームからオブジェクトへの変換.
	 * 
	 * @return LoginFormオブジェクト
	 */
	@ModelAttribute
	private LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/**
	 * 管理者情報登録画面へ遷移する.
	 * 
	 * @return 管理者情報登録画面へフォワード
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
		return "redirect:";
	}

	/**
	 * 管理者ログイン画面へ遷移する.
	 * 
	 * @return 管理者ログイン画面へのフォワード
	 */
	@RequestMapping("")
	public String toLogin() {
		return "administrator/login";
	}

	/**
	 * 管理者用ログイン.
	 * 
	 * @param form  ログイン用フォーム
	 * @param model エラーメッセージ格納用モデル
	 * @return 従業員一覧画面へフォワード
	 */
	@RequestMapping("login")
	public String login(LoginForm form, Model model) {
		Administrator admin = administratorService.login(form.getMailAddress(), form.getPassword());
		if (admin == null) {
			model.addAttribute("msg", "メールアドレスまたはパスワードが不正です");
			return "administrator/login";
		}

		session.setAttribute("administratorName", admin.getName());
		return "forward:/employee/showList";
	}
}
