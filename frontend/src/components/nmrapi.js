// defines some usefull methods which can be used by other related to handle NMR data

export class Nmrapi {
  // converts blob e.g. from Response into byte array and float array in data
  // data is reference to data object= {raw:Float32Array,ri:[[1,2],[1,3],...]}
  // optional handsontable is reference to handsontable object, if it exists, it is updated
  // with data.ri
  convert(blob,data,handsontable) {
    //convert blob to array of floats
    let arrayBuffer;
    let fileReader = new FileReader();

    // define action at the end of reading
    fileReader.addEventListener("loadend", function() {
      //console.log("fileeditor.convert() raw data read:")
      // store the read data
      let uint8Array = new Uint8Array(fileReader.result);

      // if it starts with '#' that will be probably nuts or jcamp format - ASCII until ctrl-z (#26) fid
      if (uint8Array[0]===35) {
        console.log('Jcamp or Nuts format detected - shift to CTRL-z')
        let indexbindata=uint8Array.indexOf(26);
        let uint82 = new Uint8Array(uint8Array,indexbindata+1);
        data.raw = new Float32Array(uint82.buffer);
      } else {
        data.raw = new Float32Array(uint8Array.buffer);
      }

      // now convert rawdata into array of [[Real,Imaginary],[Real,Imaginary],..]
      // which handsontable can visualize
      data.ri = [];
      for (let i=0;(i+1)<data.raw.length;i+=2){
        //console.log(data.raw[i]);
        //console.log(data.raw[i+1])
        data.ri.push([data.raw[i],data.raw[i+1]]);
      }
      //update handsontable if it is defined
      if (handsontable) handsontable.updateSettings({data:data.ri});
    });
    fileReader.readAsArrayBuffer(blob);
  }

}
