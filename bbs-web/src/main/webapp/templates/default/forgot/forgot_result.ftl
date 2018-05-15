<#include "/default/utils/layout_login.ftl"/>
<@layout "密码找回">
    <div class="login">
        <a href="${base}/index">
            <img src="${base}/assets/images/logo/m90.png" height="72" width="72">
        </a>

        <form action="login" method="post">
            <div id="message"><#include "/default/inc/action_message.ftl"/></div>
            <div class="text-center">
            <#list data.links as row>
                <a href="${base}/${row.link}" class="btn btn-success">${row.text}</a>
            </#list>
            </div>
        </form>

    </div>
</@layout>