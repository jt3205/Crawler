<%@page import="util.crawler.NewsCrawler"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>오늘의 뉴스</h1>
<%
	NewsCrawler crawler = new NewsCrawler();

	crawler.getDataSet("http://www.kbs.co.kr/rss/drama.xml", "item");
	
	String[] aTags = crawler.makeATage("title", "link", "pubDate");
	for(String aTag : aTags){
		out.println(aTag+"<br/>");
	}
%>
</body>
</html>