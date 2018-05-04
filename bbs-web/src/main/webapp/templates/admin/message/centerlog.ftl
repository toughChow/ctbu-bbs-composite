<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>消息日志</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
            </div>
        </div>
        <div class="x_content">
            <form id="qForm" class="form-inline">
                <input type="hidden" id="status" name="status" value="${status}">
                <input type="hidden" name="pn" value="${pn}"/>
                <div class="form-group">
                    <input type="text" name="key" class="form-control" value="${key}" placeholder="请输入关键字">
                </div>
                <button type="submit" class="btn btn-default">查询</button>
            </form>
            <a href="/admin/message/outbox">发信箱</a> |
            <a href="/admin/message/inbox">收信箱</a> |
            <@shiro.hasRole name="管理员">
            <a href="/admin/message/rubbishbox">信息日志</a>
            </@shiro.hasRole>
        </div>
        <#if (page.content[0])??>
        <div class="x_content">
            <table id="dataGrid" class="table table-striped table-bordered b-t">
                <thead>
                <tr>
                    <th class="text-center" width="60">#</th>
                    <th class="text-center" width="200">发信人</th>
                    <th class="text-center">收信人</th>
                    <th class="text-center" width="200">时间</th>
                    <th class="text-center" width="150">状态</th>
                    <th class="text-center" width="300">操作信息</th>

                </tr>
                </thead>
                <tbody>
                    <#list page.content as row>
                    <tr>
                        <td class="text-center"><input type="checkbox"></td>
                        <td class="text-center">${row.sender}</td>
                        <td class="text-center">${row.receiver}</td>
                        <td class="text-center">${row.pubTime}</td>
                        <td class="text-center">
                            <span class="label label-default">已删除</span>
                        </td>
                    <#--<@shiro.hasPermission name="message:view">-->
                        <td class="text-center">
                        <#--<#if row.id != 1>-->
                            <a href="javascript:void(0);" class="btn btn-xs btn-primary"
                               data-id="${row.id}" data-action="recoverMsg">
                                <i class="fa fa-check-square-o"></i> 恢复消息</a>
                        <#--<#else>-->
                            <a href="javascript:void(0);" class="btn btn-xs btn-danger"
                               data-id="${row.id}" data-action="deleteFromDb">
                                <i class="fa fa-check-square-o"></i> 彻底删除</a>
                        <#--</#if>-->
                        </td>
                    <#--</@shiro.hasPermission>-->
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
        <div class="x_content">
            <@pager "/admin/message/centerlog?status=${status}" page 5 key/>
        </div>
        <#else>
                暂无消息
        </#if>
    </div>
</div>

<script type="text/javascript">
    var J = jQuery;

    function ajaxReload(json) {
        if (json.code >= 0) {
            if (json.message != null && json.message != '') {
                layer.msg(json.message, {icon: 1});
            }
            $('#qForm').submit();
        } else {
            layer.msg(json.message, {icon: 2});
        }
    }

    $(function () {
        // 彻底删除
        $('#dataGrid a[data-action="deleteFromDb"]').bind('click', function () {
            var that = $(this);
            layer.confirm('该信息删除后，将不能恢复，确定要停用?', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                J.getJSON('${base}/admin/message/deleteFromDb', {id: that.attr('data-id'), active: false}, ajaxReload);
            }, function () {
            });
            return false;
        });

        // 恢复数据
        $('#dataGrid a[data-action="recoverMsg"]').bind('click', function () {
            var that = $(this);
            layer.confirm('确定将该信息恢复至收件箱?', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                J.getJSON('${base}/admin/message/changeStatus1', {id: that.attr('data-id'), active: false}, ajaxReload);
            }, function () {
            });
            return false;
        });
    });
</script>
</@layout>