<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4>1부터 100까지 홀수의 합</h4>
	<c:set var="sum" value="0"/>
	<c:forEach var="i" begin="1" end="100" step="2">
		<c:set var="sum" value="${sum + i }"/>
	</c:forEach>
	결과 = ${sum}
	
	<c:set var="dan" value="${param.dan}"/>
	<h4>구구단 : ${dan}단</h4>
	<ul>
		<c:forEach var="i" begin="1" end="${dan}">
			<c:forEach var="j" begin="1" end="9">
				<li> ${i} * ${j} = ${j*i} </li>
			</c:forEach>
		</c:forEach>
	</ul>
</body>
</html>