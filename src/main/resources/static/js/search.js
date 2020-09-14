function search (){
    var context = document.querySelector('base').getAttribute('href');
    var url = context + "item/search?q="+document.querySelector('#searchField').value;
    var options = {method : "GET"};
    fetch(url, options)
        .then(function(response){
            return response.json();
        })
        .then(function(elements){
            let container = document.querySelector("#contenuto");
            if (elements.length>0) {

                let pars = "";
                for (var i = 0; i < elements.length; i++) {
                    pars += createDiv(elements[i]);
                }
                container.innerHTML = pars;
            }
        })
}


function createDiv(item){
    var context = document.querySelector('base').getAttribute('href');
    var field = '<div class="card border border-secondary my-1">'+
        '<h5 class="card-header bg-secondary text-light">'+item.title+'</h5>';

    if(item.image.length!=0)

        field += '<div class="card-img-top justify-content-center">'+
                '<img class="col-10 my-2" alt="'+item.title+'" src="'+context+'item/'+ item.itemId+'/image"></div>';
    var shortDescr= "";
    var str = item.description.split(" ");
    var limit = 4;
    if (str.length<4)
        limit = str.length;
    for (i=0; i<limit; i++){
        shortDescr= shortDescr+ " "+str[i];
    }
    if (str.length> 4)
        shortDescr=shortDescr+" ...";
    field+='<div class="card-body bg-gradient-secondary">'+
        '<h6 class="card-title">'+item.adType+'</h6>'+
        '<p class="card-text">'+shortDescr+'</p>'+
        '</div>'+
        '<div class="card-link bg-gradient-secondary">'+
        '<a class="text-decoration-none" href="'+context+'item/'+item.itemId+'">'+
        '<img alt="Search" id=search src="'+context+'icons/search.png" width="30px">'+
        '</a>'+
        '</div>'+
        '<div class="card-footer bg-secondary text-light">'+
        '<small>'+ dateFormatter(item.date) + '</small>'+
        '</div>'+
        '</div>';
    return field;

}
function dateFormatter(date){
    var year = date.substr(0,4);
    var month = date.substr(5,2);
    var day = date.substr(8,2);
    var hour = date.substr(11,2)
    var min= date.substr(14,2);
    var str= hour+":"+min+" "+day+"-"+month+"-"+year;
    return str;

}

var input = document.getElementById("searchField");

input.addEventListener("keyup", function(event) {
    if (event.keyCode === 13) {
        event.preventDefault();
        document.getElementById("searchButton").click();
    }else if (document.getElementById("searchField").value.length>2 || document.getElementById("searchField").value.length==0) {
        search();
    }

});


document.getElementById('searchButton').addEventListener('click', function (event) {
    event.preventDefault();
    search();
    return false;
});
