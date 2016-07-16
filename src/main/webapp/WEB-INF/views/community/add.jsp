<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>

<div class="pageContent">
    <form method="post" action="/community/add" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>小区名字：</label>
                <input name="title" class="required textInput valid" type="text" size="80"
                       alt="请输入小区名字">
            </p>
            <p style="width: 100%;">
                <label>物业电话：</label>
                <input name="phone" class="required textInput valid" type="text" size="80"
                       alt="请输入电话">
            </p>
            <div class="unit">
                <label>所在地区：</label>
                <span id="home"></span>
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
<script src="/js/module/area.js"></script>
<script type="text/javascript">
    $("#home").areaPicker({});
</script>

