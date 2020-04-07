<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>userInfo</title>
</head>
<script> 
$(document).on('click', '#userAddBtn', function(e){
	//e.preventDefault();
	location.href = "${pageContext.request.contextPath}/userInfo/userInfoUpdate";
});

$(document.body).ready(function () {
    var API_SERVER = "http://api-demo.ax5.io";
    var firstGrid = new ax5.ui.grid();

    firstGrid.setConfig({
        target: $('[data-ax5grid="first-grid"]'),
        showLineNumber: true,
        showRowSelector: true,
        header: {align:'center'},
        columns: [
            {key: "menucd", label: "Id", width: 100, align: "center",
	            enableFilter: true,
	            editor: {
	                type: "text", disabled: function () {
	                    // item, value
	                    return false;
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
            {key: "usrtype", label: "Type", width: 100, align: "left",
            	editor: {
                    type: "select", config: {
                        columnKeys: {
                            optionValue: "CD", optionText: "NM"
                        },
                        options: [
                            {CD: "IN", NM: "IN"},
                            {CD: "EX", NM: "EX"}
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
            columnHeight: 26,
            onDataChanged: function () {
                if (this.key == 'useflag') {
                    this.self.updateChildRows(this.dindex, {isChecked: this.item.isChecked});
                }
                else if(this.key == '__selected__'){
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
                parentKey: "pid",
                selfKey: "id"
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
        url: "/menu/getGridMenuList",
        success: function (res) {
            firstGrid.setData(res.mList);
        }
    });
    $('[data-grid-control]').click(function () {
    	firstGrid.exportExcel("grid-to-excel.xls");            
    });        
}); 
</script> 
<article> 
	<div class="container"> 
		<h2>Menu list</h2> 
		<div style="position: relative;height:300px;" id="grid-parent">
		    <div data-ax5grid="first-grid" data-ax5grid-config="{}" style="height: 100%;"></div>
		</div>	
		<div style="padding: 10px;">
		    <button class="btn btn-sm btn-primary" data-grid-control="excel-export">Excel Export</button>
		    <button class="btn btn-sm btn-primary" id="userAddBtn">Add</button>
		</div>		
	</div> 
</article>