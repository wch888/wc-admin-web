<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/inc/taglib.jsp" %>
<html>
<head>
<title>登录注册页</title>
<link href="themes/css/login.css" rel="stylesheet" type="text/css" />
<script src="/js/jquery-1.7.2.js"></script>
</head>
<body>
<div id="login">
    <div id="login_header">
        <div class="login_headerContent">
            <div class="navList">
                <ul>
                </ul>
            </div>
            <h2 class="login_title"><img src="themes/default/images/login_title.png" /></h2>
        </div>
    </div>
    <div id="login_content">
        <div class="loginForm">
            <div>
                <c:if test="${!result.result}">
                    <p style="text-align: center;color: red;">
                            ${result.msg}
                    </p>
                </c:if>
            </div>
            <form action="/login" method="post" id="J_Login_Form">
                <p>
                    <label>用户名：</label>
                    <input type="text" name="username" size="20" class="login_input" />
                </p>
                <p>
                    <label>密码：</label>
                    <input type="password" name="password" size="20" class="login_input" />
                </p>
                <div class="login_bar">
                    <input class="sub" type="submit" value=" " />
                </div>
            </form>
        </div>
        <div class="login_banner"><img src="themes/default/images/login_banner.jpg" /></div>
        <div class="login_main">
            <ul class="helpList">
            </ul>
            <div class="login_inner">
            </div>
        </div>
    </div>
    <div id="login_footer">
        <div id="footer">Copyright &copy; 2013</div>
    </div>
</div>
<script>
    $(function(){
        $(document).keypress(function(e){
            if(e.keyCode==13){
                e.preventDefault();
                $('#J_Login_Form').submit();
            }
        });
    });
    $("input[name='username']").focus();
</script>
</body>
</html>
