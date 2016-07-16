<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>

<div class="pageContent">
    <form method="post" action="/luckyMoney/add" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>金额：</label>
                <input name="money" class="required number valid" type="text" size="80"
                       alt="请输入金额">
            </p>
            <p style="width: 100%;">
                <label>数量：</label>
                <input name="stock" class="required number valid" type="text" size="80"
                       alt="请输入数量">
            </p>
            <p style="width: 100%;">
                <label>开始时间：</label>
                <input type="text" name="startTime"
                       class="date textInput readonly valid required" datefmt="yyyy-MM-dd HH:mm:ss" readonly="true">
            </p>
            <p style="width: 100%;">
                <label>结束时间：</label>
                <input type="text" name="endTime"
                       class="date textInput readonly valid required" datefmt="yyyy-MM-dd HH:mm:ss" readonly="true">
            </p>
            <p style="width: 100%;">
                <label>选择商品：</label>
                <input name="product.id" value="" type="hidden"/>
                <input name="product.name" class="required textInput valid readonly" readonly="true">
                <label>
                    <a class="btnLook" href="/product/select" lookupGroup="product">选择商品</a>
                    <%--<a class="button" href="/product/select" target="dialog" rel="dlg_page10" mask="true"--%>
                    <%--title="选择商品"><span>选择商品</span></a>--%>

                </label>
            </p>
            <p style="width: 100%;">
                <label>状态：</label>
                <select name="status" id="">
                    <option value="1">有效</option>
                    <option value="0">无效</option>
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

