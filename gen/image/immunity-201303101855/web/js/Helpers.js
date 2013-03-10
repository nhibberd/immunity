CreateModal = function(message) {

	var $dialog = $('<div></div>')
		.html(message)
		.dialog({
			autoOpen: true,
			title: "Notify",
			minHeight: 200,
			minWidth: 400,
			resizable: false,
			draggable: false,
			modal: true
	});	

}

var TC = ".";

CreateTermsAndConditions = function()
{
    var $dialog = $('<div></div>')
        .html('<center>'+TC+'</center>')
        .dialog({
            resizable: false,
            title: "Terms & Conditions",
            height:400,
            width: 480,
            modal: true,
            draggable: false,
            buttons: {
                "Close": function() {
                    $( this ).dialog( "close" );
                }
            }
        });
}

CreateTermsAndConditionsAccept = function()
{
   var $dialog = $('<div></div>')
       .html('<center>'+TC+'</center>')
       .dialog({
           open: function(event, ui) { $(".ui-dialog-titlebar-close").hide(); },
           resizable: false,
           title: "Terms & Conditions",
           height:400,
           width: 480,
           modal: true,
           draggable: false,
           buttons: {
               "Accept Terms And Conditions": function() {
                   $( this ).dialog( "close" );
               },
               Cancel: function() {
                   $( this ).dialog( "close" );
                   window.location.assign(Application.UrlHead);
               }
           },
           closeOnEscape: false
       });
}

ResizeFooter = function() {

    var excessHeight = $(window).height() - 718;
    if(excessHeight < 50) { excessHeight = 50; }
    var footer = document.getElementById("f");

    // footer.style.height = excessHeight + "px";
    //$(footer).height( ($(window).height() - $('#Banner').height() - $('#MenuHolder').height() - $('#MainWindow').height()) + "px" );

    footer.style.height = "50px";
    $('#MainWindow').height( ($(window).height() - $('#Banner').height() - $('#MenuHolder').height() - $(footer).height() - 60) + "px" );

    var mainChild = $('#MainWindow').children()[0];
    if(mainChild) {
        //Compare ChildHeight and MainWindowHeight
        if($(mainChild).height() > $('#MainWindow').height()) {
            $('#MainWindow').height($(mainChild).height() + 10 + "px");
        }
    }

    var cHeight = parseInt(footer.style.height, 10);
    if(cHeight < 50) {
        footer.style.height = "50px";
    }


    var windowWidth = $(window).width();
    var mHolder =  document.getElementById('aaa');
    if(windowWidth < 1022 && customizeForDevice() == false) {
        document.getElementById("f").style.width = 1022 + "px";
        document.getElementById("Banner").style.width = 1022 + "px";
        $(mHolder).width(1022 + "px");
    }
    else if (!customizeForDevice()) {
        document.getElementById("f").style.width = "100%";
        document.getElementById("Banner").style.width = "100%";
        mHolder.style.width = "100%";
    }

    if(customizeForDevice() == true) {
        document.getElementById("f").style.width = 1022 + "px";
        document.getElementById("Banner").style.width = 1022 + "px";
        $(mHolder).width(1022 + "px");
    }
}

/*** sniff the UA of the client and show hidden div's for that device ***/
customizeForDevice = function(){
    var ua = navigator.userAgent;
    var checker = {
      iphone: ua.match(/(iPhone|iPod|iPad)/),
      blackberry: ua.match(/BlackBerry/),
      android: ua.match(/Android/)
    };
    if (checker.android){
		return true;
    }
    else if (checker.iphone){
        return true;
    }
    else if (checker.blackberry){
        return true;
    }
    else {
		return false;
    }
}



ModalSignup = function(message,id) {

	var $dialog = $('<div></div>')
		.html(message)
		.dialog({
			autoOpen: true,
			title: "<center>Set Client Details</center>",
			minHeight: 350,
			minWidth: 1200,
			resizable: false,
			draggable: false,
			modal: true,
			position: "relative",
			zIndex: 99999
	});	


	var SignupInfo = new UpdateInfo($dialog,id);
	var Div = SignupInfo.Load();
	//Div.style.position = 'relative';
	//Div.style.zIndex = 9999; 
	$dialog.css('position','relative');
	$dialog.css('z-index','9999');
$dialog.css('contenteditable','true');	
	$dialog.append(Div);

}



ApplyContentBg =function(obj) {
    $(obj).addClass("grad");
}

ApplyBg =function(obj, val) {
	obj.style.backgroundColor = val;
}

ApplyContentCurve =function(obj) {

	obj.id = "ContentCurve";

}

CreateModalMessage = function(message,title) {

	var $dialog = $('<div></div>')
		.html(message)
		.dialog({
			autoOpen: true,
			title: title,
			minHeight: 200,
			minWidth: 400,
			resizable: false,
			draggable: false,
			modal: true
		});
	//$dialog.id = "dialog";
	//$dialog.dialog

}		

CreateLoad = function() {

	var $dialog = $('<div></div>')
		.html("<center><img src='Images/ajax-loader.gif' height='20' width='220' /></center>")
		.dialog({
		open: function(event, ui) { 
//hide close button.
$(this).parent().children().children('.ui-dialog-titlebar-close').hide();
$(this).parent().children()[1].style.height = "25px";;
},
			autoOpen: true,
			closeOnEscape: false,
			title: "Loading...",
			maxHeight: 50,
			maxWidth: 80,
			resizable: false,
			draggable: false,
			modal: true
		});

	//create({closure: this, dialog: $dialog}, DialogManager.Save);
	DialogManager.SaveLoading($dialog);
}

ApplyButtonParams =function(obj) {

	var bg = "#286da3"
	var borderRadius = "3px"
	var border = "1px solid #51A0B3"
	obj.id = "Button";
	obj.style.fontSize = "20px";
	obj.style.height = "30px";
	obj.style.cursor = "pointer";
	obj.style.lineHeight = "30px";

	$(obj).mouseleave(this.create(this, ButtonMouseOut));
	$(obj).mouseenter(this.create(this, ButtonMouseOver));

}
ButtonMouseOver = function(e) {
	if($(e.target).get(0).tagName == "B") {
		e.target.parentNode.style.borderColor = "#7AC5CD";
	}
	else {
		e.target.style.borderColor = "#7AC5CD";	
	}
}

ButtonMouseOut = function(e) {

	if($(e.target).get(0).tagName == "B") {
		e.target.parentNode.style.borderColor = "gainsboro";
	}
	else {
		e.target.style.borderColor = "gainsboro";
	}	
}

create = function(obj, func){
    var f = function(){
        var target = arguments.callee.target;
        var func = arguments.callee.func;
        return func.apply(target, arguments);
    };
    
    f.target = obj;
    f.func = func;
    return f;
}

function validEmail(emailAddress) {
    var pattern = new RegExp(/^(("[\w-+\s]+")|([\w-+]+(?:\.[\w-+]+)*)|("[\w-+\s]+")([\w-+]+(?:\.[\w-+]+)*))(@((?:[\w-+]+\.)*\w[\w-+]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][\d]\.|1[\d]{2}\.|[\d]{1,2}\.))((25[0-5]|2[0-4][\d]|1[\d]{2}|[\d]{1,2})\.){2}(25[0-5]|2[0-4][\d]|1[\d]{2}|[\d]{1,2})\]?$)/i);
    return pattern.test(emailAddress);
};