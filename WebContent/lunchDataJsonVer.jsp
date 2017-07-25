<%@page import="util.crawler.Crawler"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	String strDate = request.getParameter("date");
	String[] strDates = strDate.split("-");
	strDate = strDates[0];
	for(int i = 1; i < strDates.length; i++){
		strDate += strDates[i];
	}
	Crawler menuCrawler = new Crawler();
	String strMenu = menuCrawler.getMenuData(strDate);
	String returnJSON = "";
	if(strMenu != null){
		returnJSON = "{\"success\":true, \"menuData\":\""+strMenu+"\"}";
	}
	else{
		returnJSON = "{success:false}";
	}
	System.out.println(returnJSON);
	response.setContentType("application/json");
	out.print(returnJSON);
	out.flush();
%>