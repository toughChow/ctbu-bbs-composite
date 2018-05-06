<#include "/admin/utils/ui.ftl"/>
<@layout>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>帖子列表</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <form id="qForm" class="form-inline">
                    <input type="hidden" name="pn" value="${page.pageNo}"/>
                    <div class="form-group">
                        <input type="text" name="key" class="form-control" value="${key}" placeholder="请输入关键字">
                    </div>
                    <button type="submit" class="btn btn-default">查询</button>
                </form>
            </div>
            <div class="x_content">
                <table id="dataGrid" class="table table-striped table-bordered b-t text-small">
                    <thead>
                    <tr>
                        <th width="80" style="text-align: center">#</th>
                        <th>类型名称</th>
                        <th width="300" style="text-align: center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list page.content as row>
                        <tr>
                            <td class="text-center">${row.id}</td>
                            <td>${row.name}</td>
                            <td class="text-center">
                                <@shiro.hasPermission name="roles:edit">
                                    <a href="javascript:void(0);" id="delete_postType" class="btn btn-xs btn-danger delete_postType" data-id="${row.id}"
                                       data-action="close">
                                        <i class="fa fa-close"></i> 删除
                                    </a>
                                </@shiro.hasPermission>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <@pager "" page 5 />
            </div>
            <!-- 模态框 -->
            <div class="modal fade addGroupb" data-backdrop="static">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myModalLabel">修改帖子类型</h4>
                        </div>
                        <form class="form-horizontal" action="${base}/admin/posts/edit_postType" method="post"
                              onsubmit="return submitTest()">
                            <div class="modal-body">
                                <div class="form-group">
                                    <lable class="col-sm-2 control-label" for="inputGroupName">类型名称<em>*</em></lable>
                                    <div class="col-sm-8">
                                        <input type="hidden" id="typeId" name="id">
                                        <input id="inputTypeName" name="name" class="form-control" type="text">
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default closeaddGroupb">关闭</button>
                                <button type="submit" class="btn btn-primary" id="addGroup">提交更改</button>
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>
        </div>
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

    //删除
    $(".delete_postType").on('click', function () {
        var id = $(this).attr('data-id');
        layer.confirm('确定要执行该操作吗？', {
            btn: ['确定', '取消'], //按钮
            shade: false //不显示遮罩
        }, function () {
            J.getJSON('${base}/admin/posts/delete_postType', {
                id: id
            }, ajaxReload);
        }, function () {
        });
        return false;
    });

    //编辑
    $("#edit_postType").on('click', function () {
        var name = $(this).attr('data-name');
        var id = $(this).attr('data-id');
        $('#inputTypeName').val(name);
        $('#typeId').val(id);
    });

    })

    //模态框
    $(function () {
        var modals = [
            {name: 'addGroup'},
            {name: 'addType'},
            {name: 'operateGroup'},
            {name: 'addMember'}
        ]

        modals.forEach(function (e) {
            $('.' + e.name + 'a').on("click", function () {
                $('.' + e.name + 'b').modal('show');
            });
            $('.'+e.name + 'a').on('click',function () {
                $('.'+e.name + 'b').modal('show');
            });
            $('.close' + e.name + 'b').on("click", function () {
                $('.' + e.name + 'b').modal('hide');
                count = 1;
                if (('close' + e.name + 'b') == "closeoperateGroupb") {
                    window.location.href = "https://localhost:9090/admin/posts/type";
                }
            });
        })
    });
</script>
</@layout>