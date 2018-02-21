import {Webdavresource} from "../components/messages";
import {EventAggregator} from 'aurelia-event-aggregator';
import {ProjectApi} from '../components/projectapi';

/* Dashboarddetails shows details of projects/datasets */
export class Createdataset {
  static inject = [EventAggregator, ProjectApi];

  constructor(ea, pa) {
    this.ea = ea;
    this.pa = pa;
    this.datasetname="Antidote";
    this.datasetinfo="0 b";
    this.datasetsummary="spectrum of antidote"
  }

  submit(){
    console.log("submitting dataset:"+this.datasetname);
    this.datasetname="";
    this.datasetinfo="";
    this.datasetsummary="";
    this.pa.submitDataset({name:this.datasetname,info:this.datasetinfo,summary:this.datasetsummary});
  }

  generate(){
    this.datasetname="Antidote";
    this.datasetinfo=Math.floor((Math.random() * 100) + 1)+" b";
    this.datasetsummary="spectrum of antidote"

  }
}
