$(document).ready(function () {
    var url = window.location;
    $('ul.nav a[href="'+ url +'"]').parent().addClass('active');
    $('ul.nav a').filter(function() {
        return this.href == url;
    }).parent().addClass('active');
    
    $("#confirmPassword, #enterPassword").bind("keyup", enableNextButton);
    
    if($('#asset').val()==''){
    	$('input[name=minersFee]').attr('disabled', true);
    }
    
    $('#recTx').DataTable({
        columns: [
                  {  },
                  {  },
                  {
                      render: function ( data, type, row ) {
                	  var lang = $("#messages_lang").val();
                          if ( type === 'display' || type === 'filter' ) {
                              if (lang === "en") {
                          	return $.format.date(data, "MMM dd yyyy HH:mm:ss");
                              } else if (lang === "zh_cn") {
                          	return $.format.date(data, "yyyy-MM-dd HH:mm:ss");
                              }
                          }

                          return $.format.date(data, "yyyyMMddHHmmss");
                      }
                  },
                  {  },
                  {  }
         ]
    }).order([2,'desc']).draw();
    
    $('#sendTx').DataTable({
        columns: [
                  {  },
                  {  },
                  {
                      render: function ( data, type, row ) {
                	  var lang = $("#messages_lang").val();
                          if ( type === 'display' || type === 'filter' ) {
                              if (lang === "en") {
                          	return $.format.date(data, "MMM dd yyyy HH:mm:ss");
                              } else if (lang === "zh_cn") {
                          	return $.format.date(data, "yyyy-MM-dd HH:mm:ss");
                              }
                          }

                          return $.format.date(data, "yyyyMMddHHmmss");
                      }
                  },
                  {  },
                  {  }
         ]
    }).order([2,'desc']).draw();
    
    $("input").each(function() {
    	if(typeof ($(this).attr('ondrop')) == typeof undefined){
    		$(this).attr('ondrop', 'return false;');
    	}
    });

    $('i[id=tooltip]').tooltip({placement: 'right'});
    
    $('div[id=successAlert]').attr('hidden', true);
    
    $('form[id=payAssetIssuanceFee]').submit(function(e){
	  	$('#balanceTabId').tab('show');
    });
    
    $('form[id=confirmTransaction]').submit(function(e){
        e.preventDefault();
        $("#confirmSendTx").attr('disabled', true);
        $.ajax({
            type: 'POST',
            cache: false,
            url: 'https://localhost:8888/wallet/send',
            data: $(this).serialize(),
            success: function(data) {
            	if(data.successful){
            		$("#successAlert").html(data.message);
            		$("#successAlert").attr('hidden', false);
            		$("#errorAlert").addClass("hidden");
            		$("#passwordConfirmationDiv").attr('hidden', true);
            		$("#reviewTransactionInfo").attr('hidden', true);
            		$("#back").addClass("hidden");
            		$("#sentTransactionBack").removeClass("hidden");
            	}else{
            		$("#confirmSendTx").attr('disabled', false);
            		$("#errorAlert").html(data.message);
					$("#errorAlert").removeClass("hidden");
					$("#successAlert").attr('hidden', true);
            	}
            },
            timeout: function() {
                alert('timeout');
            }
        });
    });
    
    if ($("#upholdCardSelectId").length > 0) {
	$("#upholdCardSelectId").select2();
        $("#upholdCardSelectId").change(function() {
            $("#addressTo").val($(this).val());
        });
    }
});

$(function() {
	$('select[id=asset]').change(function() {
		if ($(this).val() == '') {
			$('input[id=minersFee]').val('');
			$('input[id=minersFee]').attr('disabled', true);
			$('p[name=xtcFeeInfo]').attr('hidden', true);
			$( "#asset\\.protocol").attr("value", "");
		}

		for (var i = 0; i < assets.length; i++) {
			if ($("option:selected", this).val() == assets[i].name) {
				$('input[id=minersFee]').val(assets[i].minersFee);
				$('input[id=minersFee]').attr('disabled', false);

				if (assets[i].name == 'XTC') {
					$('p[name=xtcFeeInfo]').removeClass("hidden");
				}else{
					$('p[name=xtcFeeInfo]').addClass("hidden");
				}
			}
		}
	});
});


function clipboard(ev) {
    var bg, cb;
    bg = chrome.extension.getBackgroundPage();
    cb = bg.document.getElementById("cb");
    cb.style.display = "block";
    cb.value = $(ev.target).text();
    cb.select();
    bg.document.execCommand("Copy");
    cb.style.display = "none";
}

function activateButton(element,btnId) {
        $(btnId).prop('disabled', !element.checked);
}
function loginFormSubmit(){
	sendLoginForm();
}
function isBlockchainDownloading() {
	$.get( "/checkBlockchain", function( downloading ) {
		if(downloading==true){
			if($('.alert-danger').length==1){
				$('#block-error').removeClass('hidden');
			}
		} else {
			sendLoginForm();
		}
	});
}

function sendLoginForm() {
	$('#password').val($('#username').val());
	$('form[name=login]').submit();
}

$(function () {
    $("#createNewAccount").click(function() {
        $(this).addClass('disabled');
        $("#sync-status").removeClass("hidden");
    });
    $('#next3stepBtn').prop('disabled', true);
    $('#next4stepBtn').prop('disabled', true);
    $('#lastStepBtn').prop('disabled', true);
    $('#saveAccount').prop('disabled', true);
    
    $("#divCheckPasswordMatch").prop('hidden', true);
    $("#divCheckPasswordComp").prop('hidden', true);

    $('i').tooltip({placement: 'right'});
});

function allowDrop(ev) {
	ev.preventDefault();
}

function drag(ev) {
	ev.dataTransfer.setData("Text", ev.target.id);
}

function drop(ev) {
	ev.preventDefault();
	var data = ev.dataTransfer.getData("Text");
	var address = document.getElementById(data);
	ev.target.value = address.innerHTML;
}

function copyAddress(id, targetID){
	var address =  document.getElementById(id).innerHTML;
	var addressField = document.getElementById(targetID);
	addressField.value = address;
}

function selectFieldToCopyAddress(){
    var $tab = $('#transactionTabContent'), 
    $active = $tab.find('.tab-pane.active'), 
    text = $active.find('i:hidden').text();
    copyAddress("address", text);
}

function checkPasswordMatch() {
    var password = $("#enterPassword").val();
    var confirmPassword = $("#confirmPassword").val();

    if (password != confirmPassword){
    	$("#divCheckPasswordMatch").prop('hidden', false);
        return false;
    }
    else{
    	$("#divCheckPasswordMatch").prop('hidden', true);
    	return true;
    }
}

function checkComplexity() {
    var password = $("#enterPassword").val();
    if(password.length < 5){
    	$("#divCheckPasswordComp").prop('hidden', false);
    	return false;
	}else{
    	$("#divCheckPasswordComp").prop('hidden', true);
    	return true;
    }
}

function enableNextButton(){
	var x = checkPasswordMatch();
	var y = checkComplexity();
	if(x && y){
		$('#lastStepBtn').prop('disabled', false);
	}else{
		$('#lastStepBtn').prop('disabled', true);
	}
}

$(function() { 
	  //for bootstrap 3 use 'shown.bs.tab' instead of 'shown' in the next line
	  $('a[class="tab-1"]').on('shown.bs.tab', function (e) {
	    //save the latest tab; use cookies if you like 'em better:
	    localStorage.setItem('firstLvlTab', $(e.target).attr('id'));
	    localStorage.removeItem('secondLvlTab');
	  });
	  $('a[class="tab-2"]').on('shown.bs.tab', function (e) {
	    localStorage.setItem('secondLvlTab', $(e.target).attr('id'));
	  });

	  var firstTab = localStorage.getItem('firstLvlTab');
	  var secondTab = localStorage.getItem('secondLvlTab');
	  if (firstTab) {
	      $('#'+firstTab).tab('show');
	  }
	  if (secondTab) {
	      $('#'+secondTab).tab('show');
	  }
});

function resetTabs(){
	localStorage.removeItem('firstLvlTab');
	localStorage.removeItem('secondLvlTab');
}

$(function() { 
	$('input[id=callable]').change(function() {
		if($('input[id=callable]').is(':checked')){
			$('div[id=calldate]').removeClass("hidden");
			$('div[id=callprice]').removeClass("hidden");
		}else{
			$('div[id=calldate]').addClass("hidden");
			$('div[id=callprice]').addClass("hidden");
		}
	});
});

$(function() {
	$('select[id=assetCategory]').change(function() {
		$('input[id=otherSubCategoryInput]').addClass("hidden");
		if ($(this).val() != '') {
			var selectId= 'subAssetCategory'+$(this).val();
			$('#subAssetCategory'+$(this).val()).removeClass("hidden");
			$('#defaultsubAssetCategory').addClass("hidden");
			
			for (var i = 0; i < categoryList.length; i++) {
				if(categoryList[i].parent==null && categoryList[i].id!=$(this).val()){
					$('#subAssetCategory'+categoryList[i].id).addClass("hidden");
				}
			}	
		}else{
			for (var i = 0; i < categoryList.length; i++) {
					$('#subAssetCategory'+categoryList[i].id).addClass("hidden");
			}	
			$('#defaultsubAssetCategory').addClass("hidden");
		}
		if ($(this).val() == '0') {
			$('div[id=otherCategoryDiv]').removeClass("hidden");
			$('input[id=otherCategoryInput]').removeClass("hidden");
			
			$('#defaultsubAssetCategory').removeClass("hidden");
		}else{
			$('div[id=otherCategoryDiv]').addClass("hidden");
			$('input[id=otherCategoryInput]').addClass("hidden");
		}
	});
});
$(function() {      
	$("select[id^='subAssetCategory']").change(function() {
		if ($(this).val() == '0') {
			$('div[id=otherSubCategoryDiv]').removeClass("hidden");
			$('input[id=otherSubCategoryInput]').removeClass("hidden");
		}else{
			$('div[id=otherSubCategoryDiv]').addClass("hidden");
			$('input[id=otherSubCategoryInput]').addClass("hidden");
		}
	});
});
/*$(function() {
	$('form[id=assetCreation]').submit(function() {
		if(!$('#defaultsubAssetCategory').is(":visible")){
			$('#defaultsubAssetCategory').remove();
		}
		for (var i = 0; i < categoryList.length; i++) {
			if(!$('#subAssetCategorySelect'+categoryList[i].id).is(":visible")){
				$('#subAssetCategorySelect'+categoryList[i].id).remove();
			}
		}
	    return true; //send
	});
});*/