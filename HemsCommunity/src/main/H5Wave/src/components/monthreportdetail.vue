<template>
	<div class="detail-root">
		<detailheader :type="type"></detailheader>
		<!-- 网络异常的显示 -->
		<div class="detail-net-error" v-if="netError">
				<img src="../assets/img/404.png"></img>
				<label style="color: lightgray; font-weight: 300; margin-bottom: 10px;">当前网络较差，请重新加载</label>
		</div>
		<div v-else class="info-outer-div">
			<span class="info-title textcenter" v-text="infocontent.title">11</span>
			<div class="info-detail-div">
				<span class="info-detail" v-text="'来源：'+infocontent.source+'  '">来源：22</span>
				<span class="info-detail" v-text="'作者：'+infocontent.author+'  '">作者：33</span>
				<span class="info-detail show-time" id="showTime"></span>
			</div>		
			<div class="ql-editor info-content-div" v-html="content">

			</div>

		</div>
		<detailfooter :collecNumber="collectNumber" :collected="collected" :commentNum="commentNum" 
			:informationId="informationId" :isFisrtError="isFisrtError"></detailfooter>
	</div>

</template>

<script>
import detailheader from '@/components/detailheader.vue'
import detailfooter from '@/components/detailfooter.vue'

export default {
	data(){
		return{
			infocontent:{
				detailContent:"",
				source:"",
				author:"",
				releaseTime:"",
				title:""
			},
			collectNumber:"",
			collected:false,
			commentNum:"0",
			informationId:this.$route.query.informationId,
			type:this.$route.query.type,
			netError:false,
			isFisrtError:false
		}
	},
	mounted(){
        this.handleShow();
     },
	methods:{
		 handleShow() {
		 	
		 	var self = this;
				ajaxRequest({
					url : "queryinformationById",
					data : {
						"informationId":self.informationId,
					},
					async : true,
					success : function(dataJson) {
						self.infocontent = dataJson;
//						document.getElementById('info_content').innerHTML=self.infocontent.detailContent;
						$('#showTime').html(
//							self.infocontent.releaseTime.substring(0,4)+"-"+
				    		" "+self.infocontent.releaseTime.substring(4,6)+"-"+
							self.infocontent.releaseTime.substring(6,8)+" "+
							self.infocontent.releaseTime.substring(8,10)+":"+
							self.infocontent.releaseTime.substring(10,12)
//							+":"+
//							self.infocontent.releaseTime.substring(12,14)
						);
						
						var imgContent =[];
						imgContent = self.infocontent.detailContent.split("<img");
						var htmlString = "";
						for(var i=0; i < imgContent.length; i++) {
							if (i != imgContent.length-1) {
								htmlString +=imgContent[i] + 
								'<br><img width="60%" style="padding-left:20%;padding-top:10px;padding-bottom:10px;"';
							} else {
								htmlString +=imgContent[i];
							}
						}
//						alert(htmlString);
						self.content = htmlString;
//						$('#info_content').html(htmlString);
						self.isFisrtError = false;
					},
					error : function() {
						alertSysErrorTip(self);
						self.isFisrtError = true;
					}
				});
                ajaxRequest({
					url : "/queryCollectionsInfo",
					data : {
						"informationId":self.informationId
					},
					async : true,
					success : function(dataJson) {
						self.collectNumber = dataJson.collectNum;
						self.collected = dataJson.isCollect;
						self.commentNum = dataJson.commentNum;
						self.isFisrtError = false;
					},
					error : function() {
						alertSysErrorTip(self);
						self.isFisrtError = true;
					}
				});
           },
	},
 	components:{
		detailheader,
		detailfooter,
	},

}
</script>
<style>
p{
color: #000000;	
margin: 0;	
}


.detail-root{
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: flex-start;
}
.info-outer-div{
	width: calc(100% - 20px);
	height: calc(100% - 86px);
	overflow:auto;
	background-color: white;
	margin-left: 20px;
	
}	

.textcenter{
	text-align: center;
	display:inline-block;
}
.info-title{
	width: 100%;
	margin-top: 10px;
	font-size: 22px;
}
.info-detail-div{
	text-align: left;
	width: 100%;
	margin-top: 2%;
	white-space: nowrap;
}
.info-detail{
	font-size: 13px;
	white-space: nowrap;
	color: #7e7e7e;
	line-height: 17px;
	margin-right: 5px;
}
.info-content-div{
	width: 100%;
	margin-top: 3%;
	padding: 0;
	height: auto;
	margin-right: 20px;
}
#newsImg{
	width: 60%;
	height: 180px;
	margin-left: 20%;
}
.detail-net-error{
	width: 100%;
	height:100%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}
.null-div{
	width: 100%;
	height: 46px;
}
.show-time{
	float: right;
	margin-right: 20px;
	top: 2%;
}
</style>