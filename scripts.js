
function resetValues(){
	document.getElementById("msg").innerHTML="Compnent added successfully!";
	document.getElementById("msg1").innerHTML="Next component can now be added.";
	document.getElementById("inputForm").reset();
}
//console.log("hdjhs");
var form;
function validateForm(){
	form=document.getElementById("inputForm");
	//console.log(form.value);
	var component=form.elements[0].value;
	var mva=form.elements[1].value;
	var kva=form.elements[2].value;
	var psi=form.elements[3].value;
	var nsi=form.elements[4].value;
	var zsi=form.elements[5].value;
	console.log(component);
	console.log(mva);
	console.log(kva);
	console.log(psi);
	console.log(nsi);
	console.log(zsi);
}
function printComponents(){
	document.getElementById("opForm").innerHTML="SCMA";
}