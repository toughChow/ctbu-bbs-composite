<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>群组管理</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <div class="group_head">
                    <div class="col-md-5 col-sm-5 col-xs-5 group_btn">
                        <div class="btn btn-primary addGroupa"><span class="fa fa-plus"></span>添加群组</div>
                        <div class="btn btn-default"><span class="fa fa-trash-o"></span><a id="delete_group">删除群组</a>
                        </div>
                    </div>
                    <div class="group_search">

                        <form id="qForm" class="form-inline">
                            <div class="input-group" style="display: flex">
                                <input type="hidden" name="pn" value="${page.pn}"/>
                                <input class="form-control" name="key" placeholder="请输入群名称/群号码" type="text" value="${key}">
                                <span clas="input-group-btn" style="margin: 0">
                                        <button type="submit" value="" style="border-radius: 0"
                                                class="btn btn-info btn-search ">查找</button>
                                    </span>
                            </div>
                        </form>

                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="group_content">
                    <table class="table">
                        <thead>
                        <tr>
                            <th class="text-center"><input name="check_group" type="checkbox"/></th>
                            <th class="text-center">群名称(群号码)</th>
                            <th class="text-center">群主</th>
                            <th class="text-center">联系电话</th>
                            <th class="text-center">群人数</th>
                            <th class="text-center">创建时间</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list page.content as row>
                            <tr>
                                <td class="text-center"><input type="checkbox" data-id="${row.id}"/></td>
                                <td class="text-center" data-group-name="${row.name}">${row.name}(${row.id})</td>
                                <td class="text-center">${row.userGroupName}</td>
                                <td class="text-center">${row.mobile}</td>
                                <td class="text-center">${row.countNum}</td>
                                <td class="text-center">${row.create_time}</td>
                            <#--<@shiro.hasPermission name="group:edit">-->
                                <td class="text-center">
                                <#-- <a data-groupid="${row.id}"
                                    class="operateGroupa">成员管理</a>-->
                                    <a href="${base}/admin/users/group/id/${row.id}">群成员管理</a>
                                    <a data-GroupIds="${row.id}" class="infoGroupa">群信息管理</a>
                                </td>
                            <#--</@shiro.hasPermission>-->
                            </#list>

                        </tbody>
                    </table>
                    <@pager "group" page 5 key/>
                </div>
            <#--模态框-->
                <div class="modal fade addGroupb" data-backdrop="static">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title" id="myModalLabel">添加群组</h4>
                            </div>
                            <form class="form-horizontal" action="${base}/admin/users/add_group" method="post"
                                  onsubmit="return submitTest()">
                                <div class="modal-body">
                                    <div class="form-group">
                                        <lable class="col-sm-2 control-label" for="inputGroupName">群名称<em>*</em></lable>
                                        <div class="col-sm-8">
                                            <input id="inputGroupName" name="name" class="form-control" type="text">
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
                <div class="modal fade infoGroupb" data-backdrop="static">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title" id="myModalLabel">群信息群组</h4>
                            </div>

                            <div class="modal-body">

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default closeinfoGroupb">关闭</button>
                                <button type="button" id="infoGroup" class="btn btn-primary">提交更改</button>
                            </div>

                        </div><!-- /.modal-content -->
                    </div><!-- /.modal -->
                </div>
                <div class="modal fade addMemberb" data-backdrop="static">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-body">
                                <div class="ue-container">
                                    <select multiple="multiple" size="10" name="doublebox" class="memberShuttle">
                                    </select>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default closeaddMemberb">关闭</button>
                                <button type="button" id="addUserMember" class="btn btn-primary">提交更改</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal -->
                </div>
            </div>
        </div>
    </div>
</div>
<script>
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

    //全选
    $('input[name=check_group]').map(function (i) {
        $('input[name=check_group]').eq(i).on('click', function () {
            if (this.checked) {
                $('tbody').eq(i).find('input').prop('checked', true);
            } else {
                $('tbody').eq(i).find('input').removeProp('checked', false);
            }
            ;
        })
    })


    //模态框
    $(function () {
        var modals = [
            {name: 'addGroup'},
            {name: 'infoGroup'},
            {name: 'operateGroup'},
            {name: 'addMember'}
        ]

        modals.forEach(function (e) {
            $('.' + e.name + 'a').on("click", function () {
                $('.' + e.name + 'b').modal('show');
            });
            $('.close' + e.name + 'b').on("click", function () {
                $('.' + e.name + 'b').modal('hide');
                count = 1;
                if (('close' + e.name + 'b') == "closeoperateGroupb") {
                    window.location.href = "https://localhost:9090/admin/users/group";
                }
            });
        })
    });

    function submitTest() {
        var inputValue = $("#inputGroupName").val();
        var inputLength = $("#inputGroupName").val().length;
        if (inputValue == null || inputLength == 0) {
            alert("请输入群昵称");
            return false;
        }
    }

    //添加用户成员
    $("#addUserMember").on('click', function () {
        var userIds = $(".memberShuttle").val();
        /*var groupid = $(this).attr("data-groupid");*/
        console.log(groupid);
        $.ajax({
            url: "${base}/admin/users/add_group_member_right?userIds=" + userIds + "&" + "groupId=" + groupid,
            type: "get",
            success: function (data) {
                if (data.code === 0) {
                    ajaxReload(data);
                    count = 1;
                    window.location.href = "${base}/admin/users/group";
                } else {
                    ajaxReload(data);
                }
            }
        })

    })
    //修改群信息
    $("#infoGroup").on('click', function () {
        var name = $(".group_info").serialize();
        var username = decodeURIComponent(name, true);
        $.ajax({
            url: "${base}/admin/users/update_group?" + username,
            type: "get",
            success: function (data) {
                if (data.code == 0) {
                    ajaxReload(data);
                    window.location.href = "${base}/admin/users/group";
                } else {
                    ajaxReload(data)
                }
            }
        })
    })
    $('.infogroupb').on('hide.bs.modal', function (e) {
        $('this')
    })
    //删除群
    $("#delete_group").on('click', function () {
        var id = '', groupid;
        $('input:checked').each(function () {
            id += $(this).attr('data-id') + ',';
        });
        groupid = id.substr(0, id.length - 1);
        if (groupid == '') {
            return false;
        }
        layer.confirm('确定要执行该操作吗？', {
            btn: ['确定', '取消'], //按钮
            shade: false //不显示遮罩
        }, function () {
            J.getJSON('${base}/admin/users/delete_group', {
                userGroupId: groupid
            }, ajaxReload);
        }, function () {
        });
        return false;
    });
    //获取群信息
    $('.infoGroupa').on("click", function () {
        groupid = $(this).attr("data-GroupIds");

    });
    //群信息管理
    $('.infoGroupb').on('show.bs.modal', function (e) {
        $.ajax({
            url: '${base}/admin/users/group_info?groupId=' + groupid,
            type: 'get',
            success: function (data) {
                console.log(data)
                var html = ``;
                html = ` <form class="group_info form-horizontal">
                                    <div class="form-group">
                                        <lable class="col-sm-2 control-label" for="GroupName">群名称<em>*</em></lable>
                                        <div class="col-sm-8">
                                            <input id="GroupName" data-gourp-name=` + data.name + ` class="form-control" type="text" value=` + data.name + ` name="GroupName">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <lable class="col-sm-2 control-label">群号码</lable>
                                        <div class="col-sm-8">
                                            <p class="number"><input type="hidden" name="groupid" value=` + data.id + `>` + data.id + `</p>
                                        </div>
                                    </div>
                              </form> `;
                $('.infoGroupb').children().children().children(".modal-body").html(html);
            },
            error: function () {

            }
        })
    })

    //第二个模态框关闭，第一个模态框无滚动条BUG
    $('.addMemberb').on('hidden.bs.modal', function () {
        $('.operateGroupb').css({'overflow-y': 'scroll'});
    });
    //右边自适应
    var _height = $("body").height();//获取当前窗口的高度
    $('.x_content').css('height', (_height - 200) + 'px');//调整列表的宽高
</script>
</@layout>