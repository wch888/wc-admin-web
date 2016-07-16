<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-cn">
    <meta charset="UTF-8">
    <title>万昌会</title>

    <link href="themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
    <link href="uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
    <!--[if IE]>
    <link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
    <![endif]-->

    <!--[if lte IE 9]>
    <script src="js/speedup.js" type="text/javascript"></script>
    <![endif]-->

    <script src="js/jquery-1.7.2.js" type="text/javascript"></script>
    <script src="js/jquery.cookie.js" type="text/javascript"></script>
    <script src="js/jquery.validate.js" type="text/javascript"></script>
    <script src="js/jquery.bgiframe.js" type="text/javascript"></script>
    <script src="xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
    <script src="xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
    <script src="uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

    <!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换是下面dwz.regional.zh.js还需要引入)
    <script src="bin/dwz.min.js" type="text/javascript"></script>
    -->
    <script src="js/dwz.min.js" type="text/javascript"></script>

    <script src="js/dwz.regional.zh.js" type="text/javascript"></script>
    <script type="text/javascript" charset="utf-8"
            src="http://map.qq.com/api/js?v=2.exp&key=HYJBZ-YGHWG-RBLQU-IXNX4-IRXU6-X4FTK"></script>
    <script type="text/javascript"
            src="http://map.qq.com/api/js?v=2.exp&key=YOUR_KEY&libraries=drawing,geometry,autocomplete,convertor"></script>

    <script type="text/javascript">
        $(function(){
            DWZ.init("/js/dwz.frag.xml", {
                loginUrl: "/loginAjaxView", loginTitle: "登录",	// 弹出登录对话框
//                loginUrl:"/loginAjax",	// 跳到登录页面
                statusCode:{ok:200, error:300, timeout:301}, //【可选】
                pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
                keys: {statusCode:"statusCode", message:"message"}, //【可选】
                ui:{hideMode:'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
                debug:false,	// 调试模式 【true|false】
                callback:function(){
                    initEnv();
                    $("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
                }
            });
        });

    </script>
</head>

<body scroll="no">
<div id="layout">
    <div id="header">
        <div class="headerNav">
            <a class="logo" href="#">标志</a>
            <ul class="nav">
                <li><a href="#">${member.nickname}</a></li>
                <li><a href="/loginout">退出</a></li>
            </ul>
        </div>
        <!-- navMenu -->
    </div>

    <div id="leftside">
        <div id="sidebar_s">
            <div class="collapse">
                <div class="toggleCollapse"><div></div></div>
            </div>
        </div>
        <div id="sidebar">
            <div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>


            <div class="accordion" fillSpace="sidebar">
                <c:if test="${fn:contains(menuStr,',1,')}">
                <div class="accordionHeader">
                    <h2><span>Folder</span>资讯管理</h2>
                </div>
                <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <c:if test="${fn:contains(menuStr,',2,')}">
                        <li><a href="news/list" target="navTab">新闻列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',3,')}">
                        <li><a href="introduction/list" target="navTab">企业简介</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',4,')}">
                            <li><a href="introduction/list" target="navTab">aaaa</a></li>
                        </c:if>
                    </ul>
                </div>
                </c:if>

                <c:if test="${fn:contains(menuStr,',10,')}">
                <div class="accordionHeader">
                    <h2><span>Folder</span>消息管理</h2>
                </div>
                <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <c:if test="${fn:contains(menuStr,',11,')}">
                        <li><a href="message/list" target="navTab">消息列表</a></li>
                        </c:if>
                    </ul>
                </div>
                </c:if>

                <c:if test="${fn:contains(menuStr,',20,')}">
                <div class="accordionHeader">
                    <h2><span>Folder</span>楼盘管理</h2>
                </div>
                <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <c:if test="${fn:contains(menuStr,',21,')}">
                        <li><a href="product/list" target="navTab">楼盘列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',22,')}">
                        <li><a href="bespeak/list" target="navTab">预约列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',23,')}">
                        <li><a href="clue/list" target="navTab">线索列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',24,')}">
                        <li><a href="luckyMoney/list" target="navTab">红包列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',25,')}">
                        <li><a href="luckyMoneyLog/list" target="navTab">红包日志列表</a></li>
                        </c:if>
                    </ul>
                </div>
                </c:if>

                <c:if test="${fn:contains(menuStr,',30,')}">
                <div class="accordionHeader">
                    <h2><span>Folder</span>理财管理</h2>
                </div>

                <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <c:if test="${fn:contains(menuStr,',31,')}">
                        <li><a href="financing/list" target="navTab">理财列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',32,')}">
                        <li><a href="financing/user/list" target="navTab">理财订购用户</a></li>
                        </c:if>
                    </ul>
                </div>
                </c:if>

                <c:if test="${fn:contains(menuStr,',40,')}">
                <div class="accordionHeader">
                    <h2><span>Folder</span>物业管理</h2>
                </div>

                <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <c:if test="${fn:contains(menuStr,',41,')}">
                        <li><a href="community/list" target="navTab">小区列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',42,')}">
                        <li><a href="waterRate/list" target="navTab">水费列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',43,')}">
                        <li><a href="powerRate/list" target="navTab">电费列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',44,')}">
                        <li><a href="propertyFee/list" target="navTab">物业费列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',45,')}">
                        <li><a href="repair/list" target="navTab">报修列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',46,')}">
                            <li><a href="waterRateExcel/excel" target="navTab">水费导出导入</a></li>
                        </c:if>
                    </ul>
                </div>
                </c:if>

                <c:if test="${fn:contains(menuStr,',50,')}">
                <div class="accordionHeader">
                    <h2><span>Folder</span>用户管理</h2>
                </div>

                <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <c:if test="${fn:contains(menuStr,',51,')}">
                        <li><a href="member/list" target="navTab">用户列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',54,')}">
                            <li><a href="member/verifyList" target="navTab">待审核业主列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',55,')}">
                            <li><a href="member/list?type=2" target="navTab">业主列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',56,')}">
                            <li><a href="agent/list" target="navTab">官方经纪人</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',57,')}">
                            <li><a href="customer/list" target="navTab">客户列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',52,')}">
                        <li><a href="wallet/list" target="navTab">积分红包列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',53,')}">
                        <li><a href="integrationLog/list" target="navTab">积分日志列表</a></li>
                        </c:if>

                    </ul>
                </div>
                </c:if>

                <c:if test="${fn:contains(menuStr,',60,')}">
                <div class="accordionHeader">
                    <h2><span>Folder</span>积分商品管理</h2>
                </div>

                    <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <c:if test="${fn:contains(menuStr,',61,')}">
                        <li><a href="goods/list" target="navTab">积分商品列表</a></li>
                        </c:if>
                    </ul>
                </div>
                </c:if>

                <c:if test="${fn:contains(menuStr,',70,')}">
                <div class="accordionHeader">
                    <h2><span>Folder</span>运营管理</h2>
                </div>

                    <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <c:if test="${fn:contains(menuStr,',71,')}">
                        <li><a href="/flash/list" target="navTab">首页闪图</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',72,')}">
                        <li><a href="/nav/list" target="navTab">首页导航</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',73,')}">
                            <li><a href="/setting/integration" target="navTab">积分分值设置</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',74,')}">
                            <li><a href="/setting/list" target="navTab">配置管理</a></li>
                        </c:if>
                    </ul>
                </div>
                </c:if>

                <c:if test="${fn:contains(menuStr,',80,')}">
                <div class="accordionHeader">
                    <h2><span>Folder</span>系统设置</h2>
                </div>

                    <div class="accordionContent">
                    <ul class="tree treeFolder">
                        <c:if test="${fn:contains(menuStr,',81,')}">
                        <li><a href="/price/list" target="navTab">价格列表</a></li>
                        </c:if>
                        <c:if test="${fn:contains(menuStr,',82,')}">
                            <li><a href="/role/list" target="navTab">角色列表</a></li>
                        </c:if>
                    </ul>
                </div>
                </c:if>
            </div>

        </div>
    </div>
    <div id="container">
        <div id="navTab" class="tabsPage">
            <div class="tabsPageHeader">
                <div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
                    <ul class="navTab-tab">
                        <li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
                    </ul>
                </div>
                <div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
                <div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
                <div class="tabsMore">more</div>
            </div>
            <ul class="tabsMoreList">
                <li><a href="javascript:;">我的主页</a></li>
            </ul>
            <div class="navTab-panel tabsPageContent layoutBox">

                <div class="page unitBox" style="">
                    <div class="accountInfo">
                    </div>
                    <div class="pageFormContent" layouth="80"
                         style="margin-right: 230px; height: 449px; overflow: auto;">

                    </div>
                </div>

            </div>
        </div>
    </div>

</div>

<%--<div id="footer">Copyright &copy; 2010 <a href="demo_page2.html" target="dialog">DWZ团队</a> 京ICP备05019125号-10</div>--%>

<script>
    $(".pageFormContent").load("/content");
</script>

</body>
</html>