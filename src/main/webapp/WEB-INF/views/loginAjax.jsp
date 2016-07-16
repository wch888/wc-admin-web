<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent">
    <form method="post" action="/loginAjax" class="pageForm  required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
        <input type="hidden" value="1" name="handle">
        <div class="pageFormContent" layoutH="58">
            <div class="unit">
                <label>用户名：</label>
                <input type="text" name="username" size="30" class="required"/>
            </div>
            <div class="unit">
                <label>密码：</label>
                <input type="password" name="password" size="30" class="required"/>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
    </form>
    <script>
        $("input[name='username']").focus();
    </script>
</div>
