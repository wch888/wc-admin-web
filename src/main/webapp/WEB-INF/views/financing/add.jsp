<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>

<div class="pageContent">
    <form method="post" action="/financing/add" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>标题：</label>
                <input name="title" class="required textInput valid" type="text" size="80"
                       alt="请输入标题">
            </p>
            <p style="width: 100%;">
                <label>价格：</label>
                <input name="price" class="required number valid" type="text" size="80" alt="请输入价格">
            </p>
            <p style="width: 100%;">
                <label>理财周期：</label>
                <input name="time_limit" class="digits valid required">天
                <span>0代表无限期</span>
            </p>
            <p style="width: 100%;">
                <label>年化率：</label>
                <input name="rate" class="required number valid" type="text" size="80" alt="年化率">
            </p>
            <p style="width: 100%;">
                <label>认购或者取出条件：</label>
                <input name="condition" class="required textInput valid" type="text" size="80">
            </p>
            <p style="width: 100%;">
                <label>发行公司：</label>
                <input name="company" class="required textInput valid" type="text" size="80">
            </p>
            <p style="width: 100%;">
                <label>收益规则：</label>
                <input name="revenueRule" class="required textInput valid" type="text" size="80">
            </p>
            <p style="width: 100%;">
                <label>退保费用：</label>
                <input name="refund" class="required textInput valid" type="text" size="80">
            </p>
            <p style="width: 100%;">
                <label>时间类型：</label>
                <select name="type" id="">
                    <option value="1">短期</option>
                    <option value="2">中期</option>
                    <option value="3">长期</option>
                </select>
            </p>

            <div class="unit">
                <label>简介：</label>
                <textarea class="editor" name="content" rows="30" cols="100" tools="simple"></textarea>
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

