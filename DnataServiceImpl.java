package com.teleapps.DnataReportingTool.ServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.teleapps.DnataReportingTool.Dto.DnataDto;
import com.teleapps.DnataReportingTool.Dto.DnataDtoMonthly;
import com.teleapps.DnataReportingTool.Service.DnataService;

@Service
public class DnataServiceImpl implements DnataService {

	String falconApp = "Falcon";
	String omniApp = "Omni";
	String sql = null;

	private static final Logger logger = LogManager.getLogger(DnataServiceImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("deprecation")
	@Override
	public List<DnataDto> getDailyReports(String application, String startDate, String endDate, String reportType) {

		String report = "Daily";
	
		System.out.println("Entered the getDailyReports method");
		logger.info("Enterted the DnataServiceImpl class getDailyReports method");

		try {

			System.out.println("Entered the try block.");
			logger.info("Enterted the DnataServiceImpl class getDailyReports method try block.");

			if (application.equalsIgnoreCase(falconApp) && report.equals(reportType)) {

				System.out.println("Entered the if condition");
				logger.info("Enterted the DnataServiceImpl class getDailyReports method Falcon Application.");

				sql = "EXEC GetIVRReportForFalcon ?, ?, ?";
				return jdbcTemplate.query(sql, new Object[] { startDate, endDate, reportType }, (rs, rowNum) -> {
					DnataDto dto = new DnataDto();
					dto.setDate(rs.getString(1));
					System.out.println(rs.getString(1));
					dto.setOffered(rs.getInt(2));
					dto.setAnswered(rs.getInt(3));
					dto.setTransferred(rs.getInt(4));
					dto.setAvgHandlingTime(rs.getInt(5));
					return dto;
				});
			} else if (application.equalsIgnoreCase(omniApp) && report.equals(reportType)) {

				System.out.println("Entered the else if condition");
				logger.info("Enterted the DnataServiceImpl class getDailyReports method Omni Application.");

				sql = "EXEC GetIVRReportForOmini ?, ?, ?";

				return jdbcTemplate.query(sql, new Object[] { startDate, endDate, reportType }, (rs, rowNum) -> {
					DnataDto dto = new DnataDto();
					dto.setDate(rs.getString(1));
					System.out.println(rs.getString(1));
					dto.setOffered(rs.getInt(2));
					dto.setAnswered(rs.getInt(3));
					dto.setTransferred(rs.getInt(4));
					dto.setAvgHandlingTime(rs.getInt(5));
					return dto;
				});
			}

		} catch (DataAccessException ex) {

			System.out.println("Entered the catch exception");
			logger.error("Exception occured." + ex);

			// Handle database access exception
			ex.printStackTrace();
			System.out.println("The catch return statement result : " + Collections.emptyList());
			logger.info("The catch return statement result : " + Collections.emptyList());
			return Collections.emptyList();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public List<DnataDto> getMonthlyReports(String application, String startDate, String endDate, String reportType) {

		String report = "Monthly";
		logger.info("Enterted the DnataServiceImpl class getMonthlyReports method");

		try {

			System.out.println("Entered try block.");
			logger.info("Enterted the DnataServiceImpl class getMonthlyReports method try block.");

			if (application.equalsIgnoreCase(falconApp) && report.equals(reportType)) {

				System.out.println("Entered the if condition");
				logger.info("Enterted the DnataServiceImpl class getMonthlyReports method Falcon Application.");

				sql = "EXEC GetIVRReportForFalcon ?, ?, ?";

				return jdbcTemplate.query(sql, new Object[] { startDate, endDate, reportType }, (rs, rowNum) -> {
					DnataDto dto = new DnataDto();
					dto.setDate(rs.getString(1));
					System.out.println(rs.getString(1));
					dto.setOffered(rs.getInt(2));
					dto.setAnswered(rs.getInt(3));
					dto.setTransferred(rs.getInt(4));
					dto.setAvgHandlingTime(rs.getInt(5));
					return dto;
				});
				

			} else if (application.equalsIgnoreCase(omniApp) && report.equals(reportType)) {

				System.out.println("Entered the else if condition");
				logger.info("Enterted the DnataServiceImpl class getMonthlyReports method Omni Application.");
				sql = "EXEC GetIVRReportForOmini ?, ?, ?";

				return jdbcTemplate.query(sql, new Object[] { startDate, endDate, reportType }, (rs, rowNum) -> {
					DnataDto dto = new DnataDto();
					dto.setDate(rs.getString(1));
					System.out.println(rs.getString(1));
					dto.setOffered(rs.getInt(2));
					dto.setAnswered(rs.getInt(3));
					dto.setTransferred(rs.getInt(4));
					dto.setAvgHandlingTime(rs.getInt(5));
					return dto;
				});
			}

		} catch (EmptyResultDataAccessException ex) {

			System.out.println("Entered EmptyResultDataAccessException " + ex);
			logger.error("EmptyResultDataAccessException occured." + ex);

			// Handle database access exception
			ex.printStackTrace();
			return null;
		} catch (DataAccessException ex) {
			// Handle other data access exceptions
			System.out.println("Entered the DataAccessException" + ex);
			logger.error("DataAccessException occurred." + ex);
			ex.printStackTrace();
			return Collections.emptyList();
		}
		return null;
	}
}
