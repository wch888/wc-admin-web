<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>

<div class="pageContent">
    <form method="post" action="/news/add" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <input type="hidden" name="pid" value="${pid}">
            <p style="width: 100%;">
                <label>标题：</label>
                <input name="title" class="required textInput valid" type="text" size="80"
                       alt="请输入标题">
            </p>

            <div class="unit">
                <label>简介：</label>
                <textarea name="desc" id="" cols="100" rows="3" class="required"></textarea>
            </div>
            <div class="unit">
                <label>内容：</label>
				<textarea class="editor" name="description" rows="30" cols="100" tools="simple">

				</textarea>
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

</script>

