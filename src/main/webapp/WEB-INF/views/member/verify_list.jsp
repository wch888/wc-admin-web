<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<form id="pagerForm" method="post" action="/member/list">
    <input type="hidden" name="pageNum" value="${pc.pageInfo.page}"/>
    <input type="hidden" name="numPerPage" value="${pc.pageInfo.pagesize}"/>
</form>

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="edit" href="/member/detail?id={id}" target="navTab"><span>详情</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="100" id="J-table">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">姓名</th>
            <th align="center">手机号</th>
            <th align="center">类型</th>
            <th align="center">经纪人类型</th>
            <th align="center">创建时间</th>
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
                        <%--0注册会员 1待审核业主 1业主 2普通经纪人(全民) 3官方经济人 4后台管理员--%>
                    <td>
                        <c:if test="${e.type ==0}">注册会员</c:if>
                        <c:if test="${e.type ==1}">待审核业主</c:if>
                        <c:if test="${e.type ==2}">业主</c:if>
                    </td>
                    <td>
                        <c:if test="${e.brokerType ==0}">普通经纪人</c:if>
                        <c:if test="${e.brokerType ==1}">官方经纪人</c:if>
                    </td>
                    <td><fmt:formatDate value="${e.createTime}" type="both"/></td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <%@include file="/WEB-INF/inc/per_page.jsp" %>
</div>
<script>

</script>
