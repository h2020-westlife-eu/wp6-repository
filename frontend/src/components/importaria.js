export class Importaria {
  constructor () {
   this.client_id = "asdf"
   this.oaservice='https://www.structuralbiology.eu/ws/oauth/';
   //random int between 1000000, 9999999
   this.oastate=Math.floor(Math.random() * (9999999 - 1000000) ) + 1000000;
  }

  attached () {
    this.ariaurl=this.oaservice+
      "authorize?client_id="+this.client_id+"&state="+this.oastate;
  }
}
