/*! Select2 4.0.3 | https://github.com/select2/select2/blob/master/LICENSE.md */

/*(function () {
    if (jQuery && jQuery.fn && jQuery.fn.select2 && jQuery.fn.select2.amd) var e = jQuery.fn.select2.amd;
    return e.define("select2/i18n/zh-CN", [], function () {
        return {
            errorLoading: function () {
                return "无法载入结果。"
            }, inputTooLong: function (e) {
                var t = e.input.length - e.maximum, n = "请删除" + t + "个字符";
                return n
            }, inputTooShort: function (e) {
                var t = e.minimum - e.input.length, n = "请再输入至少" + t + "个字符";
                return n
            }, loadingMore: function () {
                return "载入更多结果…"
            }, maximumSelected: function (e) {
                var t = "最多只能选择" + e.maximum + "个项目";
                return t
            }, noResults: function () {
                return "未找到结果"
            }, searching: function () {
                return "搜索中…"
            }
        }
    }), {define: e.define, require: e.require}
})();*/
define(function () {
    // Chinese (Simplified)
    return {
        errorLoading: function () {
            return '无法载入结果。';
        },
        inputTooLong: function (args) {
            var overChars = args.input.length - args.maximum;

            var message = '请删除' + overChars + '个字符';

            return message;
        },
        inputTooShort: function (args) {
            var remainingChars = args.minimum - args.input.length;

            var message = '请再输入至少' + remainingChars + '个字符';

            return message;
        },
        loadingMore: function () {
            return '载入更多结果…';
        },
        maximumSelected: function (args) {
            var message = '最多只能选择' + args.maximum + '个项目';

            return message;
        },
        noResults: function () {
            return '未找到结果';
        },
        searching: function () {
            return '搜索中…';
        }
    };
});

