<template>
	<div class="demo-infinite-container">
		<div class="titleBar">
			<div class="leftDiv"> 
				<img class="leftImg" @click="goback" src="../telehems/img/common/title_arrow_left_white.png"/>		
			</div>
			<div class="centerDiv"> 
				<span class="titleName">评论</span>
			</div>
			
		</div>
		<div id="list_div" class="list-div">	
			<mu-list class="list-style" v-show="isHasData">
				<template v-for="item in list">
					<div v-on:click="open(item)">
						<!--<mu-list-item :title="item"/>-->
						<div id="mainDiv" style="text-align: left;">
							<img :src="item.imgPath"></img>
							<label id="name">{{item.name.length > 10 ? item.name.substring(0,10)+'...' : item.name}}</label>
							<!--<label id="level">{{item.level}}</label>-->
							<!--<label id="time">{{item.time}}</label>-->
						</div>
						<mu-content-block style="text-align: left;margin:0px;padding: 0px;padding-top: 10px;padding-bottom: 10px;">
							<label style="font-weight: 400; margin-left: 60px;margin-right: 1%;margin-top: -5.5%;">{{item.content}}</label>
						</mu-content-block>
						<div id="count" onclick="event.cancelBubble=true" >
							<div id="leftDiv">
								<!--<label id="thumbCount">{{item.preferenceCount + "个赞"}}</label>-->
								<label id="time" v-text="showTime(item.time)"></label>
							</div>
						
							<div id="rightDiv" v-on:click="thumbsUpOrCancel(item)">
								<label id="thumbCount" style="margin-right: 20%;">{{item.preferenceCount}}</label>
								<img id="thumb" :src="item.isThumbedImgSrc"></img>
								<!--<label id="isthumb" style="font-weight: 400;">{{item.isThumbed == 1?'取消':'点赞'}}</label>-->
							</div>
						</div>
						<mu-divider/>
					</div>
				</template>
			</mu-list>
	
			<mu-infinite-scroll :scroller="scroller" :loading="loading" @load="loadMore" v-show="isHasData"/>
			<div v-show="!isHasData" class="comment-nodata-div">
				<img class="comment-nodata-img" src="../assets/img/history_empty.png">
			</div>
		</div>	
		<div id="bottomDiv">
			<div style="width:100%; margin: 5px; margin-left: 10px;  position:fixed; text-align: left;">
				<mu-text-field :hintText="hintText" v-model="inputStr" v-focus="focusStatus" class="comment-text-input"/><br/>
			</div>
			<div style="float: right;margin: 5px;">
				<mu-flat-button @click="reply(inputStr)" backgroundColor="#2AB4FA" color="#FFF" label="发表" class="demo-flat-button" />
			</div>

		</div>

		<mu-dialog :open="dialog" title="请选择" @close="close">
			<mu-menu>
				<mu-menu-item :title=" menu" v-for="menu, index in menus" @click="click(index)" :key="index" />
			</mu-menu>
		</mu-dialog>

		<mu-dialog :open="selfDialog" title="请选择" @close="selfClose">
			<mu-menu>
				<mu-menu-item :title="selfMenus" v-for="selfMenus, index in selfMenus" @click="selfClick(index)" :key="index" />
			</mu-menu>
		</mu-dialog>

		<mu-dialog :open="reportedDialog" title="请选择举报理由" @close="reportedClose">
			<mu-menu>
				<mu-menu-item :title="reports" v-for="reports, index in reports" @click="reportedClick(index)" :key="index" />
			</mu-menu>
		</mu-dialog>

		<mu-dialog :open="tipDialog" title="温馨提示" @close="tipClose">
			举报成功，小赫对恶意发言的行为绝不姑息！
			<mu-flat-button slot="actions" primary @click="tipClose" label="确定" />
		</mu-dialog>

	</div>
</template>

<script>
	export default {
		data() {
			const menus = []
			const selfMenus = []
			const list = []
			const reports = []
			const listTemp = {};
			menus.push("回复");
			menus.push("举报");
			menus.push("取消");
			selfMenus.push("回复");
			selfMenus.push("删除");
			selfMenus.push("取消");
			return {
				hintText:"提示文字",
				list:[],
				num: 10,
				pageNum: 1,
				loading: false,
				scroller: null,
				dialog: false,
				reportedDialog: false,
				tipDialog: false,
				selfDialog: false,
				clickname: "",
				inputStr: '',
				menus,
				reports,
				selfMenus,
				selfUserId: this.$route.query.userId,
				selectItem: [],
				focusStatus: true,
				informationId:this.$route.query.informationId,
				reportTypeList:[],
				canLoading:true,
				isHasData:true,
				informationDataArr:[],
				isReply:false,
				quaryTime:""
			}
		},		
		mounted() {
			this.scroller = document.getElementById("list_div");
			this.initRefresh();
		},
		directives: {
			focus: {
				inserted: function(el, {
					value
				}) {
					if(value) {
						el.focus();
					}
				}
			}
		},
		methods: {
			
			showTime(val){
				if (val) {
					return val.substring(5,16)
				} else {
					return ""
				}
				
			},
			
			goback() {
				this.$router.go(-1);
			},
			loadMore() {
				
				var self = this;
				if(!self.canLoading) {
					return
				}
				self.pageNum++;
				ajaxRequest({
					url: 'comment/getComments',
					data: {
						"informationId": self.informationId,
						"pageNum": self.pageNum,
						"time":self.time,
					},
					async: true,
					success: function(dataJson) {
						var array = dataJson.result;	
						
						self.initRefreshList(array);
						if (array.length < 10) {
							self.canLoading = false;
						} else {
							self.canLoading = true;
						}
					},
					error: function() {
						alertSysErrorTip(self);
					}
				});
			},
			//			点赞
			thumbsUpOrCancel(item) {
				var self = this;
				if(item.isThumbed == 1) {
					ajaxRequest({
						url: 'comment/cancelThumbsUp',
						data: {
							"commentId": item.commentId,
							"userId": self.selfUserId,
							"time":self.time,
						},
						async: true,
						success: function(dataJson) {
							var result = dataJson.result;
							
							if(result == "successed") {
								item.isThumbed = 0;
								item.preferenceCount = item.preferenceCount - 1;
								item.isThumbedImgSrc = require("../telehems/img/cancelThumbsUp.png");
							}
						},
						error: function() {
							alertSysErrorTip(self);
						}
					});
				} else {
					ajaxRequest({
						url: 'comment/thumbsUp',
						data: {
							"commentId": item.commentId,
							"userId": self.selfUserId
						},
						async: true,
						success: function(dataJson) {
							var result = dataJson.result;
							if(result == "successed") {
								item.isThumbed = 1;
								item.preferenceCount = item.preferenceCount + 1;
								item.isThumbedImgSrc = require("../telehems/img/thumbsUp.png");
							}
						},
						error: function() {
							alertSysErrorTip(self);
						}
					});
				}
			},
			//			初始化刷新数据
			initRefresh() {
				var self = this;
				ajaxRequest({
					url: 'comment/getComments',
					data: {
						"informationId": self.informationId,
						"pageNum": 1,
					},
					async: true,
					success: function(dataJson) {
						var array = dataJson.result;					
						if (dataJson.reportTypeList != undefined) {
							self.reportTypeList = dataJson.reportTypeList;
						 	for(var i = 0; i <dataJson.reportTypeList.length; i++){		
						 		self.reports.push(dataJson.reportTypeList[i].reportValue);
						 	}
						}
						self.list=[];
						self.initRefreshList(array);
						
						
					},
					error: function() {
						alertSysErrorTip(self);
					}
				});
			},
			// 初始化刷新评论列表
			initRefreshList(initList) {
				for(var i = 0; i < initList.length; i++) {
					var listMode = {};
					listMode.commentId = initList[i].commentId;
					listMode.userId = initList[i].userId;
					listMode.name = initList[i].name;
					listMode.level = "";
					listMode.content = initList[i].commentDetail;
					listMode.preferenceCount = parseInt(initList[i].preferenceCount);
					listMode.isThumbed = initList[i].isThumbed;
					listMode.time = initList[i].commentTime;
					listMode.imgPath = initList[i].imgPath;
					listMode.isSelf = initList[i].isSelf;

//					listMode.commentId = initList[i].commentId;
//					listMode.userId = initList[i].userId;
//					listMode.name = "1231";
//					listMode.level = "";
//					listMode.content = initList[i].commentDetail;
//					listMode.preferenceCount = parseInt(initList[i].preferenceCount);
//					listMode.isThumbed = initList[i].isThumbed;
//					listMode.time = initList[i].commentTime;
//					listMode.imgPath = "../telehems/img/integral_circle_icon.png";
//					//listMode.isSelf = ture;


					if(listMode.isThumbed == "1") {
						listMode.isThumbedImgSrc = require("../telehems/img/thumbsUp.png");
					} else {
						listMode.isThumbedImgSrc = require("../telehems/img/cancelThumbsUp.png");
					}
					this.list.push(listMode);
				}
				if (this.list.length > 0) {
					this.isHasData = true;
				} else {
					this.isHasData = false;
				}
			},
			// 刷新评论列表并新增
			refreshList(initList) {
				this.num = initList.length;
				for(let i = 0; i < initList.length; i++) {
					var listMode = {};
					listMode.commentId = initList[i].commentId;
					listMode.userId = initList[i].userId;
					listMode.name = initList[i].name;
					listMode.level = "";
					listMode.content = initList[i].commentDetail;
					listMode.preferenceCount = parseInt(initList[i].preferenceCount);
					listMode.isThumbed = initList[i].isThumbed;
					listMode.time = initList[i].commentTime;
					listMode.imgPath = require("../telehems/img/" + initList[i].imgPath);
					listMode.isSelf = initList[i].isSelf;
					if(listMode.isThumbed == "1") {
						listMode.isThumbedImgSrc = require("../telehems/img/thumbsUp.png");
					} else {
						listMode.isThumbedImgSrc = require("../telehems/img/cancelThumbsUp.png");
					}
					this.list.push(listMode);
				}
				
			},
			reply(message) {
					var self = this;
					if (message.replace(/(^\s+)|(\s+$)/g, "") == ""){
						return
					}
					if (self.isReply) {
						message = message+"@" + this.selectItem.name;
					}
					ajaxRequest({
						url: 'comment/addComment',
						data: {
							"commentDetail": message,
							"informationId": self.informationId,
						},
						async: true,
						success: function(dataJson) {
							self.inputStr = "";
							self.isReply = false;
							self.quaryTime=dataJson.time;
							self.pageNum=2;
							self.list=[];
							self.initRefreshList(dataJson.result);
						},
						error: function() {
							alertSysErrorTip(self);
						}
					});			
			},
			open(item) {
				console.log(JSON.stringify(item));
				this.selectItem = item;

				if(item.isSelf == true) {
					this.selfDialog = true;
				} else {
					this.dialog = true
				}
			},
			close() {
				this.dialog = false
			},
			click(index) {
				if(index == 0) {
					this.focusStatus = true;
					this.hintText = "@" + this.selectItem.name + " 回复：";
					this.isReply = true;
				} else if(index == 1) {
					this.reportedDialog = true;
				}
				this.dialog = false;
			},
			selfClose() {
				this.selfDialog = false;
			},
			selfClick(index) {
				if(index == 0) {
					this.focusStatus = true;
					this.hintText = "@" + this.selectItem.name + " 回复：";
					this.isReply = true;
				} else if(index == 1) {
					this.deleteComment(this.selectItem.commentId);	
				}
				this.selfDialog = false;
			},
			reportedOpen() {
				this.reportedDialog = true
			},
			reportedClose() {
				this.reportedDialog = false
			},
			//			举报
			reportedClick(index) {
				var self = this;
				self.reportedDialog = false;
				ajaxRequest({
					url: 'comment/reported',
					data: {
						"reportType": self.reportTypeList[index].reportType,					
						"commentId": self.selectItem.commentId,
					},
					async: true,
					success: function(dataJson) {
						var result = dataJson.result;
						console.log(result);
						if(result == "successed") {
							self.tipDialog = true;
						}
					},
					error: function() {
						alertSysErrorTip(self);
					}
				});
			},
			tipOpen() {
				this.tipDialog = true;
			},
			tipClose() {
				this.tipDialog = false;
			},
			//			删除评论
			deleteComment(commentId) {
				var self = this;
				ajaxRequest({
					url: 'comment/deleteComment',
					data: {
						"commentId": commentId,
					},
					async: true,
					success: function(dataJson) {
						var result = dataJson.result;
						console.log(result);
						if(result == "successed") {
							var tempList;
							self.list = self.list.filter(t => t.commentId != self.selectItem.commentId);
							tempList = self.list;
							self.list = [];
							self.list = tempList;
						}
					},
					error: function() {
						alertSysErrorTip(self);
					}
				});
			}
		
		},
		components: {
		
		}
	}
</script>

<style lang="css" scoped="yes">
	.titleBar{
		height: 40px;
		width: 100%;
		background-color: #00abfd;
		display: flex;
		
		flex-direction: row;
		align-items:center;
	}
	.leftDiv{
		width: 20%;
		height: 100%;
		text-align: left;
	}
	.leftImg{
		height: 24px;
		width: 24px;
		margin-left: 13px;
		margin-top: 8px;
	}
	.centerDiv{
		width: 60%;
		height: 100%;
		text-align: center;
	}
	.titleName{
		height: 100%; 
        line-height: 43px;  
		color: white;
		font-size: 20px;
	}
	.list-style {
		margin-bottom: 32px;
		
	}
	
	.demo-infinite-container {
		width: 100%;
		height: 100%;
	}
	
	div img {
		width: 40px;
		height: 40px;
		margin: 10px;
		margin-bottom: 0px;
		position: relative;
	}
	
	#thumb {
		width: 17px;
		height: 17px;
		top: 13px;
		margin: 0px;
		margin-right: 10px;
	}
	
	#isthumb {
		height: 17px;
		top: 0px;
		margin: 0px;
		margin-right: 10px;
	}
	
	#thumbCount {
		position: relative;
		display: flex;
		text-align: left;
		justify-content: flex-start;
		bottom: 0px;
		left: 10px;
		top: 16px;
		margin: 0px;
		margin-left: 10px;
		font-size: 12px;
		font-weight: 400;
		color: gray;
	}
	
	#name {
		position: absolute;
		margin-left: 0px;
		margin-top: 10px;
		font-size: 15px;
		text-align: left;
		font-weight: 400;
		color: cornflowerblue;
	}
	
	#level {
		/*background-color: #FBC653;
		padding-left: 5px;
		padding-right: 5px;
		font-size: 11px;
		color: white;*/
	}
	
	#time {
		position: relative;
		display: flex;
		text-align: left;
		justify-content: flex-start;
		bottom: 0px;
		
		margin: 0px;
		margin-left: 60px;
		font-size: 12px;
		font-weight: 400;
		color: gray;
	}
	
	#content {
		position: relative;
		line-break: strict;
		text-align: left;
		margin-left: 20px;
		margin-right: 20px;
		margin-top: 5px;
		font-size: 12px;
		/*line-height: 17px;*/
		color: black;
		
	}
	
	#count {
		position: relative;
		text-align: right;
		display: flex;
		flex-direction: row;
		bottom: 0px;
		right: 0px;
		top: -17px;
		margin: 0px;
		margin: 10px;
		font-size: 12px;
		color: black;
		height: 0px;
	}
	
	#leftDiv {
		position: relative;
		text-align: right;
		align-items: flex-start;
		flex-direction: row;
		display: flex;
		width: 80%;
		bottom: 0px;
		right: 10px;
		margin: 0px;
		font-size: 12px;
		color: black;
	}
	
	#rightDiv {
		position: relative;
		align-items: flex-end;
		justify-content: flex-end;
		flex-direction: row;
		text-align: right;
		display: flex;
		width: 20%;
		bottom: 0px;
		right: 0px;
		margin: 0px;
		font-size: 12px;
		color: black;
	}
	
	#bottomDiv {
		background-color: white;
		width: 100%;
		border-top: 1px solid lightgray;
		/*border-width: 2px;*/
		/*border-color: lightgray;*/
		height: 50px;
		position: fixed;
		bottom: 0px;
	}
	.comment-text-input{
		width: 65%;
	}
	.comment-nodata-div{
		width: 100%;
		height: 90%;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.comment-nodata-img{
		width: 35%;
		height: 35vw;
	}
	.list-div{
		overflow: auto;
		height:calc(100% - 40px);
	}
			
</style>