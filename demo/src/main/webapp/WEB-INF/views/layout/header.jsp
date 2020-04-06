<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script><!-- jQuery --> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script> 
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script><!-- Bootstrap --> 
<script src="${pageContext.request.contextPath}/resources/common/ckeditor/ckeditor.js"></script><!-- ckeditor4 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/common/css/common.css"><!-- common CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous"><!-- Bootstrap CSS -->

<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
    <a class="navbar-brand" href="#">SHCH.COM</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample03" aria-controls="navbarsExample03" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
	<div class="collapse navbar-collapse" id="navbarsExample03">
    	<ul class="navbar-nav mr-auto">
<!-- 	        <li class="nav-item active">
	        	<a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
		    </li>
      		<li class="nav-item">
        		<a class="nav-link" href="#">Link</a>
		    </li>
      		<li class="nav-item">
		        <a class="nav-link disabled" href="#">Disabled</a>
		    </li> -->
		    <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="dropdown03" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Board</a>
		        <div class="dropdown-menu" aria-labelledby="dropdown03">
			        <a class="dropdown-item" href="#">게시판</a>
		            <a class="dropdown-item" href="#">Q&A</a>
		        </div>
      		</li>
	    </ul>
	    <form class="form-inline my-2 my-md-0">
	        <input class="form-control" type="text" placeholder="Search">
	    </form>
    </div>
</nav>
