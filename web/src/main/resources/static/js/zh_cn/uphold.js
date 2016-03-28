$(document).ready(function() {
    var upholdTickersTable = $('#upholdTickersTable').DataTable({
        dom: '<"row wrapper"<"col-md-8"l><"toolbar col-md-4 dataTables_filter">>rt<"row wrapper"<"col-md-3"i><"col-md-9"p>>',
        processing: true,
        serverSide: false,
        stateSave: false,
        paging: true,
        language: datatables_language,
        ajax: {
            url: "/uphold/listTickers/ALL",
            type: "GET"
        },
        columns: [{
            "data": "currency",
            "className": "text-center"
        },
        {
            "data": "pair",
            "className": "text-center"
        },
        {
            "data": "ask",
            "className": "text-right"
        },
        {
            "data": "bid",
            "className": "text-right"
        }],
        tableTools: {
            sRowSelect: "os"
        }
    }).order([0, 'asc']);

    var upholdTickersTableToolbar = '<label>币种：'
                                      + '    <select id="upholdTickersSelectCurrencyId" class="form-control currency-selector" style="width: 250px; font-size: 12px;">'
                                      + '        <option value="ALL">全部币种</option>';
    CURRENCY_OPTIONS.forEach(function(currency){  
        upholdTickersTableToolbar += '       <option value="' + currency.value + '">' + currency.label + '</option>';
    });
    upholdTickersTableToolbar += '    </select>'
                                   + '</label>';
    upholdTickersTableToolbar += '&nbsp;'
                                   + '<button id="upholdTickersRefreshBtn" class="btn btn-default" title="刷新" style="padding: 4px 8px 4px 8px;">'
                                   + '     <i class="fa fa-refresh"></i>'
                                   + '</button>';
    
    //setTimeout(function(){
        $("#upholdTickersTable_wrapper div.dataTables_filter").html(upholdTickersTableToolbar);

        $("#upholdTickersSelectCurrencyId").on("change", function(e) {
            var currency = $(this).val();
            $("#upholdTickersTable").DataTable().ajax.url("/uphold/listTickers/" + currency);
            $("#upholdTickersTable").DataTable().ajax.reload();
        });

        $("#upholdTickersRefreshBtn").on("click", function(e) {
            var currency = $("#upholdTickersSelectCurrencyId").val();
            $("#upholdTickersTable").DataTable().ajax.url("/uphold/listTickers/" + currency);
            $("#upholdTickersTable").DataTable().ajax.reload();
        });
    //}, 1000);
    
});