package kr.co.killers.deployutil.service.impl;

import java.io.File;
import java.util.Map;

import org.h2.store.fs.FileUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import kr.co.killers.deployutil.service.ShellService;

@Service
public class ShellServiceImpl implements ShellService {

	@Override
	public void MakeShell(Map<String, String> makefileList) {
		DateTime dt = new DateTime();

		
		String dir = "C://temp" + File.separatorChar + "";
		FileUtils.createDirectories(dir);

	}

}
