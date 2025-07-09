"use strict";

const field = {
    usernameField: document.getElementById("username-field"),
    passwordField: document.getElementById("password-field"),
    submitBtn: document.getElementById("submit-btn")
};

const loginAction = () => {
    field.submitBtn.addEventListener('click', () => {
        loginCheck(field.usernameField.value, field.passwordField.value, (x) => {
            console.log(x);
        });
    });
};

window.onload = function () {
    loginAction();
};