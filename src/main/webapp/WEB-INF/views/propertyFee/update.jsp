<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent">
    <form method="post" action="/propertyFee/update" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <input type="hidden" name="id" value="${bean.id}">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>用户：</label>
                <input name="user.id" value="${bean.userId}" type="hidden"/>
                <input name="user.mobile" class="required textInput valid readonly" readonly="true"  value="${member.mobile}">
                <a class="btnLook" href="/member/select" lookupGroup="user">选择用户</a>
            </p>
            <div class="unit">
                <label>应缴月份：</label>
                <input type="text" name="month" class="date textInput readonly valid required" readonly="true"
                       value="<fmt:formatDate value="${bean.month}" pattern="yyyy-MM"/>">
            </div>
            <div class="unit">
                <label>缴费日期：</label>
                <input type="text" name="payTime" class="date textInput readonly valid required" readonly="true"
                       value="<fmt:formatDate value="${bean.payTime}" pattern="yyyy-MM-dd"/>">
            </div>
            <div class=" unit">
                <label>缴费金额：</label>
                <input name="amount" class="required number valid" type="text" size="80" alt="请输入缴费金额"
                       value="${bean.amount}">元
            </div>
            <div class="unit">
                <label>状态：</label>
                <select name="status" id="">
                    <option value="1" <c:if test="${bean.status ==1}">selected</c:if>>已缴费</option>
                    <option value="0" <c:if test="${bean.status ==0}">selected</c:if>>欠费</option>
                </select>
            </div>

        </div>
        <div class=" formBar">
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


