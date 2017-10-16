/**
 * Created by vagrant on 10/13/17.
 */
import {HttpClient} from 'aurelia-http-client';

export class Newproposal {

  constructor() {
    this.proposalform=[{title:"Title"},{title:"Owner"},{title:"Principal Investigator"},{title:"Team",longtext:true},
      {title:"Scientific background and significance",longtext:true},
      {title:"Research programme and methodology",longtext:true},
      {title:"Ethical concerns",longtext:true,conditional:true},
      {title:"Safety concerns",longtext:true,conditional:true},
      {title:"Background in your lab & current results",longtext:true},
      {title:"Relevant publication"},
      {title:"Files & figures"},
      {title:"Funding"},
      {title:"Estimated access units"},
      {title:"4 key words"},
      {title:"Value for translational research"},
      {title:"Has the host been contacted?", conditional:true},
      {title:"Host collaborator"},
      {title:"Requested support"},
      {title:"Sample characteristics"},
      {title:"Has previous NMR work been performed?",longtext:true,conditional:true},
      {title:"Temperature for NMR experiments (°C)"},
      {title:" 	Desired experiments"},
      {title:" 	Estimated measurement time"},
      {title:"Sequence of target(s)",longtext:true},
      {title:"I can make 300ul of sample at concentration more than 5mg/ml",conditional:true},
      {title:"MW of (complex) macromolecule"},
      {title:"Gel of purified protein"},
      {title:"(MW<40,000) I will provide 15N labeled sample (500ul, 0.2 mM) for HSQC/NMR",conditional:true},
      {title:"MW>200,000) I will provide a sample (50 ul, 1mg/ml) for EM megative-stain analysis",conditional:true},
      {title:" 	Please upload an image of your previous cryoEM work"},
      {title:" 	Is your sample biosafety level 1",conditional:true},
      {title:"Experimental plan"},
      {title:" 	Do you have an industrial collaboration or contract",conditional:true},
      {title:"Do any of the envisaged samples have biosafety issues",conditional:true},
      {title:"iNEXT MX/SAXS individual platform data"}]

  }
  submit() {
    location.assign('#visitingscientist')
  }
}
