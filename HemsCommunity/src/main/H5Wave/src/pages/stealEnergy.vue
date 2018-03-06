<template>
	<div class="steal-energy-root">
		<div class="sign-titleBar">
			<div class="leftDiv" v-on:click="gotoSign">
				<img class="leftImg" src="../assets/images/title_arrow_left_white.png" />
			</div>
		</div>
		<div class="steal-div">
			<!--<div class="energy-title-bar">
				<div @click="onBack" class="energy-title-leftDiv"> 
					<img class="energy-title-leftImg" src="../telehems/images/common/title_arrow_left_white.png"/>		
				</div>
			</div>-->
			<mainHouseSelf :mainHouseData="mainHouseData"></mainHouseSelf>
		</div>
		<other-energy :stealEnergyResult="stealEnergyResult" v-if="!netError"></other-energy>
		<!-- 网络异常的显示 -->
		<div class="currentActivity" style="text-align: center; margin-top: 30px;" v-if="netError">
			<div>
				<img src="../assets/img/404.png"></img>
				<br/>
				<label style="color: lightgray; font-weight: 300; margin-bottom: 10px;">当前网络较差，请重新加载</label>
			</div>
		</div>
	</div>
</template>

<script>
	import mainHouseSelf from '@/components/mainHouse.vue'
	import otherEnergy from '@/components/otherEnergy.vue'
	export default {
		//this.$route.query.currentUserId
		data() {
			return {
				stealEnergyResult: {
					//					"stolenIcon":"http://qq.5068.com/uploads/allimg/131226/13320533V-11.jpg",
					//					"stolen":"50",
					//					"winner":"0",
					//					"stealerIcon":"http://pic.baike.soso.com/p/20131105/20131105155548-374146863.jpg",
					//					"stealer":"10"
				},
				mainHouseData: {},
				netError: false,
			}
		},
		created() {
			this.getStolenData();
		},
		components: {
			otherEnergy,
			mainHouseSelf
		},
		methods: {
			onBack: function() {
				this.$router.go(-1);
			},
			gotoSign: function() {
				this.$router.push({
						path: '/mainView'
					})
			},
			getStolenData() {
				var self = this;
				ajaxRequest({
					url: "hemsCircleQueryStolenUserInfos",
					data: {
						"userId": this.$route.params.currentUserId,
						"stolenUserId": this.$route.params.stealUserId
					},
					async: true,
					success: function(dataJson) {
//																		alert(JSON.stringify(dataJson.result));
						$('#point').html(dataJson.result.points);
						$('#times').html(dataJson.result.prizeDrawNum);
						self.stealEnergyResult = {
							"stolenIcon": dataJson.result.stolenUserIconPath,
							"stolen": dataJson.result.hisValleyEnergy,
							"winner": (parseInt(dataJson.result.valleyEnergy) > parseInt(dataJson.result.hisValleyEnergy)) ? "0" : "1",
							"stealerIcon": dataJson.result.userIconPath,
							"stealer": dataJson.result.valleyEnergy
						};
						if(self.stealEnergyResult == {}) {
							self.netError = true;
						}
						self.mainHouseData = {};
						self.mainHouseData.enviromentLevel = "";
						self.mainHouseData.generatedEnergy = "";
						self.mainHouseData.AllValleyEnergy = "";
						self.mainHouseData.isMain = "steal";
						self.mainHouseData.enviromentLevel = dataJson.result.enviromentLevel;

						self.mainHouseData.generatedEnergy = dataJson.result.generatedEnergy;
					},
					error: function() {
						alertSysErrorTip(self);
					}
				});
			},
		}
	}
</script>

<style>
	.energy-title-bar {
		height: 40px;
		width: 100%;
		position: fixed;
		top: 0;
		display: flex;
		flex-direction: row;
	}
	
	.energy-title-leftDiv {
		width: 20%;
		height: 40px;
		text-align: left;
	}
	
	.energy-title-leftImg {
		height: 24px;
		width: 24px;
		margin-left: 13px;
		margin-top: 8px;
	}
	
	.steal-energy-root {
		height: 100%;
	}
	
	.steal-div {
		height: 55vw;
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