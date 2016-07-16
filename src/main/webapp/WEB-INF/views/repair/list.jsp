<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<form id="pagerForm" method="post" action="/repair/list">
    <input type="hidden" name="pageNum" value="${pc.pageInfo.page}"/>
    <input type="hidden" name="numPerPage" value="${pc.pageInfo.pagesize}"/>
</form>

<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="/repair/list" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tbody><tr>
                    <td>
                        <input type="hidden" name="communityId"   value="${communityId}">
                        房号：<input type="text" name="address" class="textInput" value="${address}">
                    </td>
                    <td>
                        状态：
                        <select name="status" id="">
                            <option value="0" <c:if test="${status ==0}">selected</c:if>>未受理</option>
                            <option value="1" <c:if test="${status ==1}">selected</c:if>>处理中</option>
                            <option value="2" <c:if test="${status ==2}">selected</c:if>>处理完成</option>
                        </select>
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
    <div class="panelBar">
    <ul class="toolBar">
    <%--<li><a class="add" href="/repair/addView" target="navTab"><span>添加</span></a></li>--%>
    <li><a class="edit" href="/repair/updateView?id={id}" target="navTab"><span>详情</span></a></li>
    <%--<li><a class="delete" href="/repair/del?id={id}" target="ajaxTodo"--%>
    <%--title="你确定要删除吗?"><span>删除</span></a></li>--%>
    
    </ul>
    </div>
    <table class="table" width="100%" layoutH="110" id="J-table">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">房号</th>
            <th align="center">联系方式</th>
            <th align="center">小区</th>
            <th align="center">内容</th>
            <th align="center">状态</th>
            <th align="center">报修时间</th>
            <th align="center">受理时间</th>
            <th align="center">完成时间</th>
            <th align="center">评分</th>
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
                    <td>${e.address}</td>
                    <td>${e.mobile}</td>
                    <td>${e.communityName}</td>
                    <td>${fn:substring(e.content, 0, 15)}</td>
                    <td>
                        <c:if test="${e.status ==2}">处理完成</c:if>
                        <c:if test="${e.status ==1}">处理中</c:if>
                        <c:if test="${e.status ==0}">未受理</c:if>
                    </td>
                    <td><fmt:formatDate value="${e.createTime}" type="both"/></td>
                    <td><fmt:formatDate value="${e.followTime}" type="both"/></td>
                    <td><fmt:formatDate value="${e.finishTime}" type="both"/></td>
                    <td>${e.grade}</td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <%@include file="/WEB-INF/inc/per_page.jsp" %>
</div>
<script>

</script>
