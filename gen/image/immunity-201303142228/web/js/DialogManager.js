var DialogManager = (function(){ 

	function DialogManager(){
	
		this.OpenDialog = null;

	
	} 
	
	DialogManager.prototype.CloseLoading = function() {
	
		this.OpenDialog.dialog('close');
	
	}
	
	DialogManager.prototype.SaveLoading = function(dialog) {
		
		this.OpenDialog = dialog;
	
	}
	
	return new DialogManager(); 
})(); 

