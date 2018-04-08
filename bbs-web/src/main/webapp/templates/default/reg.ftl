<#include "/default/utils/layout_login.ftl"/>

<@layout "注册">
<div class="login reg">
    <a href="${base}/index">
        <img src="${base}/assets/img/logo/m90.png" height="72" width="72">
    </a>
    <h1>${site_welcomes}</h1>
    <a href="${base}/login" class="signup-link gapps"><span>已有账号, 登录</span></a>
    <hr>
    <form class="form-horizontal" action="reg" method="post">
        <div id="message">
            <#include "/default/inc/action_message.ftl"/>
        </div>
        <div class="form-group">
            <label class="col-lg-3 col-md-3 col-xs-3 control-label" for="id_username">用户名:</label>
            <div class="col-lg-9 col-md-9 col-xs-9" id="id_username">
                <input maxlength="18" class="form-control border" id="username" name="username" value="${post.username}" placeholder="用户名"
                       type="text" data-required data-conditional="username" data-description="username"
                       data-describedby="message">
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 col-md-3 col-xs-3 control-label" for="id_phone">手机号:</label>
            <div class="col-lg-9 col-md-9 col-xs-9" id="id_phone">
                <input type="text" class="form-control border" name="mobile" id="mobile" value="${post.mobile}" placeholder="手机号"
                       data-required data-conditional="mobile" data-description="mobile" data-describedby="message"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 col-md-3 col-xs-3 control-label">验证码:</label>
            <label class="col-lg-5 col-md-5 col-xs-5">
                <input type="text" maxlength="6" autocomplete="off" class="form-control border" name="messageCode" id="messageCode" placeholder="短信验证码"
                       data-required>
            </label>
            <div class="col-lg-4 col-md-4 col-xs-4">
                <input type="button" class="form-control border" id="verifyCode" value="获取验证码"
                       data-conditional="verifyCode" data-description="verifyCode" data-describedby="message"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 col-md-3 col-xs-3 control-label" for="id_password">密码:</label>
            <div class="col-lg-9 col-md-9 col-xs-9" id="id_password">
                <input id="password" class="form-control border" maxlength="18" name="password" placeholder="密码"
                       type="password" data-required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-3 col-md-3 col-xs-3 control-label" for="id_password2">确认密码:</label>
            <div class="col-lg-9 col-md-9 col-xs-9" id="id_password2">
                <input maxlength="18" class="form-control border" name="password2" placeholder="请再输入一次密码" type="password"
                       data-required data-conditional="confirm" data-describedby="message" data-description="confirm">
            </div>
        </div>
        <input type="submit" id="submit" class="btn btn-success border" value="注 册">
    </form>
</div>

<script type="text/javascript">
    $(function () {
        $('form').validate({
            onKeyup: true,
            onChange: true,
            eachValidField: function () {
                $(this).closest('div').removeClass('has-error').addClass('has-success');
            },
            eachInvalidField: function () {
                $(this).closest('div').removeClass('has-success').addClass('has-error');
            },
            conditional: {
                confirm: function () {
                    return $(this).val() == $('#password').val();
                },
                mobile: function () {
                    return /^1[0-9]{10}$/.test($(this).val());
                },
                username: function () {
                    return /^[a-z][a-z_0-9]{4,18}$/i.test($(this).val());
                },
            },
            description: {
                confirm: {
                    conditional: '<div class="alert alert-danger">两次输入的密码不一致</div>'
                },
                mobile: {
                    conditional: '<div class="alert alert-danger">手机格式不合法</div>'
                },
                username: {
                    conditional: '<div class="alert alert-danger">只能是字母/字母+数字的组合,不少于5位</div>'
                },
            }
        });
        $('#verifyCode').on('click', function (e) {

            var username = $('#username').val();

            var flag = /^[a-z][a-z_0-9]{4,18}$/i.test(username);
            if(flag){
                $.post(
                        'valiUser',
                        {'username':username},
                        function (data) {
                            if(data == 1){
                                showMsg("用户名已存在",0);
                                e.preventDefault();
                                return false;
                            }
                            // showMsg("用户名可用",1)
                        }
                )
            }else {
                e.preventDefault();
                showMsg("请完善信息",0)
            }

            // :::~

            var mobile = $('#mobile').val();
            var reg = /^1[0-9]{10}$/;
            var isTrue = reg.test(mobile);

            if (mobile != null && isTrue) {
                $.post(
                    "regCode",
                    {"phone": mobile},
                    function (data) {
                        // alert(data);
                        if(data == '0'){
                            var htmlVal = "<div class=\"alert alert-danger\">" +
                                    "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span></button>" +
                                    "该手机号已被注册!" +
                                    "</div>"
                            $('#message').html(htmlVal);
                        }else if(data == '1'){
                            // var htmlVal1 = "<div class=\"alert alert-success\">" +
                            //         "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span></button>" +
                            //         "验证码已发送,请注意查收!" +
                            //         "</div>";
                            // $('#message').html(htmlVal1);
                            showMsg("验证码已发送,请注意查收!",1);

                            var i = 60;
                            var times = setInterval(function () {
                                if (i > 0){
                                    i--;
                                    $('#verifyCode').val(i+'s再次获取');
                                    $('#verifyCode').attr('disabled',true)
                                } else {
                                    $('#verifyCode').val('获取验证码');
                                    $('#verifyCode').attr('disabled',false);
                                    clearInterval(times);
                                }
                            },1000);
                        }
                    }
                )

            }
        });// get verifycode

        function showMsg(msg,type) { // 0 danger 1 success
            var htmlVal = '';
            if(type == 1){
                htmlVal = "<div class=\"alert alert-success\">" +
                        "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span></button>" +
                        msg +
                        "</div>";
            }else if(type == 0){
                htmlVal = "<div class=\"alert alert-danger\">" +
                        "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span></button>" +
                        msg +
                        "</div>"
            }
            $('#message').html(htmlVal);
        }

    })
</script>

</@layout>