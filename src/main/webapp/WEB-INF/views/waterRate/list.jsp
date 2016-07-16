<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<form id="pagerForm" method="post" action="/waterRate/list">
    <input type="hidden" name="pageNum" value="${pc.pageInfo.page}"/>
    <input type="hidden" name="numPerPage" value="${pc.pageInfo.pagesize}"/>
</form>
<h2 class="contentTitle">${community.name}</h2>
<div class="pageHeader">
    <form rel="pagerForm" method="post" action="/member/multiSelect" onsubmit="return dwzSearch(this, 'dialog');">
        <div class="searchBar">
            <table class="searchContent">
                <tbody>
                <tr>
                    <td>
                        <label>房号:</label>
                        <input type="text" name="address" class="textInput" value="${address}" size="12">
                    </td>
                    <td>
                        <label>状态：</label>
                        <select name="status" id="">
                            <option value="1" <c:if test="${bean.status ==1}">selected</c:if>>已缴费</option>
                            <option value="0" <c:if test="${bean.status ==0}">selected</c:if>>欠费</option>
                        </select>
                    </td>
                    <td>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">查询</button>
                            </div>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>

        </div>
    </form>
</div>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="/waterRate/addView" target="navTab"><span>添加</span></a></li>
            <li><a class="edit" href="/waterRate/updateView?id={id}" target="navTab"><span>编辑</span></a></li>
            <li><a class="delete" href="/waterRate/del?id={id}" target="ajaxTodo"
                   title="你确定要删除吗?"><span>删除</span></a></li>
            <li class="line">line</li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="120" id="J-table">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">房号</th>
            <th align="center">业主</th>
            <th align="center">电话</th>
            <th align="center">应缴月份</th>
            <th align="center">缴费日期</th>
            <th align="center">用水量</th>
            <th align="center">金额</th>
            <th align="center">状态</th>
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
                    <td>${e.address}</td>
                    <td>${e.userName}</td>
                    <td>${e.mobile}</td>
                    <td><fmt:formatDate value="${e.payTime}" pattern="yyyy-MM-dd"/></td>
                    <td><fmt:formatDate value="${e.month}" pattern="yyyy-MM"/></td>
                    <td>${e.total}</td>
                    <td>${e.amount}</td>
                    <td>
                        <c:if test="${e.status ==1}">已缴费</c:if>>
                        <c:if test="${e.status ==0}">欠费</c:if>>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <%@include file="/WEB-INF/inc/per_page.jsp" %>
</div>
<script>

</script>
