$(document).ready(function() {
    var result = null;
    $.ajax({
        url: "http://localhost:8080/api/wifistreamer/v1/analytics/categorycount"
    }).then(function(data) {
        result = data;
        alert("Data Loaded: " + data);
    });
    return result;
});