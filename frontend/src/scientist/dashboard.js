import {Ariaapi} from '../components/ariaapi';


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
  static inject = [Ariaapi];
  constructor(ariaapi) {
    this.ariaapi = ariaapi;
    this.importingaria=false;
    this.importariastatus="";
    this.importariaerror=false;
  }

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
        }
      });
    }
  }
}

