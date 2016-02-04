'use strict';
require('./lib/common.js');
function getFormData(params) {
    return $.extend(true, params, require('./lib/formutil.js').getData('testForm'));
}
$('#table').bootstrapTable({
    sortOrder: 'desc',
    url: 'data.json',
    sidePagination: 'server',
    pagination: true,
    queryParams: getFormData,
    columns: [
        {
            field: 'id',
            title: 'Item ID'
        },
        {
            field: 'name',
            title: 'Item Name'
        },
        {
            field: 'price',
            title: 'Item Price'
        }]
});
$("#test").on('click', function () {
    $('#table').bootstrapTable('selectPage', 1);
});