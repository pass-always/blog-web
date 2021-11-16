window.addEventListener("load", () => {

    let registrationFormContainer = document.querySelector("#registration-form-container");
    let loginFormContainer = document.querySelector("#login-form-container");
    let loginBox = document.querySelector(".login-box");

    document.querySelector("#register-link").addEventListener("click", function(event) {
        event.preventDefault();
        registrationFormContainer.hidden = false;
        loginFormContainer.hidden = true;

        // registration box is much taller, so add a margin at the top to stop it from jutting up against the top
        loginBox.style.transform = "translate(-50%, -15%)"
        // registration box has more fields and some require longer inputs, so increasing the width should make it look better
        loginBox.style.width = "33%";
    });

    document.querySelector("#login-link").addEventListener("click", function(event) {
        event.preventDefault();
        registrationFormContainer.hidden = true;
        loginFormContainer.hidden = false;
        loginBox.style = null //.clear;   // revert to standard styles
    });
});


// //Validation code for Inputs
//
// let user = 'admin';
// let pass = 'password';
//
// let username = document.forms['form']['username'];
// let password = document.forms['form']['password'];
//
// let username_error = document.getElementById('username_error');
// let password_error = document.getElementById('password_error');
//
// function username_verify() {
//     if (username.value.length >= 3){
//         username_error.style.display ="none";
//         return true;
//     }
// }
//
// username.addEventListener('textInput', username_verify);
//
// function password_verify() {
//     if (password.value.length >= 5){
//         password_error.style.display ="none";
//         return true;
//     }
// }
//
// password.addEventListener('textInput', password_verify);
//
// function validated() {
//     if (username.value.length<4) {
//         username_error.style.display = "block";
//         username.focus();
//         return false;
//     }
//     if (password.value.length < 6) {
//         password_error.style.display = "block";
//         password.focus();
//         return false;
//     }
//     alert("Login Successful");
//
// }
//
// /*
//
// function validation() {
//     var user = 'admin';
//     var pass = 'password';
//
//     var userName = document.getElementById('username').value;
//     var userPassword = document.getElementById('password').value;
//
//     if ((user == userName) && (pass == userPassword)){
//             swal({
//                 title: "Login Successful!",
//                 text:  "Redirecting...",
//                 type: "success",
//                 timer: 50000,
//             });
//
//     }
//     else if ((userName.length <=0) && (userPassword.length <=0)){
//         swal({
//             title: "Login Failed...",
//             text: "Username or Password is not fulfilled",
//             icon: "info",
//         });
//     }
//     else {
//         swal({
//             title: "Login Failed...",
//             text: "Username or Password is incorrect, Please try again",
//             icon: "error",
//         });
//     }
// }
// */