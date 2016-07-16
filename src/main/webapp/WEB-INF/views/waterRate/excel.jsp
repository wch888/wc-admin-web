<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent">
    <select action="/waterRateExcel/out">
        <input type="text" name="month" class="date textInput readonly valid required" readonly="true">
        <select name="communityId" id=""></select>
        <c:forEach var="e" varStatus="i" items="${list}">
            <option value="${e.id}">${e.name}</option>
        </c:forEach>
    </select>
        <input type="submit" value="水费导出">
    </form>
    <hr>
    <div id="waterRateUpload">水费导入</div>
    <p>状态:已缴费 未缴费</p>
    <p>费用单元格格式默认为数值</p>
</div>
<script>
    /*uploadify*/
    $('#waterRateUpload').uploadify({
        swf: '/swf/uploadify.swf',
        uploader: '/waterRateExcel/in',
        height: 30,
        width: 120,
        buttonText: '水费导入',
        buttonCursor: 'pointer',
        fileObjName: 'file',
        auto: true,
        fileSizeLimit: '15000KB',
        fileTypeExts: '*.xls',
        formData: {},
        method: 'post',
        multi: true,
        queueID: 'J-add-five-image',
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

            alert("导入成功")

        },
        onQueueComplete: function (queueData) {

        }
    });
</script>
