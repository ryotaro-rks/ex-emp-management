package jp.co.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@RequestMapping("update")
	public String update(UpdateEmployeeForm form) {
		Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}
}
