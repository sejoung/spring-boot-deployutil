package kr.co.killers.deployutil.param;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class TestParam {

	private String name;

	private String svnUrl;

	@NotEmpty
	private String svnId;

	@NotEmpty
	private String svnPassword;

}
