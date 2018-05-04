<#include "/admin/utils/ui.ftl"/>
<@layout>
<style>
    .nav-tabs li a:hover {
        text-decoration: none;
        background-color: #eee;
    }

    #myTabContent {
        margin-top: 20px;
    }

    .optionstyle {
        background-color: #00a2d4;
        color: #fff;
    }
</style>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>消息发送</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content" style="width: 500px;">
                <ul id="myTab" class="nav nav-tabs">
                    <li class="active"><a class="messageTab" href="#messageTab" data-toggle="tab">
                        发消息</a></li>
                    <@shiro.hasRole name="管理员">
                    <li><a class="messageTab" href="#annocementTab" data-toggle="tab">发公告</a></li>
                    </@shiro.hasRole>
                    <#--<li><a class="messageTab" href="#emailTab" data-toggle="tab">发邮件</a></li>-->
                </ul>

                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="messageTab">
                        <div id="P2PPanel">
                            <form class="bs-example bs-example-form" role="form" id="p2p-form">

                                <div class="input-group selectMethodDiv" style="display: show">
                                    <label for="p2p-username">收信人</label>
                                    <div style="width: 100%;position: relative;float: left">
                                        <input type="text" list="receivers_list" class="p2p-usr-cl form-control" id="p2p-username" placeholder="请输入收信人"
                                               autocomplete="on"/>
                                        <datalist id="receivers_list"></datalist>
                                        <#--<select id="username-panel" style="visibility: hidden"></select>-->
                                        <div id="username-panel"
                                             style="padding-top: 30px;position: absolute;background-color: #fff;border: 1px solid #e0e0e0"></div>
                                        <@shiro.hasRole name="管理员">
                                        <a id="sendByGroup" class="sendMethodOpt"
                                           style="position: absolute;top: 0;right: -40px;display: inline-block;line-height: 34px;cursor: pointer">群发</a>
                                        </@shiro.hasRole>
                                    </div>
                                </div>

                                <div class="input-group selectMethodDiv" style="display: none">
                                    <label for="p2p-username">收信群组</label>
                                    <div style="width: 100%;position: relative;float: left">
                                        <select class="form-control" id="selectGroupBox">
                                            <option value="-1">请选择群组</option>
                                        </select>
                                        <div id="username-panel"
                                             style="padding-top: 30px;position: absolute;background-color: #fff;border: 1px solid #e0e0e0"></div>
                                        <a id="sendByDan" class="sendMethodOpt"
                                           style="position: absolute;top: 0;right: -40px;display: inline-block;line-height: 34px;cursor: pointer">单发</a>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="p2p-content">内容</label>
                                    <textarea rows="8" placeholder="请输入内容" class="form-control errno"
                                              id="p2p-content"></textarea>
                                </div>
                                <input type="button" id="p2p-form-submit" class="btn btn-primary" value="发送"/>
                                <b id="p2p-error-message"></b>
                            </form>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="annocementTab">
                        <div id="P2MPanel">
                            <form class="bs-example bs-example-form" role="form" id="p2m-form">
                                <div class="form-group">
                                    <label for="p2m-content">公告</label>
                                    <textarea rows="8" placeholder="请输入公告内容" class="form-control errno"
                                              id="p2m-content"></textarea>
                                </div>
                                <button id="p2m-form-submit" class="btn btn-primary" type="button">发送</button>
                                <b id="p2m-error-message"></b>
                            </form>
                        </div>
                    </div>

                    <div class="tab-pane fade" id="emailTab">
                        <div id="EmailPanel">
                            <form method="post" action="/admin/message/email" class="bs-example bs-example-form"
                                  role="form" id="email-form" enctype="multipart/form-data">
                                <div class="input-group">
                                    <label for="email-username">收信人</label>
                                    <input type="email" name="receiver" id="email-username" class="form-control errno"
                                           placeholder="请输入收信人">
                                </div>
                                <div class="input-group">
                                    <label for="email-username">主题</label>
                                    <input type="text" name="subject" id="email-subject" class="form-control errno"
                                           placeholder="请输入主题">
                                </div>
                                <div class="form-group">
                                    <label for="p2p-content">内容</label>
                                <#--<p contenteditable="true">内容包含图片</p>-->
                                    <textarea rows="8" name="content" placeholder="请输入内容" class="form-control"
                                              id="email-content"></textarea>
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="email-file"></label>
                                    <input type="file" name="emailFile" id="emailFile" multiple="multiple">
                                </div>
                                <!-- 隐藏url地址 -->
                                <input type="hidden" name="url" id="emailUrl"/>
                                <input type="button" id="email-form-submit" class="btn btn-primary" value="发送"/>
                                <b id="email-error-message"></b>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    $(function () {
        var flagAj = 1;
        var flgs = true;
        // bootstrap标签体
        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            // Get the name of active tab
            var activeTab = $(e.target).text();
            // Get the name of previous tab
            var previousTab = $(e.relatedTarget).text();
            $(".active-tab span").html(activeTab);
            $(".previous-tab span").html(previousTab);
        });

        var userFromServer = new Array();
        //    @down 整合user后异步查询user
        $('#p2p-username').on('input', function () {
            $('#p2p-error-message').html("");
            var username = $('#p2p-username').val();
            if (username != '') {
                $.post(
                    "/admin/users/search",
                    {"username": username},
                    function (users) {
                        var htmls = '';
                        if (users != '' && users != null) {
                            // $('#username-panel').css("visibility","visible");
                            for (var i = 0; i < users.length; i++) {
                                if (i === 0) {
                                    htmls += "<option class='option-username optionstyle' data-index='" + i + "' id='" + users[i].id + "' value='" + users[i].username + "'>" + users[i].username + "</option>";
                                } else {
                                    htmls += "<option class='option-username' data-index='" + i + "' id='" + users[i].id + "' value='" + users[i].username + "'>" + users[i].username + "</option>";
                                }
                                // $('#username-panel').html(htmls);
                                $('#receivers_list').html(htmls);
                            }
                            // onblur deal
                            $('#p2p-username').on('input',function () {
                                if($(this).val() == '' && $(this).val() == null){
                                    $('#p2p-error-message').html("");
                                }
                                    var tempVal = $(this).val();
                                console.log(tempVal+"666")
                                    $.each(users,function (k,v) {
                                        console.log(v.username);
                                        if(v.username === tempVal){
                                            flagAj = 1;
                                            $('#p2p-error-message').html("");
                                            return false;
                                        }else{
                                            flagAj = 0;
                                            $('#p2p-error-message').html("该用户不存在").css("color", "red");
                                        }
                                    })
                            });// :onblur deal~

                        } else {// 为空
                            $('#receivers_list').empty();
                            // $('#username-panel').css("visibility","hidden");
                        }
                    }, "");
                //endpoint
            } else {
                $('#receivers_list').empty();
            }
        });

        // : form表单验证
        $('#p2p-form-submit').on('click', function () {
            var flag = false;
            var p2pContent = $("#p2p-content").val();
            var groupSelectionsLen = $('.p2p-grp-cl').val();
            if(flgs){
                var username = $('.p2p-usr-cl').val();
                // var groupSelectionsLen = $('.p2p-grp-cl > option').length;
                if (username != '') {
                    flag = true;
                } else {
                    $('#p2p-error-message').html("收信人不能为空").css("color", "red");
                    return;
                }
                if (groupSelectionsLen != -1) {
                    flag = true;
                } else {
                    $('#p2p-error-message').html("收信群组不能为空").css("color", "red");
                    return;
                }
                if (p2pContent != '') {
                    flag = true;
                } else {
                    $('#p2p-error-message').html("信息内容不能为空").css("color", "red");
                    return;
                }
                if(flagAj == 1){
                    flag = true;
                    $('#p2p-error-message').html("");
                }else{
                    $('#p2p-error-message').html("该用户不存在").css("color", "red");
                    return;
                }
                if (username != '' && p2pContent != '') {
                    $.post(
                            "/admin/users/searchSingle",
                            {"username": username},
                            function (data) {
                                if (data == '0') {
                                    flag = true;
                                }
                            }, "");
                    setTimeout(function () {
                        if (flag == true) {
                            submitMsg();
                        } else {
                            $('#p2p-error-message').html("用户不存在").css("color", "red");
                        }
                    }, 50);
                }
            }else{
                $.post(
                        "/admin/users/searchSingle",
                        {"username": username},
                        function (data) {
                            if (data == '0') {
                                flag = true;
                            }
                        }, "");
                setTimeout(function () {

                        $("#p2p-form").submit();
                }, 50);
            }
        })  //:~

        //: p2m表单验证
        $('#p2m-form-submit').on('click', function () {
            var p2pContent = $("#p2m-content").val();
            var flag = false;
            if (p2pContent != '') {
                flag = true;
            } else {
                $('#p2m-error-message').html("公告内容不能为空").css("color", "red");
            }
            if (flag == true) {
                $("#p2m-form").submit();
            } else {
                $('#p2m-error-message').html("请完善好信息后发送").css("color", "red");
            }
        })  //:~

        //: email表单验证
        $('#email-form-submit').on('click', function () {
            var emailUsername = $('#email-username').val();
            var emailSubject = $('#email-subject').val();
            var emailContent = $('#email-content').val();
            var flag = false;
            if (emailUsername != '') {
                flag = true;
            } else {
                $('#email-error-message').html("收信人不能为空").css("color", "red");
                return;
            }
            if (emailSubject != '') {
                flag = true;
            } else {
                $('#email-error-message').html("主题不能为空").css("color", "red");
                return;
            }
            if (emailContent != '') {
                flag = true;
            } else {
                $('#email-error-message').html("邮件内容不能为空").css("color", "red");
                return;
            }
            var regEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            var isEmail = regEmail.test(emailUsername);
            if (isEmail == true) {
                flag = true;
            } else {
                $('#email-error-message').html("收信邮件格式有误").css("color", "red");
                return;
            }
            if (flag == true) {
                $("#email-form").submit();
            } else {
                $('#email-error-message').html("请完善好信息后发送").css("color", "red");
            }
        })  //:~

        //: emailJs
        $('#emailFile').on('change', function () {
            var formData = new FormData($('#email-form')[0]);
                $.ajax({
                    url: '/admin/message/uploadFile',
                    type: 'POST',
                    cache: false,
                    data: formData,
                    processData: false,
                    contentType: false}).done(function(res) {
                    var url = res;
                    $('#emailUrl').val(url);
                }).fail(function(res) {
                    $('#email-error-message').html("文件超过指定大小").css("color", "red");
                });

        })


        // sendByGroup
        $('#sendByGroup').on('click', function () {
            flgs = false;
            $('#p2p-content').val("");
            $('#p2p-username').val("");
            $('#p2p-error-message').html("");
            if($('#selectGroupBox > option').length == 1){
                $.post(
                        "/admin/message/findAllGroups",
                            function(data) {
                            for (var i = 0; i < data.length; i++){
                                $('#selectGroupBox').append("<option value="+data[i].id+">"+data[i].name+"</option>");
                            }
                        });
            }
        });
        $('#sendByDan').on('click', function () {
            $('#p2p-error-message').html("");
            $('#p2p-content').val("");
            $('#p2p-username').val("");
        })
        //:sendByGroup ~

        // toggle
        $(".sendMethodOpt").on('click',function () {
            $('.selectMethodDiv').toggle();
            $('#p2p-username').toggleClass("p2p-usr-cl");
            $('#selectGroupBox').toggleClass("p2p-grp-cl");
        });



        Array.prototype.slice.call(document.getElementsByClassName('messageTab')).forEach(function(e,i){
            e.addEventListener('click',function(){
                $('#p2p-error-message').html("");
                $('#p2p-username').val("");
                $('#email-error-message').html("");
                $('#p2m-error-message').html("");
                $('#email-username').val("");
                $('#email-subject').val("");
                $('#email-content').val("");
                $('#p2m-content').val("");
                $('#p2p-content').val("");
            },false)
        })

        Array.prototype.slice.call(document.getElementsByClassName('errno')).forEach(function(e,i){
            e.addEventListener('input',function(){
                $('#email-error-message').html("");
                $('#p2m-error-message').html("");
                $('#p2p-error-message').html("");
            },false)
        })

        $('#selectGroupBox').on('input',function(){
            $('#p2p-error-message').html("");
        })

    })

</script>
</@layout>