
window.addEventListener("load", function () {
    let chooseButton = document.querySelector("#choose-button");
    let fileButton = document.querySelector("#file-upload");
    chooseButton.addEventListener("click",function () {
        fileButton.click();
    })
    let previewPath;
    fileButton.addEventListener('change', function () {
        // user selected file
        let file = this.files[0];
        // local url
        previewPath = URL.createObjectURL(file);

        let currentImage = document.querySelector("#preview-image");
        currentImage.setAttribute("src", previewPath);
        currentImage.style.opacity = "1";
    });
})