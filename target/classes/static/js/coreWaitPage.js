// Function to move place images to the end positions of
// AddUser page.
function imageLocation(){
    // Door image
    var door = document.getElementsByTagName("img")[3];
    // Placement at correct location in page.
    door.style.left = "-0.7cm";

    // Monster at AddUser page
    var monster1 = document.getElementsByTagName("img")[5];
    // Placement at correct location in page.
    monster1.style.left="620px";
    monster1.style.top="300px";

    // Monster at AddUser page
    var monster2 = document.getElementsByTagName("img")[6];
    // Placement at correct location in page.
    monster2.style.left="20px";
    monster2.style.top="300px";
}