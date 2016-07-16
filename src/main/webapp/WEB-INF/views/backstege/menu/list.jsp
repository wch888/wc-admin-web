<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<script src="js/dwz.tree.js" type="text/javascript"></script>
<div class="pageContent">
    <h5>${role.name}</h5>
    <form method="post" action="menu/add" class="pageForm required-validate" onsubmit="return validateCallback(this)">
        <input type="hidden" name="roleId" value="${role.id}">
        <ul class="tree treeFolder treeCheck expand">
            <li><a tname="menuId" tvalue="1" <c:if test="${fn:contains(menuStr,',1,')}">checked="true"</c:if>>资讯管理</a>
                <ul>
                    <li><a tname="menuId" tvalue="2" <c:if test="${fn:contains(menuStr,',2,')}">checked="true"</c:if>>新闻列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="3" <c:if test="${fn:contains(menuStr,',3,')}">checked="true"</c:if>>企业简介</a>
                    </li>
                </ul>
            </li>
            <li><a tname="menuId" tvalue="10" <c:if test="${fn:contains(menuStr,',10,')}">checked="true"</c:if>>消息管理</a>
                <ul>
                    <li><a tname="menuId" tvalue="11" <c:if test="${fn:contains(menuStr,',11,')}">checked="true"</c:if>>消息列表</a>
                    </li>
                </ul>
            </li>
            <li><a tname="menuId" tvalue="20" <c:if test="${fn:contains(menuStr,',20,')}">checked="true"</c:if>>楼盘管理</a>
                <ul>
                    <li><a tname="menuId" tvalue="21" <c:if test="${fn:contains(menuStr,',21,')}">checked="true"</c:if>>楼盘列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="22" <c:if test="${fn:contains(menuStr,',22,')}">checked="true"</c:if>>预约列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="23" <c:if test="${fn:contains(menuStr,',23,')}">checked="true"</c:if>>线索列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="24" <c:if test="${fn:contains(menuStr,',24,')}">checked="true"</c:if>>红包列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="25" <c:if test="${fn:contains(menuStr,',25,')}">checked="true"</c:if>>红包日志列表</a>
                    </li>
                </ul>
            </li>
            <li><a tname="menuId" tvalue="30"
                   <c:if test="${fn:contains(menuStr,',30,')}">checked="true"</c:if> >理财管理</a>
                <ul>
                    <li><a tname="menuId" tvalue="31" <c:if test="${fn:contains(menuStr,',31,')}">checked="true"</c:if>>理财列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="32" <c:if test="${fn:contains(menuStr,',32,')}">checked="true"</c:if>>理财订购用户</a>
                    </li>
                </ul>
            </li>
            <li><a tname="menuId" tvalue="40" <c:if test="${fn:contains(menuStr,',40,')}">checked="true"</c:if>>小区管理</a>
                <ul>
                    <li><a tname="menuId" tvalue="41" <c:if test="${fn:contains(menuStr,',41,')}">checked="true"</c:if>>小区列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="42" <c:if test="${fn:contains(menuStr,',42,')}">checked="true"</c:if>>水费列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="43" <c:if test="${fn:contains(menuStr,',43,')}">checked="true"</c:if>>电费列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="44" <c:if test="${fn:contains(menuStr,',44,')}">checked="true"</c:if>>物业费列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="45" <c:if test="${fn:contains(menuStr,',45,')}">checked="true"</c:if>>报修列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="46" <c:if test="${fn:contains(menuStr,',46,')}">checked="true"</c:if>>水费导出导入</a>
                    </li>

                </ul>
            </li>
            <li><a tname="menuId" tvalue="50" <c:if test="${fn:contains(menuStr,',50,')}">checked="true"</c:if>>用户管理</a>
                <ul>
                    <li><a tname="menuId" tvalue="51" <c:if test="${fn:contains(menuStr,',51,')}">checked="true"</c:if>>用户列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="54" <c:if test="${fn:contains(menuStr,',54,')}">checked="true"</c:if>>待审核业主列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="55" <c:if test="${fn:contains(menuStr,',55,')}">checked="true"</c:if>>业主列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="56" <c:if test="${fn:contains(menuStr,',56,')}">checked="true"</c:if>>官方经纪人</a>
                    </li>
                    <li><a tname="menuId" tvalue="57" <c:if test="${fn:contains(menuStr,',57,')}">checked="true"</c:if>>客户列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="52" <c:if test="${fn:contains(menuStr,',52,')}">checked="true"</c:if>>积分红包列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="53" <c:if test="${fn:contains(menuStr,',53,')}">checked="true"</c:if>>积分日志列表</a>
                    </li>
                </ul>
            </li>
            <li><a tname="menuId" tvalue="60"
                   <c:if test="${fn:contains(menuStr,',60,')}">checked="true"</c:if>>积分商品管理</a>
                <ul>
                    <li><a tname="menuId" tvalue="61" <c:if test="${fn:contains(menuStr,',61,')}">checked="true"</c:if>>积分商品列表</a>
                    </li>
                </ul>
            </li>
            <li><a tname="menuId" tvalue="70" <c:if test="${fn:contains(menuStr,',70,')}">checked="true"</c:if>>运营管理</a>
                <ul>
                    <li><a tname="menuId" tvalue="71" <c:if test="${fn:contains(menuStr,',71,')}">checked="true"</c:if>>首页闪图</a>
                    </li>
                    <li><a tname="menuId" tvalue="72" <c:if test="${fn:contains(menuStr,',72,')}">checked="true"</c:if>>首页导航</a>
                    </li>
                    <li><a tname="menuId" tvalue="73" <c:if test="${fn:contains(menuStr,',73,')}">checked="true"</c:if>>积分分值设置</a>
                    </li>
                    <li><a tname="menuId" tvalue="74" <c:if test="${fn:contains(menuStr,',74,')}">checked="true"</c:if>>配置管理</a>
                    </li>
                </ul>
            </li>
            <li><a tname="menuId" tvalue="80" <c:if test="${fn:contains(menuStr,',80,')}">checked="true"</c:if>>系统设置</a>
                <ul>
                    <li><a tname="menuId" tvalue="81" <c:if test="${fn:contains(menuStr,',81,')}">checked="true"</c:if>>价格列表</a>
                    </li>
                    <li><a tname="menuId" tvalue="82" <c:if test="${fn:contains(menuStr,',82,')}">checked="true"</c:if>>角色列表</a>
                    </li>
                </ul>
            </li>

        </ul>
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

<script type="text/javascript">

</script>