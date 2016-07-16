<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<form id="pagerForm" method="post" action="/product/list">
    <input type="hidden" name="pageNum" value="${pc.pageInfo.page}"/>
    <input type="hidden" name="numPerPage" value="${pc.pageInfo.pagesize}"/>
</form>

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="/product/addView" target="navTab"><span>添加</span></a></li>
            <li><a class="edit" href="/product/updateView?id={id}" target="navTab"><span>编辑</span></a></li>
            <li><a class="delete" href="/product/del?id={id}" target="ajaxTodo" title="你确定要删除吗?"><span>删除</span></a>
            </li>
            <li class="line">line</li>
            <li><a class="edit" href="/product/img/list?pid={id}" target="navTab"><span>编辑图片</span></a></li>
            <li><a class="edit" href="/product/type/list?pid={id}" target="navTab"><span>编辑户型</span></a></li>
            <li><a class="edit" href="/product/map/map?pid={id}" target="navTab"><span>地理位置</span></a></li>
            <li class="line">line</li>
            <li><a class="add" href="/news/addView?pid={id}" target="navTab"><span>添加楼盘资讯</span></a></li>

        </ul>
    </div>
    <table class="table" width="100%" layoutH="100" id="J-table">
        <thead>
        <tr>
            <th align="center">编号</th>
            <th align="center">标题</th>
            <th align="center">价格</th>
            <th align="center">电话</th>
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
                    <td>${e.id}</td>
                    <td>${e.name}</td>
                    <td>${e.price}</td>
                    <td>${e.phone}</td>
                    <td>
                        <c:if test="${e.status ==1}">上架</c:if>
                        <c:if test="${e.status ==-1}">下架</c:if>
                        <c:if test="${e.status ==0}">售罄</c:if>
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
