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
  }
  attached() {
    console.log("VisitingScientist atached()")
  }
}
