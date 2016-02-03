'use strict';
require('./lib/common.js');
function getFormData(params) {
    return $.extend(true, params, require('./lib/formutil.js').getData('testForm'));
}
$('#table').bootstrapTable({ url: 'data.json', sidePagination: 'server', pagination: true, queryParams: getFormData });
$("#test").on('click', function () {
    $('#table').bootstrapTable('selectPage', 1);
});