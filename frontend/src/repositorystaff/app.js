import {HttpClient} from 'aurelia-http-client';

export class App {
  static inject = [HttpClient];

constructor(httpclient){
    this.location = window.location.protocol;
    this.islocalhost= this.location.startsWith('http:');
    this.client=httpclient;

    this.items=[{date:"06/09/2017",name:"v_noesy.mac", info:"1.6 Mb"},
      {date:"07/09/2017",name:"v_ghsqc_pro.mac", info:"1.3 Mb"},
      {date:"08/09/2017",name:"sqcstrychnine.sqc", info:"2.1 Mb"},
    ]
  this.visitors=["Tomas Kulhanek","Andrea Giaccheti"];
    this.filestoupload=[];
    this.uploadfiles=[];
    this.uploaddir="";
  }

  selectItem(item) {
    console.log("selected item");
    console.log(item);
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
