<#include "/admin/utils/ui.ftl"/>
<@layout>
<style>
    span {
        margin-left: 0;
        font-size: 12px;
    }

    b {
        margin-top: 2px !important;
    }

    .select2-selection {
        border: 1px solid #ccc;
    }

    .select2-dropdown {
        top: -5px;
    }

    .select2-search__field {
        border-radius: 3px;
    }

    .select2-selection__rendered {
        padding-left: 10px;
    }

    .col-lg-6 input {
        border-radius: 3px !important;
        border: 1px solid #aaa;
    }

    ._form6 select, ._form6 input {
        border-radius: 4px;
        height: 38px;
        font-size: 12px;
        padding: 0 10px;
        color: #999;
    }
</style>

<div class="row">
    <div class="col-md-12 col-sm-12 col-xs-12">
        <div class="x_panel">
            <div class="x_title">
                <h2>菜单新增</h2>
                <ul class="nav navbar-right panel_toolbox">
                </ul>
                <div class="clearfix"></div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="region">
                        <div class="region_header">
                            <#--<h5 class="h5 pull-left">新增</h5>-->
                            <div class="pull-right header_right">
                            <#--<button type="button" data-toggle="block-option" data-action="refresh_toggle" data-action-mode="demo"><i class="fa fa-refresh"></i></button>-->
                            <#--<button type="button" data-toggle="block-option" data-action="fullscreen_toggle"><i class="fa fa-arrows-alt"></i></button>-->
                            </div>
                        </div>

                        <div class="tab-content region_content">
                            <div class="tab-pane active">
                                <div class="block-content">
                                    <input type="hidden" id="selectedReg" value="${authMenu.id}">
                                    <form class="form-builder form-horizontal form-label-left row" name="form-builder"
                                          action="${base}/admin/users/menu/save" method="post">

                                        <div class="form-group ">
                                            <label class="col-lg-2 control-label">上级菜单：</label>
                                            <div class="col-lg-6">
                                                <select name="parent_id" class="form-control"
                                                        id="select-pid">
                                                    <option></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="name" class="col-lg-2 control-label">菜单名称：</label>
                                            <div class="col-lg-6">
                                                <input type="text" class="form-control" placeholder="请输入菜单名称"
                                                       name="name">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="url" class="col-lg-2 control-label">URL：</label>
                                            <div class="col-lg-6">
                                                <input type="text" class="form-control" placeholder="以网站根目录'/'开始"
                                                       name="url">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="permission" class="col-lg-2 control-label">权限点：</label>
                                            <div class="col-lg-6">
                                                <input type="text" class="form-control" placeholder="如admin:user:view"
                                                       name="permission">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="sort" class="col-lg-2 control-label">排序：</label>
                                            <div class="col-lg-6">
                                                <input type="text" class="form-control" placeholder="请输入顺序" name="sort">
                                            </div>
                                        </div>

                                        <div class="form-group col-md-12 col-lg-12">
                                            <input type="hidden" id="hide-code" name="parentId"
                                                   value="<#if authMenu??>${authMenu.id}</#if>">
                                            <div class="col-md-10 col-md-offset-2" style="padding-left: 5px">
                                                <button class="btn btn-minw btn-primary ajax-post"
                                                        target-form="form-builder" type="submit">
                                                    提交
                                                </button>
                                                <button class="btn btn-default" type="button"
                                                        onclick="javascript:history.back(-1);return false;">
                                                    返回
                                                </button>
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
</div>

<script>
    $(document).ready(function () {
        $.post("${base}/admin/users/getAllMenus",
                {},
                function (data) {
                    var ul = createTree(data, 0);
                    var selectedReg = $('#selectedReg').val();
                    // $('#select-pid').find('option[value="13"]').attr('selected', true);
                    // $('#select-pid').find('option[value="13"]').val("adsf");
                    $('select[name="parent_id"] option[value="' + selectedReg + '"]').attr('selected', true);
                    $('#select-pid').select2({
                        placeholder: '请选择上级区域',
                        allowClear:true
                    })
                    // console.log(ul)
                    // $('#select-pid').append(ul)
                }, "json");

        //主方法，运用递归实现
        function createTree(jsons, pid) {
            console.log(jsons);
            // console.log(pid);
            var ul = '';
            if (jsons != null) {
                for (var i = 0; i < jsons.length; i++) {
                    if (jsons[i].parentId ==0) {
                        $('#select-pid').append($('<option value=' + jsons[i].id+'>┝' + jsons[i].name + ' </option>'));
                        for (var j = 0; j < jsons.length; j++) {
                            if (jsons[j].parentId == jsons[i].id) {
                                $('#select-pid').append($('<option value=' + jsons[j].id+'>&nbsp;&nbsp;&nbsp;&nbsp;┝' + jsons[j].name + ' </option>'));
                                for (var k = 0; k < jsons.length; k++) {
                                    if (jsons[k].parentId == jsons[j].id) {
                                        $('#select-pid').append($('<option value=' + jsons[k].id+'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┝' + jsons[k].name + ' </option>'));
                                        for (var l = 0; l < jsons.length; l++) {
                                            if (jsons[l].parentId == jsons[k].id) {
                                                $('#select-pid').append($('<option value=' + jsons[l].id+'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┝' + jsons[l].name + ' </option>'));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return ul;
        }
    })

    $('#select-pid').on('change', function () {
        var $tmpid = $(this).find('option:selected').val();
        $('#hide-code').val($tmpid);
    });

</script>
</@layout>