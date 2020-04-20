<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board</title>
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
</head>
<body style="padding-top: 0px;">
	<article>
		<div class="container" role="main">
			<h2>board View</h2>
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
	</article>
</body>
</html>