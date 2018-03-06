<template>
	<div class="footerDiv">
		<div class="lineDiv"></div>
		<div class="footerContentDiv">
			<div id="heartDiv" class="heartDiv">
				<img v-on:click="clickHeart" id="heartImg" class="heartImg" alt="未选" src="../telehems/img/monthreportdetail/heart_line.png">
			</div>
			<div class="collectionDiv" >
				<span id ="collectionNum" class="collectionNum"  v-text="this.collecNumber"></span>
			</div>
			<div class="collectionTextDiv">
				<span class="collectionText">收藏</span>
			</div>
			<div class="discussDiv">
				<div cl
</template>ass="roundedDiv" @click="jump">
<div class="messageImgDiv">
  <img class="messageImg" src="../telehems/img/monthreportdetail/message.png">
  <div class="discussOuterNumDiv">
    <div class="discussNumDiv" v-show="this.commentNum>0">
      <span class="discussNum" v-text="this.commentNum"></span>
    </div>
  </div>
</div>
<span class="discussText">参与讨论</span>
</div>

</div>
</div>
</div>
<script type="text/javascript">
 export default {
 	props:['collecNumber','collected','commentNum','informationId','isFisrtError'],
 	data(){
		return{

		}
	},
 	created(){

 	},
 	updated(){
 		if (this.collecNumber == 0) {
 			document.getElementById("collectionNum").innerText = "";
 		}
 		var checkimg = document.getElementById("heartImg");
 		if (this.collected==true){
 			checkimg.src = require('../telehems/img/monthreportdetail/heart.png');
	        checkimg.alt ="选中";
 		} else{
 			checkimg.src= require('../telehems/img/monthreportdetail/heart_line.png');
	        checkimg.alt = "未选";
 		}
 	},
 	methods: {
 		clickHeart:function () {
		    var checkimg = document.getElementById("heartImg");
		    var collectionNum = document.getElementById("collectionNum");
		    var num = 0;
		    if (collectionNum.innerText != ""){
		    	num = parseInt(collectionNum.innerText);
		    }
	        if(checkimg.alt == "选中"){
	            checkimg.src= require('../telehems/img/monthreportdetail/heart_line.png');
	            checkimg.alt = "未选";
            if (num - 1 <= 0) {
              collectionNum.innerText = "";
            } else {
              collectionNum.innerText = num - 1;
            }
            //删除一个收藏
	            var self = this;
				ajaxRequest({
					url : "/collectionDelete",
					data : {
						"informationId":self.informationId
					},
					async : true,
					success : function(dataJson) {

					},
					error : function() {
						alertSysErrorTip(self);
					}
				});

	        } else{
	           checkimg.src = require('../telehems/img/monthreportdetail/heart.png');
	           checkimg.alt ="选中";
	           collectionNum.innerText = num + 1;
	           this.collectionNum = num + 1;
	           //保存一个收藏
	           var self = this;
				ajaxRequest({
					url : "/addCollections",
					data : {
						"informationId":self.informationId
					},
					async : true,
					success : function(dataJson) {

					},
					error : function() {
						alertSysErrorTip(self);
					}
				});
	        }
	    },
	    jump:function() {
	    	if (this.isFisrtError){
					alertLoadError(this);
					return
			}
	    	var self = this;
			this.$router.push({
				path:'/Comments',
				query:{
					informationId:self.informationId
				}

			});
		}
 	}
}
</script>

<style>
	.footerDiv{
		height: 46px;
		width: 100%;
		background: #fafbfb;
		position: fixed;
		bottom: 0;
		left: 0;
	}
	.footerContentDiv{
		height: 100%;
		width: calc(100% - 40px);
		display: flex;
		margin-left: 20px;
        flex-direction: row;
		align-items:center;
		background: #fafbfb;
	}
	.heartDiv{
		height: 100%;
		width: 24px;
	}
	.heartImg{
		height: 24px;
		width: 24px;
		margin-top: 11px;
	}
	.collectionDiv{
		height: 100%;
	}

	.lineDiv{
		width: 100%;
		height: 1px;
		background-color: #dde1e1;
	}
	.collectionNum{
		font-size: 12px;
		line-height:20px;
		margin-left: 2px;
		color: #8f9191;
	}
	.collectionText{
		font-size: 16px;
		color: #8f9191;
		white-space:nowrap;
		margin-left: 6px;
	}
	.discussDiv{
		height: 100%;
		width: 100%;
		display: flex;
		float: right;
		flex-direction: row;
		align-items:center;
		justify-content: flex-end;

	}
	.roundedDiv{
		height: 30px;
		width: 120px;
		background: #00abfd;
		border-radius:15px;

		display: flex;
		flex-direction: row;
		align-items:center;
	}
	.messageImgDiv{
		width: 40px;
		height: 24px;
		margin-left: 10px;
		margin-top: 5px;
		text-align: center;
		position:relative;
	}
	.messageImg{
		width: 24px;
		height: 20px;
		margin-left: -20px;
	}
	.discussOuterNumDiv{
		width: 100%;
		height: 30px;
		display: flex;
		flex-direction: row;
		align-items:center;
		justify-content: center;
	}
	.discussNumDiv{
		background: red;
		min-width: 15px;
		height: 12px;
		border-radius:7px;
		font-size: 0px;
		margin-top: -73px;
		text-align: center;
	}
	.discussNum{
		font-size: 8px;
		color: white;
		margin-left: 2px;
		margin-right: 2px;
		margin-top: 1px;
		margin-bottom: 1px;
	}
	.discussText{
		font-size: 16px;
		color: white;
	}
</style>
