
package kr.co.killers.deployutil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class DeployUtilWebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DeployUtilWebApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DeployUtilWebApplication.class, args);
	}

}
