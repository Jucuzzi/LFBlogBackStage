var canCircle = false;
var selectNumber = 0;
var needQueryNews = 0;
var firstCurl = 0;
var current = 0;
var newPrizeCode = "";
var ifNoChance = 0;
var luck = {
	index: -1, //当前转动到哪个位置，起点位置
	count: 0, //总共有多少个位置
	timer: 0, //setTimeout的ID，用clearTimeout清除
	speed: 20, //初始转动速度
	times: 0, //转动次数
	cycle: 50, //转动基本次数：即至少需要转动多少次再进入抽奖环节
	prize: -1, //中奖位置
	init: function(id) {
		if($("#" + id).find(".luck-unit").length > 0) {
			$luck = $("#" + id);
			$units = $luck.find(".luck-unit");
			this.obj = $luck;
			this.count = $units.length;
			$luck.find(".luck-unit-" + this.index).addClass("active");
		};
	},

	roll: function() {
		var index = this.index;
		var count = this.count;
		var luck = this.obj;
		$(luck).find(".luck-unit-" + index).removeClass("active");
		index += 1;
		if(index > count - 1) {
			index = 0;
		};
		$(luck).find(".luck-unit-" + index).addClass("active");
		this.index = index;
		return false;
	},
	stop: function(index) {
		this.prize = index;
		return false;
	},
	refresh:function(){
		var luck = this.obj;
		for(var index = 0;index<9;index++){
			$(luck).find(".luck-unit-" + index).removeClass("active");
		}
	}
};

function roll() {
	if(needQueryNews < 1) {
		var self = this;
		if(firstCurl == 0) {
			ajaxRequest({
				url: "/hemsCirclePrizeDraw",
				data: {},
				async: true,
				success: function(dataJson) {
					if(!dataJson.result.prizeType) {
						needQueryNews = 1;
						firstCurl = 0;
						selectNumber = 0;
						ifNoChance = 1;
						click = false;
						
						luck.refresh();
						luck.index = -1;
						
						if(dataJson.result.error_description) {
							alertTip(self,dataJson.result.error_description);
						} else {
							alertTip(self,"抽奖失败");
						}

					} else {
						//						alert("asdfasdfasdfasdfasdf");
						needQueryNews = 1;
						firstCurl = 0;
						selectNumber = parseInt(dataJson.result.prizeType) - 1;
//						selectNumber = 0;
						newPrizeCode = dataJson.result.redeemCode;
						
						$('#point').html(dataJson.result.points);
						$('#times').html(dataJson.result.prizeDrawNum);
					}

				},
				error: function() {
					ifNoChance = 1;
					click = false;
					needQueryNews = 1;
					firstCurl = 0;
					selectNumber = 0;
					alertSysErrorTip(self);
				}
			});
		}
		firstCurl = 1;
	}
	if(canCircle && (ifNoChance < 1)) {
		luck.times += 1;
		luck.roll();
		if(luck.times > luck.cycle + 10 && luck.prize == luck.index) {
			clearTimeout(luck.timer);

			switch(luck.prize) {
				case 0:
					//结束转圈
					$('#apply0').modal('show');
					break;
				case 1:
					//结束转圈
					$('#apply98').modal('show');
					$('#getPrizeCode98').html("卡密为:"+newPrizeCode);
					break;
				case 2:
					//结束转圈
					$('#apply95').modal('show');
					$('#getPrizeCode95').html("卡密为:"+newPrizeCode);
					break;
				case 3:
					//结束转圈
					$('#apply98').modal('show');
					$('#getPrizeCode98').html("卡密为:"+newPrizeCode);
					break;
				case 4:
					//结束转圈
					$('#apply9').modal('show');
					$('#getPrizeCode9').html("卡密为:"+newPrizeCode);
					break;
				case 5:
					//结束转圈
					$('#apply93').modal('show');
					$('#getPrizeCode93').html("卡密为:"+newPrizeCode);
					break;
				case 6:
					//结束转圈
					$('#apply95').modal('show');
					$('#getPrizeCode95').html("卡密为:"+newPrizeCode);
					break;
				case 7:
					//结束转圈
					$('#apply93').modal('show');
					$('#getPrizeCode93').html("卡密为:"+newPrizeCode);
					break;
				case 8:
					//结束转圈
					$('#apply0').modal('show');
					//$('#getPrizeCode95').html("卡密为:"+newPrizeCode);
					break;
				default:
					$('#apply0').modal('show');
					break;
			}
			luck.prize = -1;
			luck.times = 0;
			needQueryNews = 0;

			click = false;
		} else {
			if(luck.times < luck.cycle) {
				luck.speed -= 10;
			} else if(luck.times == luck.cycle) {

				//抽中哪一个
				var index = selectNumber;
				luck.prize = index;
			} else {
				if(luck.times > luck.cycle + 10 && ((luck.prize == 0 && luck.index == 7) || luck.prize == luck.index + 1)) {
					luck.speed += 110;
				} else {
					luck.speed += 20;
				}
			}
			if(luck.speed < 40) {
				luck.speed = 40;
			};

			luck.timer = setTimeout(roll, luck.speed);
		}
	}

	return false;
}

//闪灯效果
var num = 0;
setInterval(function clocks() {
	num++;
	if(num % 2 != 0) {
		$('#deng').addClass("shanDeng2");
	} else {
		$('#deng').addClass("shanDeng");
		$('#deng').removeClass('shanDeng2');
	}
}, 500)

var click = false;
//window.onload = function() {
//	luck.init('luck');
//	$("#btn").click(function() {
//		//按下弹起效果
//		$("#btn").addClass("cjBtnDom");
//		setTimeout(function() {
//			$("#btn").removeClass("cjBtnDom");
//		}, 200);
//		if(click) {
//			return false;
//		} else {
//			canCircle = true;
//			luck.speed = 100;
//			roll();
//			click = true;
//			return false;
//		}
//	});
//};