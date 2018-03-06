<template>
	<div class="raiders-root">
		<div class="sign-titleBar">
			<div class="leftDiv" v-on:click="gotoMainView">
				<img class="leftImg" src="../assets/images/title_arrow_left_white.png" />
			</div>
			<div class="centerDiv">
				<span class="titleName"></span>
			</div>
		</div>
		<div class="raiders-outer-div">
			<div class="raiders-up-outer">
				<img class="raiders-icon" src="../assets/strategy/iead_title.png">	
				
			</div>
		</div>
		<div class="raiders-rules-text">
			<span class="activity-rules-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你知道吗？目前我国在污染环境的各因素中，70%以上的总悬浮颗粒物，90%以上的二氧化硫，60%以上的氮氧化合物，85%以上的矿物燃料生产的二氧化碳均来自煤炭。与此同时，中国煤炭总量50%用于发电，发电总量的80%是使用燃煤，而发电燃煤污染占到所有污染的40%以上。“吞云吐雾”的火力发电机组在为我们生活提供巨大便利的同时，天空变得迷蒙暗淡，河流随之腐烂发臭。城市，承载着我们万家灯火的地方，正笼罩在一片阴霾之下。</span>
			<img class="cityBadIcon" src="../assets/strategy/city_bad.png">
			<span class="text_Image">图1 越来越严重的城市污染</span>
			<span class="activity-rules-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;赫马虽小，但我们拥有大大的梦想“以创新推动更智能、更高效的绿色能源”，智能家居从来都不是我们的初心与信仰，多年来赫马从未停止对梦想的追逐。推出的“谷电补贴”活动，进一步拉开峰谷电价，助力电网削峰填谷，有效关停或减少火电发电机组，正吸引越来越多支持节能环保的小伙伴加入。</span>
			<img class="cityhomeIcon" src="../assets/strategy/home_idea.png">
			<span class="text_Image">图2 赫马推出的小家概念</span>
			<span class="activity-rules-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“蚂蚁森林”的成功，愈发坚定了我们的信念，全新推出的“绿色谷电”将为大家带来同样的环保理念，全新的环保体验。家庭与城市共生共存，万千赫马家庭的绿色能量汇聚在一起，还城市天空以蔚蓝、河流以清澈，呵护家庭健康成长。践行梦想，道阻且长，伴君同行，与君共勉。</span>
			<img class="cityActivitiIcon" src="../assets/strategy/activity_idea.png">
			<span class="text_Image">图3 绿色谷电能量活动攻略</span>
			<div>
				<br /><br /><br />
			</div>
		</div>
	</div>
</template>

<script>
export default{
	methods: {
 		onBack:function () {
 			this.$router.go(-1);
 		},
 		gotoMainView: function() {
				this.$router.push({
					path: '/raiders'
				})
			},
			goIdeaType: function() {
				this.$router.push({
					path: '/ideaType'
				});
			},
 	}
}
</script>

<style scoped="yes">
	.raiders-root{
		display:flex; 
		flex-direction: column;
		align-items: center;
	}
	.raiders-outer-div{
		width: 100%;
		height: 50vw;
		display:flex; 
		flex-direction: column;
		align-items: center;
	}
	.raiders-title-bar{
		height: 40px;
		width: 100%;
		position:fixed;
        top:0;
		display: flex;
		flex-direction: row;
	}
	.raiders-title-leftDiv{
		width: 20%;
		height: 40px;
		text-align: left;
	}
	.raiders-title-leftImg{
		height: 24px;
		width: 24px;
		margin-left: 13px;
		margin-top: 8px;
	}
	.raiders-up-outer{
		
		display:flex; 
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding-bottom: 8%;
	}

	.raiders-icon{
		width: 100%;
		height: 50vw;
	}
	.cityBadIcon{
		margin-left: 6%;
		margin-top: 2%;
		width: 88%;
		height: 55vw;
	}
	.cityhomeIcon{
		margin-left: 6%;
		margin-top: 2%;
		width: 88%;
		height: 48vw;
	}
	.cityActivitiIcon{
		margin-left: 6%;
		margin-top: 2%;
		width: 88%;
		height: 76vw;
	}
	.valley-energy{
		font-size: 31px;
		color: white;
		font-weight:600;
	}

	
	.raiders-rules-text{
		width: 100%;
		margin-top: 0%;
		background-color: white;
		display:flex; 
		flex-direction: column;
		align-items: flex-start;
		justify-content: flex-start;
	}
	.activity-rules{
		margin-left: 4%;
		margin-right: 4%;
		text-align: left;
		font-size: 18px;
		font-weight: 600;
	}
	.activity-rules-text{
		margin-top: 3%;
		font-size: 16px;
		color: gray;
		margin-left: 6%;
		margin-right: 4%;
		text-align: left;
	}
	.text_Image{
		font-size: 14px;
		color: gray;
		margin-left: 27%;
		margin-top: 2%;
	
	}
	/*标题栏*/
	
	.sign-titleBar {
		height: 44px;
		width: 100%;
		position: fixed;
		top: 0;
		/*background: /*#1cbafb*/;
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
</style>