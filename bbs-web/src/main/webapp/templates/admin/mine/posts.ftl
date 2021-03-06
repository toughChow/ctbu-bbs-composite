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
                        <th>标题</th>
                        <th>内容</th>
                        <th>发布时间</th>
                        <th>点赞次数</th>
                        <th>举报次数</th>
                        <th width="300" style="text-align: center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list page.content as row>
                        <tr>
                            <td class="text-center">${row.id}</td>
                            <td>${row.title}</td>
                                <#if row.content?length lt 20 >
                            <td class="text-center tb-content" data-tb-content="${row.content}" data-toggle="modal"
                                data-target="#myModal">${row.content}</td>
                                <#else>
                            <td class="text-center tb-content" data-tb-content="${row.content}" data-toggle="modal"
                                data-target="#myModal">${row.content?substring(0,18)}...
                            </td>
                                </#if>
                            <td>${row.createTime}</td>
                            <td>${row.upvote}</td>
                            <td>${row.tipOff}</td>
                            <td class="text-center">
                                <@shiro.hasPermission name="mine:view">
                                    <a href="javascript:void(0);" class="btn btn-xs btn-success edit_post"
                                       data-tb-title="${row.title}" data-tb-content="${row.content}" data-toggle="modal"
                                       data-target="#myPost" data-id="${row.id}"
                                       data-action="close">
                                        <i class="fa fa-edit"></i> 修改
                                    </a>
                                    <a href="javascript:void(0);" id="delete_postType"
                                       class="btn btn-xs btn-danger delete_postType" data-id="${row.id}"
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
            <!-- 模态框（Modal） -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="modal-title" id="myModalLabel">
                                内容
                            </h4>
                        </div>
                        <div class="modal-body">
                            <!-- content from the message -->
                        <#--<textarea id="modal-content" rows="3" cols="10" readonly="readonly"></textarea>-->
                            <div id="modal-content"
                                 style="width: 460px;height: 100px;word-wrap: break-word;margin: 0 auto;"></div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                            </button>
                        <#--<button type="button" class="btn btn-primary">-->
                        <#--提交更改-->
                        <#--</button>-->
                        </div>
                    </div><!-- /.modal-content -->
                </div>
            </div><!-- /.modal -->
            <!-- 修改（Modal） -->
            <div class="modal fade" id="myPost" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <form role="form" action="${base}/admin/mine/updatePost">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                    &times;
                                </button>
                                <h4 class="modal-title" id="myModalLabel">
                                    修改帖子
                                </h4>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="name">标题：</label>
                                    <input type="text" class="form-control" id="edit_title" name="title"
                                           placeholder="请输入标题">
                                </div>
                                <div class="form-group">
                                    <label for="name">内容：</label>
                                    <textarea name="content" id="edit_post" class="form-control" rows="3"></textarea>
                                </div>
                                <input type="hidden" name="id" id="edit_id">

                                <!-- content from the message -->
                            <#--<textarea id="modal-content" rows="3" cols="10" readonly="readonly"></textarea>-->
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-success">发布</button>
                                <button type="button" class="btn btn-danger" data-dismiss="modal">关闭
                                </button>
                            </div>
                        </div><!-- /.modal-content -->
                    </form>
                </div>
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
                J.getJSON('${base}/admin/posts/delete_post', {
                    id: id
                }, ajaxReload);
            }, function () {
            });
            return false;
        });

        //查看
        $("#edit_postType").on('click', function () {
            var name = $(this).attr('data-name');
            var id = $(this).attr('data-id');
            $('#inputTypeName').val(name);
            $('#typeId').val(id);
        });
        //编辑
        $(".edit_post").on('click', function () {
            var name = $(this).attr('data-tb-content');
            var title = $(this).attr('data-tb-title');
            var id = $(this).attr('data-id');
            $('#edit_post').val(name);
            $('#edit_title').val(title);
            $('#edit_id').val(id);
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
            $('.' + e.name + 'a').on('click', function () {
                $('.' + e.name + 'b').modal('show');
            });
            $('.close' + e.name + 'b').on("click", function () {
                $('.' + e.name + 'b').modal('hide');
                count = 1;
                if (('close' + e.name + 'b') == "closeoperateGroupb") {
                    window.location.href = "${base}/admin/posts/type";
                }
            });
        })
    });

    $(function () {
        $('.tb-content').on('click', function () {
            var valueFromTb = $(this).attr('data-tb-content');
            // var valueFromTb = $(this).next().val();
            $('#modal-content').html(valueFromTb);
        })
    })
</script>
</@layout>