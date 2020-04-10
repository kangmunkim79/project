<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/layout/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자관리</title>
</head>
<script type="text/javascript">
$(document.body).ready(function () {
    var myViewer_0 = new ax5.ui.mediaViewer({
        target: $("#media-viewer-target-0"),
        loading: {
            icon: '<i class="fa fa-spinner fa-pulse fa-2x fa-fw margin-bottom" aria-hidden="true"></i>',
            text: '<div>Now Loading</div>'
        },
        media: {
            prevHandle: '<i class="fa fa-chevron-left"></i>',
            nextHandle: '<i class="fa fa-chevron-right"></i>',
            width: 36, height: 36,
            poster: '<i class="fa fa-youtube-play" style="line-height: 32px;font-size: 20px;"></i>',
            list: [
                {
                    video: {
                        html: '<iframe src="${pageContext.request.contextPath}/resources/Photos/20191230_153144.mp4" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>',
                        poster: ''
                    }
                },
                {
                    video: {
                        html: '<iframe src="${pageContext.request.contextPath}/resources/Photos/20191230_153510.mp4" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>',
                        poster: ''
                    }
                },
                {
                    image: {
                        src: 'http://www.improgrammer.net/wp-content/uploads/2015/11/top-20-node-js-Frameworks-1.jpg',
                        poster: 'http://www.improgrammer.net/wp-content/uploads/2015/11/top-20-node-js-Frameworks-1.jpg'
                    }
                }
            ]
        }
    });
});
</script> 
<body>
<article> 
	<div class="container">
		<h2></h2>
		<div id="media-viewer-target-0"></div>			
	</div> 
</article>
</body>
</html>