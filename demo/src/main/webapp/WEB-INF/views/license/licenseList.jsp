<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>license</title>
</head>
<script type="text/javascript">
var firstGrid = new ax5.ui.grid();
var firstGrid2 = new ax5.ui.grid();
var firstGrid3 = new ax5.ui.grid();
$(document.body).ready(function () {
  	var API_SERVER = "http://api-demo.ax5.io";  	
  	var dialog = new ax5.ui.dialog({
  	    title: "Message"
  	});    
  	$('#alert-close').click(function () {
  	    dialog.close();
  	});
    
    picker.bind({
        target: $('[data-ax5picker="basic"]'),
        direction: "top",
        content: {
            width: 270,
            margin: 10,
            height: 30,
            type: 'date',
            config: {
                control: {
                    left: '<i class="fa fa-chevron-left"></i>',
                    yearTmpl: '%s',
                    monthTmpl: '%s',
                    right: '<i class="fa fa-chevron-right"></i>'
                },
                lang: {
                    yearTmpl: "%s년",
                    months: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
                    dayTmpl: "%s"
                }
            }
        },
        onStateChanged: function () {
			
        }
    });
    firstGrid = new ax5.ui.grid({
        target: $('[data-ax5grid="first-grid"]'),
        columns: [
            {key: "licnm", label: "License Name", width: 150, align: "left"},
            {key: "topmodulenm", label: "Main Module", width: 150, align: "left"}
        ],
        body: {
            onClick: function () {
                if (this.column.key == "licnm" || this.column.key == "modulenm"){
                	moduleList(this.item.licserver, this.item.modulenm);
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
    firstGrid2 = new ax5.ui.grid({
        target: $('[data-ax5grid="first-grid2"]'),
        columns: [
            {key: "modulenm", label: "Module Name", width: 150, align: "left"},
            {key: "usage", label: "Max Use Count", width: 100, align: "right"},
            {key: "maxcredit", label: "License Count", width: 100, align: "right"}
        ],
        body: {
            onClick: function () {
                if (this.column.key == "licnm" || this.column.key == "modulenm"){
                  	licUserList(this.item.licserver, this.item.modulenm);
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
              	firstGrid2.exportExcel("grid-to-excel.xls");
                firstGrid2.contextMenu.close();
                //또는 return true;
            }
        }                   
    });    
    firstGrid3 = new ax5.ui.grid({
        target: $('[data-ax5grid="first-grid3"]'),
        columns: [
            {key: "id", label: "Id", width: 100, align: "left"},
            {key: "name", label: "Name", width: 100, align: "left"},
            {key: "deptnm", label: "Dept Name", width: 100, align: "left"},
            {key: "usecount", label: "Use Count", width: 100, align: "right"}
        ],
        body: {
            onClick: function () {
                if (this.column.key == "licnm" || this.column.key == "modulenm"){
                  	//fn_contentView(this.item.bid);
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
              	firstGrid3.exportExcel("grid-to-excel.xls");
                firstGrid3.contextMenu.close();
                //또는 return true;
            }
        }                   
    }); 

    function moduleList(licserver, modulenm){
    	var param = {};	
    	var stDate = $("#stDateTime").val();
    	var etDate = $("#etDateTime").val();
    	var daychk = $("#daychk")[0].checked;
    	param = {"stDateTime":stDate,"etDateTime":etDate,"daychk":daychk,"licserver":licserver,"modulenm":modulenm};
    	
    	common_gridloading_open("grid-parent2");
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/license/getGridModuleList",
            data: JSON.stringify(param),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
            	firstGrid2.setData(data.mList);
            	common_gridloading_close();
            }
        });		
    }        

    function licUserList(licserver, modulenm){
    	var param = {};	
    	var stDate = $("#stDateTime").val();
    	var etDate = $("#etDateTime").val();
    	var daychk = $("#daychk")[0].checked;
    	param = {"stDateTime":stDate,"etDateTime":etDate,"daychk":daychk,"licserver":licserver,"modulenm":modulenm};
    	
    	common_gridloading_open("grid-parent3");
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/license/getGridLicUserList",
            data: JSON.stringify(param),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
            	firstGrid3.setData(data.uList);
            	common_gridloading_close();
            }
        });		
    } 
});        
	// 그리드 데이터 가져오기
function licenseList(){
	var param = {};	
	var stDate = $("#stDateTime").val();
	var etDate = $("#etDateTime").val();
	var daychk = $("#daychk")[0].checked;
	param = {"stDateTime":stDate,"etDateTime":etDate,"daychk":daychk,"licserver":"","modulenm":""};
	
    common_gridloading_open("grid-parent");	
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/license/getGridLicenseList",
        data: JSON.stringify(param),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	firstGrid.setData(data.lList);
        	common_gridloading_close();
        }
    });		
}
</script>
<body>
<article>
	<div class="container">
		<h2>license List</h2>
  		<form class="form-inline" action="#">
  			<div data-ax5picker="basic">
	    		<label for="stDateTime" class="mb-2 mr-sm-2" style="float:left;margin-top:5px;">From date:</label>
	    		<input type="text" class="form-control mb-2 mr-sm-2" style="float:left;" id="stDateTime" placeholder="yyyy/mm/dd" name="stDateTime">
	    		<label for="etDateTime" class="mb-2 mr-sm-2" style="float:left;margin-top:5px;">To date:</label>
	    		<input type="text" class="form-control mb-2 mr-sm-2" style="float:left;" id="etDateTime" placeholder="yyyy/mm/dd" name="etDateTime">
    		</div>
    		<div class="form-check mb-2 mr-sm-2">
      			<label class="form-check-label">
        			<input type="checkbox" class="form-check-input" name="daychk" id="daychk"> All Time
      			</label>
    		</div>    
    		<button type="button" class="btn btn-primary float-right" style="float:right;" onclick="javascript:licenseList()">Search</button>
  		</form>
		<div style="position: relative;width:300px;height:300px;float:left;" id="grid-parent">
		    <div data-ax5grid="first-grid" data-ax5grid-config="{
			                    showLineNumber: true,
			                    showRowSelector: false,
			                    sortable: true,
			                    header: {align:'center'}
			                    }" style="width:300px;height:300px;"></div>
		</div>
		<div style="position: relative;width:400px;height:300px;float:left;" id="grid-parent2">
		    <div data-ax5grid="first-grid2" data-ax5grid-config="{
			                    showLineNumber: true,
			                    showRowSelector: false,
			                    sortable: true,
			                    header: {align:'center'}
			                    }" style="width:400px;height:300px;"></div>
		</div>
		<div style="position: relative;width:300px;height:300px;float:left;" id="grid-parent3">
		    <div data-ax5grid="first-grid3" data-ax5grid-config="{
			                    showLineNumber: true,
			                    showRowSelector: false,
			                    sortable: true,
			                    header: {align:'center'}
			                    }" style="width:300px;height:300px;"></div>
		</div>																								
	</div>
</article>	
</body>
</html>