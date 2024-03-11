let slots = document.querySelector("#slots");
let emailInput = document.querySelector("#email");
let descriptionInput = document.querySelector("#description");
let submit = document.querySelector("#submit");
let valid = document.querySelector("#valid");

// Set the minimum booking date and time
const today = new Date();
const year = today.getFullYear();
const month = (today.getMonth() + 1).toString().padStart(2, '0');
const day = (today.getDate() + 1).toString().padStart(2, '0');
let min = `${year}-${month}-${day}T09:30`;
slots.min = min;

// Event handler for the appointment input
slots.onchange = function() {
    validateForm();
}

emailInput.oninput = function() {
    validateForm();
}

descriptionInput.oninput = function() {
    validateForm();
}

function validateForm() {
    let appointment = new Date(slots.value);
    let min_date = new Date(min);
    let max_date = new Date("2024-12-31T17:00");

    // Check if the appointment is on a weekend
    if (appointment.getDay() === 0 || appointment.getDay() === 6) {
        updateValidity("Invalid Date");
        return;
    }

    // Check if the appointment time is within valid hours
    if (appointment < min_date || appointment > max_date) {
        updateValidity("Invalid Date");
        return;
    }

    // Check if email field is empty
    if (emailInput.value.trim() === "") {
        alert("Please enter your email.");
        emailInput.focus();
        return;
    }

    // Check if description field is empty
    if (descriptionInput.value.trim() === "") {
        alert("Please provide a description of your service.");
        descriptionInput.focus();
        return;
    }

    // All fields are filled, form is valid
    updateValidity("Valid Date and Time");
}

function updateValidity(message) {
    valid.textContent = message;
    valid.style.display = "inline";
    if (message !== "Valid Date and Time") {
        valid.classList.add("invalid");
        submit.disabled = true;
    } else {
        valid.classList.add("valid");
        submit.disabled = false;
    }
}

// Event listener for form submission
submit.addEventListener("click", function(event) {
    if (valid.textContent !== "Valid Date and Time") {
        event.preventDefault();
        alert("Please fill in all fields before submitting the form.");
    }
});
