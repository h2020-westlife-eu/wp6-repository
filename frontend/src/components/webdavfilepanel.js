import {HttpClient} from 'aurelia-fetch-client';
import {EventAggregator} from 'aurelia-event-aggregator';
import {Editfile,Webdavresource} from './messages';

export class Webdavfilepanel {
  static inject = [EventAggregator,HttpClient];

  constructor(ea,httpclient) {
    this.ea =ea;
    this.httpclient = httpclient;
    this.httpclient.configure(config => {
      config
        .rejectErrorResponses()
        .withBaseUrl('')
        .withDefaults({
          credentials: 'same-origin',
          headers: {
            'Accept': 'application/json',
            'X-Requested-With': 'Fetch'
          }
        })
    });
    //this.webdavbase = ;
    this.ea.subscribe(Webdavresource,msg =>this.setwebdav(msg.webdavurl))
    this.webdavpath = '/files/XufWqKau/';
  }

  setwebdav(webdavurl) {
    this.webdavpath = webdavurl;
    this.httpclient.fetch(this.webdavpath, {
      method: 'PROPFIND',
      headers: {'Depth': '1'}
    }).then(response => response.text())
      .then(str => (new window.DOMParser()).parseFromString(str, "text/xml"))
      .then(data => {
        console.log("selectDataset() files:")
        console.log(data);
        //parse structure https://stackoverflow.com/questions/17604071/parse-xml-using-javascript
        this.files = [];
        let filesDOM = data.getElementsByTagName("D:response");
        for (let fileitem of filesDOM) {
          let filename = this.getFirstElementByTagName(fileitem, "D:href");
          let filedate = this.getFirstElementByTagName(fileitem, "lp1:creationdate");
          let filesize = this.getFirstElementByTagName(fileitem, "lp1:getcontentlength");
          let filetype = this.getFirstElementByTagName(fileitem, "D:getcontenttype");
          if (filename != this.webdavpath) //do not include current dir
          {
            let item = {};
            item.name = filename.replace(this.webdavpath, '')
            item.date = filedate;
            item.size = filetype == 'httpd/unix-directory' ? 'DIR' : filesize;
            item.type = filetype;
            console.log(item);
            this.files.push(item);
          }

        }
        console.log(this.files);

      }).catch(error => {
      console.log("selectDataset() error");
      console.log(error);
    });
  }

  getFirstElementByTagName(fileitem,tag) {
    //console.log(tag);
    let elements =fileitem.getElementsByTagName(tag);
    //console.log(elements);
    return elements.length>0?elements[0].textContent:'';
  }

  selectFile(file){
    file.webdavurl = this.webdavpath+file.name;
    //if (file.size < 4096)
    this.ea.publish(new Editfile(file));
  }

}
