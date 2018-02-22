import {Interceptor, HttpResponseMessage, RequestMessage} from "aurelia-fetch-client";

class CsrfHeaderInterceptor implements Interceptor {
  TOKEN_HEADER = 'X-CSRF-Token';
  latestCsrfToken;

  response(response){
    if (response.headers.has(this.TOKEN_HEADER)) {
      this.latestCsrfToken = response.headers.get(this.TOKEN_HEADER);
    }
    return response;
  }

  request(request){
    if (this.latestCsrfToken) {
      if (['POST', 'PUT', 'PATCH'].indexOf(request.method) >= 0) {
        request.headers.add(this.TOKEN_HEADER, this.latestCsrfToken);
      }
    }
    return request;
  }
}
