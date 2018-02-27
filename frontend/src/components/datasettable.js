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
      this.datasets=this.alldatasets;
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
    if (id==0) {
      this.datasets=this.alldatasets;
      return;
    }
    console.log("datasettable.filterProjectDatasets()");
    console.log(this.alldatasets);
    this.datasets=this.alldatasets.filter(i => i.projectId == id);
    console.log(this.datasets)
  }

  filterProjectDatasets(msg){
    this.filterProjectDatasets2(this.pa.getSelectedProject());
  }
}
