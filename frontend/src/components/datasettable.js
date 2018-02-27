import {ProjectApi} from '../components/projectapi';
import {EventAggregator} from 'aurelia-event-aggregator';
import {Selecteddataset,Preselecteddataset,Preselectedproject,Preselecteddatasets} from "./messages";

export class Datasettable {
  static inject = [ProjectApi,EventAggregator];

  constructor(pa,ea) {
    this.pa=pa;
    this.alldatasets = [];
    //this.showDatasets =true;
    this.ea=ea;
    this.ea.subscribe(Preselecteddataset,msg =>this.filterSelectedDataset(msg.datasetid));
    this.ea.subscribe(Preselecteddatasets,msg =>this.filterProjectDatasets(msg));
  }

  attached() {
    console.log("datasettable attached()");
    this.pa.getDatasets().then(data => {
      this.alldatasets = data;
      this.selectedDatasetId=this.pa.getSelectedDataset();
      if (this.selectedDatasetId>0) this.filterSelectedDataset(this.selectedDatasetId);
    });
  }
/*
  activate(params, routeConfig, navigationInstruction){
    console.log("Visitingscientist activate()")
    if (params && params.datasetid) {
      this.filterSelectedDataset(params.datasetid);
    }
  }
  */

  selectDataset(dataset){
    this.selectedDataset=dataset;
    this.ea.publish(new Selecteddataset(dataset))
    return true;

  }
  deselectProject(){
    this.selectedDataset=null;
    this.ea.publish(new Selecteddataset(null))
    return true;
  }


  filterProjectDatasets2(id) {
    //this.selectedProposal=item;
    //this.selectedDatasetId=id;
    //this.filterMyProject();
    //get the selected dataset
    console.log("filterMyDataset()");
    console.log(this.alldatasets);
    let mydataset= this.alldatasets.filter(i=> i.id == id);
    console.log(mydataset);
    if (mydataset.length>0){
      //get project related to dataset
      this.selectedProjectId=mydataset[0].projectId;
      console.log(this.selectedProjectId);
      //filter datasets of the project
      this.datasets=this.alldatasets.filter(i => i.projectId == this.selectedProjectId);
      console.log("datasets:");
      console.log(this.datasets);
      //this.showProposals=false;
      this.ea.publish(new Preselectedproject(this.selectedProjectId));//this.filterProject();
      //this.selectDataset(mydataset[0]);
      this.selectedDataset=mydataset[0];
    }
  }

  filterProjectDatasets(msg){
    this.filterProjectDatasets2(this.pa.getSelectedProject());
  }
}
