var currencies = [
    { value: 'No data available', data: 'NO DATA' },
  ];


function refrescarListaEventos(){
	$.ajax({
	    url: '/MeetBook/JSON_API.do?method=getEvents',
	    dataType: 'text',
	    async: false,
	   
	}).done(function(data) {
	 currencies = eval(data);
	  //El autocompletado se pone con los datos obtenidos
	  $('#autocomplete').autocomplete({
		  lookup: eval(data),
	    onSelect: function (suggestion) {
	    	
	    	window.location.href = "http://localhost:8080/MeetBook/EventDetail.do?id=" + suggestion.data;
	    	
	     
	    }
	  });
	  
	}).fail(function() {
	    console.log("Error obtaining user data");
	})
}

$(function(){
 
	
	
  // setup autocomplete function pulling from currencies[] array
	 refrescarListaEventos();
	window.setInterval(function(){
		  refrescarListaEventos();
		}, 60000);
 

});

function seleccionarUsuario(){
	  var valor = $('#nickInvitado').val();
	  //En el textarea que procesara el Servlet pasamos el 'valor' que será el nick
	  $('#invitadosNick').val($('#invitadosNick').val() + valor + "\n");
	  //En la lista que se muestra al usuario enseñamos nick y mail
	  $('#listaInvitados').append('<li class="list-group-item">' + $('#nick_mail_invitado').val() + '</li>');
	  console.log("Seleccionado " + valor);
	  $('#autocomplete').val("");
	  $('#botonBuscar').prop('disabled', true);
};

function searchChanged(){
	 setTimeout(function() {
		 console.log("Search changed to" +  $('#autocomplete').val());
		 if(isSearchValid()){
				$('#botonBuscar').prop('disabled', false);
			}
			else{
				$('#botonBuscar').prop('disabled', true);
			}
     },200);
	
	
}
function isSearchValid(){
	 var valor = $('#autocomplete').val();
	
	 var i;
	 var found = false;
	 for (i = 0; i < currencies.length && found == false; i++){
		 var actual = currencies[i];
		 if(actual.value==valor){
			 found = true;
		 }
	 }
	 return found;
}