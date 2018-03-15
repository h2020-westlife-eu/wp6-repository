//import {HttpClient,json} from 'aurelia-fetch-client';
import {HttpClient} from 'aurelia-http-client';

/* Provides methods to return promise of data from REST Project api*/
export class Ariaapi {
  static inject = [HttpClient];

  constructor(httpclient) {
    this.httpclient=httpclient;
    /*this.httpclient.configure(config=> {
      config.withHeader('Accept', 'application/json');
      config.withHeader('Content-Type', 'application/json');
    });*/

    //needs SSO credentials
    this.proposallisturl = "https://www.structuralbiology.eu/ws/oauth/proposallist";
    this.proposalurl = "https://www.structuralbiology.eu/ws/oauth/proposal";
    this.accesstokenserviceurl="/ariademo/accessToken.php";
    this.accesstoken=null;
  }

  attached() {
    console.log('ariaapi.attached()');
  }

  created() {
    console.log('ariaapi.created()');
  }

  getProposal(pid) {
//    return this.httpclient.fetch(this.proposalurl)
      return this.httpclient.get(this.proposalurl+"?access_token="+this.accesstoken.access_token+"&aria_show_all=true&aria_pid="+pid)
        .then(response => response.json())
        .then(data => {
          console.log("ariaapixhr.getProposal()");
          console.log(data);
          this.proposal=data;
          return data;
        })
        .catch(error => {
          console.log(error);
        });
  }

  getAriaLink() {
/*    return this.httpclient.fetch(this.accesstokenserviceurl)
      .then(response => response.json())
      .then(data => {
        return data;
      })
      .catch(error => {
        console.log(error);
      });*/
    return this.httpclient.get(this.accesstokenserviceurl).then(data =>
    {
      console.log("getAriaLink()");
      console.log(data);
      return JSON.parse(data.response);
    })

  }

  getAccessToken(code,state){
    console.log("Ariaapi.getAccessToken()");
//    return this.httpclient.fetch(this.accesstokenserviceurl+"?code="+code+"&state="+state)
    return this.httpclient.get(this.accesstokenserviceurl+"?code="+code+"&state="+state)
      .then(data => {
        console.log("ariaapi.getaccesstoken() returned:");
        console.log(data);
        this.accesstoken=JSON.parse(data.response);
        return this.accesstoken;
      })
      .catch(error => {
        console.log(error);
      });
  }

  getProposalList() {
    if (this.accesstoken && this.accesstoken.access_token) {
//      return this.httpclient.fetch( this.proposallisturl,{
      return this.httpclient.get( this.proposallisturl+"?access_token="+this.accesstoken.access_token)

          .then(data => {
            console.log("ariaapixhr.getProposalList()");
            console.log(data);
            this.proposallistwrapper= JSON.parse(data.response);
            return this.proposallistwrapper.proposals
          })
          .catch(error => {
            console.log("ariaapixhr.getProposalList() error");
            console.log(error);
            throw error;
          });
      }

  }
}
