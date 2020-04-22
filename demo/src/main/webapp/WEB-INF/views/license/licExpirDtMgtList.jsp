<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>License Mgt</title>
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
	        {key: "licnm", label: "License Name", width: 200, align: "center"},
	        {key: "status", label: "Status", width: 100, align: "cneter",formatter: function(){
		        var signal = "";
		        if(this.item.status == "R"){
		        	signal = "<img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_r.png'/>";
			    }else if(this.item.status == "Y"){
			    	signal = "<img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_y.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/>";
			    }else if(this.item.status == "G"){
			    	signal = "<img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_g.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/>";
				}else{
					signal = "<img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/>";
				}    
	            return signal;
	        }}
	    ],
	    body: {
	        onClick: function () {
	            if (this.column.key == "licnm"){
	            	exModuleList(this.item.licserver);                  	
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
	
	var firstGrid2 = new ax5.ui.grid({
	    target: $('[data-ax5grid="first-grid2"]'),
	    columns: [
	        {key: "modulenm", label: "Module Name", width: 200, align: "center"},
	        {key: "status", label: "Status", width: 100, align: "cneter",formatter: function(){
		        var signal = "";
		        if(this.item.status == "R"){
		        	signal = "<img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_r.png'/>";
			    }else if(this.item.status == "Y"){
			    	signal = "<img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_y.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/>";
			    }else if(this.item.status == "G"){
			    	signal = "<img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_g.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/>";
				}else{
					signal = "<img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/><img src='${pageContext.request.contextPath}/resources/common/img/icon_circle_w.png'/>";
				}    
	            return signal;
	        }},
	        {key: "unit", label: "Unit", width: 80, align: "cneter"},
	        {key: "expiredt", label: "Expire Date", width: 100, align: "cneter"},
	        {key: "remark", label: "Remark", width: 200, align: "cneter"}                  
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
	        	firstGrid2.exportExcel("grid-to-excel.xls");
	            firstGrid2.contextMenu.close();
	            //또는 return true;
	        }
	    }
	});
	exLicList();
	
    function exLicList(){
	    $.ajax({
	        method: "GET",
	        url: "/license/getExpirLicList",
	        success: function (res) {
	            firstGrid.setData(res.eList);
	        }
	    });
    }

    function exModuleList(lic){
        var param = {"licserver":lic};
	    common_gridloading_open("grid-parent");	
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/license/getExpirModuleList",
	        data: JSON.stringify(param),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	firstGrid2.setData(data.eList);
	        	common_gridloading_close();
	        }
	    });	
    }
});	 
</script>
<body>
	<div class="container tb-basic">
		<div class="row">
			<div class="col-sm-6">
				<h2>Expiration Date Management</h2>
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
			<div class="col-sm-4">
				<div style="position: relative;height:500px;" id="grid-parent">
				    <div data-ax5grid="first-grid" data-ax5grid-config="{showLineNumber: true,showRowSelector: true,header: {align:'center'}}" style="height:500px;"></div>
				</div>				
			</div>
			<div class="col-sm-8">
				<div style="position: relative;height:500px;" id="grid-parent2">
				    <div data-ax5grid="first-grid2" data-ax5grid-config="{showLineNumber: true,showRowSelector: true,header: {align:'center'}}" style="height:500px;"></div>
				</div>				
			</div>
		</div>					
	</div>
</body>
</html>