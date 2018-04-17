<#include "/admin/utils/ui.ftl"/>
<@layout>
<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>菜单管理</h2>
                <div class="clearfix"></div>
            </div>
            <div class="x_content">

                <form action="/admin.php/admin/street_lamp/index.html" method="get">
                    <div class="toolbar-btn-action">
                    <@shiro.hasPermission name="menu:edit">
                        <a title="新增" class="btn btn-primary" href="${base}/admin/users/menu/add"><i
                                class="fa fa-plus-circle"></i> 新增</a>
                    </@shiro.hasPermission>
                        <button title="展开所有节点" type="button" class="btn btn-success" id="expand-all"><i
                                class="fa fa-plus"></i> 展开所有节点
                        </button>
                        <button title="收起所有节点" type="button" class="btn btn-warning" id="collapse-all"><i
                                class="fa fa-minus"></i> 收起所有节点
                        </button>
                    </div>
                </form>

                <div class="dd" id="nestable3">
                </div>

            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    main();

    //主方法，运用递归实现
    function createTree(jsons, pid) {
        if (jsons != null) {
            var ul = '<ol class="dd-list">';
            for (var i = 0; i < jsons.length; i++) {
                if (jsons[i].parentId == pid) {
                    ul += '<li class="dd-item dd3-item" data-id="' + jsons[i].id + '">' +
                            '<div class=\"dd-handle dd3-handle\">Drag</div><div class=\"dd3-content\">' + jsons[i].name +
                            '<div class=\"action\"  id=\"action' + jsons[i].id + '\">' +
                            '</div></div>';
                    ul += createTree(jsons, jsons[i].id);
                }
            }
            ul += "</ol>";
        }
        return ul;
    }

    function main() {
        $.post("${base}/admin/plates/findAll",
                {},
                function (data) {
                    var ul = createTree(data, 0);
                    $(".dd").append(ul);
                    $('.dd').nestable();
                    var jsonData = $('.dd').nestable('serialize');
                    console.log(jsonData);
                    getChildren(jsonData);
                    $('.ajax-get').on('click',function () {
                        var id = $(this).attr('data-id');
                        layer.confirm('确定要执行该操作吗?', {
                            btn: ['确认', '取消']
                        }, function(index, layero){
                            //按钮【按钮一】的回调
                            layer.close(index);
                            $.ajax({
                                url: "/admin/plates/delete",
                                type: "GET",
                                data: {'id': id},
                                success: function () {
                                    layer.load();
                                    layer.msg('删除成功!',{icon: 1});
                                    setTimeout(function () {
                                        window.location.href = "${base}/admin/users/menu";
                                    }, 2000);
                                },
                                error: function () {
                                    layer.msg('网络异常,请稍后重试!',{icon: 2});
                                    layer.close();
                                }
                            });
                        }, function(index, layero){
                            //按钮【按钮二】的回调
                            layer.close();
                        });
                    })
                }, "json");
    }
    // 设置节点操作类型
    function getChildren(jsons) {
        for (var i = 0; i < jsons.length; i++) {
            if (jsons[i].children.length != 0) {
                var htm = '<a href="${base}/admin/plates/add/pid/'+jsons[i].id+'"  data-toggle=\"tooltip\" data-original-title=\"新增\">' +
                        '<i class=\"list-icon fa fa-plus fa-fw\"></i>' +
                        '</a>' +
                        '<a href="${base}/admin/plates/edit?id='+jsons[i].id+'" data-toggle=\"tooltip\" data-original-title=\"编辑\">' +
                        '<i class=\"list-icon fa fa-pencil fa-fw\"></i>' +
                        '</a>' +
                        '<a href="javascript:void(0)" class=\"ajax-get\" data-id=\"'+jsons[i].id+'\" data-toggle=\"tooltip\" data-original-title=\"删除\">' +
                        '<i class=\"list-icon fa fa-times fa-fw\"></i>' +
                        '</a>';
                var count = jsons[i].id;
                $('#action' + count).append(htm);
                getChildren(jsons[i].children);
            } else {
                var htm = '<a href="${base}/admin/plates/edit?id='+jsons[i].id+'" data-toggle=\"tooltip\" data-original-title=\"编辑\">' +
                        '<i class=\"list-icon fa fa-pencil fa-fw\"></i>' +
                        '</a>' +
                        '<a href="javascript:void(0)" class=\"ajax-get\" data-id=\"'+jsons[i].id+'\" data-toggle=\"tooltip\" data-original-title=\"删除\">' +
                        '<i class=\"list-icon fa fa-times fa-fw\"></i>' +
                        '</a>';
                var count = jsons[i].id;
                $('#action' + count).append(htm);
                continue;
            }
        }
    }

    // 初始化节点拖拽
    //.nestable({maxDepth:4})
    $('#nestable3').on('change', function(){
        $('#save').removeAttr("disabled").removeClass('btn-default disabled').addClass('btn-success');
    });

    // 展开所有节点
    $('#expand-all').click(function(){
        $('#nestable3').nestable('expandAll');
    });

    // 收起所有节点
    $('#collapse-all').click(function(){
        $('#nestable3').nestable('collapseAll');
    });
</script>
</@layout>