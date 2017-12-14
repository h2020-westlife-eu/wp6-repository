
import {EventAggregator} from 'aurelia-event-aggregator';
import {Uploaddir} from '../components/messages';

export class Repositorytovf {
  static inject = [EventAggregator]
  constructor(ea) {
    this.ea = ea;
    console.log("Repositorytovf()");


    this.items=[{name:"sucrose.t1",size:133511,date:"18/06/2001"},
      {name:"hetcor.2d",size:525832,date:"18/06/2001"},
      {name:"menth.c13",size:132108,date:"18/06/2001"},
      {name:"noesy.fid",size:1640436,date:"18/06/2001"},
      {name:"hsqc.fid",size:2098448,date:"18/06/2001"},
      {name:"hmbc.fid",size:2098448,date:"18/06/2001"}];
    this.filestoupload=[];
    this.uploadfiles=[];
    this.uploaddir="";
    this.selectingfiles=true;
    this.ea.subscribe(Uploaddir,msg =>this.setuploaddir(msg.webdavurl))
    this.copyinprogress=false;
  }

  attached() {

  }

  setuploaddir(url) {
    console.log("setuploaddir()");
    console.log(url)
    this.uploaddir=url;
    this.selectedUploadDir = true;
  }

  selectitem(item) {
    console.log("Selected",item)
  }

  submitfiles() {
    this.selectingfiles=false;
  }

  unsubmitfiles(){
    this.selectingfiles=true;
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
  copy(){
    this.copyinprogress=true;
  }

}
