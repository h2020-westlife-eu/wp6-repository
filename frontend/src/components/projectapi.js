import {HttpClient} from 'aurelia-fetch-client';

/* Provides methods to return promise of data from REST Project api*/
export class ProjectApi {
  static inject = [HttpClient];

  constructor(httpclient) {
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
    //needs SSO credentials
    let apiurl = "/restcon";
    //test fronted calls test backend uri - which has test authentication - test credentials added
    if (window.location.pathname.indexOf('repositorytest2')>0) apiurl = "/restcontest2";
    else if (window.location.pathname.indexOf('repositorytest')>0) apiurl = "/restcontest";
    this.projecturl=apiurl+"/project";
    this.dataurl=apiurl+"/filelist";
    this.userinfourl=apiurl+"/user";
    //needs admin/staff credentials
    this.usersurl="/admin/restcon/users";
    this.projects=[];
    this.datasets=[];
  }

  getProjects() {
    //if the projects is already fetched - returns it, otherwise fetch
    if (this.projects.length>0)
      return new Promise(resolve => resolve(this.projects))
    else
      return this.httpclient.fetch(this.projecturl)
      .then(response => response.json())
      .then(data => {
        this.projects= data
        return data
      })
      .catch(error => {
        console.log(error);

      });
  }

      /*
        this.proposalsall = data;
        this.proposals = this.prop/osalsall.slice(0,3);
        this.proposalslength= this.proposalsall.length;
        this.showallbutton = this.proposalsall.length > 3;
        this.showmorebutton = true;

      })*/
    //not yet implemented on backend
  getDatasets() {
    if (this.datasets.length>0)
      return new Promise(resolve => resolve(this.datasets))
    else
    return this.httpclient.fetch(this.dataurl)
      .then(response => response.json())
      .then(data => {
        this.datasets=data;
        return data;
      })
      .catch(error => {
        console.log(error);
      });

  }

  /* getting list of users - available for staff */
  getUsers() {
    return this.httpclient.fetch(this.usersurl)
      .then(response => response.json())
      .then(data => {
        return data;
      })
      .catch(error => {
        console.log('getUsers() returns error:');
        console.log(error);
        //alert("Sorry, error:"+error.statusCode+" "+error.message);
      });
  }

  getUserInfo() {
      return this.httpclient.fetch(this.userinfourl)
        .then(response => response.json())
        .then(data => {
          return data;
        })
        .catch(error => {
          console.log('getUserInfo() returns error:');
          //console.log(error);
          throw error;
          //          alert("Sorry, error:"+error.statusCode+" "+error.message);
        });
    }
}
