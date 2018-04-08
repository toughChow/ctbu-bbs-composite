<#include "/default/utils/layout_login.ftl"/>

<@layout "登录">

<div class="login">
    <!--
    <div class="vegas-overlay" style="opacity: 0.2; margin: 0px; padding: 0px; position: fixed; left: 0px; top: 0px; width: 100%; height: 100%; background-image: url(${base}/assets/images/overlay.png); z-index: -1;"></div>
    -->

    <a href="${base}/index">
        <img src="${base}/assets/img/logo/m90.png" height="72" width="72">
    </a>
    <h1>${site_welcomes}</h1>
    <hr>
    <form id="qForm" action="login" method="post">
        <div id="message">
            <#if message??>
                <div class="alert alert-danger">${message}</div>
            </#if>
        </div>
        <label for="id_email">登录名:</label>
        <div id="id_email">
            <#if username??>
                <input name="username" value="${username}" class="form-control border inputBox" placeholder="用户名" type="text" data-required>
            <#else>
                <input name="username" class="form-control border inputBox" placeholder="用户名" type="text" data-required />
            </#if>
        </div>
        <label for="id_password">密码:</label>
        <div id="id_password">
            <input name="password" class="form-control border inputBox" placeholder="密码" type="password" data-required/>
        </div>


        <div class="form-group validation">
        </div>
 <#--<#if ((imageCount>=2)&&ipDiffer)>-->
        <div id="gt2">
        <#if (imageCount>=2)>
            <!-- gt2 -->
            <div>
                <div>
                    <label>完成验证：</label>
                    <div id="captcha2">
                        <p id="wait2">正在加载验证码......</p>
                    </div>
                </div>
                <br>
                <p id="notice2" class="alert-danger" style="display: none">请先完成验证</p>
                <!-- gt2 end~ -->
            </div>
        </#if>
        </div>
        <input type="hidden" id="countErr" value="${imageCount}"/>




        <div>
            <label>
                <input type="checkbox" name="rememberMe" value="1"> 记住登录？
            </label>
        </div>

        <div style="margin-top: 15px;">
            <div class="forgot">
                <a href="${base}/reg">
                    注册
                </a>
                <a href="${base}/forgot/apply">
                    忘记密码
                </a>
            </div>
            <input type="submit" id="submit2" class="pull-right btn btn-success border" value="登录">
        </div>
    </form>
    <#--<div class="with-line">使用第三方帐号登录</div>-->
    <#--<div class="buttons">-->
        <#--<a href="${base}/oauth/callback/call_weibo" title="微博帐号登录" rel="nofollow" class="weibo"></a>-->
        <#--<a href="${base}/oauth/callback/call_qq" title="QQ帐号登录" rel="nofollow" class="qzone"></a>-->
        <#--<a href="${base}/oauth/callback/call_weibo" title="微信帐号登录" rel="nofollow" class="wechat"></a>-->
        <#--&lt;#&ndash;<a href="${base}/oauth/callback/call_douban" title="豆瓣帐号登录" rel="nofollow" class="douban"></a>&ndash;&gt;-->
    <#--</div>-->

</div>

<script type="text/javascript">

    // global variable
    var countErr = $('#countErr').val();
    var user = $('.inputBox')[0];
    var phoneFromServer;
    var judgeSubmit = false;
    $(function () {
        validateUserIp();
    })

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

    var handler2 = function (captchaObj) {
        $(".submit-vali").click(function (e) {
            var result = captchaObj.getValidate();
            if (!result) {
                $('#message').hide();
                $("#notice2").show();
                setTimeout(function () {
                    $("#notice2").hide();
                }, 2000);
            } else {
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
                            // alert('登录成功');
                            $('#qForm').submit();
                        } else if (data.status === 'fail') {
                            // alert(1);
                            // alert('登录失败');
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
            if(tmp != null){
                tmp.style.display = "none";
            }else{
                // alert("gg")
            }

        });
    }; // handler~
    var ajaxVali = function () {
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
    }

    if(countErr >= 2){
        // $('#submit2').className('submit-vali');
        var subDom = document.getElementById('submit2')
        subDom.className = subDom.className.replace(' ',' ')
        subDom.className += ' submit-vali';

        ajaxVali();

    }


    // 根据用户名获取ip和phone
    $(user).on('blur',function (e) {
        validateUserIp();
    });

    function validateUserIp(){

        var valDom = $('.validation');
        // valDom.html()
        var valHtml = '';
        var username = $(user).val();
        if(username != '' && username != null){
            $.post(
                    "/lg/ipExist",
                    {"username":username},
                    function (data) {
                        if(data == "msg2") {
                            valDom.html('');
                            phoneFromServer = null;
                            // console.log("999")
                            return;
                        } else if(data == "msg1"){
                            valDom.html('');
                            phoneFromServer = null;
                          return;
                        } else {
                            phoneFromServer = data; // set phone num
                            valHtml += `
                        <label control-label">验证码:</label>
                        <div>
                            <input type="number" maxlength="6" autocomplete="off" class="form-control border" name="messageCode" id="messageCode" placeholder="短信验证码"
                                   data-required>
                        </div>
                        <div>
                            <input type="button" class="form-control border" id="verifyCode" value="获取验证码"
                                   data-conditional="verifyCode" data-description="verifyCode" data-describedby="message"/>
                        </div>
                        `;
                            valDom.html(valHtml)
                            // alert(valDom);//添加短信验证
                            verifyCode();
                        }
                    }
            )
        } else {
            valDom.html('');
            phoneFromServer = null;
        }
    }

    function verifyCode() {
        $('#verifyCode').on('click', function () {
            // var mobile = $('#mobile').val();
            // var reg = /^1[0-9]{10}$/;
            // var isTrue = reg.test(mobile);
            if (phoneFromServer != null) {
                $.post(
                    "/ipDifferValiCode",
                    {"phone": phoneFromServer},
                    function (data) {
                        if(data == 1){
                            showMsg("验证码已发送,请注意查收!",1);
                        }else {
                            showMsg("网络异常,请稍后重试",0);
                        }
                    }
                )
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
        });// get verifycode
    }

    //获取验证码图片
    <#--function changeCaptcha(){-->
             <#--$("#changeCaptcha").attr("src","${base}/imagecode");-->
         <#--}-->
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
                mobile: function () {
                    return /^1[0-9]{10}$/.test($(this).val());
                },
            },
            description: {
                message: {
                    required: '<div class="alert alert-danger">请先输入用户名/密码再进行登录操作</div>'
                },
                mobile: {
                    conditional: '<div class="alert alert-danger">手机格式不合法</div>'
                },
            }
        });
        var $val = $('.validation').html();
        if ($val == undefined || $val == null || $val == '') {
            $('#submit2').on('click',function (e) {
                    e.preventDefault();
                    var msgCode = $('#messageCode').val();
                    if(msgCode != ''){
                        $('#qForm').submit();
                    }else{
                        var htmlVal1 = "<div class=\"alert alert-danger\">" +
                                "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span></button>" +
                                "请输入验证码" +
                                "</div>";
                        $('#message').html(htmlVal1);
                    }
            })
        }

    })
</script>

</@layout>

