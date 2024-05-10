package com.teleapps.DnataReportingTool.Interceptor;

//import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoggingInterceptor implements HandlerInterceptor {

	private static final Logger logger = LogManager.getLogger(LoggingInterceptor.class);

	@Autowired
	private Environment environment;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// Log request information
		logger.info("Request URL: {} ", request.getRequestURL());
		logger.info("Request Method: {} ", request.getMethod());
		System.out.println("Request URL: " + request.getRequestURL());
		System.out.println("Request Method: " + request.getMethod());
		// You can log additional information such as request headers, parameters, etc.

		try {
			String databaseUrl = environment.getProperty("spring.datasource.url");
			String username = environment.getProperty("spring.datasource.username");
			String password = environment.getProperty("spring.datasource.password");
			String driver = environment.getProperty("spring.datasource.driver-class-name");
			String size = environment.getProperty("spring.datasource.hikari.maximum-pool-size");

			logger.info("Property Value from application.properties-databaseUrl : {}", databaseUrl);
			logger.info("Property Value from application.properties-username : {}", username);
			logger.info("Property Value from application.properties-password : {}", password);
			logger.info("Property Value from application.properties-driver : {}", driver);
			logger.info("Property Value from application.properties-size : {}", size);
			System.out.println("Property Value from application.properties: " + databaseUrl);
		} catch (CannotGetJdbcConnectionException exception) {
			logger.error("Failed to connect to the database: {}", exception.getMessage());
			System.out.println("Exception is "+exception);
		} catch (Exception exception) {
			logger.error("Failed to connect to the database: {}", exception.getMessage());
			System.out.println("Exception is "+exception);
		}

		return true; // Continue processing the request
	}
}
