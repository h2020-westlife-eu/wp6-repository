import {HttpClient} from 'aurelia-fetch-client';

/* Provides methods to return promise of data from REST Project api*/
export class Ariaapi {
  static inject = [HttpClient];

  constructor(httpclient) {
    this.httpclient=httpclient;
    this.httpclient.configure(config => {
      config
        .rejectErrorResponses()
        .withBaseUrl('https://www.structuralbiology.eu/')
        .withDefaults({
          credentials: 'same-origin',
          headers: {
            'Accept': 'application/json',
            'X-Requested-With': 'Fetch'
          }
        })
    });
    //needs SSO credentials
    this.proposallisturl = "/ws/oauth/proposallist";
    this.proposalurl = "/ws/oauth/proposal";
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
}
