function majmatch(){
	var url = tableUrl;
//	alert(url);
	url += "&q="+q;
//	alert(url);
	gettable(url,false,"new");
}

function do_action(url){
	freeze(true);
	var oXmlHttp = zXmlHttp.createRequest();
	oXmlHttp.open("get", url, true);
	oXmlHttp.onreadystatechange = function() {
		if (oXmlHttp.readyState == 4) {
			freeze(false);
			if(oXmlHttp.status == 200) {
				if(oXmlHttp.responseText=="OK"){
					alert("ok");
					cacher_matchs();
				}
				else{
					alerter(oXmlHttp.responseText,"red");
					return false;
				}
			} else {
				alerter("Une erreur s'est produite : "+ oXmlHttp.statusText,"red");
				return false;
			}
		}
	};
	oXmlHttp.send(null);
}

function gettable(url,bool,lakel){
//	alert("gettable : "+url);
	wait_table(true);
	var oXmlHttp = zXmlHttp.createRequest();
	oXmlHttp.open("get", url, true);
	oXmlHttp.onreadystatechange = function() {
		if (oXmlHttp.readyState == 4) {
			wait_table(false);
//			alert("status : "+oXmlHttp.status);
			if(oXmlHttp.status == 200) {
				if(bool){
					document.location.href=document.location.href;
				}
				else{
					remplir_table(oXmlHttp.responseText,lakel);
				}
			} else {
				alerter("Une erreur s'est produite : "+ oXmlHttp.statusText,"red");
				return false;
			}
		}
	};
	oXmlHttp.send(null);
}

function remplir_table(txt,lakel){
	var elm = document.getElementById(lakel);
	elm.childNodes[3].innerHTML = txt;
}

function remplir_oppo(txt, id){
	var elm = document.getElementById(id);
	elm.innerHTML = txt;
}

function wait_table(bool){
	if(bool){
		var elm = document.getElementById('alert');
		elm.innerHTML = '<center><img src="public/images/loader.gif" /></center>';
	}else{
		var elm = document.getElementById('alert');
		elm.innerHTML = '<br/>';
	}
}

function alerter(msg,color){
	var elm = document.getElementById('alert');
	elm.innerHTML = "<span class=\""+color+"\">"+msg+"</span>";
}

function freeze(bool){
	alert("freeze");
}

function qchange(zeq){
	var exelm = document.getElementById(q);
	var nelm = document.getElementById(zeq);
	exelm.className = "lien";
	nelm.className = "actuel";
	q = zeq;
}
function hide(bloc){
	var elm = document.getElementById(bloc);
	if(elm != null)
		elm.style.display= elm.style.display=="none"?"":"none";
}
function hideblocs(){
	for(var i=0;i<8;i++){
		if(gbloc!=i){
			hide('bl'+i);
		}
	}
}
function classuntil(nb){
	var url = "gettable.php?q="+q+"&untilday="+nb;
	gettable(url,false,"new");
}
function classuntildate(timestamp){
	var url = "gettable.php?q="+q+"&untildate="+timestamp;
	gettable(url,false,"new");
}
function getOpponents(jr,equipe){
	//wait_table(true);
	var oXmlHttp = zXmlHttp.createRequest();
	oXmlHttp.open("get", "getopponents.php?jr="+jr+"&equipe="+equipe, true);
	oXmlHttp.onreadystatechange = function() {
		if (oXmlHttp.readyState == 4) {
			//wait_table(false);
			if(oXmlHttp.status == 200) {
				//alert("ok3 : "+oXmlHttp.responseText);
				remplir_oppo(oXmlHttp.responseText,"oppo"+jr);
			} else {
				alert("Une erreur s'est produite : "+ oXmlHttp.statusText);
				return false;
			}
		}
	};
	oXmlHttp.send(null);
}
function loader(){
//	alert("loader");
	var url = tableUrl;
//	alert(url);
	url += "&escape=true&q="+q;
//	alert(url);
	gettable(url,false,"actual");
	majmatch();
	//hideblocs();
}
onload=loader;