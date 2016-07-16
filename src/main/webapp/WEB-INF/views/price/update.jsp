<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent">
    <form method="post" action="/price/update" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <input type="hidden" name="id" value="${bean.id}">
        <input type="hidden" id="price_province_code" value="${bean.provinceId}">
        <input type="hidden" id="price_city_code" value="${bean.cityId}">
        <input type="hidden" id="price_area_code" value="${bean.areaId}">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>标题：</label>
                <input name="title" class="required textInput valid" type="text" size="80" value="${bean.title}"
                       alt="请输入标题">
            </p>
            <p style="width: 100%;">
                <label>最小价格：</label>
                <input name="min" class="textInput valid" type="text" size="80" value="${bean.min}"
                       alt="请输入最小价格">
                <span>注：如果最小价格不输入或者为0的话一般为 xxxx以下 如4000以下</span>
            </p>
            <p style="width: 100%;">
                <label>最大价格：</label>
                <input name="max" class="textInput valid" type="text" size="80" value="${bean.max}"
                       alt="请输入最大价格">
                <span>注：如果最大价格不输入或者为0的话一般为 xxxx以上 如9000以上</span>
            </p>
            <div class="unit">
                <label>所在地区：</label>
                <span id="price_area_update"></span>
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
    var province = $("#price_province_code").val();
    var city = $("#price_city_code").val();
    var area = $("#price_area_code").val();

    $("#price_area_update").areaPicker({
        "provinceCode": province,
        "cityCode": city,
        "countyCode": area
    });
</script>


