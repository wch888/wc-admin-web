<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<form id="pagerForm" method="post" action="/luckyMoneyLog/list">
    <input type="hidden" name="pageNum" value="${pc.pageInfo.page}"/>
    <input type="hidden" name="numPerPage" value="${pc.pageInfo.pagesize}"/>
</form>
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="/luckyMoneyLog/list" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tbody><tr>
                    <td>
                        姓名：
                        <input type="text" name="userName" class="textInput" value="${userName}">
                    </td>

                    <td>
                        <div class="buttonActive"><div class="buttonContent"> <button type="submit">搜索</button></div></div>
                    </td>
                </tr>
                </tbody></table>
        </div>
    </form>
</div>
<div class="pageContent">
    <%--<div class="panelBar">--%>
    <%--<ul class="toolBar">--%>
    <%--<li><a class="add" href="/luckyMoney/addView" target="navTab"><span>添加</span></a></li>--%>
    <%--<li><a class="edit" href="/luckyMoney/updateView?id={id}" target="navTab"><span>编辑</span></a></li>--%>
    <%--<li><a class="delete" href="/luckyMoney/del?id={id}" target="ajaxTodo"--%>
    <%--title="你确定要删除吗?"><span>删除</span></a></li>--%>
    <%--<li class="line">line</li>--%>
    <%--</ul>--%>
    <%--</div>--%>
    <table class="table" width="100%" layoutH="110" id="J-table">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">金额</th>
            <th align="center">用户</th>
            <th align="center">商品标题</th>
            <th align="center">状态</th>
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
                    <td>${e.amount}</td>
                    <td>${e.userName}</td>
                    <td>${e.title}</td>
                    <td>
                        <c:if test="${e.status ==0}">已使用</c:if>
                        <c:if test="${e.status ==1}">有效</c:if>
                        <c:if test="${e.status ==-1}">过期</c:if>
                    </td>
                    <td><fmt:formatDate value="${e.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <%@include file="/WEB-INF/inc/per_page.jsp" %>
</div>
<script>

</script>
