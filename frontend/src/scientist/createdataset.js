import {Selectedproject, Adddataset} from "../components/messages";
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
    this.datasetsummary="spectrum of antidote";
    this.datasetprojectid=null;
    this.submitted=false;
    this.ea.subscribe(Selectedproject,msg =>this.selectProject(msg.project));
  }

  submit(){
    console.log("submitting dataset:"+this.datasetname);
    this.pa.submitDataset({name:this.datasetname,info:this.datasetinfo,summary:this.datasetsummary,projectId:this.datasetprojectid})
      .then(dataset =>{
        this.submitted=true;
        this.submitteditem=dataset;
        this.ea.publish(new Adddataset(dataset));
      })
      .catch(error => {
        console.log(error);
        alert("Error when submitting new dataset:"+error);
      });
    this.datasetname="";
    this.datasetinfo="";
    this.datasetsummary="";
  }

  generate(){
    this.datasetname="Antidote";
    this.datasetinfo=Math.floor((Math.random() * 100) + 1)+" b";
    this.datasetsummary="spectrum of antidote"
  }

  selectProject(project){
    if (project) {
      this.datasetprojectid = project.id;
    } else this.datasetprojectid=null;
  }

  selectDataset(){

  }
}
