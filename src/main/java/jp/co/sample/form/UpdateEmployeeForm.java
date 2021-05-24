package jp.co.sample.form;

import javax.validation.constraints.NotBlank;

/**
 * 従業員情報更新用フォーム.
 * 
 * @author ryotaro.seya
 *
 */
public class UpdateEmployeeForm {
	@NotBlank(message = "idが空になっています")
	private String id;
	@NotBlank(message = "扶養人数が空になっています")
	private String dependentsCount;

	@Override
	public String toString() {
		return "UpdateEmployeeForm [id=" + id + ", dependentsCount=" + dependentsCount + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

}
