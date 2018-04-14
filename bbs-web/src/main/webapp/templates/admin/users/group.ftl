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
                        <div class="btn btn-default"><span class="fa fa-trash-o"></span><a id="delete_group">删除群组</a></div>
                    </div>
                    <div class="group_search">

                            <form id="qForm" class="form-inline">
                               <div class="input-group" style="display: flex">
                                    <input type="hidden" name="pn" value="${page.pn}"/>
                                        <input class="form-control" name="key" placeholder="请输入群名称/群号码" type="text">
                                    <span clas="input-group-btn" style="margin: 0">
                                        <button type="submit" value="" style="border-radius: 0" class="btn btn-info btn-search ">查找</button>
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
                                <th><input name="check_group" type="checkbox"/></th>
                                <th>群名称(群号码)</th>
                                <th>群主</th>
                                <th>联系电话</th>
                                <th>群人数</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list page.content as row>
                                <tr>
                                    <td><input type="checkbox" data-id="${row.id}"/></td>
                                    <td data-group-name="${row.name}">${row.name}(${row.id})</td>
                                    <td>${row.userGroupName}</td>
                                    <td>${row.mobile}</td>
                                    <td>${row.countNum}</td>
                                    <td>${row.create_time}</td>
                                   <#--<@shiro.hasPermission name="group:edit">-->
                                    <td>
                                        <a data-groupid="${row.id}"
                                           class="operateGroupa"<#--获取成员管理信息href="${base}/admin/users/group_user_manager?groupId=${row.id}&userId=${row.userid}"-->>群成员管理</a>
                                        <a data-GroupIds="${row.id}" class="infoGroupa" >群信息管理</a>
                                    </td>
                                      <#--</@shiro.hasPermission>-->
                                </tr>
                            </#list>

                        </tbody>
                    </table>
                    <#if page??>
                        <@pager "group" page 5 key/>
                    </#if>
                </div>
                <#--模态框-->
                <div class="modal fade addGroupb" data-backdrop="static">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title" id="myModalLabel">添加群组</h4>
                            </div>
                            <div class="modal-body">
                                <form class="form-horizontal" action="${base}/admin/users/add_group" method="post" onsubmit="return submitTest()">

                                   <div class="form-group">
                                       <lable class="col-sm-2 control-label" for="inputGroupName" >群名称<em>*</em></lable>
                                       <div class="col-sm-8">
                                         <input id="inputGroupName" name="name" class="form-control" type="text">
                                       </div>
                                   </div>
                                    <div class="form-group">
                                        <lable class="col-sm-2 control-label">群描述</lable>
                                        <div class="col-sm-8">
                                            <textarea class="form-control"  id="" cols="20" rows="5"></textarea>
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
                <div class="modal fade bs-example-modal-lg operateGroupb" data-backdrop="static"  >
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">群成员管理-群名称</h4>
                            </div>
                            <div class="modal-body">
                                <div class="modal-btn">
                                    <div class="btn btn-primary btn-sm addMembera"><span class="fa fa-plus"></span>添加群成员</div>
                                    <div class="btn btn-default btn-sm" id="setGroupManager"><span class="fa fa-user"></span>设为群主</div>
                                    <div class="btn btn-default btn-sm"><span class="fa fa-user-plus"></span>设为管理员</div>
                                    <div class="btn btn-default btn-sm"><span class="fa fa-trash-o"></span><a class="delete_grouper">移除群成员</a></div>
                                </div>
                                <table class="table" id="test_grouppage">
                                    <thead>
                                        <tr>
                                            <th><input data-userid=" " name="check_group" type="checkbox"/></th>
                                            <th>姓名</th>
                                            <th>所属部门</th>
                                            <th>联系电话</th>
                                            <th>email</th>
                                            <th>群成员属性</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td><input type="checkbox"/></td>
                                            <td>张三</td>
                                            <td>技术部</td>
                                            <td>13456325789</td>
                                            <td>dsada@qq.com</td>
                                            <td>群主</td>
                                        </tr>
                                        <tr>
                                            <td><input type="checkbox"/></td>
                                            <td>李四</td>
                                            <td>技术部</td>
                                            <td>13456325789</td>
                                            <td>dsada@qq.com</td>
                                            <td>成员</td>
                                        </tr>
                                        <tr>
                                            <td><input type="checkbox"/></td>
                                            <td>张三</td>
                                            <td>技术部</td>
                                            <td>13456325789</td>
                                            <td>dsada@qq.com</td>
                                            <td>群主</td>
                                        </tr>
                                        <tr>
                                            <td><input type="checkbox"/></td>
                                            <td>李四</td>
                                            <td>技术部</td>
                                            <td>13456325789</td>
                                            <td>dsada@qq.com</td>
                                            <td>成员</td>
                                        </tr>
                                        <tr>
                                            <td><input type="checkbox"/></td>
                                            <td>张三</td>
                                            <td>技术部</td>
                                            <td>13456325789</td>
                                            <td>dsada@qq.com</td>
                                            <td>群主</td>
                                        </tr>
                                        <tr>
                                            <td><input type="checkbox"/></td>
                                            <td>李四</td>
                                            <td>技术部</td>
                                            <td>13456325789</td>
                                            <td>dsada@qq.com</td>
                                            <td>成员</td>
                                        </tr>
                                    </tbody>
                                <table>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default closeoperateGroupb">关闭</button>
                            </div>
                        </div>
                    </div>
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
        $('input[name=check_group]').eq(i).on('click',function () {
            if(this.checked){
                $('tbody').eq(i).find('input').prop('checked',true);
            }else {
                $('tbody').eq(i).find('input').removeProp('checked',false);
            };
        })
    })



    //模态框
    $(function() {
        var modals=[
            {name:'addGroup'},
            {name:'infoGroup'},
            {name:'operateGroup'},
            {name:'addMember'}
        ]

        modals.forEach(function (e) {
            $('.'+e.name+'a').on("click", function() {
                $('.'+e.name+'b').modal('show');
            });
            $('.close'+e.name+'b').on("click", function() {
                $('.'+e.name+'b').modal('hide');
                count = 1;
                if(('close'+e.name+'b')=="closeoperateGroupb"){
                window.location.href = "https://localhost:9090/admin/users/group";}
            });
        })
    });
    function submitTest(){
        var inputValue=$("#inputGroupName").val();
        var inputLength=$("#inputGroupName").val().length;
        if(inputValue==null||inputLength==0){
            alert("请输入群昵称");
            return false;
        }
    }
    //添加用户成员
    $("#addUserMember").on('click',function () {
        var userIds=$(".memberShuttle").val();
        /*var groupid = $(this).attr("data-groupid");*/
        console.log(groupid);
        $.ajax({
            url: "${base}/admin/users/add_group_member_right?userIds="+userIds+"&"+"groupId="+groupid,
            type: "get",
            success:function (data) {
                if(data.code===0){
                    ajaxReload(data);
                    count = 1;
                    window.location.href="${base}/admin/users/group";
            }else{
                    ajaxReload(data);
                }
            }
        })

    })
    //修改群信息
    $("#infoGroup").on('click',function () {
        var name=$(".group_info").serialize();
        var username=decodeURIComponent(name,true);
        $.ajax({
            url:"${base}/admin/users/update_group?"+username,
            type:"get",
            success:function (data) {
                if(data.code==0){
                    ajaxReload(data);
                    window.location.href="${base}/admin/users/group";
                }else{
                    ajaxReload(data)
                }
            }
        })
    })
    $('.infogroupb').on('hide.bs.modal', function (e) {
        $('this')
    })
    //删除群
    $("#delete_group").on('click',function () {
        var id = '',groupid;
       $('input:checked').each(function () {
           id+=$(this).attr('data-id')+',';
       });
       groupid = id.substr(0,id.length-1);
           $.ajax({
                url: "${base}/admin/users/delete_group?userGroupId="+groupid,
                type: "get",
                success:function (data) {
                   if(data.code ===0){
                       ajaxReload(data);
                        window.location.href="${base}/admin/users/group";
                   }else{
                       ajaxReload(data);
                   }
                }
            })
    })

    //删除群成员
    $(".delete_grouper").on('click',function () {

        var userids,id='',arr = $('.operateGroupb tbody input:checked');
        arr.each(function (i,e) {
            id += $('.operateGroupb tbody input:checked').eq(i).attr('data-userid')+',';
        });
        userids = id.substr(0,id.length-1);
       $.ajax({
           url: "${base}/admin/users/delete_user?userIds="+userids,
           type: "get",
           success:function (data) {
                if(data.code==0){
                    ajaxReload(data);
                    window.location.href="${base}/admin/users/group";
                }else{
                    ajaxReload(data);
                }
           }
       })
    })

    //设置群主
    $('#setGroupManager').on('click',function () {
        var userids,id='',arr = $('.operateGroupb tbody input:checked');
        arr.each(function (i,e) {
            id += $('.operateGroupb tbody input:checked').eq(i).attr('data-userid')+',';
        });
        console.log(id.length);
        if(id.length>3){
            return ajaxReload({"code":-1,"message":"只能选择一个群主"})
        }
        userids = id.substr(0,id.length-1);
        $.ajax({
            url: "${base}/admin/users/setGroupManager?userIds="+userids+"&groupId="+groupid,
            type: "get",
            success:function (data) {
                if(data.code==0){
                    ajaxReload(data);
                    window.location.href="${base}/admin/users/group";
                }else{
                    ajaxReload(data);
                }
            }
        })

    })

    //获取群成员
    var groupid;
    $('.operateGroupa').on('click',function () {
        groupid = $(this).attr("data-groupid");
    })
    var count = 1;
    $('.operateGroupb').on('show.bs.modal', function (e) {
        //console.log(count)
        if(count++ == 1){
            $.ajax({
                url: '${base}/admin/users/group_user_manager?groupId='+groupid,
                type:'get',
                success:function (data) {
                    var html =``;
                    var isGroupManager;
                    $.each(data,function (k,v) {
                        if(v.isGroupManager === 0){
                            isGroupManager = '群主';
                        }else{
                            isGroupManager = '成员';
                        }
                        html += `

                        <tr>
                                <input type="hidden" name="groupId" value=`+groupid+`/>
                                <td><input data-userid=`+v.id+` type="checkbox"/></td>
                                <td>`+v.username+`</td>
                                <td></td>
                                <td>`+v.mobile+`</td>
                                <td>`+v.email+`</td>
                                <td>`+isGroupManager+`</td>
                            </tr>

`
                    });

                    $('.operateGroupb tbody').html(html);
                    var table = $('#test_grouppage').DataTable({
                        // paging: false,
                        destroy:true,
                        searching: false,
                        aLengthMenu:[6],//设置一页展示10条记录
                        "bLengthChange": false,//屏蔽tables的一页展示多少条记录的下拉列表
                        "oLanguage": {  //对表格国际化
                            "sLengthMenu": "每页显示 _MENU_条",
                            "sZeroRecords": "没有找到符合条件的数据",
                            //  "sProcessing": "&lt;img src=’./loading.gif’ /&gt;",
                            "sInfo": "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
                            "sInfoEmpty": "木有记录",
                            "sInfoFiltered": "(从 _MAX_ 条记录中过滤)",
                            "sSearch": "搜索：",
                            "oPaginate": {
                                "sFirst": "首页",
                                "sPrevious": "前一页",
                                "sNext": "后一页",
                                "sLast": "尾页"
                            }
                        }
                    });
                }

            });
        }else{
            //穿梭框
            //添加用户成员view
            alert();
            var user = [];
            $.ajax({
                url: "${base}/admin/users/add_group_member_view?groupid=" + groupid,
                type: "get",
                success: function (data) {
                    data.forEach(function (e, i) {
                        user[i] ={"roleId":e.id,"roleName":e.username};
                    })
                    $('.memberShuttle').empty();
                    $('.memberShuttle').doublebox({
                        nonSelectedListLabel: '可选用户',
                        selectedListLabel: '已选用户',
                        preserveSelectionOnMove: 'moved',
                        moveOnSelect: false,
                        nonSelectedList:'',
                        selectedList:'',
                        optionValue:"roleId",
                        optionText:"roleName",
                        doubleMove:false,
                    });
                }
            })
        }
    })
    //获取群信息
    $('.infoGroupa').on("click",function () {
        groupid= $(this).attr("data-GroupIds");

    })
    $('.infoGroupb').on('show.bs.modal', function (e) {
        $.ajax({
            url:'${base}/admin/users/group_info?groupId='+groupid,
            type: 'get',
            success:function (data) {
                console.log(data)
                var html=``;
                html = ` <form class="group_info form-horizontal">
                                    <div class="form-group">
                                        <lable class="col-sm-2 control-label" for="GroupName">群名称<em>*</em></lable>
                                        <div class="col-sm-8">
                                            <input id="GroupName" data-gourp-name=`+data.name+` class="form-control" type="text" value=`+data.name+` name="GroupName">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <lable class="col-sm-2 control-label">群号码</lable>
                                        <div class="col-sm-8">
                                            <p class="number"><input type="hidden" name="groupid" value=`+data.id+`>`+data.id+`</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <lable class="col-sm-2 control-label">群描述</lable>
                                        <div class="col-sm-8">
                                            <textarea class="form-control" name="name" id="" cols="20" rows="5">十大啊啊</textarea>
                                        </div>
                                    </div>
                              </form> `;

                $('.infoGroupb').children().children().children(".modal-body").html(html);
            },
            error:function () {

            }
        })
    })

    //第二个模态框关闭，第一个模态框无滚动条BUG
    $('.addMemberb').on('hidden.bs.modal', function() {
        $('.operateGroupb').css({'overflow-y':'scroll'});
    });
    //右边自适应
    var _height=$("body").height();//获取当前窗口的高度
    $('.x_content').css('height',(_height-200)+'px');//调整列表的宽高
</script>
</@layout>