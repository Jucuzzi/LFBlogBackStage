<template>
	<div id="">
		<div class="info-title-div">
			<span class="info-title-text">资讯标题：</span>
			<input id="info_title_input" class="info-title-input"></input>
		</div>
		<div class="info-title-div">
			<span class="info-title-text">作<span class="kongge-span"></span>者：</span>
			<input id="info_user_input" class="info-title-input"></input>
		</div>
		<div class="info-title-div">
			<span class="info-title-text">来<span class="kongge-span"></span>源：</span>
			<input id="info_resouce_input" class="info-title-input"></input>
		</div>
		<div class="info-title-div">
			<span class="info-title-text">类<span class="kongge-span"></span>别：</span>
			<el-select v-model="infoType" placeholder="请选择" class="infotype-select">
				<el-option label="官方" value="0"></el-option>
				<el-option label="精品" value="1"></el-option>
				<el-option label="教程" value="2"></el-option>
				<el-option label="娱乐" value="3"></el-option>
			</el-select>
		</div>
		<div class="upload-img-div">
			<!--<span class="info-title-text">资讯封面：</span>
			<img id="upload_img" v-show="src!=''" class="upload-img" :src="src" alt="">
			<input value="选择图片" v-on:change="uploadImg" id="upload" name="file" accept="image/*" type="file" style="" />-->
		</div>
		<form method="post" action="#" enctype="multipart/form-data">
			<p>
				<button class="btn-upload btn-lg">Select</button>
				<input type="file" name="avatar" id="cropper-input" cropper-source="avatar" cropper-file-types="jpg,jpeg,png"> Support formats: JPG, PNG
			</p>
			<div class="preview-container">
				<div class="image-container target" id="cropper-target" cropper-aspect-ratio="auto" cropper-context="cropContext">
					<img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" class="noavatar" />
				</div>
				<div class="large-wrapper">
					<div class="image-container large" id="preview-large">
						<img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" class="noavatar">
					</div>
					<p>预览</p>
				</div>
				<!--<div>
					<div class="image-container medium" id="preview-medium">
						<img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" class="noavatar">
					</div>
					<p>Medium</p>
					<div class="image-container small" id="preview-small">
						<img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" class="noavatar">
					</div>
					<p>Small</p>
				</div>-->
			</div>
		</form>
		<!--<form method="post" action="#" enctype="multipart/form-data">
			<input type="file" id="cropper-input" name="avatar" cropper-source="avatar" cropper-file-types="jpg,jpeg,png" />
			<div class="image-container target" id="cropper-target" cropper-aspect-ratio="auto" cropper-context="cropContext">
				<img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" class="noavatar" />
			</div>
			<div class="image-container medium" id="preview-medium" cropper-preview="avatar">
				<img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" class="noavatar" />
			</div>
		</form>-->

		<quill-editor id="editor_input" ref="myTextEditor" v-model="content" :option="editorOption" @change="onEditorChange($event)"></quill-editor>
		<el-button class="editor-btn" type="primary" @click="preview">预览</el-button>
		<el-button class="editor-btn" type="primary" @click="submit">发布</el-button>

		<br/>

		<!--<div id="editor" type="text/plain" style="width:1024px;height:500px;"></div> 
 		-->
		<canvas id="canvas"></canvas>
		<check-dialog ref="checkdialog" :infocontent="infocontent"></check-dialog>
	</div>

</template>

<script>
	import { quillEditor } from 'vue-quill-editor';
	import checkDialog from './information/checkDialog.vue'
	import axios from 'axios'

	export default {
		data() {
			return {
				content: "",
				infocontent: {},
				uploadImgBase: "",
				src: '../../../static/image/information/picture_error.png',
				edit: null,
				ttt: null,
				infoType: "0",
				briefIntroduction: "",
				editorOption: {
					// some quill options
				}
			}
		},
		mounted() {
			var self = this;
			var cropper = new Cropper({
				element: document.getElementById('cropper-target'),
				previews: [
					document.getElementById('preview-large'),
//					document.getElementById('preview-medium'),
//					document.getElementById('preview-small')
				],
				onCroppedRectChange: function(rect) {
					
					var f = document.getElementById('cropper-input').files[0];
					var fileType = f.type;
					var reader = new FileReader();
					reader.readAsDataURL(f);
					reader.onload = function(e) { // reader onload start
						var result = event.target.result; //返回的dataURL   
						var image = new Image();
						image.src = result;
						//若图片大小大于1M，压缩后再上传，否则直接上传  
						if(f.size > 1 * 1024) {
							image.onload = function() { //创建一个image对象，给canvas绘制使用  
								var canvas = document.getElementById("canvas");
								canvas.width = 200;
								canvas.height = 200; //计算等比缩小后图片宽高   
								var ctx = canvas.getContext('2d');
//								alert(rect.left);
								ctx.drawImage(this, rect.left,rect.top,rect.width,rect.height,0, 0, canvas.width, canvas.height);
								var newImageData = canvas.toDataURL(fileType, 0.6); //重新生成图片      
								var sendData = newImageData.replace("data:" + fileType + ";base64,", '');
								self.uploadImgBase = sendData;
//								$("#upload_img").attr("src", newImageData);
//								$("#canvas").hide();
							}
						} else {
							image.onload = function() { //创建一个image对象，给canvas绘制使用     
								var sendData = result.replace("data:" + fileType + ";base64,", '');
//								alert(sendData);
//								$("#upload_img").attr("src", result);
								self.uploadImgBase = sendData;

							}
						}
					}
					console.log(rect);
				}
			});
			var self = this;
			var input = document.getElementById('cropper-input');
			$("#canvas").hide();
			input.onchange = function() {
				if(typeof FileReader !== 'undefined') {
					var reader = new FileReader();
					reader.onload = function(e) { // reader onload start 
						cropper.setImage(e.target.result);
					}
					if(input.files && input.files[0]) {
						reader.readAsDataURL(input.files[0]);
					}
				} else { // IE10-
					input.select();
					input.blur();
					var src = document.selection.createRange().text;
					cropper.setImage(src);
				}
			};
			//      	document.getElementById("upload").change(function () { 	        		
			//      		var objUrl = this.getObjectURL(this.files[0]) ; //获取图片的路径，该路径不是图片在本地的路径 
			//      		if (objUrl) { 
			//      			document.getElementById('upload_img').attr("src", objUrl) ; //将图片路径存入src中，显示出图片
			//      		} 
			//      	});

		},
		components: {
			quillEditor,
			checkDialog,
		},
		methods: {
			onEditorChange({
				editor,
				html,
				text
			}) {
				//				alert(html);
				var textString = text.replace(/ /g, "");
				this.edit = editor;
				this.content = html;
				if(textString.length > 50) {
					this.briefIntroduction = textString.substring(0, 50);
				} else {
					this.briefIntroduction = textString;
				}
			},
			submit() {
				const self = this;
				self.insetInfo();
				if(self.infocontent.title == '') {
					self.$message.error('标题不能为空');
					return
				} else if(self.content == '') {
					self.$message.error('内容不能为空');
					return
				} else if(this.infocontent.author == '') {
					this.$message.error('作者不能为空');
					return
				} else if(this.uploadImgBase == '') {
					this.$message.error('资讯封面不能为空');
					return
				}
				ajaxRequest({
					url: "addinformation",
					data: self.infocontent,
					async: true,
					success: function(dataJson) {
						if(dataJson.result == "successed") {
							self.$message.success("操作成功");
							document.getElementById("info_title_input").value = "";
							document.getElementById("info_user_input").value = "";
							document.getElementById("info_resouce_input").value = "";

						}

					},
					error: function() {
						alert("系统繁忙");
					}
				});

			},
			preview() {
				this.insetInfo();
				if(this.infocontent.title == '') {
					this.$message.error('标题不能为空');
					return
				} else if(this.content == '') {
					this.$message.error('内容不能为空');
					return
				} else if(this.infocontent.author == '') {
					this.$message.error('作者不能为空');
					return
				} else if(this.uploadImgBase == '') {
					this.$message.error('资讯封面不能为空');
					return
				}
				this.$refs.checkdialog.showDialog();
			},
			insetInfo() {
				this.infocontent["title"] = document.getElementById("info_title_input").value;
				this.infocontent["releaseTime"] = this.getNowFormatDate();
				this.infocontent["author"] = document.getElementById("info_user_input").value;
				this.infocontent["source"] = document.getElementById("info_resouce_input").value;
				this.infocontent["detailContent"] = '' + this.content;
				this.infocontent["userId"] = '1';
				this.infocontent["type"] = this.infoType;
				this.infocontent["imgPath"] = this.uploadImgBase;
				this.infocontent["briefIntroduction"] = this.briefIntroduction;
			},
			getNowFormatDate() {
				var date = new Date();
				var seperator1 = "-";
				var seperator2 = ":";
				var month = date.getMonth() + 1;
				var strDate = date.getDate();
				var str
				if(month >= 1 && month <= 9) {
					month = "0" + month;
				}
				if(strDate >= 0 && strDate <= 9) {
					strDate = "0" + strDate;
				}
				var currentdate = date.getFullYear() + month + strDate +
					"" + this.formatNum(date.getHours()) + this.formatNum(date.getMinutes()) +
					this.formatNum(date.getSeconds());
				return currentdate;
			},
			uploadImg() {
				var self = this;
				var f = document.getElementById('upload').files[0];
				var fileType = f.type;
				var reader = new FileReader();
				reader.readAsDataURL(f);
				reader.onload = function(e) { // reader onload start
					var result = event.target.result; //返回的dataURL   
					var image = new Image();
					image.src = result;
					//若图片大小大于1M，压缩后再上传，否则直接上传  
					if(f.size > 4 * 1024) {
						image.onload = function() { //创建一个image对象，给canvas绘制使用  
							var canvas = document.getElementById("canvas");
							canvas.width = 200;
							canvas.height = 200; //计算等比缩小后图片宽高   
							var ctx = canvas.getContext('2d');
							ctx.drawImage(this, 0, 0, canvas.width, canvas.height);
							var newImageData = canvas.toDataURL(fileType, 0.8); //重新生成图片      
							var sendData = newImageData.replace("data:" + fileType + ";base64,", '');
							self.uploadImgBase = sendData;
							$("#upload_img").attr("src", newImageData);
							$("#canvas").hide();
						}
					} else {
						image.onload = function() { //创建一个image对象，给canvas绘制使用     
							var sendData = result.replace("data:" + fileType + ";base64,", '');
							alert(sendData);
							$("#upload_img").attr("src", result);
							self.uploadImgBase = sendData;

						}
					}
				}
			},
			formatNum(num) {
				return num < 10 ? '0' + num : num;
			}
		},
		computed: {
			editor() {
				return this.$refs.myTextEditor.quillEditor;
			}

		}
	}
</script>

<style scoped="yes">
	.editor-btn {
		margin-top: 15px;
	}
	
	.info-title-div {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-bottom: 10px;
	}
	
	.info-title-text {
		font-size: 18px;
	}
	
	.info-title-input {
		height: 25px;
		margin-left: 10px;
		width: 50%;
	}
	
	.upload-img {
		width: 100px;
		height: 100px;
		margin: 20px;
	}
	
	.upload-img-div {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-bottom: 10px;
		margin-bottom: 20px;
	}
	
	.upload-btn {
		width: 80px;
	}
	
	.kongge-span {
		padding-left: 36px;
	}
	
	.infotype-select {
		margin-left: 10px;
	}
	
	form {
		margin-left: 18px;
	}
	
	form>p {
		padding-top: 22px;
		line-height: 40px;
		font-size: 14px;
		position: relative;
		color: #666;
		margin-bottom: 30px;
		overflow: hidden;
	}
	
	form>p .btn-upload {
		width: 120px;
		padding: 12px 20px;
		font-size: 14px;
		border: 1px solid #1e89e0;
		background-color: transparent;
		color: #1e89e0;
		margin-right: 20px;
	}
	
	form>p input {
		position: absolute;
		font-size: 480px;
		top: 22px;
		left: -30px;
		height: 42px;
		width: 150px;
		opacity: 0;
		-ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
		cursor: pointer;
	}
	
	form .btn-submit {
		font-size: 14px;
		padding: 12px 20px;
		width: 120px;
		margin-right: 20px;
	}
	
	.preview-container {
		overflow: hidden;
		color: #999;
		margin-bottom: 20px;
	}
	
	.preview-container .noavatar {
		background-color: #efeeef;
	}
	
	.preview-container>div {
		float: left;
		height: 320px;
	}
	
	.large-wrapper {
		border-left: 1px solid #eee;
		padding-left: 30px;
		margin-right: 30px;
	}
	
	.image-container {
		position: relative;
		overflow: hidden;
		border: 1px solid #bbb;
		margin-bottom: 5px;
	}
	
	.image-container .image-wrapper {
		position: absolute;
		width: 100%;
		height: 100%;
		left: 0;
		top: 0;
		overflow: hidden;
	}
	
	.image-container img {
		position: absolute;
	}
	
	.image-container.target {
		width: 320px;
		height: 320px;
		margin-right: 30px;
	}
	
	.image-container.target img {
		width: 320px;
		height: 320px;
	}
	
	.image-container.large {
		width: 210px;
		height: 210px;
	}
	
	.image-container.large img {
		width: 210px;
		height: 210px;
	}
	
	.image-container.large .noavatar {
		background-position: -320px 0;
	}
	
	.image-container.medium {
		width: 48px;
		height: 48px;
	}
	
	.image-container.medium img {
		width: 48px;
		height: 48px;
	}
	
	.image-container.medium .noavatar {
		background-position: -324px -210px;
	}
	
	.image-container.small {
		width: 20px;
		height: 20px;
		margin-top: 30px;
	}
	
	.image-container.small img {
		width: 20px;
		height: 20px;
	}
	
	.image-container.small .noavatar {
		background-position: -373px -210px;
	}
</style>