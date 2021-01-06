function isPasswordOk() {
    let password = document.getElementById("exampleInputPassword1");
    let re_password = document.getElementById("exampleReInputPassword1");
    let warning = document.getElementById("password_warning");
    let submit_button = document.getElementById("submit_button");
    if (password.value !== re_password.value) {
        warning.setAttribute("class", "alert alert-danger");
        submit_button.disabled = true;
    } else {
        warning.setAttribute("class", "d-none");
        submit_button.disabled = false;
        return true;
    }
}

function checkPasswordsForUpdate() {
    let oldPassword = document.getElementById("exampleOldPassword1");
    let newPassword = document.getElementById("exampleNewPassword1");
    let reNewPassword = document.getElementById("exampleReNewPassword1");
    let button = document.getElementById("updatePasswordButton");
    button.disabled = !(oldPassword.value !== newPassword.value && newPassword.value === reNewPassword.value);
}

function searchForUsers() {
    const httpRequest = new XMLHttpRequest();
    let location = document.getElementById("location_of_friends_or_found_users");
    location.innerHTML = "";
    httpRequest.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            const array = JSON.parse(httpRequest.responseText);
            for (let i = 0; i < array.length; ++i) {
                let row = document.createElement("DIV");
                row.setAttribute("class", "row mb-3 friends");
                let col = document.createElement("DIV");
                col.setAttribute("class", "col p-0");
                let card = document.createElement("DIV");
                card.setAttribute("class", "card");
                let card_body = document.createElement("DIV");
                card_body.setAttribute("class", "card-body");
                let flex1 = document.createElement("DIV");
                flex1.setAttribute("class", "row");
                let flex2 = document.createElement("DIV");
                flex2.setAttribute("class", "col-3");
                let img = document.createElement("IMG");
                img.setAttribute("class", "w-100");
                if (array[i].picture_url === undefined) {
                    img.setAttribute("src", "/vendor/images/fodera.png");
                } else {
                    img.setAttribute("src", array[i].picture_url);
                }
                flex2.appendChild(img);
                let flexCol = document.createElement("DIV");
                flexCol.setAttribute("class", "col-9");
                let a_wrapper = document.createElement("A");
                a_wrapper.setAttribute("href", "/profile?id=" + array[i].id)
                a_wrapper.setAttribute("class", "text-decoration-none text-dark")
                let h5 = document.createElement("H5");
                h5.setAttribute("class", "card-title");
                h5.innerHTML = array[i].full_name;
                a_wrapper.appendChild(h5)
                let p = document.createElement("P");
                p.setAttribute("class", "card-text");
                p.innerHTML = array[i].age + " years old";
                let a = document.createElement("A");
                a.setAttribute("class", "card-link btn btn-outline-primary");
                if (array[i].request_sent === true) {
                    a.innerHTML = "Request Sent";
                    a.setAttribute("href", "#");
                } else if (array[i].request_sent === false) {
                    a.innerHTML = "Add to Friends";
                    a.setAttribute("onclick", "addToFriend(" + array[i].id + ")");
                } else {
                    a.innerHTML = "Send Message";
                    a.setAttribute("href", "/chat?id=" + array[i].id);
                }
                flexCol.appendChild(a_wrapper);
                flexCol.appendChild(p);
                flexCol.appendChild(a);
                flex1.appendChild(flex2);
                flex1.appendChild(flexCol);
                card_body.appendChild(flex1);
                card.appendChild(card_body);
                col.appendChild(card);
                row.appendChild(col);
                location.appendChild(row);
            }
        }
    }
    let search_text = document.getElementById("search_input").value;
    if (search_text.length > 0) {
        console.log("sending request for search_users")
        httpRequest.open("GET", "/search_users?search_text=" + search_text, true);
        httpRequest.send();
    } else {
        httpRequest.open("GET", "/search_users", true);
        httpRequest.send();
    }
}

function addToFriend(id) {
    const httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            searchForUsers();
        }
    }
    httpRequest.open("GET", "/add_to_friends?id=" + id, true);
    httpRequest.send();
}

function confirmFriendRequest(id) {
    const httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            searchForUsers();
        }
    }
    httpRequest.open("GET", "/confirm_friend_request?id=" + id, true);
    httpRequest.send();
}

function rejectFriendRequest(id) {
    const httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            searchForUsers();
        }
    }
    httpRequest.open("GET", "/reject_friend_request?id=" + id, true);
    httpRequest.send();
}