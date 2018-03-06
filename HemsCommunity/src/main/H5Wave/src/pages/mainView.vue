<template>
	<div>
		<div class="sign-titleBar">
			<div class="leftDiv" v-on:click="gotoSign">
				<img class="leftImg" src="../assets/images/title_arrow_left_white.png" />
			</div>
		</div>
		<div >
			<mainHouseSelf v-bind:mainHouseData="mainHouseData"  :isFisrtError="isFisrtError"></mainHouseSelf>
		</div>
		

		<div class="maintitle">
			<ul class="list-title">
				<li class="tableView-cell">
					<div class="title">最新动态</div>
				</li>
			</ul>
		</div>
		<div class="sperateView">
		</div>
		<!-- 动态列表 -->
		<div class="currentActivity" style="text-align: center;" v-if="!netError">
			<ul class="list-infoView" v-if="thieves.length!=0">
				<br />
				<li class="tableView-cell-thieves" v-on:click="stealEnergyJump(item)" v-for="item in thieves">
					<div class="name">{{item.userName}}&nbsp;&nbsp;&nbsp;<span style="font-size: 15px; color: darkgrey;">收取{{item.stealEnergy}}g</span></div>
					<div class="time">{{item.date}}&nbsp;&nbsp;&nbsp;</div>
				</li>
			</ul>
			<div v-if="thieves.length==0">
				<img src="../assets/img/noStealRecord.png"></img>
				<br/>
				<label style="color: lightgray; font-weight: 300; margin-bottom: 10px;">最近还没有人来偷过你的能量哦</label>
			</div>

		</div>
		<!-- 网络异常的显示 -->
		<div class="currentActivity" style="text-align: center;" v-if="netError">
			<div>
				<img src="../assets/img/404.png"></img>
				<br/>
				<label style="color: lightgray; font-weight: 300; margin-bottom: 10px;">当前网络较差，请重新加载</label>
			</div>
		</div>
		
		<div class="sperateView">
		</div>
		<div class="findMore" v-on:click="MoreDynamicJump">
			查看更多动态
		</div>
		<div class="sperateViewLarge">
		</div>

		<div class="midetitle">
			<ul>
				<li class="tableView-cell">
					<div class="title" style="margin-top: 15px;margin-bottom: 15px;">周边好友</div>
				</li>
			</ul>
		</div>
		<div class="sperateView">
		</div>
		<!-- 周边好友列表 -->
		<div class="list-frands" v-if="!netError">
			<ul class="list-frandsView" id="list-frandsViews">
				<li class="tableView-cell-frands" v-on:click="stealEnergyJump(item)" v-for="item in friends">
					<div class="userInfo">
						<div class="selfTitleImage">
							<img :src="item.userIconPath" id="theShow" />
						</div>
						<div class="selfName">
							{{item.userName}}
						</div>
						<div class="numOfElec">
							本月已收集谷电能量{{item.AllValleyEnergy}}g
						</div>
						<div class="canGet" v-if="item.canBeStolen != 0">
							<img src="../../static/img/getIt.png" id="haveEnage" />
						</div>
					</div>
				</li>
			</ul>
		</div>
		<!-- 网络异常的显示 -->
		<div class="list-frands" v-if="netError" style="text-align: center;">
			<div>
				<img src="../assets/img/404.png"></img>
				<br/>
				<label style="color: lightgray; font-weight: 300; margin-bottom: 10px;">当前网络较差，请重新加载</label>
			</div>
		</div>

	</div>
</template>

<script>
	import mainHouseSelf from './../components/mainHouse.vue'

	export default {
		data() {
			return {
				"friends": [], //可偷取的用户列表（周边好友）
				"thieves": [], //偷取能量的用户列表（最新动态）
				"mainHouseData": {},
				"netError":false,
				isFisrtError:true
			}
		},
		mounted() {
//			alert( sessionScope.userId);
//			this.login();
			this.getData();
		},
		components: {
			mainHouseSelf
		},

		methods: {
			login() {
				var self = this;
				ajaxRequest({
					url: 'userlogin',
					data: {
						"userId": "1415"
					},
					async: true,
					success: function(dataJson) {
					},
					error: function() {
						self.netError = true;
					}
				});
			},
			gotoSign: function() {
				this.$router.push({
						path: '/'
				})
			},
			MoreDynamicJump: function() {
			if (this.isFisrtError){
					alertLoadError(this);
					return
				}
				this.$router.push({
					path: '/MoreDynamic'
				});
			},
			stealEnergyJump: function(item) {
			if (this.isFisrtError){
					alertLoadError(this);
					return
				}
				this.$router.push({
					name: 'stealEnergy',
					params: {
						"stealUserId": item.userId,
						"currentUserId": "1419"
					}
				});
			},
			getData: function() {
				var self = this;
				ajaxRequest({
					url: 'getValleyEnergyMain',
					data: {},
					async: true,
					success: function(dataJson) {
//																		alert(JSON.stringify(dataJson));
						self.thieves = dataJson.result.thieves;
						self.friends = dataJson.result.friends;
						//						alert(JSON.stringify(self.friends));
						//						"enviromentLevel": "", //环境等级（窗外等级）
						//						"generatedEnergy": [], //浮动的能量球
						self.mainHouseData = {};
						self.mainHouseData.enviromentLevel = "";
						self.mainHouseData.generatedEnergy = "";
						self.mainHouseData.AllValleyEnergy = "";
						self.mainHouseData.isMain = "main";
						self.mainHouseData.enviromentLevel = dataJson.result.enviromentLevel;
						self.mainHouseData.hasRedPacket = dataJson.result.hasRedPacket;
						self.mainHouseData.generatedEnergy = dataJson.result.generatedEnergy;
						self.mainHouseData.userIconPath = dataJson.result.userIconPath;
						//						self.mainHouseData.generatedEnergy = [{"ValleyEnergy":"10","generatedId":"12","type":"1"}];
						//						self.mainHouseData.generatedEnergy = [{"ValleyEnergy":"10","generatedId":"11","type":"1"},{"ValleyEnergy":"10","generatedId":"12","type":"1"}];
						//						alert(JSON.stringify(self.mainHouseData));

						self.mainHouseData.AllValleyEnergy = dataJson.result.AllValleyEnergy;
						//						self.generatedEnergy = dataJson.result.generatedEnergy;
						//						self.AllValleyEnergy = dataJson.result.AllValleyEnergy;
						//						self.refresh();
						self.isFisrtError = false;
					},
					error: function() {
						alertSysErrorTip(self);
						self.netError = true;
						self.isFisrtError = true;
					}
				});

			},
		}
	}
</script>

<style scoped="yes">
	.*{
		margin: 0px;
		padding: 0px;
	}
	.backgroundTitleView {
		position: absolute;
		left: 0;
		top: 0;
		width: 100%;
		height: 260px;
		background: url(../../static/img/freshHome.png) center;
		background-size: 100% 100%;
	}
	
	.strategy {
		width: 50px;
		height: 50px;
		padding-left: 10px;
	}
	
	#strategyButton {
		width: 50px;
		height: 50px;
		margin-top: 410%;
	}
	
	.redMoney {
		width: 50px;
		height: 50px;
		padding-left: 20px;
	}
	
	#redPacketButton {
		width: 50px;
		height: 50px;
		padding-left: 50px;
		margin-top: 310%;
	}
	
	.list-frandsView {
		margin-left: 10px;
	}
	
	.userInfo {
		height: 70px;
		width: 100%;
	}
	
	#userShowImg {
		width: 50px;
		height: 50px;
		border-radius: 30px;
	}
	
	.infoName {
		color: white;
		font: "微软雅黑";
		font-size: 15px;
		padding-right: 2px;
		margin-top: -52px;
		position: relative;
		z-index: 5;
	}
	
	.infoDetail {
		color: white;
		font: "微软雅黑";
		font-size: 15px;
		padding-right: 25px;
		margin-top: 0px;
		position: relative;
		z-index: 5;
	}
	
	.maintitle {
		/*margin-top: 260px;*/
	}
	
	.tableView-cell {
		width: 100%;
		height: 40px;
		margin-left: 10px;
		list-style-type: none;
	}
	
	.tableView-cell-thieves {
		width: 100%;
		height: 30px;
		margin-left: 10px;
		list-style-type: none;
	}
	
	.tableView-cell-frands {
		position: relative;
		width: 100%;
		height: 70px;
		list-style-type: none;
		/*设置下边框样式*/
		border-bottom-style: solid;
		border-bottom-width: 1px;
		border-bottom-color: #D3D3D3;
	}
	
	.title {
		text-align: left;
		font-size: 24px;
		padding-top: 7px;
	}
	
	.sperateView {
		width: 100%;
		height: 1px;
		background-color: #D3D3D3;
	}
	
	.name {
		text-align: left;
		font-weight: 300;
		font-size: 15px;
		/*margin-left: -20px;*/
		padding-top: 0px;
	}
	
	.time {
		text-align: right;
		font-weight: 300;
		margin-top: -22px;
		padding-top: 0px;
		font-size: 15px;
		color: darkgrey;
	}
	
	.findMore {
		width: 100%;
		height: 60px;
		font-size: 24px;
		font-weight: normal;
		text-align: center;
		padding-top: 12px;
		color: black;
	}
	
	.sperateViewLarge {
		width: 100%;
		height: 20px;
		background-color: #F3F3F3;
	}
	
	.selfTitleImage {
		position: relative;
		top: 10px;
		width: 50px;
		height: 50px;
	}
	
	#theShow {
		width: 45px;
		height: 45px;
		margin-top: 0px;
	}
	
	.selfName {
		font: "微软雅黑";
		font-size: 18px;
		/*margin-left: -33%;*/
		/*margin-top: -60px;*/
		position: absolute;
		top: 10px;
		left: 60px;
	}
	
	.numOfElec {
		position: absolute;
		font: "微软雅黑";
		font-size: 13px;
		color: darkgray;
		text-align: left;
		left: 60px;
		top: 35px;
		width: 60%;
	}
	
	.canGet {
		position: absolute;
		right: 20px;
		width: 40px;
		top: 15px
		/*padding-left: 85%;*/
		/*margin-top: -50px;*/
	}
	
	#haveEnage {
		width: 40px;
		height: 40px;
	}
	
	.midetitle {
		margin-top: -10px;
	}
	/*标题栏*/
	
	.sign-titleBar {
		height: 44px;
		width: 100%;
		position: fixed;
		top: 0;
		/*background: /*#1cbafb*/
		;
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
		color: black;
		font-size: 17px;
	}
	
	.rightDiv {
		width: 30%;
		height: 100%;
		text-align: right;
	}
	
</style>