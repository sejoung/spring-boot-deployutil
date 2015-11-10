package kr.co.killers.deployutil.param;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class TestParam {

	@NotEmpty
	private String name;

}
