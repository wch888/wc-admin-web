<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<div class="pageContent">
    <form method="post" action="/product/update" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <input id="product_update" type="hidden" name="id" value="${bean.id}">
        <input type="hidden"  id="product_province_code" value="${bean.province}">
        <input type="hidden" id="product_city_code" value="${bean.city}">
        <input type="hidden"  id="product_area_code" value="${bean.area}">
        <div class="pageFormContent" layoutH="56">
            <p style="width: 100%;">
                <label>标题：</label>
                <input name="name" class="required textInput valid" type="text" size="80" value="${bean.name}"
                       alt="请输入标题">
            </p>
            <p style="width: 100%;">
                <label>价格：</label>
                <input name="price" class="required number valid" type="text" size="80" value="${bean.price}"
                       alt="请输入价格">
            </p>
            <p style="width: 100%;">
                <label>状态：</label>
                <select name="status" id="">
                    <option value="1" <c:if test="${bean.status ==1}">selected</c:if>>上架</option>
                    <option value="-1" <c:if test="${bean.status ==-1}">selected</c:if>>下架</option>
                    <option value="0" <c:if test="${bean.status ==0}">selected</c:if>>售罄</option>
                </select>
            </p>
            <div class="unit">
                <label>所在地区：</label>
                <span id="product_area"></span>
            </div>
            <p style="width: 100%;">
                <label>地址：</label>
                <input name="location" class="required textInput valid" type="text" size="80" value="${bean.location}"
                       alt="请输入地址">
            </p>
            <p style="width: 100%;">
                <label>开盘时间：</label>
                <input type="text" name="start_time"
                       value='<fmt:formatDate value="${bean.startTime}" pattern="yyyy-MM-dd hh:mm:ss"/>'
                       class="date textInput readonly valid required" datefmt="yyyy-MM-dd HH:mm:ss" readonly="true">
            </p>
            <p style="width: 100%;">
                <label>客服电话：</label>
                <input name="phone" class="required textInput valid" type="text" size="80" value="${bean.phone}"
                       alt="请输入客服电话">
            </p>
            <p style="width: 100%;">
                <label>物业：</label>
                <input name="service" class="required textInput valid" type="text" size="80" value="${bean.service}"
                       alt="请输入物业">
            </p>
            <p style="width: 100%;">
                <label>优惠信息：</label>
                <input name="discount" class="required textInput valid" type="text" size="80" value="${bean.discount}"
                       alt="请输入优惠信息">
            </p>
            <p style="width: 100%;">
                <label>专属顾问服务：</label>
                <input name="consultant" class="textInput valid" type="text" size="80"
                       alt="请输入专属顾问服务" value="${bean.consultant}">
            </p>
            <p style="width: 100%;">
                <label>开发商：</label>
                <input name="company" class="textInput valid" type="text" size="80"
                       alt="请输入开发商" value="${detail.company}">
            </p>
            <p style="width: 100%;">
                <label>装修状况：</label>
                <input name="decorate" class="textInput valid" type="text" size="80"
                       alt="请输入装修状况" value="${detail.decorate}">
            </p>
            <p style="width: 100%;">
                <label>住户数：</label>
                <input name="houseCount" class="textInput valid" type="text" size="80"
                       alt="请输入住户数" value="${detail.houseCount}">
            </p>
            <p style="width: 100%;">
                <label>容积率：</label>
                <input name="plotRatio" class="textInput valid" type="text" size="80"
                       alt="请输入容积率" value="${detail.plotRatio}">
            </p>
            <p style="width: 100%;">
                <label>绿化率：</label>
                <input name="greeningRate" class="textInput valid" type="text" size="80"
                       alt="请输入绿化率" value="${detail.greeningRate}">
            </p>
            <p style="width: 100%;">
                <label>停车位：</label>
                <input name="parkingSpace" class="textInput valid" type="text" size="80"
                       alt="请输入停车位" value="${detail.parkingSpace}">
            </p>
            <p style="width: 100%;">
                <label>年限：</label>
                <input name="age" class="textInput valid" type="text" size="80"
                       alt="请输入年限" value="${detail.age}">
            </p>
            <div class="unit">
                <label>第三方视频地址（flash地址）：</label>
                <input type="text"  name="video" i size="80" value="${detail.video}">
            </div>
            <div class="unit">
                <label>360看房第三方视频地址（flash地址）：</label>
                <input type="text"  name="video360" i size="80" value="${detail.video360}">
            </div>
            <p></p>
            <p style="width: 100%;">
                <label>经纪人：</label>
                <label>
                    <a class="button" href="/member/multiSelect?brokerType=1" target="dialog" rel="dlg_page10" mask="true"
                       width="750" height="400" title="添加经纪人"><span>添加经纪人</span></a>
                </label>
                <label>
                    <a class="button" href="javascript:;" id="delete_broker"><span>删除经纪人</span></a>
                </label>
            </p>
            <p id="product_broker" style="width: 100%;height: initial;border:1px solid #B8D0D6;padding:5px;margin:5px">
                <c:forEach var="e" varStatus="i" items="${broker}">
                    <label><input type="checkbox" name="broker" value="${e.userId}">${e.nickname}</label>
                </c:forEach>
            </p>
            <p style="width: 100%;">
                <label><a class="button" href="/product/img/list?pid=${bean.id}"
                          target="navTab"><span>图片管理</span></a></label>
                <label><a class="button" href="/product/type/list?pid=${bean.id}" target="navTab"><span>户型管理</span></a></label>
                <label><a class="button" href="/product/map/map?pid=${bean.id}"
                          target="navTab"><span>地理位置</span></a></label>
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
    var province = $("#product_province_code").val();
    var city = $("#product_city_code").val();
    var area = $("#product_area_code").val();

    $("#product_area").areaPicker({
        "provinceCode": province,
        "cityCode": city,
        "countyCode": area
    });

    function getCheckBoxVals() { //jquery获取复选框值
        var chk_value = [];
        $('#product_broker input[name="broker"]:checked').each(function () {
            chk_value.push($(this).val());
        });
        return chk_value;
//        alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value);
    }
    $("#delete_broker").click(function () {
        var chk_value = getCheckBoxVals();
        if (chk_value.length == 0) {
            alertMsg.error("请选择经纪人");
        }
        var pid = $("#product_update").val();
        var userId = chk_value.join(",");
        $.ajax({
            url: "/product/delBroker",
            data: {"pid": pid, "userId": userId},
            dataType: "json",
            success: function (json) {
                if (json.result) {
                    $('#product_broker input[name="broker"]:checked').each(function () {
                        $(this).parent().remove();
                    });
                }

            }
        });


        return false;
    });
</script>


