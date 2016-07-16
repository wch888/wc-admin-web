<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<style>

    #product_img .uploadify-queue-item {
        float: left;
        position: relative;
        width: 150px;
        height: 170px;
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
        width: 150px;
        height: 150px;
        top: 0;
        left: 0;
        -webkit-border-radius: 0;
        -moz-border-radius: 0;
        border-radius: 0;
    }

    #product_img .uploadify-queue-item p a {
        margin-left: 20px;;
    }
</style>
<p><img id="default_img" src="${product.defaultImg}" alt="" class="imgCube"
        style="display: inline-block"><span>${product.name}</span></p>
<div>
    <input type="hidden" id="image_pid" value="${product.id}">
    <div class="hd">
        <div class="post-btn" id="J-pro-add-uploadImg">上传图片</div>
    </div>
    <ul id="product_img" layouth="80">
        <c:if test="${!empty list}">
            <c:forEach var="e" varStatus="i" items="${list}">
                <li class="uploadify-queue-item">
                    <img class="imgCube" width="" height="" main-pic="false"
                         src="${e.image}" rel="${e.id}">
                    <p><a class="set" href="javascript:;">设置为封面</a><a class="del" href="javascript:;">删除</a></p>
                </li>
            </c:forEach>
        </c:if>
    </ul>
</div>

<script type="text/javascript">
    var pid = $("#image_pid").val();

    $("#product_img").on("click", ".set", function () {
        var defaultImg = $(this).parents(".uploadify-queue-item").find("img").attr("src");
        if (defaultImg && pid) {
            $.ajax({
                url: "/product/img/defaultImg",
                data: {"id": pid, "defaultImg": defaultImg},
                dataType: "json",
                success: function (json) {
                    $("#default_img").attr("src", defaultImg);
                    alertMsg.correct('设置成功')
                }
            });
        } else {
            alertMsg.error('参数错误' + defaultImg + pid);
        }
    });

    $("#product_img").on("click", ".del", function () {
        var $item = $(this).parents(".uploadify-queue-item");
        var imgId = $item.find("img").attr("rel");
        if (imgId && pid) {
            $.ajax({
                url: "/product/img/del",
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

            data = $.parseJSON(data);//把json字符串转换成json对象
            var _con = '<li id="' + file.id + '" class="uploadify-queue-item"><img class="imgCube"  src="' + data.url + '" alt="" key="' + data.persistUri + '">';
            _con += '<p><a  class="set" href="javascript:;">设置为封面</a><a class="del" href="javascript:;">删除</a></p></li>';
            $('#product_img').append(_con).find('.data').css('color', '#11CDF1').fadeOut(2000).end().find('.uploadify-progress').fadeOut(2500);

            $.ajax({
                url: "/product/img/add",
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
