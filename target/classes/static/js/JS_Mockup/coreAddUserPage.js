// ------------------------------------------------------------------
// Author: Theofanis Tsiantas
// Developemt span: 27.12.2021 - 05.01.2022
// Description:
// The following js-functions were used at the HTML_Mockup webpages
// of the same name during testing of the backend and the communication.
// ------------------------------------------------------------------

// Function to move image slowly every some
// milliseconds.
function moveImage(){
    // Monster at AddUser page
    // Placement at correct location in page.
    var monster1 = document.getElementsByTagName("img")[5];
    monster1.style.left="20px";
    monster1.style.top="300px";

    // Monster at AddUser page
    // Placement at correct location in page.
    var monster2 = document.getElementsByTagName("img")[6];
    monster2.style.left="620px";
    monster2.style.top="300px";

    // Moving loop
    for (let i = 0; i < 100; i++) {
        setTimeout(move, i*100, i);
    }
}

// Function that moves the door to the left.
function move(i) {
    // Door image
    var door = document.getElementsByTagName("img")[3];
    // Monsters at AddUser page
    var monster1 = document.getElementsByTagName("img")[5];
    var monster2 = document.getElementsByTagName("img")[6];
    // Parameter which controls how much the door slides to the right.
    var doorslide = 21;

    // Move the door but only slightly.
    if (i<doorslide){
        door.style.left = (-2.7+0.1*i)+"cm";
    }
    // Move the monsters after the door has been moved.
    else{
        // Move the monsters but only a bit.
        if (parseFloat(monster1.style.left)<620) {
            i=i-doorslide; // Reset for smooth movement.
            monster1.style.left = 20+i*20+"px";
            monster2.style.left = 620-i*20+"px";
        }
    }
}