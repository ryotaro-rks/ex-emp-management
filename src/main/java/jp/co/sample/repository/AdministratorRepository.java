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

import jp.co.sample.domain.Administrator;

/**
 * administratorsテーブル操作用リポジトリ.
 * 
 * @author ryotaro.seya
 *
 */
@Repository
public class AdministratorRepository {
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = new BeanPropertyRowMapper<>(
			Administrator.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLE_NAME = "administrators";

	/**
	 * 管理者をDBに登録する.
	 * 
	 * @param administrator 追加する管理者情報
	 */
	public void insert(Administrator administrator) {
		String sql = "insert into " + TABLE_NAME
				+ "(name, mail_address, password) values(:name, :mailAddress, :password)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		template.update(sql, param);
	}

	/**
	 * メールアドレスとパスワードから管理者情報を取得する.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 取得した管理者情報(存在しない場合はnullを返す)
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		String sql = "select id, name, mail_address, password from " + TABLE_NAME
				+ " where mail_address = :mailAddress and password = :password";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",
				password);
		List<Administrator> adminList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);

		if (adminList.size() == 0) {
			return null;
		}

		return adminList.get(0);
	}
}
