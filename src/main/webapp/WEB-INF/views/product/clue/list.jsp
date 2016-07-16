<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<form id="pagerForm" method="post" action="/clue/list">
    <input type="hidden" name="pageNum" value="${pc.pageInfo.page}"/>
    <input type="hidden" name="numPerPage" value="${pc.pageInfo.pagesize}"/>
</form>
<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="/clue/list" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tbody><tr>
                    <td>
                        姓名：<input type="text" name="name" class="textInput" value="${name}">
                    </td>
                    <td>
                        电话：<input type="text" name="mobile" class="textInput" value="${mobile}">
                    </td>
                    <td>
                        状态：
                        <select name="status" id="">
                            <option value="0"  <c:if test="${status ==0}">selected</c:if>>未认领</option>
                            <option value="1" <c:if test="${status ==1}">selected</c:if>>置业顾问跟进中</option>
                            <option value="2" <c:if test="${status ==2}">selected</c:if>>交易成功</option>
                            <option value="-2" <c:if test="${status ==-2}">selected</c:if>>交易失败</option>
                            <option value="-1" <c:if test="${status ==-1}">selected</c:if>>线索无效</option>
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
            <%--<li><a class="add" href="/clue/addView" target="navTab"><span>添加</span></a></li>--%>
            <li><a class="edit" href="/clue/updateView?id={id}" target="navTab"><span>编辑</span></a></li>
            <li><a class="delete" href="/clue/del?id={id}" target="ajaxTodo" title="你确定要删除吗?"><span>删除</span></a></li>
            <li class="line">line</li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="120" id="J-table">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">姓名</th>
            <th align="center">电话</th>
            <th align="center">内容</th>
            <th align="center">状态</th>
            <th align="center">提供人</th>
            <th align="center">跟进人</th>
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
                <tr target="id" rel="${e.id}"   <c:if test="${e.overTime!=null&&e.overTime==true}"> style="color: red" </c:if>>
                    <td>${i.index+1}</td>
                    <td>${e.name}</td>
                    <td>${e.mobile}</td>
                    <td>${e.content}</td>
                    <td>
                            <%-- 0未认领 1跟进中 2交易成功 -1线索无效 -2交易失败--%>
                        <c:if test="${e.status ==0}">未认领</c:if>
                        <c:if test="${e.status ==1}">置业顾问跟进中</c:if>
                        <c:if test="${e.status ==2}">交易成功</c:if>
                        <c:if test="${e.status ==-2}">交易失败</c:if>
                        <c:if test="${e.status ==-1}">线索无效</c:if>
                    </td>
                    <td>${e.userName}</td>
                    <td>${e.agentName}</td>
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
