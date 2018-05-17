<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>重置密码</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">
                <br>
                <#include "/admin/message.ftl">
                <#--<form id="qForm" class="form-horizontal form-label-left" method="post">-->
                    <div class="form-group">
                        <p>设置  ${view.username} 的新密码:</p>
                        <div style="width: 260px;">
                            <input type="text" class="input-small form-control" data-required="true" name="newPassword"
                                   placeholder="新密码">
                        </div>
                    </div>
                    <div class="actions m-t">
                        <button type="submit" id="submit" class="btn btn-primary btn-small">提交</button>
                    </div>
                <#--</form>-->
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var J = jQuery;

    $('#submit').on('click',function () {
        var newPassword=$('input[name="newPassword"]').val().trim();
        if (newPassword.length<1){
            layer.msg("请输入密码",{icon:2});
        }else {
            $.ajax({
                url:"${base}/admin/users/changePwd",
                type:"POST",
                data:{'id':${view.id},
                    'newPassword':newPassword},
                success:function () {
                    layer.msg("修改成功",{icon:1});
                    setTimeout(function () {
                        window.location.href="/admin/users/list";
                    },500)

                }
            })
        }
    })
</script>
</@layout>