import {bindable} from 'aurelia-framework';
import {EventAggregator} from 'aurelia-event-aggregator';
import {Uploaddir} from '../components/messages';

export class Pickerclient {
  static inject = [EventAggregator];
  @bindable mode;

  constructor(ea) {
    this.ea=ea;
    this.href="https://portal.west-life.eu/virtualfolder/filepickercomponent.html";
    this.href2="https://portal.west-life.eu/virtualfolder/uploaddirpickercomponent.html";
    this.vfurl="";
    //receives message from popup window, fills target element with the data received
    let th2= this
    this.receiveMessage = e => {
      console.log("received event");
      console.log(e);
      th2.vfurl=e.data;
      console.log("publishing event");
      th2.ea.publish(new Uploaddir(th2.vfurl))
    }
    console.log("Pickerclient()");
    console.log(this.mode)
  }

  attached() {
    //registers itself to receive message from popup window
    window.addEventListener("message", this.receiveMessage);
    console.log("Pickerclient attached()");
    console.log(this.mode);
    this.filepicker = (this.mode === "file")
  }

  detached() {
    window.removeEventListener("message", this.receiveMessage)
  }

//opens popup window in defined location, sets the target element id.
  openfilewindow() {
    this.popup=window.open(this.href, 'newwindow', 'width=640, height=480');
  }
  opendirwindow() {
    this.popup=window.open(this.href2, 'newwindow', 'width=640, height=480');
  }
}
