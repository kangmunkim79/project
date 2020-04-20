<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>license</title>
<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/resources/common/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/resources/common/css/small-business.css" rel="stylesheet">
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<script type="text/javascript">
var firstGrid = new ax5.ui.grid();
var firstGrid2 = new ax5.ui.grid();
var firstGrid3 = new ax5.ui.grid();
var API_SERVER = "http://api-demo.ax5.io";  	
var dialog = new ax5.ui.dialog({
    title: "Message"
});    
$('#alert-close').click(function () {
    dialog.close();
});
$(document.body).ready(function () {

    
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
                	$('#lic').val(this.item.licserver);
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
                  	licUserList(this.item.licserver, this.item.modulenm, this.item.licnm, this.item.maxcredit);   
                  	$('#mod').val(this.item.modulenm);               	
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

    function licUserList(licserver, modulenm, licnm, maxcredit){
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
            	licchartgraph(licserver, modulenm, licnm, maxcredit);
            }
        });		
    } 

    $('[data-grid-control]').click(function () {
    	switch (this.getAttribute("data-grid-control")) {
        case "search":
        	licenseList();
            break;
        case "rawData":
        	if(!validationCheck()) return;
        	rawDataDown();
            break;
        case "report":
        	if(!validationCheck()) return;
        	reportDown();
            break;  
        case "detail":
        	if(!validationCheck()) return;
        	detailPop();
            break;          
    	}        
    	            
    });   
    
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
google.charts.load("current", {packages:['bar']});
google.charts.setOnLoadCallback(licchartgraph);	
function licchartgraph(licserver, modulenm, licnm, maxcredit) {
	var param = {};	
	var stDate = $("#stDateTime").val();
	var etDate = $("#etDateTime").val();
	var daychk = $("#daychk")[0].checked;
	var datetype = $('input[name="datetype"]:checked').val();
	var stitleTemp = "";
	var ticks = common_npercent(Number(maxcredit));
	
	if(datetype == "time") {
		stitleTemp = stDate + " 01:00 ~" + etDate + " 23:00";
	}else{
		stitleTemp = stDate+" ~ "+etDate;
	}
	var xlabel = datetype;
	var gtitle = "License: "+licnm+", Module: "+modulenm+", 라이선스 총 수량: "+maxcredit;
	var stitle = stitleTemp;
	
	param = {"stDateTime":stDate,"etDateTime":etDate,"daychk":daychk,"licserver":licserver,"modulenm":modulenm,"datetype":datetype};
	
    common_gridloading_open("licchart");	
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/license/getlicchartList",
        data: JSON.stringify(param),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (res) {	    	
			var gdata = [['Year('+xlabel+')', 'Use Count']];	
			for(var d=0;d<res.gList.length;d++){
				gdata[d+1] = [res.gList[d].regdate,res.gList[d].usercnt];
			}	
			var data = google.visualization.arrayToDataTable(gdata);	
			var options = {
				chart: {
					title: gtitle,
					subtitle: stitle
				},				
				hAxis: {
					title: "Years("+datetype+")" , 
					direction:-1, 
					slantedText:true, 
					slantedTextAngle:90 
				},
				vAxis: {
					ticks: ticks,
					viewWindow: {
						min:0
					}
				}
			};
			var chart = new google.charts.Bar(document.getElementById('licchart'));
			chart.draw(data, google.charts.Bar.convertOptions(options));
			common_gridloading_close();

		},
		error:function(info,xhr){
			alert(info.status);
		}
	});
}  

function rawDataDown(){
	var param = {};
	var licserver = $("#lic").val();
	var modulenm = $("#mod").val();	
	var stDate = $("#stDateTime").val();
	var etDate = $("#etDateTime").val();
	var daychk = $("#daychk")[0].checked;
	param = {"stDateTime":stDate,"etDateTime":etDate,"licserver":licserver,"modulenm":modulenm};
	
    common_gridloading_open("container");	
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/license/rawDataDown",
        data: JSON.stringify(param),
        success: function (res) {
        	dialog.alert("Success Rawdata Download!!!!");
        	common_gridloading_close();
        },
        error: function (e) {
        	dialog.alert("Success Rawdata Download!!");
        	common_gridloading_close();
        }
    });		
}

function reportDown(){
	var param = {};
	var licserver = $("#lic").val();
	var modulenm = $("#mod").val();	
	var stDate = $("#stDateTime").val();
	var etDate = $("#etDateTime").val();
	var daychk = $("#daychk")[0].checked;
	param = {"stDateTime":stDate,"etDateTime":etDate,"licserver":licserver,"modulenm":modulenm};
	
    common_gridloading_open("container");	
    $.ajax({
        type: "POST",
        contentType : "application/json; charset=UTF-8",
        url: "/license/reportDown",
        data: JSON.stringify(param),
        success: function (response) {
        	dialog.alert("Success Report Download!!!!--> " + response);
        	common_gridloading_close();
        },
        error: function (request, status, error) {
        	dialog.alert("Failed Report Download!!");
        	common_gridloading_close();
        }
    });		
}

function detailPop(){
	var param = {};
	var licserver = $("#lic").val();
	var modulenm = $("#mod").val();	
	var stDate = $("#stDateTime").val();
	var etDate = $("#etDateTime").val();
	var daychk = $("#daychk")[0].checked;
	param = {"stDateTime":stDate,"etDateTime":etDate,"licserver":licserver,"modulenm":modulenm};
	
    common_gridloading_open("container");	
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/license/detailPop",
        data: JSON.stringify(param),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	$('#licnm').val(data.detail.licnm);
        	$('#modulenm').val(data.detail.modulenm);
        	$('#maxcnt').val(data.detail.maxcnt);
        	$('#peakcnt').val(data.detail.peakcnt);
        	$('#avgcnt').val(data.detail.avgcnt);
        	$('#avgper').val(data.detail.avgper);
        	$('#timelog').val(data.detail.timelog);
        	$('#datelog').val(data.detail.datelog);
        	common_gridloading_close();
        }
    });		
}

function validationCheck(){
	var lic = $('#lic').val();
	var mod = $('#mod').val();
	var stDate = $('#stDateTime').val();
	var etDate = $('#etDateTime').val();
	
	if(lic == ""){
		dialog.alert("Please, License select row");
		return false;
	}
	
	if(mod == ""){
		dialog.alert("Please, Module select row");
		return false;
	}
	
	if(stDate == "" || etDate == ""){
		dialog.alert("Date Time Required !!");
		return false;
	}
	
	return true;
}
</script>
<body style="padding-top: 0px;">
<article>
	<input id="lic" type="hidden" value="">
	<input id="mod" type="hidden" value="">
	<div class="container">
		<div class="row">
			<div class="col-sm-6">
				<h2>License Management</h2>
			</div>
			<div class="col-sm-6">
				<div style="padding: 10px;" align="right">
					<button class="btn btn-sm btn-primary" data-grid-control="rawData">Raw Data</button>
					<button class="btn btn-sm btn-primary" data-grid-control="report">Report</button>
					<button class="btn btn-sm btn-primary" data-grid-control="detail" data-toggle="modal" data-target="#detailModal">Detail</button>
					<button class="btn btn-sm btn-primary" data-grid-control="search">Search</button>
				</div>
			</div>
		</div>	
		<div class="card text-white bg-secondary my-1 text-center">
      		<div class="card-body">	
				<form class="form-horizontal">
			       <div class="container">
			           <div class="row">
			               <div class="form-group form-group-sm col-sm-6">
			                   <div class="row" data-ax5picker="basic">
			                       <label for="stDateTime" class="col-sm-3 col-form-label">Date:</label>
			                       <div class="col-sm-4">
			                           <input type="text" class="form-control" id="stDateTime" placeholder="yyyy-mm-dd" name="stDateTime">
			                       </div>
								   <label for="etDateTime" class="col-sm-1 col-form-label">~</label>
			                       <div class="col-sm-4">
			                           <input type="text" class="form-control" id="etDateTime" placeholder="yyyy-mm-dd" name="etDateTime">
			                       </div>
			                   </div>
			               </div>
			               <div class="form-group form-group-sm col-sm-2">
			                   <div class="checkbox"> 
			                   	   <label for="daychk"><input type="checkbox" name="daychk" id="daychk">  All Time</label> 
			                   </div>
			               </div>			               			               			               			
			           </div>			
			       </div>
				</form>      		
		  	</div>
		</div>
		<div class="row">
			<div class="col-sm-4">
		        <div style="position: relative;width:100%;height:300px;" id="grid-parent">
				    <div data-ax5grid="first-grid" data-ax5grid-config="{
					                    showLineNumber: true,
					                    showRowSelector: false,
					                    sortable: true,
					                    header: {align:'center'}
					                    }" style="width:100%;height:300px;"></div>
				</div>			
			</div>
			<div class="col-sm-4">
			    <div style="position: relative;width:100%;height:300px;" id="grid-parent2">
			        <div data-ax5grid="first-grid2" data-ax5grid-config="{
				                    showLineNumber: true,
				                    showRowSelector: false,
				                    sortable: true,
				                    header: {align:'center'}
				                    }" style="width:100%;height:300px;"></div>
				</div>			
			</div>
			<div class="col-sm-4">
				<div style="position: relative;width:100%;height:300px;" id="grid-parent3">
				    <div data-ax5grid="first-grid3" data-ax5grid-config="{
					                    showLineNumber: true,
					                    showRowSelector: false,
					                    sortable: true,
					                    header: {align:'center'}
					                    }" style="width:100%;height:300px;"></div>
				</div>			
			</div>
		</div>		
	    <div class="row">
	        <div class="col-md-12">
            	<div class="radio">
					<label><input type="radio" name="datetype" value="day" checked> day </label>
				    <label><input type="radio" name="datetype" value="time"> time </label>
				</div>
	        </div>        
	    </div>		
	    <div class="row">
	        <div class="col-md-12">
               	<div id="licchart" style="width: 100%; height: 300px;"></div>
	        </div>        
	    </div> 

		<!-- The Modal -->
		<div class="modal" id="detailModal">
		  	<div class="modal-dialog modal-dialog-scrollable">
		    	<div class="modal-content">	    
		      		<!-- Modal Header -->
		     		<div class="modal-header">
		       			<h3 class="modal-title">License Monitoring Detail</h3>
		       			<button type="button" class="close" data-dismiss="modal">×</button>
		     		</div>
		     
			     	<!-- Modal body -->
			     	<div class="modal-body">
					    <div class="form-group row">
					    	<label for="licnm" class="col-sm-4 col-form-label">License Name : </label>
					    	<div class="col-sm-8">
					      		<input type="text" readonly class="form-control-plaintext" id="licnm" value="">
					    	</div>
					  	</div>
					  	<div class="form-group row">
					    	<label for="modulenm" class="col-sm-4 col-form-label">Module Name : </label>
					    	<div class="col-sm-8">
					      		<input type="text" readonly class="form-control-plaintext" id="modulenm" value="">
					    	</div>
					  	</div>			     	
			     		<div class="form-group row">
					    	<label for="maxcnt" class="col-sm-4 col-form-label">라이선스 총 수 : </label>
					    	<div class="col-sm-8">
					      		<input type="text" readonly class="form-control-plaintext" id="maxcnt" value="">
					    	</div>
					  	</div>
			       		<div class="form-group row">
					    	<label for="peakcnt" class="col-sm-4 col-form-label">Peak 사용량 : </label>
					    	<div class="col-sm-8">
					      		<input type="text" readonly class="form-control-plaintext" id="peakcnt" value="">
					    	</div>
					  	</div>
			       		<div class="form-group row">
					    	<label for="avgcnt" class="col-sm-4 col-form-label">평균 사용 수 : </label>
					    	<div class="col-sm-8">
					      		<input type="text" readonly class="form-control-plaintext" id="avgcnt" value="">
					    	</div>
					  	</div>
					  	<div class="form-group row">
					    	<label for="avgper" class="col-sm-4 col-form-label">평균 백분율 : </label>
					    	<div class="col-sm-8">
					      		<input type="text" readonly class="form-control-plaintext" id="avgper" value="">
					    	</div>
					  	</div>
			       		<div class="form-group row">
					    	<label for="timelog" class="col-sm-4 col-form-label">분석시간 : </label>
					    	<div class="col-sm-8">
					      		<input type="text" readonly class="form-control-plaintext" id="timelog" value="">
					    	</div>
					  	</div>
					  	<div class="form-group row">
					    	<label for="datelog" class="col-sm-4 col-form-label">분석기간 : </label>
					    	<div class="col-sm-8">
					      		<input type="text" readonly class="form-control-plaintext" id="datelog" value="">
					    	</div>
					  	</div>
			     	</div>
			     
			     	<!-- Modal footer -->
			     	<div class="modal-footer">
			            <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			     	</div>		      
				</div>
		    </div>
		</div>	    	         				
	</div>
</article>	
</body>
</html>