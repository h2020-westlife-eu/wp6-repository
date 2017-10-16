define('app',['exports'], function (exports) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var App = exports.App = function () {
    function App() {
      _classCallCheck(this, App);
    }

    App.prototype.configureRouter = function configureRouter(config, router) {
      config.title = 'Aurelia Router Demo';
      config.map([{ route: ['', 'home'], name: 'home', moduleId: 'home/home', nav: true, title: 'Home' }, { route: 'login', name: 'login', moduleId: 'login/login', nav: true, title: 'Login' }, { route: 'visitingscientist', name: 'visitingscientist', moduleId: 'visitingscientist/visitingscientist', nav: true, title: 'Visiting Scientist' }, { route: 'newproposal', name: 'fnewproposal', moduleId: 'visitingscientist/newproposal', nav: false, title: 'New Proposal' }, { route: 'instructstaff', name: 'instructstaff', moduleId: 'instructstaff/instructstaff', nav: true, title: 'Instruct Staff' }, { route: 'facilityadmin', name: 'facilityadmin', moduleId: 'facilityadmin/facilityadmin', nav: true, title: 'Facility Admin' }]);
      this.router = router;
    };

    return App;
  }();
});
define('environment',["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.default = {
    debug: true,
    testing: true
  };
});
define('main',['exports', './environment'], function (exports, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.configure = configure;

  var _environment2 = _interopRequireDefault(_environment);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  function configure(aurelia) {
    aurelia.use.standardConfiguration().feature('resources');

    if (_environment2.default.debug) {
      aurelia.use.developmentLogging();
    }

    if (_environment2.default.testing) {
      aurelia.use.plugin('aurelia-testing');
    }

    aurelia.start().then(function () {
      return aurelia.setRoot();
    });
  }
});
define('navitem',['exports', 'aurelia-framework'], function (exports, _aureliaFramework) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Navitem = undefined;

  function _initDefineProp(target, property, descriptor, context) {
    if (!descriptor) return;
    Object.defineProperty(target, property, {
      enumerable: descriptor.enumerable,
      configurable: descriptor.configurable,
      writable: descriptor.writable,
      value: descriptor.initializer ? descriptor.initializer.call(context) : void 0
    });
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  function _applyDecoratedDescriptor(target, property, decorators, descriptor, context) {
    var desc = {};
    Object['ke' + 'ys'](descriptor).forEach(function (key) {
      desc[key] = descriptor[key];
    });
    desc.enumerable = !!desc.enumerable;
    desc.configurable = !!desc.configurable;

    if ('value' in desc || desc.initializer) {
      desc.writable = true;
    }

    desc = decorators.slice().reverse().reduce(function (desc, decorator) {
      return decorator(target, property, desc) || desc;
    }, desc);

    if (context && desc.initializer !== void 0) {
      desc.value = desc.initializer ? desc.initializer.call(context) : void 0;
      desc.initializer = undefined;
    }

    if (desc.initializer === void 0) {
      Object['define' + 'Property'](target, property, desc);
      desc = null;
    }

    return desc;
  }

  function _initializerWarningHelper(descriptor, context) {
    throw new Error('Decorating class property failed. Please ensure that transform-class-properties is enabled.');
  }

  var _desc, _value, _class, _descriptor;

  var Navitem = exports.Navitem = (_class = function Navitem() {
    _classCallCheck(this, Navitem);

    _initDefineProp(this, 'href', _descriptor, this);
  }, (_descriptor = _applyDecoratedDescriptor(_class.prototype, 'href', [_aureliaFramework.bindable], {
    enumerable: true,
    initializer: null
  })), _class);
});
define('facilityadmin/facilityadmin',['exports', 'aurelia-http-client'], function (exports, _aureliaHttpClient) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Facilityadmin = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var _class, _temp;

  var Facilityadmin = exports.Facilityadmin = (_temp = _class = function Facilityadmin(httpclient) {
    _classCallCheck(this, Facilityadmin);

    this.location = window.location.protocol;
    this.islocalhost = this.location.startsWith('http:');
    this.client = httpclient;
  }, _class.inject = [_aureliaHttpClient.HttpClient], _temp);
});
define('facilityadmin/main',['exports', '../environment'], function (exports, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.configure = configure;

  var _environment2 = _interopRequireDefault(_environment);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  Promise.config({
    warnings: {
      wForgottenReturn: false
    }
  });

  function configure(aurelia) {
    aurelia.use.standardConfiguration().feature('resources');

    if (_environment2.default.debug) {
      aurelia.use.developmentLogging();
    }

    if (_environment2.default.testing) {
      aurelia.use.plugin('aurelia-testing');
    }

    aurelia.start().then(function () {
      return aurelia.setRoot();
    });
  }
});
define('home/home',["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var Home = exports.Home = function Home() {
    _classCallCheck(this, Home);
  };
});
define('instructstaff/instructstaff',['exports', 'aurelia-http-client'], function (exports, _aureliaHttpClient) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Instructstaff = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var Instructstaff = exports.Instructstaff = function Instructstaff() {
    _classCallCheck(this, Instructstaff);
  };
});
define('instructstaff/main',['exports', '../environment'], function (exports, _environment) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.configure = configure;

  var _environment2 = _interopRequireDefault(_environment);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  Promise.config({
    warnings: {
      wForgottenReturn: false
    }
  });

  function configure(aurelia) {
    aurelia.use.standardConfiguration().feature('resources');

    if (_environment2.default.debug) {
      aurelia.use.developmentLogging();
    }

    if (_environment2.default.testing) {
      aurelia.use.plugin('aurelia-testing');
    }

    aurelia.start().then(function () {
      return aurelia.setRoot();
    });
  }
});
define('login/login',['exports', 'aurelia-http-client'], function (exports, _aureliaHttpClient) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Login = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var Login = exports.Login = function Login() {
    _classCallCheck(this, Login);
  };
});
define('resources/index',["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.configure = configure;
  function configure(config) {}
});
define('visitingscientist/newproposal',["exports", "aurelia-http-client"], function (exports, _aureliaHttpClient) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Newproposal = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var Newproposal = exports.Newproposal = function () {
    function Newproposal() {
      _classCallCheck(this, Newproposal);

      this.proposalform = [{ title: "Title" }, { title: "Owner" }, { title: "Principal Investigator" }, { title: "Team", longtext: true }, { title: "Scientific background and significance", longtext: true }, { title: "Research programme and methodology", longtext: true }, { title: "Ethical concerns", longtext: true, conditional: true }, { title: "Safety concerns", longtext: true, conditional: true }, { title: "Background in your lab & current results", longtext: true }, { title: "Relevant publication" }, { title: "Files & figures" }, { title: "Funding" }, { title: "Estimated access units" }, { title: "4 key words" }, { title: "Value for translational research" }, { title: "Has the host been contacted?", conditional: true }, { title: "Host collaborator" }, { title: "Requested support" }, { title: "Sample characteristics" }, { title: "Has previous NMR work been performed?", longtext: true, conditional: true }, { title: "Temperature for NMR experiments (°C)" }, { title: " 	Desired experiments" }, { title: " 	Estimated measurement time" }, { title: "Sequence of target(s)", longtext: true }, { title: "I can make 300ul of sample at concentration more than 5mg/ml", conditional: true }, { title: "MW of (complex) macromolecule" }, { title: "Gel of purified protein" }, { title: "(MW<40,000) I will provide 15N labeled sample (500ul, 0.2 mM) for HSQC/NMR", conditional: true }, { title: "MW>200,000) I will provide a sample (50 ul, 1mg/ml) for EM megative-stain analysis", conditional: true }, { title: " 	Please upload an image of your previous cryoEM work" }, { title: " 	Is your sample biosafety level 1", conditional: true }, { title: "Experimental plan" }, { title: " 	Do you have an industrial collaboration or contract", conditional: true }, { title: "Do any of the envisaged samples have biosafety issues", conditional: true }, { title: "iNEXT MX/SAXS individual platform data" }];
    }

    Newproposal.prototype.submit = function submit() {
      location.assign('#visitingscientist');
    };

    return Newproposal;
  }();
});
define('visitingscientist/visitingscientist',["exports", "aurelia-http-client"], function (exports, _aureliaHttpClient) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Visitingscientist = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var Visitingscientist = exports.Visitingscientist = function () {
    function Visitingscientist() {
      _classCallCheck(this, Visitingscientist);

      console.log("VisitingScientist()");
      this.proposals = [{
        "pid": "1582",
        "title": "API Test with Crystallisation and MX beamtime for strychnine",
        "statusID": "5",
        "status": "Approved",
        "submitted": "1454583055",
        "display": "PID: 1582 - API Test with Crystallisation and MX beamtime for strychnine"
      }, {
        "pid": "1586",
        "title": "API Test with Crystallisation and MX beamtime for acetylcholine",
        "statusID": "3",
        "status": "Pending",
        "submitted": "1454583056",
        "display": "PID: 1586 - API Test with Crystallisation and MX beamtime for acetylcholine"
      }];
    }

    Visitingscientist.prototype.attached = function attached() {
      console.log("VisitingScientist atached()");
    };

    return Visitingscientist;
  }();
});
define('text!app.html', ['module'], function(module) { module.exports = "<template>\n  <require from=\"./w3.css\"></require>\n  <require from=\"components/navigation.html\"></require>\n  <!--navigation router.bind=\"router\" class=\"primary-navigation\"></navigation-->\n  <div class=\"w3-card-2 w3-white w3-margin w3-padding\">\n    <router-view>\n      <!-- Top level views are rendered here -->\n    </router-view>\n  </div>\n  <!--\n  <div class=\"w3-card-2 w3-white w3-margin w3-padding\">\n    <p>\n      You can register and schedule a new visit or ammend existing one.\n      <a href=\"login.html\" class=\"w3-button\">Login</a>\n    </p>\n\n  </div>\n  -->\n</template>\n"; });
define('text!w3.css', ['module'], function(module) { module.exports = "/* W3.CSS 2.99 Mar 2017 by Jan Egil and Borge Refsnes */\nhtml{box-sizing:border-box}*,*:before,*:after{box-sizing:inherit}\n/* Extract from normalize.css by Nicolas Gallagher and Jonathan Neal git.io/normalize */\nhtml{-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}body{margin:0}\narticle,aside,details,figcaption,figure,footer,header,main,menu,nav,section,summary{display:block}\naudio,canvas,progress,video{display:inline-block}progress{vertical-align:baseline}\naudio:not([controls]){display:none;height:0}[hidden],template{display:none}\na{background-color:transparent;-webkit-text-decoration-skip:objects}\na:active,a:hover{outline-width:0}abbr[title]{border-bottom:none;text-decoration:underline;text-decoration:underline dotted}\ndfn{font-style:italic}mark{background:#ff0;color:#000}\nsmall{font-size:80%}sub,sup{font-size:75%;line-height:0;position:relative;vertical-align:baseline}\nsub{bottom:-0.25em}sup{top:-0.5em}figure{margin:1em 40px}\nimg{border-style:none}svg:not(:root){overflow:hidden}\ncode,kbd,pre,samp{font-family:monospace,monospace;font-size:1em}\nhr{box-sizing:content-box;height:0;overflow:visible}\nbutton,input,select,textarea{font:inherit;margin:0}optgroup{font-weight:bold}\nbutton,input{overflow:visible}button,select{text-transform:none}\nbutton,html [type=button],[type=reset],[type=submit]{-webkit-appearance:button}\nbutton::-moz-focus-inner, [type=button]::-moz-focus-inner, [type=reset]::-moz-focus-inner, [type=submit]::-moz-focus-inner{border-style:none;padding:0}\nbutton:-moz-focusring, [type=button]:-moz-focusring, [type=reset]:-moz-focusring, [type=submit]:-moz-focusring{outline:1px dotted ButtonText}\nfieldset{border:1px solid #c0c0c0;margin:0 2px;padding:.35em .625em .75em}\nlegend{color:inherit;display:table;max-width:100%;padding:0;white-space:normal}textarea{overflow:auto}\n[type=checkbox],[type=radio]{padding:0}\n[type=number]::-webkit-inner-spin-button,[type=number]::-webkit-outer-spin-button{height:auto}\n[type=search]{-webkit-appearance:textfield;outline-offset:-2px}\n[type=search]::-webkit-search-cancel-button,[type=search]::-webkit-search-decoration{-webkit-appearance:none}\n::-webkit-input-placeholder{color:inherit;opacity:0.54}\n::-webkit-file-upload-button{-webkit-appearance:button;font:inherit}\n/* End extract */\nhtml,body{font-family:Verdana,sans-serif;font-size:15px;line-height:1.5}html{overflow-x:hidden}\nh1,h2,h3,h4,h5,h6,.w3-slim,.w3-wide{font-family:\"Segoe UI\",Arial,sans-serif}\nh1{font-size:36px}h2{font-size:30px}h3{font-size:24px}h4{font-size:20px}h5{font-size:18px}h6{font-size:16px}\n.w3-serif{font-family:\"Times New Roman\",Times,serif}\nh1,h2,h3,h4,h5,h6{font-weight:400;margin:10px 0}.w3-wide{letter-spacing:4px}\nh1 a,h2 a,h3 a,h4 a,h5 a,h6 a{font-weight:inherit}\nhr{border:0;border-top:1px solid #eee;margin:20px 0}\na{color:inherit}\n.w3-image{max-width:100%;height:auto}\n.w3-table,.w3-table-all{border-collapse:collapse;border-spacing:0;width:100%;display:table}\n.w3-table-all{border:1px solid #ccc}\n.w3-bordered tr,.w3-table-all tr{border-bottom:1px solid #ddd}\n.w3-striped tbody tr:nth-child(even){background-color:#f1f1f1}\n.w3-table-all tr:nth-child(odd){background-color:#fff}\n.w3-table-all tr:nth-child(even){background-color:#f1f1f1}\n.w3-hoverable tbody tr:hover,.w3-ul.w3-hoverable li:hover{background-color:#ccc}\n.w3-centered tr th,.w3-centered tr td{text-align:center}\n.w3-table td,.w3-table th,.w3-table-all td,.w3-table-all th{padding:8px 8px;display:table-cell;text-align:left;vertical-align:top}\n.w3-table th:first-child,.w3-table td:first-child,.w3-table-all th:first-child,.w3-table-all td:first-child{padding-left:16px}\n.w3-btn,.w3-btn-block,.w3-button{border:none;display:inline-block;outline:0;padding:6px 16px;vertical-align:middle;overflow:hidden;text-decoration:none!important;color:#fff;background-color:#000;text-align:center;cursor:pointer;white-space:nowrap}\n.w3-btn:hover,.w3-btn-block:hover,.w3-btn-floating:hover,.w3-btn-floating-large:hover{box-shadow:0 8px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19)}\n.w3-button{color:#000;background-color:#e1e1e1;padding:8px 16px}.w3-button:hover{color:#000!important;background-color:#ccc!important}\n.w3-btn,.w3-btn-floating,.w3-btn-floating-large,.w3-closenav,.w3-opennav,.w3-btn-block,.w3-button{-webkit-touch-callout:none;-webkit-user-select:none;-khtml-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none}\n.w3-btn-floating,.w3-btn-floating-large{display:inline-block;text-align:center;color:#fff;background-color:#000;position:relative;overflow:hidden;z-index:1;padding:0;border-radius:50%;cursor:pointer;font-size:24px}\n.w3-btn-floating{width:40px;height:40px;line-height:40px}.w3-btn-floating-large{width:56px;height:56px;line-height:56px}\n.w3-disabled,.w3-btn:disabled,.w3-button:disabled,.w3-btn-floating:disabled,.w3-btn-floating-large:disabled{cursor:not-allowed;opacity:0.3}.w3-disabled *,:disabled *{pointer-events:none}\n.w3-btn.w3-disabled:hover,.w3-btn-block.w3-disabled:hover,.w3-btn:disabled:hover,.w3-btn-floating.w3-disabled:hover,.w3-btn-floating:disabled:hover,\n.w3-btn-floating-large.w3-disabled:hover,.w3-btn-floating-large:disabled:hover{box-shadow:none}\n.w3-btn-group .w3-btn{float:left}.w3-btn-block{width:100%}\n.w3-btn-bar .w3-btn{box-shadow:none;background-color:inherit;color:inherit;float:left}.w3-btn-bar .w3-btn:hover{background-color:#ccc}\n.w3-badge,.w3-tag,.w3-sign{background-color:#000;color:#fff;display:inline-block;padding-left:8px;padding-right:8px;text-align:center}\n.w3-badge{border-radius:50%}\nul.w3-ul{list-style-type:none;padding:0;margin:0}ul.w3-ul li{padding:6px 2px 6px 16px;border-bottom:1px solid #ddd}ul.w3-ul li:last-child{border-bottom:none}\n.w3-tooltip,.w3-display-container{position:relative}.w3-tooltip .w3-text{display:none}.w3-tooltip:hover .w3-text{display:inline-block}\n.w3-navbar{list-style-type:none;margin:0;padding:0;overflow:hidden}\n.w3-navbar li{float:left}.w3-navbar li a,.w3-navitem,.w3-navbar li .w3-btn,.w3-navbar li .w3-input{display:block;padding:8px 16px}.w3-navbar li .w3-btn,.w3-navbar li .w3-input{border:none;outline:none;width:100%}\n.w3-navbar li a:hover{color:#000;background-color:#ccc}\n.w3-navbar .w3-dropdown-hover,.w3-navbar .w3-dropdown-click{position:static}\n.w3-navbar .w3-dropdown-hover:hover{background-color:#ccc;color:#000}\n.w3-navbar a,.w3-topnav a,.w3-sidenav a,.w3-dropdown-content a,.w3-accordion-content a,.w3-dropnav a,.w3-navblock a{text-decoration:none!important}\n.w3-navbar .w3-opennav.w3-right{float:right!important}.w3-topnav{padding:8px 8px}\n.w3-navblock .w3-dropdown-hover:hover,.w3-navblock .w3-dropdown-click:hover{background-color:#ccc;color:#000}\n.w3-navblock .w3-dropdown-hover,.w3-navblock .w3-dropdown-click{width:100%}.w3-navblock .w3-dropdown-hover .w3-dropdown-content,.w3-navblock .w3-dropdown-click .w3-dropdown-content{min-width:100%}\n.w3-topnav a{padding:0 8px;border-bottom:3px solid transparent;-webkit-transition:border-bottom .25s;transition:border-bottom .25s}\n.w3-topnav a:hover{border-bottom:3px solid #fff}.w3-topnav .w3-dropdown-hover a{border-bottom:0}\n.w3-opennav,.w3-closenav{color:inherit}.w3-opennav:hover,.w3-closenav:hover{cursor:pointer;opacity:0.8}\n.w3-btn,.w3-btn-floating,.w3-dropnav a,.w3-btn-floating-large,.w3-btn-block, .w3-navbar a,.w3-navblock a,.w3-sidenav a,.w3-pagination li a,.w3-hoverable tbody tr,.w3-hoverable li,\n.w3-accordion-content a,.w3-dropdown-content a,.w3-dropdown-click:hover,.w3-dropdown-hover:hover,.w3-opennav,.w3-closenav,.w3-closebtn,*[class*=\"w3-hover-\"]\n{-webkit-transition:background-color .25s,color .15s,box-shadow .25s,opacity 0.25s,filter 0.25s,border 0.15s;transition:background-color .25s,color .15s,box-shadow .15s,opacity .25s,filter .25s,border .15s}\n.w3-ripple:active{opacity:0.5}.w3-ripple{-webkit-transition:opacity 0s;transition:opacity 0s}\n.w3-sidenav,.w3-sidebar{height:100%;width:200px;background-color:#fff;position:fixed!important;z-index:1;overflow:auto}\n.w3-sidenav a,.w3-navblock a{padding:4px 2px 4px 16px}.w3-sidenav a:hover,.w3-navblock a:hover{background-color:#ccc;color:#000}.w3-sidenav a,.w3-dropnav a,.w3-navblock a{display:block}\n.w3-sidenav .w3-dropdown-hover:hover,.w3-sidenav .w3-dropdown-hover:first-child,.w3-sidenav .w3-dropdown-click:hover,.w3-dropnav a:hover{background-color:#ccc;color:#000}\n.w3-sidenav .w3-dropdown-hover,.w3-sidenav .w3-dropdown-click,.w3-bar-block .w3-dropdown-hover,.w3-bar-block .w3-dropdown-click{width:100%}\n.w3-sidenav .w3-dropdown-hover .w3-dropdown-content,.w3-sidenav .w3-dropdown-click .w3-dropdown-content,.w3-bar-block .w3-dropdown-hover .w3-dropdown-content,.w3-bar-block .w3-dropdown-click .w3-dropdown-content{min-width:100%}\n.w3-bar-block .w3-dropdown-hover .w3-button,.w3-bar-block .w3-dropdown-click .w3-button{width:100%;text-align:left;background-color:inherit;color:inherit;padding:6px 2px 6px 16px}\n.w3-main,#main{transition:margin-left .4s}\n.w3-modal{z-index:3;display:none;padding-top:100px;position:fixed;left:0;top:0;width:100%;height:100%;overflow:auto;background-color:rgb(0,0,0);background-color:rgba(0,0,0,0.4)}\n.w3-modal-content{margin:auto;background-color:#fff;position:relative;padding:0;outline:0;width:600px}.w3-closebtn{text-decoration:none;float:right;font-size:24px;font-weight:bold;color:inherit}\n.w3-closebtn:hover,.w3-closebtn:focus{color:#000;text-decoration:none;cursor:pointer}\n.w3-pagination{display:inline-block;padding:0;margin:0}.w3-pagination li{display:inline}\n.w3-pagination li a{text-decoration:none;color:#000;float:left;padding:8px 16px}\n.w3-pagination li a:hover{background-color:#ccc}\n.w3-input-group,.w3-group{margin-top:24px;margin-bottom:24px}\n.w3-input{padding:8px;display:block;border:none;border-bottom:1px solid #808080;width:100%}\n.w3-label{color:#009688}.w3-input:not(:valid)~.w3-validate{color:#f44336}\n.w3-select{padding:9px 0;width:100%;color:#000;border:1px solid transparent;border-bottom:1px solid #009688}\n.w3-select select:focus{color:#000;border:1px solid #009688}.w3-select option[disabled]{color:#009688}\n.w3-dropdown-click,.w3-dropdown-hover{position:relative;display:inline-block;cursor:pointer}\n.w3-dropdown-hover:hover .w3-dropdown-content{display:block;z-index:1}\n.w3-dropdown-click:hover{background-color:#ccc;color:#000}\n.w3-dropdown-hover:hover > .w3-button:first-child,.w3-dropdown-click:hover > .w3-button:first-child{background-color:#ccc;color:#000}\n.w3-dropdown-content{cursor:auto;color:#000;background-color:#fff;display:none;position:absolute;min-width:160px;margin:0;padding:0}\n.w3-dropdown-content a{padding:6px 16px;display:block}\n.w3-dropdown-content a:hover{background-color:#ccc}\n.w3-accordion{width:100%;cursor:pointer}\n.w3-accordion-content{cursor:auto;display:none;position:relative;width:100%;margin:0;padding:0}\n.w3-accordion-content a{padding:6px 16px;display:block}.w3-accordion-content a:hover{background-color:#ccc}\n.w3-progress-container{width:100%;height:1.5em;position:relative;background-color:#f1f1f1}\n.w3-progressbar{background-color:#757575;height:100%;position:absolute;line-height:inherit}\ninput[type=checkbox].w3-check,input[type=radio].w3-radio{width:24px;height:24px;position:relative;top:6px}\ninput[type=checkbox].w3-check:checked+.w3-validate,input[type=radio].w3-radio:checked+.w3-validate{color:#009688}\ninput[type=checkbox].w3-check:disabled+.w3-validate,input[type=radio].w3-radio:disabled+.w3-validate{color:#aaa}\n.w3-bar{width:100%;overflow:hidden}.w3-center .w3-bar{display:inline-block;width:auto}\n.w3-bar .w3-bar-item{padding:8px 16px;float:left;background-color:inherit;color:inherit;width:auto;border:none;outline:none;display:block}\n.w3-bar .w3-dropdown-hover,.w3-bar .w3-dropdown-click{position:static;float:left}\n.w3-bar .w3-button{background-color:inherit;color:inherit;white-space:normal}\n.w3-bar-block .w3-bar-item{width:100%;display:block;padding:6px 2px 6px 16px;text-align:left;background-color:inherit;color:inherit;border:none;outline:none}\n.w3-block{display:block;width:100%}\n.w3-responsive{overflow-x:auto}\n.w3-container:after,.w3-container:before,.w3-panel:after,.w3-panel:before,.w3-row:after,.w3-row:before,.w3-row-padding:after,.w3-row-padding:before,.w3-cell-row:before,.w3-cell-row:after,\n.w3-topnav:after,.w3-topnav:before,.w3-clear:after,.w3-clear:before,.w3-btn-group:before,.w3-btn-group:after,.w3-btn-bar:before,.w3-btn-bar:after,.w3-bar:before,.w3-bar:after\n{content:\"\";display:table;clear:both}\n.w3-col,.w3-half,.w3-third,.w3-twothird,.w3-threequarter,.w3-quarter{float:left;width:100%}\n.w3-col.s1{width:8.33333%}\n.w3-col.s2{width:16.66666%}\n.w3-col.s3{width:24.99999%}\n.w3-col.s4{width:33.33333%}\n.w3-col.s5{width:41.66666%}\n.w3-col.s6{width:49.99999%}\n.w3-col.s7{width:58.33333%}\n.w3-col.s8{width:66.66666%}\n.w3-col.s9{width:74.99999%}\n.w3-col.s10{width:83.33333%}\n.w3-col.s11{width:91.66666%}\n.w3-col.s12,.w3-half,.w3-third,.w3-twothird,.w3-threequarter,.w3-quarter{width:99.99999%}\n@media (min-width:601px){\n  .w3-col.m1{width:8.33333%}\n  .w3-col.m2{width:16.66666%}\n  .w3-col.m3,.w3-quarter{width:24.99999%}\n  .w3-col.m4,.w3-third{width:33.33333%}\n  .w3-col.m5{width:41.66666%}\n  .w3-col.m6,.w3-half{width:49.99999%}\n  .w3-col.m7{width:58.33333%}\n  .w3-col.m8,.w3-twothird{width:66.66666%}\n  .w3-col.m9,.w3-threequarter{width:74.99999%}\n  .w3-col.m10{width:83.33333%}\n  .w3-col.m11{width:91.66666%}\n  .w3-col.m12{width:99.99999%}}\n@media (min-width:993px){\n  .w3-col.l1{width:8.33333%}\n  .w3-col.l2{width:16.66666%}\n  .w3-col.l3,.w3-quarter{width:24.99999%}\n  .w3-col.l4,.w3-third{width:33.33333%}\n  .w3-col.l5{width:41.66666%}\n  .w3-col.l6,.w3-half{width:49.99999%}\n  .w3-col.l7{width:58.33333%}\n  .w3-col.l8,.w3-twothird{width:66.66666%}\n  .w3-col.l9,.w3-threequarter{width:74.99999%}\n  .w3-col.l10{width:83.33333%}\n  .w3-col.l11{width:91.66666%}\n  .w3-col.l12{width:99.99999%}}\n.w3-content{max-width:980px;margin:auto}\n.w3-rest{overflow:hidden}\n.w3-layout-container,.w3-cell-row{display:table;width:100%}.w3-layout-row{display:table-row}.w3-layout-cell,.w3-layout-col,.w3-cell{display:table-cell}\n.w3-layout-top,.w3-cell-top{vertical-align:top}.w3-layout-middle,.w3-cell-middle{vertical-align:middle}.w3-layout-bottom,.w3-cell-bottom{vertical-align:bottom}\n.w3-hide{display:none!important}.w3-show-block,.w3-show{display:block!important}.w3-show-inline-block{display:inline-block!important}\n@media (max-width:600px){.w3-modal-content{margin:0 10px;width:auto!important}.w3-modal{padding-top:30px}\n  .w3-topnav a{display:block}.w3-navbar li:not(.w3-opennav){float:none;width:100%!important}.w3-navbar li.w3-right{float:none!important}\n  .w3-topnav .w3-dropdown-hover .w3-dropdown-content,.w3-navbar .w3-dropdown-click .w3-dropdown-content,.w3-navbar .w3-dropdown-hover .w3-dropdown-content,.w3-dropdown-hover.w3-mobile .w3-dropdown-content,.w3-dropdown-click.w3-mobile .w3-dropdown-content{position:relative}\n  .w3-topnav,.w3-navbar{text-align:center}.w3-hide-small{display:none!important}.w3-layout-col,.w3-mobile{display:block;width:100%!important}.w3-bar-item.w3-mobile,.w3-dropdown-hover.w3-mobile,.w3-dropdown-click.w3-mobile{text-align:center}\n  .w3-dropdown-hover.w3-mobile,.w3-dropdown-hover.w3-mobile .w3-btn,.w3-dropdown-hover.w3-mobile .w3-button,.w3-dropdown-click.w3-mobile,.w3-dropdown-click.w3-mobile .w3-btn,.w3-dropdown-click.w3-mobile .w3-button{width:100%}}\n@media (max-width:768px){.w3-modal-content{width:500px}.w3-modal{padding-top:50px}}\n@media (min-width:993px){.w3-modal-content{width:900px}.w3-hide-large{display:none!important}.w3-sidenav.w3-collapse,.w3-sidebar.w3-collapse{display:block!important}}\n@media (max-width:992px) and (min-width:601px){.w3-hide-medium{display:none!important}}\n@media (max-width:992px){.w3-sidenav.w3-collapse,.w3-sidebar.w3-collapse{display:none}.w3-main{margin-left:0!important;margin-right:0!important}}\n.w3-top,.w3-bottom{position:fixed;width:100%;z-index:1}.w3-top{top:0}.w3-bottom{bottom:0}\n.w3-overlay{position:fixed;display:none;width:100%;height:100%;top:0;left:0;right:0;bottom:0;background-color:rgba(0,0,0,0.5);z-index:2}\n.w3-left{float:left!important}.w3-right{float:right!important}\n.w3-tiny{font-size:10px!important}.w3-small{font-size:12px!important}\n.w3-medium{font-size:15px!important}.w3-large{font-size:18px!important}\n.w3-xlarge{font-size:24px!important}.w3-xxlarge{font-size:36px!important}\n.w3-xxxlarge{font-size:48px!important}.w3-jumbo{font-size:64px!important}\n.w3-vertical{word-break:break-all;line-height:1;text-align:center;width:0.6em}\n.w3-left-align{text-align:left!important}.w3-right-align{text-align:right!important}\n.w3-justify{text-align:justify!important}.w3-center{text-align:center!important}\n.w3-display-topleft{position:absolute;left:0;top:0}.w3-display-topright{position:absolute;right:0;top:0}\n.w3-display-bottomleft{position:absolute;left:0;bottom:0}.w3-display-bottomright{position:absolute;right:0;bottom:0}\n.w3-display-middle{position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);-ms-transform:translate(-50%,-50%)}\n.w3-display-left{position:absolute;top:50%;left:0%;transform:translate(0%,-50%);-ms-transform:translate(-0%,-50%)}\n.w3-display-right{position:absolute;top:50%;right:0%;transform:translate(0%,-50%);-ms-transform:translate(0%,-50%)}\n.w3-display-topmiddle{position:absolute;left:50%;top:0;transform:translate(-50%,0%);-ms-transform:translate(-50%,0%)}\n.w3-display-bottommiddle{position:absolute;left:50%;bottom:0;transform:translate(-50%,0%);-ms-transform:translate(-50%,0%)}\n.w3-display-container:hover .w3-display-hover{display:block}.w3-display-container:hover span.w3-display-hover{display:inline-block}.w3-display-hover{display:none}\n.w3-display-position{position:absolute}\n.w3-circle{border-radius:50%!important}\n.w3-round-small{border-radius:2px!important}.w3-round,.w3-round-medium{border-radius:4px!important}\n.w3-round-large{border-radius:8px!important}.w3-round-xlarge{border-radius:16px!important}\n.w3-round-xxlarge{border-radius:32px!important}.w3-round-jumbo{border-radius:64px!important}\n.w3-border-0{border:0!important}.w3-border{border:1px solid #ccc!important}\n.w3-border-top{border-top:1px solid #ccc!important}.w3-border-bottom{border-bottom:1px solid #ccc!important}\n.w3-border-left{border-left:1px solid #ccc!important}.w3-border-right{border-right:1px solid #ccc!important}\n.w3-margin{margin:16px!important}.w3-margin-0{margin:0!important}\n.w3-margin-top{margin-top:16px!important}.w3-margin-bottom{margin-bottom:16px!important}\n.w3-margin-left{margin-left:16px!important}.w3-margin-right{margin-right:16px!important}\n.w3-section{margin-top:16px!important;margin-bottom:16px!important}\n.w3-padding-tiny{padding:2px 4px!important}.w3-padding-small{padding:4px 8px!important}\n.w3-padding-medium,.w3-padding,.w3-form{padding:8px 16px!important}\n.w3-padding-large{padding:12px 24px!important}.w3-padding-xlarge{padding:16px 32px!important}\n.w3-padding-xxlarge{padding:24px 48px!important}.w3-padding-jumbo{padding:32px 64px!important}\n.w3-padding-4{padding-top:4px!important;padding-bottom:4px!important}\n.w3-padding-8{padding-top:8px!important;padding-bottom:8px!important}\n.w3-padding-12{padding-top:12px!important;padding-bottom:12px!important}\n.w3-padding-16{padding-top:16px!important;padding-bottom:16px!important}\n.w3-padding-24{padding-top:24px!important;padding-bottom:24px!important}\n.w3-padding-32{padding-top:32px!important;padding-bottom:32px!important}\n.w3-padding-48{padding-top:48px!important;padding-bottom:48px!important}\n.w3-padding-64{padding-top:64px!important;padding-bottom:64px!important}\n.w3-padding-128{padding-top:128px!important;padding-bottom:128px!important}\n.w3-padding-0{padding:0!important}\n.w3-padding-top{padding-top:8px!important}.w3-padding-bottom{padding-bottom:8px!important}\n.w3-padding-left{padding-left:16px!important}.w3-padding-right{padding-right:16px!important}\n.w3-topbar{border-top:6px solid #ccc!important}.w3-bottombar{border-bottom:6px solid #ccc!important}\n.w3-leftbar{border-left:6px solid #ccc!important}.w3-rightbar{border-right:6px solid #ccc!important}\n.w3-row-padding,.w3-row-padding>.w3-half,.w3-row-padding>.w3-third,.w3-row-padding>.w3-twothird,.w3-row-padding>.w3-threequarter,.w3-row-padding>.w3-quarter,.w3-row-padding>.w3-col{padding:0 8px}\n.w3-spin{animation:w3-spin 2s infinite linear;-webkit-animation:w3-spin 2s infinite linear}\n@-webkit-keyframes w3-spin{0%{-webkit-transform:rotate(0deg);transform:rotate(0deg)}100%{-webkit-transform:rotate(359deg);transform:rotate(359deg)}}\n@keyframes w3-spin{0%{-webkit-transform:rotate(0deg);transform:rotate(0deg)}100%{-webkit-transform:rotate(359deg);transform:rotate(359deg)}}\n.w3-container{padding:0.01em 16px}\n.w3-panel{padding:0.01em 16px;margin-top:16px!important;margin-bottom:16px!important}\n.w3-example{background-color:#f1f1f1;padding:0.01em 16px}\n.w3-code,.w3-codespan{font-family:Consolas,\"courier new\";font-size:16px}\n.w3-code{line-height:1.4;width:auto;background-color:#fff;padding:8px 12px;border-left:4px solid #4CAF50;word-wrap:break-word}\n.w3-codespan{color:crimson;background-color:#f1f1f1;padding-left:4px;padding-right:4px;font-size:110%}\n.w3-example,.w3-code{margin:20px 0}.w3-card{border:1px solid #ccc}\n.w3-card-2,.w3-example{box-shadow:0 2px 4px 0 rgba(0,0,0,0.16),0 2px 10px 0 rgba(0,0,0,0.12)!important}\n.w3-card-4,.w3-hover-shadow:hover{box-shadow:0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19)!important}\n.w3-card-8{box-shadow:0 8px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19)!important}\n.w3-card-12{box-shadow:0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19)!important}\n.w3-card-16{box-shadow:0 16px 24px 0 rgba(0,0,0,0.22),0 25px 55px 0 rgba(0,0,0,0.21)!important}\n.w3-card-24{box-shadow:0 24px 24px 0 rgba(0,0,0,0.2),0 40px 77px 0 rgba(0,0,0,0.22)!important}\n.w3-animate-fading{-webkit-animation:fading 10s infinite;animation:fading 10s infinite}\n@-webkit-keyframes fading{0%{opacity:0}50%{opacity:1}100%{opacity:0}}\n@keyframes fading{0%{opacity:0}50%{opacity:1}100%{opacity:0}}\n.w3-animate-opacity{-webkit-animation:opac 0.8s;animation:opac 0.8s}\n@-webkit-keyframes opac{from{opacity:0} to{opacity:1}}\n@keyframes opac{from{opacity:0} to{opacity:1}}\n.w3-animate-top{position:relative;-webkit-animation:animatetop 0.4s;animation:animatetop 0.4s}\n@-webkit-keyframes animatetop{from{top:-300px;opacity:0} to{top:0;opacity:1}}\n@keyframes animatetop{from{top:-300px;opacity:0} to{top:0;opacity:1}}\n.w3-animate-left{position:relative;-webkit-animation:animateleft 0.4s;animation:animateleft 0.4s}\n@-webkit-keyframes animateleft{from{left:-300px;opacity:0} to{left:0;opacity:1}}\n@keyframes animateleft{from{left:-300px;opacity:0} to{left:0;opacity:1}}\n.w3-animate-right{position:relative;-webkit-animation:animateright 0.4s;animation:animateright 0.4s}\n@-webkit-keyframes animateright{from{right:-300px;opacity:0} to{right:0;opacity:1}}\n@keyframes animateright{from{right:-300px;opacity:0} to{right:0;opacity:1}}\n.w3-animate-bottom{position:relative;-webkit-animation:animatebottom 0.4s;animation:animatebottom 0.4s}\n@-webkit-keyframes animatebottom{from{bottom:-300px;opacity:0} to{bottom:0px;opacity:1}}\n@keyframes animatebottom{from{bottom:-300px;opacity:0} to{bottom:0;opacity:1}}\n.w3-animate-zoom {-webkit-animation:animatezoom 0.6s;animation:animatezoom 0.6s}\n@-webkit-keyframes animatezoom{from{-webkit-transform:scale(0)} to{-webkit-transform:scale(1)}}\n@keyframes animatezoom{from{transform:scale(0)} to{transform:scale(1)}}\n.w3-animate-input{-webkit-transition:width 0.4s ease-in-out;transition:width 0.4s ease-in-out}.w3-animate-input:focus{width:100%!important}\n.w3-opacity,.w3-hover-opacity:hover{opacity:0.60;-webkit-backface-visibility:hidden}\n.w3-opacity-off,.w3-hover-opacity-off:hover{opacity:1;-webkit-backface-visibility:hidden}\n.w3-opacity-max{opacity:0.25;-webkit-backface-visibility:hidden}\n.w3-opacity-min{opacity:0.75;-webkit-backface-visibility:hidden}\n.w3-greyscale-max,.w3-grayscale-max,.w3-hover-greyscale:hover,.w3-hover-grayscale:hover{-webkit-filter:grayscale(100%);filter:grayscale(100%)}\n.w3-greyscale,.w3-grayscale{-webkit-filter:grayscale(75%);filter:grayscale(75%)}\n.w3-greyscale-min,.w3-grayscale-min{-webkit-filter:grayscale(50%);filter:grayscale(50%)}\n.w3-sepia{-webkit-filter:sepia(75%);filter:sepia(75%)}\n.w3-sepia-max,.w3-hover-sepia:hover{-webkit-filter:sepia(100%);filter:sepia(100%)}\n.w3-sepia-min{-webkit-filter:sepia(50%);filter:sepia(50%)}\n.w3-text-shadow{text-shadow:1px 1px 0 #444}.w3-text-shadow-white{text-shadow:1px 1px 0 #ddd}\n.w3-transparent{background-color:transparent!important}\n.w3-hover-none:hover{box-shadow:none!important;background-color:transparent!important}\n/* Colors */\n.w3-amber,.w3-hover-amber:hover{color:#000!important;background-color:#ffc107!important}\n.w3-aqua,.w3-hover-aqua:hover{color:#000!important;background-color:#00ffff!important}\n.w3-blue,.w3-hover-blue:hover{color:#fff!important;background-color:#2196F3!important}\n.w3-light-blue,.w3-hover-light-blue:hover{color:#000!important;background-color:#87CEEB!important}\n.w3-brown,.w3-hover-brown:hover{color:#fff!important;background-color:#795548!important}\n.w3-cyan,.w3-hover-cyan:hover{color:#000!important;background-color:#00bcd4!important}\n.w3-blue-grey,.w3-hover-blue-grey:hover,.w3-blue-gray,.w3-hover-blue-gray:hover{color:#fff!important;background-color:#607d8b!important}\n.w3-green,.w3-hover-green:hover{color:#fff!important;background-color:#4CAF50!important}\n.w3-light-green,.w3-hover-light-green:hover{color:#000!important;background-color:#8bc34a!important}\n.w3-indigo,.w3-hover-indigo:hover{color:#fff!important;background-color:#3f51b5!important}\n.w3-khaki,.w3-hover-khaki:hover{color:#000!important;background-color:#f0e68c!important}\n.w3-lime,.w3-hover-lime:hover{color:#000!important;background-color:#cddc39!important}\n.w3-orange,.w3-hover-orange:hover{color:#000!important;background-color:#ff9800!important}\n.w3-deep-orange,.w3-hover-deep-orange:hover{color:#fff!important;background-color:#ff5722!important}\n.w3-pink,.w3-hover-pink:hover{color:#fff!important;background-color:#e91e63!important}\n.w3-purple,.w3-hover-purple:hover{color:#fff!important;background-color:#9c27b0!important}\n.w3-deep-purple,.w3-hover-deep-purple:hover{color:#fff!important;background-color:#673ab7!important}\n.w3-red,.w3-hover-red:hover{color:#fff!important;background-color:#f44336!important}\n.w3-sand,.w3-hover-sand:hover{color:#000!important;background-color:#fdf5e6!important}\n.w3-teal,.w3-hover-teal:hover{color:#fff!important;background-color:#009688!important}\n.w3-yellow,.w3-hover-yellow:hover{color:#000!important;background-color:#ffeb3b!important}\n.w3-white,.w3-hover-white:hover{color:#000!important;background-color:#fff!important}\n.w3-black,.w3-hover-black:hover{color:#fff!important;background-color:#000!important}\n.w3-grey,.w3-hover-grey:hover,.w3-gray,.w3-hover-gray:hover{color:#000!important;background-color:#9e9e9e!important}\n.w3-light-grey,.w3-hover-light-grey:hover,.w3-light-gray,.w3-hover-light-gray:hover{color:#000!important;background-color:#f1f1f1!important}\n.w3-dark-grey,.w3-hover-dark-grey:hover,.w3-dark-gray,.w3-hover-dark-gray:hover{color:#fff!important;background-color:#616161!important}\n.w3-pale-red,.w3-hover-pale-red:hover{color:#000!important;background-color:#ffdddd!important}\n.w3-pale-green,.w3-hover-pale-green:hover{color:#000!important;background-color:#ddffdd!important}\n.w3-pale-yellow,.w3-hover-pale-yellow:hover{color:#000!important;background-color:#ffffcc!important}\n.w3-pale-blue,.w3-hover-pale-blue:hover{color:#000!important;background-color:#ddffff!important}\n.w3-text-amber,.w3-hover-text-amber:hover{color:#ffc107!important}\n.w3-text-aqua,.w3-hover-text-aqua:hover{color:#00ffff!important}\n.w3-text-blue,.w3-hover-text-blue:hover{color:#2196F3!important}\n.w3-text-light-blue,.w3-hover-text-light-blue:hover{color:#87CEEB!important}\n.w3-text-brown,.w3-hover-text-brown:hover{color:#795548!important}\n.w3-text-cyan,.w3-hover-text-cyan:hover{color:#00bcd4!important}\n.w3-text-blue-grey,.w3-hover-text-blue-grey:hover,.w3-text-blue-gray,.w3-hover-text-blue-gray:hover{color:#607d8b!important}\n.w3-text-green,.w3-hover-text-green:hover{color:#4CAF50!important}\n.w3-text-light-green,.w3-hover-text-light-green:hover{color:#8bc34a!important}\n.w3-text-indigo,.w3-hover-text-indigo:hover{color:#3f51b5!important}\n.w3-text-khaki,.w3-hover-text-khaki:hover{color:#b4aa50!important}\n.w3-text-lime,.w3-hover-text-lime:hover{color:#cddc39!important}\n.w3-text-orange,.w3-hover-text-orange:hover{color:#ff9800!important}\n.w3-text-deep-orange,.w3-hover-text-deep-orange:hover{color:#ff5722!important}\n.w3-text-pink,.w3-hover-text-pink:hover{color:#e91e63!important}\n.w3-text-purple,.w3-hover-text-purple:hover{color:#9c27b0!important}\n.w3-text-deep-purple,.w3-hover-text-deep-purple:hover{color:#673ab7!important}\n.w3-text-red,.w3-hover-text-red:hover{color:#f44336!important}\n.w3-text-sand,.w3-hover-text-sand:hover{color:#fdf5e6!important}\n.w3-text-teal,.w3-hover-text-teal:hover{color:#009688!important}\n.w3-text-yellow,.w3-hover-text-yellow:hover{color:#d2be0e!important}\n.w3-text-white,.w3-hover-text-white:hover{color:#fff!important}\n.w3-text-black,.w3-hover-text-black:hover{color:#000!important}\n.w3-text-grey,.w3-hover-text-grey:hover,.w3-text-gray,.w3-hover-text-gray:hover{color:#757575!important}\n.w3-text-light-grey,.w3-hover-text-light-grey:hover,.w3-text-light-gray,.w3-hover-text-light-gray:hover{color:#f1f1f1!important}\n.w3-text-dark-grey,.w3-hover-text-dark-grey:hover,.w3-text-dark-gray,.w3-hover-text-dark-gray:hover{color:#3a3a3a!important}\n.w3-border-amber,.w3-hover-border-amber:hover{border-color:#ffc107!important}\n.w3-border-aqua,.w3-hover-border-aqua:hover{border-color:#00ffff!important}\n.w3-border-blue,.w3-hover-border-blue:hover{border-color:#2196F3!important}\n.w3-border-light-blue,.w3-hover-border-light-blue:hover{border-color:#87CEEB!important}\n.w3-border-brown,.w3-hover-border-brown:hover{border-color:#795548!important}\n.w3-border-cyan,.w3-hover-border-cyan:hover{border-color:#00bcd4!important}\n.w3-border-blue-grey,.w3-hover-border-blue-grey:hover,.w3-border-blue-gray,.w3-hover-border-blue-gray:hover{border-color:#607d8b!important}\n.w3-border-green,.w3-hover-border-green:hover{border-color:#4CAF50!important}\n.w3-border-light-green,.w3-hover-border-light-green:hover{border-color:#8bc34a!important}\n.w3-border-indigo,.w3-hover-border-indigo:hover{border-color:#3f51b5!important}\n.w3-border-khaki,.w3-hover-border-khaki:hover{border-color:#f0e68c!important}\n.w3-border-lime,.w3-hover-border-lime:hover{border-color:#cddc39!important}\n.w3-border-orange,.w3-hover-border-orange:hover{border-color:#ff9800!important}\n.w3-border-deep-orange,.w3-hover-border-deep-orange:hover{border-color:#ff5722!important}\n.w3-border-pink,.w3-hover-border-pink:hover{border-color:#e91e63!important}\n.w3-border-purple,.w3-hover-border-purple:hover{border-color:#9c27b0!important}\n.w3-border-deep-purple,.w3-hover-border-deep-purple:hover{border-color:#673ab7!important}\n.w3-border-red,.w3-hover-border-red:hover{border-color:#f44336!important}\n.w3-border-sand,.w3-hover-border-sand:hover{border-color:#fdf5e6!important}\n.w3-border-teal,.w3-hover-border-teal:hover{border-color:#009688!important}\n.w3-border-yellow,.w3-hover-border-yellow:hover{border-color:#ffeb3b!important}\n.w3-border-white,.w3-hover-border-white:hover{border-color:#fff!important}\n.w3-border-black,.w3-hover-border-black:hover{border-color:#000!important}\n.w3-border-grey,.w3-hover-border-grey:hover,.w3-border-gray,.w3-hover-border-gray:hover{border-color:#9e9e9e!important}\n.w3-border-light-grey,.w3-hover-border-light-grey:hover,.w3-border-light-gray,.w3-hover-border-light-gray:hover{border-color:#f1f1f1!important}\n.w3-border-dark-grey,.w3-hover-border-dark-grey:hover,.w3-border-dark-gray,.w3-hover-border-dark-gray:hover{border-color:#616161!important}\n.w3-border-pale-red,.w3-hover-border-pale-red:hover{border-color:#ffe7e7!important}.w3-border-pale-green,.w3-hover-border-pale-green:hover{border-color:#e7ffe7!important}\n.w3-border-pale-yellow,.w3-hover-border-pale-yellow:hover{border-color:#ffffcc!important}.w3-border-pale-blue,.w3-hover-border-pale-blue:hover{border-color:#e7ffff!important}\n\n"; });
define('text!navitem.html', ['module'], function(module) { module.exports = "<template>\n  <a href=\"${href}\" class=\"w3-button  w3-small w3-padding-4 vf-right-border\">\n  <slot></slot>\n  </a>\n</template>\n"; });
define('text!components/navigation.html', ['module'], function(module) { module.exports = "<!-- components/navigation.html -->\n<template bindable=\"router\">\n  <!-- breadcrumbs like\n  <span repeat.for=\"row of router.navigation\" class=\"${row.isActive ? 'active' : ''}\">\n    <a class=\"w3-button  w3-small w3-padding-4 vf-right-border\" href.bind=\"row.href\">${row.title}</a>\n  </span>\n  -->\n  <nav style=\"float:left\">\n\n    <ul>\n      <li style=\"padding:0!important\">\n        <!--<li class=\"w3-dropdown-hover\">-->\n        <a href=\"index.html\">menu</div>\n        </div>\n        </a>\n        <ul><!-- class=\"w3-dropdown-content w3-white w3-card-4\">-->\n          <li repeat.for=\"row of router.navigation\" class=\"${row.isActive ? 'active' : ''}\">\n            <a href.bind=\"row.href\">${row.title}</a>\n          </li>\n          <li><button onclick=\"document.getElementById('id01').style.display='block'\" class=\"w3-button\">About</button></li>\n        </ul>\n      </li>\n\n    </ul>\n  </nav>\n</template>\n\n"; });
define('text!facilityadmin/facilityadmin.html', ['module'], function(module) { module.exports = "<template>\n      <h3>Facility Admin</h3>\n      <p>\n        Can set facility settings, arrange visit, see who is requesting visit for his facility\n</p>\n</template>\n"; });
define('text!home/home.html', ['module'], function(module) { module.exports = "<template>\n  <h2>Home</h2>\n  <p>You can register and schedule a new visit or ammend existing one.</p>\n  <a href=\"#login\" class=\"w3-button\">Login</a>\n\n</template>\n"; });
define('text!instructstaff/instructstaff.html', ['module'], function(module) { module.exports = "<template>\n      <h3>Instruct Staff</h3>\n  <p>\n    Can manage request for visit - approve, reject ...\n  </p>\n</template>\n"; });
define('text!login/login.html', ['module'], function(module) { module.exports = "<template>\n      <h3>Login</h3>\n      <p>\n        You can login as\n        <a href=\"#visitingscientist\" class=\"w3-button\">Visiting Scientist</a> to select facility and request visit to perform experiment.\n      </p>\n      <p>\n        You can login as\n        <a href=\"#facilityadmin\" class=\"w3-button\">Facility Admin</a> Arrange a visit, set available facilities, dates, etc.\n      </p>\n      <p>\n        You can login as\n        <a href=\"#instructstaff\" class=\"w3-button\">Instruct staff</a> Arrange a visit.\n      </p>\n</template>\n"; });
define('text!visitingscientist/newproposal.html', ['module'], function(module) { module.exports = "<template>\n  <h3>New visit proposal</h3>\n  <form role = \"form\" submit.delegate = \"submit()\">\n    <table>\n      <thead>\n      <tr>\n        <th style=\"width:40%\"></th><th style=\"width:60%\"></th>\n      </tr>\n      </thead>\n      <tbody>\n      <tr repeat.for=\"item of proposalform\">\n        <td><label>${item.title}</label></td>\n\n        <td if.bind=\"item.conditional\"><input type=\"checkbox\" checked.bind = \"item.value\">\n          <span if.bind=\"item.longtext\">\n          <textarea if.bind=\"item.value\" value.bind=\"item.detail\" placeholder.bind=\"item.title\" style=\"width:100%\"></textarea>\n          </span>\n        </td>\n        <td if.bind=\"!item.conditional\">\n          <input  if.bind=\"!item.longtext\" value.bind=\"item.value\" placeholder.bind=\"item.title\" style=\"width:100%\">\n          <textarea if.bind=\"item.longtext\" value.bind=\"item.value\" placeholder.bind=\"item.title\" style=\"width:100%\"></textarea>\n        </td>\n      </tr>\n      <tr><td></td><td><button type = \"submit\">Submit</button></td></tr>\n      </tbody>\n    </table>\n  </form>\n</template>\n"; });
define('text!visitingscientist/visitingscientist.html', ['module'], function(module) { module.exports = "<template>\n      <h3>Visiting Scientist</h3>\n      <p>Create New Proposal for a Visit\n        <a href=\"#newproposal\" class=\"w3-button\">New Proposal</a>\n      </p>\n      <p>Current proposals:</p>\n      <table>\n        <tr>\n          <th>pid</th><th>title</th><th>statusID</th><th>status</th>\n        </tr>\n        <tr repeat.for=\"proposal of proposals\" >\n          <td>${proposal.pid}</td><td>${proposal.title}</td><td>${proposal.statusID}</td><td>${proposal.status}</td>\n        </tr>\n      </table>\n</template>\n"; });
//# sourceMappingURL=app-bundle.js.map