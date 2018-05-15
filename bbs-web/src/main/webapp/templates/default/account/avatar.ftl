<#include "/default/utils/layout.ftl"/>
<@ui_simple "修改用户信息">

<div class="panel panel-default stacked">
    <div class="panel-heading">
        <ul class="nav nav-pills account-tab">
            <li><a href="profile">基本信息</a></li>
            <li class="active"><a href="avatar">修改头像</a></li>
            <li><a href="password">修改密码</a></li>
        </ul>
    </div>
    <div class="panel-body">
        <div id="message">
            <#include "/default/inc/action_message.ftl"/>
        </div>
        <form class="form-horizontal" action="${base}/account/avatar" method="post" enctype="multipart/form-data">
            <div class="upload-btn">
                <label>
                    <span>头像修改</span>
                    <input id="upload_btn" id="select_file" type="file" name="avater" accept="image/*" title="点击添加图片">
                </label>
            </div>
            <div class="update_ava">
                <img width="128px" name="newAva" src="<@resource src=profile.avatar/>" id="target" alt="[Jcrop Example]"/>
            </div>
            <div class="form-group">
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">提交</button>
                </div>
            </div>
        </form>
    </div><!-- /panel-content -->
</><!-- /panel -->


<script type="text/javascript">
    seajs.use('avatar');

    var upload_btn = document.querySelector('#upload_btn');
    if(!(window.FileReader && window.File && window.FileList && window.Blob)){
        show.innerHTML = '您的浏览器不支持预览';
        upload_btn.setAttribute('disabled', 'disabled');
    }
    upload_btn.addEventListener('change', function(e){
        var files = this.files;
        if(files.length){
            // 对文件进行处理，下面会讲解checkFile()会做什么
            checkFile(this.files);
        }
    });
    // 图片处理
    function checkFile(files){
        var file = files[0];
        var reader = new FileReader();
        // show表示<div id='show'></div>，用来展示图片预览的
        var show = document.querySelector("#target")
        if(!/image\/\w+/.test(file.type)){
            show.innerHTML = "请确保文件为图像类型";
            return false;
        }
        // onload是异步操作
        reader.onload = function(e){
            //show.innerHTML = '<img src="'+e.target.result+'" alt="img">';
            show.setAttribute("src", e.target.result);
        }
        reader.readAsDataURL(file);
    }
</script>
</@ui_simple>