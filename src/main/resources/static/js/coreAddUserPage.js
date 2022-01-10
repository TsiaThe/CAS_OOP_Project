// Function to move image slowly every some
// milliseconds.
function moveImage(){
    for (let i = 0; i < 20; i++) {
        setTimeout(move, i*100, i);
    }
}

// Function that moves the door to the left.
function move(i) {
    // The monster image is the 4rth image element of the LoginPage.
    var imageToMove = document.getElementsByTagName("img")[3];
    imageToMove.style.left = (-2.7+0.1*i)+"cm";
}