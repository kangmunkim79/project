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
    var firstGrid = new ax5.ui.grid({        
        target: $('[data-ax5grid="first-grid"]'),
        columns: [
            {key: "delchk", label: "Delete", align: "center", sortable: false,
                width: 60,
            	editor: {
                    type: "checkbox", 
                    config: {
                        height: 17, 
                        trueValue: "Y", 
                        falseValue: "N"
                    }
            	}
            },
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
                }
                else if(this.key == 'usrType'){
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
                itemIcon: '<i class="fa fa-circle" aria-hidden="true"></i>'
            },
            columnKeys: {
                parentKey: "pmenucd",
                selfKey: "menucd"
            }
        }                  
    });
	go();
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
    $('[data-grid-control]').click(function () {
    	switch (this.getAttribute("data-grid-control")) {
        case "level1":
            alert(1);
            firstGrid.addRow($.extend({}, firstGrid.list[Math.floor(Math.random() * firstGrid.list.length)], {__index: undefined}));
            break;
        case "level2":
        	alert(2);
            firstGrid.addRow($.extend({}, firstGrid.list[Math.floor(Math.random() * firstGrid.list.length)], {__index: undefined}));
            break;    
        case "remove":
            if(firstGrid.getList("selected").length == 0){
            	dialog.alert("삭제할 목록을 선택하세요.");
                $('#alert-close').click(function () {
                    dialog.close();
                });
            }else{
            	dialog.confirm({
                    title: "Message",
                    msg: '삭제하시겠습니까?'
                }, function(){
                    if(this.key == "ok"){
                    	firstGrid.deleteRow("selected");
                        dialog.alert(firstGrid.getList("deleted").length+"건의 데이터가 삭제되었습니다.");
                        $('#alert-close').click(function () {
                            dialog.close();
                        });
                    }else if(this.key == "cancel"){
                        dialog.close();
                    }
                });
            }
            go();
            break;
        case "update":
            var updateIndex = Math.floor(Math.random() * firstGrid.list.length);
            firstGrid.updateRow($.extend({}, firstGrid.list[updateIndex], {price: 100, amount: 100, cost: 10000}), updateIndex);
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
		<h2>Menu list</h2> 
		<div style="padding: 10px;" align="right">
		    <button class="btn btn-sm btn-primary" data-grid-control="level1">Add Level 1</button>
		    <button class="btn btn-sm btn-primary" data-grid-control="level2">Add Level 2</button>
		    <button class="btn btn-sm btn-primary" data-grid-control="remove">Delete</button>
		    <button class="btn btn-sm btn-primary" data-grid-control="update">Save</button>
		    <button class="btn btn-sm btn-primary" data-grid-control="excel-export">Excel Export</button>
		</div>		
		<div style="position: relative;height:300px;" id="grid-parent">
		    <div data-ax5grid="first-grid" data-ax5grid-config="{showLineNumber: true, showRowSelector: true, header: {selector: false, align:'center'}}" style="height: 100%;"></div>
		</div>			
	</div> 
</article>
</body>
</html>