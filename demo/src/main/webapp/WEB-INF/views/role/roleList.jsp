<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>권한 관리</title>
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
            {key: "rolecd", label: "권한코드", width: 120, align: "center"},
            {key: "rolenm", label: "권한명", width: 120, align: "cneter",
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
	                }
	            }  
            },          
            {key: "useflag", label: "사용여부", width: 80, align: "center",
	        	editor: {
	                type: "checkbox", 
	                config: {
	                    height: 17, 
	                    trueValue: "Y", 
	                    falseValue: "N"
	                }
	        	}
	        }
        ],
        body: {
            onClick: function () {
                if (this.column.key == "rolecd" || this.column.key == "rolenm"){
                	menugo(this.item.rolecd);                  	
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
            {key: "menunm", label: "Name", width: 120, align: "left", treeControl: true},            
            {key: "menucd", label: "Id", width: 100, align: "center"},
            {key: "useflag", label: "사용여부", align: "center", sortable: false,
            	editor: {
                    type: "checkbox", 
                    config: {
                        height: 17, 
                        trueValue: "Y", 
                        falseValue: "N"
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
        },        
        tree: {
            use: true,
            indentWidth: 10,
            arrowWidth: 15,
            iconWidth: 18,
            icons: {
                openedArrow: '<i class="fa fa-caret-down" aria-hidden="true"></i>',
                collapsedArrow: '<i class="fa fa-caret-right" aria-hidden="true"></i>',
                groupIcon: '<i class="fa fa-folder-open" aria-hidden="true"></i>',
                collapsedGroupIcon: '<i class="fa fa-folder" aria-hidden="true"></i>',
                itemIcon: '<i class="fa fa-folder" aria-hidden="true"></i>'
            },
            columnKeys: {
                parentKey: "pmenucd",
                selfKey: "menucd"
            }
        }                  
    });    
	rolego();
	menugo();
	
	$('[data-grid-control]').click(function () {
    	switch (this.getAttribute("data-grid-control")) {
        case "add":
            var cnt = firstGrid.list.length;
            var maxNoArray = [];
            for(var i=0;i<cnt;i++){         		
            	var menucdNo = firstGrid.list[i].rolecd.substring(4);
				maxNoArray.push(menucdNo);            		
            }
            if(cnt == 0){
            	maxNoArray.push("0000");
            }
            var maxNo = Math.max.apply(null, maxNoArray);
            var rolecd = "ROLE"+common_lpad(maxNo+1, 4, "0");
			var addRowParam = {"rolecd":rolecd,"rolenm":null,"useflag":"Y"};
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
                    	roleSave(upList);
                    }else if(this.key == "cancel"){
                        dialog.close();
                    }
                });
            }            
            break; 
        case "updateAuth":
        	var modList = firstGrid2.getList("modified");
            var updateList = firstGrid2.list;
            if(modList.length == 0){
            	dialog.alert("변경된 사항이 없습니다.");
            }else{
            	dialog.confirm({
                    title: "Message",
                    msg: '저장하시겠습니까?'
                }, function(){
                    if(this.key == "ok"){
                    	var upList = [];
                    	var rolecd = updateList[0].rolecd
                        for(var i=0;i<updateList.length;i++){
                        	upList.push(updateList[i]);
                        }
                    	roleMenuSave(upList, rolecd);
                    }else if(this.key == "cancel"){
                        dialog.close();
                    }
                });
            }            
            break;            
    	}        
    	            
    }); 


	
    // 그리드 데이터 가져오기
    function rolego(){
	    $.ajax({
	        method: "POST",
	        url: "/role/roleList",
	        success: function (result) {
	            firstGrid.setData(result.roleList);
	        }
	    });
    }

    function menugo(rolecd){
    	var param = {"rolecd":rolecd};	
	    $.ajax({
	    	type: "POST",
	        contentType: "application/json",
	        url: "/role/roleMenuList",
	        data: JSON.stringify(param),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (result) {
	            firstGrid2.setData(result.menuList);
	        }
	    });
    }
    
    function roleDelete(delList, delCnt){
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/role/deleteRole",
	        data: JSON.stringify(delList),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
                dialog.alert(delCnt+"건의 데이터가 삭제되었습니다.");
                rolego();
	        }

	    });
    }   

    function roleSave(upList){
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/role/mergeRole",
	        data: JSON.stringify(upList),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
                dialog.alert("저장되었습니다.");
                rolego();
	        }

	    });
    }    

    function roleMenuSave(upList, rolecd){
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/role/mergeRoleMenu",
	        data: JSON.stringify(upList),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
                dialog.alert("저장되었습니다.");
                menugo(rolecd);
	        }

	    });
    }
        
}); 
</script> 
<body>
	<div class="container tb-basic">
		<div class="row">
			<div class="col-sm-2">
				<h3>권한 관리</h3>
			</div>
			<div class="col-sm-4">
				<div style="padding: 10px;" align="right"> 
				    <button class="btn btn-sm btn-primary" data-grid-control="add">Add</button>
				    <button class="btn btn-sm btn-primary" data-grid-control="remove">Delete</button>
				    <button class="btn btn-sm btn-primary" data-grid-control="update">Save</button>
				</div>			
			</div>
			<div class="col-sm-2">
			</div>
			<div class="col-sm-4">
				<div style="padding: 10px;" align="right"> 
				    <button class="btn btn-sm btn-primary" data-grid-control="updateAuth">Save</button>
				</div>			
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
				<div style="position: relative;height:500px;" id="grid-parent2">
		    		<div data-ax5grid="first-grid2" data-ax5grid-config="{
		    							showLineNumber: true,
		    							showRowSelector: false,
		    							header: {selector: false, align:'center'}
		    							}" style="height:500px;"></div>
				</div>
			</div>
		</div>				
	</div> 
</body>
</html>