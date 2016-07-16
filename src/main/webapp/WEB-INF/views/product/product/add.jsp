<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>

<div class="pageContent">
    <form method="post" action="/product/add" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>标题：</label>
                <input name="name" class="required textInput valid" type="text" size="80"
                       alt="请输入标题">
            </p>
            <p style="width: 100%;">
                <label>价格：</label>
                <input name="price" class="required number valid" type="text" size="80"
                       alt="请输入价格">
            </p>
            <p style="width: 100%;">
                <label>状态：</label>
                <select name="status" id="">
                    <option value="1">上架</option>
                    <option value="-1">下架</option>
                    <option value="0">售罄</option>
                </select>
            </p>
            <div class="unit">
                <label>所在地区：</label>
                <span id="product_area_add"></span>
            </div>
            <p style="width: 100%;">
                <label>地址：</label>
                <input name="location" class="required textInput valid" type="text" size="80"
                       alt="请输入地址">
            </p>
            <p style="width: 100%;">
                <label>开盘时间：</label>
                <input type="text" name="start_time" class="date textInput readonly valid" datefmt="yyyy-MM-dd HH:mm:ss"
                       readonly="true">
            </p>
            <p style="width: 100%;">
                <label>物业：</label>
                <input name="service" class="required textInput valid" type="text" size="80"
                       alt="请输入物业">
            </p>
            <p style="width: 100%;">
                <label>客服电话：</label>
                <input name="phone" class="required textInput valid" type="text" size="80"
                       alt="请输入客服电话">
            </p>
            <p style="width: 100%;">
                <label>优惠信息：</label>
                <input name="discount" class="required textInput valid" type="text" size="80"
                       alt="请输入优惠信息">
            </p>
            <p style="width: 100%;">
                <label>专属顾问服务：</label>
                <%--房源推荐 购房咨询 现场带看--%>
                <input name="consultant" class="textInput valid" type="text" size="80"
                       alt="请输入专属顾问服务">
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

    $("#product_area_add").areaPicker({});

</script>

