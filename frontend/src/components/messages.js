/**
 * Created by Tomas Kulhanek on 1/9/17.
 */

export class Webdavresource {
  constructor(webdavurl){
    this.webdavurl = webdavurl;
  }
}

export class Editfile {
  constructor(file){
    this.file = file;
  }
}

export class Uploaddir {
  constructor(webdavurl){
    this.webdavurl = webdavurl;
  }
}

export class Selectedproject {
  constructor(project){
    this.project = project;
  }
}
