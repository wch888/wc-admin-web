<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent">
    <form method="post" id="flash_update_form" action="/clue/update" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <input type="hidden" name="id" value="${bean.id}">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>姓名：</label>
                <span>${bean.name}</span>
            </p>
            <p style="width: 100%;">
                <label>电话：</label>
                <span>${bean.mobile}</span>
            </p>
            <p style="width: 100%;">
                <label>内容：</label>
                <span>${bean.content}</span>
            </p>
            <div class="unit">
                <label>状态：</label>
                <select name="status" id="">
                    <option value="0"  <c:if test="${bean.status ==0}">selected</c:if>>未认领</option>
                    <option value="1" <c:if test="${bean.status ==1}">selected</c:if>>置业顾问跟进中</option>
                    <option value="2" <c:if test="${bean.status ==2}">selected</c:if>>交易成功</option>
                    <option value="-2" <c:if test="${bean.status ==-2}">selected</c:if>>交易失败</option>
                    <option value="-1" <c:if test="${bean.status ==-1}">selected</c:if>>线索无效</option>
                </select>
            </div>
            <p style="width: 100%;">
                <label>提示语：</label>
                <input name="tip" class="required textInput valid" type="text" size="80" alt="请输入提示语"
                       value="${bean.tip}">
            </p>
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


