<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>简历管理-网站信息</title>
    <%@include file="common/style.jsp"%>
</head>

<body class="theme-blue">
    <%@include file="common/nav.jsp"%>>

    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>网站信息</h2>
                        </div>
                        <div class="body">
                            <form class="form-validation" method="post" action="${ctx}/website/save">
                                <input name="id" hidden value="${website.id}">
                                <div class="row">
                                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3 form-control-label">
                                        <label for="footer">底部</label>
                                    </div>
                                    <div class="col-lg-10 col-md-10 col-sm-9 col-xs-9">
                                        <div class="form-group">
                                            <div class="form-line">
                                                <textarea name="footer" maxlength="1000"
                                                          id="footer" cols="30" rows="5"
                                                          class="form-control no-resize"
                                                          placeholder="底部">${website.footer}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-lg-offset-2 col-md-offset-2 col-sm-offset-3 col-xs-offset-3">
                                        <button class="btn btn-primary waves-effect m-l-15" type="submit">保存</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <%@include file="common/script.jsp"%>
    <script>
        $('.menu .list .website').addClass('active')

        //get
        // const xhr = new XMLHttpRequest();
        // xhr.open('GET', 'http://localhost:8080/xr', true)
        // xhr.responseType = 'json'
        // xhr.send()
        // xhr.onload = function () {
        //     if (xhr.status !== 200) return
        //     console.log(xhr.response)
        // }

        // const xhr = new  XMLHttpRequest()
        // xhr.open('POST', 'http://localhost:8080/xr', true)
        // xhr.responseType = 'json'
        // xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoding; charset=UTF-8')
        // xhr.send('name=cheche')
        // xhr.onload = function () {
        //     if (xhr.status !== 200) return
        //     console.log(xhr.response)
        // }

        $.ajax({
            method: 'POST',
            url:'http://loaclhost:2020/xh',
            dataType:'json',
            data:{
                age:210,
                name:'cheche'
            },
            success:function (data) {
                console.log(data)
            }
        })

        $.get('http://loaclhost:2021/xr', {name: 'minic'},function (data) {

        }, 'json')

        $.getJSON('http://localhost:4505/xr', {age:20},function (data) {
            console.log(data)
        })



    </script>
</body>

</html>
