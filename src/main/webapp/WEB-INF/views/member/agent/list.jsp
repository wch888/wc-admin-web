<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<form id="pagerForm" method="post" action="/agent/list">
    <input type="hidden" name="pageNum" value="${pc.pageInfo.page}"/>
    <input type="hidden" name="numPerPage" value="${pc.pageInfo.pagesize}"/>
</form>

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <%--<li><a class="add" href="/member/addView" target="navTab"><span>添加</span></a></li>--%>
            <li><a class="edit" href="/agent/updateView?id={id}" target="navTab"><span>详情</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="100" id="J-table">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">姓名</th>
            <th align="center">手机号</th>
            <th align="center">跟进预约</th>
            <th align="center">已录入名源</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty pc.entityList}">
            <tr>
                <td style="text-align: center;"><font color="#dc143c">暂无数据</font></td>
            </tr>
        </c:if>
        <c:if test="${!empty pc.entityList}">
            <c:forEach var="e" varStatus="i" items="${pc.entityList}">
                <tr target="id" rel="${e.id}">
                    <td>${i.index+1}</td>
                    <td>${e.nickname}</td>
                    <td>${e.mobile}</td>
                    <td><a href="/bespeak/list?userId=${e.id}" target="navTab"><span>查看列表</span></a></td>
                    <td><a href="/customer/list?userId=${e.id}" target="navTab"><span>查看列表</span></a></td>
                        <%--0注册会员 1待审核业主 1业主 2普通经纪人(全民) 3官方经济人 4后台管理员--%>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <%@include file="/WEB-INF/inc/per_page.jsp" %>
</div>
<script>

</script>
