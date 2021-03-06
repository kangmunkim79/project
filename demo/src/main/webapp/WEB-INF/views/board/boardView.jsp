<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 상세</title>
</head>
<script>
$(document).on('click', '#btnList', function(){
	location.href = "${pageContext.request.contextPath}/board/getBoardList";
});

$(document).on('click', '#btnUpdate', function(){
	var url = "${pageContext.request.contextPath}/board/boardEdit";
	url = url + "?bid=${boardView.bid}&mode=edit";
	location.href = url;
});

$(document).on('click', '#btnDelete', function(){
    var url = "${pageContext.request.contextPath}/board/deleteBoard";
    url = url + "?bid=${boardView.bid}";
	location.href = url;
});
</script>
<body>
	<div class="container tb-basic" role="main">
		<h3>게시판 상세</h3>
		<div class="bg-white rounded shadow-sm">
			<div class="board_title"><c:out value="${boardView.title}"/></div>
			<div class="board_info_box">
				<span class="board_author"><c:out value="${boardView.regcd}"/>,</span><span class="board_date"><c:out value="${boardView.regdt}"/></span>
			</div>
			<div class="board_content">${boardView.content}</div>
			<div class="board_tag">TAG : <c:out value="${boardView.tag}"/></div>
		</div>
		<div style="margin-top : 20px">
			<button type="button" class="btn btn-sm btn-primary" id="btnUpdate">수정</button>
			<button type="button" class="btn btn-sm btn-primary" id="btnDelete">삭제</button>
			<button type="button" class="btn btn-sm btn-primary" id="btnList">목록</button>
		</div>
	</div>
</body>
</html>