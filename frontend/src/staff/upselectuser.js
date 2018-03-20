import {ProjectApi} from '../components/projectapi';


export class Upselectuser {
  static inject = [ProjectApi];

  constructor(pa) {
    this.visitors = [];
    this.pa = pa;
 }

  attached(){
    //this.pa.selectedUser;
    this.pa.getUsers()
     .then(data => { this.visitors = data });
 }

 selectUser(user){
    this.pa.selectedUser=user;
    return true;
 }
}
