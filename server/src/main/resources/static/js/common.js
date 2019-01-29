
function getCookie(objName) {
    var arrStr = document.cookie.split("; ");
    for (var i = 0; i < arrStr.length; i++) {
        var temp = arrStr[i].split("=");
        if (temp[0] == objName) return unescape(temp[1]);
    }
    return "";
}

function isLogin() {
    return true;
    //return getCookie("userName") != "";
}

function isAuthorized() {
    return true;
    return getCookie("authorized") != "";
}

function initAll() {
    //填充所有的下来列表
}

function handleCallback(e) {
	if (e.message) {
        alert(e.message)
    }
    if (e.url) {
        window.location.href = e.url
    }
}

function doPost(path, data, successCB) {
	 if(window.location.href.indexOf('file://') == 0) {
	 	//path = "http://127.0.0.1:8999" + path
	 	path = "http://106.14.160.40:8999" + path
	 } 
    
    $.ajax({
        url: path,
        data: JSON.stringify(data),
        contentType: "application/json;charset=utf-8",
        type: "post",
        dataType: "json",
        success: function (e) {
        	try {
                if (successCB == null) {
                	handleCallback(e)
                } else {
        		  successCB(e)
                }
        	} catch(err) {
        		alert(err)
        	} 
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("Error: " + errorThrown);
		}
    })
}

function doLogin() {
	doPost("/smsLogin", getFormDat())
}

function doQuery() {
    doPost("/smsLogin", getFormDat())
}

function getFormDat() {
	var formArray = $('#postForm').serializeArray();
	var returnArray = {};
	for (var i = 0; i < formArray.length; i++){
    	returnArray[formArray[i]['name']] = formArray[i]['value'];
	}
	return returnArray;
}


