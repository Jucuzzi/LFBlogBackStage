//var androidutil = {
//  backApp: function(){
//     window.javaObjectFromApp.javaFun("111111");
//  }
//}
//export default androidutil
function backAndroidApp(){
	if (window.javaObjectFromApp) {
		 window.javaObjectFromApp.finishWebView();
	}
	//ios 返回pp
	 window.location.href='back://backToApp';
}
