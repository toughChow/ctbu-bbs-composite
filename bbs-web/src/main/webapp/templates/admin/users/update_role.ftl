<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>修改角色</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <br>
                <#include "/admin/message.ftl">
                <form id="qForm" class="form-horizontal form-label-left" method="post" action="update_role">
                    <input type="hidden" name="id" value="${user.id}"/>

                    <div class="form-group">
                        <label class="col-lg-2 control-label">用户名</label>
                        <div class="col-lg-8">
                            <input class="form-control" type="text" value="${user.username}" disabled
                                   style="width:200px;">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label">角色</label>
                        <div class="col-lg-8">
                            <#list roles as role>
                                <#assign hasRole ="false">
                                <label class="checkbox-inline">
                                    <#list user.roles as userRole>
                                        <#if userRole.id == role.id>
                                            <#assign hasRole ="true">
                                            <#break>
                                        </#if>
                                    </#list>
                                    <#if hasRole == "true">
                                        <input type="checkbox" name="roleIds" value="${role.id}"
                                               checked="checked" <#if user.username == "admin">
                                               disabled</#if>> ${role.name}
                                    <#else>
                                        <input type="checkbox" name="roleIds" value="${role.id}"
                                            <#if user.username == "admin">
                                               disabled</#if>> ${role.name}
                                    </#if>
                                </label>
                            </#list>
                        </div>
                    </div>

                    <div class="ln_solid"></div>
                    <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <button type="submit" id="submit" class="btn btn-success <#if user.username == "admin"> disabled</#if>">
                                提交
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var J = jQuery;

    $(function () {

    })

    $('#submit').on('click', function () {
        var ids = [];
        $('input[name="roleIds"]:checked').each(function () {
            ids.push($(this).val());
        })
        if (ids.length < 1) {
            layer.msg('请至少为用户分配一个角色',{icon:2});
            return false;
        }
    })
</script>
</@layout>