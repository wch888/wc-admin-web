<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent">
    <form method="post" action="/suggest/update" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <input type="hidden" name="id" value="${bean.id}">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>用户：</label>
                <input name="userId" class="required textInput valid" type="text" size="80" alt="请输入用户"
                       value="${bean.userId}>
            </p>
            <div class=" unit">
                <label>缴费金额：</label>
                <input name="amount" class="required number valid" type="text" size="80" alt="请输入缴费金额"
                       value="${bean.amount}>元
            </div>
            <div class=" unit">
                <label>用水量：</label>
                <input name="total" class="required number valid" type="text" size="80" alt="请输入用水量"
                       value="${bean.total}">立方米
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


