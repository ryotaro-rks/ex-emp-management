package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * 従業員情報の取得&更新用リポジトリ
 * 
 * @author ryotaro.seya
 *
 */
@Repository
public class EmployeeRepository {
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = new BeanPropertyRowMapper<>(Employee.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLE_NAME = "employees";

	private static final String ALL_COLUMN_NAME = "id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count";

	/**
	 * 従業員一覧情報を入社日順(降順)で取得する 従業員が存在しない場合はサイズ0件のリストを返す
	 * 
	 * @return
	 */
	public List<Employee> findAll() {
		String sql = "select " + ALL_COLUMN_NAME + " from " + TABLE_NAME + " order by hireDate desc";
		return template.query(sql, EMPLOYEE_ROW_MAPPER);
	}

	/**
	 * 主キーから従業員情報を取得
	 * 
	 * @param id 従業員id 主キー
	 * @return 従業員情報
	 */
	public Employee load(Integer id) {
		String sql = "select " + ALL_COLUMN_NAME + " from " + TABLE_NAME + " where id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		// 従業員が存在しない場合, 自動で例外を吐き出す
		try {
			return template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		} catch (Exception e) {
			throw new RuntimeException("従業員が存在しませんでした", e);
		}
	}

	/**
	 * 従業員情報を変更する
	 * 
	 * @param employee
	 */
	public void update(Employee employee) {
		StringBuilder sql = new StringBuilder();
		sql.append("update " + TABLE_NAME);
		sql.append(" set ");
		String[] allColumnList = ALL_COLUMN_NAME.split(",");
		for (String column : allColumnList) {
			if (column.equals(allColumnList[allColumnList.length - 1])) {
				sql.append(column + " = :" + column);
			} else {
				sql.append(column + " = :" + column + ",");
			}
		}
		sql.append(" where id = :id");
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		template.update(sql.toString(), param);
	}
}
