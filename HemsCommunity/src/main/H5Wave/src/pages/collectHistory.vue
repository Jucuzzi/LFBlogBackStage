<template>

	<div style="margin-top: 40px;" id="downDiv">
		<div class="sign-titleBar">
			<div class="leftDiv" v-on:click="gotoMainView">
				<img class="leftImg" src="../assets/images/title_arrow_left_white.png" />
			</div>
			<div class="centerDiv">
				<span class="titleName">收集记录</span>
			</div>
		</div>
		<div class="demo-infinite-container" style="" v-if="!netError" id="tab_outer">
			<div v-if="noRecord" style="text-align: center; ">
				<img src="../assets/img/noStealRecord.png"></img>
				<br/>
				<label style="color: lightgray; font-weight: 300; margin-bottom: 10px;">最近没有收集过能量，赶紧去收集吧</label>
			</div>
			<ul style="" v-if="!noRecord">
				<li class="tableView-cell" v-for="item in stealRecord">
					<mu-paper class="demo-paper" :zDepth="1">
						<img class = "time" src="../assets/img/time.png" />
							<div class="showTime">{{item.date}}</div>
							<div class="showInfo">获取谷电能量{{item.energy}}g</div>
					</mu-paper>
				</li>
			</ul>
			<mu-infinite-scroll :scroller="scroller" :loading="loading" @load="loadMore" />
		</div>
		<!-- 网络异常的显示 -->
		<div  style="text-align: center; margin-top: 60px;" v-if="netError">
			<div>
				<img src="../assets/img/404.png"></img>
				<br/>
				<label style="color: lightgray; font-weight: 300; margin-bottom: 10px;">当前网络较差，请重新加载</label>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				"stealRecord": [],
				"lastRecordId": "",
				num: 0,
				loading: false,
				scroller: null,
				userId:"",
				noRecord:false,
				netError:false
			}
		},
		methods: {
			gotoMainView: function() {
				this.$router.push({
					path: '/rankList'
				})
			},
			getData() {
				var self = this;
				ajaxRequest({
					url: 'getStealRecord',
					data: {
					},
					async: true,
					success: function(dataJson) {
						self.stealRecord = dataJson.result.root;
						self.lastRecordId = dataJson.result.root[dataJson.result.root.length - 1].id;
						self.num = dataJson.result.total;
					},
					error: function() {
						self.netError = true;
						alertSysErrorTip(self);
					}
				});
			},
			loadMore() {
				if(this.num == 10) {
					this.loading = true
					var self = this;
					setTimeout(() => {
						ajaxRequest({
							url: 'getStealRecord',
							data: {
								"lastRecordId": self.lastRecordId,
							},
							async: true,
							success: function(dataJson) {
								for(var i = 0; i < dataJson.result.root.length; i++) {
									self.stealRecord.push(dataJson.result.root[i]);
								}
								self.lastRecordId = dataJson.result.root[dataJson.result.root.length - 1].id;
								self.num = dataJson.result.root.length
							},
							error: function() {
								self.netError = true;
								alertSysErrorTip(self);
							}
						});
						this.loading = false
					}, 1000)
				}
			}
		},
		mounted() {
			this.getData();
//			this.scroller = document.getElementById("tab_outer");
			//设置自适应高度
			var height = window.screen.height - 60;
			$("#downDiv").css("height", height+"px"); //通过设置CSS属性来设置元素的高
			$("#downDiv").css("overflow-y", "auto");
			$("#downDiv").css("overflow-x", "hidden");
			this.scroller = document.getElementById("downDiv");
//			this.scroller = this.$el;
		}

	}
</script>

<style scoped="yes">
	* {
		margin: 0;
		padding: 0;
		list-style: none;
		box-sizing: border-box;
	}
	
	body {
		font-family: 'SimHei';
	}
	
	.titles {
		width: 100%;
		height: 60px;
		background: url(../../static/img/titleImg.png) round;
	}
	
	#titleName {
		padding-top: 18px;
		margin-left: 5%;
		color: white;
	}
	
	.historyTitle {
		text-align: center;
	}
	
	#line1 {
		width: 100%;
		border-bottom: 1px solid #D3D3D3;
	}
	/* 字体文件 */
	/* header、footer */
	
	.header,
	.footer {
		position: fixed;
		z-index: 2;
		left: 0;
		display: flex;
		width: 100%;
	}
	
	.header {
		top: 0;
		height: 55px;
		background: #35a251;
	}
	
	.header .icon-shouyeshouye {
		position: absolute;
		left: 10px;
		flex: 0 0 40px;
		width: 40px;
		height: 55px;
		line-height: 55px;
		font-size: 33px;
		color: #fff;
	}
	
	.header .title {
		flex: 1;
		line-height: 55px;
		color: #fff;
		text-align: center;
	}
	
	.footer {
		bottom: 0;
		height: 50px;
		line-height: 50px;
		background: #1a1a1a;
	}
	
	.footer span {
		flex: 1;
		font-size: 20px;
		color: #fff;
		text-align: center;
	}
	/* 列表容器 */
	
	.list-wrapper {
		position: fixed;
		z-index: 1;
		top: 60px;
		bottom: 0px;
		left: 0;
		width: 100%;
		background: #ccc;
		overflow: hidden;
	}
	
	.list-wrapper .list-content {
		background: #fff;
	}
	
	.list-wrapper .list-item {
		display: flex;
		padding: 10px;
		width: 100%;
		border-bottom: 1px solid #ddd;
	}
	
	.list-wrapper .list-item .avatar {
		flex: 0 0 100px;
		border: 1px solid #ddd;
	}
	
	.list-wrapper .list-item .text {
		position: relative;
		flex: 1;
		padding-left: 10px;
	}
	
	.list-wrapper .list-item .text h2 {
		font-size: 16px;
		font-weight: normal;
		color: rgb(7, 17, 27);
	}
	
	.list-wrapper .list-item .text span {
		position: absolute;
		bottom: 20px;
		color: rgba(7, 17, 27, 0.7);
	}
	/* 下拉、上拉提示信息 */
	
	.top-tip {
		position: absolute;
		top: -40px;
		left: 0;
		z-index: 1;
		width: 100%;
		height: 40px;
		line-height: 40px;
		text-align: center;
		color: #555;
	}
	
	.bottom-tip {
		width: 100%;
		height: 35px;
		line-height: 35px;
		text-align: center;
		color: #777;
		background: #f2f2f2;
	}
	/* 全局提示信息 */
	
	.alert {
		display: none;
		position: fixed;
		top: 55px;
		left: 0;
		z-index: 2;
		width: 100%;
		height: 35px;
		line-height: 35px;
		text-align: center;
		color: #fff;
		font-size: 12px;
		background: rgba(7, 17, 27, 0.7);
	}
	
	.tableView-cell {
		width: 100%;
		height: 100px;
		list-style-type: none;
		position: relative;
		/*marginz;*/
		/*设置下边框样式*/
		/*border-bottom-style: solid;
		border-bottom-width: 1px;
		border-bottom-color: #D3D3D3;*/
	}
	
	.tableView-cell-title {
		width: 100%;
		height: 80px;
		list-style-type: none;
		/*设置下边框样式
		border-bottom-style: solid;
		border-bottom-width: 1px;
		border-bottom-color: #D3D3D3;*/
	}
	
	.cellDataTime {
		width: 50%;
	}
	
	.showTime {
		position: absolute;
		text-align: center;
		top: 20px;
		font-size: 17px;
		left: 70px;
		height: 30px;
		
		/*height: 100%;*/
		/*padding-top: 25px;*/
	}
	
	.time{
		position: absolute;
		width: 25px;
		height: 25px;
		top: 20px;
		left: 30px;
	}
	
	.showTimeTitle {
		text-align: center;
		font-size: 20px;
		height: 100%;
		padding-top: 10px;
	}
	
	.cellDataInfo {
		/*margin-left: 50%;*/
		width: 50%;
		/*margin-top: -55px;*/
	}
	
	.showInfo {
		position: absolute;
		top: 55px;
		text-align: center;
		font-size: 17px;
		height: 30px;
		left: 30px;
		/*padding-top: 25px;*/
	}
	
	.showInfoTitle {
		text-align: center;
		font-size: 20px;
		height: 100%;
		padding-top: 25px;
	}
	/*标题栏*/
	
	.sign-titleBar {
		height: 44px;
		width: 100%;
		position: fixed;
		top: 0;
		background: #1cbafb;
		display: flex;
		flex-direction: row;
		z-index: 1000;
	}
	
	.leftDiv {
		width: 30%;
		height: 100%;
		text-align: left;
	}
	
	.leftImg {
		height: 24px;
		width: 24px;
		margin-left: 13px;
		margin-top: 10px;
	}
	
	.centerDiv {
		width: 40%;
		height: 100%;
		text-align: center;
	}
	
	.titleName {
		height: 100%;
		line-height: 44px;
		color: white;
		font-size: 17px;
	}
	
	.rightDiv {
		width: 30%;
		height: 100%;
		text-align: right;
	}
	
	/*.demo-infinite-container {
		margin-top: 40px;
	}*/
	
	.demo-paper {
		display: inline-block;
		height: 80px;
		width: calc(100% - 20px);
		/*margin-left: 10%;*/
		margin: 10px;
		overflow: -Scroll;
		/*text-align: center;*/
	}
</style>