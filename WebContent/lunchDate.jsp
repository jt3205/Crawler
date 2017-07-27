<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="util.crawler.Crawler"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String strDate = request.getParameter("date");
		String[] strDates = strDate.split("-");

		strDate = strDates[0];
		for (int i = 1; i < strDates.length; i++) {
			strDate += strDates[i];
		}

		Crawler menuCrawler = new Crawler();
		String strMenu = menuCrawler.getMenuData(strDate);

		String[] menus = null;
		if (strMenu != null) {
			menus = strMenu.split("\\.");
		}
	%>

	<div class="container">
		<%
			if (menus != null) {
				out.println(strDate + "의 메뉴");
				out.println("<ul>");
				for (String menu : menus) {
					out.println("<li>" + menu + "</li>");
				}
			} else {
				out.println("<h3>오늘은 식사가 없습니다.</h3>");
				out.println("<img src='/imgs/sad.jpg'/>");
			}
		%>
	</div>
</body>
</html>