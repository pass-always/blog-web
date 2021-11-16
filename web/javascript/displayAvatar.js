
window.addEventListener("load", function () {

    let previewPath;
    let uploadButton = document.querySelector("#avatar-upload");
    uploadButton.addEventListener('change', function () {
        // user selected file
        let file = this.files[0];
        // local url
        previewPath = URL.createObjectURL(file);

        /*--------------------------------------------------------------------------------------------------------*/
        // set name of the file
    /*        let message = document.querySelector("#avatar-message");
        console.log(file.name)
        message.innerHTML = file.name;
        message.style.display = 'inline-block';*/


        let currentImage = document.querySelector("#preview-image");
        currentImage.setAttribute("src", previewPath);
        currentImage.style.opacity = "1";
        /*--------------------------------------------------------------------------------------------------------*/

        // show cancel and upload buttons now
        // document.querySelector("#cancel-image").style.display = 'inline-block';
        // document.querySelector("#upload-button").style.display = 'inline-block';
    });
})