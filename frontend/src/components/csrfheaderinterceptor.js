import {Interceptor, HttpResponseMessage, RequestMessage} from "aurelia-fetch-client";

export class Csrfheaderinterceptor { //implements Interceptor {
  constructor() {
    this.TOKEN_HEADER = 'X-CSRF-TOKEN';
    this.latestCsrfToken;
  }
  response(response) {
    console.log("csrfheaderinterceptor, headers:")
    console.log(response.headers)
    if (response.headers.has(this.TOKEN_HEADER)) {
      this.latestCsrfToken = response.headers.get(this.TOKEN_HEADER);
    }
    return response;
  }

  request(request) {
    if (this.latestCsrfToken) {
      console.log(request)
      console.log(this.latestCsrfToken)
      if (['POST', 'PUT', 'PATCH'].indexOf(request.method) >= 0) {
        request.headers.add(this.TOKEN_HEADER, this.latestCsrfToken);
      }
    }
    return request;
  }

}
