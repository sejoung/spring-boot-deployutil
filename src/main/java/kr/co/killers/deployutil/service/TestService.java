package kr.co.killers.deployutil.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.killers.deployutil.domain.Test;


public interface TestService {

	public Page<Test> findTestAll(Pageable pageable);
	
	public List<Test> findAll();
	
}
