<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>userInfo</title>
</head>
<script> 
$(document.body).ready(function () {
	var API_SERVER = "http://api-demo.ax5.io";
	var dialog = new ax5.ui.dialog({
	    title: "Message"
	});    
	$('#alert-close').click(function () {
	    dialog.close();
	});
    var firstGrid = new ax5.ui.grid({
        target: $('[data-ax5grid="first-grid"]'),
        columns: [
            {key: "username", label: "Id", width: 120, align: "center"},
            {key: "name", label: "Name", width: 120, align: "cneter"},
            {key: "email", label: "Email", width: 150, align: "center"},
            {key: "regdt", label: "등록일", align: "center"}
        ],
        body: {
            onClick: function () {
                if (this.column.key == "username" || this.column.key == "name"){
                	userInfo(this.item.username);                  	
                }
            }
        },          
        contextMenu: {
            iconWidth: 20,
            acceleratorWidth: 100,
            itemClickAndClose: false,
            icons: {
                'arrow': '<i class="fa fa-caret-right"></i>'
            },
            items: [
                {type: 1, label: "menu"},
                {divide: true},
                {
                    label: "Tools",
                    items: [
                        {type: 1, label: "Excel Export"}
                    ]
                }
            ],
            popupFilter: function (item, param) {
                //console.log(item, param);
                if(param.element) {
                    return true;
                }else{
                    return item.type == 1;
                }
            },
            onClick: function (item, param) {
            	firstGrid.exportExcel("grid-to-excel.xls");
                firstGrid.contextMenu.close();
                //또는 return true;
            }
        }                   
    });
    userlist();
    
	function userlist(){
	    // 그리드 데이터 가져오기
	    $.ajax({
	        method: "POST",
	        url: "/userInfo/getGridUserList",
	        success: function (res) {
	            firstGrid.setData(res.uList);
	        }
	    });
	}

	function userInfo(username){
		var param = {"username":username};
	    // 그리드 데이터 가져오기
	    $.ajax({
	    	type: "POST",
	        contentType: "application/json",
	        url: "/userInfo/getGridUserInfo",
	        data: JSON.stringify(param),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (result) {
	        	$('#userno').val(result.uInfo.userno);
	        	$('#username').val(result.uInfo.username);
	        	$('#name').val(result.uInfo.name); 
	        	$('#password').val(result.uInfo.password); 
	        	$('#repassword').val(result.uInfo.password); 
	        	$('#email').val(result.uInfo.email);
	        }
	    });
	}

	function saveUserInfo(){
		if($('#password').val() != $('#repassword').val()){
			dialog.alert("비밀번호를 다시 확인하세요.");
		}
		var param = {
			 "userno":$('#userno').val()	
			,"username":$('#username').val()
			,"name":$('#name').val()
			,"password":$('#password').val()
			,"email":$('#email').val()
		};
		dialog.confirm({
            title: "Message",
            msg: '저장하시겠습니까?'
        }, function(){
            if(this.key == "ok"){
            	// 그리드 데이터 가져오기
        	    $.ajax({
        	    	type: "POST",
        	        contentType: "application/json",
        	        url: "/userInfo/saveUserInfo",
        	        data: JSON.stringify(param),
        	        dataType: 'json',
        	        cache: false,
        	        timeout: 600000,
        	        success: function (result) {
        	        	dialog.alert("저장되었습니다.");
        	        	$('#username').val(result.uInfo.username);
        	        	$('#name').val(result.uInfo.name); 
        	        	$('#password').val(result.uInfo.password); 
        	        	$('#repassword').val(result.uInfo.password); 
        	        	$('#email').val(result.uInfo.email);
        	        }
        	    });
            }else if(this.key == "cancel"){
                dialog.close();
                userInfo($('#username').val());
            }
        });
	}
	
    $('[data-grid-control]').click(function () {
    	switch (this.getAttribute("data-grid-control")) {
        case "userAddBtn":
        	location.href = "${pageContext.request.contextPath}/userInfo/userInfoUpdate";
            break;
        case "excel-export":
        	firstGrid.exportExcel("grid-to-excel.xls"); 
            break;
        case "btnSave":
        	saveUserInfo();
            break;    
    	}    
    });        
}); 
</script> 
<body>
<article> 
	<div class="container">
		<div class="row">
			<div class="col-sm-2">
				<h2>User list</h2>
			</div>
			<div class="col-sm-4">
				<div style="padding: 10px;" align="right">
				    <button class="btn btn-sm btn-primary" data-grid-control="excel-export">Excel Export</button>
				    <button class="btn btn-sm btn-primary" data-grid-control="userAddBtn" id="userAddBtn">Add</button>
				</div>			
			</div>
			<div class="col-sm-6">
			</div>
		</div>
		<div class="row"> 
			<div class="col-sm-6">
				<div style="position: relative;height:500px;" id="grid-parent">
				    <div data-ax5grid="first-grid" data-ax5grid-config="{
					                    showLineNumber: true,
					                    showRowSelector: true,
					                    sortable: true,
					                    header: {align:'center'}
					                    }" style="height:500px;"></div>
				</div>	
			</div>
			<div class="col-sm-6">
				<ul class="nav nav-tabs">
				    <li class="nav-item">
				        <a class="nav-link active" data-toggle="tab" href="#userinfo">INFO</a>
				    </li>
				    <li class="nav-item">
				    	<a class="nav-link" data-toggle="tab" href="#userauth">권한</a>
				    </li>
				</ul>
				<div class="tab-content">
				    <div class="tab-pane fade show active" id="userinfo">
				    	<div class="row">
				    		<div class="col-sm-12">
								<div style="padding: 10px;" align="right">
								    <button class="btn btn-sm btn-primary" data-grid-control="btnSave">Save</button>
								</div>				 
							</div>   	
				    	</div>
						<div class="form-group row"> 
							<label for="username" class="col-sm-3 col-form-label-sm text-md-right">아이디</label> 
							<div class="col-md-9"> 
								<input type="hidden" id="userno"/> 
								<input type="text" id="username" class="form-control form-control-sm" placeholder="아이디을 입력해 주세요" /> 
							</div> 
						</div> 
						<div class="form-group row"> 
							<label for="name" class="col-md-3 col-form-label-sm text-md-right">이름</label> 
							<div class="col-md-9"> 
								<input type="text" id="name" class="form-control form-control-sm" placeholder="이름을 입력해 주세요" /> 
							</div> 
						</div> 
						<div class="form-group row"> 
							<label for="password" class="col-md-3 col-form-label-sm text-md-right">비밀번호</label> 
							<div class="col-md-9"> 
								<input type="password" id="password" class="form-control form-control-sm" placeholder="비밀번호를 입력해 주세요" /> 
							</div> 
						</div> 
						<div class="form-group row"> 
							<label for="repassword" class="col-md-3 col-form-label-sm text-md-right">비밀번호 확인</label> 
							<div class="col-md-9"> 
								<input type="password" id="repassword" class="form-control form-control-sm" placeholder="비밀번호를 입력해 주세요" /> 
							</div> 
						</div> 
						<div class="form-group row"> 
							<label for="email" class="col-md-3 col-form-label-sm text-md-right">Email</label> 
							<div class="col-md-9"> 
								<input type="text" id="email" class="form-control form-control-sm" placeholder="이메일을 입력해 주세요" /> 
							</div> 
						</div> 
				  	</div>
				  	<div class="tab-pane fade" id="userauth">
				    	<p>Nunc vitae turpis id nibh sodales commodo et non augue. Proin fringilla ex nunc. Integer tincidunt risus ut facilisis tristique.</p>
				  	</div>
				</div>			
			</div>
		</div>						
	</div> 
</article>
</body>
</html>