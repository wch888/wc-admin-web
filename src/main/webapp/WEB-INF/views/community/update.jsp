<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent">
    <form method="post" action="/community/update" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <input type="hidden" name="id" value="${bean.id}">
        <input type="hidden" id="community_province_code" value="${bean.provinceId}">
        <input type="hidden" id="community_city_code" value="${bean.cityId}">
        <input type="hidden" id="community_area_code" value="${bean.areaId}">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>标题：</label>
                <input name="title" class="required textInput valid" type="text" size="80" value="${bean.name}"
                       alt="请输入标题">
            </p>
            <p style="width: 100%;">
                <label>物业电话：</label>
                <input name="phone" class="required textInput valid" type="text" size="80" value="${bean.phone}"
                       alt="请输入电话">
            </p>
            <div class="unit">
                <label>所在地区：</label>
                <span id="home"></span>
            </div>
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
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>
<script src="/js/module/area.js"></script>
<script type="text/javascript">
    var province = $("#community_province_code").val();
    var city = $("#community_city_code").val();
    var area = $("#community_area_code").val();

    $("#home").areaPicker({
        "provinceCode": province,
        "cityCode": city,
        "countyCode": area
    });
</script>


