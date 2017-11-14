import {HttpClient} from 'aurelia-http-client';

export class Repositorystaff {

  constructor() {
    console.log("Repositorystaff()");
    this.visitors = ["Tomas Kulhanek","Andrea Giacchieti","Antonio Rosatto"];
    this.items=[{date:"06/09/2017",summary:"spectrum of strychnine process with v_noesy_pro.mac (NUTS-Pro) or v_noesy.mac (NUTS-2D)", info:"1.6 Mb"},
      {date:"07/09/2017",summary:" spectrum of sucrose (1.3 Mbytes); process with v_ghsqc_pro.mac (NUTS-Pro) or v_ghsqc.mac (NUTS-2D)", info:"1.3 Mb"},
      {date:"08/09/2017",summary:"spectrum of strychnine (2.1 Mbytes); process with v_hsqc_pro.mac (NUTS-Pro) or v_hsqc.mac (NUTS-2D).", info:"2.1 Mb"},
    ]
    this.filestoupload=[];
    this.uploadfiles=[];
    this.uploaddir="";

  }
  attached() {

  }

  selectitem(item) {
    console.log("Selected",item)
  }
  deleteitem(item){
    let indexremoved = this.items.indexOf(item);
    if (indexremoved >=0 ) this.items.splice(indexremoved,1);
  }
  
    selectItemToUpload(item) {
    console.log("selected item");
    console.log(item);
  }

  removeItemToUpload(item){
    console.log("selected item");
    console.log(item);
  let i = this.filestoupload.indexOf(item);
    this.filestoupload.splice(i,1);
  }

  dropped(event){
    console.log("Dropped")
    console.log(event.dataTransfer.files);
    event.stopPropagation();
    event.preventDefault();
    this.filestoupload.unshift(...event.dataTransfer.files);
    return true;
  }

  dragged(event){
    return true;//event.preventDefault(); event.dataTransfer.dropEffect = 'copy';
  }

  appendFiles(event){
    console.log("appendFiles()");
    console.log(event.target.files);
    //this.uploadfiles=event.target.files;
    this.filestoupload.unshift(...event.target.files);
    //else this.filestoupload.unshift(event.target.files);
  }

  appendDir(event) {
    console.log("appendDir")
    console.log(event.target.files);
    this.filestoupload.unshift(...event.target.files)
  }

  submitUpload(){
    //TODO fake implementation
    this.items.unshift(...this.filestoupload);
    this.filestoupload=[];
  }

}
