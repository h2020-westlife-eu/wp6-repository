import {HttpClient} from 'aurelia-fetch-client';


/* Provides methods to return promise of data from REST Project api*/
export class Ariaapi {
  static inject = [HttpClient];

  constructor(httpclient) {
    this.httpclient=httpclient;
    this.httpclient.configure(config => {
      config
        .rejectErrorResponses()
        .withDefaults({
          credentials: 'same-origin',
          headers: {
            'Accept': 'application/json',
            'X-Requested-With': 'Fetch'
          }
        })
    });
    //needs SSO credentials
    this.proposallisturl = "https://www.structuralbiology.eu/ws/oauth/proposallist";
    this.proposalurl = "https://www.structuralbiology.eu/ws/oauth/proposal";
    this.accesstokenserviceurl="/ariademo/accessToken.php";
  }

  attached() {
    console.log('ariaapi.attached()');
  }

  created() {
    console.log('ariaapi.created()');
  }

  getProposallist() {
    //if the projects is already fetched - returns it, otherwise fetch
   return this.httpclient.fetch(this.proposallisturl)
        .then(response => response.json())
        .then(data => {
          this.proposallist= data
          return data
        })
        .catch(error => {
          console.log(error);
        });
  }

  getProposal() {
      return this.httpclient.fetch(this.proposalurl)
        .then(response => response.json())
        .then(data => {
          this.proposal=data;
          return data;
        })
        .catch(error => {
          console.log(error);
        });
  }

  getAriaLink() {
    return this.httpclient.fetch(this.accesstokenserviceurl)
      .then(response => response.json())
      .then(data => {
        return data;
      })
      .catch(error => {
        console.log(error);
      });
  }

  getAccessToken(code,state){
    console.log("Ariaapi.getAccessToken()");
    return this.httpclient.fetch(this.accesstokenserviceurl+"?code="+code+"&state="+state)
      .then(response => response.json())
      .then(data => {
        console.log("ariaapi.getaccesstoken() returned:");
        console.log(data);
        return data;
      })
      .catch(error => {
        console.log(error);
      });
  }

}
