package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

/**
 * 管理者情報用サービスクラス.
 * 
 * @author ryotaro.seya
 *
 */
@Service
@Transactional
public class AdministratorService {
	@Autowired
	private AdministratorRepository administratorRepository;

	/**
	 * 管理者情報を登録.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		administratorRepository.insert(administrator);
	}

	/**
	 * ログインする.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return パラメータ情報に一致する管理者情報
	 */
	public Administrator login(String mailAddress, String password) {
		return administratorRepository.findByMailAddressAndPassword(mailAddress, password);
	}
}
