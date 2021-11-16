$(document).ready(function() {

    configureDetailsForm();

    const tabBtn = document.querySelectorAll('.nav ul li');
    const tab = document.querySelectorAll('.tab');

    function tabs(panelIndex) {
        tab.forEach(function (node) {
            node.style.display='none';
        });
        tab[panelIndex].style.display = 'block';
    }

    tabs(0);

    $('.user-details').click(function() {
        toggleActiveClass($(this));
        $('.profile-details').show().siblings().hide();
    });

    $('.user-comment').click(function() {
        toggleActiveClass($(this));
        $('.profile-comment').show().siblings().hide();
    });

    $('.user-dashboard').click(function() {
        toggleActiveClass($(this));
        $('.profile-dashboard').show().siblings().hide();
    });


});

let bio = document.querySelector('.bio');

function bioText() {
    bio.oldText = bio.innerText;
    bio.innerText = bio.innerText.substring(0,100) + "...";
    bio.innerHTML += "&nbsp;" + `<span onclick='addLength()' id='see-more-bio'>See More</span>`
}
bioText();

function addLength(){
    bio.innerHTML = bio.oldText;
    bio.innerHTML += "&nbsp;" + `<span onclick='bioText()' id='see-less-bio'>See Less</span>`
}

function toggleActiveClass(clickedElement) {
    clickedElement.addClass("active").siblings().removeClass('active');
}

/**
 * Configures the user details form used in the user-details tab.
 * Assigns click handlers to the various buttons
 * Sets the visibility of the form
 */
function configureDetailsForm() {
    const formObject = getUserDetailsFormObject();

    formObject.credentials.container.hidden = true;
    formObject.credentials.button.hidden = false;

    formObject.personalDetails.container.hidden = true;
    formObject.personalDetails.button.hidden = false;

    formObject.contactDetails.container.hidden = true;
    formObject.contactDetails.button.hidden = false;

    formObject.avatar.container.hidden = true;
    formObject.avatar.button.hidden = false;

    formObject.submitButton.container.hidden = true;

    formObject.credentials.button.addEventListener("click", function() {
        toggleSection(formObject.credentials, this);
    });

    formObject.personalDetails.button.addEventListener("click", function() {
        toggleSection(formObject.personalDetails, this);
    });

    formObject.contactDetails.button.addEventListener("click", function() {
        toggleSection(formObject.contactDetails, this);
    });

    formObject.avatar.button.addEventListener("click", function() {
        toggleSection(formObject.avatar, this);
    });

    function toggleSection(formSection, element) {
        toggleElementVisibility(formSection);

        if (formSection.visible) {
            element.innerText = "Hide Section (changes will be lost)";
            formObject.submitButton.container.hidden = false;

        } else {

            element.innerText = formSection.visibleText;
            tryHideSubmitButton();

            // reset each of the contained fields to their initial value so we don't have accidental changes after submission
            formSection.fields.forEach(e => {
                e.object.value = e.initialValue;
                // manually fire the events that are listening for form changes so they know that the form has been changed
                // this ensures that the warning messages that are shown (e.g. because the user name is taken) are cleared
                e.object.dispatchEvent(new Event("change"));
                e.object.dispatchEvent(new Event("keyup"));
            });

        }
    }

    /**
     * Checks whether the submit button should be hidden, and hides it if so
     * Submit button should be hidden when all form sections are not visible
     */
    function tryHideSubmitButton() {
        if (!formObject.credentials.visible &&
                !formObject.personalDetails.visible &&
                !formObject.contactDetails.visible &&
                !formObject.avatar.visible) {

            formObject.submitButton.container.hidden = true;
            formObject.submitButton.visible = false;

        }
    }
}

/**
 * Toggles the visibility of section by added or removing the hidden attribute
 * @param formSection the sub-object within the props object that relates to that section
 * @return true if the section is now visible
 */
function toggleElementVisibility(formSection) {
    formSection.container.hidden = !formSection.container.hidden;
    formSection.visible = !formSection.visible;
}

function getUserDetailsFormObject() {
    return {
        credentials: {
            visible: false,
            button: document.querySelector("#show-credentials"),
            container: document.querySelector("#credentials"),
            visibleText: "Update My Credentials",
                fields: [
                {
                    initialValue: document.querySelector("#username-field").value,
                    object: document.querySelector("#username-field")
                },
                {
                    initialValue: "",   // password initial value is always an empty field
                    object: document.querySelector("#password-field")
                },
                {
                    initialValue: "",   // password initial value is always an empty field
                    object: document.querySelector("#confirm-pass-field")
                }
            ]
        },
        personalDetails: {
            visible: false,
            button: document.querySelector("#show-personal-details"),
            container: document.querySelector("#personal-details"),
            visibleText: "Update My Personal Details",
            fields: [
                {
                    initialValue: document.querySelector("#firstname-field").value,
                    object: document.querySelector("#firstname-field")
                },
                {
                    initialValue: document.querySelector("#lastname-field").value,
                    object: document.querySelector("#lastname-field")
                },
                {
                    initialValue: document.querySelector("#dob-field").value,
                    object: document.querySelector("#dob-field")
                },
                {
                    initialValue: document.querySelector("#description-field").value,
                    object: document.querySelector("#description-field")
                }
            ]
        },
        contactDetails: {
            visible: false,
            button: document.querySelector("#show-contact-details"),
            container: document.querySelector("#contact-details"),
            visibleText: "Update My Contact Details",
            fields: [
                {
                    initialValue: document.querySelector("#phoneNumber-field").value,
                    object: document.querySelector("#phoneNumber-field")
                },
                {
                    initialValue: document.querySelector("#email-field").value,
                    object: document.querySelector("#email-field")
                },
                {
                    initialValue: document.querySelector("#city-field").value,
                    object: document.querySelector("#city-field")
                },
                {
                    initialValue: document.querySelector("#country-field").value,
                    object: document.querySelector("#country-field")
                }
            ]
        },
        avatar: {
            visible: false,
            button: document.querySelector("#show-avatar"),
            container: document.querySelector("#upload-container"),
            visibleText: "Update My Avatar",
            fields: [
                {
                    initialValue: null, // avatar default value is always null
                    object: document.querySelector("#file-upload")
                }
            ],
        },
        submitButton: {
            visible: false,
            container: document.querySelector("#submit-user-details")   // strictly speaking this is the button itself, but it's not in a container (other than the form, so I've used container to be consistent
        },
        before: document.querySelector(".before-form"),
        after: document.querySelector(".after-form")
    };
}