/**
 * 
 */

 function confirmar(idcon){
	 let resposta = confirm("confirma a exclusão deste contato")
	 if(resposta === true){
		window.location.href = "delete?idcon=" + idcon
	 }
 }