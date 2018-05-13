<#include "/default/inc/header.ftl">

<div class="eis_wp988" xmlns="http://www.w3.org/1999/html">
    <div class="eis_wp_l">
        <div class="eis_wp_r">
            <div class="eis_wp_t cl">


                <div id="wp" class="wp cl">
                    <script type="text/javascript">var fid = parseInt('28'), tid = parseInt('31229');</script>

                    <script type="text/javascript">zoomstatus = parseInt(1);
                    var imagemaxwidth = '600';
                    var aimgcount = new Array();</script>

                    <style id="diy_style" type="text/css"></style>
                    <!--[diy=diynavtop]-->
                    <div id="diynavtop" class="area"></div><!--[/diy]-->
                    <div id="pt" class="bm cl">
                        <div class="z">
                            <a href="./" class="nvhm" title="首页">重庆工商大学校园论坛</a><em>»</em><a
                                href="${base}/">论坛</a> <em></em> 发帖</div>
                    </div>



                    <div id="ct" class="wp cl">
                        <div id="f_pst" class="bm">
                            <div class="bm_c">
                                <form class="form-horizontal" id="pForm" action="${base}/admin/posts/send" method="post" role="form">
                                    <div class="form-group">
                                        <label for="firstname">主题</label>
                                            <input type="text" name="title" class="form-control" id="firstname" placeholder="请输入标题">
                                    </div>
                                    <div class="form-group">
                                        <label for="name">内容</label>
                                        <textarea name="content" class="form-control" placeholder="请输入内容"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="name">帖子类型</label>
                                        <select name="postTypeId" class="form-control">
                                            <#list postTypes as row>
                                                <option name="postTypeId" value="${row.id}">${row.name}</option>
                                            </#list>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="name">所属板块</label>
                                        <select name="plateId" class="form-control">
                                            <#list plates as row>
                                                <option name="plateId" value="${row.id}">${row.name}</option>
                                            </#list>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <div>
                                            <button type="button" class="btn btn-default send-post">发布</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $('.send-post').on('click',function () {
        layer.msg("发布成功，等待管理员审核，即将跳转到首页",{icon:1});
        setTimeout(function () {
            $('#pForm').submit();
        },3000)
    })
</script>

<#include "/default/inc/footer.ftl">