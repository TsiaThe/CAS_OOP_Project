
function moveImage(){
    for (let i = 0; i < 100; i++) {
        setTimeout(move, i*100, i);
    }
}


function move(i) {
    var imageToMove = document.getElementsByTagName("img")[3];
    imageToMove.style.left = -i*6+"px";
}
