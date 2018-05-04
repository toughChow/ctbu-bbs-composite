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
        <#if (page.content[0])?? >
        <div class="x_content">
            <table id="dataGrid" class="table table-striped table-bordered b-t">
                <thead>
                <tr>
                    <th class="text-center" width="60">#</th>
                    <th class="text-center" width="120">收信人</th>
                    <th class="text-center">内容</th>
                    <th class="text-center" width="200">时间</th>
                    <th class="text-center" width="150">状态</th>
                    <th class="text-center" width="300">操作信息</th>
                </tr>
                </thead>
                <tbody>
                    <#list page.content as row>
                    <tr>
                        <td class="text-center"><input type="checkbox"></td>
                        <td class="text-center">${row.receiver}</td>
                        <#if row.content?length lt 20 >
                        <td class="text-center tb-content" data-tb-content="${row.content}" data-toggle="modal" data-target="#myModal">${row.content}</td>
                        <#else>
                        <td class="text-center tb-content" data-tb-content="${row.content}" data-toggle="modal" data-target="#myModal">${row.content?substring(0,18)}...</td>
                        </#if>
                        <td class="text-center">${row.pubTime}</td>
                        <td class="text-center">
                            <#if (row.status == 0)>
                                <span class="label label-success">未读取</span>
                            <#elseif (row.status == 1)>
                                <span class="label label-primary">已读取</span>
                            <#elseif (row.status == 2)>
                                <span class="label label-danger">已删除</span>
                            </#if>
                        </td>
                    <#--<@shiro.hasPermission name="message:view">-->
                        <td class="text-center">
                        <#--<#if row.id != 1>-->
                            <a href="javascript:void(0);"
                               class="btn btn-xs btn-primary md-transfer" data-toggle="modal" data-tf-origin="${row.sender}" data-tf-content="${row.content}" data-target="#myModalForTransfer">
                                <i class="fa fa-check-square-o"></i> 转发消息
                            </a>
                        <#--<#else>-->
                            <a href="javascript:void(0);" class="btn btn-xs btn-danger"
                               data-id="${row.id}" data-action="delete">
                                <i class="fa fa-check-square-o"></i> 删除消息</a>
                        <#--</#if>-->
                        <#if (row.status == 2)>
                              <a href="javascript:void(0);" class="btn btn-xs btn-primary"
                                 data-id="${row.id}" data-action="read">
                                  <i class="fa fa-check-square-o"></i> 标记已读</a>
                        </#if>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
        <div class="x_content">
            <@pager "/admin/message/sendlog" page 5 key/>
        </div>
        <#else>
                暂无消息
        </#if>
    </div>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
                    <div id="modal-content" style="width: 460px;height: 100px;word-wrap: break-word;margin: 0 auto;"></div>
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
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModalForTransfer" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        消息转发
                    </h4>
                </div>
                <div class="modal-body">
                    <form id="model-qForm" class="form-horizontal form-label-left" method="post" action="${base}/admin/message/transmit_message">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">收信方</label>
                            <div class="col-lg-8" style="position: relative">
                                <input class="form-control" list="receivers_list" name="receiver" id="transmit_user" type="text" autocomplete="off"
                                       style="width:200px;">
                                <datalist id="receivers_list"></datalist>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">信息</label>
                            <div class="col-lg-8">
                                <textarea id="tf-content" name="content" style="width: 400px;height: 200px;"></textarea>
                            <#--<input type="text" id="tf-content" name="content" style="width: 400px;height: 200px;"/>-->
                            </div>
                        </div>

                        <div class="ln_solid"></div>
                        <div class="form-group">
                            <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                <button type="button" id="md-form-sb" class="btn btn-success <#if user == "admin"> disabled</#if>">
                                    转发
                                </button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                                </button>
                                <b id="md-error-message"></b>
                            </div>
                        </div>
                    </form>
                </div>
            <#--<div class="modal-footer">-->
            <#--<button type="button" class="btn btn-primary">-->
            <#--提交更改-->
            <#--</button>-->
            <#--</div>-->
            </div><!-- /.modal-content -->
        </div>
    </div><!-- /.modal -->
</div>

<script type="text/javascript">
    var J = jQuery;

    var flagAj = 1;

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
        // 删除
        $('#dataGrid a[data-action="delete"]').bind('click', function () {
            var that = $(this);
            layer.confirm('该信息删除后，将不能恢复，确定要停用?', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                J.getJSON('${base}/admin/message/delete', {id: that.attr('data-id'), active: false}, ajaxReload);
            }, function () {
            });
            return false;
        });

        // todo reduce the same
        <!-- same -->
        $('.tb-content').on('click',function () {
            var valueFromTb = $(this).attr('data-tb-content');
            // var valueFromTb = $(this).next().val();
            $('#modal-content').html(valueFromTb);
        })

        $('.md-transfer').on('click',function () {
            var contentVal = $(this).attr('data-tf-content');
            var originUser = $(this).attr('data-tf-origin');
            $('#tf-content').val(contentVal+'-[转自:'+originUser+']')
        })

        //    @down 整合user后异步查询user
        $('#transmit_user').on('input',function(){
            var username = $('#transmit_user').val();
            if(username != ''){
                $.post(
                    "/admin/users/search",
                    {"username":username},
                    function(users) {
                        var htmls = '';
                        if(users != '' && users != null){
                            for(var i = 0; i < users.length; i++){
                                if(i === 0){
                                    htmls += "<option class='option-username optionstyle' data-index='" + i + "' id='" + users[i].id+"' value='"+users[i].username+"'>"+users[i].username+"</option>";
                                }else {
                                    htmls += "<option class='option-username' data-index='" + i + "' id='" + users[i].id+"' value='"+users[i].username+"'>"+users[i].username+"</option>";
                                }
                                $('#receivers_list').html(htmls);
                            }
                            // onblur deal
                            $('#transmit_user').on('blur',function () {
                                if($(this).val() == ''){
                                    $('#p2p-error-message').html("");
                                }
                                var tempVal = $(this).val();
                                $.each(users,function (k,v) {
                                    if(v.username == tempVal){
                                        flagAj = 1;
                                        $('#md-error-message').html("");
                                    }else{
                                        flagAj = 0;
                                        $('#md-error-message').html("该用户不存在").css("color", "red");
                                    }
                                })
                            });// :onblur deal~
                        }else{
                            $('#receivers_list').empty();
                        }
                    },"");
                //endpoint
            }else{
                $('#receivers_list').empty();
            }
        });

        $('#md-form-sb').on('click',function () {
            var username = $('#transmit_user').val();
            var content = $('#tf-content').val();
            if(username == ''){
                $('#md-error-message').html("收信人不能为空").css("color", "red");
                return;
            }else if(content == ''){
                $('#md-error-message').html("内容不能为空").css("color", "red");
                return;
            }else if(flagAj == 0){
                $('#md-error-message').html("该用户不存在").css("color", "red");
                return;
            }else {
                $('#model-qForm').submit();
            }
        })
    });
</script>
</@layout>