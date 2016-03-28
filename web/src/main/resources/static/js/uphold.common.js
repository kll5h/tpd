$.ajaxSetup({
    contentType: "application/x-www-form-urlencoded;charset=utf-8",
    complete: function(XMLHttpRequest, textStatus) {
        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
        if (sessionstatus == "timeout") {
            alert("Your session has timed out, please login again!");
            window.location.replace("/logout");
        }
    }
});

function getUser() {
    var user = null;
    
    $.ajax({
        url: "/uphold/getUser",
        async: false
    }).done(function(json) {
	user = json.data;
    });
    
    return user;
}

function getCards() {
    var user = getUser();
    var cards = user.cards
    return cards;
}

var CURRENCY_OPTIONS = [ {
    label : "Bitcoin",
    value : "BTC"
}, {
    label : "Swiss Franc",
    value : "CHF"
}, {
    label : "Chinese Yuan",
    value : "CNY"
}, {
    label : "European Euro",
    value : "EUR"
}, {
    label : "British Pound",
    value : "GBP"
}, {
    label : "Indian Rupee",
    value : "INR"
}, {
    label : "Japanese Yen",
    value : "JPY"
}, {
    label : "Mexican Peso",
    value : "MXN"
}, {
    label : "United States Dollar",
    value : "USD"
},


{
    label : "Emirati Dirham",
    value : "AED"
}, {
    label : "Argentine Peso",
    value : "ARS"
}, {
    label : "Australian Dollar",
    value : "AUD"
}, {
    label : "Brazilian Real",
    value : "BRL"
}, {
    label : "Canadian Dollar",
    value : "CAD"
}, {
    label : "Danish Krone",
    value : "DKK"
}, {
    label : "Hong Kong Dollar",
    value : "HKD"
}, {
    label : "Israeli Shekel",
    value : "ILS"
}, {
    label : "Kenyan Shilling",
    value : "KES"
}, {
    label : "Norwegian Krone",
    value : "NOK"
}, {
    label : "New Zealand Dollar",
    value : "NZD"
}, {
    label : "Philippine Peso",
    value : "PHP"
}, {
    label : "Polish Złoty",
    value : "PLN"
}, {
    label : "Swedish Krona",
    value : "SEK"
}, {
    label : "Singapore Dollar",
    value : "SGD"
},


{
    label : "Ounces of Silver",
    value : "XAG"
}, {
    label : "Ounces of Gold",
    value : "XAU"
}, {
    label : "Ounces of Palladium",
    value : "XPD"
}, {
    label : "Ounces of Platinum",
    value : "XPT"
} ];

function getCardOptions() {
    var options = new Array();
    
    var cards = $('#upholdCardsTable').DataTable().data();
    $.each(cards, function(entryIndex, card){
        var option = { id: card.id, text: card.label, title: card.currency};
        options.push(option);
    });
    
    return options;
}

function getContactOptions() {
    var options = new Array();
    
    var contacts = $('#upholdContactsTable').DataTable().data();
    
    return contacts;
}

function formatSelectCurrency(currency) {
    if (!currency.id) {
        return currency.text;
    }
    var $currency = currency.element.value === "ALL" ? $('<span style="font-size: 12px;">' + currency.element.text + '</span>') : $('<span style="font-size: 12px;"><img src="/images/uphold/currencies/' + currency.element.value + '.png" class="img-flag" height="12%" width="12%" /> ' + currency.element.text + '</span>');
    return $currency;
};

function formatSelectCard(card) {
    if (!card.id) {
        return card.text;
    }
    var $card = card.element.title === "" ? $('<span style="font-size: 12px;">' + card.element.text + '</span>') : $('<span style="font-size: 12px;"><img src="/images/uphold/currencies/' + card.element.title + '.png" class="img-flag" height="12%" width="12%" /> ' + card.element.text + '</span>');
    return $card;
};

function formatSelectCardWithAddress(card) {
    if (!card.id) {
        return card.text;
    }
    var $card = card.element.title === "" ? $('<span style="font-size: 12px;">' + card.element.text + '</span>') : $('<span style="font-size: 12px;"><img src="/images/uphold/currencies/' + card.element.title + '.png" class="img-flag" height="12%" width="12%" /> ' + card.element.text + '</span>');
    return $card;
};

function formatAmount(amount, currency) {
    var bigNumber = new BigNumber(amount);
    
    if (currency === "BTC") {
    	return '<i class="fa fa-btc"></i> ' + bigNumber.toFormat(8) + ' BTC';
    } else if (currency === "CHF") {
    	return 'CHF ' + bigNumber.toFormat(2) + ' Fr.';
    } else if (currency === "CNY") {
    	return '<i class="fa fa-jpy"></i> ' + bigNumber.toFormat(2) + ' CNY';
    } else if (currency === "EUR") {
    	return '<i class="fa fa-eur"></i> ' + bigNumber.toFormat(2) + ' EUR';
    } else if (currency === "GBP") {
    	return '<i class="fa fa-gbp"></i> ' + bigNumber.toFormat(2) + ' GBP';
    } else if (currency === "INR") {
    	return '<i class="fa fa-inr"></i> ' + bigNumber.toFormat(2) + ' INR';
    } else if (currency === "JPY") {
    	return '<i class="fa fa-jpy"></i> ' + bigNumber.toFormat(2) + ' JPY';
    } else if (currency === "MXN") {
    	return '<i class="fa fa-usd"></i> ' + bigNumber.toFormat(2) + ' MXN';
    } else if (currency === "USD") {
    	return '<i class="fa fa-usd"></i> ' + bigNumber.toFormat(2) + ' USD';
    } else if (currency === "AED") {
    	return 'AED ' + bigNumber.toFormat(2) + ' د.إ';
    } else if (currency === "ARS") {
    	return '<i class="fa fa-usd"></i> ' + bigNumber.toFormat(2) + ' ARS';
    } else if (currency === "AUD") {
    	return '<i class="fa fa-usd"></i> ' + bigNumber.toFormat(2) + ' AUD';
    } else if (currency === "BRL") {
    	return 'R<i class="fa fa-usd"></i> ' + bigNumber.toFormat(2) + ' BRL';
    } else if (currency === "CAD") {
    	return '<i class="fa fa-usd"></i> ' + bigNumber.toFormat(2) + ' CAD';
    } else if (currency === "DKK") {
    	return 'DKK ' + bigNumber.toFormat(2) + ' kr.';
    } else if (currency === "HKD") {
    	return '<i class="fa fa-usd"></i> ' + bigNumber.toFormat(2) + ' HKD';
    } else if (currency === "ILS") {
    	return '₪ ' + bigNumber.toFormat(2) + ' ILS';
    } else if (currency === "KES") {
    	return 'KSh ' + bigNumber.toFormat(2) + ' KES';
    } else if (currency === "NOK") {
    	return 'NOK ' + bigNumber.toFormat(2) + ' kr';
    } else if (currency === "NZD") {
    	return '<i class="fa fa-usd"></i> ' + bigNumber.toFormat(2) + ' NZD';
    } else if (currency === "PHP") {
    	return '₱ ' + bigNumber.toFormat(2) + ' PHP';
    } else if (currency === "PLN") {
    	return 'zł ' + bigNumber.toFormat(2) + ' PLN';
    } else if (currency === "SEK") {
    	return 'SEK ' + bigNumber.toFormat(2) + ' kr';
    } else if (currency === "SGD") {
    	return '<i class="fa fa-usd"></i> ' + bigNumber.toFormat(2) + ' SGD';
    } else if (currency === "XAG") {
    	return 'XAG ' + bigNumber.toFormat(8) + ' oz';
    } else if (currency === "XAU") {
    	return 'XAU ' + bigNumber.toFormat(8) + ' oz';
    } else if (currency === "XPD") {
    	return 'XPD ' + bigNumber.toFormat(8) + ' oz';
    } else if (currency === "XPT") {
    	return 'XPT ' + bigNumber.toFormat(8) + ' oz';
    }

    return amount;
}

function formatAmountWithOutPrefix(amount, currency) {
    var bigNumber = new BigNumber(amount);
    
    if (currency === "BTC") {
    	return bigNumber.toFormat(8) + ' BTC';
    } else if (currency === "CHF") {
    	return bigNumber.toFormat(2) + ' Fr.';
    } else if (currency === "CNY") {
    	return bigNumber.toFormat(2) + ' CNY';
    } else if (currency === "EUR") {
    	return bigNumber.toFormat(2) + ' EUR';
    } else if (currency === "GBP") {
    	return bigNumber.toFormat(2) + ' GBP';
    } else if (currency === "INR") {
    	return bigNumber.toFormat(2) + ' INR';
    } else if (currency === "JPY") {
    	return bigNumber.toFormat(2) + ' JPY';
    } else if (currency === "MXN") {
    	return bigNumber.toFormat(2) + ' MXN';
    } else if (currency === "USD") {
    	return bigNumber.toFormat(2) + ' USD';
    } else if (currency === "AED") {
    	return bigNumber.toFormat(2) + ' د.إ';
    } else if (currency === "ARS") {
    	return bigNumber.toFormat(2) + ' ARS';
    } else if (currency === "AUD") {
    	return bigNumber.toFormat(2) + ' AUD';
    } else if (currency === "BRL") {
    	return bigNumber.toFormat(2) + ' BRL';
    } else if (currency === "CAD") {
    	return bigNumber.toFormat(2) + ' CAD';
    } else if (currency === "DKK") {
    	return bigNumber.toFormat(2) + ' kr.';
    } else if (currency === "HKD") {
    	return bigNumber.toFormat(2) + ' HKD';
    } else if (currency === "ILS") {
    	return bigNumber.toFormat(2) + ' ILS';
    } else if (currency === "KES") {
    	return bigNumber.toFormat(2) + ' KES';
    } else if (currency === "NOK") {
    	return bigNumber.toFormat(2) + ' kr';
    } else if (currency === "NZD") {
    	return bigNumber.toFormat(2) + ' NZD';
    } else if (currency === "PHP") {
    	return bigNumber.toFormat(2) + ' PHP';
    } else if (currency === "PLN") {
    	return bigNumber.toFormat(2) + ' PLN';
    } else if (currency === "SEK") {
    	return bigNumber.toFormat(2) + ' kr';
    } else if (currency === "SGD") {
    	return bigNumber.toFormat(2) + ' SGD';
    } else if (currency === "XAG") {
    	return bigNumber.toFormat(8) + ' oz';
    } else if (currency === "XAU") {
    	return bigNumber.toFormat(8) + ' oz';
    } else if (currency === "XPD") {
    	return bigNumber.toFormat(8) + ' oz';
    } else if (currency === "XPT") {
    	return bigNumber.toFormat(8) + ' oz';
    }

    return amount;
}

function renderSelectCurrency() {
    $("select.currency-selector").select2({
        templateResult: formatSelectCurrency
    });
}

function renderSelectCurrencyById(id) {
    if ($("#" + id).hasClass("select2-hidden-accessible")) {
	return;
    }
    $("#" + id).select2({
        templateResult: formatSelectCurrency
    });
}

function renderSelectCard() {
    var cards = $('#upholdCardsTable').DataTable().data();
    var options = new Array();
    options.push({ id: '', text: 'All Cards', title: ''});
    $.each(cards, function(entryIndex, card){
        var option = { id: card.id, text: card.label, title: card.currency};
        options.push(option);
    });
    
    $("select.card-selector").select2({
	data: options,
        templateResult: formatSelectCard
    });
}

function renderSelectCardById(id) {
//    if ($("#" + id).hasClass("select2-hidden-accessible")) {
//	return;
//    }
    
    var cards = $('#upholdCardsTable').DataTable().data();
    var options = new Array();
    $.each(cards, function(entryIndex, card){
        var option = { id: card.id, text: card.label, title: card.currency};
        options.push(option);
    });
    var selector = $("#" + id);
    selector.empty();
    selector.select2({
	data: options,
        templateResult: formatSelectCard
    });
}

function renderSelectContactById(id) {
//    if ($("#" + id).hasClass("select2-hidden-accessible")) {
//	return;
//    }
    
    var contacts = $('#upholdContactsTable').DataTable().data();
    var selector = $("#" + id);
    selector.empty();
    $.each(contacts, function(contactIndex, contact){
	var optgroup = document.createElement('optgroup');  
	optgroup.label = contact.name;
	
	var emails = contact.emails;
        $.each(emails, function(emailIndex, email){
            $(optgroup).append(new Option(email, email));
        });
	
        var addresses = contact.addresses;
        $.each(addresses, function(addressIndex, address){
            $(optgroup).append(new Option(address, address));
        });
	
	selector.append(optgroup);
    });
    
    selector.select2();
}
