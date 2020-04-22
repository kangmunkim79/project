<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>License Monitoring System</title>
</head>
<script type="text/javascript">

</script>
<body>
	<div class="container tb-basic">
		<div class="row">
			<div class="col-sm-12">
			  	<div id="myCarousel" style="margin-top:10px;" class="carousel slide" data-ride="carousel">
			    <!-- Indicators -->
			    	<ol class="carousel-indicators">
			      		<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			      		<li data-target="#myCarousel" data-slide-to="1"></li>
			      		<li data-target="#myCarousel" data-slide-to="2"></li>
			    	</ol>
			
			    	<!-- Wrapper for slides -->
			    	<div class="carousel-inner">
			      		<div class="item active">
			        		<img src="${pageContext.request.contextPath}/resources/Photos/la.jpg" alt="Los Angeles" style="width:100%;">
			      		</div>
			      		<div class="item">
			        		<img src="${pageContext.request.contextPath}/resources/Photos/chicago.jpg" alt="Chicago" style="width:100%;">
			      		</div>
			      		<div class="item">
			        		<img src="${pageContext.request.contextPath}/resources/Photos/ny.jpg" alt="New york" style="width:100%;">
			      		</div>
			    	</div>
			    	<!-- Left and right controls -->
			    	<a class="left carousel-control" href="#myCarousel" data-slide="prev">
			      		<span class="glyphicon glyphicon-chevron-left"></span>
			      		<span class="sr-only">Previous</span>
			    	</a>
			    	<a class="right carousel-control" href="#myCarousel" data-slide="next">
			      		<span class="glyphicon glyphicon-chevron-right"></span>
			      		<span class="sr-only">Next</span>
			    	</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
