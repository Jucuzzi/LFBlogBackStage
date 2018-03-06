<template>
	<div class="sign-list-root" id="sign-list-root">
		<span class="sort-num" v-text="this.index+1">1</span>
		<img :id="'rank-icon-'+this.index" class="rank-icon"/>
		<img class="user-icon1" :src="this.data.userIconPath"/>
		<div class="sign-list-item-center">
			<span class="user-name" v-text="this.data.userName"></span>
			<span :id="'sign-line-'+this.index" class="sign-line"></span>
		</div>
		<div class="sign-list-item-right-outer">
			<div class="sign-list-item-right">
				<span class="get-points" v-text="this.data.points+' 积分'">0积分</span>
				<span class="sign-time" v-text="this.data.signInTime"></span>
			</div>
		</div>
	</div>
</template>

<script>
export default{
	props:['data','index'],
	data(){
		return{
		}
	},
	mounted(){
		
		if (this.data.data == "empty" || this.data == null) {
//			$('.sign-list-root').html("暂无签到信息");
			var showImg = "../../assets/img/sign/left_arrow.png";
			document.getElementById('sign-list-root').innerHTML="<span id='emptyData'>暂无信息</span>";
//			document.getElementById('sign-list-root').innerHTML="	<img id='emptyData' src='../../assets/img/sign/right_arrow.png' />";
		} else {
			var sortId = parseInt(this.index);
			var signLine = document.getElementById('sign-line-'+sortId);
			var rankIcon = document.getElementById('rank-icon-'+sortId);
			if (sortId == 0) {
				signLine.style.width='100%';
				signLine.style.backgroundColor='#f0773d';
				rankIcon.src=require('../../assets/img/sign/rank1.png');
			} else if (sortId == 1) {
				signLine.style.width='50%'; 
				signLine.style.backgroundColor='#fbcf49';
				rankIcon.src=require('../../assets/img/sign/rank2.png');
			} else if (sortId == 2) {
				signLine.style.width='40%';
				signLine.style.backgroundColor='#fbcf49';
				rankIcon.src=require('../../assets/img/sign/rank3.png');
			} else if (sortId <= 9){
				signLine.style.width='25%';
				signLine.style.backgroundColor='#7bd4e1';
				rankIcon.style.visibility="hidden";
			} else {
				signLine.style.width='15%';
				signLine.style.backgroundColor='#c5cacc';
				rankIcon.style.visibility="hidden";
			}
		}
	},
	updated(){
	
	}
}
</script>

<style scoped="yes">
.sign-list-root{
	width: 100%;
	height: 60px;
	display:flex; 
	flex-direction: row;
	align-items: center;
}
.sort-num{
	width: 20px;
	margin-left: 10px;
	margin-right: 0px;
	font: "微软雅黑";
	font-style: italic;
	font-size: 19px;
}
#emptyData{
	width: 40%;
	height: 100px;
	text-align: center;
	margin-left: 30%;
	margin-top: 50%;
	font: "微软雅黑";
	font-size: 18px;
}
.rank-icon{
	width: 20px;
	height: 20px;
	margin-bottom: 20px;
	margin-right: -10px;
	z-index: 1;
}
.user-icon1{
	width: 40px;
	height: 40px;
	border-radius: 15px;
}
.sign-list-item-center{
	width: calc(100% - 190px);
}
.sign-list-item-center{
	display:flex; 
	flex-direction: column;
	align-items: flex-start;
	justify-content: center;
	margin-left: 10px;
}
.user-name{
	width: 100%;
	font-size: 17px;
	overflow: hidden;
	text-overflow:ellipsis;
	white-space:nowrap;
	text-align: start;
}
.sign-line{
	width: 100%;
	height: 4px;
	margin-top: 3px;
	border-radius: 2px;
	background: #00ABFD;
}
.sign-list-item-right-outer{
	width: 100px;
	margin-top: 4px;
	display:flex; 
	flex-direction: row;
	align-items: center;
	justify-content: flex-end;
}
.sign-list-item-right{
	display:flex; 
	flex-direction: column;
	align-items: flex-end;
	justify-content: center;
}
.get-points{
	margin-right: 10px;
	padding-left: 4px;
	padding-right: 4px;
	border-radius: 5px;
	border-width: 4px;
	border: 1px solid grey; 
	display: flex;
	white-space:pre;
	flex-direction: row;
	align-items: center;
	justify-content: center;
	font-size: 15px;
}
.sign-time{
	margin-right: 15px;
	margin-top: 4px;
	font-size: 13px;
	color: grey;
}
</style>