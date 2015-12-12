var techButton = document.getElementById('radio-techy');
var nonTechButton = document.getElementById('radio-non-techy');

techButton.onclick = function() {
    document.getElementById('select-div').className = 'tech';
}
nonTechButton.onclick = function() {
    document.getElementById('select-div').className = 'non-tech';
}