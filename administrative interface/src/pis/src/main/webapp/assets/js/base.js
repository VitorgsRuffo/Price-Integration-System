
/**
 * Check whether an object is empty or not (wider checking than obj === undefined).
 */
function isEmpty(someObj){
    if(someObj === undefined || someObj === null){
        return true;
    }else if(typeof someObj === "string" && $.trim(someObj) === ""){
        return true;
    }else return !!(typeof someObj === "number" && someObj === 0);
}

/**
 * Returns a formatted url with the proper base prepended
 */
$.url = function (url) {
    var baseUrl = $('base').attr('href');
    if (isEmpty(baseUrl)) { // application deployed on ROOT
        return window.location.protocol + "//" + window.location.host + url;
    } else { // other path, must prepend the base url
        return baseUrl + url.substr(1);
    }
};