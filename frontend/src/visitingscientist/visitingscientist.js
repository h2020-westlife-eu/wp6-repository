import {HttpClient} from 'aurelia-http-client';

export class Visitingscientist {

  constructor() {
    console.log("VisitingScientist()");
    this.proposals = [
      {
        "pid": "1582",
        "title": "API Test with Crystallisation and MX beamtime for strychnine",
        "statusID": "5",
        "status": "Approved",
        "submitted": "1454583055",
        "display": "PID: 1582 - API Test with Crystallisation and MX beamtime for strychnine"
      },
      {
        "pid": "1586",
        "title": "API Test with Crystallisation and MX beamtime for acetylcholine",
        "statusID": "3",
        "status": "Pending",
        "submitted": "1454583056",
        "display": "PID: 1586 - API Test with Crystallisation and MX beamtime for acetylcholine"
      }
    ];
    this.items=[{date:"06/09/2017",summary:"spectrum of strychnine process with v_noesy_pro.mac (NUTS-Pro) or v_noesy.mac (NUTS-2D)", info:"1.6 Mb"},
      {date:"07/09/2017",summary:" spectrum of sucrose (1.3 Mbytes); process with v_ghsqc_pro.mac (NUTS-Pro) or v_ghsqc.mac (NUTS-2D)", info:"1.3 Mb"},
      {date:"08/09/2017",summary:"spectrum of strychnine (2.1 Mbytes); process with v_hsqc_pro.mac (NUTS-Pro) or v_hsqc.mac (NUTS-2D).", info:"2.1 Mb"},
    ]

  }
  attached() {
    console.log("VisitingScientist atached()")
  }
}
