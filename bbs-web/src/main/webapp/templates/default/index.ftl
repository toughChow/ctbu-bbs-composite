<#include "/default/utils/utils.ftl">

<html>
<head>
    <link rel="stylesheet" href="${base}/assets/vendors/bootstrap/css/bootstrap-combined.min.css">
<#--<link rel="stylesheet" href="${base}/assets/vendors/bootstrap/css/bootstrap.min.css">-->
    <link rel="stylesheet" href="${base}/assets/css/index.css">
    <script src="${base}/assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/assets/vendors/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid index_main">
    <div class="row-fluid">
        <div class="span12">
            <div class="navbar navbar-inverse nav-wrapper">
                <div class="navbar-inner">
                    <div class="container-fluid">
                        <a class="btn btn-navbar" data-target=".navbar-responsive-collapse" data-toggle="collapse"></a>
                        <a class="brand" href="#"> <strong>CTBB</strong></a>
                        <div class="nav-collapse collapse navbar-responsive-collapse">
                            <ul class="nav">
                            <#--<li class="active">-->
                            <#--<a href="#">CTBU-BBS</a>-->
                            <#--</li>-->
                            <#--<li>-->
                            <#--<a href="#">链接</a>-->
                            <#--</li>-->
                            <#--<li>-->
                            <#--<a href="#">链接</a>-->
                            <#--</li>-->
                            <#--<li class="dropdown">-->
                            <#--<a class="dropdown-toggle" data-toggle="dropdown" href="#">下拉菜单</a>-->
                            <#--<ul class="dropdown-menu">-->
                            <#--<li>-->
                            <#--<a href="#">下拉导航1</a>-->
                            <#--</li>-->
                            <#--<li>-->
                            <#--<a href="#">下拉导航2</a>-->
                            <#--</li>-->
                            <#--<li>-->
                            <#--<a href="#">其他</a>-->
                            <#--</li>-->
                            <#--<li class="divider">-->
                            <#--</li>-->
                            <#--<li class="nav-header">-->
                            <#--标签-->
                            <#--</li>-->
                            <#--<li>-->
                            <#--<a href="#">链接1</a>-->
                            <#--</li>-->
                            <#--<li>-->
                            <#--<a href="#">链接2</a>-->
                            <#--</li>-->
                            <#--</ul>-->
                            <#--</li>-->
                            </ul>
                            <ul class="nav pull-right">
                                <#if profile??>
                                    <li class="dropdown">
                                        <a href="#" class="ava dropdown-toggle" data-toggle="dropdown">
                                            <img class="" src="${base}${profile.avatar}" style="width:30px">
                                        </a>
                                        <ul class="dropdown-menu" role="menu">
                                            <#--<li>-->
                                                <#--<a href="${base}/account/profile" class="ava">-->
                                                    <#--<@showAva profile.avatar "" />-->
                                                    <#--<span>${profile.name}</span>-->
                                                <#--</a>-->
                                            <#--</li>-->
                                            <#--<li class="divider"></li>-->

                                            <@shiro.hasPermission name="admin">
                                                <li><a href="${base}/admin">后台管理</a></li>
                                            </@shiro.hasPermission>
                                            <@shiro.hasPermission name="user">
                                                <li><a href="${base}/admin">个人账户</a></li>
                                            </@shiro.hasPermission>

                                            <li><a href="${base}/logout">退出</a></li>
                                        </ul>
                                    </li>
                                <#else>
                                    <li><a href="${base}/login" class="signin">登录</a></li>

                                    <li><a href="${base}/reg" class="signup">注册</a></li>
                                </#if>
                            <#--<li class="dropdown">-->
                            <#--<a class="dropdown-toggle" data-toggle="dropdown" href="#">下拉菜单</a>-->
                            <#--<ul class="dropdown-menu">-->
                            <#--<li>-->
                            <#--<a href="#">下拉导航1</a>-->
                            <#--</li>-->
                            <#--<li>-->
                            <#--<a href="#">下拉导航2</a>-->
                            <#--</li>-->
                            <#--<li>-->
                            <#--<a href="#">其他</a>-->
                            <#--</li>-->
                            <#--<li class="divider">-->
                            <#--</li>-->
                            <#--<li>-->
                            <#--<a href="#">链接3</a>-->
                            <#--</li>-->
                            <#--</ul>-->
                            <#--</li>-->
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row-fluid">
                <div class="span1">
                </div>
                <div class="span10">
                    <ul class="breadcrumb">
                        <li>
                            <a href="#">主页</a> <span class="divider">/</span>
                        </li>
                        <li>
                            <a href="#">类目</a> <span class="divider">/</span>
                        </li>
                        <li class="active">
                            主题
                        </li>
                    </ul>
                    <div class="row-fluid">
                        <div class="span6">
                            <div class="carousel slide" id="carousel-751010">
                                <ol class="carousel-indicators">
                                    <li data-slide-to="0" data-target="#carousel-751010">
                                    </li>
                                    <li data-slide-to="1" data-target="#carousel-751010" class="active">
                                    </li>
                                    <li data-slide-to="2" data-target="#carousel-751010">
                                    </li>
                                </ol>
                                <div class="carousel-inner">
                                    <div class="item">
                                        <img alt="" src="${base}/assets/img/1.jpg"/>
                                        <div class="carousel-caption">
                                            <h4>
                                                房屋出租
                                            </h4>
                                            <p>
                                                整租 | 东方星园 精装修 家电齐全电梯 拎包入住 户型方正
                                            </p>
                                        </div>
                                    </div>
                                    <div class="item active">
                                        <img alt="" src="${base}/assets/img/2.jpg"/>
                                        <div class="carousel-caption">
                                            <h4>
                                                2室2厅1卫
                                            </h4>
                                            <p>
                                                整租 | 东城百合公寓。两室两厅一套豪华装修。小区免费停车欢迎
                                            </p>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <img alt="" src="${base}/assets/img/3.jpg"/>
                                        <div class="carousel-caption">
                                            <h4>
                                                1室1厅1卫
                                            </h4>
                                            <p>
                                                整租 | 急租豪装带四个空调东方星园
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <a data-slide="prev" href="#carousel-751010" class="left carousel-control">‹</a> <a
                                    data-slide="next" href="#carousel-751010" class="right carousel-control">›</a>
                            </div>
                        </div>
                        <div class="span6">
                            <div class="tabbable" id="tabs-728636">
                                <ul class="nav nav-tabs">
                                    <li class="active">
                                        <a href="#panel-new" data-toggle="tab">最新帖子</a>
                                    </li>
                                    <li>
                                        <a href="#panel-heat" data-toggle="tab">最热帖子</a>
                                    </li>
                                    <li>
                                        <a href="#panel-recommend" data-toggle="tab">推荐帖子</a>
                                    </li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active" id="panel-new">
                                        <li><a href="#">1</a></li>
                                        <li><a href="#">2</a></li>
                                        <li><a href="#">3</a></li>
                                        <li><a href="#">4</a></li>
                                        <li><a href="#">5</a></li>
                                        <li><a href="#">6</a></li>
                                        <li><a href="#">7</a></li>
                                        <li><a href="#">8</a></li>
                                    </div>
                                    <div class="tab-pane" id="panel-heat">
                                        <li><a href="#">8</a></li>
                                        <li><a href="#">7</a></li>
                                        <li><a href="#">6</a></li>
                                        <li><a href="#">5</a></li>
                                        <li><a href="#">4</a></li>
                                        <li><a href="#">3</a></li>
                                        <li><a href="#">2</a></li>
                                        <li><a href="#">1</a></li>
                                    </div>
                                    <div class="tab-pane" id="panel-recommend">
                                        <li><a href="#">6</a></li>
                                        <li><a href="#">6</a></li>
                                        <li><a href="#">6</a></li>
                                        <li><a href="#">6</a></li>
                                        <li><a href="#">6</a></li>
                                        <li><a href="#">6</a></li>
                                        <li><a href="#">6</a></li>
                                        <li><a href="#">6</a></li>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="accordion" id="accordion-272447">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h3 class="panel-title">面板标题</h3>
                            </div>
                            <div class="panel-body">
                                这是一个基本的面板
                            </div>
                        </div>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    带有 title 的面板标题
                                </h4>
                            </div>
                            <div class="panel-body">
                                面板内容
                            </div>
                        </div>
                    </div>
                </div>
                <div class="span1">
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>