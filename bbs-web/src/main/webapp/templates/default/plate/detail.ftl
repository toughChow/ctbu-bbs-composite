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
                                href="${base}/">论坛</a> <em>›</em> <a href="javascript:void(0)">${grandpa}</a> <em>›</em>
                            <a
                                    href="${base}/plates/${parent.id}">${parent.name}</a> <em>›</em> <a
                                href="${base}/post/${post.id}">${post.title}</a>
                        </div>
                    </div>


                    <div class="wp">
                        <!--[diy=diy1]-->
                        <div id="diy1" class="area"></div><!--[/diy]-->
                    </div>

                    <div id="ct" class="wp cl">
                        <div id="pgt" class="pgs mbm cl ">
                            <div class="pgt"></div>
                            <span class="y pgb" id="visitedforums"
                                  onmouseover="$('visitedforums').id = 'visitedforumstmp';this.id = 'visitedforums';showMenu({'ctrlid':this.id,'pos':'34'})"><a
                                    href="${base}/plates/${parent.id}">返回列表</a></span>
                            <a id="newspecial" href="javascript:;" title="发新帖"><img
                                    src="static/image/common/pn_post.png" alt="发新帖"></a>
                        </div>

                        <div id="postlist" class="pl bm">
                            <div id="post_93017">
                                <div class="bm_h">
                                    <h2>正文</h2>
                                </div>
                                <table id="pid93017" class="plhin" summary="pid93017" cellspacing="0" cellpadding="0">
                                    <tbody>
                                    <input type="hidden" value="${post.id}" id="thisId"/>
                                    <tr>
                                        <td class="pls" rowspan="2">
                                            <div id="favatar93017" class="pls favatar">
                                                <div class="pi">
                                                    <div class="authi">作者：<a href="javascript:void(0)" target="_blank"
                                                                             class="xw1"
                                                                             style="color: #FF66FF">${author.username}</a>
                                                    </div>
                                                </div>
                                                <div class="p_pop blk bui card_gender_2" id="userinfo93017"
                                                     style="display: none; margin-top: -11px;">
                                                    <div class="m z">
                                                        <div id="userinfo93017_ma"></div>
                                                    </div>

                                                </div>
                                                <div>
                                                    <div class="avatar">
                                                        <a href="space-uid-32942" class="avtm" target="_blank"><img
                                                                src="${base}${author.avatar};size=middle"></a>
                                                    </div>
                                                </div>
                                                <div class="tns xg2">
                                                    <table cellspacing="0" cellpadding="0">
                                                        <tbody>
                                                        <tr>
                                                            <th><p><a
                                                                    href="home.php?mod=space&amp;uid=32942&amp;do=thread&amp;type=thread&amp;view=me&amp;from=space"
                                                                    class="xi2">32</a></p>主题
                                                            </th>
                                                            <th><p><a
                                                                    href="home.php?mod=space&amp;uid=32942&amp;do=thread&amp;type=reply&amp;view=me&amp;from=space"
                                                                    class="xi2">390</a></p>帖子
                                                            </th>
                                                            <td><p><a
                                                                    href="home.php?mod=space&amp;uid=32942&amp;do=thread&amp;type=thread&amp;view=me&amp;from=space"
                                                                    class="xi2">0</a></p>精华
                                                            </td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </td>
                                        <td></td>
                                        <td class="plc">
                                            <div class="pct">
                                                <div class="t_fsz">
                                                    <table cellspacing="0" cellpadding="0">
                                                        <tbody>
                                                        <tr>
                                                            <td class="t_f" id="postmessage_93017">
                                                                <i class="pstatus"> 本帖最后由 ${author.username}
                                                                    于 ${post.createTime}
                                                                    编辑 </i><br>
                                                                <br>
                                                                <!-- 内容 -->
                                                            ${post.content}
                                                            </td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                 </tr>

                                <tr></tr><tr><td></td><td></td><td>
                            <#if profile??>
                            <a href="javascript:void(0)" id="collectPost">收藏</a>
                            <a href="javascript:void(0)" id="upvote">点赞</a>&emsp;
                            <a href="javascript:void(0)" id="tipoff">举报</a></td></tr>
                            </#if>
                            </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div id="f_pst" class="bm">
                    <div class="bm_h">
                        <h2>评论区</h2>
                    </div>
                    <div id="comment_area">
                    </div>
                    </div>
                </div>

                <div id="f_pst" class="bm">
                    <div class="bm_h">
                        <h2>快速回帖</h2>
                    </div>
                    <div class="bm_c">
                        <#if !profile??>

                            <form method="post" autocomplete="off" id="fastpostform"
                                  action="forum.php?mod=post&amp;action=newthread&amp;fid=28&amp;topicsubmit=yes&amp;infloat=yes&amp;handlekey=fastnewpost"
                                  onsubmit="return fastpostvalidate(this)">

                                <div id="fastpostreturn" style="margin:-5px 0 5px"></div>
                                <div class="cl">
                                    <div id="fastposteditor">
                                        <div class="tedt">
                                            <div class="bar">
                                                <div class="fpd">
                                                    <a href="javascript:;" title="文字加粗" class="fbld">B</a>
                                                    <a href="javascript:;" title="设置文字颜色" class="fclr"
                                                       id="fastpostforecolor">Color</a>
                                                    <a id="fastpostimg" href="javascript:;" title="图片"
                                                       class="fmg">Image</a>
                                                    <a id="fastposturl" href="javascript:;" title="添加链接"
                                                       class="flnk">Link</a>
                                                    <a id="fastpostquote" href="javascript:;" title="引用"
                                                       class="fqt">Quote</a>
                                                    <a id="fastpostcode" href="javascript:;" title="代码"
                                                       class="fcd">Code</a>
                                                    <a href="javascript:;" class="fsml" id="fastpostsml">Smilies</a>
                                                </div>
                                            </div>
                                            <div class="area">
                                                <div class="pt hm">
                                                    您需要登录后才可以回帖 <a href="${base}/login" class="xi2">登录</a> | <a
                                                        href="${base}/reg" class="xi2">注册</a>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="seccheck_fastpost">
                                    </div>
                                    <input type="hidden" name="formhash" value="0995f66b">
                                    <input type="hidden" name="usesig" value="">
                                </div>

                                <p class="ptm pnpost">
                                    <a href="home.php?mod=spacecp&amp;ac=credit&amp;op=rule&amp;fid=28" class="y"
                                       target="_blank">本版积分规则</a>
                                    <button type="submit"
                                            onmouseover="checkpostrule('seccheck_fastpost', 'ac=newthread');this.onmouseover=null"
                                            name="topicsubmit" id="fastpostsubmit" value="topicsubmit" tabindex="13"
                                            class="pn pnc"><strong>发表帖子</strong></button>
                                </p>
                            </form>
                        <#else>
                                <div class="form-group">
                                    <textarea name="content" id="thisComment" class="form-control"
                                              placeholder="请输入内容"></textarea>
                                </div>
                                <div class="form-group">
                                    <div>
                                        <button type="button" class="btn btn-default send-post" id="doComment">评论
                                        </button>
                                    </div>
                                </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <form id="qForm" class="form-inline">
    </form>
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

    function getComments(id) {
        $.ajax({
            url: "${base}/comments",
            type: "post",
            data: {"postId": id},
            dataType: 'json',
            success: function (data) {
                for(var i = 0; i < data.length; i++){
                    var dt = '/Date('+data[i].commentTime+')/';
                    var formatTime = convertTime(dt, "yyyy-MM-dd hh:mm:ss");
                    data[i].commentTime = formatTime;
                }
                        commentContent = '';
                        for(var i = 0; i < data.length; i++){
                            html = '<div class="bm_c">\n' +
                            '<table id="pid93017" class="plhin" summary="pid93017" cellspacing="0" cellpadding="0">\n' +
                            '                            <tbody>\n' +
                            '                            <tr>\n' +
                            '                                <td class="pls" rowspan="2">\n' +
                            '                                    <div id="favatar93017" class="pls favatar">\n' +
                            '\n' +
                            '                                        <div class="p_pop blk bui card_gender_2" id="userinfo93017"\n' +
                            '                                             style="display: none; margin-top: -11px;">\n' +
                            '                                            <div class="m z">\n' +
                            '                                                <div id="userinfo93017_ma"></div>\n' +
                            '                                            </div>\n' +
                            '                                        </div>\n' +
                            '                                        <div>\n' +
                            '                                            <div class="avatar" ' +
                            '                                                <a href="javascript:void(0)" class="avtm" target="_blank"><img\n' +
                            '                                                        src="${base}${author.avatar};size=middle"></a>\n' +
                            '                                            </div>\n' +
                            '                                        </div>\n' +
                            '                                    </div>\n' +
                            '                                </td>\n' +
                            '                                <td></td>\n' +
                            '                                <td class="plc">\n' +
                            '                                    <div class="pct">\n' +
                            '                                        <div class="t_fsz">\n' +
                            '                                            <table cellspacing="0" cellpadding="0">\n' +
                            '                                                <tbody>\n' +
                            '                                                <tr>\n' +
                            '                                                    <td class="t_f" id="postmessage_93017">\n' +
                            '                                                        <!-- 内容 -->\n' +
                            '                                                    ' + data[i].content + '<br><br>\n' +
                            '                                                        <br><i class="pstatus"> 评论最后由 ' + data[i].user.username + '\n' +
                            '                                                            于 ' + data[i].commentTime + '\n' +
                            '                                                            编辑 </i>\n' +
                            '                                                    </td>\n' +
                            '                                                </tr>\n' +
                            '                                                </tbody>\n' +
                            '                                            </table>\n' +
                            '                                        </div>\n' +
                            '                                    </div>\n' +
                            '                                </td>\n' +
                            '                            </tr>\n' +
                            '                           <tr></tr><tr><td></td><td></td><td><#if profile??><a href="javascript:void(0)" id="upvote">点赞</a>&emsp;<a href="javascript:void(0)" id="tipoff">举报</a></#if></td></tr>\n'+
                            '                           </tbody>\n' +
                            '                        </table>\n' +
                            '                   </div>';

                            commentContent = commentContent + html;
                        }
                        $('#comment_area').html(commentContent);
                console.log(data);
            },
            error: function () {
                alert("gg")
            }
        })
    }

    function convertTime(jsonTime, format) {
        var date = new Date(parseInt(jsonTime.replace("/Date(", "").replace(")/", ""), 10));
        var formatDate = date.format(format);
        return formatDate;
    }
    Date.prototype.format = function (format) {
        var date = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S+": this.getMilliseconds()
        };

        if (/(y+)/i.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
        }

        for (var k in date) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
            }
        }
        return format;
    }

    $(function () {
        var id = $('#thisId').val();
        getComments(id);

        //评论
        $("#doComment").on('click', function () {
            var id = $('#thisId').val();
            var content = $('#thisComment').val();
            if (content == null || content == '') {
                layer.msg("请输入评论内容", {icon: 2})
                return false;
            }
            layer.confirm('确定要评论该帖吗？', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                J.getJSON('${base}/admin/posts/commentPost', {
                    id: id, content: content
                }, ajaxReload);
                // reload comment
                getComments(id);
            }, function () {
            });
            return false;
        });

        //收藏
        $("#collectPost").on('click', function () {
            var id = $('#thisId').val();
            layer.confirm('确定要收藏该帖吗？', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                J.getJSON('${base}/admin/posts/collectPost', {
                    id: id
                }, ajaxReload);
            }, function () {
            });
            return false;
        });

    });
</script>

<#include "/default/inc/footer.ftl">