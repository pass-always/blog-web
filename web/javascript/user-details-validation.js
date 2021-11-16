/**
 * Javascript that validates the new user registration form
 * Should be included on any pages that may use the user-details-fields.jsp fragment
 * Validates:
 *      the selected username is not currently in use by another user
 *      the password and confirm-password fields are identical
 * In addition to the user-details-fields fragment, it expects there to be a submit input element with the id submit-user-details
 */
window.addEventListener("load", async () => {

    let currentUserName
    if (document.querySelector("h3.user-name") !== null) {   // conditional as may not have a current user name
        currentUserName = document.querySelector("h3.user-name").innerText;
    }


    const passwordField = document.querySelector("#password-field");
    const confirmPasswordField = document.querySelector("#confirm-pass-field");
    const usernameField = document.querySelector("#username-field");

    const submitButton = document.querySelector('#submit-user-details');

    const errorMessage = document.querySelector("#passwords-unmatched-error-message");
    const userNameUsedMessage = document.querySelector("#user-name-used-error-message");


    usernameField.addEventListener("change", validateUserName);
    passwordField.addEventListener("keyup", getPasswordValidationFunction(passwordField));
    confirmPasswordField.addEventListener("keyup", getPasswordValidationFunction(confirmPasswordField));


    /**
     * Validates that the username is unique
     * Case is ignored - ADMIN, admin and AdMiN are all considered the same username
     */
    async function validateUserName() {

        if (usernameField.value === "") {

            submitButton.disabled = true;
            userNameUsedMessage.hidden = false;
            userNameUsedMessage.innerText = "User name cannot be blank";
            return;

        } else if (equalsIgnoreCase(usernameField.value, currentUserName)) {
            submitButton.disabled = false;
            userNameUsedMessage.hidden = true;
            return; // hasn't changed; do nothing; prevents username taken message being displayed for current username

        }

        await fetch(`./username-used?username=${usernameField.value}`).then(response => {
            if (response.status === 500) {  // there was an error accessing the data base or a similarly bad event
                userNameUsedMessage.innerText = "The database is unavailable. Please try again later";
                userNameUsedMessage.hidden = false;
                return;
            }

            response.json().then(json => {
                if (json) { // this endpoint returns only a boolean
                    userNameUsedMessage.innerText = "User name is in use"
                    userNameUsedMessage.hidden = false;
                    submitButton.disabled = true
                } else {
                    userNameUsedMessage.hidden = true;
                    submitButton.disabled = false;
                }

            });
        });
    }

    /**
     * Returns a closure with the appropriate function for validating the password fields
     * If New Password field, then unless they match or it is blank the submit button is disabled
     * If Confirm Password field, then unless they match, the submit button is disabled or
     * @param field the password field that this function is being attached to
     * @returns {function(...[*]=)} the validation function required
     */
    function getPasswordValidationFunction(field) {

        return function () {
            if (passwordsMatch()) {
                submitButton.disabled = false;
                errorMessage.hidden = true;
                return;
            }

            if (field === passwordField) {
                if (confirmPasswordField.value === '') {
                    submitButton.disabled = true;
                }
            } else {
                submitButton.disabled = true;
                errorMessage.hidden = false;
            }
        };
    }

    /**
     * Tests if the passwordField and the confirmPassword field contain the same value
     * Will return true if both are empty
     * @returns {boolean}
     */
    function passwordsMatch() {
        return passwordField.value === confirmPasswordField.value;
    }

    /**
     * Tests if two strings are equal apart from case
     * @param text
     * @param other
     * @returns {boolean}
     */
    function equalsIgnoreCase(text, other) {
        return text.localeCompare(other, undefined, { sensitivity: 'base' }) === 0;
        // TODO test if we have issues with mobile browsers
    }
});