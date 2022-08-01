<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>简历管理-登录</title>
    <%@ include file="../WEB-INF/page/admin/common/style.jsp" %>
</head>

<body class="login-page">
    <div class="login-box">
        <div class="logo">
            <a href="javascript:void(0);"><b>简历管理后台登录</b></a>
            <small>您身边最好用的简历助手</small>
        </div>
        <div class="card">
            <div class="body">
                <form class="form-validation" method="post" action="${ctx}/user/login">
                    <div class="msg">赶紧登录吧</div>
                    <div class="input-group form-group form-float">
                        <span class="input-group-addon">
                            <i class="material-icons">email</i>
                        </span>
                        <div class="form-line">
                            <input type="email" class="form-control" name="email" maxlength="50" placeholder="邮箱" required autofocus>
                        </div>
                    </div>
                    <div class="input-group form-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                        <div class="form-line">
<%--                            而加这个是为了可以将password发送给后台，它的值是通过jQuery获取然后通过加密再设置回来的--%>
                            <input type="hidden" name="password">
<%-- 这里password不可以有name属性，不然就会将input输入的发给服务器，我们需要加密后才发过去，所以不需要name属性--%>
                            <input  id="originPassword" type="password" class="form-control" maxlength="20"placeholder="密码" required>
                        </div>
                    </div>
                    <div class="input-group form-group captcha">
                        <span class="input-group-addon">
                            <i class="material-icons">security</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control" name="captcha" placeholder="验证码" required>
                        </div>
<%--                        验证码由后端生成--%>
                        <img id="captcha" src="${ctx}/user/captcha" alt="验证码">
                    </div>
                    <div class="row">
                        <div class="col-xs-8 p-t-5">
                            <input type="checkbox" name="rememberme" id="rememberme" class="filled-in chk-col-pink">
                            <label for="rememberme">记住密码</label>
                        </div>
                        <div class="col-xs-4">
                            <button class="btn btn-block bg-pink waves-effect" type="submit">登录</button>
                        </div>
                    </div>
<%--                    <div class="row m-t-15 m-b--20">--%>
<%--                        <div class="col-xs-6">--%>
<%--                            <a href="register.html">现在注册</a>--%>
<%--                        </div>--%>
<%--                    </div>--%>
                </form>
            </div>
        </div>
    </div>

    <%@include file="../WEB-INF/page/admin/common/script.jsp"%>
    <script src="${ctx}/asset/plugin/JavaScript-MD5/md5.min.js"></script>
    <script>
        addValidatorRules('.form-validation', function () {
            $('[name=password]').val(md5($('#originPassword').val()))

            // 先弹框
            swal({
                title: '正在登录中...',
                text: ' ',
                icon: 'info',
                button: false,
                closeOnClickOutside: false
            })

            //利用AJAX发送请求给服务器
            $.post('${ctx}/user/login', {
                email:$('[name=email]').val(),
                password:$('[name=password]').val(),
                captcha: $('[name=captcha]').val()
            },function (data) {
                if (data.success) {
                    location.href = '${ctx}/user/admin'
                }else {
                    swal({
                        title: "提示",
                        text: data.msg,
                        icon: 'error',
                        dangerMode: true,
                        buttons: false,
                        timer: 1500
                    })
                }
            },'json')
            return false
        })

        $('#captcha').click(function () {
            $(this).hide().attr('src', '${ctx}/user/captcha?time=' + new Date().getTime()).fadeIn()
        })
    </script>
</body>

</html>