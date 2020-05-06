<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Change Password</title>
</head>
<script> 
$(document).ready(function(){
	e.preventDefault();
});
$(document).on('click', '#btnSignup', function(e){ 
	e.preventDefault(); 
	var pwd = $('#password').val(); 
	var repwd = $('#repassword').val();
	if(pwd != repwd){
		dialog.alert("비밀번호를 다시 확인하세요.");
		return;
	}
	$("#form").submit(); 
}); 
$(document).on('click', '#btnCancel', function(e){
	e.preventDefault();
	location.href="${pageContext.request.contextPath}/"; 
});

</script> 
<body> 
	<div class="container tb-basic" role="main"> 
		<div class="card"> 
			<div class="card-header">Change Password</div> 
			<div class="card-body"> 
				<form:form name="form" id="form" class="form-signup" role="form" modelAttribute="userInfo" method="post" action="${pageContext.request.contextPath}/userInfo/updatePasswordUser"> 
					<form:input type="hidden" id="userno" path="userno"/>
					<div class="form-group row"> 
						<label for="password" class="col-md-3 col-form-label text-md-right">비밀번호</label> 
						<div class="col-md-5"> 
							<form:password path="password" id="password" class="form-control" placeholder="비밀번호를 입력해 주세요" /> 
						</div> 
					</div> 
					<div class="form-group row"> 
						<label for="repassword" class="col-md-3 col-form-label text-md-right">비밀번호 확인</label> 
						<div class="col-md-5"> 
							<form:password path="repassword" id="repassword" class="form-control" placeholder="비밀번호를 입력해 주세요" /> 
						</div> 
					</div> 
				</form:form> 
			</div> 
		</div> 
		<div style="margin-top:10px"> 
			<button type="button" class="btn btn-sm btn-primary" id="btnSignup">Change</button> 
			<button type="button" class="btn btn-sm btn-primary" id="btnCancel">Cancel</button> 
		</div> 
	</div> 
</body>
</html>