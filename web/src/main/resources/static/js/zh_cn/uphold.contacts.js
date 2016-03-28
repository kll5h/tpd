function formatContact(d) {
    var tableStart = '<table class="table table-hover">';
    var tbodyStart = '<tbody>';
    var trStart = '<tr>';
    
    var nameTd = '<td style="width: 10%;">&nbsp;</td><td style="width: 20%;">全名：</td><td class="text-right" style="width: 70%;">';
    if (d.name !== null) {
	nameTd += d.name;
    }
    nameTd += '</td>';
    
    var firstNameTd = '<td style="width: 10%;">&nbsp;</td><td style="width: 20%;">名字：</td><td class="text-right" style="width: 70%;">';
    if (d.firstName !== null) {
	firstNameTd += d.firstName;
    }
    firstNameTd += '</td>';
    
    var lastNameTd = '<td style="width: 10%;">&nbsp;</td><td style="width: 20%;">姓氏：</td><td class="text-right" style="width: 70%;">';
    if (d.lastName !== null) {
	lastNameTd += d.lastName;
    }
    lastNameTd += '</td>';
    
    var companyTd = '<td style="width: 10%;">&nbsp;</td><td style="width: 20%;">公司：</td><td class="text-right" style="width: 70%;">';
    if (d.company !== null) {
	companyTd += d.company;
    }
    companyTd += '</td>';
    
    var emailsTd = '<td style="width: 10%;">&nbsp;</td><td style="width: 20%;">电子邮箱地址：</td><td class="text-right" style="width: 70%;">';
    if (d.emails !== null && d.emails.length > 0) {
	var emails = d.emails;
	for (var i = 0; i < emails.length; i++) {
	    var email = emails[i];
	    emailsTd += email + '<br />';
	}
    }
    emailsTd += '</td>';
    
    var addressesTd = '<td style="width: 10%;">&nbsp;</td><td style="width: 20%;">比特币地址：</td><td class="text-right" style="width: 70%;">';
    if (d.addresses !== null && d.addresses.length > 0) {
	var addresses = d.addresses;
	for (var i = 0; i < addresses.length; i++) {
	    var address = addresses[i];
	    addressesTd += address + '<br />';
	}
    }
    addressesTd += '</td>';
    
    var trEnd = '</tr>';
    var tbodyEnd = '</tbody>';
    var tableEnd = '</table>';
    
    return  tableStart + tbodyStart + trStart + nameTd + trEnd + trStart + firstNameTd + trEnd + trStart + lastNameTd + trEnd + trStart + companyTd + trEnd + trStart + emailsTd + trEnd + trStart + addressesTd + trEnd + tbodyEnd + tableEnd;
}

$(document).ready(function() {
    var contactCreator = new $.fn.dataTable.Editor({
        action: "create",
        ajax: {
            url: "/uphold/createContact",
            type: "POST",
            data: function(contact) {
        	contact.firstName = $('#create_contact_firstName_id').val();
        	contact.lastName = $('#create_contact_lastName_id').val();
        	contact.company = $('#create_contact_company_id').val();
        	contact.email1 = $('#create_contact_email1_id').val();
        	contact.email2 = $('#create_contact_email2_id').val();
        	contact.email3 = $('#create_contact_email3_id').val();
        	contact.address1 = $('#create_contact_address1_id').val();
        	contact.address2 = $('#create_contact_address2_id').val();
        	contact.address3 = $('#create_contact_address3_id').val();
            }
        },
        table: "#upholdContactsTable",
        i18n: {
            create: {
        	title: '<i class="fa fa-user"></i><span>创建新联系人</span>',
                button: '新建',
                submit: '创建'
            }
        },
        fields: [{
            id: "create_contact_firstName_id",
            label: "名字：",
            name: "firstName",
        },
        {
            id: "create_contact_lastName_id",
            label: "姓氏：",
            name: "lastName"
        },
        {
            id: "create_contact_company_id",
            label: "公司：",
            name: "company"
        },
        {
            id: "create_contact_email1_id",
            label: "电子邮箱地址一：",
            name: "email1"
        },
        {
            id: "create_contact_email2_id",
            label: "电子邮箱地址二：",
            name: "email2"
        },
        {
            id: "create_contact_email3_id",
            label: "电子邮箱地址三：",
            name: "email3"
        },
        {
            id: "create_contact_address1_id",
            label: "比特币地址一：",
            name: "address1"
        },
        {
            id: "create_contact_address2_id",
            label: "比特币地址二：",
            name: "address2"
        },
        {
            id: "create_contact_address3_id",
            label: "比特币地址三：",
            name: "address3"
        }]
    });

    contactCreator.on('preCreate', function(e, json, data) {
        var contact = json.data;
        data.id = contact.id;
        data.name = contact.name;
        data.firstName = contact.firstName;
        data.lastName = contact.lastName;
        data.company = contact.company;
        data.emails = contact.emails;
        data.addresses = contact.addresses;
    });

    contactCreator.on('open', function(e) {
	var form = $("#create_contact_firstName_id").closest("form");
	form.find(":input").each(function() {
	    if (!$(this).hasClass("form-control")) {
		$(this).addClass("form-control");
	    }
	});
    });

    var upholdContactsTable = $('#upholdContactsTable').DataTable({
        dom: '<"row wrapper"<"col-md-7"l><"col-md-3 text-right"f><"col-md-2 text-right"T>>rt<"row wrapper"<"col-md-6"i><"col-md-6"p>>',
        processing: true,
        serverSide: false,
        stateSave: false,
        paging: true,
        language: datatables_language,
        ajax: {
            url: "/uphold/listContacts",
            type: "GET",
            async: false
        },
        columnDefs: [{
            targets: [0],
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
            "data": "name",
            "className": "text-left"
        },
        {
            "data": "firstName",
            "className": "text-left"
        },
        {
            "data": "lastName",
            "className": "text-left"
        },
        {
            "data": "company",
            "className": "text-left"
        },
        {
            "data": "emails",
            "visible": false,
            "searchable": false
        },
        {
            "data": "addresses",
            "visible": false,
            "searchable": false
        },
        {
            "data": "id",
            "visible": false,
            "searchable": false
        }],
        tableTools: {
            sRowSelect: "os",
            aButtons: [{
                sExtends: "editor_create",
                sButtonClass: "btn-primary",
                sToolTip: "新建联系人",
                editor: contactCreator,
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
                    $("#upholdContactsTable").DataTable().ajax.reload();
                }
            }]
        }
    })//.order([1, 'asc']);

    $('#upholdContactsTable tbody').on('click', 'td.details-control',
    function() {
        var tr = $(this).closest('tr');
        var row = upholdContactsTable.row(tr);

        if (row.child.isShown()) {
            row.child.hide();
            tr.removeClass('shown');
        } else {
            row.child(formatContact(row.data())).show();
            tr.addClass('shown');
        }
    });

    
});