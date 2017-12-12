import {HttpClient} from 'aurelia-fetch-client';

export class Userinfo {
  static inject = [HttpClient];

  constructor(httpclient) {
    this.httpclient = httpclient;
    this.httpclient.configure(config => {
      config
        .rejectErrorResponses()
        .withBaseUrl('/restcon/')
        .withDefaults({
          credentials: 'same-origin',
          headers: {
            'Accept': 'application/json',
            'X-Requested-With': 'Fetch'
          }
        })
    })
    this.showuserinfo=false;
  }

  attached(){
    console.log("Userinfo atached()")
    this.httpclient.fetch("user")
      .then(response => response.json())
      .then(data => {
        console.log(data);
        this.userinfo=data;
        this.showuserinfo=true;
      })
      .catch(error => {
        //console.log(error);
        console.log(error);
//          alert("Sorry, error:"+error.statusCode+" "+error.message);
      });
  }
}
