/**
 * Javascript con las funciones del chat.
 */

var wsocket;
var serviceLocation = "ws://localhost:8080/MeetBook/chat/";
var $nickName;
var $message;
var $chatWindow;

var room = '';

// Ejecutada al recibirse un mensaje. Lo pinta en pantalla
function onMessageReceived(evt) {
	var msg = JSON.parse(evt.data);
	
	

	var $message = $('<li class="left clearfix"><span class="chat-img pull-left">'+
			
		    '</span>'+
		     '<div class="chat-body clearfix">'+
		      '<div class="header">'+
		       '<small class="pull-right text-muted">'+
		                   '<span class="glyphicon glyphicon-time"></span>' + msg.fecha.substring(0,10) + ' a las ' + msg.fecha.substring(11,16)+ '</small>'+
		            '</div>'+
		            '<strong class="primary-font pull-left">' + msg.remitente +':<br/>  </strong>' +
		            
		            '<div><p class="pull-left">'+ 
		                '<br/>' + msg.contenido +
		            '</p></div>'+
		        '</div>'+
		    '</li>');
	
	
	
	var $messageNew = $('<li>Hola</li>');
	var $messageLine = $('<div class="panel panel-default"><div class="panel-heading"><h3 class="panel-title">' + msg.remitente + 
			'</h3></div><div class="panel-body">' + msg.contenido + '</div></div>');
	
	$chatWindow.append($message);
}

// Ejecutada al enviar un mensaje.
function sendMessage() {
	
	var msg = '{"contenido":"' + $message.val() + '", "remitente":"'
			+ $nickName.val() + '", "fecha":' + JSON.stringify(new Date()) + '}';
	wsocket.send(msg);
	console.log("Fecha de envio: " + JSON.stringify(new Date()));
	$message.val('').focus();
}

// Funcion para conectarse a una sala de servidor.
function connectToChatserver() {

		room = document.getElementById('evId').value;
		wsocket = new WebSocket(serviceLocation + room);
		wsocket.onmessage = onMessageReceived;
		console.log("Connected");

}

// Funcion para dejar la sala.
function leaveRoom() {
	wsocket.close();
	$chatWindow.empty();
	$('.chat-wrapper').hide();
	$('.chat-signin').show();
	$nickName.focus();
}

// Ejecutado al cargarse el documento
$(document).ready(function() {
	$nickName = $('#nickname');
	$message = $('#message');
	$chatWindow = $('#response');
	// $room = $('#room');
	// Automaticamente entramos en la sala
	// todo: mas de una sala
	connectToChatserver();
	

	$('#do-chat').submit(function(evt) {
		evt.preventDefault();
		sendMessage()
	});

	$('#leave-room').click(function() {
		leaveRoom();
	});

}
)
;