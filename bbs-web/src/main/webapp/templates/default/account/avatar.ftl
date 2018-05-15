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
        <#--<form class="form-horizontal" action="avatar" method="post" enctype="multipart/form-data">-->
            <#--&lt;#&ndash;<input type="hidden" id="x" name="x" value=""/>-->
            <#--<input type="hidden" id="y" name="y" value=""/>-->
            <#--<input type="hidden" id="width" name="width" value=""/>-->
            <#--<input type="hidden" id="height" name="height" value=""/>-->
            <#--<input type="hidden" id="path" name="path" value=""/>&ndash;&gt;-->
            <#--<div class="upload-btn">-->
                <#--<label>-->
                    <#--<span>头像修改</span>-->
                    <#--<input id="upload_btn" id="select_file" type="file" name="avater" accept="image/*" title="点击添加图片">-->
                <#--</label>-->
            <#--</div>-->
            <#--<div class="update_ava">-->
                <#--<img width="128px" src="<@resource src=profile.avatar/>" id="target" alt="[Jcrop Example]"/>-->
            <#--</div>-->
            <#--<div class="form-group">-->
                <#--<div class="text-center">-->
                    <#--<button type="submit" class="btn btn-primary">提交</button>-->
                <#--</div>-->
            <#--</div>-->
        <#--</form>-->

        <form class="form-horizontal" action="avatar" method="post" enctype="multipart/form-data">
            <div class="htmleaf-container">
                <div style="width: 100%;">
                    <div class="imageBox" style="background: url('<@resource src=profile.avatar/>') no-repeat;background-size: cover;width:250px;height: 250px;">
                        <div class="thumbBox"></div>
                        <div class="spinner" style="display: none">请上传图片、自行剪裁！</div>
                    </div>
                    <div class="action">
                        <div class="new-contentarea tc">
                            <a href="javascript:void(0)" class="upload-img">
                                <label for="upload-file">上传图像</label>
                            </a>
                            <input type="file" class="" name="upload-file" id="upload-file" />
                        </div>
                        <input type="button" id="btnCrop"  class="Btnsty_peyton" value="裁切">
                        <input type="button" id="btnZoomIn" class="Btnsty_peyton" value="+"  >
                        <input type="button" id="btnZoomOut" class="Btnsty_peyton" value="-" >
                    </div>
                    <div class="cropped"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="text-center">
                    <button type="button" id="cropEndBtn" class="btn btn-primary">提交</button>
                </div>
            </div>
        </form>
    </div><!-- /panel-content -->
</><!-- /panel -->

<script>window.jQuery || document.write('<script src="${base}/assets/js/jquery.min.js"><\/script>')</script>
                <script type="text/javascript" src="${base}/assets/js/avatar/cropbox.js"></script>
                <script type="text/javascript">
                    $(window).load(function() {
                        var options =
                                {
                                    thumbBox: '.thumbBox',
                                    spinner: '.spinner',
                                    imgSrc: ''
                                }
                        console.log("=======================",cropbox)
                        var cropper = $('.imageBox').cropbox(options);
                        $('#upload-file').on('change', function(){
                            var reader = new FileReader();
                            reader.onload = function(e) {
                                options.imgSrc = e.target.result;
                                cropper = $('.imageBox').cropbox(options);
                            }
                            reader.readAsDataURL(this.files[0]);
                            // this.files = [];
                        })
                        $('#btnCrop').on('click', function(){
                            var img = cropper.getDataURL();
                            console.log(cropper)
                            console.log(img)
                            $('.cropped').html('');
                            $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:64px;margin-top:4px;border-radius:64px;box-shadow:0px 0px 12px #7E7E7E;" ><p>64px*64px</p>');
                            $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:128px;margin-top:4px;border-radius:128px;box-shadow:0px 0px 12px #7E7E7E;"><p>128px*128px</p>');
                            $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:180px;margin-top:4px;border-radius:180px;box-shadow:0px 0px 12px #7E7E7E;"><p>180px*180px</p>');
                        })
                        $('#btnZoomIn').on('click', function(){
                            cropper.zoomIn();
                        })
                        $('#btnZoomOut').on('click', function(){
                            cropper.zoomOut();
                        })
                    });


                    $("#cropEndBtn").on("click", function () {
                        //得到img base64
                        var imgs = $(".cropped").children("img");
                        if(imgs.length == 0){
                            layer.msg("请先选择图片并剪裁！",{icon:5})
                            return;
                        }
                        var imgSmallBase64 = imgs.eq(1).attr("src");
                        console.log(imgSmallBase64);

                        //base64 转 blob
                        var $Blob= getBlobBydataURI(imgSmallBase64,'image/jpeg');
                        var formData = new FormData();
                        formData.append("avater", $Blob ,"file_"+Date.parse(new Date())+".jpg");
                        //组建XMLHttpRequest 上传文件
                        var request = new XMLHttpRequest();
                        request.open("POST", "${base}/account/avatar");
                        request.onreadystatechange=function()
                        {
                            if (request.readyState==4)
                            {
                                if(request.status==200){
                                    console.log("上传成功");
                                    window.location.href="${base}/admin"
                                }else{
                                    console.log("上传失败,检查上传地址是否正确");
                                }
                            }
                        };
                        request.send(formData);

                    });

                    function getBlobBydataURI(dataURI,type) {
                        var binary = atob(dataURI.split(',')[1]);
                        var array = [];
                        for(var i = 0; i < binary.length; i++) {
                            array.push(binary.charCodeAt(i));
                        }
                        return new Blob([new Uint8Array(array)], {type:type });
                    }
                </script>

<#--<script type="text/javascript">-->
    <#--//seajs.use('avatar');-->

    <#--var upload_btn = document.querySelector('#upload_btn');-->
    <#--if(!(window.FileReader && window.File && window.FileList && window.Blob)){-->
        <#--show.innerHTML = '您的浏览器不支持预览';-->
        <#--upload_btn.setAttribute('disabled', 'disabled');-->
    <#--}-->
    <#--upload_btn.addEventListener('change', function(e){-->
        <#--var files = this.files;-->
        <#--if(files.length){-->
            <#--// 对文件进行处理，下面会讲解checkFile()会做什么-->
            <#--checkFile(this.files);-->
        <#--}-->
    <#--});-->
    <#--// 图片处理-->
    <#--function checkFile(files){-->
        <#--var file = files[0];-->
        <#--var reader = new FileReader();-->
        <#--// show表示<div id='show'></div>，用来展示图片预览的-->
        <#--var show = document.querySelector("#target")-->
        <#--if(!/image\/\w+/.test(file.type)){-->
            <#--show.innerHTML = "请确保文件为图像类型";-->
            <#--return false;-->
        <#--}-->
        <#--// onload是异步操作-->
        <#--reader.onload = function(e){-->
            <#--//show.innerHTML = '<img src="'+e.target.result+'" alt="img">';-->
            <#--show.setAttribute("src", e.target.result);-->
        <#--}-->
        <#--reader.readAsDataURL(file);-->
    <#--}-->
<#--</script>-->
</@ui_simple>