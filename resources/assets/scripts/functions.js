// This is a reusable method for creating a CORS request. Do not edit this.
function createCORSRequest(method, url) {
    var xhr = new XMLHttpRequest();
    if ("withCredentials" in xhr) {

        // Check if the XMLHttpRequest object has a "withCredentials" property.
        // "withCredentials" only exists on XMLHTTPRequest2 objects.
        xhr.open(method, url, false);

    } else if (typeof XDomainRequest != "undefined") {

        // Otherwise, check if XDomainRequest.
        // XDomainRequest only exists in IE, and is IE's way of making CORS requests.
        xhr = new XDomainRequest();
        xhr.open(method, url);

    } else {

        // Otherwise, CORS is not supported by the browser.
        xhr = null;
    }

    return xhr;
}

// Add the content of a specified id in the target html to the target container
function addHTML(url, container) {
    $.get(url, function(data) {
        $(container).append(data);
    });
}

// Add the content of a specified id in the target html to the target container
function swapHTML(url, container, content) {
    $.get(url, function(data) {
        if(content) {
            $(container).html($(data).find(content));
        } else {
            $(container).html(data);
        }
    });
}
