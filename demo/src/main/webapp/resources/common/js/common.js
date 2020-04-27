var picker = new ax5.ui.picker();
var mask = new ax5.ui.mask();
var API_SERVER = "http://api-demo.ax5.io";  	
var dialog = new ax5.ui.dialog({
    title: "Message"
});    
$('#alert-close').click(function () {
    dialog.close();
});
function common_gridloading_open(id){	
	var gridid = "#"+id;
	mask.open({
        // position: "fixed", // Maybe options that may be required to you
        zIndex: 99,
        target: $(gridid).get(0),
        content: '<h1><i class="fa fa-spinner fa-spin"></i> Loading</h1>'
		/*onClick: function(){
            console.log(this);
        } */
    });
}
function common_gridloading_close(){
	mask.close();
}
/**
 * 좌측문자열채우기
 * @params
 *  - str : 원 문자열
 *  - padLen : 최대 채우고자 하는 길이
 *  - padStr : 채우고자하는 문자(char)
 */
function common_lpad(str, padLen, padStr) {
    if (padStr.length > padLen) {
        console.log("오류 : 채우고자 하는 문자열이 요청 길이보다 큽니다");
        return str;
    }
    str += ""; // 문자로
    padStr += ""; // 문자로
    while (str.length < padLen)
        str = padStr + str;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;
}

/**
 * 우측문자열채우기
 * @params
 *  - str : 원 문자열
 *  - padLen : 최대 채우고자 하는 길이
 *  - padStr : 채우고자하는 문자(char)
 */
function common_rpad(str, padLen, padStr) {
    if (padStr.length > padLen) {
        console.log("오류 : 채우고자 하는 문자열이 요청 길이보다 큽니다");
        return str + "";
    }
    str += ""; // 문자로
    padStr += ""; // 문자로
    while (str.length < padLen)
        str += padStr;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;
}

function common_npercent(cnt){
	var str = [];
    var k = 0;
    if(cnt <= 10) {
    	k = 1;
    }
    if(cnt > 10 && test <= 20) {
    	k = 2;
    }
    if(cnt > 20 && test <= 40) {
    	k = 4;
    }
    if(cnt > 40 && test <= 80) {
    	k = 8;
    }
    if(cnt > 80 && test <= 160) {
    	k = 16;
    }
    if(cnt > 160 && test <= 320) {
    	k = 32;
    }
    if(cnt > 320 && test <= 640) {
    	k = 64;
    }
    if(cnt > 640) {
    	k = 100;
    }
    
    var kk = 0;
    for(var i=0;i<cnt;i++) {    	
    	if(i == 0) {
    		str[kk] = i;
    		kk++;
    	} else {
        	if(i%k == 0) {
        		str[kk] = i;
        		kk++;
        	}
    	}        	        	
    }
    
    str[kk] = cnt;
	return str;
}