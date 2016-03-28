function formatTransaction(d) {
    var statusRow = '<div class="row">'
	          + '    <div class="col-md-12">'
	          + '        <div class="panel">'
	          + '            <div class="panel-heading">'
                  + '                <h5 class="panel-title">';
    var status = d.status
    if (d.params.refunds !== undefined && d.params.refunds !== null && d.params.refunds !== "") {
	status = d.status + "(refund)";
    }
    if (d.status === "pending") {
       statusRow += '                     <div class="text-pending"><i class="fa fa-hourglass-start"></i><span>' + status + '</span></div>';
    } else if (d.status === "waiting") {
       statusRow += '                     <div class="text-waiting"><i class="fa fa-hourglass-half"></i><span>' + status + '</span></div>';
    }else if (d.status === "processing") {
       statusRow += '                     <div class="text-processing"><img src="/components/Editor-1.5.1/images/ajax-loader-small.gif" class="img-flag"/>&nbsp;<span>' + d.status + '</span></div>';
    } else if (d.status === "completed") {
       statusRow += '                     <div class="text-completed"><i class="fa fa-hourglass-end"></i><span>' + status + '</span></div>';
    } else if (d.status === "cancelled") {
       statusRow += '                     <div class="text-cancelled"><i class="fa fa-hourglass-o"></i><span>' + status + '</span></div>';
    }  
    statusRow += '                        <div class="col-md-12 text-right">';
    if (d.status === "waiting") {
       statusRow += '                        &nbsp;';
    } else {
       statusRow += '                        &nbsp;';
    }
       statusRow += '                     </div>'
                  + '                </h5>'
                  + '            </div>'
                  + '            <div class="panel-body">'
                  + '                <ul class="list-unstyled" style="color: grey;">'
                  + '                    <li class="divider"></li>'
                  + '                    <li>'
                  + '                        <div class="col-md-12 text-center">'
                  + '                            <strong>Created At : </strong>';
       var lang = $("#messages_lang").val();
       if (lang === "en") {
	   statusRow += $.format.date(d.createdAt, "MMM dd yyyy HH:mm:ss");
       } else if (lang === "zh_cn") {
	   statusRow +=  $.format.date(d.createdAt, "yyyy-MM-dd HH:mm:ss");
       }
       statusRow += '                            ,&nbsp;&nbsp;'
                  + '                            <strong>Type : </strong>' + d.type
                  + '                        </div>'
                  + '                    </li>'
                  + '                </ul>'
                  + '            </div>'
                  + '        </div>'
                  + '    </div>'
                  + '</div>';
    
    var contentRow = '<div class="row">'
                   + '    <div class="col-md-6">'
                   + '        <div class="panel">'
                   + '            <div class="panel-heading">'
                   + '                <h5 class="panel-title">';
    if (d.origin.type === 'card') {
	if (d.origin.CardId === null) {
	    contentRow += '                     <div><i class="fa fa-user fa-fw fa-2x"></i> From <strong style="color: orange;">@' + d.origin.username + '(' + d.origin.description + ')</strong></div>';
	} else {
	    if (d.origin.card !== undefined && d.origin.card !== null) {
		contentRow += '                     <div><img src="/images/uphold/currencies/' + d.origin.currency + '.png" class="img-flag" height="9%" width="9%" />  From <strong style="color: orange;">' + d.origin.card.label + '</strong></div>';
	    } else {
		contentRow += '                     <div><img src="/images/uphold/currencies/' + d.origin.currency + '.png" class="img-flag" height="9%" width="9%" />  From <strong style="color: orange;">' + d.origin.CardId + '</strong></div>';
	    }
	}
    }else if (d.origin.type === 'email') {
	contentRow += '                     <div><i class="fa fa-user fa-fw fa-2x"></i> From <strong style="color: orange;">' + d.origin.description + '</strong></div>';
    } else if (d.origin.type === 'external') {
	contentRow += '                     <div><i class="fa fa-connectdevelop fa-fw fa-2x"></i> From <strong style="color: orange;">' + d.origin.description + '</strong></div>';
    }              
        contentRow += '                </h5>'
                    + '            </div>'
                    + '            <div class="panel-body">'
                    + '                <ul class="list-unstyled">'
                    + '                    <li class="divider"></li>'
                    + '                    <li class="text-danger">'
                    + '                        <div class="col-md-6"><strong>Debited</strong></div>'
                    + '                        <div class="col-md-6 text-right"><strong>' + formatAmount('-' + d.origin.amount, d.origin.currency)  + '</strong></div>'
                    + '                    </li>'
                    + '                     <li>'
                    + '                         <ul class="list-unstyled" style="color:grey; font-size: 12px;">'
                    + '                             <li>'
                    + '                                 <div class="col-md-6">'
                    + '                                     <span>Market value</span>'
                    + '                                     <small> (' + formatAmountWithOutPrefix(d.params.rate, d.params.currency) + ')</small>'
                    + '                                 </div>'
                    + '                                 <div class="col-md-6 text-right">'
                    + '                                     <span>' + formatAmountWithOutPrefix('-' + d.origin.base, d.origin.currency) + '</span>'
                    + '                                 </div>'
                    + '                             </li>'
                    + '                             <li>'
                    + '                                  <div class="col-md-6">Bitcoin Network fee</div>'
                    + '                                  <div class="col-md-6 text-right">'
                    + '                                      <span>' + formatAmountWithOutPrefix('-' + d.origin.fee, d.origin.currency) + '</span>'
                    + '                                  </div>'
                    + '                              </li>'
                    + '                              <li>'
                    + '                                  <div class="col-md-6">'
                    + '                                      <span>Commission</span>'
                    + '                                      <small> (' + d.params.margin + '%)</small>'
                    + '                                  </div>'
                    + '                                  <div class="col-md-6 text-right">'
                    + '                                       <span>' + formatAmountWithOutPrefix('-' + d.origin.commission, d.origin.currency) + '</span>'
                    + '                                  </div>'
                    + '                              </li>'
                    + '                          </ul>'
                    + '                      </li>'
                    + '                </ul>'
                    + '            </div>'
                    + '        </div>'
                    + '    </div>'
                    + '    <div class="col-md-6">'
                    + '        <div class="panel">'
                    + '            <div class="panel-heading">'
                    + '                <h5 class="panel-title">';
    if (d.destination.type === 'card') {
        if (d.destination.card !== undefined && d.destination.card !== null) {
            contentRow += '                     <div><img src="/images/uphold/currencies/' + d.destination.currency + '.png" class="img-flag" height="9%" width="9%" />  To <strong style="color: orange;">' + d.destination.card.label + '</strong></div>';
	    } else {
            contentRow += '                     <div><img src="/images/uphold/currencies/' + d.destination.currency + '.png" class="img-flag" height="9%" width="9%" />  To <strong style="color: orange;">' + d.destination.CardId + '</strong></div>';
        }
    } else if (d.destination.type === 'user') {
	    if (d.destination.CardId === null) {
	        contentRow += '                     <div><i class="fa fa-user fa-fw fa-2x"></i> To <strong style="color: orange;">@' + d.destination.username + '(' + d.destination.description + ')</strong></div>';
	    } else {
	        if (d.destination.card !== undefined && d.destination.card !== null) {
	            contentRow += '                 <div><img src="/images/uphold/currencies/' + d.destination.currency + '.png" class="img-flag" height="9%" width="9%" />  To <strong style="color: orange;">' + d.destination.card.label + '</strong></div>';
            } else {
	            contentRow += '                 <div><img src="/images/uphold/currencies/' + d.destination.currency + '.png" class="img-flag" height="9%" width="9%" />  To <strong style="color: orange;">' + d.destination.CardId + '</strong></div>';
	        }
	    }
    } else if (d.destination.type === 'email') {
	    contentRow += '                     <div><i class="fa fa-user fa-fw fa-2x"></i> To <strong style="color: orange;">' + d.destination.description + '</strong></div>';
    } else if (d.destination.type === 'external') {
        contentRow += '                     <div><i class="fa fa-connectdevelop fa-fw fa-2x"></i> To <strong style="color: orange;">' + d.destination.description + '</strong></div>';
    }    
        contentRow += '                </h5>'
                    + '            </div>'
                    + '            <div class="panel-body">'
                    + '                <ul class="list-unstyled">'
                    + '                    <li class="divider"></li>'
                    + '                    <li class="text-success">'
                    + '                        <div class="col-md-6"><strong>Credited</strong></div>'
                    + '                        <div class="col-md-6 text-right"><strong>' + formatAmount(d.destination.amount, d.destination.currency)  + '</strong></div>'
                    + '                    </li>'
                    + '                    <li>'
                    + '                        <ul class="list-unstyled" style="color:grey; font-size: 12px;">'
                    + '                            <li>'
                    + '                                <div class="col-md-6">'
                    + '                                    <span>Market value</span>'
                    + '                                    <small> (' + formatAmountWithOutPrefix(d.params.rate, d.params.currency) + ')</small>'
                    + '                                </div>'
                    + '                                <div class="col-md-6 text-right">'
                    + '                                    <span>' + formatAmountWithOutPrefix(d.destination.base, d.destination.currency) + '</span>'
                    + '                                </div>'
                    + '                            </li>'
                    + '                            <li>'
                    + '                                <div class="col-md-6">'
                    + '                                    <span>Commission</span>'
                    + '                                    <small> (' + d.params.margin + '%)</small>'
                    + '                                </div>'
                    + '                                <div class="col-md-6 text-right">'
                    + '                                       <span>' + formatAmountWithOutPrefix('-' + d.destination.commission, d.destination.currency) + '</span>'
                    + '                                </div>'
                    + '                            </li>'
                    + '                            <li>'
                    + '                                <div class="col-md-6">&nbsp;'
                    + '                                </div>'
                    + '                                <div class="col-md-6 text-right">&nbsp;'
                    + '                                </div>'
                    + '                            </li>'
                    + '                        </ul>'
                    + '                    </li>'
                    + '                </ul>'
                    + '            </div>'
                    + '        </div>'
                    + '    </div>'
                    + '</div>';
    
    var messageRow = '<div class="row">'
                   + '    <div class="col-md-12">'
                   + '        <div class="panel">'
                   + '            <div class="panel-heading">'
                   + '                <h5 class="panel-title">'
                   + '                     <div>Message</div>'
                   + '                </h5>'
                   + '            </div>'
                   + '            <div class="panel-body">'
                   + '                <ul class="list-unstyled" style="color: grey;">'
                   + '                    <li class="divider"></li>'
                   + '                    <li>'
                   + '                        <div class="col-md-12"><textarea style="min-width: 100%; border:0; overflow:hidden; resize:none;" readonly="readonly">' + (d.message === null ? "" : d.message)+ '</textarea></div>'
                   + '                    </li>'
                   + '                </ul>'
                   + '            </div>'
                   + '        </div>'
                   + '    </div>'
                   + '</div>';
    
    return statusRow + contentRow + messageRow;
}

$(document).ready(function() {
    var upholdTransactionsTable = $('#upholdTransactionsTable').DataTable({
        dom: '<"row wrapper"<"col-md-6"l><"col-md-6"f>>rt<"row wrapper"<"col-md-6"i><"col-md-6"p>>',
        processing: true,
        serverSide: false,
        stateSave: false,
        paging: true,
        ajax: {
            url: "/uphold/listUserTransactions",
            type: "GET"
        },
        columnDefs: [{
            targets: [0],
            searchable: false,
            orderable: false
        },
        {
            targets: [7],
            searchable: false,
            orderable: false
        }],
        columns: [{
            "className": "text-center details-control",
            "orderable": false,
            "searchable": false,
            "data": null,
            "defaultContent": ""
        },
        {
            "data": "createdAt",
            "render": function ( data, type, row ) {
        	var lang = $("#messages_lang").val();
                if ( type === 'display' || type === 'filter' ) {
                    if (lang === "en") {
                	return $.format.date(data, "MMM dd yyyy HH:mm:ss");
                    } else if (lang === "zh_cn") {
                	return $.format.date(data, "yyyy-MM-dd HH:mm:ss");
                    }
                }
                
                return $.format.date(data, "yyyyMMddHHmmss");
            },
            "className": "text-center"
        },
        {
            "data": "type",
            "className": "text-center",
            "render": function(data, type, row) {
                if ( row.params.refunds !== undefined && row.params.refunds !== null && row.params.refunds !== "") {
                    return data + "(refund)";
                }
                return data;
            }
        },
        {
            "data": "origin.description",
            "className": "text-center",
            "render": function(data, type, row) {
                if ( row.origin.card !== undefined && row.origin.card !== null && row.origin.card.label !== "") {
                    return row.origin.card.label;
                }
                return data;
            }
        },
        {
            "data": "destination.description",
            "className": "text-center",
            "render": function(data, type, row) {
                if ( row.destination.card !== undefined && row.destination.card !== null && row.destination.card.label !== "") {
                    return row.destination.card.label;
                }
                return data;
            }
        },
        {
            "data": "origin.amount",
            "className": "text-right",
            "render": function(data, type, row) {
                if ( type === 'display' ) {
                    return formatAmount(data, row.origin.currency);
                }
                return data;
            }
        },
        {
            "data": "status",
            "className": "text-center"
        },
        {
            "data": null,
            "className": "text-center",
            "render": function(data, type, row) {
                if (row.status === "waiting") {
                    return '<div class="btn-group"><button class="btn btn-link btn-xs btn-resend"><i class="fa fa-share"></i><span>Resend</span></button><button class="btn btn-link btn-xs btn-cancel"><i class="fa fa-trash-o"></i><span>Cancel</span></button></div>';
                }
                return null;
            }
        },
        {
            "data": "id",
            "visible": false,
            "searchable": false
        }],
        tableTools: {
            sRowSelect: "os"
        }
    }).order([1, 'desc']);
    
    //setTimeout(function(){
        $("#upholdTransactionsTable_wrapper div.dataTables_filter").append("&nbsp;"
            + '<label>Card:'
            + '    <select id="upholdTransactionsSelectCardId" class="form-control input-sm card-selector" style="width: 250px; font-size: 12px;">'
            + '    </select>'
            + '</label>').append("&nbsp;"
            + '<button id="upholdTransactionsRefreshBtn" class="btn btn-default" title="refresh" style="padding: 4px 8px 4px 8px;">'
            + '    <i class="fa fa-refresh"></i>'
            + '</button>');
        
        $("#upholdTransactionsSelectCardId").on("change", function(e) {
            var cardId = $(this).val();
            if (cardId === "" || cardId === "All Cards") {
                $("#upholdTransactionsTable").DataTable().ajax.url("/uphold/listUserTransactions");
            } else {
                $("#upholdTransactionsTable").DataTable().ajax.url("/uphold/listCardTransactions/" + cardId);
            }
            
            $("#upholdTransactionsTable").DataTable().ajax.reload();
        });
        
        $("#upholdTransactionsRefreshBtn").on("click", function(e) {
            $("#upholdTransactionsTable").DataTable().ajax.reload();
        });
        
        $('#upholdTransactionsTable tbody').on('click', 'td.details-control', function() {
            var tr = $(this).closest('tr');
            var row = upholdTransactionsTable.row(tr);
    
            if (row.child.isShown()) {
                row.child.hide();
                tr.removeClass('shown');
            } else {
                row.child(formatTransaction(row.data())).show();
                tr.addClass('shown');
            }
        });
    //}, 1000);

    var transactionResendEditor = new $.fn.dataTable.Editor({
        action: "edit",
        ajax: {
            url: "/uphold/resendTransaction",
            type: "POST"
        },
        table: "#upholdTransactionsTable",
        fields: [{
            label: "Transaction:",
            name: "id"
        },
        {
            label: "Card:",
            name: "origin.CardId"
        },
        {
            label: "Message:",
            name: "message",
            type: "hidden"
        },
        {
            name: "type",
            type: "hidden"
        },
        {
            name: "status",
            type: "hidden"
        },
        {
            name: "createdAt",
            type: "hidden"
        },
        {
            name: "denomination.amount",
            type: "hidden"
        },
        {
            name: "denomination.currency",
            type: "hidden"
        },
        {
            name: "denomination.pair",
            type: "hidden"
        },
        {
            name: "denomination.rate",
            type: "hidden"
        },
        {
            name: "params.currency",
            type: "hidden"
        },
        {
            name: "params.margin",
            type: "hidden"
        },
        {
            name: "params.pair",
            type: "hidden"
        },
        {
            name: "params.progress",
            type: "hidden"
        },
        {
            name: "params.rate",
            type: "hidden"
        },
        {
            name: "params.ttl",
            type: "hidden"
        },
        {
            name: "params.type",
            type: "hidden"
        },
        {
            name: "normalized",
            type: "hidden"
        },
        {
            name: "origin.type",
            type: "hidden"
        },
        {
            name: "origin.card",
            type: "hidden"
        },
        {
            name: "origin.card.id",
            type: "hidden"
        },
        {
            name: "origin.card.label",
            type: "hidden"
        },
        {
            name: "origin.card.currency",
            type: "hidden"
        },
        {
            name: "origin.amount",
            type: "hidden"
        },
        {
            name: "origin.base",
            type: "hidden"
        },
        {
            name: "origin.commission",
            type: "hidden"
        },
        {
            name: "origin.currency",
            type: "hidden"
        },
        {
            name: "origin.description",
            type: "hidden"
        },
        {
            name: "origin.fee",
            type: "hidden"
        },
        {
            name: "origin.rate",
            type: "hidden"
        },
        {
            name: "origin.sources",
            type: "hidden"
        },
        {
            name: "origin.username",
            type: "hidden"
        },
        {
            name: "origin.isMember",
            type: "hidden"
        },
        {
            name: "destination.type",
            type: "hidden"
        },
        {
            name: "destination.CardId",
            type: "hidden"
        },
        {
            name: "destination.card",
            type: "hidden"
        },
        {
            name: "destination.card.id",
            type: "hidden"
        },
        {
            name: "destination.card.label",
            type: "hidden"
        },
        {
            name: "destination.card.currency",
            type: "hidden"
        },
        {
            name: "destination.ContactId",
            type: "hidden"
        },
        {
            name: "destination.address",
            type: "hidden"
        },
        {
            name: "destination.amount",
            type: "hidden"
        },
        {
            name: "destination.base",
            type: "hidden"
        },
        {
            name: "destination.commission",
            type: "hidden"
        },
        {
            name: "destination.currency",
            type: "hidden"
        },
        {
            name: "destination.description",
            type: "hidden"
        },
        {
            name: "destination.fee",
            type: "hidden"
        },
        {
            name: "destination.rate",
            type: "hidden"
        },
        {
            name: "destination.username",
            type: "hidden"
        },
        {
            name: "destination.isMember",
            type: "hidden"
        }]
    });
    
    $('#upholdTransactionsTable tbody').on('click', 'button.btn-resend', function() {
	$(this).attr("disabled","disabled");
	$(this).addClass("disabled");
	var tr = $(this).closest('tr');
        var row = upholdTransactionsTable.row(tr);
        transactionResendEditor.edit(row.index(), false).submit(
            function (e, json, data) {
        	
            },
            function (e, xhr, err, thrown, data) {
                alert(e.error);
            },
            function(e, data, action){
            	
            },
            true
        );
    });
    
    var transactionCancelEditor = new $.fn.dataTable.Editor({
        action: "edit",
        ajax: {
            url: "/uphold/cancelTransaction",
            type: "POST"
        },
        table: "#upholdTransactionsTable",
        fields: [{
            label: "Transaction:",
            name: "id"
        },
        {
            label: "Card:",
            name: "origin.CardId"
        },
        {
            label: "Message:",
            name: "message",
            type: "hidden"
        },
        {
            name: "type",
            type: "hidden"
        },
        {
            name: "status",
            type: "hidden"
        },
        {
            name: "createdAt",
            type: "hidden"
        },
        {
            name: "denomination.amount",
            type: "hidden"
        },
        {
            name: "denomination.currency",
            type: "hidden"
        },
        {
            name: "denomination.pair",
            type: "hidden"
        },
        {
            name: "denomination.rate",
            type: "hidden"
        },
        {
            name: "params.currency",
            type: "hidden"
        },
        {
            name: "params.margin",
            type: "hidden"
        },
        {
            name: "params.pair",
            type: "hidden"
        },
        {
            name: "params.progress",
            type: "hidden"
        },
        {
            name: "params.rate",
            type: "hidden"
        },
        {
            name: "params.ttl",
            type: "hidden"
        },
        {
            name: "params.type",
            type: "hidden"
        },
        {
            name: "normalized",
            type: "hidden"
        },
        {
            name: "origin.type",
            type: "hidden"
        },
        {
            name: "origin.card",
            type: "hidden"
        },
        {
            name: "origin.card.id",
            type: "hidden"
        },
        {
            name: "origin.card.label",
            type: "hidden"
        },
        {
            name: "origin.card.currency",
            type: "hidden"
        },
        {
            name: "origin.amount",
            type: "hidden"
        },
        {
            name: "origin.base",
            type: "hidden"
        },
        {
            name: "origin.commission",
            type: "hidden"
        },
        {
            name: "origin.currency",
            type: "hidden"
        },
        {
            name: "origin.description",
            type: "hidden"
        },
        {
            name: "origin.fee",
            type: "hidden"
        },
        {
            name: "origin.rate",
            type: "hidden"
        },
        {
            name: "origin.sources",
            type: "hidden"
        },
        {
            name: "origin.username",
            type: "hidden"
        },
        {
            name: "origin.isMember",
            type: "hidden"
        },
        {
            name: "destination.type",
            type: "hidden"
        },
        {
            name: "destination.CardId",
            type: "hidden"
        },
        {
            name: "destination.card",
            type: "hidden"
        },
        {
            name: "destination.card.id",
            type: "hidden"
        },
        {
            name: "destination.card.label",
            type: "hidden"
        },
        {
            name: "destination.card.currency",
            type: "hidden"
        },
        {
            name: "destination.ContactId",
            type: "hidden"
        },
        {
            name: "destination.address",
            type: "hidden"
        },
        {
            name: "destination.amount",
            type: "hidden"
        },
        {
            name: "destination.base",
            type: "hidden"
        },
        {
            name: "destination.commission",
            type: "hidden"
        },
        {
            name: "destination.currency",
            type: "hidden"
        },
        {
            name: "destination.description",
            type: "hidden"
        },
        {
            name: "destination.fee",
            type: "hidden"
        },
        {
            name: "destination.rate",
            type: "hidden"
        },
        {
            name: "destination.username",
            type: "hidden"
        },
        {
            name: "destination.isMember",
            type: "hidden"
        }]
    });
    
    $('#upholdTransactionsTable tbody').on('click', 'button.btn-cancel', function() {
	$(this).attr("disabled","disabled");
	$(this).addClass("disabled");
        var tr = $(this).closest('tr');
        var row = upholdTransactionsTable.row(tr);
        transactionCancelEditor.edit(row.index(), false).submit(
            function (e, json, data) {
                $("#upholdTransactionsTable").DataTable().ajax.reload();
            },
            function (e, xhr, err, thrown, data) {
                alert(e.error);
            },
            function(e, data, action){
        	
            },
            true
        );
    });
    
});