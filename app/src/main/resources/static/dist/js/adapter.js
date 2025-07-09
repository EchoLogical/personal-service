"use strict";

const baseUrl = document.getElementsByTagName("html")[0]
    .getAttribute("base-url");

const endpoints = {
    loginCheck: new URL("/api/v1/login/validate", baseUrl),
    loginRequest: new URL("/api/v1/login/request", baseUrl)
};


const loginCheck = (username, password, action) => {
    const data = JSON.stringify({
        "username": username,
        "password": password
    });
    let response = null;

    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === this.DONE) {
            action(xhr);
        }
    });

    xhr.open("POST", endpoints.loginCheck);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.send(data);

    return response;
};

const loginProcess = (username, password, rememberMe, action) => {
    const data = JSON.stringify({
        "username": username,
        "password": password,
        "rememberMe": rememberMe
    });
    let response = null;

    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === this.DONE) {
            action(xhr);
        }
    });

    xhr.open("POST", endpoints.loginCheck);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.send(data);

    return response;
};