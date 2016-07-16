<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<form id="pagerForm" method="post" action="/product/list">
    <input type="hidden" name="pageNum" value="${pc.pageInfo.page}"/>
    <input type="hidden" name="numPerPage" value="${pc.pageInfo.pagesize}"/>
</form>

<div class="pageContent">
    <table class="table" width="100%" layoutH="100" id="J-table">
        <thead>
        <tr>
            <th align="center">编号</th>
            <th align="center">标题</th>
            <th align="center">价格</th>
            <th align="center">电话</th>
            <th align="center">状态</th>
            <th align="center">选择</th>
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
                    <td>${e.id}</td>
                    <td>${e.name}</td>
                    <td>${e.price}</td>
                    <td>${e.phone}</td>
                    <td>
                        <c:if test="${e.status ==1}">上架</c:if>
                        <c:if test="${e.status ==-1}">下架</c:if>
                        <c:if test="${e.status ==0}">售罄</c:if>
                    </td>
                    <td><a href="javascript:" onclick="$.bringBack({id:'${e.id}', name:'${e.name}'})">选择</a></td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <%@include file="/WEB-INF/inc/per_page.jsp" %>
</div>
<script>

</script>
