/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.co.killers.deployutil.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.killers.deployutil.domain.Test;
import kr.co.killers.deployutil.param.TestParam;
import kr.co.killers.deployutil.service.TestService;

@Controller
public class WelcomeController {

	private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);

	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@Autowired
	private TestService testService;

//	@RequestMapping("/")
//	public String welcome(Map<String, Object> model) {
//		log.debug("welcome gogogogo");
//		model.put("time", new Date());
//		model.put("message", this.message);
////		return "svn";
//		return "welcome";
//	}

	@RequestMapping("/foo")
	public String foo(Map<String, Object> model) {
		throw new RuntimeException("Foo");
	}

	@RequestMapping("/findAll")
	public String findAll(Map<String, Object> model) {

		List<Test> page = testService.findAll();

		model.put("time", new Date());
		model.put("datas", page);
		return "list";
	}

	@RequestMapping("/findTestAll")
	public String findTestAll(Map<String, Object> model) {

		Pageable pageable = new PageRequest(0, 20);

		Page<Test> page = testService.findTestAll(pageable);

		model.put("time", new Date());
		model.put("datas", page);
		return "data";
	}

	@RequestMapping("/save")
	public String insert(Map<String, Object> model, @Valid TestParam valid) {
		log.debug("save start");
		model.put("datas", testService.save(valid));

		return "view";
	}
}
