import {HttpClient,json} from 'aurelia-fetch-client';


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
            'Content-Type': 'application/json'
          }
        })
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
        this.accesstoken=data;
        return data;
      })
      .catch(error => {
        console.log(error);
      });
  }

  getProposalList() {
    if (this.accesstoken && this.accesstoken.access_token) {
      return this.httpclient.fetch( this.proposallisturl,{
          method:"POST",
          body:json({access_token:this.accesstoken.access_token,aria_response_format:'json'})
        })
          .then(response => response.json())
          .then(data => {
            this.proposallist= data
            return data
          })
          .catch(error => {
            console.log(error);
          });
      }

  }

}
