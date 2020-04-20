<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board</title>
<script>
$(document).ready(function(){
	var mode = '<c:out value="${mode}"/>';
	if ( mode == 'edit'){
		//입력 폼 셋팅
		$("input:hidden[name='bid']").val('<c:out value="${boardView.bid}"/>');
		$("input:hidden[name='mode']").val('<c:out value="${mode}"/>');
		$("#title").val('<c:out value="${boardView.title}"/>');
		$("#content").val('<c:out value="${boardView.content}" escapeXml="false"/>');			
		$("#tag").val('<c:out value="${boardView.tag}"/>');
	}
});

$(document).on('click', '#btnSave', function(e){
	e.preventDefault();
	$("#form").submit();
});
	
$(document).on('click', '#btnList', function(e){
	e.preventDefault();
	location.href="${pageContext.request.contextPath}/board/getBoardList";
});
</script>
</head>
<body style="padding-top: 0px;">
	<article>
		<div class="container" role="main">
			<h2>board Update</h2>
			<form:form name="form" id="form" role="form" modelAttribute="board" method="post" action="${pageContext.request.contextPath}/board/saveBoard">
				<form:hidden path="bid" />
				<form:hidden path="catecd" value="BOARD" />
				<input type="hidden" name="mode" />
				<div class="mb-3">
					<label for="title">제목</label>
					<form:input path="title" id="title" class="form-control" placeholder="제목을 입력해 주세요" />
				</div>
				<div class="mb-3">
					<label for="content">내용</label>
					<form:textarea path="content" id="content" class="form-control" rows="5" placeholder="내용을 입력해 주세요" />
					<script>
					CKEDITOR.replace('content');	
					CKEDITOR.instances.content.updateElement();	  					    
					</script>
				</div>
				<div class="mb-3">
					<label for="tag">TAG</label>
					<form:input path="tag" id="tag" class="form-control" placeholder="태그를 입력해 주세요" />
				</div>
			</form:form>
			<div>
				<button type="button" class="btn btn-sm btn-primary" id="btnSave">저장</button>
				<button type="button" class="btn btn-sm btn-primary" id="btnList">목록</button>
			</div>
		</div>
	</article>
</body>
</html>