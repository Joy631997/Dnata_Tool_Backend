package com.teleapps.DnataReportingTool.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teleapps.DnataReportingTool.Dto.DnataDto;
import com.teleapps.DnataReportingTool.Dto.DnataDtoMonthly;
import com.teleapps.DnataReportingTool.Dto.JsonResponse;
import com.teleapps.DnataReportingTool.Service.DnataService;

@RestController
@RequestMapping("/api")
public class DnataController {

	private static final Logger logger = LogManager.getLogger(DnataController.class);

	@Autowired
	DnataService dnataService;

	// @CrossOrigin(origins = "http://127.0.0.1:5500")
	@CrossOrigin
	@GetMapping("/daily")
	public ResponseEntity<JsonResponse> getDailyReports(@RequestParam("application") String application,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("reportType") String reportType) {

		System.out.println("Entered the Daily API");
		System.out.println("Input from the request : " + application);
		System.out.println("Input from the request start date : " + startDate);
		System.out.println("Input from the request end date : " + endDate);
		System.out.println("Input from the request report type : " + reportType);

		List<DnataDto> reports = null;

		try {
			String falconApp = "Falcon";
			String omniApp = "Omni";
			String report = "Daily";

			String fromDateTimeRemoval[] = startDate.split(" ");
			System.out.println("From Date time removal after : " + fromDateTimeRemoval[0]);
			logger.info("From Date time removal after the Date format is : " + fromDateTimeRemoval[0]);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate fromDate = LocalDate.parse(fromDateTimeRemoval[0], formatter);
			System.out.println(fromDate);
			logger.info("Corrected start date format : " + fromDate);

			String toDateTimeRemoval[] = endDate.split(" ");
			LocalDate toDate = LocalDate.parse(toDateTimeRemoval[0], formatter);
			System.out.println("To date time removal after : " + toDate);
			logger.info("Corrected end date format : " + toDate);

			LocalDate currentDate = LocalDate.now();

			if ((!application.isEmpty() && application != null && !application.isBlank()
					&& (application.equalsIgnoreCase(falconApp) || application.equalsIgnoreCase(omniApp)))
					&& (!startDate.isEmpty() && startDate != null && !startDate.isBlank())
					&& (!endDate.isEmpty() && endDate != null && !endDate.isBlank()) && (!toDate.isBefore(fromDate))
//					&& (!toDate.isAfter(currentDate))
					&& (!reportType.isEmpty() && reportType != null && !reportType.isBlank())
					&& report.equals(reportType)) {
				System.out.println("Entered");
				logger.info("Entered the DnataController getDailyReports method.");

				reports = dnataService.getDailyReports(application, startDate, endDate, reportType);

				if (reports != null) {
					System.out.println(reports);
					logger.info("The result from the service " + reports);
					JsonResponse jsonResponse = new JsonResponse(200, "OK", "Got the Daily Reports Successfully",
							reports);
					logger.info("The response : " + jsonResponse);

					return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.OK);

				} else {
					System.out.println("Database failure");
					logger.error("Database failure");
					JsonResponse jsonResponse = new JsonResponse(500, "Error", "Internal server error occurred",
							reports);
					logger.info("The response : " + jsonResponse);

					return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);

				}
			} else if (toDate.isBefore(fromDate)) {
				System.out.println("Please verify the date is correct!");
				logger.error("Please verify the From date!.");
				JsonResponse jsonResponse = new JsonResponse(400, "Error", "Please verify the From date!", reports);
				logger.info("The response : " + jsonResponse);

				return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.BAD_REQUEST);

			} else if (toDate.isAfter(currentDate)) {
				System.out.println("Please verify the date is correct!");
				logger.error("Please verify the date is correct!.");
				JsonResponse jsonResponse = new JsonResponse(400, "Error", "Your To date is exceeds the current date!",
						reports);
				logger.info("The response : " + jsonResponse);

				return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.BAD_REQUEST);

			} else {
				System.out.println("Please send the correct request");
				logger.error("Please select and send the correct request with non-empty parameters.");
				JsonResponse jsonResponse = new JsonResponse(400, "Error",
						"Please select and send the correct request with non-empty parameters", reports);
				logger.info("The response : " + jsonResponse);

				return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (DateTimeParseException e) {
			System.out.println("Please send the correct request");
			logger.error("Please select the Date from the Date Picker.");
			JsonResponse jsonResponse = new JsonResponse(400, "Error", "Please select the Date from the Date Picker.",
					reports);
			logger.info("The response : " + jsonResponse);

			return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Exception occured " + e);
			System.out.println("Exception occured");
			JsonResponse jsonResponse = new JsonResponse(204, "Error",
					"No data found for the given parameters. Please select the valid dates", reports);
			logger.info("The response : " + jsonResponse);

			return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.NO_CONTENT);
		}

	}

	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@GetMapping("/monthly")
	public ResponseEntity<JsonResponse> getMonthlyReports(@RequestParam("application") String application,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("reportType") String reportType) {

		System.out.println("Entered the Monthly API");
		System.out.println("Input from the request : " + application);
		System.out.println("Input from the request start date : " + startDate);
		System.out.println("Input from the request end date : " + endDate);
		System.out.println("Input from the request report type : " + reportType);

		List<DnataDto> monthlyReports = null;

		try {

			String falconApp = "Falcon";
			String omniApp = "Omni";
			String report = "Monthly";

			String fromDateTimeRemoval[] = startDate.split(" ");
			System.out.println("From Date time removal after : " + fromDateTimeRemoval[0]);
			logger.info("From Date time removal after the Date format is : " + fromDateTimeRemoval[0]);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate fromDate = LocalDate.parse(fromDateTimeRemoval[0], formatter);
			System.out.println(fromDate);
			logger.info("Formatted after start date is : " + fromDate);

			String toDateTimeRemoval[] = endDate.split(" ");
			LocalDate toDate = LocalDate.parse(toDateTimeRemoval[0], formatter);
			System.out.println("To date time removal after : " + toDate);
			logger.info("Formatted after end date is : " + toDate);

			LocalDate currentDate = LocalDate.now();

			if ((!application.isEmpty() && application != null && !application.isBlank()
					&& (application.equalsIgnoreCase(falconApp) || application.equalsIgnoreCase(omniApp)))
					&& (!startDate.isEmpty() && startDate != null && !startDate.isBlank())
					&& (!endDate.isEmpty() && endDate != null && !endDate.isBlank()) && (!toDate.isBefore(fromDate))
//					&& (!toDate.isAfter(currentDate))
					&& (!reportType.isEmpty() && reportType != null && !reportType.isBlank())
					&& report.equals(reportType)) {

				monthlyReports = dnataService.getMonthlyReports(application, startDate, endDate, reportType);
				System.out.println("Monthly Reports Response Result : " + monthlyReports);

				if (monthlyReports != null) {
					System.out.println(monthlyReports);
					logger.info("The result from the service : " + monthlyReports);
					JsonResponse jsonResponse = new JsonResponse(200, "OK", "Got the Monthly Reports Successfully",
							monthlyReports);
					logger.info("The response : " + jsonResponse);
					return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.OK);

				} else {
					logger.error("Database failure");
					JsonResponse jsonResponse = new JsonResponse(500, "Error", "Internal server error occurred",
							monthlyReports);
					logger.info("The response : " + jsonResponse);
					return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
				}

			} else if (toDate.isBefore(fromDate)) {
				System.out.println("Please verify the date is correct!");
				logger.error("Please verify the From date!.");
				JsonResponse jsonResponse = new JsonResponse(400, "Error", "Please verify the From date!",
						monthlyReports);
				logger.info("The response : " + jsonResponse);

				return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.BAD_REQUEST);

			} else if (toDate.isAfter(currentDate)) {
				System.out.println("Please verify the date is correct!");
				logger.error("Please verify the date is correct!.");
				JsonResponse jsonResponse = new JsonResponse(400, "Error", "Your To date is exceeds the current date!",
						monthlyReports);
				logger.info("The response : " + jsonResponse);

				return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.BAD_REQUEST);

			} else {
				System.out.println("Please send the correct request");
				JsonResponse jsonResponse = new JsonResponse(400, "Error",
						"Please select and send the correct request with non-empty parameters", monthlyReports);
				logger.info("Please select and send the correct request with non-empty parameters");
				logger.info("The response : " + jsonResponse);
				return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.BAD_REQUEST);

			}
		} catch (DateTimeParseException e) {
			System.out.println("Please select the correct date from the Date Picker");
			logger.error("Please select the correct date from the Date Picker.");
			JsonResponse jsonResponse = new JsonResponse(400, "Error",
					"Please select the correct date from the Date Picker.", monthlyReports);
			logger.info("The response : " + jsonResponse);

			return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
			JsonResponse jsonResponse = new JsonResponse(204, "No content error",
					"No data found for the given parameters. Please select the valid dates", monthlyReports);
			logger.info("No data found for the given parameters. Please select the valid dates");
			logger.info("The response : " + jsonResponse);
			return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.NO_CONTENT);

		}
	}
	
	
	
	
//	@CrossOrigin(origins = "http://127.0.0.1:5500")
//	@GetMapping("/weekly")
//	public ResponseEntity<JsonResponse> getMonthlyReports(@RequestParam("application") String application,
//			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
//			@RequestParam("reportType") String reportType) {
//
//		System.out.println("Entered the Monthly API");
//		System.out.println("Input from the request : " + application);
//		System.out.println("Input from the request start date : " + startDate);
//		System.out.println("Input from the request end date : " + endDate);
//		System.out.println("Input from the request report type : " + reportType);
//
//		List<DnataDto> monthlyReports = null;
//
//		try {
//
//			String falconApp = "Falcon";
//			String omniApp = "Omni";
//			String report = "Monthly";
//
//			String fromDateTimeRemoval[] = startDate.split(" ");
//			System.out.println("From Date time removal after : " + fromDateTimeRemoval[0]);
//			logger.info("From Date time removal after the Date format is : " + fromDateTimeRemoval[0]);
//
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//			LocalDate fromDate = LocalDate.parse(fromDateTimeRemoval[0], formatter);
//			System.out.println(fromDate);
//			logger.info("Formatted after start date is : " + fromDate);
//
//			String toDateTimeRemoval[] = endDate.split(" ");
//			LocalDate toDate = LocalDate.parse(toDateTimeRemoval[0], formatter);
//			System.out.println("To date time removal after : " + toDate);
//			logger.info("Formatted after end date is : " + toDate);
//
//			LocalDate currentDate = LocalDate.now();
//
//			if ((!application.isEmpty() && application != null && !application.isBlank()
//					&& (application.equalsIgnoreCase(falconApp) || application.equalsIgnoreCase(omniApp)))
//					&& (!startDate.isEmpty() && startDate != null && !startDate.isBlank())
//					&& (!endDate.isEmpty() && endDate != null && !endDate.isBlank()) && (!toDate.isBefore(fromDate))
////					&& (!toDate.isAfter(currentDate))
//					&& (!reportType.isEmpty() && reportType != null && !reportType.isBlank())
//					&& report.equals(reportType)) {
//
//				monthlyReports = dnataService.getMonthlyReports(application, startDate, endDate, reportType);
//				System.out.println("Monthly Reports Response Result : " + monthlyReports);
//
//				if (monthlyReports != null) {
//					System.out.println(monthlyReports);
//					logger.info("The result from the service : " + monthlyReports);
//					JsonResponse jsonResponse = new JsonResponse(200, "OK", "Got the Monthly Reports Successfully",
//							monthlyReports);
//					logger.info("The response : " + jsonResponse);
//					return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.OK);
//
//				} else {
//					logger.error("Database failure");
//					JsonResponse jsonResponse = new JsonResponse(500, "Error", "Internal server error occurred",
//							monthlyReports);
//					logger.info("The response : " + jsonResponse);
//					return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//				}
//
//			} else if (toDate.isBefore(fromDate)) {
//				System.out.println("Please verify the date is correct!");
//				logger.error("Please verify the From date!.");
//				JsonResponse jsonResponse = new JsonResponse(400, "Error", "Please verify the From date!",
//						monthlyReports);
//				logger.info("The response : " + jsonResponse);
//
//				return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.BAD_REQUEST);
//
//			} else if (toDate.isAfter(currentDate)) {
//				System.out.println("Please verify the date is correct!");
//				logger.error("Please verify the date is correct!.");
//				JsonResponse jsonResponse = new JsonResponse(400, "Error", "Your To date is exceeds the current date!",
//						monthlyReports);
//				logger.info("The response : " + jsonResponse);
//
//				return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.BAD_REQUEST);
//
//			} else {
//				System.out.println("Please send the correct request");
//				JsonResponse jsonResponse = new JsonResponse(400, "Error",
//						"Please select and send the correct request with non-empty parameters", monthlyReports);
//				logger.info("Please select and send the correct request with non-empty parameters");
//				logger.info("The response : " + jsonResponse);
//				return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.BAD_REQUEST);
//
//			}
//		} catch (DateTimeParseException e) {
//			System.out.println("Please select the correct date from the Date Picker");
//			logger.error("Please select the correct date from the Date Picker.");
//			JsonResponse jsonResponse = new JsonResponse(400, "Error",
//					"Please select the correct date from the Date Picker.", monthlyReports);
//			logger.info("The response : " + jsonResponse);
//
//			return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			System.out.println("Exception occured" + e);
//			JsonResponse jsonResponse = new JsonResponse(204, "No content error",
//					"No data found for the given parameters. Please select the valid dates", monthlyReports);
//			logger.info("No data found for the given parameters. Please select the valid dates");
//			logger.info("The response : " + jsonResponse);
//			return new ResponseEntity<JsonResponse>(jsonResponse, HttpStatus.NO_CONTENT);
//
//		}
//	}


}
