<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.crawler.NewsCrawler" %>
<%@ page import="domain.RecordVO" %>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<title>뉴스 정보 사이트</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<h1>오늘의 뉴스</h1>
<%
 	NewsCrawler crawler = new NewsCrawler();
	crawler.updateNewsTable();
	
	List<RecordVO> records = crawler.getRecordList();
	request.setAttribute("records", records);
%>
<div class="container">
	<div class="row" style="min-height:450px;">
		<div class="col-md-4">
			<ul class="nav nav-stacked nav-pills">
			<c:forEach items="${records}" var="data" >
				<li><a data-target="${data.id}"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${data.time}"/><p>${data.cnt}</p></a></li>
				
			</c:forEach>
			</ul>
		</div>
		<div class="col-md-8">
			<ul id="target">
				
			</ul>
		</div>
	</div>
</div>
<script>
	$(document).on("click",".nav > li > a", function(e) {
		var id = $(this).data("target");
		$.ajax({
			method:"post",
			url:"newsData.jsp",
			data:{"id" : id},
			success:function(data){
				var str = "";
				for (var i = 0; i < data.length; i++) {
					str += "<li> <a href='" + data[i].link + "'>" +data[i].title + "[ " +data[i].pubDate+"]</a></li>"
				}
				$("#target").empty();
				$("#target").append(str);
			}
		});
	});
</script>
</body>
</html>
