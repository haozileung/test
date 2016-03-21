'use strict';
require('./lib/common.js');
function getFormData(params) {
    return $.extend(true, params, require('./lib/formutil.js').getData('searchForm'));
}
$('#table').bootstrapTable({
    url: '/dictionary',
    sidePagination: 'server',
    pagination: true,
    queryParams: getFormData,
    columns: [
    {field: 'code',title: '编码'},
    {field: 'name',title: '名称'},
    {field: 'remarks',title: '备注'}
    ]
});
$("#test").on('click', function () {
    $('#table').bootstrapTable('selectPage', 1);
});