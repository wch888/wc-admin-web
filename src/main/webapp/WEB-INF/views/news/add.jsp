<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>

<div class="pageContent" id="news_add">
	<form method="post" action="/news/add" class="pageForm required-validate"  onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" name="pid" value="${pid}">
			<p style="width: 100%;">
				<label>标题：</label>
				<input name="title" class="required textInput valid" type="text" size="80"
					   alt="请输入标题" >
			</p>
			<div class="unit">
				<label>默认图片：</label>
				<input name="thumb" class="textInput valid" type="hidden" size="80">
				<img src="" alt="" id="thumb" width="50px" height="50px">
			</div>
			<div class="unit">
				<div class="post-btn" id="J-news-add-uploadImg">上传图片</div>
			</div>
			<div class="unit">
				<label>简介：</label>
				<textarea name="desc" id="" cols="100" rows="3" class="required"></textarea>
			</div>
			<div class="unit">
				<label>内容：</label>
				<textarea class="editor" name="content" rows="30" cols="100" tools="simple"
						  upImgUrl="/file/uploadImg" upImgExt="jpg,jpeg,gif,png" >

				</textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	$('#J-news-add-uploadImg').uploadify({
		swf: '/swf/uploadify.swf',
		uploader: 'http://120.24.234.117:8080/file/uploadImg?size=600&size=300&size=200',
		height: 30,
		width: 120,
		buttonText: '上传图片',
		buttonCursor: 'pointer',
		fileObjName: 'file',
		auto: true,
		fileSizeLimit: '5000KB',
		fileTypeDesc: 'Image Files',
		fileTypeExts: '*.gif; *.jpg; *.png',
		formData: {},
		method: 'post',
		multi: true,
		queueID: 'J-add-five-image',
		queueSizeLimit: 99,
		uploadLimit: 50,
		removeCompleted: true,
		removeTimeout: 3,
		requeueErrors: false,
		progressData: 'speed',
		preventCaching: false,
		suceessTimeout: 300,
		onDialogOpen: function () {
		},
		onUploadSuccess: function (file, data, response) {

			data = $.parseJSON(data);//把json字符串转换成json对象

			$("#news_add input[name='thumb']").val(data.url);
			$("#thumb").attr("src",data.url);

		},
		onQueueComplete: function (queueData) {

		}
	});
</script>

