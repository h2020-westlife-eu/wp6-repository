/**
 * Created by Tomas Kulhanek on 2/17/17.
 */

import * as CodeMirror from "codemirror";
import "codemirror/mode/clike/clike";
import "codemirror/mode/htmlmixed/htmlmixed";
import "codemirror/mode/javascript/javascript";

import {EventAggregator} from 'aurelia-event-aggregator';
import {HttpClient} from 'aurelia-fetch-client';
import {Editfile} from './messages';
import {bindable} from 'aurelia-framework';


//import $ from 'jquery';


export class Fileeditor {
  static inject = [EventAggregator, HttpClient];
  @bindable pid;

  constructor(ea,httpclient) {

    this.ea = ea;
    this.client = httpclient;
    this.ea.subscribe(Editfile, msg => this.selectFile(msg.file));
    this.isimage=false;
    this.filename="";
    this.showtable=false;

  }

  attached() {
    console.log("Fileeditor.attached()1");
    this.codemirror = CodeMirror.fromTextArea(this.cmTextarea, {
      lineNumbers: true,
      mode: "text/x-less",
      lineWrapping: true
    });
    this.codemirror.refresh();
    //sample data ofr table
    this.data=[["no data for preview",1],[1,1]];

    //handsontable needs to be inlcuded in <script ...> of index.html page
    this.ht2 = new Handsontable (this.filetable, {
      data: this.data,
      rowHeaders: true,
      colHeaders: ["R","I"],
      autoWrapRow:true,
      stretchH: "all",
      autoResizeColumn: true

    });
    console.log(this.ht2);
  }

  selectFile(file) {
    let that =this

      this.imageurl = file.webdavurl;
      //visualizeimg is set & image extension is detected
      //console.log("fileeditor.selectfile() visualizeimg: isimage:")
      //console.log(localStorage.getItem("visualizeimg"));
      //vfstorage returns string - should convert to boolean
      this.isimage =
        ((file.name.endsWith('.JPG'))||
          (file.name.endsWith('.jpg'))||
          (file.name.endsWith('.PNG'))||
          (file.name.endsWith('.png'))||
          (file.name.endsWith('.GIF'))||
          (file.name.endsWith('.gif'))||
          (file.name.endsWith('.BMP'))||
          (file.name.endsWith('.bmp'))||
          (file.name.endsWith('.SVG'))||
          (file.name.endsWith('.svg')));

      //console.log("fileeditor.selectfile() visualizeimg: isimage:")
      //console.log(this.isimage);

      /*get first 2 kB of data, if it is supported by web server in Range header */
      if (!this.isimage)

        this.client.fetch(file.webdavurl, {credentials: 'same-origin',headers:{'Range': 'bytes=0-2047'}})
          .then(response => {
            let response2= response.clone();
            response.blob().then(data2 => {
              console.log("file blob content:");
              console.log(data2);
              let bindata= this.convert(data2);
//              console.log(bindata);
//              that.ht2.updateSettings({data:bindata})

            })
              response2.text().then(data => {
                that.codemirror.setValue(data);
                that.codemirror.refresh();
                that.filename = file.webdavurl;
              });
            }
          ).catch(error => {
          alert('Error retrieving content from ' + file.webdavurl);
          console.log(error);
        });


  }
  //triggered when click on button
  table() {
    this.showtable= ! this.showtable;
    //workaround - table width is incorect when rendered hidden
    //render it after 100 ms again, usually after it is shown, thus calculating
    //correct width
    if (this.showtable) window.setTimeout(function(that){that.ht2.render()},100,this);
  }

  convert(blob) {
    //convert blob to array of floats
    let arrayBuffer;
    let fileReader = new FileReader();
    let that = this;
    fileReader.addEventListener("loadend", function() {
      console.log("fileeditor.convert() raw data read:")
      //console.log(fileReader.result);
      that.rawdata = new Float32Array(fileReader.result);
      that.data = []
      for (let i=0;(i+1)<that.rawdata.length;i+=2){
        console.log(that.rawdata[i]);
        console.log(that.rawdata[i+1])
        that.data.push([that.rawdata[i],that.rawdata[i+1]]);
      }
      console.log(that.data);
      that.ht2.updateSettings({data:that.data});
      // reader.result contains the contents of blob as a typed array
    });
    fileReader.readAsArrayBuffer(blob);
  }
}
