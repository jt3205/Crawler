<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<style>
* {
	margin: 0px;
	padding: 0px;
}

html, body {
	width: 100%;
	height: 100%;
}

#container {
	width: 100%;
	height: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
}

#inputBox {
	width: 50%;
	min-height: 30%;
	border-radius: 10px;
	border: 1px solid black;
	display: flex;
	justify-content: center;
	padding: 20px
}

#inputBox>form {
	width: 70%;
	height: 80%;
}

#dataBox {
	width: 50%;
	border: 1px solid black;
	border-radius: 10px;
	margin-top: 20px;
	padding: 20px;
}
</style>
</head>
<body>
	<div id="container">
		<div id="inputBox">
			<form action="lunchDate.jsp" method="post" class="form">
				<h2>급식일 입력</h2>
				<input type="date" name="date" class="form-control" /><br /> <input
					type="submit" value="급식보기" class="btn btn-default" />
			</form>
		</div>
		<div id="dataBox">
			<h3>메뉴</h3>
		</div>
	</div>
</body>
<script>
	$(document).ready(function() {
		$("form").on("submit", function(e) {
			var date = $("input[type=date]").val();
			$.ajax({
				url : "lunchDataJsonVer.jsp",
				method : "post",
				data : {
					"date" : date
				},
				success : function(data) {
					var menus = data.menuData.split(".");
					returnStr = "<ul>";
					menus.forEach(function(value) {
						returnStr += "<li>" + value + "</li>";
					});
					returnStr += "</ul>";

					$("#dataBox").html("<h3>메뉴</h3>"+returnStr);
				}
			});
			e.preventDefault();//페이지 안넘어가게 함
		});
	});
</script>
</html>