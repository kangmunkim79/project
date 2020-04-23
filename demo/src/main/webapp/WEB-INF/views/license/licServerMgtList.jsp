<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>라이선스 서버 관리</title>
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
            {key: "licserver", label: "라이선스 서버", width: 150, align: "left",
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
	                }
	            }
            },            
            {key: "licnm", label: "라이선스명", width: 150, align: "left",
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
	                }
	            }
            },
            {key: "topmodulenm", label: "Main module", width: 150, align: "left",
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
	                }
	            }
            },
            {key: "fileurl", label: "파일경로", width: 150, align: "left",
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
	                }
	            }
            },
            {key: "filenmtype", label: "파일명 형식", width: 200, align: "center",
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
	                }
	            }
            },
            {key: "useflag", label: "사용여부", align: "center", sortable: false,
            	editor: {
                    type: "checkbox", 
                    config: {
                        height: 17, 
                        trueValue: "Y", 
                        falseValue: "N"
                    }
            	}
            },
            {key: "sort", label: "Sort", width: 80, align: "center",
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
	                }
	            }
            }
        ],
        body: {
            onDataChanged: function () {
                if (this.key == 'useflag') {
                    this.self.updateChildRows(this.dindex, {isChecked: this.item.isChecked});
                }
            }           
        }             
    });
	go();

    $('[data-grid-control]').click(function () {
    	switch (this.getAttribute("data-grid-control")) {
        case "add":
			var addRowParam = {"licserver":null,"licnm":null,"topmodulenm":null,"fileurl":null,"filenmtype":null,"useflag":"Y","sort":1};
            firstGrid.addRow($.extend({}, addRowParam, 0));
            break;
        case "remove":
            if(firstGrid.getList("selected").length == 0){
            	dialog.alert("삭제할 목록을 선택하세요.");
            }else{
            	dialog.confirm({
                    title: "경고",
                    msg: '삭제하시겠습니까?'
                }, function(){
                    if(this.key == "ok"){                        
                    	firstGrid.deleteRow("selected");
                    	var deleteList = firstGrid.getList("deleted");
                    	var deleteCnt = deleteList.length; 
                    	var delArrayList = [];
                        for(var i=0;i<deleteCnt;i++){
                        	delArrayList.push(deleteList[i]);
                        }
                    	menuDelete(delArrayList, deleteCnt);
                    }else if(this.key == "cancel"){
                        dialog.close();
                    }
                });
            }
            break;
        case "update":
            var updateList = firstGrid.getList("modified");
            if(updateList.length == 0){
            	dialog.alert("변경된 사항이 없습니다.");
            }else{
            	dialog.confirm({
                    title: "Message",
                    msg: '저장하시겠습니까?'
                }, function(){
                    if(this.key == "ok"){
                    	var upList = [];
                        for(var i=0;i<updateList.length;i++){
                        	upList.push(updateList[i]);
                        }
                    	save(upList);
                    }else if(this.key == "cancel"){
                        dialog.close();
                    }
                });
            }            
            break;
        case "excel-export":
        	firstGrid.exportExcel("grid-to-excel.xls");
        	break;    
    	}        
    	            
    });     

    // 그리드 데이터 가져오기
    function go(){
	    $.ajax({
	        method: "GET",
	        url: "/license/getGridLicServerList",
	        success: function (res) {
	            firstGrid.setData(res.mList);
	        }
	    });
    }

    function menuDelete(delList, delCnt){
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/license/deleteLicenseList",
	        data: JSON.stringify(delList),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
                dialog.alert(delCnt+"건의 데이터가 삭제되었습니다.");
                go();
	        }

	    });
    }   

    function save(upList){
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/license/saveLicList",
	        data: JSON.stringify(upList),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
                dialog.alert("저장되었습니다.");
                go();
	        }
	    });
    }
}); 
</script> 
<body> 
	<div class="container tb-basic">
		<div class="row">
			<div class="col-sm-6">
				<h3>라이선스 서버 관리</h3>
			</div>
		 	<div class="col-sm-6">
				<div style="padding: 10px;" align="right">
				    <button class="btn btn-sm btn-primary" data-grid-control="add">Add</button>
				    <button class="btn btn-sm btn-primary" data-grid-control="remove">Delete</button>
				    <button class="btn btn-sm btn-primary" data-grid-control="update">Save</button>
				    <button class="btn btn-sm btn-primary" data-grid-control="excel-export">Excel Export</button>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div style="position: relative;height:500px;" id="grid-parent">
				    <div data-ax5grid="first-grid" data-ax5grid-config="{showLineNumber: true,showRowSelector: true,header: {align:'center'}}" style="height:500px;"></div>
				</div>				
			</div>
		</div>									
	</div> 
</body>
</html>