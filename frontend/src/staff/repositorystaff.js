import {HttpClient} from 'aurelia-http-client';

export class Repositorystaff {
  static inject = [HttpClient];
  constructor(httpclient) {
    console.log("Repositorystaff()");
    this.visitors = [];//"Tomas Kulhanek","Andrea Giacchieti","Antonio Rosatto"];
    this.items=[];
    this.filestoupload=[];
    this.uploadfiles=[];
    this.uploaddir="";
    this.selectinguser=true;
    this.selectedvisitor="";
    this.client =httpclient;
    this.serviceurl="/admin/restcon/users"
  }

  attached() {
    this.client.get(this.serviceurl)
      .then(data => {
        //console.log(data);
        if (data.response) {
          this.visitors = JSON.parse(data.response);
        }
      })
      .catch(error => {
        //console.log(error);
        console.log(error);
        //alert("Sorry, error:"+error.statusCode+" "+error.message);
      });
  }

  selectitem(item) {
    console.log("Selected",item)
  }

  selectvisitor(visitor) {
    this.selectinguser=false;
    this.selectedvisitor = visitor;
  }
  deselectvisitor(){
    this.selectinguser=true;
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
