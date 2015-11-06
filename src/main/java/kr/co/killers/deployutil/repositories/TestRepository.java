package kr.co.killers.deployutil.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import kr.co.killers.deployutil.domain.Test;

public interface TestRepository extends Repository<Test, Long> {

	Page<Test> findAll(Pageable pageable);
	List<Test> findAll();
}
