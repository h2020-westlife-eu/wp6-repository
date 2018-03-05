import {ProjectApi} from '../components/projectapi';

export class Repositorystaff {
  static inject = [ProjectApi];

  constructor(pa) {
    console.log("Repositorystaff()");
    this.visitors = [];//"Tomas Kulhanek","Andrea Giacchieti","Antonio Rosatto"];
    this.datasets=[];
    this.filestoupload=[];
    this.uploadfiles=[];
    this.uploaddir="";
    this.selectinguser=true;
    this.selectedvisitor="";
    this.pa = pa;
  }

  attached() {
    this.pa.getUsers()
      .then(data => { this.visitors = data });
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
    let indexremoved = this.datasets.indexOf(item);
    if (indexremoved >=0 ) this.datasets.splice(indexremoved,1);
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
    console.log("Dropped");
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
    console.log("appendDir");
    console.log(event.target.files);
    this.filestoupload.unshift(...event.target.files)
  }

  submitUpload(){
    //TODO fake implementation
    this.datasets.unshift(...this.filestoupload);
    this.filestoupload=[];
  }

}
