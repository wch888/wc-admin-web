<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<style>

</style>
<div class="pageContent">

    <form method="post" id="nav_add_form" action="/nav/add" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <div class="pageFormContent" layoutH="56">
                <p style="width: 100%;">
                    <label>标题：</label>
                    <input name="title" class="required textInput valid" type="text" size="80"
                           alt="请输入标题">
                </p>
                <div class="unit">
                    <label>图片：</label>
                    <input name="imgUrl" type="hidden" size="80">
                    <img src="" alt="" width="200px" height="150px">
                </div>
                <div class="unit">
                    <div id="nav_add-uploadImg">上传图片</div>
                </div>
                <div class="unit">
                    <label>类型：</label>
                    <select name="type">
                        <option value="product">楼盘</option>
                    </select>
                </div>
                <div class="unit">
                    <label>参数：</label>
                    <input name="title" class="textInput valid" type="text" size="80"
                           alt="请输入参数">
                    <p>1.当类型为楼盘，参数输入楼盘的编号</p>
                </div>
                <div class="unit">
                    <label>状态：</label>
                    <select name="status" id="">
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">保存</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>
<script type="text/javascript">
    $('#nav_add-uploadImg').uploadify({
        swf: '/swf/uploadify.swf',
        uploader: 'http://120.24.234.117/file/uploadImg?size=600&size=300&size=200',
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
        queueSizeLimit: 99,
        uploadLimit: 50,
        removeCompleted: true,
        removeTimeout: 3,
        requeueErrors: false,
        preventCaching: false,
        onDialogOpen: function () {
        },
        onUploadSuccess: function (file, data, response) {

            data = $.parseJSON(data);//把json字符串转换成json对象
            $("#nav_add_form input[name='imgUrl']").val(data.url);
            $("#nav_add_form").find("img").attr("src", data.url);
        },
        onQueueComplete: function (queueData) {

        }
    });
</script>

