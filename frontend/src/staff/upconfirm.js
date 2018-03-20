import {ProjectApi} from '../components/projectapi';
import {HttpClient} from 'aurelia-fetch-client';

export class Upconfirm {
  static inject = [ProjectApi,HttpClient]
  constructor (pa,client){
    this.pa=pa;
    this.httpclient=client;
  }

  attached() {
    this.filestoupload= this.pa.filestoupload;
    this.dataset=this.pa.selectedDataset;
  }

  uploadFiles(){
    console.log('upconfirm.uploadFiles()');
    console.log(this.filestoupload);
    console.log(this.dataset);
    let port = (window.location.port == "" || window.location.port=="80" || window.location.port == "443")? "" :":"+window.location.port;
    this.hreffull = window.location.protocol+ '//'+window.location.hostname+port+this.dataset.href;
    for (let file of this.filestoupload) {
      this.httpclient.fetch(this.dataset.webdavurl+file.name, {method:"PUT",body:file}).then(response =>
      {
        console.log("fetch ok:",response);
      }).catch(error => {
        console.log("fetch error:",error);
        }
      );
    }
    //this.pa.filestoupload -> webdav this.pa.dataset
  }


}
