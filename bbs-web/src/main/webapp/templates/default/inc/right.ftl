<script type="text/javascript">
    var hot_li_template = '<li><div class="li-out"><span class="last"><i>{0}</i></span><span class="name"><a  href="{1}">{2}</a></span><span class="nums">{3}</span></div></li>'
    var latest_li_template = '<li><div class="li-out"><span class="name"><a  href="{1}">{2}</a></span><span class="nums">{3}</span></div></li>'

    var hotUser_li_template = '<li class=""><a  href="{1}"><img src="${base}{0}" class="imguser"/></a></li>'

    seajs.use('sidebox', function (sidebox) {
        sidebox.init({

            maxResults: 10,
            // callback
            onLoadHot: function (i, data) {
                var url = '${base}/view/' + data.id;
                var item = jQuery.format(hot_li_template, i + 1, url, data.title, numberScale(data.views));
                return item;
            },
            onLoadLatest: function (i, data) {
                var url = '${base}/view/' + data.id;
                var item = jQuery.format(latest_li_template, i + 1, url, data.title, numberScale(data.views));
                return item;
            },
            onLoadHotUser: function (i, data) {
                var url = '${base}/ta/' + data.id;
                var item = jQuery.format(hotUser_li_template, data.avatar, url, data.name, data.name);
                return item;
            }
        });
    });
</script>