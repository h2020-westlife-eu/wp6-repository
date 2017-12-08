import {HttpClient} from 'aurelia-fetch-client';

export class Visitingscientist {
  static inject = [HttpClient];
  constructor(httpclient) {
    this.httpclient=httpclient;
    this.httpclient.configure(config => {
      config
        .withBaseUrl('/restcon/')
        .withDefaults({
          credentials: 'same-origin',
          headers: {
            'Accept': 'application/json',
            'X-Requested-With': 'Fetch'
          }
        })
    });

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
    //demo data until backend is implemented
    this.itemsall=[{projectId:"1",date:"06/09/2017",summary:"spectrum of strychnine process with v_noesy_pro.mac (NUTS-Pro) or v_noesy.mac (NUTS-2D)", info:"1.6 Mb"},
      {projectId:"1",date:"07/09/2017",summary:" spectrum of sucrose (1.3 Mbytes); process with v_ghsqc_pro.mac (NUTS-Pro) or v_ghsqc.mac (NUTS-2D)", info:"1.3 Mb"},
      {projectId:"2",date:"08/09/2017",summary:"spectrum of strychnine (2.1 Mbytes); process with v_hsqc_pro.mac (NUTS-Pro) or v_hsqc.mac (NUTS-2D).", info:"2.1 Mb"},
    ]
    this.items = this.itemsall.slice()
    this.showProposals=true;
    this.showDatasets=true;
  }

  attached() {
    console.log("VisitingScientist atached()")
    this.httpclient.fetch("project")
      .then(response => response.json())
      .then(data => {
        console.log(data);

          this.proposalsall = data;
          this.proposals = this.proposalsall.slice(0,3);
          this.proposalslength= this.proposalsall.length;
          this.showallbutton = this.proposalsall.length > 3;
          this.showmorebutton = true;

      })
      .catch(error => {
        //console.log(error);
          console.log(error);
          alert("Sorry, error:"+error.statusCode+" "+error.message);


      });
    //not yet implemented on backend
    this.httpclient.fetch("data")
      .then(response => response.json())
      .then(data => {
        console.log(data);
          this.itemsall = data
        })
      .catch(error => {
        //console.log(error);
        console.log(error);
        //alert("Sorry, error:"+error.statusCode+" "+error.message);
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
    console.log("SelectedProposal()")
    console.log(item.projectId);

    this.selectedProposal=item;
    this.showProposals=false;
    //now remember all datasets
    //this.allitems = this.items.splice();
    //filter per the selected project
    this.items = this.itemsall.filter(filtereditem => filtereditem.projectId == item.projectId)
  }
  showAllProposals(){
    this.showProposals=true;
    this.showDatasets=true;
    this.items = this.itemsall.slice()
  }

  selectDataset(item) {
    console.log("SelectedDataset()")
    console.log(item);
    this.showDatasets=false;
    this.selectedDataset=item;
  }
}
