<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board</title>
</head>
<script type="text/javascript">
	$(document).on('click', '#btnWriteForm', function(e){
		e.preventDefault();
		location.href = "${pageContext.request.contextPath}/board/boardUpdate";
	});

	function fn_contentView(bid){
		var url = "${pageContext.request.contextPath}/board/getBoardView";
		url = url + "?bid="+bid;
		location.href = url;
	}
   
    $(document.body).ready(function () {
        var API_SERVER = "http://api-demo.ax5.io";
        var firstGrid = new ax5.ui.grid();
 
        firstGrid.setConfig({
            target: $('[data-ax5grid="first-grid"]'),
            showLineNumber: true,
            showRowSelector: false,
            header: {align: 'center'},
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
 
        // 그리드 데이터 가져오기
        $.ajax({
            method: "POST",
            url: "/board/getGridBoardList",
            success: function (res) {
                firstGrid.setData(res.bList);
            }
        });
        $('[data-grid-control]').click(function () {
        	firstGrid.exportExcel("grid-to-excel.xls");            
        });        
    });        
</script>
<body>
<article>
	<div class="container">
		<h2>board List</h2>
		<div style="position: relative;height:400px;" id="grid-parent">
		    <div data-ax5grid="first-grid" data-ax5grid-config="{
			                    showLineNumber: true,
			                    showRowSelector: false,
			                    sortable: true,
			                    header: {align:'center'}
			                    }" style="height: 100%;"></div>
		</div>	
		<div style="padding: 10px;">
		    <button class="btn btn-sm btn-primary" data-grid-control="excel-export">Excel Export</button>
		    <button class="btn btn-sm btn-primary" id="btnWriteForm">글쓰기</button>
		</div>				
	</div>
</article>	
</body>
</html>