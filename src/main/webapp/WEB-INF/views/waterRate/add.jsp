<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>

<div class="pageContent">
    <form method="post" action="/waterRate/add" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>用户：</label>
                <input name="user.id" value="" type="hidden"/>
                <input name="user.mobile" class="required textInput valid readonly" readonly="true">
                <a class="btnLook" href="/member/select" lookupGroup="user">选择用户</a>
            </p>
            <div class="unit">
                <label>应缴月份：</label>
                <input type="text" name="month" class="date textInput readonly valid required" readonly="true">
            </div>
            <div class="unit">
            <label>缴费日期：</label>
            <input type="text" name="payTime" class="date textInput readonly valid required" readonly="true">
            </div>
            <div class="unit">
                <label>水费：</label>
                <input name="waterAmount" class="required number valid" type="text" size="80" alt="请输入水费">元
            </div>
            <div class="unit">
                <label>污水处理费：</label>
                <input name="sewageAmount" class="required number valid" type="text" size="80" alt="请输入污水处理费">元
            </div>
            <div class="unit">
                <label>水资源费：</label>
                <input name="resourceAmount" class="required number valid" type="text" size="80" alt="请输入水资源费">元
            </div>
            <div class="unit">
                <label>用水量：</label>
                <input name="total" class="required number valid" type="text" size="80" alt="请输入用水量">立方米
            </div>
            <div class="unit">
                <label>单价：</label>
                <input name="price" class="required number valid" type="text" size="80" alt="请输入单价"
                       value="">元/立方米
            </div>
            <div class="unit">
                <label>其他费用：</label>
                <input name="other" class="required number valid" type="text" size="80" alt="请输入其他费用"
                       value="">元
            </div>
            <div class="unit">
                <label>状态：</label>
                <select name="status" id="">
                    <option value="1">已缴费</option>
                    <option value="0">欠费</option>
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

</script>

