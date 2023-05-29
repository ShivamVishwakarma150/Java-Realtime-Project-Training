package com.shivam.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shivam.request.SearchRequest;
import com.shivam.response.SearchResponse;

public interface ReportService {
	public List<String> getUniquePlanNames();
	
	public List<String> getUniquePlanStatuses();
	
	public List<SearchResponse> search(SearchRequest request);
	
	public void generateExcel(HttpServletResponse response)throws Exception;
	
	public void generatePdf(HttpServletResponse response) throws Exception;
	
	
	
}
