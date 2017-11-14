import {HttpClient} from 'aurelia-http-client';

export class Repositorystaff {

  constructor() {
    console.log("Repositorystaff()");
    this.visitors = ["Tomas Kulhanek","Andrea Giacchieti","Antonio Rosatto"];
    this.items=[{date:"06/09/2017",summary:"spectrum of strychnine process with v_noesy_pro.mac (NUTS-Pro) or v_noesy.mac (NUTS-2D)", info:"1.6 Mb"},
      {date:"07/09/2017",summary:" spectrum of sucrose (1.3 Mbytes); process with v_ghsqc_pro.mac (NUTS-Pro) or v_ghsqc.mac (NUTS-2D)", info:"1.3 Mb"},
      {date:"08/09/2017",summary:"spectrum of strychnine (2.1 Mbytes); process with v_hsqc_pro.mac (NUTS-Pro) or v_hsqc.mac (NUTS-2D).", info:"2.1 Mb"},
    ]

  }
  attached() {

  }

  selectitem(item) {
    console.log("Selected",item)
  }
  deleteitem(item){
    let indexremoved = this.items.indexOf(item);
    if (indexremoved >=0 ) this.items.splice(indexremoved,1);
  }
}
