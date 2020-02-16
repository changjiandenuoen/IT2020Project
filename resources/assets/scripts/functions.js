// This is a reusable method for creating a CORS request. Do not edit this.
function createCORSRequest(method, url, async) {
    var xhr = new XMLHttpRequest();
    if ("withCredentials" in xhr) {
        if(async) {
            xhr.open(method, url, async);
        } else {
            xhr.open(method, url, false);
        }
    } else if (typeof XDomainRequest != "undefined") {
        xhr = new XDomainRequest();
        xhr.open(method, url);
    } else {
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
