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

    // 그리드 데이터 가져오기
    $.ajax({
        method: "POST",
        url: "/userInfo/getGridUserList",
        success: function (res) {
            firstGrid.setData(res.uList);
        }
    });
    $('[data-grid-control]').click(function () {
    	switch (this.getAttribute("data-grid-control")) {
        case "userAddBtn":
        	location.href = "${pageContext.request.contextPath}/userInfo/userInfoUpdate";
            break;
        case "excel-export":
        	firstGrid.exportExcel("grid-to-excel.xls"); 
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
			<div class="col-sm-2">
			</div>
			<div class="col-sm-4">
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
						<div class="card"> 
							<div class="card-body"> 
								<div class="row"> 
									<label for="uid" class="col-md-3 col-form-label text-md-right">아이디</label> 
									<div class="col-md-5"> 
										<input type="text" id="uid" class="form-control" placeholder="아이디을 입력해 주세요" /> 
									</div> 
								</div> 
								<div class="row"> 
									<label for="name" class="col-md-3 col-form-label text-md-right">이름</label> 
									<div class="col-md-5"> 
										<input type="text" id="name" class="form-control" placeholder="이름을 입력해 주세요" /> 
									</div> 
								</div> 
								<div class="row"> 
									<label for="password" class="col-md-3 col-form-label text-md-right">비밀번호</label> 
									<div class="col-md-5"> 
										<input type="password" id="password" class="form-control" placeholder="비밀번호를 입력해 주세요" /> 
									</div> 
								</div> 
								<div class="row"> 
									<label for="repassword" class="col-md-3 col-form-label text-md-right">비밀번호 확인</label> 
									<div class="col-md-5"> 
										<input type="password" id="repassword" class="form-control" placeholder="비밀번호를 입력해 주세요" /> 
									</div> 
								</div> 
								<div class="row"> 
									<label for="email" class="col-md-3 col-form-label text-md-right">이메일</label> 
									<div class="input-group col-md-7"> 
										<div class="input-group-prepend"> 
											<span class="input-group-text">@</span> 
										</div> 
										<input type="text" id="email" class="form-control" placeholder="이메일을 입력해 주세요" /> 
									</div> 
								</div> 
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