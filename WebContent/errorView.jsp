
<%-- JSP que muestra información sobre un error. --%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="infraestructura.*"%>
<%@ page import="infraestructura.MenuGenerator"%>
<html>
<head >
	<meta charset="UTF-8">
	<title>Error || MeetBook</title>
	<meta name="keywords" content="Error, películas, series">
	<meta name="description" content="Error">
	<link rel="stylesheet" type="text/css" href="style.css" />
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="/MeetBook/css/bootstrap.min.css">
	<!-- Latest compiled JavaScript -->
	<script src="/MeetBook/js/bootstrap.min.js"></script>
</head>
<body background="images/wallpaper.jpg">
	<div id="login-overlay" class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				
				<h4 class="modal-title" id="myModalLabel">Se ha producido un error</h4>
			</div>
			<div class="modal-body">
				<% String error =(String) request.getAttribute("error"); 
				out.println(error);
				%>
				<br/>
				Sentimos las molestias.
				
			</div>
		</div>
	</div>
</body>

</html>
