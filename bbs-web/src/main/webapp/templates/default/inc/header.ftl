<#include "/default/utils/utils.ftl">

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0034)https://cssauw.org/forum/forum.php -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="${base}/assets/vendors/bootstrap/css/bootstrap.min.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>论坛 - 重庆工商大学校园论坛 - Powered by toughChow!</title>

    <meta name="keywords" content="论坛">
    <meta name="description" content="论坛 ,重庆工商大学校园论坛">

    <base href=".">
    <link rel="stylesheet" type="text/css" href="/assets/css/style_2_common.css">
    <link rel="stylesheet" type="text/css" href="/assets/img/bbs/style_2_forum_index.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/style_2_forum_forumdisplay.css">
    <link rel="stylesheet" id="css_extstyle" type="text/css" href="/assets/img/bbs/style.css">
    <#--<script src="/assets/img/bbs/common.js.下载" type="text/javascript"></script>-->
    <meta name="application-name" content="重庆工商大学校园论坛">
    <meta name="msapplication-tooltip" content="重庆工商大学校园论坛"
    ">
    <meta name="msapplication-task"
          content="name=论坛;action-uri=https://cssauw.org/forum/forum.php;icon-uri=https://cssauw.org/forum/static/image/common/bbs.ico">
    <link rel="archives" title="重庆工商大学校园论坛" href="https://localhost:8088/index">
    <script src="/assets/img/bbs/forum.js.下载" type="text/javascript"></script>
    <!--[if IE 6]>
    <script src="template/eis_free/eis/js/png.js" type="text/javascript"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('.eis_tpipe,.hdc h2 img,');
    </script>
    <![endif]-->
    <style type="text/css">
        #eis_toptb .y .showmenu {
            padding: 0 15px 0 5px;
        }

    </style>
    <#--<script type="text/javascript" id="ZXljeZWYDaYYKIIKJUanPNWXFOOtRFQJ" charset="UTF-8"-->
            <#--src="/assets/img/bbs/common_extra.js.下载"></script>-->

    <#--<link rel="stylesheet" href="${base}/assets/vendors/bootstrap/css/bootstrap-combined.min.css">-->
    <link rel="stylesheet" href="${base}/assets/css/index.css">
    <script src="${base}/assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/assets/vendors/bootstrap/js/bootstrap.min.js"></script>

    <!-- own css-->
    <link rel="stylesheet" href="${base}/assets/css/user/head.css"/>
    <!-- own js -->
    <script type="text/javascript" src="${base}/assets/js/user/head.js"></script>
</head>


<body id="nv_forum" class="pg_index" onkeydown="if(event.keyCode==27) return false;">
<div id="append_parent"></div>
<div id="ajaxwaitid"></div>
<div class="eis_body_1">
    <div class="eis_body_2">
        <div class="eis_body_3">
            <div id="eis_toptb" class="cl">
                <div class="eis_wp cl">
                    <div class="z">
                    </div>
                    <div class="y">
                    </div>
                <#if profile??>
                    <li class="dropdown">
                        <a href="#" class="ava dropdown-toggle" data-toggle="dropdown">
                            <img class="img-circle" src="${base}${profile.avatar}">
                        </a>
                        <ul class="dropdown-menu" role="menu" style="display: none;">
                            <li>
                                <a href="${base}/account/profile" class="ava">
                                    <@showAva profile.avatar "img-circle" />
                                    <span>${profile.name}</span>
                                </a>
                            </li>
                            <#--<li class="divider"></li>-->

                            <@shiro.hasPermission name="admin">
                                <li><a href="${base}/admin">后台管理</a></li>
                            </@shiro.hasPermission>

                            <li><a href="${base}/logout">退出</a></li>
                        </ul>
                    </li>
                <#else>
                    <a href="${base}/login" title="登录">登录</a>
                    <a href="${base}/reg" title="注册">注册</a>
                    <a href="${base}/forgot/apply" title="找回密码">找回密码</a>
                </#if>
                    <#--<ul>-->
                        <#--<li class="dropdown">-->

                        <#--<a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown"-->
                       <#--aria-expanded="false">-->
                        <#--<!-- 消息提示功能 &ndash;&gt;-->
                        <#--&lt;#&ndash;<img src="${base}${profile.avatar}" alt=""&ndash;&gt;-->
                             <#--&lt;#&ndash;class="popover-show"&ndash;&gt;-->
                             <#--&lt;#&ndash;title="消息中心" data-container="body"&ndash;&gt;-->
                             <#--&lt;#&ndash;data-toggle="popover" data-placement="bottom"&ndash;&gt;-->
                             <#--&lt;#&ndash;data-html="true"&ndash;&gt;-->
                             <#--&lt;#&ndash;data-content="您有新消息,点击<a href='/admin/message/inbox'>查看</a>"/>&ndash;&gt;-->
                        <#--<img src="${base}${profile.avatar}"    style="width: 30px;height: 30px;">-->
                    <#--${profile.username}-->
                        <#--<span class=" fa fa-angle-down"></span>-->
                    <#--</a>-->
                    <#--<ul class="dropdown-menu dropdown-usermenu pull-right">-->
                    <#--&lt;#&ndash;<li><a href="${base}/account/profile">我的</a></li>&ndash;&gt;-->
                        <#--<li class="my_profile"><a href="javascript:void(0)">我的</a></li>-->
                        <#--<li><a href="${base}/logout"><i class="fa fa-sign-out pull-right"></i>登出</a></li>-->
                    <#--</ul>-->
                        <#--</li>-->
                    <#--</ul>-->
                    </div>
                </div>
            </div>

            <div id="hd">
                <div class="eis_wp988">
                    <div class="eis_wp">
                        <div class="hdc cl">
                            <h2><a href="https://localhost:8088/" title="重庆工商大学校园论坛"><img
                                    src="/assets/img/bbs/ctbulogo.gif" alt="重庆工商大学校园论坛" border="0"></a></h2>
                        </div>
                    </div>
                    <div id="eis_nv">
                        <div class="eis_nv">
                            <ul class="cl">
                                <!-- 搜索框 -->
                                <form method="post" autocomplete="off" action="${base}/search" target="_blank">
                                    <input type="hidden" name="mod" id="scbar_mod" value="forum">
                                    <input type="hidden" name="formhash" value="a3f7337b">
                                    <input type="hidden" name="srchtype" value="title">
                                    <input type="hidden" name="srhfid" value="">
                                    <input type="hidden" name="srhlocality" value="forum::index">
                                    <input type="text" name="srchtxt" id="eskey" autocomplete="off">
                                    <button type="submit" name="searchsubmit" id="esbtn" sc="1" value="true"></button>
                                </form>
                                <li class="a" id="mn_forum"><a href="https://localhost:8088/" hidefocus="true"
                                                               title="BBS">论坛<span>BBS</span></a></li>
                                <li id="mn_N05dc"><a href="https://localhost:8088/" hidefocus="true">CSSA主页</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="p_pop h_pop" id="mn_userapp_menu" style="display: none"></div>
                </div>
            </div>
