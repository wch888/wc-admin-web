<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>

<div class="pageContent">
    <form method="post" action="/member/add" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>手机号：</label>
                <input name="mobile" class="required textInput valid" type="text" size="20"
                       alt="请输入手机号">
            </p>
            <p style="width: 100%;">
                <label>密码：</label>
                <input name="password" class="required textInput valid" type="text" size="20"
                       alt="请输入密码">
            </p>
            <p style="width: 100%;">
                <label>类型：</label>
                <select name="type" id="">
                    <option value="0">注册会员</option>
                    <option value="1">待审核业主</option>
                    <option value="2">业主</option>
                </select>
            </p>
            <p style="width: 100%;">
                <label>经纪人类型：</label>
                <select name="brokerType" id="">
                    <option value="0">普通经纪人</option>
                    <option value="1">官方经纪人</option>
                </select>
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

