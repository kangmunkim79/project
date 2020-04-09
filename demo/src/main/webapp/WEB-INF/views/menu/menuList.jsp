<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자관리</title>
</head>
<script> 
$(document).on('click', '#userAddBtn', function(e){
	//e.preventDefault();
	location.href = "${pageContext.request.contextPath}/userInfo/userInfoUpdate";
});

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
            {key: "menunm", label: "Name", width: 120, align: "left", treeControl: true,
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
	                }
	            }
            },            
            {key: "menucd", label: "Id", width: 100, align: "center",
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
	                }
	            }
            },
            {
                key: "usrtype", label: "userType", editor: {
                type: "select", config: {
                    columnKeys: {
                        optionValue: "CD", optionText: "NM"
                    },
                    options: [
                        {CD: "IN", NM: "IN: ADMIN"},
                        {CD: "EX", NM: "EX: USER"}
                    ]
                }
                }
            },
            {key: "depth", label: "Depth", width: 80, align: "center",
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
	                }
	            }
            },
            {key: "urlpath", label: "Url", width: 120, align: "left",
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
	                }
	            }
            },
            {key: "sortseq", label: "Sort", width: 80, align: "center",
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
	                }
	            }
            },
            {key: "useflag", label: "Use Flag", align: "center", sortable: false,
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
                }else if(this.key == 'usrType'){
                    this.self.updateChildRows(this.dindex, {__selected__: this.item.__selected__});
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
	go();

    $('[data-grid-control]').click(function () {
    	switch (this.getAttribute("data-grid-control")) {
        case "level1":
            var cnt = firstGrid.list.length;
            var maxNoArray = [];
            var maxSeqArray = [];
            for(var i=0;i<cnt;i++){
            	var depth = firstGrid.list[i].depth;            		
            	var menucdNo = firstGrid.list[i].menucd.substring(4);
            	if(depth == 1){
            		maxNoArray.push(menucdNo);            		
                }
            	maxSeqArray.push(firstGrid.list[i].sortseq);
            }
            var maxNo = Math.max.apply(null, maxNoArray);
            var maxSeq = Math.max.apply(null, maxSeqArray);
            var menucd = "SHCH"+common_lpad(maxNo+1, 4, "0");
			var addRowParam = {"menucd":menucd,"pmenucd":null,"menunm":null,"depth":1,"urlpath":null,"usrtype":"IN","useflag":"Y","sortseq":maxSeq+1,"sts":"I"};
            firstGrid.addRow($.extend({}, addRowParam, 0));
            break;
        case "level2":
            var list = firstGrid.getList("selected");
            var listCnt = list.length;
        	if(listCnt == 0){
            	dialog.alert("상위 메뉴를 먼저 선택하세요.");
                return;
            }
        	if(listCnt > 1){
        		dialog.alert("상위 메뉴를 하나만 선택하세요.");
                return;
            }
			if(list[0].depth > 1 ){
            	dialog.alert("상위 메뉴를 먼저 선택하세요.");
                return;
			}	
			var cnt = firstGrid.list.length;
            var maxNoArray = [];
            for(var i=0;i<cnt;i++){
            	var depth = firstGrid.list[i].depth;            		
            	var menucdNo = firstGrid.list[i].menucd.substring(4);
            	if(depth == 2){
            		maxNoArray.push(menucdNo);            		
                }
            }
            var maxNo = Math.max.apply(null, maxNoArray);
            var menucd = "SHCH"+common_lpad(maxNo+1, 4, "0");            
			var addRowParam = {"menucd":menucd,"pmenucd":list[0].menucd,"menunm":null,"depth":2,"urlpath":null,"usrtype":"IN","useflag":"Y","sortseq":list[0].sortseq,"sts":"I"};
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
	        method: "POST",
	        url: "/menu/getGridMenuList",
	        success: function (res) {
	            firstGrid.setData(res.mList);
	        }
	    });
    }

    function menuDelete(delList, delCnt){
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/menu/deleteMenuList",
	        data: JSON.stringify(delList),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
                dialog.alert(delCnt+"건의 데이터가 삭제되었습니다.");
                reMenuSet();
	        }

	    });
    }   

    function save(upList){
	    $.ajax({
	        type: "POST",
	        contentType: "application/json",
	        url: "/menu/saveMenuList",
	        data: JSON.stringify(upList),
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
                dialog.alert("저장되었습니다.");
                reMenuSet();
	        }

	    });
    }

    function reMenuSet(){
        var url = "${pageContext.request.contextPath}/menu/menuList";
    	location.href = url;
    }
}); 
</script> 
<body>
<article> 
	<div class="container"> 
		<h2>Menu list</h2> 
		<div style="padding: 10px;" align="right">
		    <button class="btn btn-sm btn-primary" data-grid-control="level1">Add Level 1</button>
		    <button class="btn btn-sm btn-primary" data-grid-control="level2">Add Level 2</button>
		    <button class="btn btn-sm btn-primary" data-grid-control="remove">Delete</button>
		    <button class="btn btn-sm btn-primary" data-grid-control="update">Save</button>
		    <button class="btn btn-sm btn-primary" data-grid-control="excel-export">Excel Export</button>
		</div>		
		<div style="position: relative;height:400px;" id="grid-parent">
		    <div data-ax5grid="first-grid" data-ax5grid-config="{showLineNumber: true,showRowSelector: true,header: {selector: false, align:'center'}}" style="height: 100%;"></div>
		</div>			
	</div> 
</article>
</body>
</html>