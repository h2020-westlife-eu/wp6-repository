import {ProjectApi} from './projectapi';

export class Userinfo {
  static inject = [ProjectApi];

  constructor(projectapi) {
    this.pa = projectapi;
    this.showuserinfo=false;
  }

  attached(){
    //console.log("Userinfo atached()")
    //console.log(this.pa);
    this.pa.getUserInfo().then(data => {
        //console.log(data);
        this.userinfo=data;
        this.showuserinfo=true;
      })
  }
}
