<template>
	<div style="height: 100%;">
		<div v-show="isLogin" style="height: 100%;">
			<div class="top-background-outer">
				<div class="leftDiv">
					<img class="leftImg" @click="goback" src="../telehems/img/common/title_arrow_left_white.png" />
				</div>
				<div class="top-background-btn">
					<div class="top-layout-left-right">
						<div class="button-left-right" @click="goEnergy">
							<img id="icon_image" src="../telehems/img/10.png" width="25px" height="29px" />
							<div class="text-type-one">谷电能量</div>
						</div>
					</div>
					<div class="top-layout-left-right">
						<div class="button-left-right" @click="goChoujiang">

							<img id="icon_image" src="../telehems/img/9.png" width="25px" height="29px" />

							<div class="text-type-two">积分商城</div>

						</div>

					</div>
				</div>
			</div>

			<div class="mui-content" style="height: 70%">
				<div id="slider" class="mui-slider" style="height: 100%">
					<mu-tabs :value="activeTab" :lineClass="mylineClass" @change="handleTabChange" style="height: 8%;">
						<mu-tab value="0" title="官方" />
						<mu-tab value="1" title="精品" />
						<mu-tab value="2" title="教程" />
						<mu-tab value="3" title="娱乐" />
						<mu-tab value="4" title="收藏" />
					</mu-tabs>
					<div id="tab_outer" style="height: 92%;overflow:auto;background-color: white;">
						<div v-if="activeTab == '0'">
							<!-- 网络异常的显示 -->
							<div class="neterror-div" style="text-align: center; margin-top: 30px;" v-if="netError">
								<div>
									<img src="../assets/img/404.png"></img>
									<br/>
									<label style="color: lightgray; font-weight: 300; margin-bottom: 10px;">当前网络较差，请重新加载</label>
								</div>
							</div>
							<div id="tab_zero" class="demo-infinite-container" v-else-if="!netError&&isHasData[0]">
								<mu-list>
									<template v-for="item in informationDataArr[0]">
										<!--<mu-list-item :title="item.entityJSONObject.title" />
											<mu-divider/>-->
										<mu-paper class="demo-paper" :zDepth="1">
											<div class="div1" @click="itemJump(item.informationId)">
												<div class="div2">
													<img id="icon_image" :src="item.imgPath" height="70px" width="70px" />
												</div>
												<div class="div3">
													<p class="title-font">{{item.title}}</p>
												</div>
												<div class="div4">
													<p class="content-font">{{item.briefIntroduction}}</p>
												</div>
												<div class="div3">
													<p class="bottom-font-one" v-text="showTime(item.releaseTime)"></p>
													<p class="bottom-font-two" v-text="item.collectNum+' 收藏'"></p>
												</div>
											</div>
										</mu-paper>
									</template>
								</mu-list>
								<mu-infinite-scroll :scroller="scroller" :loading="loading[0]" @load="loadMore" />
							</div>
							<div v-else class="nodata-div">
								<img class="nodata-img" src="../assets/img/history_empty.png">
							</div>

						</div>
						<div v-if="activeTab == '1'" class="demo-infinite-container">
							<!-- 网络异常的显示 -->
							<div class="neterror-div" style="text-align: center; margin-top: 30px;" v-if="netError">
								<div>
									<img src="../assets/img/404.png"></img>
									<br/>
									<label style="color: lightgray; font-weight: 300; margin-bottom: 10px;">当前网络较差，请重新加载</label>
								</div>
							</div>
							<div id="tab_one" class="demo-infinite-container" v-else-if="!netError&&isHasData[1]">
								<mu-list>
									<template v-for="item in informationDataArr[1]">
										<!--<mu-list-item :title="item.entityJSONObject.title" />
											<mu-divider/>-->
										<mu-paper class="demo-paper" :zDepth="1">
											<div class="div1" @click="itemJump(item.informationId)">
												<div class="div2">
													<img id="icon_image" :src="item.imgPath" height="70px" width="70px" />
												</div>
												<div class="div3">
													<p class="title-font">{{item.title}}</p>
												</div>
												<div class="div4">
													<p class="content-font">{{item.briefIntroduction}}</p>

												</div>
												<div class="div3">
													<p class="bottom-font-one" v-text="showTime(item.releaseTime)"></p>
													<p class="bottom-font-two" v-text="item.collectNum+' 收藏'"></p>
												</div>
											</div>
										</mu-paper>
									</template>
								</mu-list>
								<mu-infinite-scroll :scroller="scroller" :loading="loading[1]" @load="loadMore" />
							</div>
							<div v-else class="nodata-div">
								<img class="nodata-img" src="../assets/img/history_empty.png">
							</div>
						</div>
						<div v-if="activeTab == '2'">
							<!-- 网络异常的显示 -->
							<div class="neterror-div" style="text-align: center; margin-top: 30px;" v-if="netError">
								<div>
									<img src="../assets/img/404.png"></img>
									<br/>
									<label style="color: lightgray; font-weight: 300; margin-bottom: 10px;">当前网络较差，请重新加载</label>
								</div>
							</div>
							<div id="tab_two" class="demo-infinite-container" v-else-if="!netError&&isHasData[2]">
								<mu-list>
									<template v-for="item in informationDataArr[2]">
										<!--<mu-list-item :title="item.entityJSONObject.title" />
											<mu-divider/>-->
										<mu-paper class="demo-paper" :zDepth="1">
											<div class="div1" @click="itemJump(item.informationId)">
												<div class="div2">
													<img id="icon_image" :src="item.imgPath" height="70px" width="70px" />
												</div>
												<div class="div3">
													<p class="title-font">{{item.title}}</p>
												</div>
												<div class="div4">
													<p class="content-font">{{item.briefIntroduction}}</p>
												</div>
												<div class="div3">
													<p class="bottom-font-one" v-text="showTime(item.releaseTime)"></p>
													<p class="bottom-font-two" v-text="item.collectNum+' 收藏'"></p>
												</div>
											</div>
										</mu-paper>
									</template>
								</mu-list>
								<mu-infinite-scroll :scroller="scroller" :loading="loading[2]" @load="loadMore" />
							</div>
							<div v-else class="nodata-div">
								<img class="nodata-img" src="../assets/img/history_empty.png">
							</div>
						</div>
						<div v-if="activeTab == '3'">
							<!-- 网络异常的显示 -->
							<div class="neterror-div" style="text-align: center; margin-top: 30px;" v-if="netError">
								<div>
									<img src="../assets/img/404.png"></img>
									<br/>
									<label style="color: lightgray; font-weight: 300; margin-bottom: 10px;">当前网络较差，请重新加载</label>
								</div>
							</div>
							<div id="tab_three" class="demo-infinite-container" v-else-if="!netError&&isHasData[3]">
								<mu-list>
									<template v-for="item in informationDataArr[3]">
										<!--<mu-list-item :title="item.entityJSONObject.title" />
											<mu-divider/>-->
										<mu-paper class="demo-paper" :zDepth="1">
											<div class="div1" @click="itemJump(item.informationId)">
												<div class="div2">
													<img id="icon_image" :src="item.imgPath" height="70px" width="70px" />
												</div>
												<div class="div3">
													<p class="title-font">{{item.title}}</p>
												</div>
												<div class="div4">
													<p class="content-font">{{item.briefIntroduction}}</p>
												</div>
												<div class="div3">
													<p class="bottom-font-one" v-text="showTime(item.releaseTime)"></p>
													<p class="bottom-font-two" v-text="item.collectNum+' 收藏'"></p>
												</div>
											</div>
										</mu-paper>
									</template>
								</mu-list>
								<mu-infinite-scroll :scroller="scroller" :loading="loading[3]" @load="loadMore" />
							</div>
							<div v-else class="nodata-div">
								<img class="nodata-img" src="../assets/img/history_empty.png">
							</div>
						</div>
						<div v-if="activeTab == '4'">
							<!-- 网络异常的显示 -->
							<div class="neterror-div" style="text-align: center; margin-top: 30px;" v-if="netError">
								<div>
									<img src="../assets/img/404.png"></img>
									<br/>
									<label style="color: lightgray; font-weight: 300; margin-bottom: 10px;">当前网络较差，请重新加载</label>
								</div>
							</div>
							<div id="tab_four" class="demo-infinite-container" v-else-if="!netError&&isHasData[4]">
								<mu-list>
									<template v-for="item in informationDataArr[4]">
										<!--<mu-list-item :title="item.entityJSONObject.title" />
											<mu-divider/>-->
										<mu-paper class="demo-paper" :zDepth="1">
											<div class="div1" @click="itemJump(item.informationId)">
												<div class="div2">
													<img id="icon_image" :src="item.imgPath" height="70px" width="70px" />
												</div>
												<div class="div3">
													<p class="title-font">{{item.title}}</p>
												</div>
												<div class="div4">
													<p class="content-font">{{item.briefIntroduction}}</p>
												</div>
												<div class="div3">
													<p class="bottom-font-one" v-text="showTime(item.releaseTime)"></p>
													<p class="bottom-font-two" v-text="item.collectNum+' 收藏'"></p>
												</div>
											</div>
										</mu-paper>
									</template>
								</mu-list>

								<mu-infinite-scroll :scroller="scroller" :loading="loading[4]" @load="loadMore" />
							</div>
							<div v-else class="nodata-div">
								<img class="nodata-img" src="../assets/img/history_empty.png">
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>

	</div>

</template>

<script>
	export default {
		data() {
			return {
				//高亮显示的标签
				activeTab: "0",
				num: 10,
				loading: [false, false, false, false, false],
				scroller: null,
				full: [false, false, false, false, false],
				loadingText: "正在加载...",
				informationDataArr: [
					[],
					[],
					[],
					[],
					[]
				],
				count: [1, 1, 1, 1, 1],
				isHasData: [true, true, true, true, true],
				quaryTime: ["", "", "", "", ""],
				isLogin: true,
				netError: false,
				mylineClass: "lineClass2",
				isFisrtError:false,
				isFirstLogin:true,
				userId:"",
			}
		},
		computed: {

		},
		methods: {
			showTime(val) {
				if(val) {
					return val.substring(0, 4) + "-" +
						val.substring(4, 6) + "-" +
						val.substring(6, 8) + " " +
						val.substring(8, 10) + ":" +
						val.substring(10, 12)
				} else {
					return ""
				}

			},
			loadMore() {
				var type = this.activeTab;
				if(!this.full[type]) {
					var self = this;
					self.$set(self.loading, type, true);
					//					this.loading[type] = true;
					if(type == 4) {
						setTimeout(() => {
							ajaxRequest({
								url: 'queryCollectionsByUser',
								data: {
									"pageNumber": self.count[type],
									"time": self.quaryTime[type]
								},
								async: false,
								success: function(dataJson) {

									for(var i = 0; i < dataJson.collectList.length; i++) {
										self.informationDataArr[type].push(dataJson.collectList[i]);
									}
									//											alert("总共数据的长度是" + self.informationDataArr.length);
									self.count[type]++;
									self.$set(self.loading, type, false);
									//									self.loading[type] = false;
									if(dataJson.collectList.length < 10) {
										//										self.full[type] = true;
										self.$set(self.full, type, true);
									}
								},
								error: function() {
									alertSysErrorTip(self);
								}
							});
						}, 2000)
					} else {
						setTimeout(() => {
							ajaxRequest({
								url: 'queryinformationByType',
								data: {
									"pageNumber": self.count[type],
									"type": type,
									"time": self.quaryTime[type]
								},
								async: false,
								success: function(dataJson) {

									for(var i = 0; i < dataJson.infoList.length; i++) {
										self.informationDataArr[type].push(dataJson.infoList[i]);
									}
									//											alert("总共数据的长度是" + self.informationDataArr.length);
									self.count[type]++;
									//									self.loading[type] = false;
									self.$set(self.loading, type, false);
									if(dataJson.infoList.length < 10) {
										//										self.full[type] = true;
										self.$set(self.full, type, true);

									}

								},
								error: function() {
									alertSysErrorTip(self);
								}
							});
						}, 2000)
					}

				} else {
					this.loadingText = "没有更多";
				}

			},
			handleTabChange(val) {
				this.activeTab = val;
				if(parseInt(val) == 4) {
					this.getCollectData(parseInt(val))
				} else {
					this.getData(parseInt(val));
				}
			},
			itemJump: function(val) {
				this.$router.push({
					path: '/monthreportdetail',
					query: {
						informationId: val,
						type: this.activeTab
					}
				});
			},
			goChoujiang: function() {
				this.$router.push({
					path: '/sign'
				});
			},
			goEnergy: function() {
				this.$router.push({
					path: '/mainView'
				});
			},
			getData(type) {
				var self = this;
//				alert("开始查询数据了")
				self.$set(self.loading, type, true);
				if(self.full[type] == true) {
					return;
				}
				ajaxRequest({
					url: 'queryinformationByType',
					data: {
						"pageNumber": self.count[type],
						"type": type,
					},
					async: false,
					success: function(dataJson) {

						for(var i = 0; i < dataJson.infoList.length; i++) {
							self.informationDataArr[type].push(dataJson.infoList[i]);
						}
						self.count[type]++;
						if(self.informationDataArr[type].length == 0) {
							self.$set(self.isHasData, type, false);
						} else {
							self.$set(self.isHasData, type, true);
						}
						self.netError = false;
						self.$set(self.quaryTime, type, dataJson.time);
						self.$set(self.loading, type, false);
						self.isFisrtError = false;
					},
					error: function() {
						alertSysErrorTip(self);
						self.netError = true;
					}
				});
			},
			getCollectData(type) {

				var self = this;
				self.$set(self.loading, type, true);
				if(self.full[type] == true) {
					return;
				}
				ajaxRequest({
					url: 'queryCollectionsByUser',
					data: {
						"pageNumber": self.count[type],
					},
					async: false,
					success: function(dataJson) {
//						alert(JSON.stringify())
						for(var i = 0; i < dataJson.collectList.length; i++) {
							self.informationDataArr[type].push(dataJson.collectList[i]);
						}
						self.count[type]++;
						if(self.informationDataArr[type].length == 0) {
							self.$set(self.isHasData, type, false);
						} else {
							self.$set(self.isHasData, type, true);
						}
						self.netError = false;
						self.$set(self.quaryTime, type, dataJson.time);
						self.$set(self.loading, type, false);
					},
					error: function() {
						alertSysErrorTip(self);
						self.netError = true;
					}
				});
			},
			userlogin() {
				var self = this;
//			alert("我是"+self.userId+"，我要开始登录了");
				ajaxRequest({
					url: 'userlogin',
					data: {
						userId: self.userId
					},
					async: true,
					success: function(dataJson) {	
//						alert(JSON.stringify(dataJson));
						if(dataJson.userId) {
							self.isLogin = true;
							self.isFirstLogin = false;
							self.getData(self.activeTab);
						} else {
							alertTip(self,"数据验证异常，请返回重试!");
							self.isLogin = false;
						}
					},
					error: function() {
						alertTip(self,"用户数据异常");
						self.isLogin = false;
					}
				})
//				alert("我是"+self.userId+"，我结束登录了");
			},
			goback() {
				//安卓返回App
				backAndroidApp();
			}
		},
		created() {
			if(this.isFirstLogin) {
//				alert("我进来了");
				var id = new RegExp("(^|&)" + "id" + "=([^&]*)(&|$)", "i");
				var param = window.location.search.substr(1).match(id);
//				alert(param);
				if(param != null) {
					this.userId = unescape(param[2]);
				} else {
					this.userId = "1400";
				}
//				alert(this.userId);
				this.userlogin();
			}
		},
		mounted() {
			this.scroller = document.getElementById("tab_outer");
			var self = this;
			

			if(this.$route.query.type) {
				this.activeTab = this.$route.query.type;
			}
			if(parseInt(this.activeTab) == 4) {
				this.getCollectData(parseInt(this.activeTab))
			} else {
				this.getData(parseInt(this.activeTab));
			}
		}
	};
</script>

<style scoped="yes">
	.titleBar {
		height: 40px;
		width: 100%;
		position: fixed;
		display: flex;
		flex-direction: row;
		align-items: center;
	}
	
	.leftDiv {
		width: 20%;
		height: 40px;
		text-align: left;
	}
	
	.leftImg {
		height: 24px;
		width: 24px;
		margin-left: 13px;
		margin-top: 8px;
	}
	
	.mui-bar~.mui-content .mui-fullscreen {
		top: 44px;
		height: auto;
	}
	
	.mui-pull-top-tips {
		position: absolute;
		top: -20px;
		left: 50%;
		margin-left: -25px;
		width: 40px;
		height: 40px;
		border-radius: 100%;
		z-index: 1;
	}
	
	.mui-bar~.mui-pull-top-tips {
		top: 24px;
	}
	
	.mui-pull-top-wrapper {
		width: 42px;
		height: 42px;
		display: block;
		text-align: center;
		background-color: #efeff4;
		border: 1px solid #ddd;
		border-radius: 25px;
		background-clip: padding-box;
		box-shadow: 0 4px 10px #bbb;
		overflow: hidden;
	}
	
	.mui-pull-top-tips.mui-transitioning {
		-webkit-transition-duration: 200ms;
		transition-duration: 200ms;
	}
	
	.mui-pull-top-tips .mui-pull-loading {
		/*-webkit-backface-visibility: hidden;
		-webkit-transition-duration: 400ms;
		transition-duration: 400ms;*/
		margin: 0;
	}
	
	.mui-pull-top-wrapper .mui-icon,
	.mui-pull-top-wrapper .mui-spinner {
		margin-top: 7px;
	}
	
	.mui-pull-top-wrapper .mui-icon.mui-reverse {
		/*-webkit-transform: rotate(180deg) translateZ(0);*/
	}
	
	.mui-pull-bottom-tips {
		text-align: center;
		background-color: #efeff4;
		font-size: 15px;
		line-height: 40px;
		color: #777;
	}
	
	.mui-pull-top-canvas {
		overflow: hidden;
		background-color: #fafafa;
		border-radius: 40px;
		box-shadow: 0 4px 10px #bbb;
		width: 40px;
		height: 40px;
		margin: 0 auto;
	}
	
	.mui-pull-top-canvas canvas {
		width: 40px;
	}
	
	.mui-slider-indicator.mui-segmented-control {
		background-color: #efeff4;
	}
	/*上面部分为下拉组件的样式*/
	
	.grey-background {
		padding: 0px;
		margin: 0px;
		background-color: #efeff4;
	}
	
	.myself-col-xs-5 {
		width: 20%
	}
	/*列表文字标题的样式*/
	
	.title-font {
		color: #000;
		font-size: 14px;
		overflow: hidden;
		text-overflow: ellipsis;
		text-align: left;
		display: -webkit-box;
		-webkit-line-clamp: 1;
		-webkit-box-orient: vertical
	}
	/*列表内容的样式*/
	
	.content-font {
		color: #8a8a8a;
		font-size: 12px;
		height: 31px;
		text-align: left;
		letter-spacing: 0px;
		line-height: 15px;
		margin-bottom: 3px;
		margin-top: 3px;
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical
	}
	/*列表底部元素的样式一*/
	
	.bottom-font-one {
		float: left;
		color: #8a8a8a;
		font-size: 12px;
		text-indent: 0px;
		letter-spacing: 0px;
		line-height: 14.5px;
		padding-bottom: 3px;
		padding-top: 3px;
	}
	/*列表底部元素的样式二*/
	
	.bottom-font-two {
		color: #00abfd;
		font-size: 12px;
		text-indent: 10px;
		letter-spacing: 0px;
		line-height: 14.5px;
		padding-bottom: 3px;
		padding-top: 3px;
	}
	
	.item-content {
		background-color: #efeff4;
		position: relative;
		overflow: hidden;
	}
	
	.div1 {
		/*border:1px red solid;*/
		overflow: hidden;
		zoom: 1;
		height: 80px;
		background-color: #ffffff;
		margin: 5px;
		padding: 5px;
	}
	
	.div2 {
		width: 70px;
		height: 70px;
		/*border:1px blue solid;*/
		float: left;
	}
	
	.div3 {
		height: 20px;
		/*border:1px green solid;*/
	}
	
	.div4 {
		height: 35px;
		/*border:1px green solid;*/
	}
	
	.div3,
	.div4 {
		margin-left: 85px;
	}
	
	.mui-control-content {
		background-color: #efeff4;
		min-height: 395px;
		max-height: 395px;
		height: 100%;
	}
	
	.mui-control-content .mui-loading {
		margin-top: 50px;
	}
	
	.top-background-outer {
		background-image: url(../telehems/img/8.png);
		background-size: 100% 100%;
		width: 100%;
		height: 30%;
	}
	
	.top-background-btn {
		display: -webkit-flex;
		display: flex;
		justify-content: center;
		width: 100%;
		height: 30%;
		margin-top: 10%;
	}
	
	.top-layout-left-right {
		display: -webkit-flex;
		display: flex;
		width: 50%;
		justify-content: center;
		align-items: center;
	}
	
	.button-left-right {
		display: -webkit-flex;
		display: flex;
		background-image: url(../telehems/img/11.png);
		background-size: 100% 100%;
		width: 150px;
		height: 50px;
		margin-top: 40%;
		margin-bottom: 10%;
		justify-content: center;
		align-items: center;
	}
	
	.text-type-one {
		color: #36ae3e;
		margin-left: 10px;
	}
	
	.text-type-two {
		color: #cd4a4a;
		margin-left: 10px;
	}
	
	.demo-infinite-container {
		/*position: absolute;*/
		/*display: absolute;*/
		width: 100%;
		/*height:50%;*/
		overflow: auto;
		-webkit-overflow-scrolling: touch;
		/*border: 2px solid #d9d9d9;*/
	}
	
	.demo-paper {
		display: inline-block;
		height: auto;
		width: 95%;
		margin-top: 1%;
		margin-left: 2.5%;
		margin-right: 2.5%;
		margin-bottom: 1%;
		/*text-align: center;*/
	}
	
	.nodata-div {
		width: 100%;
		height: 100%;
		padding-top: 20%;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	.nodata-img {
		width: 35%;
	}
	
	.lineClass2 {
		visibility: hidden;
		/*height: 10px;
		color: white;
		width: 10px;
		line-height: 10px;
		outline-width: medium;*/
	}
</style>