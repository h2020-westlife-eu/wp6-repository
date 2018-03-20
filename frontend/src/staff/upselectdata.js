import {ProjectApi} from "../components/projectapi";

export class Upselectdata {
  static inject = [ProjectApi];

  constructor (pa) {
    this.pa=pa;
    this.filestoupload=[];
    this.filesuploaded=[];
  }

  attached() {
    console.log("Upselectdata.attached()")
    this.filestoupload=[];
    this.filestoupload= this.pa.filestoupload;
  }

  detached() {
    console.log("Upselectdata.dettached()");
    console.log(this.filestoupload);
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
    console.log("appendFiles()",event,this.uploaddir);

    this.filestoupload.unshift(...event.target.files);
    console.log("filestoupload",this.filestoupload);

  }

  appendDir(event) {
    console.log("appendDir",event,this.uploadfiles);

      this.filestoupload.unshift(...event.target.files);

    console.log("filestoupload",this.filestoupload);
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
