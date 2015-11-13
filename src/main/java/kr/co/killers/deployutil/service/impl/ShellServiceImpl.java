package kr.co.killers.deployutil.service.impl;

import java.io.File;
import java.util.Map;

import org.h2.store.fs.FileUtils;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import kr.co.killers.deployutil.service.ShellService;

@Service
public class ShellServiceImpl implements ShellService {

	@Override
	public void MakeShell(Map<String, String> makefileList) {
		LocalDate theDay = new LocalDate();
		String pattern = "yyyyMMdd";
		String today =theDay.toString(pattern);
		
		String dir = "C://temp" + File.separatorChar + "";
		FileUtils.createDirectories(dir);

	}

}
