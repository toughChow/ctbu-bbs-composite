<#include "/default/utils/layout_login.ftl"/>

<@layout "密码找回">

<div class="login">
    <a href="${base}/index">
        <img src="${base}/assets/img/logo/m90.png" height="72" width="72">
    </a>
    <div class="fgt_dv">
        <h1>账号填写</h1>
        <hr>
        <form class="form-horizontal" action="" method="post">
            <div id="message">
                <#include "/default/inc/action_message.ftl"/>
            </div>
            <label for="id_username">登录名:</label>
            <div id="id_username">
                <input name="username" id="username" value="${username}" class="form-control border inputBox"
                       placeholder="用户名" type="text" data-required>
            </div>

            <div>
                <div>
                    <label>验证码：</label>
                    <div id="captcha2">
                        <p id="wait2">正在加载验证码......</p>
                    </div>
                </div>
                <br>
                <p id="notice2" class="alert-danger" style="display: none">请先完成验证</p>
                <!-- gt2 end~ -->
            </div>

            <input type="button" id="submit" class="submit-vali btn btn-success border" value="确 定">
        </form>
    </div>

    <div class="fgt_dv2" style="display: none">
        <h1>身份验证</h1>
        <hr>
        <form class="form-horizontal" action="" method="post">
            <div id="message">
                <#include "/default/inc/action_message.ftl"/>
            </div>
            <label for="id_phone">请输入您绑定的手机号:</label>
            <p id="phoneFromServ">176**********</p>
            <div id="id_phone">
                <input name="phone" id="phone" class="form-control border inputBox" placeholder="完整的手机号" type="text"
                       data-required>
            </div>

            <div class="verify-code-div">
                <label control-label">验证码:</label>
                <div>
                    <input type="number" maxlength="6" autocomplete="off" class="form-control border" name="messageCode"
                           id="messageCode" placeholder="短信验证码"
                           data-required>
                </div>
                <div>
                    <input type="button" class="form-control border" id="verifyCode" value="获取验证码"
                           data-conditional="verifyCode" data-description="verifyCode" data-describedby="message"/>
                </div>
            </div>


            <input type="button" id="submitsam" class="btn btn-success border" value="确 定">
        </form>
    </div>

    <div class="fgt_dv3" style="display: none">
        <h1>设置新密码</h1>
        <hr>
        <#--<form class="form-horizontal" action="${base}/forgot/reset" method="post">-->
            <#--<div id="message">-->
                <#--<#include "/default/inc/action_message.ftl"/>-->
            <#--</div>-->
            <#--<input type="hidden" name="userIdStr">-->
            <#--<label for="id_password">新密码:</label>-->
            <#--<div id="id_password">-->
                <#--<input name="password" class="form-control border inputBox" placeholder="请输入新密码" type="password"-->
                       <#--data-required>-->
            <#--</div>-->
            <#--<label for="id_password">确认密码:</label>-->
            <#--<div id="id_password">-->
                <#--<input name="passwordRepit" class="form-control border inputBox" placeholder="请输入确认密码" type="password"-->
                       <#--data-required>-->
            <#--</div>-->


            <#--<input type="submit" id="submitForReset" class="btn btn-success border" value="确 定">-->
        <#--</form>-->
        <form action="${base}/forgot/reset" id="qForm" method="post">
            <input type="hidden" name="userId" value="${userId}"/>
        <#--<input type="hidden" name="token" value="${token}"/>-->
            <div id="message">
            <#include "/default/inc/action_message.ftl"/>
            </div>
            <label for="id_password">密码:</label>
            <div id="id_password">

                <input id="password" class="form-control border" maxlength="18" name="password" placeholder="密码"
                       type="password" data-required>
            </div>
            <label for="id_password2">确认密码:</label>
            <div id="id_password2">
                <input maxlength="18" class="form-control border" name="password2" placeholder="请再输入一次密码" type="password"
                       data-required data-conditional="confirm" data-describedby="message" data-description="confirm">
            </div>
            <input type="button" id="submitForReset" class="btn btn-success border" value="提 交">
        </form>
    </div>
</div>
<script>

    var fgtDom = document.getElementsByClassName('fgt_dv')[0];
    var fgtDom2 = document.getElementsByClassName('fgt_dv2')[0];
    var phone;

    var flags = false;

    function showMsg(msg, type) { // 0 danger 1 success
        var htmlVal = '';
        if (type == 1) {
            htmlVal = "<div class=\"alert alert-success\">" +
                    "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span></button>" +
                    msg +
                    "</div>";
        } else if (type == 0) {
            htmlVal = "<div class=\"alert alert-danger\">" +
                    "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span></button>" +
                    msg +
                    "</div>"
        }
        $('#message').html(htmlVal);
    }

    // :::~
    var handler2 = function (captchaObj) {
        $(".submit-vali").click(function (e) {
            var _this = this;
            var result = captchaObj.getValidate();
            if (!result) {
                $('#message').hide();
                $("#notice2").show();
                setTimeout(function () {
                    $("#notice2").hide();
                }, 2000);
            } else {
                flags = true;
                quba(_this);
                $.ajax({
                    url: '/gt/ajax-validate2',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        // username: $('#username2').val(),
                        // password: $('#password2').val(),
                        geetest_challenge: result.geetest_challenge,
                        geetest_validate: result.geetest_validate,
                        geetest_seccode: result.geetest_seccode
                    },
                    success: function (data) {
                        if (data.status === 'success') {
                        } else if (data.status === 'fail') {
                        }
                    }
                })
            }
            e.preventDefault();
        });
        // 将验证码加到id为captcha的元素里，同时会有三个input的值用于表单提交
        captchaObj.appendTo("#captcha2");
        captchaObj.onReady(function () {
            var tmp = document.getElementById("wait2");
            if (tmp != null) {
                tmp.style.display = "none";
            } else {
                // alert("gg")
            }

        });
    }; // handler~

    function quba(a) {
        var _this = a;
        var flag = false;
        var username = $('#username').val();
        if (username !== null && username !== '') {
            $.ajax({
                url: 'checkUserGetPhone',
                data: {'username': username},
                success: function (data) {
                    if (data == null || data == '') {
                        showMsg("用户不存在", 0);
                        flag = false;
                    } else {
                        showMsg("用户名存在", 1);
                        phone = data.mobile;
                        $('input[name=userId]').val(data.id);
                        $('#phoneFromServ').html(phone.substr(0, 3) + '********')
                        falg = true;
                        $(".fgt_dv").remove();
                        $(".fgt_dv2").show();
                    }
                    // showMsg("用户名可用",1)
                },
                error:function () {
                    showMsg("网络异常,请稍后重试", 0);
                }
            })
        } else {
            if (flags) {
                showMsg("请填写信息",0);
            } else {
                showMsg("用户名不能为空",0);
            }
        }
        // return false;
        return flag;

    }

    $.ajax({
        url: "/gt/register2?t=" + (new Date()).getTime(), // 加随机数防止缓存
        type: "get",
        dataType: "json",
        success: function (data) {
            initGeetest({
                gt: data.gt,
                challenge: data.challenge,
                new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
                offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
                product: "popup", // 产品形式，包括：float，popup
                width: "100%"
            }, handler2);
        }
    });//:~

    // :::div2
    var flagForDiv2 = false;
    $('#verifyCode').on('click', function () {
    var phoneInput = $('#phone').val();
        if (phone == phoneInput) {
            $.post(
                    "/ipDifferValiCode",
                    {"phone": phone},
                    function (data) {
                        if (data == 1) {
                            showMsg("验证码已发送,请注意查收!", 1);

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
                        } else {
                            showMsg("网络异常,请稍后重试", 0);
                        }
                    }
            )
            flagForDiv2 = true;
        } else {
            showMsg("请输入正确手机号码", 0);
        }
    })

    $("#submitsam").on("click", function () {
        var messageCode = $('#messageCode').val();
        $.post(
                'valiVerifyCode',
                {"messageCode": messageCode,"phone":phone},
                function (data) {
                    if (data == 1) {
                        $(".fgt_dv2").remove();
                        $(".fgt_dv3").show();
                    } else if (data == 0) {
                        showMsg("验证码有误,请重新输入!", 0);
                    } else if (data == -1) {
                        showMsg("验证码不能为空!", 0);
                    }
                }
        )
    })

    // :::div3~
    $('#submitForReset').on('click', function () {
        var password = $('input[name=password]').val();
        var password2 = $('input[name=password2]').val();
        if(password != null || password != '' && password2 != null || password2 != ''){
            console.log(password+","+password2);
            if(password == password2){
                $('#qForm').submit();
            }else{
                showMsg("密码不一致!",0);
            }
        }else{
            alert()
        }
    })

</script>

</@layout>