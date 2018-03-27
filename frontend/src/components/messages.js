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

export class FilterProject {
  constructor(id){
    this.id = id;
  }
}

export class FilterDatasetByProject {
  constructor(id){
    this.id = id;
  }
}

export class FilterDataset {
  constructor(id){
    this.id = id;
  }
}

export class FilterProjectByDataset {
  constructor(id){
    this.id = id;
  }
}

export class Selecteddataset {
  constructor(dataset){
    this.dataset = dataset;
  }
}
export class Preselecteddataset {
  constructor(datasetid){
    this.datasetid = datasetid;
  }
}

export class Preselecteddatasets {
  constructor(){
  }
}

export class Adddataset {
  constructor(dataset){
    this.dataset= dataset;
  }
}

export class Addproject {
  constructor(project){
    this.project= project;
  }
}
