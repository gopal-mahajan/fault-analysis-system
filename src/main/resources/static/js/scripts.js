var form;
var formArray=new Array();
var formObject=new Array();
var noOfComponents=0;//To keep count of the number of elements added
var headings=["Component","Connection Type","MVA Rating","KV Rating","Positive Sequence","Negative Sequence","Zero Sequence","Base"];
formArray.push(headings);

$(document).ready(function(){

         $(function(){
            $("#generatorInputForm").submit(function(event){
                valGenDetails();
                var mva=form.elements[0].value;
                var kva=form.elements[1].value;
                var psi=form.elements[2].value;
                var nsi=form.elements[3].value;
                var zsi=form.elements[4].value;
                var component=form.elements[5].value;
                console.log(component, mva, kva, psi, nsi, zsi);
                event.preventDefault();
        var path = "/addPowerSystem?isBASE="+component+
         "&kvRating="+kva+"&mvaRating="+mva+"&negativeSequenceImpedanceInPerUnit="+nsi+
         "&positiveSequenceImpedanceInPerUnit="+psi+
         "&powerSystemName=G1&powerSystemType=GENERATOR&zeroSequenceImpedanceInPerUnit="+zsi;
        console.log(path);
              $.ajax({
                      method: 'POST',
                      url: path,
                      data : $('#generatorInputForm').serialize(),
                      success: function(data){
                          console.log(data);
                          alert(data);

                      },
                      error: function(xhr, desc, err){
                          console.log(err);
                      }
                  });
            });

            $("#motorInputForm").submit(function(event){
                            valMotDetails();
                            var mva=form.elements[0].value;
                            var kva=form.elements[1].value;
                            var psi=form.elements[2].value;
                            var nsi=form.elements[3].value;
                            var zsi=form.elements[4].value;
                            var component=form.elements[5].value;
                            console.log(component, mva, kva, psi, nsi, zsi);
                            event.preventDefault();
                    var path = "/addPowerSystem?isBASE="+component+
                     "&kvRating="+kva+"&mvaRating="+mva+"&negativeSequenceImpedanceInPerUnit="+nsi+
                     "&positiveSequenceImpedanceInPerUnit="+psi+
                     "&powerSystemName=M1&powerSystemType=MOTOR&zeroSequenceImpedanceInPerUnit="+zsi;
                    console.log(path);
                          $.ajax({
                                  method: 'POST',
                                  url: path,
                                  data : $('#motorInputForm').serialize(),
                                  success: function(data){
                                      console.log(data);
                                      alert(data);
                                  },
                                  error: function(xhr, desc, err){
                                      console.log(err);
                                  }
                              });
                        });

            $("#transmissionLineInput").submit(function(event){
                                        valTlDetails();
                                        var psi=form.elements[0].value;
                                        var nsi=form.elements[1].value;
                                        var zsi=form.elements[2].value;
                                        console.log(psi, nsi, zsi);
                                        event.preventDefault();
                                var path = "/addTransmissionLine?TransmissionLineName=TL"+"&negativeSequenceImpedanceInPerUnit="+nsi
                                 +"&positiveSequenceImpedanceInPerUnit="+psi+"&zeroSequenceImpedanceInPerUnit="+zsi;
                                  console.log(path);
                           $.ajax({
                                              method: 'POST',
                                              url: path,
                                              data : $('#transmissionLineInput').serialize(),
                                              success: function(data){
                                                  console.log(data);
                                                  alert(data);
                                              },
                                              error: function(xhr, desc, err){
                                                  console.log(err);
                                              }
                           });
                         });

            $("#transformerInputForm").submit(function(event){
                          valTrfDetails();
                          var name=form.elements[0].value;
                          var type=form.elements[1].value;
                          var mva=form.elements[2].value;
                          var pkv=form.elements[3].value;
                          var skv=form.elements[4].value;
                          var psi=form.elements[5].value;
                          var nsi=form.elements[6].value;
                          var zsi=form.elements[7].value;
                          console.log(name,type,mva,pkv,skv,psi, nsi, zsi);
                          event.preventDefault();
                          var path="/addTransformer?PrimaryKVRating="+pkv+"&SecondaryKVRating="+skv
                          +"&connectionType="+type+"&mvaRating="+mva+"&negativeSequenceImpedanceInPerUnit="+nsi
                          +"&positiveSequenceImpedanceInPerUnit="+psi+"&transformerName="+name+
                          "&zeroSequenceImpedanceInPerUnit="+zsi;
                    console.log(path);
                   $.ajax({
                                      method: 'POST',
                                      url: path,
                                      data : $('#transformerInputForm').serialize(),
                                      success: function(data){
                                          console.log(data);
                                          alert(data);
                                      },
                                      error: function(xhr, desc, err){
                                          console.log(err);
                                      }
                   });
                 });

                 $("#faultParaForm").submit(function(event){
                          form=document.getElementById("faultParaForm");
                           console.log(form.elements[0].value,form.elements[1].value,form.elements[2].value);
                           var position=form.elements[0].value;
                                var fi=form.elements[1].value;
                                var type=form.elements[2].value;
                                console.log(position,fi,type);
                                event.preventDefault();
                                var path= "/getFaultParameter?faultImpedance="+fi+
                                "&positionOfFault="+position+"&typeOfFault="+type;
                                console.log(path);
                                $.ajax({
                                        url: path,
                                        type: 'GET',
                                        data : $('#faultParaForm').serialize(),
                                        success: function(data){
                                           console.log("Success");
                                           alert(data);
                                        },
                                        error: function(xhr, desc, err){
                                           console.log(err);
                                        }
                                });



                 });

            });
        });

function valGenDetails(){
  form=document.getElementById("generatorInputForm");
  for(var i=0;i<6;i++)
  {

    formObject.push(form.elements[i].value);
  }

  console.log(formObject.length);
  formArray.push(formObject);
  formObject=[];
  console.log(formArray);
}

function valMotDetails(){
  form=document.getElementById("motorInputForm");
  for(var i=0;i<6;i++)
  {
    formObject.push(form.elements[i].value);
  }
  console.log(formObject.length);
  formArray.push(formObject);
  formObject=[];
  console.log(formArray);
}

function valTrfDetails(){
  form=document.getElementById("transformerInputForm");
  for(var i=0;i<=7;i++)
  {
    formObject.push(form.elements[i].value);
  }
  console.log(formObject.length);
  formArray.push(formObject);
  formObject=[];
  console.log(formArray);
}

function valTlDetails(){
  form=document.getElementById("transmissionLineInput");
  for(var i=0;i<3;i++)
  {
    formObject.push(form.elements[i].value);
  }
  console.log(formObject.length);
  formArray.push(formObject);
  formObject=[];
  console.log(formArray);

}

function printComponents(){
  console.log("printComponents called.");
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

}
function changeBase(){
//  var path="";
//    $("#changeBaseForm").submit(function(event){
//                  form=document.getElementById("changeBaseForm");
//                  event.preventDefault();
//                  var path="/changeBase";

//                  $.ajax({
//                          url: "/changeBase",
//                          type: 'GET',
////                          data : $('#changeBaseForm').serialize(),
//                          success: function(){
//                             console.log("Success");
//                             alert("Success");
//                          },
//                          error: function(xhr, desc, err){
//                             console.log(err);
//                          }
//                  });
                  }




