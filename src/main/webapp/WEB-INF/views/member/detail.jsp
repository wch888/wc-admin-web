<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent">
    <input type="hidden" name="id" value="${bean.id}">
    <div class="pageFormContent" layoutH="56">
        <p style="width: 100%;">
            <label>手机号：</label>
            <input name="mobile" class="required textInput valid" type="text" size="80" value="${bean.mobile}"
                   alt="请输入手机号">
        </p>
        <p style="width: 100%;">
            <label>类型：</label>
            <select name="type" id="">
                <option value="0" <c:if test="${bean.type ==0}">selected</c:if>>注册会员</option>
                <option value="1" <c:if test="${bean.type ==1}">selected</c:if>>待审核业主</option>
                <option value="2" <c:if test="${bean.type ==2}">selected</c:if>>业主</option>
                <option value="3" <c:if test="${bean.type ==3}">selected</c:if>>普通经纪人</option>
                <option value="4" <c:if test="${bean.type ==4}">selected</c:if>>官方经济人</option>
                <option value="5" <c:if test="${bean.type ==5}">selected</c:if>>后台管理员</option>
            </select>
        </p>
        <p style="width: 100%;">
            <label>城市：</label>
            <input name="mobile" class="required textInput valid" type="text" size="80" value="${detail.cityId}"
                   alt="请输入手机号">
        </p>
        <p style="width: 100%;">
            <label>小区：</label>
            <input name="mobile" class="required textInput valid" type="text" size="80" value="${detail.cityId}">
        </p>
        <p style="width: 100%;">
            <label>地址：</label>
            <input name="address" class="required textInput valid" type="text" size="80" value="${detail.cityId}">
        </p>
        <p style="width: 100%;">
            <label>业主电话：</label>
            <input name="hostPhone" class="required textInput valid" type="text" size="80" value="${detail.hostPhone}">
        </p>
        <p style="width: 100%;">
            <label>身份证号码：</label>
            <input name="hostPhone" class="required textInput valid" type="text" size="80" value="${detail.idCard}">
        </p>
        <div class="unit">
            <label>提交相片：</label>
            <c:forEach var="e" varStatus="i" items="${imgs}">
                <img src="${e}" alt="" width="100px" height="100px">
            </c:forEach>

        </div>
        <p style="width: 100%;">
            <label><a class="button" href="/member/verify?id=${bean.id}" target="ajaxTodo" title="你确定要通过业主审核吗?"><span>通过业主审核</span></a></label>
        </p>
    </div>
</div>
<script type="text/javascript">

</script>


