<#macro layout>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${site_name} - 后台管理</title>
    <link rel="stylesheet" href="${base}/assets/vendors/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/assets/vendors/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${base}/assets/css/custom.css">
    <link rel="stylesheet" href="${base}/assets/admin/css/admin.css">
    <!-- Custom Theme Scripts -->
    <script src="${base}/assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/assets/vendors/bootstrap/js/bootstrap.min.js"></script>
    <script src="${base}/assets/js/custom.js"></script>
    <!-- layer -->
    <script src="${base}/assets/vendors/layer/layer.js"></script>
    <!-- nestable -->
    <script src="${base}/assets/vendors/nestable/jquery.nestable.js"></script>
    <link href="${base}/assets/vendors/nestable/jquery.nestable.css" rel="stylesheet">
    <link href="${base}/assets/vendors/nestable/dolphin.css" rel="stylesheet">
    <!-- select2 -->
    <link rel="stylesheet" href="${base}/assets/vendors/select2/select2.min.css">
    <script src="${base}/assets/vendors/select2/select2.min.js"></script>

</head>
<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">
                <div class="navbar nav_title" style="border: 0;">
                    <a href="${base}/index" class="site_title"><span>CTBU-BBS</span></a>
                </div>

                <div class="clearfix"></div>
                <br/>

                <!-- sidebar menu -->
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                    <div class="menu_section">
                        <h3>系统菜单</h3>
                        <ul class="nav side-menu">
                            <li><a href="${base}/admin"><i class="fa fa-home"></i> Home</a>
                            </li>
                            <@authc pid=2>
                                <#list results as menu>
                                    <@shiro.hasPermission name=menu.permission>
                                        <li class="side-menu-unit">
                                            <a href="javascript:void(0);" nav="${menu.sort}"><i
                                                    class="${menu.icon}"></i>
                                                <span>${menu.name}</span>
                                                <span class="arrow"></span>
                                            </a>
                                            <ul class="nav child_menu aaaa">
                                                <#list menu.children as subMenu>
                                                    <li>
                                                        <a href="${base}/${subMenu.url}" nav="${subMenu.sort}"><i
                                                                class="${subMenu.icon}"></i>${subMenu.name}
                                                        </a>
                                                    </li>
                                                </#list>
                                            </ul>
                                        </li>
                                    </@shiro.hasPermission>
                                </#list>
                            </@authc>
                        </ul>
                    </div>
                </div>
                <!-- /sidebar menu -->
            </div>
        </div>
        <script>
            // $(".aaaa").eq(0).show();
        </script>
        <!-- top navigation -->
        <div class="top_nav">
            <div class="nav_menu">
                <nav>
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="list">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown"
                               aria-expanded="false">
                                <!-- 消息提示功能 -->
                                <img src="${base}${profile.avatar}" alt=""
                                     class="popover-show"
                                     title="消息中心" data-container="body"
                                     data-toggle="popover" data-placement="bottom"
                                     data-html="true"
                                     data-content="您有新消息,点击<a href='/admin/message/inbox'>查看</a>"/>

                                ${profile.username}
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                                <li><a href="${base}/account/profile">我的</a></li>
                                <li><a href="${base}/logout"><i class="fa fa-sign-out pull-right"></i>登出</a></li>
                            </ul>
                        </li>
                        <!-- here -->
                    </ul>
                </nav>
            </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <div>
                <#nested/>
            </div>
        </div>
        <!-- /page content -->
    </div>

</body>
</html>
</#macro>

<#macro pager url p spans key>
    <#local span = (spans - 3)/2 />
    <#local pageNo = p.number + 1 />
    <#if (url?index_of("?") != -1)>
        <#local cURL = (url + "&pn=") />
    <#else>
        <#local cURL = (url + "?pn=") />
        <#if key="">
            <#local cKey="">
        <#else>
            <#local cKey = ("&key=" + key) />
        </#if>
    <#--<#local cKey = ("&key=" + key) />-->
    </#if>
    <#if key="">
        <#local cKey="">
    <#else>
        <#local cKey = ("&key=" + key) />
    </#if>

<ul class="pagination">
    <#if (pageNo > 1)>
        <#local prev = pageNo - 1 />
        <li><a class="prev" href="${cURL}${prev}${cKey}" pageNo="1">&nbsp;<i class="fa fa-angle-left"></i>&nbsp;</a>
        </li>
    </#if>
    <#local totalNo = span * 2 + 3 />
    <#local totalNo1 = totalNo - 1 />
    <#if (p.totalPages > totalNo)>
        <#if (pageNo <= span + 2)>
            <#list 1..totalNo1 as i>
                <@pagelink pageNo, i, cURL,cKey/>
            </#list>
            <@pagelink 0, 0, "#",cKey/>
            <@pagelink pageNo, p.totalPages, cURL ,cKey/>
        <#elseif (pageNo > (p.totalPages - (span + 2)))>
            <@pagelink pageNo, 1, cURL ,cKey/>
            <@pagelink 0, 0, "#",cKey/>
            <#local num = p.totalPages - totalNo + 2 />
            <#list num..p.totalPages as i>
                <@pagelink pageNo, i, cURL,cKey/>
            </#list>
        <#else>
            <@pagelink pageNo, 1, cURL,cKey />
            <@pagelink 0 0 "#" cKey/>
            <#local num = pageNo - span />
            <#local num2 = pageNo + span />
            <#list num..num2 as i>
                <@pagelink pageNo, i, cURL ,cKey/>
            </#list>
            <@pagelink 0, 0, "#",cKey/>
            <@pagelink pageNo, p.totalPages, cURL,cKey />
        </#if>
    <#elseif (p.totalPages > 1)>
        <#list 1..p.totalPages as i>
            <@pagelink pageNo, i, cURL,cKey />
        </#list>
    <#else>
        <@pagelink 1, 1, cURL,cKey/>
    </#if>
    <#if (pageNo lt p.totalPages)>
        <#local next = pageNo + 1/>
        <li><a href="${cURL}${next}${cKey}" pageNo="${next}">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;</a></li>
    </#if>
</ul>
</#macro>

<#macro pagelink pageNo idx url cKey>
    <#if (idx == 0)>
    <li><span>...</span></li>
    <#elseif (pageNo == idx)>
    <li class="active"><a href="javascript:void(0);"><span>${idx}</span></a></li>
    <#else>
    <li><a href="${url}${idx}${cKey}">${idx}</a></li>
    </#if>
</#macro>