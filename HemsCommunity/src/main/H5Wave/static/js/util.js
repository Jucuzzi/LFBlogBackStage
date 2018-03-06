var searchUrl = window.location.search;
var isFirstLogin = true;
var loadingErrorTip = "网络加载异常，请返回上一页重新加载";
var sysErrorTip = "系统繁忙";
var appSelf;
(function($) {
	//首先备份下jquery的ajax方法  
	var _ajax = $.ajax;

	//重写jquery的ajax方法  
	$.ajax = function(opt) {
		//备份opt中error和success方法  
		var fn = {
			error: function(XMLHttpRequest, textStatus, errorThrown) {},
			success: function(data, textStatus) {}
		}
		if(opt.error) {
			fn.error = opt.error;
		}
		if(opt.success) {

			fn.success = opt.success;
		}

		//扩展增强处理  
		var _opt = $.extend(opt, {
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				//错误方法增强处理  
				fn.error(XMLHttpRequest, textStatus, errorThrown);
			},
			success: function(data, textStatus) {
//				alert(JSON.stringify(data));
				if(data && data.sysError == "session_timeout") {
					alertTip(appSelf, "操作超时");
					backApp();
					//如果超时就处理 ，指定要跳转的页面(比如登陆页)
//					setTimeout(() => {
//						
//					}, 2000);
				}
				//成功回调方法增强处理  
				fn.success(data, textStatus);
			},
			beforeSend: function(XHR) {
				//提交前回调方法  
			},
			complete: function(XHR, TS) {
				//请求完成后回调函数 (请求成功或失败之后均调用)。  

			}
		});
		return _ajax(_opt);
	};
})(jQuery);

function ajaxRequest(obj) {

	//	var urlhead="http://192.168.155.1:8080/HemsCommunity/";
//		var urlhead="http://localhost:8080/HemsCommunity/";
//	var urlhead = "http://10.10.11.63:8080/HemsCommunity/";
		var urlhead="https://china.telehems.com/HemsCommunity/";
//	alert(obj);
//	alert("我要开始请求后台了")
	$.ajax({
		url: urlhead + obj.url,
		type: "post",
		dataType: "json",
		data: obj.data ? obj.data : {},
		async: obj.async ? true : false,
		success: obj.success,
		error: obj.error ? obj.error : function() {
			swalErrorInfo("系统繁忙");
		},
		xhrFields: {
			withCredentials: true
		}
	});
//	alert(urlhead + obj.url);
}

function backApp() {
//	alert("我进了util.js的方法了");
	if(window.javaObjectFromApp) {
		window.javaObjectFromApp.finishWebView();
	}
	//ios 返回pp
	window.location.href = 'back://backToApp';
}

function detectOS() {
	var sUserAgent = navigator.userAgent;

	var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
	var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
	if(isMac) return "Mac";
	var isUnix = (navigator.platform == "X11") && !isWin && !isMac;
	if(isUnix) return "Unix";
	var isLinux = (String(navigator.platform).indexOf("Linux") > -1);

	var bIsAndroid = sUserAgent.toLowerCase().match(/android/i) == "android";
	if(isLinux) {
		if(bIsAndroid) return "Android";
		else return "Linux";
	}
	if(isWin) {
		var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;
		if(isWin2K) return "Win2000";
		var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 ||
			sUserAgent.indexOf("Windows XP") > -1;
		if(isWinXP) return "WinXP";
		var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;
		if(isWin2003) return "Win2003";
		var isWinVista = sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;
		if(isWinVista) return "WinVista";
		var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;
		if(isWin7) return "Win7";
	}
	return "other";
}

function alertLoadError(self) {
	self.$root.eventHub.$emit('muDialog', loadingErrorTip);
}

function alertTip(self, tip) {
	self.$root.eventHub.$emit('muDialog', tip);
}

function alertSysErrorTip(self) {
	self.$root.eventHub.$emit('muDialog', sysErrorTip);
}