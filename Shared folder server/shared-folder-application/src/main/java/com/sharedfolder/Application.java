package com.sharedfolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@SpringBootApplication()
@Configuration
public class Application extends SpringBootServletInitializer {
	public void run() {
		ApplicationContext ctx = SpringApplication.run(Application.class, new String[] {});
	}
	
	public static void main(String[] args) {
		Application app = new Application();
		app.run();
	}
	
	@Value("${mysql.connection.url}")
	private String databaseURL;
	
	@Value("${mysql.username}")
	private String databaseUsername;
	
	@Value("${mysql.password}")
	private String databasePassword;
	
	
	@Bean
	@Scope("singleton")
	public DatabaseConnection databaseConnection() {
		return new DatabaseConnection(this.databaseURL,this.databaseUsername,this.databasePassword);
	}
	
	@Bean
	@Autowired
	public DatabaseOperation databaseOperation(DatabaseConnection dc) {
		return new DatabaseOperation(dc);
	}
	
	
	@Override  
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
		return application.sources(Application.class);  
	}  
}
