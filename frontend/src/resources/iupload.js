import {bindable} from 'aurelia-framework';
/** Iupload - <iupload href="link-reference"></iupload>
 * element to create a link icon which on click will copy the url into the clipboard
 */
/*
var popup;
var target;
//opens popup window in defined location, sets the target element id.
function openwindow(_target,href) {
  target = _target;
  popup=window.open(href, 'newwindow', 'width=640, height=480');
}
//receives message from popup window, fills target element with the data received
function receiveMessage(event) {
  document.getElementById(target).innerHTML=event.data;
  document.getElementById(target).setAttribute("href",event.data);
}
window.addEventListener("message", receiveMessage, false);
*/
export class Iupload {
  @bindable href;

  constructor() {
    //receiveMessage will be registered in event listener - this declaration will assure that the context 'this' won't be lost
    this.receiveMessage = event=> {
      this.uploadhref=event.data;
      if (this.uploadhref && this.uploadhref.length>0){
        //now upload from this.hreffull to this.uploadhref
        console.log("Iupload.receiveMessage()");
        console.log("will upload from:"+this.hreffull);
        console.log("              to:"+this.uploadhref);
      }
    }

  }

  attached() {
    //set value of referenced input
    // href is relative - thus adding protocol and hostname, port
    let port = (window.location.port == "" || window.location.port=="80" || window.location.port == "443")? "" :":"+window.location.port;
    this.hreffull = window.location.protocol+ '//'+window.location.hostname+port+this.href;
    console.log("Iupload.atached()")
    console.log(this.href)
    console.log(this.hreffull)
    window.addEventListener("message", this.receiveMessage, false);
  }

  dettached() {
    window.removeEventListener("message", this.receiveMessage);
  }

  openwindow(href) {
    this.popup=window.open(href, 'newwindow', 'width=640, height=480');
  }

  uploadtovf() {
    this.openwindow('https://portal.west-life.eu/virtualfolder/uploaddirpickercomponent.html');
  }
}
