<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<style>
    #map_container {
        width: 603px;
        height: 300px;
    }

</style>
<h2 class="contentTitle">${product.name}</h2>
<div class="pageContent">
    <form method="post" action="/product/map/update" class="pageForm required-validate"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <div>
            <input type="hidden" name="id" value="${product.id}">
            <input type="hidden" name="lat" id="product_map_lat" value="${product.lat}">
            <input type="hidden" name="lng" id="product_map_lng" value="${product.lng}">
            <div id="map_container"></div>
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


<script>
    $(function () {
        var map = new qq.maps.Map(document.getElementById("map_container"), {
            center: new qq.maps.LatLng(22.635763, 110.163018),
            zoom: 13
        });
        //设置定时器每隔2秒改变地图中心点位置
        // setTimeout(function() {
        //                //经纬度信息
        //     map.panTo(new qq.maps.LatLng(39.9, 116.4));
        // }, 2000);
        //绑定单击事件添加参数
        var marker = new qq.maps.Marker({});
        var lat = $("#product_map_lat").val();
        var lng = $("#product_map_lng").val();
        if (lat && lng) {
            var position = new qq.maps.LatLng(lat, lng);
            marker.setPosition(position);
            marker.setMap(map);
        }
        qq.maps.event.addListener(map, 'click', function (event) {
            console.log('您点击的位置为: [' + event.latLng.getLat() + ', ' +
                    event.latLng.getLng() + ']');
            $("#product_map_lat").val(event.latLng.getLat());
            $("#product_map_lng").val(event.latLng.getLng());

            var center = new qq.maps.LatLng(event.latLng.getLat(), event.latLng.getLng());
            marker.setPosition(center);
            marker.setMap(map);
        });

    });

</script>
