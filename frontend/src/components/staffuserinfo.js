import {ProjectApi} from './projectapi';

export class Staffuserinfo {
  static inject = [ProjectApi];

  constructor(projectapi) {
    this.pa = projectapi;
    this.showuserinfo=false;
  }

  attached(){
    this.pa.getStaffUserInfo().then(data => {
        console.log(data);
        this.userinfo=data;
        this.showuserinfo=true;
      })
      .catch(error => {
        console.log("no user info, disable showing it")
        this.showuserinfo=false;
      })
  }
}
