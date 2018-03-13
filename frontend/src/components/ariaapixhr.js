//import {HttpClient,json} from 'aurelia-fetch-client';
import {HttpClient} from 'aurelia-http-client';

/* Provides methods to return promise of data from REST Project api*/
export class Ariaapi {
  static inject = [HttpClient];

  constructor(httpclient) {
    this.httpclient=httpclient;
    this.httpclient.configure(config=> {
      config.withHeader('Accept', 'text/plain');
      config.withHeader('Content-Type', 'text/plain');
    });

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

  getProposal() {
//    return this.httpclient.fetch(this.proposalurl)
      return this.httpclient.get(this.proposalurl)
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
      return this.httpclient.post( this.proposallisturl,{access_token:this.accesstoken.access_token,aria_response_format:'json'}
        )
          .then(data => {
            console.log("ariaapixhr.getProposalList()");
            console.log(data);
            this.proposallist= JSON.parse(data.response);
            return this.proposallist
          })
          .catch(error => {
            console.log("ariaapixhr.getProposalList() error");
            console.log(error);
            throw error;
          });
      }

  }

}
