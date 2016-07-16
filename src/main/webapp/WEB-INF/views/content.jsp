<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent">
    <p><a href="/member/list?type=1" target="navTab">待审核认证业主（${verifyCount}）</a></p>
    <p><a href="/member/list?type=1" target="navTab">待处理线索（${clueCount}）</a></p>
    <p><a href="/member/list?type=1" target="navTab">待跟进预约（${bespeakCount}）</a></p>
    <p><a href="/member/list?type=1" target="navTab">待处理报修（${repairCount}）</a></p>
</div>


