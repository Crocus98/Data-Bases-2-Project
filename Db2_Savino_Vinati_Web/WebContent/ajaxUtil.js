function httpRequest(url, data, retManage) {

	// ISTANZIO UN OGGETTO HTTPREQUEST
	var xhr = new XMLHttpRequest();

	// RICEVUTI I DATI CHIAMO LA CALLBACK
	xhr.onreadystatechange = function() {
		if(xhr.readyState == XMLHttpRequest.DONE){
			retManage(xhr);
		}
    };
    
    // CHIEDO UNA CONNESSIONE
    xhr.open("POST", url);
    
    // INVIO I PARAMETRI DI RICHIESTA
    if (data == null) {
    	xhr.send();
    } else {
    	xhr.send(data);
    }
}
