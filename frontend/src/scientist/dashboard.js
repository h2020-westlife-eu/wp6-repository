//import {Ariaapi} from '../components/ariaapi';
import {Ariaapi} from '../components/ariaapixhr';
import {ProjectApi} from "../components/projectapi";
import {Addproject} from "../components/messages";
import {EventAggregator} from 'aurelia-event-aggregator';

const getParams = query => {
  if (!query) {
    return {};
  }

  return (/^[?#]/.test(query) ? query.slice(1) : query)
    .split('&')
    .reduce((params, param) => {
      let [key, value] = param.split('=');
      params[key] = value ? decodeURIComponent(value.replace(/\+/g, ' ')) : '';
      return params;
    }, {});
};


/* Dashboard receives list of projects and list of datasets, in case the project or dataset is selected either by click or within url it is filtered */
export class Dashboard {
  /* Dashboarddetails shows details of projects/datasets */
  static inject = [Ariaapi,ProjectApi,EventAggregator];
  constructor(ariaapi,pa,ea) {
    this.ariaapi = ariaapi;
    this.pa= pa;
    this.ea=ea;
    this.importingaria=false;
    this.importariastatus="";
    this.importariaerror=false;
    this.proposals=[];
    this.selectedProposal=false;
  }
/*
  activate(){
    console.log("dashboard.activated()");
  }
*/
  attached(){
    console.log("dashboard.attached()");
    this.params=getParams(window.location.search.substring(1));
    console.log(this.params);
    this.code=this.params.code;
    this.state=this.params.state;
    if (this.code && this.state) {
      this.importingaria=true;
      //code and state are in url => button (import ARIA proposals) was clicked before and redirected back from ARIA - thus
      //importing the data now
      this.ariaapi.getAccessToken(this.code,this.state).then(accessToken =>{
        console.log("Dashboard.attached() accestoken:");
        console.log(accessToken);
        this.importingaria=false;
        if (accessToken.error_description) {
          this.importariastatus=accessToken.error_description
          this.importariaerror=true;
        } else {
          //use accesstoken to get proposal list
          this.ariaapi.getProposalList().then(list =>{
            console.log("Dashboard.attached().getProposalList():")
            console.log(list);
            this.proposals=list;
          })
            .catch(error =>{
              this.importariastatus=error.statusText;
              this.importariaerror=true;
            })
        }
      });
    }
  }

  selectProposal(p){
//    this.selectedProposal=p;
    this.importingaria=true;
    this.ariaapi.getProposal(p.pid).then(detail =>{
      this.importingaria=false;
      console.log("Dashboard.selectProposal():")
      console.log(detail);
      this.selectedProposal=detail.proposal;
      this.selectedFields= Object
        .keys(this.selectedProposal.fields)
        .sort((a,b) => +a - +b)
        .filter(key => !isNaN(+key))
        .map(key => this.selectedProposal.fields[key]);
    })
      .catch(error =>{
        this.importingaria=false;
        this.importariaerror=true;
        this.importariastatus= error.statusText;
      })
  }

  importProposal(p) {
    let pr={};
    pr.projectName = p.title;
    pr.shareable=p.pid;

    this.importingaria=true;
    this.pa.submitProject(pr).then(response => response.json())
      .then(data =>{
      this.importingaria=false;
      this.ea.publish(new Addproject(data))
    }).catch (error=>{
      this.importingaria=false;
      this.importariaerror=true;
      this.importariastatus= error.statusText;
    });
  }
}

