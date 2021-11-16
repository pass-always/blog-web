<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="credentials">
    <h3 class="form-section-header">Credentials</h3>
    <div class="textbox" id="username-field-container">
        <i class="fas fa-user" aria-hidden="true"></i>
        <input autocomplete="off" type="text" placeholder="Username" name="userName" value="${user.getUserName()}" id="username-field">
    </div>

    <div class="error-message" id="user-name-used-error-message" hidden>
        That username is currently in use
    </div>

    <div class="textbox">
        <i class="fas fa-lock" aria-hidden="true"></i>
        <input type="password" placeholder="New Password <c:if test="${not empty user}">(leave blank if unchanged)</c:if>" name="password" value="" id="password-field">
    </div>

    <div class="textbox" id="confirm-pass-field-container">
        <i class="fas fa-lock" aria-hidden="true"></i>
        <input type="password" placeholder="Confirm Password <c:if test="${not empty user}">(leave blank if unchanged)</c:if>" name="confirm-password" value="" id="confirm-pass-field">
    </div>

    <div class="error-message" id="passwords-unmatched-error-message" hidden>
        Passwords do not match
    </div>
</div>
    <button class="btn user-details-form" id="show-credentials" type="button" hidden>Update My Credentials</button>

<div id="personal-details">
    <h3 class="form-section-header">Personal Details</h3>
    <div class="textbox">
        <i class="fas fa-user-edit" aria-hidden="true"></i>
        <input autocomplete="off" type="text" placeholder="First Name" name="firstName" value="${user.getUserFname()}" id="firstname-field">
    </div>

    <div class="textbox">
        <i class="fas fa-user-edit" aria-hidden="true"></i>
        <input autocomplete="off" type="text" placeholder="Last Name" name="lastName" value="${user.getUserLname()}" id="lastname-field">
    </div>


    <div class="textbox">
        <i class="fas fa-calendar-day" aria-hidden="true"></i>
        <input autocomplete="off" type="date" placeholder="1970-01-01" name="dob" value="${user.getUserDob()}" id="dob-field">
    </div>

    <div class="textbox">
        <i class="fas fa-book-user" aria-hidden="true"></i>
        <textarea placeholder="Say a few words about yourself" name="description" id="description-field" rows="5">${user.getDescription()}</textarea>
    </div>
</div>
    <button class="btn user-details-form" id="show-personal-details" type="button" hidden>Update My Personal Details</button>

<div id="contact-details">
    <h3 class="form-section-header">Contact</h3>
    <div class="textbox">
        <i class="fas fa-phone" aria-hidden="true"></i>
        <input autocomplete="off" type="text" placeholder="Phone Number" name="phone" value="${user.getUserContact()}" id="phoneNumber-field">
    </div>

    <div class="textbox">
        <i class="fas fa-at" aria-hidden="true"></i>
        <input autocomplete="off" type="email" placeholder="YourAddress@Email.com" name="email" value="${user.getUserEmail()}" id="email-field">
    </div>

    <div class="textbox">
        <i class="fas fa-address-card" aria-hidden="true"></i>
        <input autocomplete="off" type="text" placeholder="City" name="city" value="${user.getUserCity()}" id="city-field">
    </div>

    <div class="textbox">   <%--TODO replace with country list--%>
        <i class="fas fa-address-card" aria-hidden="true"></i>
        <input type="text" placeholder="Country" name="country" value="${user.getUserCountry()}" id="country-field">
    </div>
</div>
    <button class="btn user-details-form" id="show-contact-details" type="button" hidden>Update My Contact Details</button>

<div id="upload-container">
    <h3 class="form-section-header" id="avatar-header">User Avatar</h3>
    <div id="upload-menu">
        <input type="button" id="choose-button" value="Choose image">
        <input type="file" id="file-upload" name="avatar" accept="image/png, image/jpeg" />
        <img id="preview-image" src="${pageContext.request.contextPath}/images/default-avatar-1.png" alt="user-avatar" >
    </div>
</div>
    <button class="btn user-details-form" id="show-avatar" type="button" hidden>Update My Avatar</button>
