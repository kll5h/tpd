$(document).ready(function() {
    var cardCreator = new $.fn.dataTable.Editor({
        action: "create",
        ajax: {
            url: "/uphold/createCard",
            type: "POST",
            data: function(card) {
                card.label = $('#create_card_label_id').val();
                card.currency = $('#create_card_currency_id').val();
            }
        },
        table: "#upholdCardsTable",
        i18n: {
            create: {
        	title: '<i class="fa fa-credit-card"></i><span>创建新卡</span>',
            	submit: '创建'
            }
        },
        fields: [{
            id: "create_card_label_id",
            label: "卡名：",
            name: "label"
        },
        {
            id: "create_card_currency_id",
            label: "币种：",
            name: "currency",
            type: "select",
            options: CURRENCY_OPTIONS
        },
        {
            id: "create_card_id_id",
            name: "id",
            type: "hidden"
        },
        {
            id: "create_card_settings_starred_id",
            name: "settings.starred",
            type: "hidden"
        },
        {
            id: "create_card_available_id",
            name: "available",
            type: "hidden"
        },
        {
            id: "create_card_balance_id",
            name: "balance",
            type: "hidden"
        },
        {
            id: "create_card_address_bitcoin_id",
            name: "address.bitcoin",
            type: "hidden"
        }]
    });
    
    cardCreator.on('preCreate', function(e, json, data) {
        var card = json.data;
        data.id = card.id;
        data.settings.starred = card.settings.starred;
        data.available = card.available;
        data.balance = card.balance;
        data.normalized = card.normalized;
        data.address.bitcoin = card.address.bitcoin;
    });
    
    cardCreator.on('open', function(e) {
        var form = $("#create_card_label_id").closest("form");
        form.find(":input").each(function() {
            if (!$(this).hasClass("form-control")) {
                $(this).addClass("form-control");
            }
        });

        if (!$("#create_card_currency_id").hasClass("currency-selector")) {
            $("#create_card_currency_id").addClass("currency-selector");
        }

        $("#create_card_currency_id").select2({
            templateResult: formatSelectCurrency
        });
    });

    var cardBubbleEditor = new $.fn.dataTable.Editor({
        action: "edit",
        ajax: {
            url: "/uphold/updateCardLabel",
            data: function(card) {
                card.label = $('#bubbleEditor_card_label_id').val();
            }
        },
        table: "#upholdCardsTable",
        i18n: {
            edit: {
                button: "编辑",
                title:  "编辑卡名",
                submit: "保存"
            }
        },
        idSrc: "id",
        fields: [{
            id: "bubbleEditor_card_label_id",
            label: "卡名：",
            name: "label"
        },
        {
            name: "id",
            type: "hidden"
        },
        {
            id: "bubbleEditor_card_settings_starred_id",
            name: "settings.starred",
            type: "hidden"
        },
        {
            id: "bubbleEditor_card_currency_id",
            name: "currency",
            type: "hidden"
        },
        {
            id: "bubbleEditor_card_available_id",
            name: "available",
            type: "hidden"
        },
        {
            id: "bubbleEditor_card_balance_id",
            name: "balance",
            type: "hidden"
        },
        {
            id: "bubbleEditor_address_bitcoin_id",
            name: "address.bitcoin",
            type: "hidden"
        }]
    });
    
    cardBubbleEditor.on('preEdit', function(e, json, data) {
        var card = json.data;
        data.id = card.id;
        data.label = card.label;
        data.currency = card.currency;
        data.settings.starred = card.settings.starred;
        data.available = card.available;
        data.balance = card.balance;
        data.normalized = card.normalized;
        data.address.bitcoin = card.address.bitcoin;
    });

    $('#upholdCardsTable').on('click', 'tbody td:nth-child(2)', function(e) {
        cardBubbleEditor.bubble(this);
        if (!$("#bubbleEditor_card_label_id").hasClass("form-control")) {
            $("#bubbleEditor_card_label_id").addClass("form-control");
        }
    });

    var cardInlineEditor = new $.fn.dataTable.Editor({
        action: "edit",
        ajax: {
            url: "/uphold/updateCardStarred",
            data: function(card) {
                card.label = $('#inlineEditor_card_label_id').val();
            }
        },
        table: "#upholdCardsTable",
        idSrc: "id",
        fields: [{
            id: "inlineEditor_card_label_id",
            label: "卡名：",
            name: "label"
        },
        {
            name: "id",
            type: "hidden"
        },
        {
            id: "inlineEditor_card_settings_starred_id",
            name: "settings.starred",
            type: "hidden"
        },
        {
            id: "inlineEditor_card_currency_id",
            name: "currency",
            type: "hidden"
        },
        {
            id: "inlineEditor_card_available_id",
            name: "available",
            type: "hidden"
        },
        {
            id: "inlineEditor_card_balance_id",
            name: "balance",
            type: "hidden"
        },
        {
            id: "inlineEditor_address_bitcoin_id",
            name: "address.bitcoin",
            type: "hidden"
        }]
    });
    
    cardInlineEditor.on('preEdit', function(e, json, data) {
        var card = json.data;
        data.id = card.id;
        data.label = card.label;
        data.currency = card.currency;
        data.settings.starred = card.settings.starred;
        data.available = card.available;
        data.balance = card.balance;
        data.normalized = card.normalized;
        data.address.bitcoin = card.address.bitcoin;
    });
    
    $('#upholdCardsTable').on('click', 'tbody td:nth-child(1)', function(e) {
        cardInlineEditor.inline(this);
        cardInlineEditor.submit();
    });
    
    var upholdCardsTable = $('#upholdCardsTable').DataTable({
        dom: '<"row wrapper"<"col-md-7"l><"col-md-3 text-right"f><"col-md-2 text-right"T>>rt<"row wrapper"<"col-md-6"i><"col-md-6"p>>',
        processing: true,
        serverSide: false,
        stateSave: false,
        paging: true,
        language: datatables_language,
        ajax: {
            url: "/uphold/listCards",
            type: "GET",
            async: false
        },
        columnDefs: [{
            targets: [0],
            visible: false,
            searchable: false
        },
        {
            targets: [1],
            visible: false,
            searchable: false
        },
        {
            targets: [7],
            searchable: false,
            orderable: false,
            defaultContent: '<button class="btn btn-link btn-xs create-transaction-btn"><i class="fa fa-exchange"></i><span>发送或转账</span></button>'
        }],
        columns: [{
            "data": "id"
        },
        {
            "data": "currency",
            "className": "text-center"
        },
        {
            "data": "settings.starred",
            "className": "text-center",
            "render": function(data, type, row) {
                if ( type === 'display' ) {
                    if (data) {
                    return '<span style="color:#EC8600;"><i class="fa fa-star"></i></span>';
                    } else {
                    return '<span style="color:grey;"><i class="fa fa-star-o"></i></span>';
                    }
                }
                return data;
            }
        },
        {
            "data": "label",
            "className": "text-left",
            "render": function(data, type, row) {
                if ( type === 'display' ) {
                    return '<span><img src="/images/uphold/currencies/' + row.currency + '.png" height="10%" width="10%" /> ' + data + '</span>';
                }
                return data;
            }
        },
        {
            "data": "address.bitcoin",
            "className": "text-center",
            "render": function(data, type, row) {
                if ( type === 'display' ) {
                    return '<a href="' + $("#blockExplorerUrl").val() + '/address/' + data + '" target="_blank"><span>' + data + '</span></a>';
                }
                return data;
            }
        },
        {
            "data": "available",
            "className": "text-right",
            "render": function(data, type, row) {
                if ( type === 'display' ) {
                    return formatAmount(data, row.currency);
                }
                return data;
            }
        },
        {
            "data": "normalized",
            "className": "text-right",
            "render": function(data, type, row) {
            var normalized = {"available": "0.00", "balance": "0.00", "currency": "USD"};
            if (data !== undefined) {
                var normalized = data[0];
            }
            if ( type === 'display' ) {
                    var bigNumber = new BigNumber(normalized.available);
                    return '<i class="fa fa-usd"></i> ' + bigNumber.toFormat(2) + ' ' + normalized.currency;
                }
                return normalized.available;
            }
        },
        {
            "data": null,
            "className": "text-center",
        }],
        tableTools: {
            sRowSelect: "os",
            aButtons: [{
                sExtends: "editor_create",
                sButtonClass: "btn-primary",
                sButtonText: "新建",
                sToolTip: "新建卡",
                editor: cardCreator,
                fnInit: function ( nButton, oConfig ) {
                    $(nButton).removeClass("btn-default");
                    $(nButton).css("padding", "4px 8px 4px 8px");
                }
            },
            {
                sExtends: "text",
                sButtonText: '<i class="fa fa-refresh"></i>',
                sToolTip: "刷新",
                fnInit: function ( nButton, oConfig ) {
                    $(nButton).css("padding", "4px 8px 4px 8px");
                },
                fnClick: function ( nButton, oConfig, oFlash ) {
                    $("#upholdCardsTable").DataTable().ajax.reload();
                }
            }]
        }
    }).order([2, 'desc']);
    
    var transactionCreator = new $.fn.dataTable.Editor({
        action: "create",
        ajax: {
            url: "/uphold/createTransaction",
            type: "POST",
            data: function(transaction) { 
        	transaction.cardId = $('#create_transaction_card_id').val();
        	var destinationType = $("form input[name='destinationType']:checked").val();
        	transaction.destinationType = destinationType;
        	transaction.destination4Card = $('#create_transaction_destination_card_id').val();
            transaction.destination4Contact = $('#create_transaction_destination_contact_id').val();
            transaction.destination4Address = $('#create_transaction_destination_address_id').val();
        	transaction.amount = $('#create_transaction_amount_id').val();
        	transaction.currency = $('#create_transaction_currency_id').val();
        	transaction.message = $('#create_transaction_message_id').val();
            }
        },
        fields: [{
            id: "create_transaction_destination_type_id",
            label: "目的类型：",
            name: "destinationType",
            type: "radio",
            options: [
                { label: "我的卡", value: "my cards", checked: true },
                { label: "我的联系人", value: "my contacts", checked: false },
                { label: "比特币地址", value: "address", checked: false }
            ]
        },
        {
            id: "create_transaction_destination_card_id",
            label: "我的卡：",
            name: "destination4Card",
            type: "select"
        },
        {
            id: "create_transaction_destination_contact_id",
            label: "我的联系人：",
            name: "destination4Contact",
            type: "select"
        },
        {
            id: "create_transaction_destination_address_id",
            label: "比特币地址：",
            name: "destination4Address"
        },
        {
            id: "create_transaction_amount_id",
            label: "金额：",
            name: "amount"
        },
        {
            id: "create_transaction_currency_id",
            label: "币种：",
            name: "currency",
            type: "select",
            options: CURRENCY_OPTIONS
        },
        {
            id: "create_transaction_message_id",
            label: "消息：",
            name: "message",
            type: 'textarea'
        },
        {
 	   id: "create_transaction_card_id",
 	   name: "cardId"
        }]
    });
    
    $('#upholdCardsTable').on('click', 'button.create-transaction-btn', function (e) {
        var tr = $(this).closest('tr');
        var row = upholdCardsTable.row(tr);
        var card = row.data();
        transactionCreator.create('<span><img src="/images/uphold/currencies/' + card.currency + '.png" height="6%" width="6%" /> ' + card.label + '</span>', {
            "label" : "创建",
            "fn" : function() {
        	this.submit(function (json) {
        	    var transaction = json.data;
        	    var that = this;
                    $.post("/uphold/commitTransaction", {id: transaction.id, cardId: transaction.origin.CardId, message: transaction.message}).done(function(json) {
                	var table = $('#upholdTransactionsTable').DataTable();
                	if (json.successful) {
                	    table.row.add(json.data).draw();
                	    that.message('<div class="alert alert-success">交易创建成功！</div>');
                    	    setTimeout( function () {
                                that.close();
                            }, 1000);
                	} else {
                	    that.message('<div class="alert alert-danger">交易创建失败！</div>');
                	    that.error(json.error);
                	}
                    });
                }, null, null, false);
             }
        });
        $("#create_transaction_card_id").val(card.id);
    } );
    
    transactionCreator.on('initCreate', function(e) {
	if (!$('input', this.field('amount').node()).hasClass('form-control')) {
	    $('select', this.field('destination4Card').node()).addClass('form-control').addClass('card-selector').css("width", "100%");
	    $('select', this.field('destination4Contact').node()).addClass('form-control').addClass('contact-selector').css("width", "100%");
	    $('input', this.field('destination4Address').node()).addClass('form-control');
	    $('input', this.field('amount').node()).addClass('form-control');
	    $('select', this.field('currency').node()).addClass('form-control').addClass('card-selector').css("width", "100%");
	    $('textarea', this.field('message').node()).addClass('form-control');
	    $('input', this.field('cardId').node()).addClass('form-control');
	}

	var destinationType = this.field('destinationType');
        var destinationTypes = $('input', destinationType.node());
        destinationTypes.click(function(){
            var destination4Card = transactionCreator.field('destination4Card');
            var destination4Contact = transactionCreator.field('destination4Contact');
            var destination4Address = transactionCreator.field('destination4Address');
	            
            destination4Card.hide();
            destination4Contact.hide();
            destination4Address.hide();
	            
            if ($(this).val() === 'my cards') {
                destination4Card.show();
            } else if ($(this).val() === 'my contacts') {
                destination4Contact.show();
            } else if ($(this).val() === 'address') {
                destination4Address.show();
            }
        });
        
        this.field('cardId').hide();
    });
    
    transactionCreator.on('open', function(e) {
	var destinationType = this.field('destinationType');
        var destinationTypes = $('input', destinationType.node());
        $(destinationTypes[0]).trigger("click");
        
        renderSelectCardById("create_transaction_destination_card_id");
        
        renderSelectContactById("create_transaction_destination_contact_id");
        
        renderSelectCurrencyById("create_transaction_currency_id");
    });

    transactionCreator.on('close', function () {
	var destinationType = this.field('destinationType');
        var destinationTypes = $('input', destinationType.node());
        destinationTypes.off("click");
    });
    
});