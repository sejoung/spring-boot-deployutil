package kr.co.killers.deployutil.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.killers.deployutil.domain.Test;
import kr.co.killers.deployutil.param.TestParam;


public interface TestService {

	public Page<Test> findTestAll(Pageable pageable);
	
	public List<Test> findAll();
	
	
	public Test save(TestParam param);
	
}
