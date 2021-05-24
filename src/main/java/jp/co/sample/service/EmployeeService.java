package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * 従業員用サービスクラス.
 * 
 * @author ryotaro.seya
 *
 */
@Service
@Transactional
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員情報を全件取得.
	 * 
	 * @return 従業員リスト
	 */
	public List<Employee> showList() {
		return employeeRepository.findAll();
	}

	/**
	 * 従業員詳細検索.
	 * 
	 * @param id 検索id
	 * @return 従業員情報
	 */
	public Employee showDetail(Integer id) {
		return employeeRepository.load(id);
	}

	/**
	 * 従業員情報更新.
	 * 
	 * @param employee 更新する従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}

	public List<Employee> showListLimit(Integer pageNumber) {
		final Integer LIMIT = 10;
		Integer offSet = (LIMIT * (pageNumber - 1) + 1);
		return employeeRepository.findAllLimit(LIMIT, offSet);
	}

	public Integer getPageNumbers() {
		final Integer LIMIT = 10;
		return employeeRepository.getCountTable() / LIMIT + 1;
	}
}
