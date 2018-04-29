<#include "/admin/utils/ui.ftl"/>
<@layout>
<style>
    span {
        margin-left: 0;
        font-size: 12px;
    }

    b {
        margin-top: 2px !important;
    }

    .model .model_body span.select2 {
        width: 80% !important;
    }

    /* .model_body {
         height: 350px;
     }*/
</style>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>成员信息(${group.name})</h2>
                <input type="hidden" id="groupId" value="${groupId}">
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <form id="qForm" class="form-inline">
                    <input type="hidden" name="pn" value="${page.pageNo}"/>
                    <div class="form-group">
                        <input type="text" name="key" class="form-control" value="${key}" placeholder="请输入用户名">
                    </div>
                    <button type="submit" class="btn btn-default">查询</button>
                    <#if page.content?size<1>
                    <div class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal_lord"><i
                            class="fa fa-plus"></i>添加群主
                    </div>
                    <#else>
                    <div class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal"><i
                            class="fa fa-plus"></i>添加群成员
                    </div>
                    </#if>
                    <div class="btn btn-default btn-sm" data-action="remove"><i
                            class="fa fa-trash-o"></i>移除群成员
                    </div>
                </form>
            </div>
            <div class="x_content">
                <table id="dataGrid" class="table table-striped table-bordered b-t">
                    <thead>
                    <tr>
                        <th class="text-center"><input type="checkbox" id="check-all"></th>
                        <th class="text-center">用户名</th>
                        <th class="text-center">电话</th>
                        <th class="text-center">邮箱</th>
                        <th class="text-center">角色</th>
                        <th class="text-center">群成员属性</th>
                        <th class="text-center">状态</th>
                        <@shiro.hasPermission name="users:edit">
                            <th width="200" class="text-center">操作</th>
                        </@shiro.hasPermission>
                    </tr>
                    </thead>
                    <tbody>
                        <#list page.content as row>
                        <tr>
                            <td class="text-center">
                                <#if row.isGroupManager!=0>
                                    <input type="checkbox" name="checkId" value="${row.id}" readonly>
                                </#if>
                            </td>
                            <td class="text-center">${row.username}</td>
                            <td class="text-center">${row.mobile}</td>
                            <td class="text-center">${row.email}</td>
                            <td class="text-center">
                                <#if row.roles??>
                                    <#list row.roles as role>
                                        ${role.name}
                                    </#list>
                                <#else>
                                    游客
                                </#if>
                            </td>
                            <td class="text-center">
                                <#if row.isGroupManager==0>
                                    群主
                                <#else>
                                    成员
                                </#if>
                            </td>
                            <td class="text-center">
                                <#if (row.status == 0)>
                                    <span class="label label-success">启用</span>
                                <#else>
                                    <span class="label label-default">禁用</span>
                                </#if>
                            </td>
                            <@shiro.hasPermission name="users:edit">
                                <#if row.isGroupManager==0>
                                <td class="text-center">
                                    <a href="javascript:void(0);" class="btn btn-xs btn-default disabled"
                                       data-id="${row.id}"
                                       data-action="close">
                                        <i class="fa fa-close"></i>不可操作</a>
                                </td>
                                <#else>
                                <td class="text-center">
                                    <a href="javascript:void(0);" class="btn btn-xs btn-primary"
                                       data-id="${row.id}"
                                       data-action="lord">
                                        <i class="fa fa-check-square-o"></i> 设为群主
                                    </a>
                                </td>
                                </#if>
                            </@shiro.hasPermission>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <@pager "list" page 5 key/>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" data-backdrop="static" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    新增群成员
                </h4>
            </div>
            <div class="modal-body">
                <div class="row model_chuansuo">
                    <div class="col-xs-5">
                        <select name="from[]" id="search" class="form-control" size="8"
                                multiple="multiple">
                        </select>
                    </div>
                    <div class="col-xs-2">
                        <div class="btn_col2">
                            <button type="button" id="search_rightAll" class="btn btn-block"><i
                                    class="glyphicon glyphicon-forward"></i></button>
                            <button type="button" id="search_rightSelected" class="btn btn-block"><i
                                    class="glyphicon glyphicon-chevron-right"></i></button>
                            <button type="button" id="search_leftSelected" class="btn btn-block"><i
                                    class="glyphicon glyphicon-chevron-left"></i></button>
                            <button type="button" id="search_leftAll" class="btn btn-block"><i
                                    class="glyphicon glyphicon-backward"></i></button>
                        </div>
                    </div>
                    <div class="col-xs-5">
                        <select name="to[]" id="search_to" class="form-control" size="8"
                                multiple="multiple">
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" id="addUserMember" class="btn btn-primary">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal_lord" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        添加群主
                    </h4>
                </div>
                <div class="modal-body">
                <#--<div class="form-group">-->
                <#--<div class="col-xs-12 col-md-12 col-lg-12">-->
                    <input type="hidden" name="faultId">
                    <select name="userId" class="form-control" id="user-Id" style="width: 100%">
                        <option></option>
                    </select>
                <#--</div>-->
                <#--</div>-->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-info" data-action="lord-sub">
                        确定
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div><!---->

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
        // 设为群主
        $('#dataGrid a[data-action="lord"]').bind('click', function () {
            var groupId = $('#groupId').val();
            var that = $(this);
            layer.confirm('该账号设为群主后将替代原有群主，确定要设定吗？', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                J.getJSON('${base}/admin/users/group/member/lord',
                        {userId: that.attr('data-id'), groupId: groupId}, ajaxReload);
            }, function () {
                layer.tips("系统故障，请稍后再试");
            });
            return false;
        });
        $('div[data-action="remove"]').bind('click', function () {
            var ids = [];
            $('input[name="checkId"]:checked').each(function () {
                ids.push($(this).val());
            });
            if (ids == '') {
                return false;
            }
            layer.confirm('确定要移除该成员吗?', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                J.getJSON('${base}/admin/users/group/member/removeIds',
                        {ids: ids.toString()},
                        ajaxReload);
            }, function () {
            });
            return false;
        })
    })

    $('#search').multiselect({
        search: {
            left: '<input type="search" name="q" class="form-control" placeholder="请输入编码" onkeypress="return searchCode(this,event)"/>',
            // right: '<input type="text" name="q" class="form-control" placeholder="Search..." />',
        },
        fireSearch: function (value) {
            return value.length > 0;
        }
    });
    //全选 全不选
    $('#check-all').on('click', function () {
        if ($(this).is(":checked")) {
            $("input[name='checkId']").prop("checked", true);
        } else {
            $("input[name='checkId']").prop("checked", false);
        }
    })
    $('#addUserMember').on('click', function () {
        var ids = [];
        var groupId = $('#groupId').val();
        $('#search_to option').each(function () {
            ids.push($(this).val());
        });
        $.ajax({
            url: '${base}/admin/users/group/member/add',
            type: 'post',
            data: {'ids': ids.toString(), "groupId": groupId},
            success: function (data) {
                ajaxReload(data);
            }, error: function () {
                layer.tips("系统繁忙，请稍后再试");
            }
        })
    });
    $('button[data-dismiss="modal"]').on('click', function () {
        $('#search_to').empty();
    });

    $('div[data-target="#myModal_lord"]').on('click', function () {
        //模态对话框强制使自己处于焦点状态，导致select2的搜索框无法获取焦点所致。
        //重写模态对话框的enforceFocus函数
        $.fn.modal.Constructor.prototype.enforceFocus = function () {
        };
        $.ajax({
            url: "${base}/admin/users/add_group_member_view",
            type: "get",
            success: function (data) {
                console.log(data);
                $.each(data, function (i, e) {
                    $('#user-Id').append($('<option value="' + e.id + '">' + e.username + '</option>'));
                    console.log(e);
                });
            }
        })
        $('#user-Id').select2({
            placeholder: '请选择群主',
            allowClear: true
        });
    });
    $('div[data-target="#myModal"]').on('click', function () {
        $.ajax({
            url: "${base}/admin/users/add_group_member_view",
            type: "get",
            success: function (data) {
                console.log(data);
                $('select[name="from[]"]').empty();
                data.forEach(function (e, i) {
                    $('select[name="from[]"]').append($('<option value=' + e.id + '>' + e.username + '</option>'));
                })
            }
        })
    })
    $('button[data-action="lord-sub"]').on('click', function () {
        var userId = $('#user-Id').find('option:selected').val();
        var groupId = $('#groupId').val();
        $.ajax({
            url: '${base}/admin/users/group/member/lord',
            type: 'post',
            data: {"userId": userId, "groupId": groupId},
            success: function (data) {
                ajaxReload(data);
            },
            error: function () {
                layer.tips("系统繁忙，请稍后再试");
            }
        })
    })
</script>
</@layout>