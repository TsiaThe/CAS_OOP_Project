function moveImage(){
    for (let i = 0; i < 100; i++) {
        setTimeout(move, i*100, i);
    }
}

function move(i) {

    var win = window,
        doc = document,
        docElem = doc.documentElement,
        body = doc.getElementsByTagName('body')[0];


    var imageToMove1 = document.getElementById("Image1");
    var imageToMove2 = document.getElementById("Image2");
    imageToMove1.style.left = i*10+"px";
    imageToMove1.style.top = i*10+"px";


    if ((body.clientHeight-i*10)>100){
        imageToMove2.style.left = i*20+"px";
        imageToMove2.style.top = body.clientHeight-i*10+"px";
    }
}
