<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<form id="pagerForm" method="post" action="/suggest/list">
    <input type="hidden" name="pageNum" value="${pc.pageInfo.page}"/>
    <input type="hidden" name="numPerPage" value="${pc.pageInfo.pagesize}"/>
</form>

<div class="pageContent">
    <div class="panelBar">
        <%--<ul class="toolBar">--%>
        <%--<li><a class="add" href="/suggest/addView" target="navTab"><span>添加</span></a></li>--%>
        <%--<li><a class="edit" href="/suggest/updateView?id={id}" target="navTab"><span>编辑</span></a></li>--%>
        <%--<li><a class="delete" href="/suggest/del?id={id}" target="ajaxTodo"--%>
        <%--title="你确定要删除吗?"><span>删除</span></a></li>--%>
        <%--<li class="line">line</li>--%>
        <%--</ul>--%>
    </div>
    <table class="table" width="100%" layoutH="100" id="J-table">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">内容</th>
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
                <tr target="id" rel="${e.id}" <c:if
                        test="${e.overTime!=null&&e.overTime==true}"> style="color: red" </c:if>>
                    <td>${i.index+1}</td>
                    <td>${e.content}</td>
                    <td>${e.total}</td>
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
