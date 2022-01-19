// Function to move image slowly every some
// milliseconds.
function moveImage(){
    for (let i = 0; i < 100; i++) {
        setTimeout(move, i*100, i);
    }
}

// Function that performs the image move in a certain direction
function move(i) {
    // The monster image is the 4th image element of the LoginPage.
    var imageToMove = document.getElementsByTagName("img")[3];
    imageToMove.style.left = -i*6+"px";
}