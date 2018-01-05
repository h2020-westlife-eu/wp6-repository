import {Webdavresource} from "../components/messages";
import {EventAggregator} from 'aurelia-event-aggregator';
import {ProjectApi} from '../components/projectapi';

//@inject
export class Dashboard {
  static inject = [EventAggregator,ProjectApi];

  constructor(ea,pa) {
    this.ea = ea;
    this.pa=pa;

    this.proposals = [];
    //demo data until backend is implemented
    this.itemsall=[{projectId:"1",date:"06/09/2017",summary:"spectrum of strychnine process with v_noesy_pro.mac (NUTS-Pro) or v_noesy.mac (NUTS-2D)", info:"1.6 Mb",webdavurl:"/files/XufWqKa1/"},
      {projectId:"1",date:"07/09/2017",summary:" spectrum of sucrose (1.3 Mbytes); process with v_ghsqc_pro.mac (NUTS-Pro) or v_ghsqc.mac (NUTS-2D)", info:"1.3 Mb",webdavurl:"/files/XufWqKa2/"},
      {projectId:"2",date:"08/09/2017",summary:"spectrum of strychnine (2.1 Mbytes); process with v_hsqc_pro.mac (NUTS-Pro) or v_hsqc.mac (NUTS-2D).", info:"2.1 Mb",webdavurl:"/files/XufWqKa3/"},
    ]
    this.files=[{name:"sucrose.t1",size:133511,date:"18/06/2001"},
      {name:"hetcor.2d",size:525832,date:"18/06/2001"},
      {name:"menth.c13",size:132108,date:"18/06/2001"},
      {name:"noesy.fid",size:1640436,date:"18/06/2001"},
      {name:"hsqc.fid",size:2098448,date:"18/06/2001"},
      {name:"hmbc.fid",size:2098448,date:"18/06/2001"}];
    this.items = this.itemsall.slice()
    this.showProposals=true;
    this.showDatasets=true;
    this.dataseturl="";
    this.selectedProjectId=0;
  }

  activate(params, routeConfig, navigationInstruction){
    console.log("Visitingscientist activate()")
    console.log(params);
    if (params && params.projectid) {
      console.log(params.projectid);
      this.selectedProjectId = params.projectid;
    }
  }

  attached() {
    //console.log("Dashboard.attached(): pa:")
    //console.log(this.pa);
    this.pa.getProjects().then(data => {
          console.log("attached(), getProjects():")
          //console.log(data);
          this.proposalsall = data;
          this.proposals = this.proposalsall.slice(0,3);
          this.proposalslength= this.proposalsall.length;
          this.showallbutton = this.proposalsall.length > 3;
          this.showmorebutton = true;
          console.log(this.proposals);
          console.log(this.proposalsall)

      });
    //not yet implemented on backend
    this.pa.getDatasets().then(data => {
          //console.log("attahced(), getDatasets():")
          //console.log(data);
          this.itemsall = data;
          if (this.selectedProjectId>0) {
            this.showProposals=false;
            this.items = this.itemsall.filter(filtereditem => filtereditem.projectId == params.projectid);
          }

    });
  }

  switchMoreLessProposals() {
    if (this.showmorebutton) {
      this.proposals = this.proposalsall;
      this.showmorebutton = false;
    } else {
      this.proposals = this.proposalsall.slice(0,3);
      this.showmorebutton = true;
    }
  }

  selectProposal(item) {
    this.selectedProposal=item;
    return true;
    //return false;
  }

  showAllProposals(){
    this.showProposals=true;
    this.showDatasets=true;
    this.items = this.itemsall.slice()
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
    console.log("SelectFile()")
    console.log(file);
    //not yet implemented
  }
}
