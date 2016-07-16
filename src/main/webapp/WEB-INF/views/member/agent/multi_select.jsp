<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<form id="pagerForm" method="post" action="/member/multiSelect">
    <input type="hidden" name="pageNum" value="${pc.pageInfo.page}"/>
    <input type="hidden" name="numPerPage" value="${pc.pageInfo.pagesize}"/>
</form>

<div class="pageHeader">
    <form rel="pagerForm" method="post" action="/member/multiSelect" onsubmit="return dwzSearch(this, 'dialog');">
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>电话:</label>
                    <input class="textInput" name="mobile" value="" type="text">
                </li>
                <li>
                    <label>姓名:</label>
                    <input class="textInput" name="nickname" value="" type="text">
                </li>
            </ul>
            <div class="subBar">
                <ul>
                    <li>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button type="submit">查询</button>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="button">
                            <div class="buttonContent">
                                <button type="button" id="member_select" warn="请选择">选择</button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </form>
</div>
<div class="pageContent">
    <table class="table" width="100%" layoutH="100" id="member_multi_table">
        <thead>
        <tr>
            <th align="center">序号</th>
            <th align="center">注册手机号</th>
            <th align="center">选择用户</th>
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
                    <td>${e.mobile}</td>
                    <td style="width: 68px;">
                        <div>
                            <a href="javascript:multi_select({id:'${e.id}', mobile:'${e.mobile}', nickname:'${e.nickname}'})"
                               title="选择">选择</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <%@include file="/WEB-INF/inc/per_page.jsp" %>
</div>
<script>
    function multi_select(data) {
//        console.log($("#product_broker"));
        var pid = $("#product_update").val();
        $.ajax({
            url: "/product/addBroker",
            data: {"pid": pid, "userId": data.id, "nickname": data.nickname},
            dataType: "json",
            success: function (json) {
                if (json.result) {
                    $("body #product_broker").append('<label><input type="checkbox" name="broker" value="' + data.id + '">' + data.nickname + '</label>');
                }
            }
        });

    }

</script>
