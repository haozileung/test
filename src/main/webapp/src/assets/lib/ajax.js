var ajax = {};
ajax.get = function(url,success){
var request = new XMLHttpRequest();
if(url){
request.open('GET', url, true);
request.onload = function() {
  if (request.status >= 200 && request.status < 400) {
    var resp = request.responseText;
    if(success){
        success(resp);
    }
  }
};
request.send();
}else{
throw "params error: url not exist!";
}
}
ajax.getJSON = function(url,success){
var innerSuccess:function(data){
    if(success){
        success(JSON.parse(data));
    }
}
ajax.get(url,innerSuccess);
}

ajax.post = function(url,data,success){
if(url){
var request = new XMLHttpRequest();
request.open('POST', url, true);
request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
request.onload = function() {
  if (request.status >= 200 && request.status < 400) {
    var resp = request.responseText;
    if(success){
    success(resp);
    }
  }
  };
request.onerror = function() {
    if(params.error){
        params.error(request);
    }
};
request.send(data);
}else{
throw "params error: url not exist!";
}
}

ajax.delete = function(url,data,success){
if(url){
var request = new XMLHttpRequest();
request.open('DELETE', url, true);
request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
request.onload = function() {
  if (request.status >= 200 && request.status < 400) {
    var resp = request.responseText;
    if(success){
    success(resp);
    }
  }
  }
request.onerror = function() {
    if(params.error){
        params.error(request);
    }
};
request.send(data);
}else{
throw "params error: url not exist!";
}
}

ajax.put = function(url,data,success){
if(url){
var request = new XMLHttpRequest();
request.open('PUT', url, true);
request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
request.onload = function() {
  if (request.status >= 200 && request.status < 400) {
    var resp = request.responseText;
    if(success){
        success(resp);
    }
  }
  }
request.onerror = function() {
    if(params.error){
        params.error(request);
    }
};
request.send(data);
}else{
throw "params error: url not exist!";
}
}
module.exports = ajax;