<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>

<div class="pageContent">
    <form method="post" action="/price/add" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>标题：</label>
                <input name="title" class="required textInput valid" type="text" size="80"
                       alt="请输入标题">
            </p>
            <p style="width: 100%;">
                <label>最小价格：</label>
                <input name="min" class="textInput valid" type="text" size="80"
                       alt="请输入最小价格">
            <p>注：如果最小价格不输入的话一般为 xxxx以下 如4000以下</p>
            </p>
            <p style="width: 100%;">
                <label>最大价格：</label>
                <input name="max" class="textInput valid" type="text" size="80"
                       alt="请输入最大价格">
            <p>注：如果最小价格不输入的话一般为 xxxx以上 如9000以上</p>
            </p>
            <div class="unit">
                <label>所在地区：</label>
                <span id="price_area"></span>
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
    $("#price_area").areaPicker({});
</script>

