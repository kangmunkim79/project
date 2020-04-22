<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board</title>
</head>
<script type="text/javascript">
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
            {key: "rnum", label: "No", width: 70, align: "center"},
            {key: "title", label: "제목", width: 150, align: "left",formatter: function(){ return "<font color='blue' style='cursor:hand;'>" + this.value + "</font>";}},
            {key: "regcd", label: "작성자", width: 80, align: "center"},
            {key: "viewcnt", label: "조회수", width: 70, align: "center"},
            {key: "regdt", label: "작성일", align: "center"}
        ],
        body: {
            onClick: function () {
                if (this.column.key == "title"){
                  	fn_contentView(this.item.bid);
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
	go();

    $('[data-grid-control]').click(function () {
      	switch (this.getAttribute("data-grid-control")) {
            case "btnWriteForm":
      		    location.href = "${pageContext.request.contextPath}/board/boardUpdate";
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
                      		boardDelete(delArrayList, deleteCnt);
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
           method: "POST",
           url: "/board/getGridBoardList",
           success: function (res) {
               firstGrid.setData(res.bList);
           }
       });
   }
   
    function boardDelete(delList, delCnt){
  	    $.ajax({
  	        type: "POST",
  	        contentType: "application/json",
  	        url: "/board/deleteBoardList",
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

  	function fn_contentView(bid){
		var url = "${pageContext.request.contextPath}/board/getBoardView";
		url = url + "?bid="+bid;
		location.href = url;
	}   
});        
</script>
<body>
	<div class="container tb-basic">
		<div class="row">
			<div class="col-sm-6">
				<h2>board List</h2>
			</div>
			<div class="col-sm-6">
				<div style="padding: 10px;" align="right">
				    <button class="btn btn-sm btn-primary" data-grid-control="btnWriteForm">Write</button>
				    <button class="btn btn-sm btn-primary" data-grid-control="remove">Delete</button>
				    <button class="btn btn-sm btn-primary" data-grid-control="excel-export">Excel Export</button>
				</div>			
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div style="position: relative;height:500px;" id="grid-parent">
				    <div data-ax5grid="first-grid" data-ax5grid-config="{
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