import {Selecteddataset} from '../components/messages';
import {EventAggregator} from 'aurelia-event-aggregator';
import {Router} from 'aurelia-router';
import {ProjectApi} from '../components/projectapi';

export class Upselectdataset {
  static inject = [EventAggregator,Router,ProjectApi];
  constructor(ea,ro,pa){
    this.ea = ea;
    this.router = ro;
    this.pa= pa;
    this.ea.subscribe(Selecteddataset,msg =>this.selectedDataset(msg.dataset));
  }

  selectedDataset(dataset) {
    console.log('Upselectdataset.selectedDataset()');
    this.pa.selectedDataset=dataset;
    this.router.navigate("upselectdata");
  }

}
