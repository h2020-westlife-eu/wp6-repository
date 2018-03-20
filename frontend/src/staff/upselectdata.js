import {ProjectApi} from "../components/projectapi";

export class Upselectdata {
  static inject = [ProjectApi];

  constructor (pa) {
    this.pa=pa;
    this.filestoupload=[];
    this.filesuploaded=[];
  }

  attached() {
    this.filestoupload= this.pa.filestoupload;
  }

  detached() {
    this.pa.filestoupload=this.filestoupload;
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
    console.log("submitUpload()");
    this.filesuploaded.unshift(...this.filestoupload);
    this.filestoupload=[];
  }

  selectitem(item) {
    console.log("Selected",item)
  }

  deleteitem(item){
    let indexremoved = this.filesuploaded.indexOf(item);
    if (indexremoved >=0 ) this.filesuploaded.splice(indexremoved,1);
  }

}
