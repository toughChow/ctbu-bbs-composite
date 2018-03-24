<#include "/default/utils/layout.ftl"/>
<#include "/default/utils/utils.ftl"/>

<@ui_ltr site_name>
<div class="content">
    <div class="recommend hidden-xs hidden-sm"
    ">
    <ul>
        <@banner>
            <#list results as row>
                <li <#if row_index == 0> class="large" </#if>
                    <a href="${base}/view/${row.id}">
                        <@albShow row.albums[0]/>
                        <h4>${row.title}</h4>
                    </a>
                </li>
            </#list>
        </@banner>
    </ul>
</div>
</div>

    <@contents group=0>

    </@contents>

</@ui_ltr>