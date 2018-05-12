<#include "/default/inc/header.ftl">
<#include "/default/utils/utils.ftl"/>
<div class="eis_wp988">
    <div class="eis_wp_l">
        <div class="eis_wp_r">
            <div class="eis_wp_t cl">


                <div id="wp" class="wp cl"><style id="diy_style" type="text/css"></style>
                    <!--[diy=diynavtop]--><div id="diynavtop" class="area"></div><!--[/diy]-->
                    <div id="pt" class="bm cl">
                        <div class="z">
                            <a href="./" class="nvhm" title="首页">重庆工商大学校园论坛</a><em>»</em><a href="${base}/">论坛</a> <em>›</em> <a href="javascript:void(0)">${parent}</a><em>›</em> <a href="${base}/plates/${plate.id}">${plate.name}</a></div>
                    </div><div class="wp">
                        <!--[diy=diy1]--><div id="diy1" class="area"></div><!--[/diy]-->
                    </div>
                    <div class="boardnav">


                            <div class="mn">
                                <div class="bm bml pbn">
                                    <div class="bm_h cl">
                                        <span class="y">
                                        </span>
                                        <h1 class="xs2">
                                            <a href="${base}/plates/${plate.id}">求助区 (Seek Help)</a>
                                            <span class="xs1 xw0 i">今日: <strong class="xi1">0</strong><span class="pipe">|</span>主题: <strong class="xi1">669</strong><span class="pipe">|</span>排名: <strong class="xi1" title="上次排名:4">3</strong><b class="ico_increase">&nbsp;</b></span></h1>
                                    </div>
                                </div>

                                    <@pager "${base}/plates/${plate.id}" posts 5 key/>
                                <div id="threadlist" class="tl bm bmw">
                                    <div class="th">
                                        <table cellspacing="0" cellpadding="0">
                                            <tbody><tr>
                                                <th colspan="2">
                                                    <div class="tf">
                                                        <a id="filter_special" href="javascript:;" class="showmenu xi2" onclick="showMenu(this.id)">全部主题</a>&nbsp;
                                                        <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=lastpost&amp;orderby=lastpost" class="xi2">最新</a>&nbsp;
                                                        <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=heat&amp;orderby=heats" class="xi2">热门</a>&nbsp;
                                                        <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=hot" class="xi2">热帖</a>&nbsp;
                                                        <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=digest&amp;digest=1" class="xi2">精华</a>&nbsp;
                                                        <a id="filter_dateline" href="javascript:;" class="showmenu xi2" onclick="showMenu(this.id)">更多</a>&nbsp;
                                                        <span id="clearstickthread" style="display: none;">
                                                        <span class="pipe">|</span>
                                                        <a href="javascript:;" onclick="clearStickThread()" class="xi2" title="显示置顶">显示置顶</a>
                                                        </span>
                                                    </div>
                                                </th>
                                            </tr>
                                            </tbody></table>
                                    </div>
                                    <div class="bm_c">
                                        <script type="text/javascript">var lasttime = 1526115450;var listcolspan= '5';</script>
                                        <div id="forumnew" style="display:none"></div>
                                        <form method="post" autocomplete="off" name="moderate" id="moderate" action="forum.php?mod=topicadmin&amp;action=moderate&amp;fid=28&amp;infloat=yes&amp;nopost=yes">
                                            <input type="hidden" name="formhash" value="0995f66b">
                                            <input type="hidden" name="listextra" value="page%3D1">
                                            <table summary="forum_28" cellspacing="0" cellpadding="0" id="threadlisttableid">

                                                <tbody id="separatorline">
                                                <tr class="ts">
                                                    <td>&nbsp;</td>
                                                    <th><a href="javascript:;" title="查看更新" class="forumrefresh">版块主题</a></th><td>作者</td><td>回复/查看</td><td>最后发表</td>
                                                </tr>
                                                </tbody>
                                                <script type="text/javascript">hideStickThread();</script>
                                                <tbody id="normalthread_31820">
                                                <#list posts.content as row>
                                                    <tr>
                                                        <td class="icn">
                                                            <a href="${base}/post/id" title="新窗口打开" target="_blank">
                                                                <img src="${base}/assets/img/bbs/folder_common.gif">
                                                            </a>
                                                        </td>
                                                        <th class="common">
                                                            <a href="javascript:;" id="content_31820" class="showcontent y" title="更多操作" onclick="CONTENT_TID='31820';CONTENT_ID='normalthread_31820';showMenu({'ctrlid':this.id,'menuid':'content_menu'})"></a>
                                                            <a class="tdpre y" href="javascript:void(0);">预览</a>
                                                            <a href="${base}/post/${row.id}" onclick="atarget(this)" class="s xst">${row.title}</a>
                                                        </th>
                                                        <td class="by">
                                                            <cite>
                                                                <a href="javascript:void(0)" c="1" style="color: #660000;" mid="card_9344">${row.owner}</a></cite>
                                                            <em><span><span title="">${row.createTime}</span></span></em>
                                                        </td>
                                                        <td class="num"><a href="javascript:void(0)" class="xi2">0</a><em>10</em></td>
                                                        <td class="by">
                                                            <cite><a href="javascript:void(0)" c="1" mid="card_7535">${row.owner}</a></cite>
                                                            <em><a href="forum.php?mod=redirect&amp;tid=31820&amp;goto=lastpost#lastpost"><span title="">${row.createTime}</span></a></em>
                                                        </td>
                                                    </tr>
                                                </#list>
                                                </tbody>
                                            </table><!-- end of table "forum_G[fid]" branch 1/3 -->
                                        </form>
                                    </div>
                                </div>

                                <div id="filter_special_menu" class="p_pop" style="display:none" change="location.href='forum.php?mod=forumdisplay&amp;fid=28&amp;filter='+$('filter_special').value">
                                    <ul>
                                        <li><a href="forum-28-1">全部主题</a></li>
                                        <li><a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=specialtype&amp;specialtype=poll">投票</a></li><li><a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=specialtype&amp;specialtype=trade">商品</a></li><li><a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=specialtype&amp;specialtype=activity">活动</a></li><li><a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=specialtype&amp;specialtype=debate">辩论</a></li></ul>
                                </div>
                                <div id="filter_reward_menu" class="p_pop" style="display:none" change="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=specialtype&amp;specialtype=reward&amp;rewardtype='+$('filter_reward').value">
                                    <ul>
                                        <li><a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=specialtype&amp;specialtype=reward">全部悬赏</a></li>
                                        <li><a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=specialtype&amp;specialtype=reward&amp;rewardtype=1">进行中</a></li><li><a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=specialtype&amp;specialtype=reward&amp;rewardtype=2">已解决</a></li></ul>
                                </div>
                                <div id="filter_dateline_menu" class="p_pop" style="display:none">
                                    <ul class="pop_moremenu">
                                        <li>排序:
                                            <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=author&amp;orderby=dateline">发帖时间</a><span class="pipe">|</span>
                                            <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=reply&amp;orderby=replies">回复/查看</a><span class="pipe">|</span>
                                            <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;filter=reply&amp;orderby=views">查看</a>
                                        </li>
                                        <li>时间:
                                            <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;orderby=lastpost&amp;filter=dateline" class="xw1">全部时间</a><span class="pipe">|</span>
                                            <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;orderby=lastpost&amp;filter=dateline&amp;dateline=86400">一天</a><span class="pipe">|</span>
                                            <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;orderby=lastpost&amp;filter=dateline&amp;dateline=172800">两天</a><span class="pipe">|</span>
                                            <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;orderby=lastpost&amp;filter=dateline&amp;dateline=604800">一周</a><span class="pipe">|</span>
                                            <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;orderby=lastpost&amp;filter=dateline&amp;dateline=2592000">一个月</a><span class="pipe">|</span>
                                            <a href="forum.php?mod=forumdisplay&amp;fid=28&amp;orderby=lastpost&amp;filter=dateline&amp;dateline=7948800">三个月</a>
                                        </li>
                                    </ul>
                                </div>

                                <script src="data/cache/autoloadpage.js?XB6" type="text/javascript"></script>
                                <@pager "${base}/plates/${plate.id}" posts 5 key/>
                                <div id="diyfastposttop" class="area"></div><!--[/diy]-->
                                <script type="text/javascript">
                                    var postminchars = parseInt('10');
                                    var postmaxchars = parseInt('100000');
                                    var disablepostctrl = parseInt('0');
                                    var fid = parseInt('28');
                                </script>
                                <div id="f_pst" class="bm">
                                    <div class="bm_h">
                                        <h2>快速发帖</h2>
                                    </div>
                                    <div class="bm_c">
                                        <form method="post" autocomplete="off" id="fastpostform" action="forum.php?mod=post&amp;action=newthread&amp;fid=28&amp;topicsubmit=yes&amp;infloat=yes&amp;handlekey=fastnewpost" onsubmit="return fastpostvalidate(this)">

                                            <div id="fastpostreturn" style="margin:-5px 0 5px"></div>

                                            <div class="pbt cl">
                                                <input type="text" id="subject" name="subject" class="px" value="" onkeyup="strLenCalc(this, 'checklen', 80);" tabindex="11" style="width: 25em">
                                                <span>还可输入 <strong id="checklen">80</strong> 个字符</span>
                                            </div>

                                            <div class="cl">
                                                <div id="fastposteditor">
                                                    <div class="tedt">
                                                        <div class="bar">
                                                            <span class="y">
                                                            <a href="forum.php?mod=post&amp;action=newthread&amp;fid=28"
                                                               onclick="switchAdvanceMode(this.href);doane(event);">高级模式</a>
                                                            </span>
                                                            <script src="data/cache/seditor.js?XB6" type="text/javascript"></script>
                                                            <div class="fpd">
                                                                <a href="javascript:;" title="文字加粗" class="fbld">B</a>
                                                                <a href="javascript:;" title="设置文字颜色" class="fclr" id="fastpostforecolor">Color</a>
                                                                <a id="fastpostimg" href="javascript:;" title="图片" class="fmg">Image</a>
                                                                <a id="fastposturl" href="javascript:;" title="添加链接" class="flnk">Link</a>
                                                                <a id="fastpostquote" href="javascript:;" title="引用" class="fqt">Quote</a>
                                                                <a id="fastpostcode" href="javascript:;" title="代码" class="fcd">Code</a>
                                                                <a href="javascript:;" class="fsml" id="fastpostsml">Smilies</a>
                                                            </div></div>
                                                        <div class="area">
                                                            <div class="pt hm">
                                                                您需要登录后才可以发帖 <a href="${base}/login" class="xi2">登录</a> | <a href="${base}/reg" class="xi2">注册</a>

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
                                                <a href="home.php?mod=spacecp&amp;ac=credit&amp;op=rule&amp;fid=28" class="y" target="_blank">本版积分规则</a>
                                                <button type="submit" onmouseover="checkpostrule('seccheck_fastpost', 'ac=newthread');this.onmouseover=null" name="topicsubmit" id="fastpostsubmit" value="topicsubmit" tabindex="13" class="pn pnc"><strong>发表帖子</strong></button>
                                            </p>
                                        </form>
                                    </div>
                                </div>
                                <!--[diy=diyforumdisplaybottom]--><div id="diyforumdisplaybottom" class="area"></div><!--[/diy]-->
                            </div>

                        </div>
                    </div>
                    <div id="visitedforums_menu" class="p_pop blk cl" style="position: absolute; z-index: 301; left: 475.547px; top: 2426px; display: none;" initialized="true">
                        <table cellspacing="0" cellpadding="0">
                            <tbody><tr>
                                <td id="v_forums">
                                    <h3 class="mbn pbn bbda xg1">浏览过的版块</h3>
                                    <ul class="xl xl1">
                                        <li><a href="forum-6-1" class="">租房住宿 (Housing)</a></li><li><a href="forum-7-1">本地交易 (For Sale)</a></li><li><a href="forum-37-1">建议区 (Suggestions)</a></li><li><a href="forum-2-1">公告区 (Announcements)</a></li></ul>
                                </td>
                            </tr>
                            </tbody></table>
                    </div>
                    <script type="text/javascript">document.onkeyup = function(e){keyPageScroll(e, 0, 1, 'forum.php?mod=forumdisplay&fid=28&filter=&orderby=lastpost&', 1);}</script>
                    <script type="text/javascript">checkForumnew_handle = setTimeout(function () {checkForumnew(28, lasttime);}, checkForumtimeout);</script>
                    <div class="wp mtn">
                        <!--[diy=diy3]--><div id="diy3" class="area"></div><!--[/diy]-->
                    </div>
                    <script>fixed_top_nv();</script>	    </div>
            </div>
        </div>
    </div>
</div>

<#include "/default/inc/footer.ftl">