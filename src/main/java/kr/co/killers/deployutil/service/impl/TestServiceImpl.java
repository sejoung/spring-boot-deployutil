package kr.co.killers.deployutil.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.killers.deployutil.domain.Test;
import kr.co.killers.deployutil.param.TestParam;
import kr.co.killers.deployutil.repositories.TestRepository;
import kr.co.killers.deployutil.service.TestService;

@Service("TestService")
public class TestServiceImpl implements TestService {

	@Autowired
	private TestRepository testRepository;

	@Override
	public Page<Test> findTestAll(Pageable pageable) {
		return testRepository.findAll(pageable);
	}

	@Override
	public List<Test> findAll() {
		return testRepository.findAll();
	}

	@Override
	public Test save(TestParam param) {
		Test test = new Test();
		test.setName(param.getName());
		return testRepository.save(test);
	}

}
