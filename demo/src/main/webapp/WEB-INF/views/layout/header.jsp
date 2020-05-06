<%@ include file="/WEB-INF/views/common/linkScriptSet.jsp"%>
<script type="text/javascript">
$(document).ready(function () {
	var param = {};
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/menu/getMenuList",
        data: JSON.stringify(param),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
             
        	var menuHtml = "";
            $.each(data.menuList, function (i, item) {
                if(item.submenuChk == "N"){
                	menuHtml += '<li class="nav-item">';
                	menuHtml += '<a class="nav-link" href="${pageContext.request.contextPath}' + item.urlpath + '">' + item.menunm + '</a>';
                	menuHtml += '</li>';
                }else{
	           		menuHtml += '<li class="nav-item dropdown">';
	           		menuHtml += '<a class="nav-link dropdown-toggle" href="#" id="' + item.menucd + '" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' + item.menunm + '</a>';
	           		menuHtml += '<div class="dropdown-menu" aria-labelledby="' + item.menucd + '">';
	       		    $.each(data.subMenuList, function (j, subItem) {
	           		    if(item.menucd == subItem.pmenucd){              
	       		    		menuHtml += '<a class="dropdown-item" href="${pageContext.request.contextPath}' + subItem.urlpath + '">' + subItem.menunm + '</a>';
	           		    }
	       		 	});    
	       		 	menuHtml += '</div>';
	       		 	menuHtml += '</li>';
                }
            });
            $("#menuSet").append(menuHtml);
        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

});
</script>
<!-- header -->
<nav class="navbar navbar-expand-sm navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">DILMS</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample03" aria-controls="navbarsExample03" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
	<div class="collapse navbar-collapse" id="navbarsExample03">
    	<ul class="navbar-nav mr-auto" id="menuSet">
   		
	    </ul>
        <ul class="navbar-nav navbar-right top-nav">
            <li class="nav-item dropdown">
            	<a class="nav-link dropdown-toggle" href="#" id="userMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><sec:authentication property="principal.name"/></a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userMenu">
                	<a class="dropdown-item" href="${pageContext.request.contextPath}/userInfo/userPasswordUpdate">Change Password</a>
                	<a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a>
                </div>
            </li>        
        </ul>    
    </div>
</nav>
<!-- header -->
<!-- Footer -->
<nav class="navbar navbar-expand-sm navbar-dark fixed-bottom bg-dark">
  	<a class="navbar-brand navbar-center" href="#">ⓒ 2020 Doosan Corporation</a>     
</nav>
<!-- Footer -->