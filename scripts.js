var form;//variable to contain form object from html file
//var component=new Array();//Arrays to store the components of the power system and their properties
//var trf=new Array();//Array to store type of transformer
//var mva=new Array();//MVA Rating
//var kv=new Array();//KV Rating
//var psi=new Array();//Positive Sequesnce Impedence
//var nsi=new Array();//Negitive Sequence Impedence
//var zsi=new Array();//Zero Sequence Impedance
//var base=new Array();//Boolean aray to store base component
var formArray=new Array();
var formObject=new Array();
var noOfComponents=0;//To keep count of the number of elements added
var headings=["Component","Connection Type","MVA Rating","KV Rating","Positive Sequence","Negitive Sequence","Zero Sequence","Base"];
formArray.push(headings);
function resetValues(){
	document.getElementById("msg").innerHTML="Compnent added successfully";
	document.getElementById("msg1").innerHTML="Next component can now be added.";
	document.getElementById("inputForm").reset();
}
//console.log("hdjhs");

function validateForm(){
	form=document.getElementById("inputForm");
	for(var i=0;i<8;i++)
	{	if(form.elements[0].value.match("tf"))
		{
			formObject.push(form.elements[i].value);
		}
		else
		{
			if(i==1)
				formObject.push("null");
			else
				formObject.push(form.elements[i].value);
		}

	}
	console.log(formObject.length);
	formArray.push(formObject);
	formObject=[];
	console.log(formArray);
	//console.log(form.value);
	/*component.push(form.elements[0].value);
	if(component[noOfComponents].match("tf"))
	trf.push(form.elements[1].value);
	else
	trf.push("null");
	mva.push(form.elements[2].value);
	kv.push(form.elements[3].value);
	psi.push(form.elements[4].value);
	nsi.push(form.elements[5].value);
	zsi.push(form.elements[6].value);
	base.push(form.elements[7].value);*/
	/*console.log("Compnent Number"+(noOfComponents+1));
	console.log(component[noOfComponents]);
	console.log(trf[noOfComponents]);
	console.log(mva[noOfComponents]);
	console.log(kv[noOfComponents]);
	console.log(psi[noOfComponents]);
	console.log(nsi[noOfComponents]);
	console.log(zsi[noOfComponents]);
	console.log(base[noOfComponents]);*/
	noOfComponents++;
}
function printComponents(){
	console.log("printComponents called.");
	//document.getElementById("dvTable").innerHTML="CSAM";
	var table=document.createElement("TABLE");
	table.border="1";
	var columnCount=8;

	var row=table.insertRow(-1);
	for (var i = 0; i < columnCount; i++) {
            var headerCell = document.createElement("TH");
            headerCell.innerHTML = formArray[0][i];
            row.appendChild(headerCell);
        }
        
   	 for (var i =1; i < formArray.length; i++) {
            row = table.insertRow(-1);
            for (var j = 0; j < columnCount; j++) {
                var cell = row.insertCell(-1);
                cell.innherHTML=formArray[i][j];
            }
        }
   	    var dvTable = document.getElementById("dvTable");
        dvTable.innerHTML = "";
        dvTable.appendChild(table);
        console.log(formArray);	
        /*var customers = new Array();
        customers.push(["Customer Id", "Name", "Country"]);
        customers.push([1, "John Hammond", "United States"]);
        customers.push([2, "Mudassar Khan", "India"]);
        customers.push([3, "Suzanne Mathews", "France"]);
        customers.push([4, "Robert Schidner", "Russia"]);
 
        //Create a HTML Table element.
        var table = document.createElement("TABLE");
        table.border = "1";
 
        //Get the count of columns.
        var columnCount = customers[0].length;
 
        //Add the header row.
        var row = table.insertRow(-1);
        for (var i = 0; i < columnCount; i++) {
            var headerCell = document.createElement("TH");
            headerCell.innerHTML = customers[0][i];
            row.appendChild(headerCell);
        }
 
        //Add the data rows.
        for (var i = 1; i < customers.length; i++) {
            row = table.insertRow(-1);
            for (var j = 0; j < columnCount; j++) {
                var cell = row.insertCell(-1);
                cell.innerHTML = customers[i][j];
            }
        }
 
        var dvTable = document.getElementById("dvTable");
        dvTable.innerHTML = "";
        dvTable.appendChild(table);*/
	//document.getElementById("opForm").innerHTML="ABCDEFG";
}
