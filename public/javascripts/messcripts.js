function addmatch(){
	if(match < 10){
		var elm = document.getElementById("m"+match);
		elm.style.visibility = "";
		preretire_equipe();
		match++;
	}
	else{
		alert("Pas plus de 10 matchs");
	}
}

function delmatch(){
	if(match > 0){
		match--;
		var elm = document.getElementById("m"+match);
		elm.style.visibility = "hidden";
	}
	else{
		alert("Aucun match a retirer");
	}
}

function cacher_match(){
	var elm=null;
	for(var i = 0; i <10 ; i++){
		elm = document.getElementById("m"+i);
		elm.style.visibility = "hidden";
	}
}

function majmatch(){
	var url = tableUrl;
//	alert(url);
	url += "&q="+q;
//	alert(url);
	gettable(url,false,"new");
}

function valmatch(ref,mdp){
	var url = "gettable.php?nbr="+match;
	var elm = null;
	for(var i=0; i<match; i++){
		elm = document.getElementById("m"+i);
		url += "&h"+i+"="+elm.childNodes[1].value+"&sch"+i+"="+elm.childNodes[3].value+
		"&e"+i+"="+elm.childNodes[7].value+"&sce"+i+"="+elm.childNodes[5].value+"&cpe"+i+"="+elm.childNodes[10].checked;
	}
	url += "&val=1&mdp="+mdp+"&q="+q;
	gettable(url,true,"new");
	//document.location.href=document.location.href;
	//ref.parentNode.submit();
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

function preretire_equipe(){
	retire_equipe(match,"h");
	retire_equipe(match,"e");
}

function retire_equipe(id,quoi){
	var elm = document.getElementById('m'+id);
	var eq = elm.childNodes[team[quoi]].value;
	if(eq != " " && eq != ""){
		elm.childNodes[team[quoi]].setAttribute("team",eq);
		var sel = elm.childNodes[7+1-team[quoi]];
		enleve_option(eq,sel);
		for(var i = 0; i<10; i++){
			if(i != id){
				elm = document.getElementById('m'+i);
				enleve_option(eq,elm.childNodes[1]);
				enleve_option(eq,elm.childNodes[7]);
			}
		}
	}
	else{
		elm.childNodes[team[quoi]].setAttribute("team","");
	}
}

function enleve_option(str,elm){
	//if(match > 9) alert("zut2");
	var nstr = "";
	var k = elm.selectedIndex;
	for(var i=0; i<elm.options.length; i++){
		if(elm.options[i].value != str) nstr += "<option>"+elm.options[i].value+"</option>";
		else{
			if(i<=k) k=k==0?0:k-1;
		}
	}
	//nstr += "<option> </option>";
	elm.innerHTML = nstr;
	elm.selectedIndex = k;
	//alert("pause enleve("+str+","+elm.parentNode.id+")");
}

function echange(id,quoi){
	var elm = document.getElementById('m'+id);
	var neq = elm.childNodes[team[quoi]].value;
	//alert("neq=>"+neq+"<");
	var oeq = elm.childNodes[team[quoi]].getAttribute("team");
	retire_equipe(id,quoi);
	if(oeq != " " && oeq != ""){
		var sel = elm.childNodes[7+1-team[quoi]];
		ajoute_option(oeq,sel);
		for(var i = 0; i<10; i++){
			if(i != id){
				elm = document.getElementById('m'+i);
				ajoute_option(oeq,elm.childNodes[1]);
				ajoute_option(oeq,elm.childNodes[7]);
			}
		}
	}
}

function ajoute_option(str,elm){
	var nstr = "";
	var k = elm.selectedIndex;
	//alert("k="+k);
	var i = 0;
	var l = elm.options.length;
	while(i<l && elm.options[i].value <= str){
		if(elm.options[i].value != str){
			nstr += "<option>"+elm.options[i].value+"</option>";
		}
		i++;
	}
	if(i<=k) k++;
	nstr += "<option>"+str+"</option>"
	//alert("new k="+k);
	while(i<l && (elm.options[i].value > str || elm.options[i].value == "" || elm.options[i].value == " ")){ 
		nstr += "<option>"+elm.options[i].value+"</option>";i++;
	}
	elm.innerHTML = nstr;
	elm.selectedIndex = k;
	//elm.options[k].setAttribute("selected","selected");
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