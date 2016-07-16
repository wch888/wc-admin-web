<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent">
    <form method="post" id="flash_update_form" action="/flash/update" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <input type="hidden" name="id" value="${bean.id}">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>标题：</label>
                <input name="title" class="required textInput valid" type="text" size="80" value="${bean.title}"
                       alt="请输入标题">
            </p>
            <div class="unit">
                <label>图片：</label>
                <input name="imgUrl" type="hidden" size="80" value="${bean.imgUrl}">
                <img src="${bean.imgUrl}" alt="" width="200px" height="150px">
            </div>
            <div class="unit">
                <div id="flash_update-uploadImg">上传图片</div>
            </div>
            <div class="unit">
                <label>类型：</label>
                <select name="type">
                    <option value="product" <c:if test="${e.type =='product'}">selected</c:if>>楼盘</option>
                </select>
            </div>
            <div class="unit">
                <label>参数：</label>
                <input name="param" class="textInput valid" type="text" size="80" value="${bean.param}"
                       alt="请输入参数">
                <p>1.当类型为楼盘，参数输入楼盘的编号</p>
            </div>
            <div class="unit">
                <label>状态：</label>
                <select name="status" id="">
                    <option value="1" <c:if test="${e.type ==1}">selected</c:if>>有效</option>
                    <option value="0" <c:if test="${e.type ==0}">selected</c:if>>无效</option>
                </select>
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
    $('#flash_update-uploadImg').uploadify({
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
        removeCompleted: false,
        removeTimeout: 3,
        requeueErrors: false,
        progressData: 'speed',
        preventCaching: false,
        suceessTimeout: 300,
        onDialogOpen: function () {
        },
        onUploadSuccess: function (file, data, response) {

            data = $.parseJSON(data);//把json字符串转换成json对象
            $("#flash_update_form input[name='imgUrl']").val(data.url);
            $("#flash_update_form").find("img").attr("src", data.url);
        },
        onQueueComplete: function (queueData) {

        }
    });
</script>


