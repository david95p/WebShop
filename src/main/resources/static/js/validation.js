function validateFName(){
    var fn = document.getElementById("firstname").value;
    var valid= checkString(fn);
    showValidation("firstname", valid, fn.length==0);
    return valid;
}

function validateLName(){
    var ln = document.getElementById("lastname").value;
    var valid = checkString(ln);
    showValidation("lastname", valid, ln.length==0);
    return valid;
}

function validateUsername(){
    var un = document.getElementById("username").value;
    var valid = checkUser(un);
    showValidation("username", valid, un.length==0);
    return valid;
}

function validatePassword(){
    var pswd = document.getElementById("password").value;
    var valid =checkPassword(pswd);
    showValidation("password", valid, pswd.length==0);
    return valid;
}

function validateConfPassword(){
    var pswd = document.getElementById("password").value;
    var cfpswd = document.getElementById("confpassword").value;
    var valid=pswd==cfpswd;
    showValidation("confpassword", valid, cfpswd.length==0);
    return valid
}

function checkString(str){
    return (/^[a-zA-Z]+$/g).test(str);
}

function checkUser(str){
    return (/^[a-zA-Z0-9_]+$/g).test(str)
}

function checkPassword(str){
    return  (/^[a-zA-Z0-9_]{8,15}$/g).test(str);
}

function showValidation(field, valid, isEmpty){
    if (isEmpty){
        document.getElementById(field).classList.remove("red-border");
        document.getElementById(field).classList.remove("green-border");
    } else if (valid) {
        document.getElementById(field).classList.add("green-border");
        document.getElementById(field).classList.remove("red-border");
    }else {
        document.getElementById(field).classList.add("red-border");
        document.getElementById(field).classList.remove("green-border");
    }
}

function validateform(){
    var valid= validateLName();
    var valid1= validateFName();
    var valid2= validateUsername();
    var valid3= validatePassword();
    var valid4= validateConfPassword();

    if (valid&&valid1&&valid2&&valid3&&valid4){
        if (document.getElementById("registrationform").querySelector("#submitbutton") != null){
            return;
        }
        var div = document.createElement("DIV");
        div.setAttribute("id", "submitbutton");
        div.classList.add("form-group");
        div.classList.add("align-content-center");
        var button = document.createElement("BUTTON")
        button.classList.add("btn");
        button.classList.add("btn-dark");
        button.setAttribute("type", "submit");
        var t = document.createTextNode("Register");
        button.appendChild(t);
        div.appendChild(button);
        document.getElementById("registrationform").appendChild(div);
    } else {
        if (document.getElementById("registrationform").querySelector("#submitbutton") != null){
            document.getElementById("submitbutton").remove();
        }
    }
}