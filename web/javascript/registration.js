
/*-------------Registration-------------*/

function validate() {
    var uname = document.form.userName.value;

    if (uname == ""){
        alert("Please enter username");
        document.form.userName.focus();
        return false;
    }

    if ((uname.length<5) || (uname.length>15)){
        alert("Username is of valid length");
        document.form.userName.focus();
        return false;
    }

    var fname = document.form.firstName.value;

    if(fname === '') {
        alert("First Name cannot be blank");
        document.form.firstName.focus();
        return false;
    }

    var lname = document.form.lastName.value;

    if(lname === '') {
        alert("Last Name cannot be blank");
        document.form.lastName.focus();
        return false;
    }

    var pass = document.form.passWord.value;
    var illegalChar = /[\W_]/;

    if (pass == ""){
        alert("Please enter password");
        document.form.passWord.focus();
        return false;
    }

    if ((pass.length<6) || (pass.length>15)){
        alert("Password should be between 6 and 15 characters");
        document.form.passWord.focus();
        return false;
    }
    else if (illegalChar.test(pass)){
        alert("Password contains illegal character");
        document.form.passWord.focus();
        return false;
    }

    var dob = document.form.doB.value;
    if(dob === '') {
        alert("Select the DOB");
        document.form.doB.focus();
        return false;
    }

    var descrp = document.form.description.value;
    if(descrp === '') {
        alert("Description field cannot be blank");
        document.form.description.focus();
        return false;
    }

    alert("Registration Successful!!")
    window.open("profile.html");
}