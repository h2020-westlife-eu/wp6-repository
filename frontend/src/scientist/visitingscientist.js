import {HttpClient} from 'aurelia-http-client';

export class Visitingscientist {
  static inject = [HttpClient];
  constructor(httpclient) {
    this.httpclient=httpclient;
    console.log("VisitingScientist()");
    this.proposals = [];/*[
      {
        "pid": "1582",
        "title": "API Test with Crystallisation and MX beamtime for strychnine",
        "statusID": "5",
        "status": "Approved",
        "submitted": "1454583055",
        "display": "PID: 1582 - API Test with Crystallisation and MX beamtime for strychnine"
      },
      {
        "pid": "1586",
        "title": "API Test with Crystallisation and MX beamtime for acetylcholine",
        "statusID": "3",
        "status": "Pending",
        "submitted": "1454583056",
        "display": "PID: 1586 - API Test with Crystallisation and MX beamtime for acetylcholine"
      }
    ];*/
    this.items=[];/*{date:"06/09/2017",summary:"spectrum of strychnine process with v_noesy_pro.mac (NUTS-Pro) or v_noesy.mac (NUTS-2D)", info:"1.6 Mb"},
      {date:"07/09/2017",summary:" spectrum of sucrose (1.3 Mbytes); process with v_ghsqc_pro.mac (NUTS-Pro) or v_ghsqc.mac (NUTS-2D)", info:"1.3 Mb"},
      {date:"08/09/2017",summary:"spectrum of strychnine (2.1 Mbytes); process with v_hsqc_pro.mac (NUTS-Pro) or v_hsqc.mac (NUTS-2D).", info:"2.1 Mb"},
    ]*/
    this.projectserviceurl="/restcon/project"
    this.nexturl = window.location;
    this.showProposals=true;

  }
  attached() {
    console.log("VisitingScientist atached()")
    console.log(this.nexturl);
    this.httpclient.get(this.projectserviceurl)
      .then(data => {
        console.log(data);
        if (data.response) {
          this.proposalsall = JSON.parse(data.response);
          this.proposals = this.proposalsall.slice(0,3);
          this.proposalslength= this.proposalsall.length;
          this.showallbutton = this.proposalsall.length > 3;
          this.showmorebutton = true;
        }
      })
      .catch(error => {
        //console.log(error);
        if (error.statusCode=401) {
          //this.nexturl = window.location.pathname;

          window.location = "/login?next=" + this.nexturl;
        }
        else {
          console.log(error);
          alert("Sorry, error:"+error.statusCode+" "+error.message);
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
    console.log(item);
    this.selectedProposal=item;
    this.showProposals=false;
  }
  showAllProposals(){
    this.showProposals=true;
  }
}
