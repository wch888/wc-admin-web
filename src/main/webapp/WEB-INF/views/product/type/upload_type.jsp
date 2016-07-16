<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<style>

    #product_type .uploadify-queue-item {
        float: left;
        position: relative;
        width: 200px;
        height: 360px;
        overflow: hidden;
        padding: 0;
        margin: 0 10px 10px 0;
        -webkit-border-radius: 0;
        -moz-border-radius: 0;
        border-radius: 0;
        border: 1px solid rgb(228, 228, 228);
        background-color: whiteSmoke;
        font-size: 12px;
    }

    .imgCube {
        width: 200px;
        height: 200px;
        top: 0;
        left: 0;
        -webkit-border-radius: 0;
        -moz-border-radius: 0;
        border-radius: 0;
    }

    #product_type .uploadify-queue-item p {
        margin-top: 10px;;
    }

    #product_type .uploadify-queue-item p a {
        margin-left: 40px;;
    }
</style>
<h2 class="contentTitle">${product.name}</h2>
<div style="display: none" id="type_box">
    <select name="type" >
        <c:forEach var="map" varStatus="i" items="${houseType}">
            <option value="${map.key}" <c:if test="${map.key==e.type}">selected</c:if>>${map.value}</option>
        </c:forEach>
    </select>
</div>
<div>
    <input type="hidden" id="product_type_pid" value="${product.id}">
    <div class="hd">
        <div class="post-btn" id="J-pro-add-uploadImg">上传图片</div>
    </div>
    <ul id="product_type" layouth="80">
        <c:if test="${!empty list}">
            <c:forEach var="e" varStatus="i" items="${list}">
                <li class="uploadify-queue-item">
                    <img class="imgCube" width="" height="" main-pic="false"
                         src="${e.img}" rel="${e.id}">
                    <p>
                    <select name="type" id="">
                        <c:forEach var="map" varStatus="i" items="${houseType}">
                            <option value="${map.key}" <c:if test="${map.key==e.type}">selected</c:if>>${map.value}</option>
                        </c:forEach>
                    </select>
                    </p>
                    <p><input name="title" class="required textInput valid" type="text" value="${e.title}" alt="请输入居室标题"
                              size="27"></p>
                    <p><input type="text" class="required textInput valid" name="description" value="${e.description}"
                              alt="请输入几房几厅"
                              size="27"></p>
                    <p><input type="text" class="required textInput valid" name="building" value="${e.building}"
                              alt="请输入楼栋" size="27"></p>
                    <p><a class="set" href="javascript:;">保存</a><a class="del" href="javascript:;">删除</a></p>
                </li>
            </c:forEach>
        </c:if>
    </ul>
</div>

<script type="text/javascript">
    var pid = $("#product_type_pid").val();

    $("#product_type").on("click", ".set", function () {
        var $item = $(this).parents(".uploadify-queue-item");
        var title = $item.find("input[name='title']").val();
        var type = $item.find("select[name='type']").val();
        var description = $item.find("input[name='description']").val();
        var building = $item.find("input[name='building']").val();
        var id = $item.find("img").attr("rel");

        if (id && pid) {
            $.ajax({
                url: "/product/type/update",
                data: {"id": id, "title": title, "description": description, "type": type, "building": building},
                dataType: "json",
                success: function (json) {
                    alertMsg.correct('设置成功')
                }
            });
        } else {
            alertMsg.error('参数错误' + id);
        }
    });

    $("#product_type").on("click", ".del", function () {
        var $item = $(this).parents(".uploadify-queue-item");
        var imgId = $item.find("img").attr("rel");
        if (imgId && pid) {
            $.ajax({
                url: "/product/type/del",
                data: {"id": imgId},
                dataType: "json",
                success: function (json) {
                    $item.remove();
                }
            });
        } else {
            alertMsg.error('参数错误' + imgId + pid);
        }
    });

    /*uploadify*/
    $('#J-pro-add-uploadImg').uploadify({
        swf: '/swf/uploadify.swf',
        uploader: 'http://120.24.234.117/file/uploadImg?size=600&size=300&size=200',
        height: 30,
        width: 120,
        buttonText: '上传图片',
        buttonCursor: 'pointer',
        fileObjName: 'file',
        auto: true,
        fileSizeLimit: '5000KB',
        fileTypeDesc: 'Image Files',
        fileTypeExts: '*.gif; *.jpg; *.png',
        formData: {},
        method: 'post',
        multi: true,
        queueID: 'J-add-five-image',
        queueSizeLimit: 99,
        uploadLimit: 50,
        removeCompleted: false,
        removeTimeout: 3,
        requeueErrors: false,
        progressData: 'speed',
        preventCaching: false,
        suceessTimeout: 300,
        onDialogOpen: function () {
        },
        onUploadSuccess: function (file, data, response) {
            var select =$("#type_box").html();
            console.log(select);
            data = $.parseJSON(data);//把json字符串转换成json对象
            var _con = '<li id="' + file.id + '" class="uploadify-queue-item"><img class="imgCube"  src="' + data.url + '" alt="" key="' + data.persistUri + '">';
            _con += '<p>'+select+'</p>';
            _con += '<p><input name="title" class="required textInput valid" type="text" value="" alt="请输入标题" size="27"></p>';
            _con += '<p><input type="text" class="required textInput valid" name="description" value="" alt="请输入几房几厅" size="27"></p>';
            _con += '<p><input type="text" class="required textInput valid" name="building" value="" alt="请输入楼栋" size="27"></p>';
            _con += '<p><a  class="set" href="javascript:;">保存</a><a class="del" href="javascript:;" >删除</a></p></li>';
            $('#product_type').append(_con).find('.data').css('color', '#11CDF1').fadeOut(2000).end().find('.uploadify-progress').fadeOut(2500);

            $.ajax({
                url: "/product/type/add",
                type: "POST",
                data: {url: data.url, id: pid},
                success: function (data) {
                    if (!data.result) {
                        alert("保存失败");
                    } else {
                        $('#' + file.id + ' img').attr("rel", data.data.id);
                    }
                }
            });

        },
        onQueueComplete: function (queueData) {

        }
    });


</script>
