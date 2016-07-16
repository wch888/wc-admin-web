<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent" id="repair_content">
        <input type="hidden" name="id" value="${bean.id}">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>业主：</label>
                <span>${member.nickname}</span>
            </p>
            <p style="width: 100%;">
                <label>电话：</label>
                <span>${member.mobile}</span>
            </p>
            <p style="width: 100%;">
                <label>房号：</label>
                <span>${detail.address}</span>
            </p>
            <div  class="unit">
                <label>问题：</label>
                <span>${bean.content}</span>
            </div>
            <div class="unit">
                <label>相片：</label>
                <div>
                    <c:forEach var="e" varStatus="i" items="${imgs}">
                        <img src="${e}" alt="" width="100" height="100">
                    </c:forEach>
                </div>
            </div>
            <p style="width: 100%;">
                <label>处理人：</label>
                <input name="follow.id" value="${bean.followId}" type="hidden"/>
                <input name="follow.nickname" class="required textInput valid readonly" readonly="true"
                       value="${bean.followName}">
                <a class="btnLook" href="/member/select" lookupGroup="follow">选择用户</a>
                <button id="repair_follow">保存</button>
            </p>
            <p style="width: 100%;">
                <label>状态：</label>
                <select name="status" id="">
                    <option value="2" <c:if test="${bean.status ==2}">selected</c:if>>处理完成</option>
                    <option value="1" <c:if test="${bean.status ==1}">selected</c:if>>处理中</option>
                    <option value="0" <c:if test="${bean.status ==0}">selected</c:if>>未受理</option>
                </select>
            </p>
            <p style="width: 100%;">
                <label>创建时间：</label>
                <span><fmt:formatDate value="${bean.createTime}" type="both"/></span>
            </p>
            <p style="width: 100%;">
                <label>受理时间：</label>
                <span><fmt:formatDate value="${bean.followTime}" type="both"/></span>
                <c:if test="${bean.status ==0}">
                    <button id="repair_dealing">受理</button>
                </c:if>

            </p>
            <p style="width: 100%;">
                <label>完成时间：</label>
                <span><fmt:formatDate value="${bean.finishTime}" type="both"/></span>
                <c:if test="${bean.status ==1}">
                    <button id="repair_finish">处理完成</button>
                </c:if>

            </p>
            <div class="unit">
                <label>用户评分：</label>
                <input id="comment_star_hidden" name="score" type="hidden" value="${bean.grade}"/>
                <div id="comment_star"></div>
                <span id="comment_star_error" generated="true"
                      style="display:none; overflow:hidden; width:165px; height:21px; padding:0 3px; line-height:21px; background:#F00; color:#FFF; top:5px; left:318px;">还没有打分哦</span>
            </div>
            <div class="unit">
                <label>用户评价：</label>
                <p>
                    <c:forEach var="e" varStatus="i" items="${comments}">
                        ${e.content}
                    </c:forEach>
                </p>
            </div>
        </div>

</div>
<script type="text/javascript" src="js/jquery.raty.js"></script>
<script type="text/javascript">
    var score = $("#comment_star_hidden").val();
    $('#comment_star').raty({

        click: function (num) {
            $("#comment_star_hidden").val(num);
        },
        path: '/images',
        score: score
    });


    $("#pms_comment_add").validate({
        submitHandler: function (form) {

            //打分
            var star = $("#comment_star_hidden").val();

            if (!star) {

                $("#comment_star_error").show();
                return false;
            }

            $("#comment_star_error").hide();

            return validateCallback(form, navTabAjaxDone);
        }
    });

    $("#repair_dealing").click(function () {
        var follow = $('#repair_content input[name="follow.id"]').val();
        if (!follow) {
            alertMsg.error("请选择处理人");
            return false;
        }
        $.post("/repair/dealing", {
            id: ${bean.id}
        }, function (data) {
//            if (data.result) {
//                alertMsg.correct('设置成功');
//            } else {
//                alertMsg.error('设置失败');
//            }
        });
    });

    $("#repair_finish").click(function () {
        var follow = $('#repair_content input[name="follow.id"]').val();
        if (!follow) {
            alertMsg.error("请选择处理人");
            return false;
        }
        $.post("/repair/finish", {
            id: ${bean.id}
        }, function (data) {
//            if (data.result) {
//                alertMsg.correct('设置成功');
//            } else {
//                alertMsg.error('设置失败');
//            }
        });
    });

    $("#repair_follow").click(function () {
        var follow = $('#repair_content input[name="follow.id"]').val();
        var nickname = $('#repair_content input[name="follow.nickname"]').val();
        if (!follow) {
            alertMsg.error("请选择处理人");
            return false;
        }
        $.post("/repair/update", {
            id: ${bean.id},
            "follow.id": follow,
            "follow.nickname": nickname
        }, function (data) {
        });
    })


</script>


