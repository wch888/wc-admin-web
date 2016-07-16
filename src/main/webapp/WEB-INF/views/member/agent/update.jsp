<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent">
    <form method="post" action="/agent/update" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <input type="hidden" name="id" value="${bean.id}">
        <div class="pageFormContent" layoutH="700">
            <p style="width: 100%;">
                <label>姓名：</label>
                <input name="nickname" class="required textInput valid" type="text" size="20" value="${bean.nickname}"
                       alt="请输入姓名">
            </p>
            <p style="width: 100%;">
                <label>手机号：</label>
                <span>${bean.mobile}</span>
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">保存</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
    <div id="jbsxBox2" class="unitBox" style="float:left; display:block; overflow:auto; width:514px;">
        <div class="pageHeader" style="border:1px #B8D0D6 solid">
            <form onsubmit="return divSearch(this, 'jbsxBox2');" action="/customer/select?userId=${bean.id}"
                  method="post">
            </form>

            <div class="pageContent">
            </div>
        </div>
    </div>

    <div id="jbsxBox3" class="unitBox" style="margin-left:520px;">
        <div class="pageHeader" style="border:1px #B8D0D6 solid">
            <form onsubmit="return divSearch(this, 'jbsxBox3');" action="/bespeak/select?userId=${bean.id}"
                  method="post">
            </form>
            <div class="pageContent">
            </div>
        </div>
    </div>

</div>
<script type="text/javascript">
    $("#jbsxBox2 form").submit();
    $("#jbsxBox3 form").submit();
</script>


