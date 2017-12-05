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
      config.title = 'West-Life Repository Router';
    };

    App.prototype.login = function login() {};

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
define('pickerclient/pickerclient',["exports", "aurelia-framework"], function (exports, _aureliaFramework) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Pickerclient = undefined;

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

  var Pickerclient = exports.Pickerclient = (_class = function () {
    function Pickerclient() {
      var _this = this;

      _classCallCheck(this, Pickerclient);

      _initDefineProp(this, "mode", _descriptor, this);

      this.href = "https://portal.west-life.eu/virtualfolder/filepickercomponent.html";
      this.href2 = "https://portal.west-life.eu/virtualfolder/uploaddirpickercomponent.html";
      this.vfurl = "";

      this.receiveMessage = function (e) {
        console.log("received event");
        console.log(e);
        _this.vfurl = e.data;
      };
      console.log("Pickerclient()");
      console.log(this.mode);
    }

    Pickerclient.prototype.attached = function attached() {
      window.addEventListener("message", this.receiveMessage);
      console.log("Pickerclient attached()");
      console.log(this.mode);
      this.filepicker = this.mode === "file";
    };

    Pickerclient.prototype.detached = function detached() {
      window.removeEventListener("message", this.receiveMessage);
    };

    Pickerclient.prototype.openfilewindow = function openfilewindow() {
      this.popup = window.open(this.href, 'newwindow', 'width=640, height=480');
    };

    Pickerclient.prototype.opendirwindow = function opendirwindow() {
      this.popup = window.open(this.href2, 'newwindow', 'width=640, height=480');
    };

    return Pickerclient;
  }(), (_descriptor = _applyDecoratedDescriptor(_class.prototype, "mode", [_aureliaFramework.bindable], {
    enumerable: true,
    initializer: null
  })), _class);
});
define('repository/app',['exports'], function (exports) {
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
      config.title = 'West-Life Repository Router';
      config.map([{ route: ['', 'home'], name: 'home', moduleId: 'scientist/visitingscientist', nav: true, title: 'Dataset Dashboard' }, { route: 'repositorystaff', name: 'repositorystaff', moduleId: 'repositorystaff/repositorystaff', nav: true, title: 'File Upload Tool' }, { route: 'repositorytovf', name: 'repositorytovf', moduleId: 'scientist/repositorytovf', nav: true, title: 'Upload to Virtual Folder' }]);
      this.router = router;
    };

    App.prototype.login = function login() {};

    return App;
  }();
});
define('repository/main',['exports', '../environment'], function (exports, _environment) {
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
define('repositorystaff/repositorystaff',["exports", "aurelia-http-client"], function (exports, _aureliaHttpClient) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Repositorystaff = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var Repositorystaff = exports.Repositorystaff = function () {
    function Repositorystaff() {
      _classCallCheck(this, Repositorystaff);

      console.log("Repositorystaff()");
      this.visitors = ["Tomas Kulhanek", "Andrea Giacchieti", "Antonio Rosatto"];
      this.items = [{ date: "06/09/2017", summary: "spectrum of strychnine process with v_noesy_pro.mac (NUTS-Pro) or v_noesy.mac (NUTS-2D)", info: "1.6 Mb" }, { date: "07/09/2017", summary: " spectrum of sucrose (1.3 Mbytes); process with v_ghsqc_pro.mac (NUTS-Pro) or v_ghsqc.mac (NUTS-2D)", info: "1.3 Mb" }, { date: "08/09/2017", summary: "spectrum of strychnine (2.1 Mbytes); process with v_hsqc_pro.mac (NUTS-Pro) or v_hsqc.mac (NUTS-2D).", info: "2.1 Mb" }];
      this.filestoupload = [];
      this.uploadfiles = [];
      this.uploaddir = "";
      this.selectinguser = true;
      this.selectedvisitor = "";
    }

    Repositorystaff.prototype.attached = function attached() {};

    Repositorystaff.prototype.selectitem = function selectitem(item) {
      console.log("Selected", item);
    };

    Repositorystaff.prototype.selectvisitor = function selectvisitor(visitor) {
      this.selectinguser = false;
      this.selectedvisitor = visitor;
    };

    Repositorystaff.prototype.deselectvisitor = function deselectvisitor() {
      this.selectinguser = true;
    };

    Repositorystaff.prototype.deleteitem = function deleteitem(item) {
      var indexremoved = this.items.indexOf(item);
      if (indexremoved >= 0) this.items.splice(indexremoved, 1);
    };

    Repositorystaff.prototype.selectItemToUpload = function selectItemToUpload(item) {
      console.log("selected item");
      console.log(item);
    };

    Repositorystaff.prototype.removeItemToUpload = function removeItemToUpload(item) {
      console.log("selected item");
      console.log(item);
      var i = this.filestoupload.indexOf(item);
      this.filestoupload.splice(i, 1);
    };

    Repositorystaff.prototype.dropped = function dropped(event) {
      var _filestoupload;

      console.log("Dropped");
      console.log(event.dataTransfer.files);
      event.stopPropagation();
      event.preventDefault();
      (_filestoupload = this.filestoupload).unshift.apply(_filestoupload, event.dataTransfer.files);
      return true;
    };

    Repositorystaff.prototype.dragged = function dragged(event) {
      return true;
    };

    Repositorystaff.prototype.appendFiles = function appendFiles(event) {
      var _filestoupload2;

      console.log("appendFiles()");
      console.log(event.target.files);

      (_filestoupload2 = this.filestoupload).unshift.apply(_filestoupload2, event.target.files);
    };

    Repositorystaff.prototype.appendDir = function appendDir(event) {
      var _filestoupload3;

      console.log("appendDir");
      console.log(event.target.files);
      (_filestoupload3 = this.filestoupload).unshift.apply(_filestoupload3, event.target.files);
    };

    Repositorystaff.prototype.submitUpload = function submitUpload() {
      var _items;

      (_items = this.items).unshift.apply(_items, this.filestoupload);
      this.filestoupload = [];
    };

    return Repositorystaff;
  }();
});
define('resources/index',["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.configure = configure;
  function configure(config) {}
});
define('scientist/repositorytovf',["exports", "aurelia-http-client"], function (exports, _aureliaHttpClient) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.Repositorytovf = undefined;

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  var Repositorytovf = exports.Repositorytovf = function () {
    function Repositorytovf() {
      _classCallCheck(this, Repositorytovf);

      console.log("Repositorytovf()");

      this.items = [{ date: "06/09/2017", summary: "spectrum of strychnine process with v_noesy_pro.mac (NUTS-Pro) or v_noesy.mac (NUTS-2D)", info: "1.6 Mb" }, { date: "07/09/2017", summary: " spectrum of sucrose (1.3 Mbytes); process with v_ghsqc_pro.mac (NUTS-Pro) or v_ghsqc.mac (NUTS-2D)", info: "1.3 Mb" }, { date: "08/09/2017", summary: "spectrum of strychnine (2.1 Mbytes); process with v_hsqc_pro.mac (NUTS-Pro) or v_hsqc.mac (NUTS-2D).", info: "2.1 Mb" }];
      this.filestoupload = [];
      this.uploadfiles = [];
      this.uploaddir = "";
      this.selectingfiles = true;
    }

    Repositorytovf.prototype.attached = function attached() {};

    Repositorytovf.prototype.selectitem = function selectitem(item) {
      console.log("Selected", item);
    };

    Repositorytovf.prototype.submitfiles = function submitfiles() {
      this.selectingfiles = false;
    };

    Repositorytovf.prototype.unsubmitfiles = function unsubmitfiles() {
      this.selectingfiles = true;
    };

    Repositorytovf.prototype.deleteitem = function deleteitem(item) {
      var indexremoved = this.items.indexOf(item);
      if (indexremoved >= 0) this.items.splice(indexremoved, 1);
    };

    Repositorytovf.prototype.selectItemToUpload = function selectItemToUpload(item) {
      console.log("selected item");
      console.log(item);
    };

    Repositorytovf.prototype.removeItemToUpload = function removeItemToUpload(item) {
      console.log("selected item");
      console.log(item);
      var i = this.filestoupload.indexOf(item);
      this.filestoupload.splice(i, 1);
    };

    Repositorytovf.prototype.dropped = function dropped(event) {
      var _filestoupload;

      console.log("Dropped");
      console.log(event.dataTransfer.files);
      event.stopPropagation();
      event.preventDefault();
      (_filestoupload = this.filestoupload).unshift.apply(_filestoupload, event.dataTransfer.files);
      return true;
    };

    Repositorytovf.prototype.dragged = function dragged(event) {
      return true;
    };

    Repositorytovf.prototype.appendFiles = function appendFiles(event) {
      var _filestoupload2;

      console.log("appendFiles()");
      console.log(event.target.files);

      (_filestoupload2 = this.filestoupload).unshift.apply(_filestoupload2, event.target.files);
    };

    Repositorytovf.prototype.appendDir = function appendDir(event) {
      var _filestoupload3;

      console.log("appendDir");
      console.log(event.target.files);
      (_filestoupload3 = this.filestoupload).unshift.apply(_filestoupload3, event.target.files);
    };

    Repositorytovf.prototype.submitUpload = function submitUpload() {
      var _items;

      (_items = this.items).unshift.apply(_items, this.filestoupload);
      this.filestoupload = [];
    };

    return Repositorytovf;
  }();
});
define('scientist/visitingscientist',["exports", "aurelia-http-client"], function (exports, _aureliaHttpClient) {
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

  var _class, _temp;

  var Visitingscientist = exports.Visitingscientist = (_temp = _class = function () {
    function Visitingscientist(httpclient) {
      _classCallCheck(this, Visitingscientist);

      this.httpclient = httpclient;
      console.log("VisitingScientist()");
      this.proposals = [];
      this.items = [];
      this.projectserviceurl = "/restcon/project";
      this.nexturl = window.location;
      this.showProposals = true;
    }

    Visitingscientist.prototype.attached = function attached() {
      var _this = this;

      console.log("VisitingScientist atached()");
      console.log(this.nexturl);
      this.httpclient.get(this.projectserviceurl).then(function (data) {
        console.log(data);
        if (data.response) {
          _this.proposalsall = JSON.parse(data.response);
          _this.proposals = _this.proposalsall.slice(0, 3);
          _this.proposalslength = _this.proposalsall.length;
          _this.showallbutton = _this.proposalsall.length > 3;
          _this.showmorebutton = true;
        }
      }).catch(function (error) {
        console.log(error);
        alert("Sorry, error:" + error.statusCode + " " + error.message);
      });
    };

    Visitingscientist.prototype.switchMoreLessProposals = function switchMoreLessProposals() {
      if (this.showmorebutton) {
        this.proposals = this.proposalsall;
        this.showmorebutton = false;
      } else {
        this.proposals = this.proposalsall.slice(0, 3);
        this.showmorebutton = true;
      }
    };

    Visitingscientist.prototype.selectProposal = function selectProposal(item) {
      console.log(item);
      this.selectedProposal = item;
      this.showProposals = false;
    };

    Visitingscientist.prototype.showAllProposals = function showAllProposals() {
      this.showProposals = true;
    };

    return Visitingscientist;
  }(), _class.inject = [_aureliaHttpClient.HttpClient], _temp);
});
define('text!app.html', ['module'], function(module) { module.exports = "<template>\n  <require from=\"./w3.css\"></require>\n  <require from=\"components/heads.css\"></require>\n  <require from=\"components/sharedheader.html\"></require>\n  <require from=\"components/sharedfooter.html\"></require>\n  <sharedheader></sharedheader>\n  <div class=\"w3-card-2 w3-margin w3-padding\">\n  <p>\n    West-life Repository is standalone web application for experimental facilities that are newly embarking on data management.\n  </p>\n  <p>\n    This is reference implementation of a repository that supplies\n  </p>\n    <ul>\n  <li> suitable metadata to the portal (D6.2) matching the metadata standards to be devised in WP7.</li>\n  <li> registering a new project, </li>\n  <li> adding files to a project. </li>\n</ul>\n\n  <p>\n    Features in development, not yet implemented:\n  </p>\n  <ul>\n  <li>The implementation use the CERIF standard.</li>\n  <li>It will be compatible with existing CRIS repositories,</li>\n  <li>and also be capable of assigning an URI to a project if it is not yet recorded in a CRIS repository.</li>\n</ul>\n  <p>\n  To continue:\n  </p>\n  <ul>\n    <li><a href=\"repository/index.html\">Visiting scientist - requires login via West-Life SSO</a></li>\n    <li><a href=\"http://localhost:8080/index.html\">Staff access - available only from local workstation</a></li>\n  </ul>\n  </div>\n\n  <sharedfooter></sharedfooter>\n</template>\n"; });
define('text!icons.css', ['module'], function(module) { module.exports = ".fa {\n  display: inline-block;\n  font-size: inherit;\n  text-rendering: auto;\n  -webkit-font-smoothing: antialiased;\n  -moz-osx-font-smoothing: grayscale;\n  width: 16px;\n}\n.fa-remove:before,\n.fa-close:before,\n.fa-times:before {\n  content: url(\"data:image/svg+xml,%3Csvg%20version%3D%271.1%27%20width%3D%27100%25%27%20xmlns%3D%27http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%27%20viewBox%3D%270%200%2021.9%2021.9%27%20enable-background%3D%27new%200%200%2021.9%2021.9%27%3E%20%3Cg%3E%20%3Cg%3E%20%3Cpath%20d%3D%27M14.1%2C11.3c-0.2-0.2-0.2-0.5%2C0-0.7l7.5-7.5c0.2-0.2%2C0.3-0.5%2C0.3-0.7s-0.1-0.5-0.3-0.7l-1.4-1.4C20%2C0.1%2C19.7%2C0%2C19.5%2C0%20%20c-0.3%2C0-0.5%2C0.1-0.7%2C0.3l-7.5%2C7.5c-0.2%2C0.2-0.5%2C0.2-0.7%2C0L3.1%2C0.3C2.9%2C0.1%2C2.6%2C0%2C2.4%2C0S1.9%2C0.1%2C1.7%2C0.3L0.3%2C1.7C0.1%2C1.9%2C0%2C2.2%2C0%2C2.4%20%20s0.1%2C0.5%2C0.3%2C0.7l7.5%2C7.5c0.2%2C0.2%2C0.2%2C0.5%2C0%2C0.7l-7.5%2C7.5C0.1%2C19%2C0%2C19.3%2C0%2C19.5s0.1%2C0.5%2C0.3%2C0.7l1.4%2C1.4c0.2%2C0.2%2C0.5%2C0.3%2C0.7%2C0.3%20%20s0.5-0.1%2C0.7-0.3l7.5-7.5c0.2-0.2%2C0.5-0.2%2C0.7%2C0l7.5%2C7.5c0.2%2C0.2%2C0.5%2C0.3%2C0.7%2C0.3s0.5-0.1%2C0.7-0.3l1.4-1.4c0.2-0.2%2C0.3-0.5%2C0.3-0.7%20%20s-0.1-0.5-0.3-0.7L14.1%2C11.3z%27%2F%3E%20%3C%2Fg%3E%20%3C%2Fg%3E%20%3C%2Fsvg%3E \");\n}\n.fa-cog:before {\n  content: url(\"data:image/svg+xml,%3Csvg%20version%3D%271.1%27%20xmlns%3D%27http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%27%20x%3D%270px%27%20y%3D%270px%27%20viewBox%3D%270%200%20489.7%20489.7%27%20style%3D%27enable-background%3Anew%200%200%20489.7%20489.7%3B%27%3E%20%20%3Cg%3E%20%20%3Cg%3E%20%20%3Cpath%20d%3D%27M60.6%2C461.95c0%2C6.8%2C5.5%2C12.3%2C12.3%2C12.3s12.3-5.5%2C12.3-12.3v-301.6c34.4-5.9%2C60.8-35.8%2C60.8-71.9c0-40.3-32.8-73-73-73%20%20s-73%2C32.7-73%2C73c0%2C36.1%2C26.3%2C66%2C60.8%2C71.9v301.6H60.6z%20M24.3%2C88.45c0-26.7%2C21.8-48.5%2C48.5-48.5s48.5%2C21.8%2C48.5%2C48.5%20%20s-21.8%2C48.5-48.5%2C48.5S24.3%2C115.25%2C24.3%2C88.45z%27%2F%3E%20%20%3Cpath%20d%3D%27M317.1%2C401.25c0-36.1-26.3-66-60.8-71.9V27.75c0-6.8-5.5-12.3-12.3-12.3s-12.3%2C5.5-12.3%2C12.3v301.6%20%20c-34.4%2C5.9-60.8%2C35.8-60.8%2C71.9c0%2C40.3%2C32.8%2C73%2C73%2C73S317.1%2C441.45%2C317.1%2C401.25z%20M195.6%2C401.25c0-26.7%2C21.8-48.5%2C48.5-48.5%20%20s48.5%2C21.8%2C48.5%2C48.5s-21.8%2C48.5-48.5%2C48.5S195.6%2C427.95%2C195.6%2C401.25z%27%2F%3E%20%20%3Cpath%20d%3D%27M416.6%2C474.25c6.8%2C0%2C12.3-5.5%2C12.3-12.3v-301.6c34.4-5.9%2C60.8-35.8%2C60.8-71.9c0-40.3-32.8-73-73-73s-73%2C32.7-73%2C73%20%20c0%2C36.1%2C26.3%2C66%2C60.8%2C71.9v301.6C404.3%2C468.75%2C409.8%2C474.25%2C416.6%2C474.25z%20M368.1%2C88.45c0-26.7%2C21.8-48.5%2C48.5-48.5%20%20s48.5%2C21.8%2C48.5%2C48.5s-21.8%2C48.5-48.5%2C48.5C389.8%2C136.95%2C368.1%2C115.25%2C368.1%2C88.45z%27%2F%3E%20%20%3C%2Fg%3E%20%20%3C%2Fg%3E%20%20%3C%2Fsvg%3E  \");\n}\n.fa-window-minimize:before{\n  content: url(\"data:image/svg+xml,%3Csvg%20version%3D%271.1%27%20xmlns%3D%27http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%27%20x%3D%270px%27%20y%3D%270px%27%20viewBox%3D%270%200%20489.3%20489.3%27%20style%3D%27enable-background%3Anew%200%200%20489.3%20489.3%3B%27%3E%3Cg%3E%09%3Cg%3E%09%09%3Cpath%20d%3D%27M0%2C12.251v464.7c0%2C6.8%2C5.5%2C12.3%2C12.3%2C12.3h224c6.8%2C0%2C12.3-5.5%2C12.3-12.3s-5.5-12.3-12.3-12.3H24.5v-440.2h440.2v210.5%09%09%09c0%2C6.8%2C5.5%2C12.2%2C12.3%2C12.2s12.3-5.5%2C12.3-12.2v-222.7c0-6.8-5.5-12.2-12.3-12.2H12.3C5.5-0.049%2C0%2C5.451%2C0%2C12.251z%27%2F%3E%09%09%3Cpath%20d%3D%27M476.9%2C489.151c6.8%2C0%2C12.3-5.5%2C12.3-12.3v-170.3c0-6.8-5.5-12.3-12.3-12.3H306.6c-6.8%2C0-12.3%2C5.5-12.3%2C12.3v170.4%09%09%09c0%2C6.8%2C5.5%2C12.3%2C12.3%2C12.3h170.3V489.151z%20M318.8%2C318.751h145.9v145.9H318.8V318.751z%27%2F%3E%09%09%3Cpath%20d%3D%27M135.9%2C257.651c0%2C6.8%2C5.5%2C12.3%2C12.3%2C12.3h109.5c6.8%2C0%2C12.3-5.5%2C12.3-12.3v-109.5c0-6.8-5.5-12.3-12.3-12.3%09%09%09s-12.3%2C5.5-12.3%2C12.3v79.9l-138.7-138.7c-4.8-4.8-12.5-4.8-17.3%2C0c-4.8%2C4.8-4.8%2C12.5%2C0%2C17.3l138.7%2C138.7h-79.9%09%09%09C141.4%2C245.351%2C135.9%2C250.851%2C135.9%2C257.651z%27%2F%3E%09%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E\");\n}\n.fa-window-maximize:before{\n  content: url(\"data:image/svg+xml,%3Csvg%20version%3D%271.1%27%20xmlns%3D%27http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%27%20x%3D%270px%27%20y%3D%270px%27%20viewBox%3D%270%200%20258.008%20258.008%27%20style%3D%27enable-background%3Anew%200%200%20258.008%20258.008%3B%27%20%20xml%3Aspace%3D%27preserve%27%3E%20%20%3Cg%3E%20%20%3Cg%3E%20%20%3Cpath%20d%3D%27M125.609%2C122.35H10.049C4.5%2C122.35%2C0%2C126.85%2C0%2C132.399v115.56c0%2C5.549%2C4.5%2C10.048%2C10.049%2C10.048H125.61%20%20c5.548%2C0%2C10.046-4.499%2C10.046-10.048v-115.56C135.656%2C126.85%2C131.158%2C122.35%2C125.609%2C122.35z%20M115.559%2C237.909H20.098v-95.463%20%20h95.461V237.909z%27%2F%3E%20%20%3Cpath%20d%3D%27M247.958%2C0.001H10.049C4.5%2C0.001%2C0%2C4.5%2C0%2C10.049v93.312c0%2C5.55%2C4.5%2C10.05%2C10.049%2C10.05c5.55%2C0%2C10.049-4.5%2C10.049-10.05%20%20V20.098h217.812v217.812h-82.915c-5.55%2C0-10.05%2C4.5-10.05%2C10.05c0%2C5.549%2C4.5%2C10.048%2C10.05%2C10.048h92.964%20%20c5.55%2C0%2C10.05-4.499%2C10.05-10.048V10.049C258.008%2C4.5%2C253.508%2C0.001%2C247.958%2C0.001z%27%2F%3E%20%20%3Cpath%20d%3D%27M154.35%2C106.876c1.965%2C1.961%2C4.534%2C2.942%2C7.105%2C2.942c2.57%2C0%2C5.142-0.981%2C7.104-2.942l31.755-31.757V89.57%20%20c0%2C5.549%2C4.499%2C10.047%2C10.05%2C10.047c5.549%2C0%2C10.048-4.498%2C10.048-10.047V53.054c0-0.365-0.068-0.713-0.107-1.068%20%20c0.329-2.933-0.588-5.979-2.837-8.229c-2.146-2.148-5.023-3.079-7.831-2.873c-0.233-0.017-0.461-0.072-0.696-0.072h-36.513%20%20c-5.551%2C0-10.051%2C4.5-10.051%2C10.05c0%2C5.549%2C4.5%2C10.049%2C10.051%2C10.049h13.679L154.35%2C92.665%20%20C150.426%2C96.589%2C150.426%2C102.952%2C154.35%2C106.876z%27%2F%3E%20%20%3C%2Fg%3E%20%20%3C%2Fg%3E%20%20%3C%2Fsvg%3E\");\n/*  content: url(\"data:image/svg+xml;utf8,<svg version='1.1' xmlns='http://www.w3.org/2000/svg' x='0px' y='0px' viewBox='0 0 258.008 258.008' style='enable-background:new 0 0 258.008 258.008;'  xml:space='preserve'>  <g>  <g>  <path d='M125.609,122.35H10.049C4.5,122.35,0,126.85,0,132.399v115.56c0,5.549,4.5,10.048,10.049,10.048H125.61  c5.548,0,10.046-4.499,10.046-10.048v-115.56C135.656,126.85,131.158,122.35,125.609,122.35z M115.559,237.909H20.098v-95.463  h95.461V237.909z'/>  <path d='M247.958,0.001H10.049C4.5,0.001,0,4.5,0,10.049v93.312c0,5.55,4.5,10.05,10.049,10.05c5.55,0,10.049-4.5,10.049-10.05  V20.098h217.812v217.812h-82.915c-5.55,0-10.05,4.5-10.05,10.05c0,5.549,4.5,10.048,10.05,10.048h92.964  c5.55,0,10.05-4.499,10.05-10.048V10.049C258.008,4.5,253.508,0.001,247.958,0.001z'/>  <path d='M154.35,106.876c1.965,1.961,4.534,2.942,7.105,2.942c2.57,0,5.142-0.981,7.104-2.942l31.755-31.757V89.57  c0,5.549,4.499,10.047,10.05,10.047c5.549,0,10.048-4.498,10.048-10.047V53.054c0-0.365-0.068-0.713-0.107-1.068  c0.329-2.933-0.588-5.979-2.837-8.229c-2.146-2.148-5.023-3.079-7.831-2.873c-0.233-0.017-0.461-0.072-0.696-0.072h-36.513  c-5.551,0-10.051,4.5-10.051,10.05c0,5.549,4.5,10.049,10.051,10.049h13.679L154.35,92.665  C150.426,96.589,150.426,102.952,154.35,106.876z'/>  </g>  </g>  </svg>  \");*/\n}\n\n.fa-caret-down:before{\n  content:url('data:image/svg+xml,<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" x=\"0px\" y=\"0px\"  viewBox=\"0 0 292.362 292.362\" style=\"enable-background:new 0 0 292.362 292.362;\"  xml:space=\"preserve\">  <g>  <path d=\"M286.935,69.377c-3.614-3.617-7.898-5.424-12.848-5.424H18.274c-4.952,0-9.233,1.807-12.85,5.424  C1.807,72.998,0,77.279,0,82.228c0,4.948,1.807,9.229,5.424,12.847l127.907,127.907c3.621,3.617,7.902,5.428,12.85,5.428  s9.233-1.811,12.847-5.428L286.935,95.074c3.613-3.617,5.427-7.898,5.427-12.847C292.362,77.279,290.548,72.998,286.935,69.377z\"/>  </g> </svg>');\n}\n.fa-start:before{\n  content:url('data:image/svg+xml,<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" x=\"0px\" y=\"0px\"  viewBox=\"0 0 16 16\" style=\"enable-background:new 0 0 16 16;\" xml:space=\"preserve\">  <g>  <path d=\"M8,0C3.5,0,0,3.5,0,8s3.5,8,8,8s8-3.5,8-8S12.5,0,8,0z M8,14c-3.5,0-6-2.5-6-6s2.5-6,6-6s6,2.5,6,6  S11.5,14,8,14z\"/>  <polygon points=\"6,12 11,8 6,4\"/></g></svg>');\n}\n.fa-stop:before{\n  content:url('data:image/svg+xml,<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" x=\"0px\" y=\"0px\"  viewBox=\"0 0 508.52 508.52\" style=\"enable-background:new 0 0 508.52 508.52;\" xml:space=\"preserve\"><g><path d=\"M254.26,0C113.845,0,0,113.845,0,254.26s113.845,254.26,254.26,254.26s254.26-113.845,254.26-254.26S394.675,0,254.26,0z M254.26,476.737c-122.68,0-222.477-99.829-222.477-222.477c0-122.68,99.797-222.477,222.477-222.477c122.649,0,222.477,99.797,222.477,222.477C476.737,376.908,376.908,476.737,254.26,476.737z\"/><path d=\"M317.825,158.912h-127.13c-17.544,0-31.782,14.239-31.782,31.782v127.13c0,17.544,14.239,31.783,31.782,31.783h127.13c17.544,0,31.783-14.239,31.783-31.783v-127.13C349.607,173.151,335.369,158.912,317.825,158.912z\"/></g></svg>');\n}\n\n/* most icons derived from http://www.flaticon.com/free-icon/caret-down_25243, needs to attribute*/\n\n.vf-transition{\n  -webkit-transition: all 0.5s ease-in-out;\n  -moz-transition: all 0.5s ease-in-out;\n  -ms-transition: all 0.5s ease-in-out;\n  transition: visibility 0.5s, height 0.5s ease-in-out;\n}\n.vf-code-2{line-height:1;font-size:10px}\n\n.CodeMirror {\n  height: 75%!important;\n}\n\n"; });
define('text!components/navigation.html', ['module'], function(module) { module.exports = "<!-- components/navigation.html -->\n<template bindable=\"router\">\n  <!-- breadcrumbs like\n  <span repeat.for=\"row of router.navigation\" class=\"${row.isActive ? 'active' : ''}\">\n    <a class=\"w3-button  w3-small w3-padding-4 vf-right-border\" href.bind=\"row.href\">${row.title}</a>\n  </span>\n  -->\n  <nav style=\"float:left!important\">\n\n    <ul>\n      <li style=\"padding:0!important\">\n        <!--<li class=\"w3-dropdown-hover\">-->\n        <a href=\"#\">&#8801;</a>\n        <ul><!-- class=\"w3-dropdown-content w3-white w3-card-4\">-->\n          <li repeat.for=\"row of router.navigation\" class=\"${row.isActive ? 'nav-item active' : 'nav-item'}\">\n            <a href.bind=\"row.href\">${row.title}</a>\n          </li>\n        </ul>\n      </li>\n\n    </ul>\n  </nav>\n</template>\n\n"; });
define('text!w3.css', ['module'], function(module) { module.exports = "/* W3.CSS 2.99 Mar 2017 by Jan Egil and Borge Refsnes */\nhtml{box-sizing:border-box}*,*:before,*:after{box-sizing:inherit}\n/* Extract from normalize.css by Nicolas Gallagher and Jonathan Neal git.io/normalize */\nhtml{-ms-text-size-adjust:100%;-webkit-text-size-adjust:100%}body{margin:0}\narticle,aside,details,figcaption,figure,footer,header,main,menu,nav,section,summary{display:block}\naudio,canvas,progress,video{display:inline-block}progress{vertical-align:baseline}\naudio:not([controls]){display:none;height:0}[hidden],template{display:none}\na{background-color:transparent;-webkit-text-decoration-skip:objects}\na:active,a:hover{outline-width:0}abbr[title]{border-bottom:none;text-decoration:underline;text-decoration:underline dotted}\ndfn{font-style:italic}mark{background:#ff0;color:#000}\nsmall{font-size:80%}sub,sup{font-size:75%;line-height:0;position:relative;vertical-align:baseline}\nsub{bottom:-0.25em}sup{top:-0.5em}figure{margin:1em 40px}\nimg{border-style:none}svg:not(:root){overflow:hidden}\ncode,kbd,pre,samp{font-family:monospace,monospace;font-size:1em}\nhr{box-sizing:content-box;height:0;overflow:visible}\nbutton,input,select,textarea{font:inherit;margin:0}optgroup{font-weight:bold}\nbutton,input{overflow:visible}button,select{text-transform:none}\nbutton,html [type=button],[type=reset],[type=submit]{-webkit-appearance:button}\nbutton::-moz-focus-inner, [type=button]::-moz-focus-inner, [type=reset]::-moz-focus-inner, [type=submit]::-moz-focus-inner{border-style:none;padding:0}\nbutton:-moz-focusring, [type=button]:-moz-focusring, [type=reset]:-moz-focusring, [type=submit]:-moz-focusring{outline:1px dotted ButtonText}\nfieldset{border:1px solid #c0c0c0;margin:0 2px;padding:.35em .625em .75em}\nlegend{color:inherit;display:table;max-width:100%;padding:0;white-space:normal}textarea{overflow:auto}\n[type=checkbox],[type=radio]{padding:0}\n[type=number]::-webkit-inner-spin-button,[type=number]::-webkit-outer-spin-button{height:auto}\n[type=search]{-webkit-appearance:textfield;outline-offset:-2px}\n[type=search]::-webkit-search-cancel-button,[type=search]::-webkit-search-decoration{-webkit-appearance:none}\n::-webkit-input-placeholder{color:inherit;opacity:0.54}\n::-webkit-file-upload-button{-webkit-appearance:button;font:inherit}\n/* End extract */\nhtml,body{font-family:Verdana,sans-serif;font-size:15px;line-height:1.5}html{overflow-x:hidden}\nh1,h2,h3,h4,h5,h6,.w3-slim,.w3-wide{font-family:\"Segoe UI\",Arial,sans-serif}\nh1{font-size:36px}h2{font-size:30px}h3{font-size:24px}h4{font-size:20px}h5{font-size:18px}h6{font-size:16px}\n.w3-serif{font-family:\"Times New Roman\",Times,serif}\nh1,h2,h3,h4,h5,h6{font-weight:400;margin:10px 0}.w3-wide{letter-spacing:4px}\nh1 a,h2 a,h3 a,h4 a,h5 a,h6 a{font-weight:inherit}\nhr{border:0;border-top:1px solid #eee;margin:20px 0}\na{color:inherit}\n.w3-image{max-width:100%;height:auto}\n.w3-table,.w3-table-all{border-collapse:collapse;border-spacing:0;width:100%;display:table}\n.w3-table-all{border:1px solid #ccc}\n.w3-bordered tr,.w3-table-all tr{border-bottom:1px solid #ddd}\n.w3-striped tbody tr:nth-child(even){background-color:#f1f1f1}\n.w3-table-all tr:nth-child(odd){background-color:#fff}\n.w3-table-all tr:nth-child(even){background-color:#f1f1f1}\n.w3-hoverable tbody tr:hover,.w3-ul.w3-hoverable li:hover{background-color:#ccc}\n.w3-centered tr th,.w3-centered tr td{text-align:center}\n.w3-table td,.w3-table th,.w3-table-all td,.w3-table-all th{padding:8px 8px;display:table-cell;text-align:left;vertical-align:top}\n.w3-table th:first-child,.w3-table td:first-child,.w3-table-all th:first-child,.w3-table-all td:first-child{padding-left:16px}\n.w3-btn,.w3-btn-block,.w3-button{border:none;display:inline-block;outline:0;padding:6px 16px;vertical-align:middle;overflow:hidden;text-decoration:none!important;color:#fff;background-color:#000;text-align:center;cursor:pointer;white-space:nowrap}\n.w3-btn:hover,.w3-btn-block:hover,.w3-btn-floating:hover,.w3-btn-floating-large:hover{box-shadow:0 8px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19)}\n.w3-button{color:#000;background-color:#e1e1e1;padding:8px 16px}.w3-button:hover{color:#000!important;background-color:#ccc!important}\n.w3-btn,.w3-btn-floating,.w3-btn-floating-large,.w3-closenav,.w3-opennav,.w3-btn-block,.w3-button{-webkit-touch-callout:none;-webkit-user-select:none;-khtml-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none}\n.w3-btn-floating,.w3-btn-floating-large{display:inline-block;text-align:center;color:#fff;background-color:#000;position:relative;overflow:hidden;z-index:1;padding:0;border-radius:50%;cursor:pointer;font-size:24px}\n.w3-btn-floating{width:40px;height:40px;line-height:40px}.w3-btn-floating-large{width:56px;height:56px;line-height:56px}\n.w3-disabled,.w3-btn:disabled,.w3-button:disabled,.w3-btn-floating:disabled,.w3-btn-floating-large:disabled{cursor:not-allowed;opacity:0.3}.w3-disabled *,:disabled *{pointer-events:none}\n.w3-btn.w3-disabled:hover,.w3-btn-block.w3-disabled:hover,.w3-btn:disabled:hover,.w3-btn-floating.w3-disabled:hover,.w3-btn-floating:disabled:hover,\n.w3-btn-floating-large.w3-disabled:hover,.w3-btn-floating-large:disabled:hover{box-shadow:none}\n.w3-btn-group .w3-btn{float:left}.w3-btn-block{width:100%}\n.w3-btn-bar .w3-btn{box-shadow:none;background-color:inherit;color:inherit;float:left}.w3-btn-bar .w3-btn:hover{background-color:#ccc}\n.w3-badge,.w3-tag,.w3-sign{background-color:#000;color:#fff;display:inline-block;padding-left:8px;padding-right:8px;text-align:center}\n.w3-badge{border-radius:50%}\nul.w3-ul{list-style-type:none;padding:0;margin:0}ul.w3-ul li{padding:6px 2px 6px 16px;border-bottom:1px solid #ddd}ul.w3-ul li:last-child{border-bottom:none}\n.w3-tooltip,.w3-display-container{position:relative}.w3-tooltip .w3-text{display:none}.w3-tooltip:hover .w3-text{display:inline-block}\n.w3-navbar{list-style-type:none;margin:0;padding:0;overflow:hidden}\n.w3-navbar li{float:left}.w3-navbar li a,.w3-navitem,.w3-navbar li .w3-btn,.w3-navbar li .w3-input{display:block;padding:8px 16px}.w3-navbar li .w3-btn,.w3-navbar li .w3-input{border:none;outline:none;width:100%}\n.w3-navbar li a:hover{color:#000;background-color:#ccc}\n.w3-navbar .w3-dropdown-hover,.w3-navbar .w3-dropdown-click{position:static}\n.w3-navbar .w3-dropdown-hover:hover{background-color:#ccc;color:#000}\n.w3-navbar a,.w3-topnav a,.w3-sidenav a,.w3-dropdown-content a,.w3-accordion-content a,.w3-dropnav a,.w3-navblock a{text-decoration:none!important}\n.w3-navbar .w3-opennav.w3-right{float:right!important}.w3-topnav{padding:8px 8px}\n.w3-navblock .w3-dropdown-hover:hover,.w3-navblock .w3-dropdown-click:hover{background-color:#ccc;color:#000}\n.w3-navblock .w3-dropdown-hover,.w3-navblock .w3-dropdown-click{width:100%}.w3-navblock .w3-dropdown-hover .w3-dropdown-content,.w3-navblock .w3-dropdown-click .w3-dropdown-content{min-width:100%}\n.w3-topnav a{padding:0 8px;border-bottom:3px solid transparent;-webkit-transition:border-bottom .25s;transition:border-bottom .25s}\n.w3-topnav a:hover{border-bottom:3px solid #fff}.w3-topnav .w3-dropdown-hover a{border-bottom:0}\n.w3-opennav,.w3-closenav{color:inherit}.w3-opennav:hover,.w3-closenav:hover{cursor:pointer;opacity:0.8}\n.w3-btn,.w3-btn-floating,.w3-dropnav a,.w3-btn-floating-large,.w3-btn-block, .w3-navbar a,.w3-navblock a,.w3-sidenav a,.w3-pagination li a,.w3-hoverable tbody tr,.w3-hoverable li,\n.w3-accordion-content a,.w3-dropdown-content a,.w3-dropdown-click:hover,.w3-dropdown-hover:hover,.w3-opennav,.w3-closenav,.w3-closebtn,*[class*=\"w3-hover-\"]\n{-webkit-transition:background-color .25s,color .15s,box-shadow .25s,opacity 0.25s,filter 0.25s,border 0.15s;transition:background-color .25s,color .15s,box-shadow .15s,opacity .25s,filter .25s,border .15s}\n.w3-ripple:active{opacity:0.5}.w3-ripple{-webkit-transition:opacity 0s;transition:opacity 0s}\n.w3-sidenav,.w3-sidebar{height:100%;width:200px;background-color:#fff;position:fixed!important;z-index:1;overflow:auto}\n.w3-sidenav a,.w3-navblock a{padding:4px 2px 4px 16px}.w3-sidenav a:hover,.w3-navblock a:hover{background-color:#ccc;color:#000}.w3-sidenav a,.w3-dropnav a,.w3-navblock a{display:block}\n.w3-sidenav .w3-dropdown-hover:hover,.w3-sidenav .w3-dropdown-hover:first-child,.w3-sidenav .w3-dropdown-click:hover,.w3-dropnav a:hover{background-color:#ccc;color:#000}\n.w3-sidenav .w3-dropdown-hover,.w3-sidenav .w3-dropdown-click,.w3-bar-block .w3-dropdown-hover,.w3-bar-block .w3-dropdown-click{width:100%}\n.w3-sidenav .w3-dropdown-hover .w3-dropdown-content,.w3-sidenav .w3-dropdown-click .w3-dropdown-content,.w3-bar-block .w3-dropdown-hover .w3-dropdown-content,.w3-bar-block .w3-dropdown-click .w3-dropdown-content{min-width:100%}\n.w3-bar-block .w3-dropdown-hover .w3-button,.w3-bar-block .w3-dropdown-click .w3-button{width:100%;text-align:left;background-color:inherit;color:inherit;padding:6px 2px 6px 16px}\n.w3-main,#main{transition:margin-left .4s}\n.w3-modal{z-index:3;display:none;padding-top:100px;position:fixed;left:0;top:0;width:100%;height:100%;overflow:auto;background-color:rgb(0,0,0);background-color:rgba(0,0,0,0.4)}\n.w3-modal-content{margin:auto;background-color:#fff;position:relative;padding:0;outline:0;width:600px}.w3-closebtn{text-decoration:none;float:right;font-size:24px;font-weight:bold;color:inherit}\n.w3-closebtn:hover,.w3-closebtn:focus{color:#000;text-decoration:none;cursor:pointer}\n.w3-pagination{display:inline-block;padding:0;margin:0}.w3-pagination li{display:inline}\n.w3-pagination li a{text-decoration:none;color:#000;float:left;padding:8px 16px}\n.w3-pagination li a:hover{background-color:#ccc}\n.w3-input-group,.w3-group{margin-top:24px;margin-bottom:24px}\n.w3-input{padding:8px;display:block;border:none;border-bottom:1px solid #808080;width:100%}\n.w3-label{color:#009688}.w3-input:not(:valid)~.w3-validate{color:#f44336}\n.w3-select{padding:9px 0;width:100%;color:#000;border:1px solid transparent;border-bottom:1px solid #009688}\n.w3-select select:focus{color:#000;border:1px solid #009688}.w3-select option[disabled]{color:#009688}\n.w3-dropdown-click,.w3-dropdown-hover{position:relative;display:inline-block;cursor:pointer}\n.w3-dropdown-hover:hover .w3-dropdown-content{display:block;z-index:1}\n.w3-dropdown-click:hover{background-color:#ccc;color:#000}\n.w3-dropdown-hover:hover > .w3-button:first-child,.w3-dropdown-click:hover > .w3-button:first-child{background-color:#ccc;color:#000}\n.w3-dropdown-content{cursor:auto;color:#000;background-color:#fff;display:none;position:absolute;min-width:160px;margin:0;padding:0}\n.w3-dropdown-content a{padding:6px 16px;display:block}\n.w3-dropdown-content a:hover{background-color:#ccc}\n.w3-accordion{width:100%;cursor:pointer}\n.w3-accordion-content{cursor:auto;display:none;position:relative;width:100%;margin:0;padding:0}\n.w3-accordion-content a{padding:6px 16px;display:block}.w3-accordion-content a:hover{background-color:#ccc}\n.w3-progress-container{width:100%;height:1.5em;position:relative;background-color:#f1f1f1}\n.w3-progressbar{background-color:#757575;height:100%;position:absolute;line-height:inherit}\ninput[type=checkbox].w3-check,input[type=radio].w3-radio{width:24px;height:24px;position:relative;top:6px}\ninput[type=checkbox].w3-check:checked+.w3-validate,input[type=radio].w3-radio:checked+.w3-validate{color:#009688}\ninput[type=checkbox].w3-check:disabled+.w3-validate,input[type=radio].w3-radio:disabled+.w3-validate{color:#aaa}\n.w3-bar{width:100%;overflow:hidden}.w3-center .w3-bar{display:inline-block;width:auto}\n.w3-bar .w3-bar-item{padding:8px 16px;float:left;background-color:inherit;color:inherit;width:auto;border:none;outline:none;display:block}\n.w3-bar .w3-dropdown-hover,.w3-bar .w3-dropdown-click{position:static;float:left}\n.w3-bar .w3-button{background-color:inherit;color:inherit;white-space:normal}\n.w3-bar-block .w3-bar-item{width:100%;display:block;padding:6px 2px 6px 16px;text-align:left;background-color:inherit;color:inherit;border:none;outline:none}\n.w3-block{display:block;width:100%}\n.w3-responsive{overflow-x:auto}\n.w3-container:after,.w3-container:before,.w3-panel:after,.w3-panel:before,.w3-row:after,.w3-row:before,.w3-row-padding:after,.w3-row-padding:before,.w3-cell-row:before,.w3-cell-row:after,\n.w3-topnav:after,.w3-topnav:before,.w3-clear:after,.w3-clear:before,.w3-btn-group:before,.w3-btn-group:after,.w3-btn-bar:before,.w3-btn-bar:after,.w3-bar:before,.w3-bar:after\n{content:\"\";display:table;clear:both}\n.w3-col,.w3-half,.w3-third,.w3-twothird,.w3-threequarter,.w3-quarter{float:left;width:100%}\n.w3-col.s1{width:8.33333%}\n.w3-col.s2{width:16.66666%}\n.w3-col.s3{width:24.99999%}\n.w3-col.s4{width:33.33333%}\n.w3-col.s5{width:41.66666%}\n.w3-col.s6{width:49.99999%}\n.w3-col.s7{width:58.33333%}\n.w3-col.s8{width:66.66666%}\n.w3-col.s9{width:74.99999%}\n.w3-col.s10{width:83.33333%}\n.w3-col.s11{width:91.66666%}\n.w3-col.s12,.w3-half,.w3-third,.w3-twothird,.w3-threequarter,.w3-quarter{width:99.99999%}\n@media (min-width:601px){\n  .w3-col.m1{width:8.33333%}\n  .w3-col.m2{width:16.66666%}\n  .w3-col.m3,.w3-quarter{width:24.99999%}\n  .w3-col.m4,.w3-third{width:33.33333%}\n  .w3-col.m5{width:41.66666%}\n  .w3-col.m6,.w3-half{width:49.99999%}\n  .w3-col.m7{width:58.33333%}\n  .w3-col.m8,.w3-twothird{width:66.66666%}\n  .w3-col.m9,.w3-threequarter{width:74.99999%}\n  .w3-col.m10{width:83.33333%}\n  .w3-col.m11{width:91.66666%}\n  .w3-col.m12{width:99.99999%}}\n@media (min-width:993px){\n  .w3-col.l1{width:8.33333%}\n  .w3-col.l2{width:16.66666%}\n  .w3-col.l3,.w3-quarter{width:24.99999%}\n  .w3-col.l4,.w3-third{width:33.33333%}\n  .w3-col.l5{width:41.66666%}\n  .w3-col.l6,.w3-half{width:49.99999%}\n  .w3-col.l7{width:58.33333%}\n  .w3-col.l8,.w3-twothird{width:66.66666%}\n  .w3-col.l9,.w3-threequarter{width:74.99999%}\n  .w3-col.l10{width:83.33333%}\n  .w3-col.l11{width:91.66666%}\n  .w3-col.l12{width:99.99999%}}\n.w3-content{max-width:980px;margin:auto}\n.w3-rest{overflow:hidden}\n.w3-layout-container,.w3-cell-row{display:table;width:100%}.w3-layout-row{display:table-row}.w3-layout-cell,.w3-layout-col,.w3-cell{display:table-cell}\n.w3-layout-top,.w3-cell-top{vertical-align:top}.w3-layout-middle,.w3-cell-middle{vertical-align:middle}.w3-layout-bottom,.w3-cell-bottom{vertical-align:bottom}\n.w3-hide{display:none!important}.w3-show-block,.w3-show{display:block!important}.w3-show-inline-block{display:inline-block!important}\n@media (max-width:600px){.w3-modal-content{margin:0 10px;width:auto!important}.w3-modal{padding-top:30px}\n  .w3-topnav a{display:block}.w3-navbar li:not(.w3-opennav){float:none;width:100%!important}.w3-navbar li.w3-right{float:none!important}\n  .w3-topnav .w3-dropdown-hover .w3-dropdown-content,.w3-navbar .w3-dropdown-click .w3-dropdown-content,.w3-navbar .w3-dropdown-hover .w3-dropdown-content,.w3-dropdown-hover.w3-mobile .w3-dropdown-content,.w3-dropdown-click.w3-mobile .w3-dropdown-content{position:relative}\n  .w3-topnav,.w3-navbar{text-align:center}.w3-hide-small{display:none!important}.w3-layout-col,.w3-mobile{display:block;width:100%!important}.w3-bar-item.w3-mobile,.w3-dropdown-hover.w3-mobile,.w3-dropdown-click.w3-mobile{text-align:center}\n  .w3-dropdown-hover.w3-mobile,.w3-dropdown-hover.w3-mobile .w3-btn,.w3-dropdown-hover.w3-mobile .w3-button,.w3-dropdown-click.w3-mobile,.w3-dropdown-click.w3-mobile .w3-btn,.w3-dropdown-click.w3-mobile .w3-button{width:100%}}\n@media (max-width:768px){.w3-modal-content{width:500px}.w3-modal{padding-top:50px}}\n@media (min-width:993px){.w3-modal-content{width:900px}.w3-hide-large{display:none!important}.w3-sidenav.w3-collapse,.w3-sidebar.w3-collapse{display:block!important}}\n@media (max-width:992px) and (min-width:601px){.w3-hide-medium{display:none!important}}\n@media (max-width:992px){.w3-sidenav.w3-collapse,.w3-sidebar.w3-collapse{display:none}.w3-main{margin-left:0!important;margin-right:0!important}}\n.w3-top,.w3-bottom{position:fixed;width:100%;z-index:1}.w3-top{top:0}.w3-bottom{bottom:0}\n.w3-overlay{position:fixed;display:none;width:100%;height:100%;top:0;left:0;right:0;bottom:0;background-color:rgba(0,0,0,0.5);z-index:2}\n.w3-left{float:left!important}.w3-right{float:right!important}\n.w3-tiny{font-size:10px!important}.w3-small{font-size:12px!important}\n.w3-medium{font-size:15px!important}.w3-large{font-size:18px!important}\n.w3-xlarge{font-size:24px!important}.w3-xxlarge{font-size:36px!important}\n.w3-xxxlarge{font-size:48px!important}.w3-jumbo{font-size:64px!important}\n.w3-vertical{word-break:break-all;line-height:1;text-align:center;width:0.6em}\n.w3-left-align{text-align:left!important}.w3-right-align{text-align:right!important}\n.w3-justify{text-align:justify!important}.w3-center{text-align:center!important}\n.w3-display-topleft{position:absolute;left:0;top:0}.w3-display-topright{position:absolute;right:0;top:0}\n.w3-display-bottomleft{position:absolute;left:0;bottom:0}.w3-display-bottomright{position:absolute;right:0;bottom:0}\n.w3-display-middle{position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);-ms-transform:translate(-50%,-50%)}\n.w3-display-left{position:absolute;top:50%;left:0%;transform:translate(0%,-50%);-ms-transform:translate(-0%,-50%)}\n.w3-display-right{position:absolute;top:50%;right:0%;transform:translate(0%,-50%);-ms-transform:translate(0%,-50%)}\n.w3-display-topmiddle{position:absolute;left:50%;top:0;transform:translate(-50%,0%);-ms-transform:translate(-50%,0%)}\n.w3-display-bottommiddle{position:absolute;left:50%;bottom:0;transform:translate(-50%,0%);-ms-transform:translate(-50%,0%)}\n.w3-display-container:hover .w3-display-hover{display:block}.w3-display-container:hover span.w3-display-hover{display:inline-block}.w3-display-hover{display:none}\n.w3-display-position{position:absolute}\n.w3-circle{border-radius:50%!important}\n.w3-round-small{border-radius:2px!important}.w3-round,.w3-round-medium{border-radius:4px!important}\n.w3-round-large{border-radius:8px!important}.w3-round-xlarge{border-radius:16px!important}\n.w3-round-xxlarge{border-radius:32px!important}.w3-round-jumbo{border-radius:64px!important}\n.w3-border-0{border:0!important}.w3-border{border:1px solid #ccc!important}\n.w3-border-top{border-top:1px solid #ccc!important}.w3-border-bottom{border-bottom:1px solid #ccc!important}\n.w3-border-left{border-left:1px solid #ccc!important}.w3-border-right{border-right:1px solid #ccc!important}\n.w3-margin{margin:16px!important}.w3-margin-0{margin:0!important}\n.w3-margin-top{margin-top:16px!important}.w3-margin-bottom{margin-bottom:16px!important}\n.w3-margin-left{margin-left:16px!important}.w3-margin-right{margin-right:16px!important}\n.w3-section{margin-top:16px!important;margin-bottom:16px!important}\n.w3-padding-tiny{padding:2px 4px!important}.w3-padding-small{padding:4px 8px!important}\n.w3-padding-medium,.w3-padding,.w3-form{padding:8px 16px!important}\n.w3-padding-large{padding:12px 24px!important}.w3-padding-xlarge{padding:16px 32px!important}\n.w3-padding-xxlarge{padding:24px 48px!important}.w3-padding-jumbo{padding:32px 64px!important}\n.w3-padding-4{padding-top:4px!important;padding-bottom:4px!important}\n.w3-padding-8{padding-top:8px!important;padding-bottom:8px!important}\n.w3-padding-12{padding-top:12px!important;padding-bottom:12px!important}\n.w3-padding-16{padding-top:16px!important;padding-bottom:16px!important}\n.w3-padding-24{padding-top:24px!important;padding-bottom:24px!important}\n.w3-padding-32{padding-top:32px!important;padding-bottom:32px!important}\n.w3-padding-48{padding-top:48px!important;padding-bottom:48px!important}\n.w3-padding-64{padding-top:64px!important;padding-bottom:64px!important}\n.w3-padding-128{padding-top:128px!important;padding-bottom:128px!important}\n.w3-padding-0{padding:0!important}\n.w3-padding-top{padding-top:8px!important}.w3-padding-bottom{padding-bottom:8px!important}\n.w3-padding-left{padding-left:16px!important}.w3-padding-right{padding-right:16px!important}\n.w3-topbar{border-top:6px solid #ccc!important}.w3-bottombar{border-bottom:6px solid #ccc!important}\n.w3-leftbar{border-left:6px solid #ccc!important}.w3-rightbar{border-right:6px solid #ccc!important}\n.w3-row-padding,.w3-row-padding>.w3-half,.w3-row-padding>.w3-third,.w3-row-padding>.w3-twothird,.w3-row-padding>.w3-threequarter,.w3-row-padding>.w3-quarter,.w3-row-padding>.w3-col{padding:0 8px}\n.w3-spin{animation:w3-spin 2s infinite linear;-webkit-animation:w3-spin 2s infinite linear}\n@-webkit-keyframes w3-spin{0%{-webkit-transform:rotate(0deg);transform:rotate(0deg)}100%{-webkit-transform:rotate(359deg);transform:rotate(359deg)}}\n@keyframes w3-spin{0%{-webkit-transform:rotate(0deg);transform:rotate(0deg)}100%{-webkit-transform:rotate(359deg);transform:rotate(359deg)}}\n.w3-container{padding:0.01em 16px}\n.w3-panel{padding:0.01em 16px;margin-top:16px!important;margin-bottom:16px!important}\n.w3-example{background-color:#f1f1f1;padding:0.01em 16px}\n.w3-code,.w3-codespan{font-family:Consolas,\"courier new\";font-size:16px}\n.w3-code{line-height:1.4;width:auto;background-color:#fff;padding:8px 12px;border-left:4px solid #4CAF50;word-wrap:break-word}\n.w3-codespan{color:crimson;background-color:#f1f1f1;padding-left:4px;padding-right:4px;font-size:110%}\n.w3-example,.w3-code{margin:20px 0}.w3-card{border:1px solid #ccc}\n.w3-card-2,.w3-example{box-shadow:0 2px 4px 0 rgba(0,0,0,0.16),0 2px 10px 0 rgba(0,0,0,0.12)!important}\n.w3-card-4,.w3-hover-shadow:hover{box-shadow:0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19)!important}\n.w3-card-8{box-shadow:0 8px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19)!important}\n.w3-card-12{box-shadow:0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19)!important}\n.w3-card-16{box-shadow:0 16px 24px 0 rgba(0,0,0,0.22),0 25px 55px 0 rgba(0,0,0,0.21)!important}\n.w3-card-24{box-shadow:0 24px 24px 0 rgba(0,0,0,0.2),0 40px 77px 0 rgba(0,0,0,0.22)!important}\n.w3-animate-fading{-webkit-animation:fading 10s infinite;animation:fading 10s infinite}\n@-webkit-keyframes fading{0%{opacity:0}50%{opacity:1}100%{opacity:0}}\n@keyframes fading{0%{opacity:0}50%{opacity:1}100%{opacity:0}}\n.w3-animate-opacity{-webkit-animation:opac 0.8s;animation:opac 0.8s}\n@-webkit-keyframes opac{from{opacity:0} to{opacity:1}}\n@keyframes opac{from{opacity:0} to{opacity:1}}\n.w3-animate-top{position:relative;-webkit-animation:animatetop 0.4s;animation:animatetop 0.4s}\n@-webkit-keyframes animatetop{from{top:-300px;opacity:0} to{top:0;opacity:1}}\n@keyframes animatetop{from{top:-300px;opacity:0} to{top:0;opacity:1}}\n.w3-animate-left{position:relative;-webkit-animation:animateleft 0.4s;animation:animateleft 0.4s}\n@-webkit-keyframes animateleft{from{left:-300px;opacity:0} to{left:0;opacity:1}}\n@keyframes animateleft{from{left:-300px;opacity:0} to{left:0;opacity:1}}\n.w3-animate-right{position:relative;-webkit-animation:animateright 0.4s;animation:animateright 0.4s}\n@-webkit-keyframes animateright{from{right:-300px;opacity:0} to{right:0;opacity:1}}\n@keyframes animateright{from{right:-300px;opacity:0} to{right:0;opacity:1}}\n.w3-animate-bottom{position:relative;-webkit-animation:animatebottom 0.4s;animation:animatebottom 0.4s}\n@-webkit-keyframes animatebottom{from{bottom:-300px;opacity:0} to{bottom:0px;opacity:1}}\n@keyframes animatebottom{from{bottom:-300px;opacity:0} to{bottom:0;opacity:1}}\n.w3-animate-zoom {-webkit-animation:animatezoom 0.6s;animation:animatezoom 0.6s}\n@-webkit-keyframes animatezoom{from{-webkit-transform:scale(0)} to{-webkit-transform:scale(1)}}\n@keyframes animatezoom{from{transform:scale(0)} to{transform:scale(1)}}\n.w3-animate-input{-webkit-transition:width 0.4s ease-in-out;transition:width 0.4s ease-in-out}.w3-animate-input:focus{width:100%!important}\n.w3-opacity,.w3-hover-opacity:hover{opacity:0.60;-webkit-backface-visibility:hidden}\n.w3-opacity-off,.w3-hover-opacity-off:hover{opacity:1;-webkit-backface-visibility:hidden}\n.w3-opacity-max{opacity:0.25;-webkit-backface-visibility:hidden}\n.w3-opacity-min{opacity:0.75;-webkit-backface-visibility:hidden}\n.w3-greyscale-max,.w3-grayscale-max,.w3-hover-greyscale:hover,.w3-hover-grayscale:hover{-webkit-filter:grayscale(100%);filter:grayscale(100%)}\n.w3-greyscale,.w3-grayscale{-webkit-filter:grayscale(75%);filter:grayscale(75%)}\n.w3-greyscale-min,.w3-grayscale-min{-webkit-filter:grayscale(50%);filter:grayscale(50%)}\n.w3-sepia{-webkit-filter:sepia(75%);filter:sepia(75%)}\n.w3-sepia-max,.w3-hover-sepia:hover{-webkit-filter:sepia(100%);filter:sepia(100%)}\n.w3-sepia-min{-webkit-filter:sepia(50%);filter:sepia(50%)}\n.w3-text-shadow{text-shadow:1px 1px 0 #444}.w3-text-shadow-white{text-shadow:1px 1px 0 #ddd}\n.w3-transparent{background-color:transparent!important}\n.w3-hover-none:hover{box-shadow:none!important;background-color:transparent!important}\n/* Colors */\n.w3-amber,.w3-hover-amber:hover{color:#000!important;background-color:#ffc107!important}\n.w3-aqua,.w3-hover-aqua:hover{color:#000!important;background-color:#00ffff!important}\n.w3-blue,.w3-hover-blue:hover{color:#fff!important;background-color:#2196F3!important}\n.w3-light-blue,.w3-hover-light-blue:hover{color:#000!important;background-color:#87CEEB!important}\n.w3-brown,.w3-hover-brown:hover{color:#fff!important;background-color:#795548!important}\n.w3-cyan,.w3-hover-cyan:hover{color:#000!important;background-color:#00bcd4!important}\n.w3-blue-grey,.w3-hover-blue-grey:hover,.w3-blue-gray,.w3-hover-blue-gray:hover{color:#fff!important;background-color:#607d8b!important}\n.w3-green,.w3-hover-green:hover{color:#fff!important;background-color:#4CAF50!important}\n.w3-light-green,.w3-hover-light-green:hover{color:#000!important;background-color:#8bc34a!important}\n.w3-indigo,.w3-hover-indigo:hover{color:#fff!important;background-color:#3f51b5!important}\n.w3-khaki,.w3-hover-khaki:hover{color:#000!important;background-color:#f0e68c!important}\n.w3-lime,.w3-hover-lime:hover{color:#000!important;background-color:#cddc39!important}\n.w3-orange,.w3-hover-orange:hover{color:#000!important;background-color:#ff9800!important}\n.w3-deep-orange,.w3-hover-deep-orange:hover{color:#fff!important;background-color:#ff5722!important}\n.w3-pink,.w3-hover-pink:hover{color:#fff!important;background-color:#e91e63!important}\n.w3-purple,.w3-hover-purple:hover{color:#fff!important;background-color:#9c27b0!important}\n.w3-deep-purple,.w3-hover-deep-purple:hover{color:#fff!important;background-color:#673ab7!important}\n.w3-red,.w3-hover-red:hover{color:#fff!important;background-color:#f44336!important}\n.w3-sand,.w3-hover-sand:hover{color:#000!important;background-color:#fdf5e6!important}\n.w3-teal,.w3-hover-teal:hover{color:#fff!important;background-color:#009688!important}\n.w3-yellow,.w3-hover-yellow:hover{color:#000!important;background-color:#ffeb3b!important}\n.w3-white,.w3-hover-white:hover{color:#000!important;background-color:#fff!important}\n.w3-black,.w3-hover-black:hover{color:#fff!important;background-color:#000!important}\n.w3-grey,.w3-hover-grey:hover,.w3-gray,.w3-hover-gray:hover{color:#000!important;background-color:#9e9e9e!important}\n.w3-light-grey,.w3-hover-light-grey:hover,.w3-light-gray,.w3-hover-light-gray:hover{color:#000!important;background-color:#f1f1f1!important}\n.w3-dark-grey,.w3-hover-dark-grey:hover,.w3-dark-gray,.w3-hover-dark-gray:hover{color:#fff!important;background-color:#616161!important}\n.w3-pale-red,.w3-hover-pale-red:hover{color:#000!important;background-color:#ffdddd!important}\n.w3-pale-green,.w3-hover-pale-green:hover{color:#000!important;background-color:#ddffdd!important}\n.w3-pale-yellow,.w3-hover-pale-yellow:hover{color:#000!important;background-color:#ffffcc!important}\n.w3-pale-blue,.w3-hover-pale-blue:hover{color:#000!important;background-color:#ddffff!important}\n.w3-text-amber,.w3-hover-text-amber:hover{color:#ffc107!important}\n.w3-text-aqua,.w3-hover-text-aqua:hover{color:#00ffff!important}\n.w3-text-blue,.w3-hover-text-blue:hover{color:#2196F3!important}\n.w3-text-light-blue,.w3-hover-text-light-blue:hover{color:#87CEEB!important}\n.w3-text-brown,.w3-hover-text-brown:hover{color:#795548!important}\n.w3-text-cyan,.w3-hover-text-cyan:hover{color:#00bcd4!important}\n.w3-text-blue-grey,.w3-hover-text-blue-grey:hover,.w3-text-blue-gray,.w3-hover-text-blue-gray:hover{color:#607d8b!important}\n.w3-text-green,.w3-hover-text-green:hover{color:#4CAF50!important}\n.w3-text-light-green,.w3-hover-text-light-green:hover{color:#8bc34a!important}\n.w3-text-indigo,.w3-hover-text-indigo:hover{color:#3f51b5!important}\n.w3-text-khaki,.w3-hover-text-khaki:hover{color:#b4aa50!important}\n.w3-text-lime,.w3-hover-text-lime:hover{color:#cddc39!important}\n.w3-text-orange,.w3-hover-text-orange:hover{color:#ff9800!important}\n.w3-text-deep-orange,.w3-hover-text-deep-orange:hover{color:#ff5722!important}\n.w3-text-pink,.w3-hover-text-pink:hover{color:#e91e63!important}\n.w3-text-purple,.w3-hover-text-purple:hover{color:#9c27b0!important}\n.w3-text-deep-purple,.w3-hover-text-deep-purple:hover{color:#673ab7!important}\n.w3-text-red,.w3-hover-text-red:hover{color:#f44336!important}\n.w3-text-sand,.w3-hover-text-sand:hover{color:#fdf5e6!important}\n.w3-text-teal,.w3-hover-text-teal:hover{color:#009688!important}\n.w3-text-yellow,.w3-hover-text-yellow:hover{color:#d2be0e!important}\n.w3-text-white,.w3-hover-text-white:hover{color:#fff!important}\n.w3-text-black,.w3-hover-text-black:hover{color:#000!important}\n.w3-text-grey,.w3-hover-text-grey:hover,.w3-text-gray,.w3-hover-text-gray:hover{color:#757575!important}\n.w3-text-light-grey,.w3-hover-text-light-grey:hover,.w3-text-light-gray,.w3-hover-text-light-gray:hover{color:#f1f1f1!important}\n.w3-text-dark-grey,.w3-hover-text-dark-grey:hover,.w3-text-dark-gray,.w3-hover-text-dark-gray:hover{color:#3a3a3a!important}\n.w3-border-amber,.w3-hover-border-amber:hover{border-color:#ffc107!important}\n.w3-border-aqua,.w3-hover-border-aqua:hover{border-color:#00ffff!important}\n.w3-border-blue,.w3-hover-border-blue:hover{border-color:#2196F3!important}\n.w3-border-light-blue,.w3-hover-border-light-blue:hover{border-color:#87CEEB!important}\n.w3-border-brown,.w3-hover-border-brown:hover{border-color:#795548!important}\n.w3-border-cyan,.w3-hover-border-cyan:hover{border-color:#00bcd4!important}\n.w3-border-blue-grey,.w3-hover-border-blue-grey:hover,.w3-border-blue-gray,.w3-hover-border-blue-gray:hover{border-color:#607d8b!important}\n.w3-border-green,.w3-hover-border-green:hover{border-color:#4CAF50!important}\n.w3-border-light-green,.w3-hover-border-light-green:hover{border-color:#8bc34a!important}\n.w3-border-indigo,.w3-hover-border-indigo:hover{border-color:#3f51b5!important}\n.w3-border-khaki,.w3-hover-border-khaki:hover{border-color:#f0e68c!important}\n.w3-border-lime,.w3-hover-border-lime:hover{border-color:#cddc39!important}\n.w3-border-orange,.w3-hover-border-orange:hover{border-color:#ff9800!important}\n.w3-border-deep-orange,.w3-hover-border-deep-orange:hover{border-color:#ff5722!important}\n.w3-border-pink,.w3-hover-border-pink:hover{border-color:#e91e63!important}\n.w3-border-purple,.w3-hover-border-purple:hover{border-color:#9c27b0!important}\n.w3-border-deep-purple,.w3-hover-border-deep-purple:hover{border-color:#673ab7!important}\n.w3-border-red,.w3-hover-border-red:hover{border-color:#f44336!important}\n.w3-border-sand,.w3-hover-border-sand:hover{border-color:#fdf5e6!important}\n.w3-border-teal,.w3-hover-border-teal:hover{border-color:#009688!important}\n.w3-border-yellow,.w3-hover-border-yellow:hover{border-color:#ffeb3b!important}\n.w3-border-white,.w3-hover-border-white:hover{border-color:#fff!important}\n.w3-border-black,.w3-hover-border-black:hover{border-color:#000!important}\n.w3-border-grey,.w3-hover-border-grey:hover,.w3-border-gray,.w3-hover-border-gray:hover{border-color:#9e9e9e!important}\n.w3-border-light-grey,.w3-hover-border-light-grey:hover,.w3-border-light-gray,.w3-hover-border-light-gray:hover{border-color:#f1f1f1!important}\n.w3-border-dark-grey,.w3-hover-border-dark-grey:hover,.w3-border-dark-gray,.w3-hover-border-dark-gray:hover{border-color:#616161!important}\n.w3-border-pale-red,.w3-hover-border-pale-red:hover{border-color:#ffe7e7!important}.w3-border-pale-green,.w3-hover-border-pale-green:hover{border-color:#e7ffe7!important}\n.w3-border-pale-yellow,.w3-hover-border-pale-yellow:hover{border-color:#ffffcc!important}.w3-border-pale-blue,.w3-hover-border-pale-blue:hover{border-color:#e7ffff!important}\n\n"; });
define('text!components/heads.css', ['module'], function(module) { module.exports = "nav {\n    float:right!important;\n    border-radius:8px!important;\n  }\n\n  nav ul {\n    margin: 0;\n    padding: 0;\n    list-style: none;\n  }\n\n  nav a:link,\n  nav a:visited {\n    color: #f0f0f0;\n    text-decoration: none;\n  }\n\n  nav li li a:link,\n  nav li li a:visited {\n    color: #303030;\n    text-decoration: none;\n  }\n\n  nav a {\n    display: block;\n    padding: 6px 8px;\n  }\n\n  nav li {\n    font-family: 'Lato', sans-serif;\n    font-weight: 400;\n    float: left;\n    background-color: #393b3e;\n    color:#f0f0f0 !important;\n    margin-right: 0px;\n    position: relative;\n    padding: 0.9em;\n  }\n\n  nav li li{\n    width: 160px;\n    z-index:4;\n    background-color:#f0f0f0;\n    padding: 0;\n  }\n\n  nav li:hover {\n    background-color: #55afff;\n  }\n  nav li li:hover {\n    background-color: #757575;\n  }\n\n\n  nav ul ul  {\n    position: absolute;\n    visibility: hidden;\n  }\n\n  nav ul ul ul{\n    position: absolute;\n    right: 100%;\n    top: -2px;\n    border: solid 1px transparent;\n  }\n\n  nav li:hover > ul {\n    visibility: visible;\n  }\n\n  body {\n    line-height: 1.5;\n    font-size:15px;\n    margin:0;\n  }\n\n  .vf-black {\n    color:#fff!important;\n    background-color:#000!important;\n  }\n  .vf-modal{z-index:3;display:none;padding-top:100px;position:fixed;left:0;top:0;width:100%;height:100%;overflow:auto;background-color:rgb(0,0,0);background-color:rgba(0,0,0,0.4)}\n  .vf-modal-content{margin:auto;background-color:#fff;position:relative;padding:0;outline:0;width:600px}.w3-closebtn{text-decoration:none;float:right;font-size:24px;font-weight:bold;color:inherit}\n  .vf-sand{color:#000!important;background-color:#fdf5e6!important}\n  .vf-card-2{}\n  .vf-white{color:#000!important;background-color:#fff!important}\n  .vf-right-border{\n    border-top-right-radius: 16px;\n    border-bottom-right-radius: 16px;\n  }\n\n.aurelia-hide-remove {\n  -webkit-animation: fadeIn 2s;\n  animation: fadeIn 2s;\n}\n\n.aurelia-hide-add {\n  -webkit-animation: fadeOut 2s;\n  animation: fadeOut 2s;\n}\n\n.aurelia-hide-enter {\n  animation: fadeOut 2s;\n}\n.aurelia-hide-leave {\n  -webkit-animation: fadeIn 2s;\n  animation: fadeIn 2s;\n}\n\n.au-leave-active {\n  -webkit-animation: fadeIn 2s;\n  animation: fadeOut 2s;\n}\n.animation-enter-active {\n  -webkit-animation: fadeIn 2s;\n  animation: fadeIn 2s;\n}\n\n.aurelia-enter-active {\n  -webkit-animation: fadeIn 2s;\n  animation: fadeIn 2s;\n}\n\n.au-enter-active {\n  -webkit-animation: fadeIn 2s;\n  animation: fadeIn 2s;\n}\n\n/* CSS3-Animations */\n@-webkit-keyframes fadeIn {\n  0%   { opacity: 0; }\n  100% { opacity: 1; }\n}\n\n@keyframes fadeIn {\n  0%   { opacity: 0; }\n  100% { opacity: 1; }\n}\n\n@-webkit-keyframes fadeOut {\n  0%   { opacity: 1; }\n  100% { opacity: 0; }\n}\n\n@keyframes fadeOut {\n  0%   { opacity: 1; }\n  100% { opacity: 0; }\n}\n"; });
define('text!components/sharedfooter.html', ['module'], function(module) { module.exports = "<template>\n<footer>\n    <!--div class=\"w3-clear w3-margin-top\"></div-->\n    <!--div class=\"w3-center w3-black w3-bottom w3-bottombar\">\n        Development documentation at <a href=\"http://internal-wiki.west-life.eu/w/index.php?title=WP6\" target=\"_blank\">internal-wiki.west-life.eu/w/index.php?title=WP6</a>\n    </div-->\n</footer>\n</template>\n"; });
define('text!scientist/visitingscientist.css', ['module'], function(module) { module.exports = ".aurelia-hide-add {\n  animation: fadeOut 2s;\n}\n\n.aurelia-hide-remove {\n  animation: fadeIn 2s;\n}\n\n@keyframes fadeIn {\n  0%   { opacity: 0; }\n  100% { opacity: 1; }\n}\n\n@keyframes fadeOut {\n  0%   { opacity: 1; }\n  100% { opacity: 0; }\n}\n\n"; });
define('text!components/sharedheader.html', ['module'], function(module) { module.exports = "<template>\n\n<div class=\"vf-white\">\n<a href=\"/\">\n  <picture>\n    <source srcset=\"/img/westlife-logo.png\">\n    <img class=\"logo\" style=\"height:60px\" src=\"/repository/img/westlife-logo.png\" alt=\"brand logo\">\n  </picture>\n\n</a>\n<nav class=\"vf-white\">\n\n  <ul>\n          <li class=\"nav-item active\">\n            <a href=\"https://about.west-life.eu/network/west-life/west-life\">Home</a>\n          </li>                        <li class=\"nav-item\">\n          <a href=\"https://about.west-life.eu/network/west-life/services\">Services</a>\n          <ul>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/integrative-modelling\">Integrative Modelling</a>\n          </li>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/services/mx\">Crystallography</a>\n          </li>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/services/em\">Electron Microscopy</a>\n          </li>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/services/nmr\">NMR</a>\n          </li>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/services/vm\">Virtual Machines</a>\n          </li>                        <li class=\"nav-item\">\n            <a href=\"https://portal.west-life.eu/virtualfolder/\">Virtual Folder Portal</a>\n          </li>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/services/3rd-party-services\">3rd Party Services</a>\n          </li>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/toolbox\">Search Services</a>\n          </ul></li>                        <li class=\"nav-item\">\n          <a href=\"https://about.west-life.eu/network/west-life/search-support\">Support</a>\n          <ul>                        <li class=\"nav-item\">\n            <a href=\"https://www.structuralbiology.euhttps://about.west-life.eu/network/west-life/forums/\">Forums</a>\n            <ul>                        <li class=\"nav-item\">\n              <a href=\"https://www.structuralbiology.euhttps://about.west-life.eu/network/west-life/forums\">West-Life Forum</a>\n            </li>                        <li class=\"nav-item\">\n              <a href=\"http://ask.bioexcel.eu/c/haddock\">Haddock</a>\n            </li>                        <li class=\"nav-item\">\n              <a href=\"http://ask.bioexcel.eu/c/disvis\">DisVis</a>\n            </li>                        <li class=\"nav-item\">\n              <a href=\"http://ask.bioexcel.eu/c/powerfit\">PowerFit</a>\n            </li>                        <li class=\"nav-item\">\n              <a href=\"https://sourceforge.net/p/scipion/mailman/scipion-users/\">Scipion</a>\n            </ul></li>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/documentation\">Documentation</a>\n            <ul>                        <li class=\"nav-item\">\n              <a href=\"https://about.west-life.eu/network/west-life/documentation/xia2-workflow\">XIA2 Workflow</a>\n            </li>                        <li class=\"nav-item\">\n              <a href=\"https://about.west-life.eu/network/west-life/documentation/pdbe\">PDBe</a>\n              <ul>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/pdbe/pdbe-rest-api\">PDBe REST API</a>\n              </ul></li>                        <li class=\"nav-item\">\n              <a href=\"https://about.west-life.eu/network/west-life/documentation/ccp4\">CCP4</a>\n              <ul>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/ccp4/ccp4-online\">CCP4-Online</a>\n              </ul></li>                        <li class=\"nav-item\">\n              <a href=\"https://about.west-life.eu/network/west-life/documentation/ccp-em\">CCP4-EM</a>\n            </li>                        <li class=\"nav-item\">\n              <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr\">WeNMR</a>\n              <ul>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/gromacs\">GROMACS</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/haddock\">HADDOCK</a>\n                <ul>                        <li class=\"nav-item\">\n                  <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/haddock/haddock--supported-co-factors-and-modified-amino-acids\">HADDOCK – supported co-factors and modified amino acids</a>\n                </li>                        <li class=\"nav-item\">\n                  <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/haddock/haddock-22--default-settings\">HADDOCK 2.2 – Default settings</a>\n                </li>                        <li class=\"nav-item\">\n                  <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/haddock/haddock--review\">HADDOCK - Review</a>\n                </ul></li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/cs-rosetta3\">CS-Rosetta3</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/unio\">UNIO</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/disvis\">DISVIS</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/xplor-nih\">XPLOR-NIH</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"http://ask.bioexcel.eu/c/powerfit\">PowerFit</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/anisofit\">ANISOFIT</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/fanten\">FANTEN</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/amber\">AMBER</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/wenmr/antechamber\">ANTECHAMBER</a>\n              </ul></li>                        <li class=\"nav-item\">\n              <a href=\"https://about.west-life.eu/network/west-life/documentation/scipion-web-tools\">Scipion Web Tools</a>\n              <ul>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/scipion-web-tools/my-movie-alignment\">My Movie Alignment</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/scipion-web-tools/my-first-map\">My First Map</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/scipion-web-tools/my-res-map\">My Res Map</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/scipion-web-tools/my-reliability-tool\">My Reliability Tool</a>\n              </ul></li>                        <li class=\"nav-item\">\n              <a href=\"https://h2020-westlife-eu.gitbooks.io/virtual-folder-docs\">Virtual Folder</a>\n            </li>                        <li class=\"nav-item\">\n              <a href=\"https://about.west-life.eu/network/west-life/documentation/egi-platforms\">EGI Platforms</a>\n              <ul>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/egi-platforms/htc-platform\">HTC Platform</a>\n                <ul>                        <li class=\"nav-item\">\n                  <a href=\"https://about.west-life.eu/network/west-life/documentation/egi-platforms/registering-with-enmreu-vo\">Registration with ENMR.EU VO</a>\n                </li>                        <li class=\"nav-item\">\n                  <a href=\"https://about.west-life.eu/network/west-life/documentation/egi-platforms/vo-registration-troubleshooting\">VO registration troubleshooting</a>\n                </li>                        <li class=\"nav-item\">\n                  <a href=\"https://github.com/DIRACGrid/DIRAC/wiki/DIRAC-Tutorials\">Job submission using DIRAC system</a>\n                </ul></li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/egi-platforms/federated-cloud\">Federated Cloud</a>\n              </li>                        <li class=\"nav-item\">\n                <a href=\"https://about.west-life.eu/network/west-life/documentation/egi-platforms/accelerated-computing-platforms\">Accelerated Computing Platforms</a>\n                <ul>                        <li class=\"nav-item\">\n                  <a href=\"https://about.west-life.eu/network/west-life/htc-ac\">HTC-AC</a>\n                </li>                        <li class=\"nav-item\">\n                  <a href=\"https://about.west-life.eu/network/west-life/cloud-ac\">Cloud-AC</a>\n                </ul></li></ul></li></ul></li>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/support/tutorials\">Tutorials</a>\n          </li>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/support/webinars\">Webinars</a>\n          </ul></li>                        <li class=\"nav-item\">\n          <a href=\"https://about.west-life.eu/network/west-life/news\">News</a>\n        </li>                        <li class=\"nav-item\">\n          <a href=\"#x\">About</a>\n          <ul>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/about/project\">Project</a>\n          </li>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/about/partners\">Partners</a>\n          </li>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/about/advisoryboard\">Advisory Board</a>\n          </li>                        <li class=\"nav-item\">\n            <a href=\"https://about.west-life.eu/network/west-life/about/work-packages\">Work Packages</a>\n            <ul>                        <li class=\"nav-item\">\n              <a href=\"https://about.west-life.eu/network/west-life/about/work-packages/deliverables\">Deliverables</a>\n            </li>                        <li class=\"nav-item\">\n              <a href=\"https://about.west-life.eu/network/west-life/about/work-packages/milestones\">Milestones</a>\n            </ul></li>                        <li class=\"nav-item\">\n            <a href=\"http://internal-wiki.west-life.eu/\">Internal Wiki</a>\n          </ul></li>\n          <li><a href=\"https://about.west-life.eu/network/west-life/contact\">Contact</a></li>\n      <li class=\"w3-sign w3-hide\"><a href=\"/logout/\">Logout</a></li>\n    </ul>\n\n</nav>\n  <div id=\"id01\" class=\"vf-modal\">\n    <div class=\"vf-modal-content vf-card-2\">\n      <header class=\"vf-sand\">\n        <span onclick=\"document.getElementById('id01').style.display='none'\"\n              class=\"w3-button w3-display-topright\">&times;</span>\n        <h3>West-Life Repository</h3>\n      </header>\n      <div class=\"vf-white\">\n        <table>\n          <tr><td style=\"text-align: right\">Version:</td><td>17.09</td></tr>\n          <tr><td style=\"text-align: right\">Sources:</td><td><a href=\"https://github.com/h2020-westlife-eu\">github.com/h2020-westlife-eu</a></td></tr>\n          <tr><td style=\"text-align: right\">Authors:</td><td><a href=\"https://west-life.eu\">West-Life consortium</a></td></tr>\n          <tr><td style=\"text-align: right\">Ackn.:</td><td>Frontend: Aurelia framework, W3.CSS, Icons made by Freepik from www.flaticon.com, Backend: Java Spring Frameworker, Docker, Hibernate</td></tr>\n        </table>\n      </div>\n      <footer class=\"vf-sand\">\n        <a rel=\"license\" href=\"http://creativecommons.org/licenses/by/4.0/\"><img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"https://i.creativecommons.org/l/by/4.0/88x31.png\" /></a>This work is licensed under a <a rel=\"license\" href=\"http://creativecommons.org/licenses/by/4.0/\">Creative Commons Attribution 4.0 International License</a>.\n      </footer>\n    </div>\n  </div>\n\n</div>\n</template>\n"; });
define('text!home/home.html', ['module'], function(module) { module.exports = "<template>\n  <h2>Institutional Repository - home page</h2>\n  <p>You're logged as Scientist.\n    </p>\n  <a href=\"#visitingscientist\" class=\"w3-button\">VisitingScientist</a>\n\n</template>\n"; });
define('text!login/login.html', ['module'], function(module) { module.exports = "<template>\n      <h3>Login</h3>\n      <p>\n        You can login as\n        <a href=\"#visitingscientist\" class=\"w3-button\">Visiting Scientist</a> to access files related to an experiment.\n      </p>\n      <p>\n        You can login as\n        <a href=\"#repositorystaff\" class=\"w3-button\">Support Staff</a> to access, upload files from workstation to the facility repository.\n      </p>\n</template>\n"; });
define('text!pickerclient/pickerclient.html', ['module'], function(module) { module.exports = "<template mode=\"file\">\n  <div show.bind=\"filepicker\">\n  <button class=\"w3-button\" click.trigger=\"openfilewindow()\">Select File from West-Life Virtual Folder</button>\n  </div>\n  <div show.bind=\"!filepicker\">\n    <button class=\"w3-button\" click.trigger=\"opendirwindow()\">Select Dir from West-Life Virtual Folder</button>\n  </div>\n  <p>Selected Resource URL:<a href.bind=\"vfurl\">${vfurl}</a></p>\n</template>\n"; });
define('text!repository/app.html', ['module'], function(module) { module.exports = "<template>\n  <require from=\"../w3.css\"></require>\n  <require from=\"../components/heads.css\"></require>\n  <require from=\"../components/sharedheader.html\"></require>\n  <require from=\"../components/sharedfooter.html\"></require>\n  <require from=\"../components/navigation.html\"></require>\n  <sharedheader></sharedheader>\n\n      <div class=\"w3-card-2 w3-white w3-margin w3-padding\">\n\n        <navigation router.bind=\"router\"></navigation>\n        <!--nav>\n        <ul repeat.for=\"nav of router.navigation\">\n          <li class=\"${nav.isActive ? 'nav-item active' : 'nav-item'}\"><a href.bind=\"nav.href\">${nav.title}</a></li>\n        </ul>\n        </nav-->\n        <router-view>\n          <!-- Top level views are rendered here -->\n        </router-view>\n\n        <sharedfooter></sharedfooter>\n\n      </div>\n</template>\n"; });
define('text!repositorystaff/repositorystaff.html', ['module'], function(module) { module.exports = "<template>\n  <h3>File Upload</h3>\n  <p> This page shows File upload dialog, used by Support Staff at local workstation to upload data acquisition into the visiting scientist account.</p>\n  <label>Project input\n    <input class=\"w3-input\">\n  </label>\n  <div show.bind=\"selectinguser\">\n  <p><b>1.</b>Select a user, who's data will be uploaded:</p>\n  <table class=\"w3-table-all\" draggable=\"true\">\n    <tr class=\"w3-hover-green\" repeat.for=\"visitor of visitors\" click.trigger=\"selectvisitor(visitor)\">\n      <td>${visitor}</td>\n    </tr>\n  </table>\n  </div>\n  <div show.bind=\"!selectinguser\">\n    <p><b>1.</b>Selected user: ${selectedvisitor} <button class=\"w3-button w3-padding-tiny\" click.trigger=\"deselectvisitor()\">change</button></p>\n    <p><b>2.</b>Select or drop files or directories to upload to the user account.</p>\n<table>\n  <tr>\n    <td  class=\"w3-cell-top\">\n      <div>\n        <h4>Local files</h4>\n        <form>\n          <table class=\"w3-table-all w3-padding-tiny\" drop.trigger=\"dropped($event)\" ondragover=\"event.preventDefault();\">\n            <thead>\n            <tr>\n              <th>drag & drop files/directories here or browse</th>\n            </tr>\n            </thead>\n            <tbody>\n            <tr>\n              <td><input class=\"w3-button\" type=\"file\" multiple=\"multiple\" name=\"files[]\" webkitdirectory=\"true\"\n                         change.delegate=\"appendDir($event)\" value.bind=\"uploaddir\"/>\n                <input class=\"w3-button\" type=\"file\" multiple=\"multiple\" title=\"Select Files to Download\"\n                       change.delegate=\"appendFiles($event)\" value.bind=\"uploadfiles\"/>\n              </td>\n              <td>Totally: ${filestoupload.length} files will be uploaded.</td>\n            </tr>\n            <tr class=\"w3-hover-green\" repeat.for=\"item of filestoupload\" click.trigger=\"selectItemToUpload(item)\">\n              <td class=\"w3-padding-tiny\">${item.name}</td>\n              <td class=\"w3-padding-tiny\">\n                <button class=\"w3-button  w3-padding-tiny\" title=\"delete\" click.delegate=\"removeItemToUpload(item)\">x</button>\n              </td>\n            </tr>\n            </tbody>\n          </table>\n        </form>\n      </div>\n\n    </td>\n    <td class=\"w3-cell-top\">\n      <div>\n\n        <button disabled.bind=\"filestoupload.length == 0\" class=\"w3-button w3-green\"\n                click.delegate=\"submitUpload()\">Upload to &raquo;\n        </button>\n\n\n      </div>\n\n    </td>\n    <td  class=\"w3-cell-top\">\n      <div>\n        <h4>Repository files</h4>\n        <table class=\"w3-table-all\">\n          <thead>\n          <tr>\n            <th>filename</th>\n            <th>date</th>\n            <th colspan=\"2\">action</th>\n          </tr>\n          </thead>\n          <tr class=\"w3-hover-green\" repeat.for=\"item of items\" click.trigger=\"selectitem(item)\">\n            <td class=\"w3-padding-tiny\">${item.name}</td>\n            <td class=\"w3-padding-tiny\">${item.date}</td>\n            <td class=\"w3-padding-tiny\">\n              <button class=\"w3-button w3-padding-tiny\" title=\"delete\" click.trigger=\"deleteitem(item)\">x</button>\n            </td>\n\n          </tr>\n        </table>\n      </div>\n\n    </td>\n  </tr>\n</table>\n  </div>\n\n</template>\n"; });
define('text!scientist/repositorytovf.html', ['module'], function(module) { module.exports = "<template>\n  <require from=\"../pickerclient/pickerclient\"></require>\n  <h3>Repository to West-Life Virtual Folder</h3>\n  <p> This page shows dialog to select files or directories to be uploaded from local repository to user's Virtual Folder.</p>\n  <div show.bind=\"selectingfiles\">\n    <p><b>1.</b>Select files or directories that will be uploaded:</p>\n    <h4>Repository files</h4>\n    <table class=\"w3-table-all\">\n      <thead>\n      <tr>\n        <th>filename</th>\n        <th>date</th>\n        <th colspan=\"2\">action</th>\n      </tr>\n      </thead>\n      <tr class=\"w3-hover-green\" repeat.for=\"item of items\" click.trigger=\"selectitem(item)\">\n        <td class=\"w3-padding-tiny\">${item.name}</td>\n        <td class=\"w3-padding-tiny\">${item.date}</td>\n        <td class=\"w3-padding-tiny\">\n          <button class=\"w3-button w3-padding-tiny\" title=\"delete\" click.trigger=\"deleteitem(item)\">x</button>\n        </td>\n\n      </tr>\n    </table>\n    <button class=\"w3-button\" title=\"submit\" click.trigger=\"submitfiles()\">Submit</button>\n  </div>\n\n  <div show.bind=\"!selectingfiles\">\n    <p><b>1.</b>Selected files: ${selectedfiles} <button class=\"w3-button w3-padding-tiny\" click.trigger=\"unsubmitfiles()\">change</button></p>\n    <p><b>2.</b>Select Virtual Folder:</p>\n    <pickerclient mode=\"dir\"></pickerclient>\n\n  </div>\n\n</template>\n"; });
define('text!scientist/visitingscientist.html', ['module'], function(module) { module.exports = "<template>\n  <require\n    from=\"./visitingscientist.css\"></require>\n      <h3>Repository Dashboard</h3>\n<p>You are logged as visiting scientist.\n  You can view your visits or proposals.\n  Clicking on visit or proposal you can narrow datasets related to the visit.\n</p>\n<div class=\"w3-card au-animate\">\n    <h4>Proposals for Visits (Instruct):<button show.bind=\"!showProposals\" class=\"w3-button au-animate\" click.trigger=\"showAllProposals()\">Show all</button></h4>\n    <div show.bind=\"showProposals\" class=\"au-animate\">\n    <table class=\"w3-table\">\n      <tr>\n        <th>pid</th><th>title</th>\n      </tr>\n      <tr repeat.for=\"proposal of proposals\"  class=\"w3-hover-green\" click.trigger=\"selectProposal(proposal)\">\n        <td>${proposal.projectId}</td><td>${proposal.summary}(${proposal.datasets})</td>\n      </tr>\n      <tr show.bind=\"showallbutton\" class=\"w3-hover-green\" click.trigger=\"switchMoreLessProposals()\"><td colspan=\"2\"><span show.bind=\"showmorebutton\">.. see more proposals (${proposalslength})</span><span show.bind=\"!showmorebutton\"> .. see less proposals</span></td> </tr>\n    </table>\n    <p>Instruct -\n      <a href=\"https://www.structuralbiology.eu/dashboard?t=instruct\" class=\"w3-button\">Dashboard</a>\n    </p>\n    <p>Instruct -\n      <a href=\"https://www.structuralbiology.eu/submit-proposal/step1/new\" class=\"w3-button\">New Proposal</a> for a visit.\n    </p>\n      </div>\n    <div show.bind=\"! showProposals\" class=\"au-animate\">\n      <p class=\"w3-green\">${selectedProposal.projectId} ${selectedProposal.summary}</p>\n    </div>\n  </div>\n\n  <p>\n  You can view datasets.\n  Every dataset consists of files.\n  Clicking on a dataset will switch to file view of the dataset.</p>\n  <div class=\"w3-card\">\n  <h4>Datasets:</h4>\n  <table class=\"w3-table\">\n    <thead>\n    <tr>\n      <th>date</th><th>Summary</th><th>i</th>\n    </tr>\n    </thead>\n    <tbody>\n    <tr class=\"w3-hover-green\" repeat.for=\"item of items\" click.trigger=\"selectDataset(item)\">\n      <td>${item.date}</td>\n      <td>${item.summary}</td>\n      <td>${item.info}</td>\n    </tr>\n    <tr  class=\"w3-hover-green\"><td colspan=\"3\"><a href=\"#datasets\">... see all datasets ...</a></td> </tr>\n    </tbody>\n  </table>\n</div>\n  <p>&nbsp;</p>\n</template>\n"; });
//# sourceMappingURL=app-bundle.js.map