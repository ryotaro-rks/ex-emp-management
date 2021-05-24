package jp.co.sample.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報コントロールクラス.
 * 
 * @author ryotaro.seya
 *
 */
@Controller
@RequestMapping("employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@ModelAttribute
	private UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	/**
	 * 従業員情報一覧表示.
	 * 
	 * @param model リクエストスコープ用
	 * @return employee/list.htmlへのフォワード
	 */
	@RequestMapping("showList")
	public String showList(Model model) {
		model.addAttribute("employeeList", employeeService.showList());
		return "employee/list";
	}

	/**
	 * 従業員情報の詳細表示.
	 * 
	 * @param id    検索id
	 * @param model 従業員用リクエストスコープ
	 * @return detail.htmlへのフォワード
	 */
	@RequestMapping("showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/**
	 * 従業員更新.
	 * 
	 * @param form   従業員情報用スコープ
	 * @param result エラー格納
	 * @return 従業員リストへのリダイレクト
	 */
	@RequestMapping("update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.out.println("エラー");
			return this.showDetail(form.getId(), model);
		}

		Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

	/**
	 * 指定した数だけ従業員情報を取得. 一覧画面遷移時と画面遷移ボタンを押下際に呼ばれる.
	 * 
	 * @param pageNumber 画面番号. offsetの役割を持つ.
	 * @param model      従業員情報用スコープ
	 * @return 従業員情報一覧ページへのフォワード
	 */
	@RequestMapping("showListLimit")
	public String showListLimit(Integer pageNumber, Model model) {
		model.addAttribute("employeeList", employeeService.showListLimit(pageNumber));
		List<Integer> buttonNumbers = new ArrayList<>();
		for (int i = 1; i <= employeeService.getPageNumbers(); i++) {
			buttonNumbers.add(i);
		}
		model.addAttribute("buttonNumbers", buttonNumbers);
		return "employee/list";
	}
}
