package com.teleapps.DnataReportingTool.Service;

import java.util.List;

import com.teleapps.DnataReportingTool.Dto.DnataDto;
import com.teleapps.DnataReportingTool.Dto.DnataDtoMonthly;

public interface DnataService {

	 List<DnataDto> getDailyReports(String application, String startDate, String endDate, String reportType);
	 
	 List<DnataDto> getMonthlyReports(String application, String startDate, String endDate, String reportType);
}
