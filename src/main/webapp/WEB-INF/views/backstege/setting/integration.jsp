<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<style>
    .pageFormContent label {
        float: left;
        width: 150px;
        padding: 0 5px;
        line-height: 21px;
    }
</style>
<div class="pageContent">
    ${bean}
    <form method="post" action="/setting/integration/update" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <input type="hidden" name="id" value="${bean.id}">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>注册送积分：</label>
                <input name="reg" class="required textInput valid digits" type="text" size="10" value="${reg}">
            </p>
            <p style="width: 100%;">
                <label>全民经纪人-提交线索：</label>
                <input name="clueFail" class="required textInput valid digits" type="text" size="10" value="${clueFail}">
            </p>
            <p style="width: 100%;">
                <label>分享资讯送积分：</label>
                <input name="shareNews" class="required textInput valid digits" type="text" size="10" value="${shareNews}">
            </p>
            <p style="width: 100%;">
                <label>分享楼盘信息获取积分：</label>
                <input name="shareProduct" class="required textInput valid digits" type="text" size="10" value="${shareProduct}">
            </p>
            <p style="width: 100%;">
                <label>每日登陆送积分：</label>
                <input name="login" class="required textInput valid digits" type="text" size="10" value="${login}">
            </p>
            <p style="width: 100%;">
                <label>业主审核通过：</label>
                <input name="verifyHouseHold" class="required textInput valid digits" type="text" size="10" value="${verifyHouseHold}">
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
<script src="/js/module/area.js"></script>
<script type="text/javascript">
</script>


