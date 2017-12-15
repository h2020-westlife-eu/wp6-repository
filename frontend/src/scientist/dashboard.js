import {HttpClient} from 'aurelia-fetch-client';
import {Webdavresource} from "../components/messages";
import {EventAggregator} from 'aurelia-event-aggregator';


export class Dashboard {
  static inject = [EventAggregator,HttpClient];
  constructor(ea,httpclient) {
    this.ea = ea;
    this.httpclient=httpclient;
    this.httpclient.configure(config => {
      config
        .rejectErrorResponses()
        .withBaseUrl('')
        .withDefaults({
          credentials: 'same-origin',
          headers: {
            'Accept': 'application/json',
            'X-Requested-With': 'Fetch'
          }
        })
    });


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
    this.webdavurl="";
  }

  activate(params, routeConfig, navigationInstruction){
    console.log("Visitingscientist activate()")
    console.log(params);
    console.log(params.type);
    console.log(params.id);
  }

  attached() {
    this.httpclient.fetch("/restcon/project")
      .then(response => response.json())
      .then(data => {
          this.proposalsall = data;
          this.proposals = this.proposalsall.slice(0,3);
          this.proposalslength= this.proposalsall.length;
          this.showallbutton = this.proposalsall.length > 3;
          this.showmorebutton = true;

      })
      .catch(error => {
          console.log(error);
      });
    //not yet implemented on backend
    this.httpclient.fetch("/restcon/data")
      .then(response => response.json())
      .then(data => {
        console.log(data);
          this.itemsall = data
        })
      .catch(error => {
        //console.log(error);
        console.log(error);
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
    this.showProposals=false;
    this.items = this.itemsall.filter(filtereditem => filtereditem.projectId == item.id);
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
    this.webdavurl=item.webdavurl;
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
