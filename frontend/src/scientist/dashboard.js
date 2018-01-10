import {Webdavresource} from "../components/messages";
import {EventAggregator} from 'aurelia-event-aggregator';
import {ProjectApi} from '../components/projectapi';

/* Dashboard receives list of projects and list of datasets, in case the project or dataset is selected either by click or within url it is filtered */
export class Dashboard {
  static inject = [EventAggregator,ProjectApi];

  constructor(ea,pa) {
    this.ea = ea;
    this.pa=pa;

    this.proposals = [];

    //this.alldatasets=[];
    //demo data
    /*[{projectId:"1",date:"06/09/2017",summary:"spectrum of strychnine process with v_noesy_pro.mac (NUTS-Pro) or v_noesy.mac (NUTS-2D)", info:"1.6 Mb",webdavurl:"/files/XufWqKa1/"},
      {projectId:"1",date:"07/09/2017",summary:" spectrum of sucrose (1.3 Mbytes); process with v_ghsqc_pro.mac (NUTS-Pro) or v_ghsqc.mac (NUTS-2D)", info:"1.3 Mb",webdavurl:"/files/XufWqKa2/"},
      {projectId:"2",date:"08/09/2017",summary:"spectrum of strychnine (2.1 Mbytes); process with v_hsqc_pro.mac (NUTS-Pro) or v_hsqc.mac (NUTS-2D).", info:"2.1 Mb",webdavurl:"/files/XufWqKa3/"},
    ];*/
    this.files=[];
    this.datasets = [];
    this.alldatasets = [];
    this.showProposals=true;
    this.showDatasets=true;
    this.dataseturl="";
    this.selectedProjectId=0;
    this.selectedProposal={};
  }

  /* it is triggered in first click on project - url is generated - should perform selectProposal()*/
  activate(params, routeConfig, navigationInstruction){
    //console.log("Visitingscientist activate()")
    if (params && params.projectid) {
      this.selectProposal(params.projectid);
    }
  }

  attached() {
    //console.log("Dashboard.attached(): pa:")
    //console.log(this.pa);
    this.pa.getProjects().then(data => {
          console.log("attached(), getProjects():");
          //console.log(data);
          //this.proposalsall = data;
          this.proposals = data;//this.proposalsall.slice(0,3);
      if (this.selectedProjectId>0) {
        console.log(this.proposals);
        console.log(this.selectedProjectId);
        this.selectedProposals=this.proposals.filter(filtereditem=>filtereditem.id == this.selectedProjectId);
        if (this.selectedProposals.length>0) this.selectedProposal=this.selectedProposals[0];
        console.log(this.selectedProposal);
        this.showProposals=false;
      }
//          console.log(this.proposals);

      });
    //not yet implemented on backend
    this.pa.getDatasets().then(data => {
          //console.log("attahced(), getDatasets():")
          //console.log(data);
          this.alldatasets = data;
          if (this.selectedProjectId>0) {
            this.datasets = this.alldatasets.filter(filtereditem => filtereditem.projectId == this.selectedProjectId);
           }
    });
  }

  selectProposal(item) {
    this.selectedProposal=item;
    console.log("selectProposal()");
    console.log(item.id);
    console.log(this.datasets);
    this.datasets = this.alldatasets.filter(filtereditem => filtereditem.projectid === item.id)
    this.showProposals = false;
    return true;
  }

  deselectProposal() {
    //this.selectedProposal=item;
    this.datasets = this.alldatasets;
    this.showProposals = true;
  }

  selectDataset(item) {
    this.showDatasets=false;
    this.selectedDataset=item;
    //TODO replace URL by the one obtained from API
    this.dataseturl=item.webdavurl;
    this.ea.publish(new Webdavresource(item.webdavurl))

  }

  deselectDataset() {
    this.showDatasets=true;
  }

  selectFile(file){
    console.log("SelectFile()");
    console.log(file);
    //not yet implemented
  }
}
