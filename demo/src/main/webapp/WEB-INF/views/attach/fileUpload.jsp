<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>파일 관리</title>
</head>
<script type="text/javascript">
var fileGrid = new ax5.ui.grid();
$(document.body).ready(function () {
	fileGrid = new ax5.ui.grid({
        target: $('[data-ax5grid="file-grid"]'),
        columns: [
            {key: "rnum", label: "No", width: 70, align: "center"},
            {key: "filename", label: "File Name", width: 200, align: "left",formatter: function(){ return "<font color='blue' style='cursor:hand;'>" + this.value + "</font>";}},
            {key: "filesize", label: "Size", width: 70, align: "center"},
            {key: "remark", label: "Remark", width: 200, align: "center"}
        ],
        body: {
            onClick: function () {
                if (this.column.key == "filename"){
                    if(this.item.filecd == null){
                    	dialog.alert("저장되지 않은 파일이거나 없는 파일입니다.");
                    	return;
                    }
                	location.href = "/attach/download?filecd="+this.item.filecd;
                }
            }
        }                  
    });
	fileList();
	$('[data-grid-control]').click(function () {
	  	switch (this.getAttribute("data-grid-control")) {
	    case "del":
	    	if(fileGrid.getList("selected").length == 0){
            	dialog.alert("삭제할 목록을 선택하세요.");
            }else{
            	dialog.confirm({
                    title: "경고",
                    msg: '삭제하시겠습니까?'
                }, function(){
                    if(this.key == "ok"){                        
                    	fileGrid.deleteRow("selected");
                    	var deleteList = fileGrid.getList("deleted");
                    	var deleteCnt = deleteList.length; 
                    	var delArrayList = [];
                        for(var i=0;i<deleteCnt;i++){
                        	delArrayList.push(deleteList[i]);
                        }
                    	fileDelete(delArrayList, deleteCnt);
                    }else if(this.key == "cancel"){
                        dialog.close();
                    }
                });
            }
	        break; 	  	
	    case "save":
	    	uploadFile();
	        break;  
	  	}        
	});
}); 

function fileList(){
	var filegrpcd = $('#filegrpcd').val();
	var param = {"filegrpcd":filegrpcd};		
    common_gridloading_open("file-grid-parent");	
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/attach/getFileList",
        data: JSON.stringify(param),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	fileGrid.setData(data.fList);
        	common_gridloading_close();
        }
    });		
}

function fileDelete(delList, delCnt){
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/attach/deleteFileList",
        data: JSON.stringify(delList),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	reset();
            dialog.alert(delCnt+"건의 데이터가 삭제되었습니다.");            
            fileList();
        }

    });
}

function changeFile() {
	var data = new FormData($("#upload-file-form")[0]);
	var fileCnt = $('#upload-file-input')[0].files.length;
	var filegrpcd = $('#filegrpcd').val();
	var cnt = fileGrid.list.length;
	var maxno = 0;
	if(cnt > 0){
		maxno = fileGrid.list[cnt-1].rnum;
	}
	var addRowParam = {};
	for(var i=0;i<fileCnt;i++){
		var filename = $('#upload-file-input')[0].files[i].name;
		var filesize = $('#upload-file-input')[0].files[i].size;
		addRowParam = {"filegrpcd":filegrpcd,"rnum":maxno+i+1,"filename":filename,"filesize":filesize};
		fileGrid.addRow($.extend({}, addRowParam, maxno+i+1));
	}
}

function uploadFile() {
	var fcnt = $('#upload-file-input')[0].files.length;
	if(fcnt == 0){
		dialog.alert("첨부할 파일이 없습니다.");
		return;
	}
	var formData = new FormData($("#upload-file-form")[0]);
	formData.append('filegrpcd', $('#filegrpcd').val());
	common_gridloading_open("file-grid-parent");
	$.ajax({
	    url: "/attach/uploadFile",
	    type: "POST",
	    data: formData,
	    enctype: 'multipart/form-data',
	    processData: false,
	    contentType: false,
	    cache: false,
	    success: function () {
	    	reset();
	    	dialog.alert("파일이 저장되었습니다.");
		    fileList();
		    common_gridloading_close();
	    },
	    error: function () {
	    	reset();
	    	dialog.alert("실패");
	    	common_gridloading_close();
	    }
	});
}

function reset() {
	var $el = $('#upload-file-input'); 
    $el.wrap('<form>').closest( 
      'form').get(0).reset(); 
    $el.unwrap();
}
</script>
<body>
	<div class="container tb-basic">
		<div class="row">
			<div class="col-sm-6">
				<h3>파일 관리</h3>
			</div>
		 	<div class="col-sm-6">
				 	<div style="padding: 10px;" align="right">
				 		<form class="md-form" id="upload-file-form">
		 					<input type="hidden" id="filegrpcd" value="FILEGRP2020042810431111">
							<label class="btn btn-sm btn-primary file-btn-label">Browse
								<input multiple="multiple" id="upload-file-input" type="file" name="uploadfile" style="display: none;" onchange="changeFile();">
							</label>
						</form>			
						<button class="btn btn-sm btn-primary" data-grid-control="del">Delete</button>		
						<button class="btn btn-sm btn-primary" data-grid-control="save">Save</button>
					</div>					
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div style="position: relative;height:500px;" id="file-grid-parent">
				    <div data-ax5grid="file-grid" data-ax5grid-config="{
					                    showLineNumber: true,
					                    showRowSelector: true,
					                    sortable: true,
					                    header: {align:'center'}
					                    }" style="height:500px;"></div>
				</div>				
			</div>
		</div>			
	</div>	
</body>
</html>