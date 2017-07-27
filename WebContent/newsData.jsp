<%@page import="com.sun.xml.internal.messaging.saaj.util.ByteOutputStream"%>
<%@page import="java.io.OutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.crawler.NewsCrawler" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.NewsVO" %>
<%@ page import="com.fasterxml.jackson.databind.JsonMappingException" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	int id = Integer.parseInt(request.getParameter("id"));

	NewsCrawler newsCrawler = new NewsCrawler();
	
	List<NewsVO> news = newsCrawler.getNewsList(id);
	
	ObjectMapper mapper = new ObjectMapper();
	
	response.setContentType("application/json");

	out.println(mapper.writeValueAsString(news));
%>