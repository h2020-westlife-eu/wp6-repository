import {HttpClient} from 'aurelia-http-client';

export class App {
  static inject = [HttpClient];

constructor(httpclient){
    this.location = window.location.protocol;
    this.islocalhost= this.location.startsWith('http:');
    this.client=httpclient;

    this.items=[{date:"06/09/2017",summary:"spectrum of strychnine process with v_noesy_pro.mac (NUTS-Pro) or v_noesy.mac (NUTS-2D)", info:"1.6 Mb"},
      {date:"07/09/2017",summary:" spectrum of sucrose (1.3 Mbytes); process with v_ghsqc_pro.mac (NUTS-Pro) or v_ghsqc.mac (NUTS-2D)", info:"1.3 Mb"},
      {date:"08/09/2017",summary:"spectrum of strychnine (2.1 Mbytes); process with v_hsqc_pro.mac (NUTS-Pro) or v_hsqc.mac (NUTS-2D).", info:"2.1 Mb"},
    ]
  }

  selectItem(item) {
    console.log("selected item");
    console.log(item);
  }

}
