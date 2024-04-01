
document.addEventListener("DOMContentLoaded", function () {
    // Define a mapping of services to their respective costs
    const serviceCosts = {
        "Starlight Headliner": 200,
        "Ambient Lighting": 150,
        "Vinyl Wrap": 500,
        "Sound and Stereo Installs": 300,
        "Chrome deletes and De-badging": 100,
        "Headlight Restoration": 80,
        "BodyKit Installs": 600
        // Add more services and costs as needed
    };

    // Function to calculate and display the estimated cost
    // Function to calculate and display the estimated cost
function calculateEstimatedCost() {
    const service = document.getElementById("service").value;
    const estimatedCost = serviceCosts[service] || 0;
    const make = document.getElementById("make").value;
    const model = document.getElementById("carModel").value;
    const year = document.getElementById("year").value;

    const estimateDetailsContainer = document.getElementById("estimateDetails");
    estimateDetailsContainer.innerHTML = `
        <p>Service: ${service}</p>
        <p>Car Make: ${make}</p>
        <p>Car Model: ${model}</p>
        <p>Year: ${year}</p>
        <p>Estimated Cost: $${estimatedCost}</p>`;
    estimateDetailsContainer.style.display = "block";
}

    // Event listener for service selection
    document.getElementById("service").addEventListener("change", calculateEstimatedCost);

    // Validate and handle form submission
    const submit = document.querySelector("#submit");
    submit.addEventListener("click", function (event) {
        const valid = document.querySelector("#valid");
        if (valid.textContent !== "Valid Date") {
            event.preventDefault();
            alert("Please fill in all fields before submitting the form.");
        }
    });

let slots = document.querySelector("#slots");
let emailInput = document.querySelector("#email");
let descriptionInput = document.querySelector("#description");
// let submit = document.querySelector("#submit");
let valid = document.querySelector("#valid");
let timeSlotContainer = document.querySelector(".timeContainer");
let selectedSlot = null;

// let nextBtn = document.querySelector("#next");
// let container1 = document.querySelector(".container1");
// let container2 = document.querySelector(".container2");


const times = [];//An array that stores all the time slots
timeSlotGen();//Generate time slots and add to the 'times' list

// Set the minimum booking date and time
const today = new Date();
const yesterday = new Date(today.getTime());
yesterday.setDate(today.getDate() - 1);
console.log(`Today is ${today}`);
const year = yesterday.getFullYear();
const month = (yesterday.getMonth() + 1).toString().padStart(2, '0');
const day = (yesterday.getDate() + 1).toString().padStart(2, '0');
let min = `${year}-${month}-${day}`;
console.log(`Min is ${min}`);
slots.min = min;

// Event handler for the appointment input
slots.onchange = function() {
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
    if (appointment.getDay() === 5 || appointment.getDay() === 6) {
        updateValidity("Invalid Date");
        timeSlotContainer.style.display = "none";
        return;
    }

    // If time is valid display the time slots for that day
    updateValidity("Valid Date");
    timeSlotContainer.style.display = "flex";
    let deleteSlots = document.querySelectorAll("#timeSlot");
    if(deleteSlots.length > 0){
        deleteSlots.forEach((slot)=>{
            slot.remove();
        })
    //Empty the array to display new time slots
        // if(times.length > 0){
        //     times.length = 0;
        // }
    }
    displayTimeSlots();


    // Check if email field is empty
    if (emailInput.value.trim() === "") {
        //alert("Please enter your email.");
        emailInput.focus();
        return;
    }

    // Check if description field is empty
    if (descriptionInput.value.trim() === "") {
        //alert("Please provide a description of your service.");
        descriptionInput.focus();
        return;
    }
}

function updateValidity(message) {
    valid.textContent = message;
    valid.style.display = "inline";
    if (message !== "Valid Date") {
        valid.classList.add("invalid");
        valid.style.color = "red";
        submit.disabled = true;
        // nextBtn.disabled = true;
        
    } else {
        valid.classList.add("valid");
        valid.style.color = "green";
        submit.disabled = false;
        // nextBtn.disabled = false;
    }
}

// Event listener for form submission
submit.addEventListener("click", function(event) {
    if (valid.textContent !== "Valid Date") {
        event.preventDefault();
        alert("Please fill in all fields before submitting the form.");
    }
});

//Generate all the time slots and adds in the times list
function timeSlotGen(){
    for(let hours = 8; hours < 12;hours++){
        for(let minutes = 0; minutes<60; minutes+=15){
            const formattedHour = hours.toString().padStart(2,'0');
            const formattedMinutes = minutes.toString().padStart(2,'0');
            times.push(`${formattedHour}:${formattedMinutes} AM`);
        }
    }
    for(let minutes = 0; minutes<60; minutes+=15){
        const formattedMinutes = minutes.toString().padStart(2,'0');
        times.push(`12:${formattedMinutes} PM`);
    }

    for(let hours = 1; hours <8 ;hours++){
        for(let minutes = 0; minutes<60; minutes+=15){
            const formattedHour = hours.toString().padStart(2,'0');
            const formattedMinutes = minutes.toString().padStart(2,'0');
            times.push(`${formattedHour}:${formattedMinutes} PM`);
    }
}
console.log(times);
}
//A hidden input field that stores the time that user wants to book
const timeInput = document.querySelector("#time");
//Getting all the existing appointments
const existingAppointments = document.querySelectorAll(".existingAppointments");

function displayTimeSlots(){
    let userDate = new Date(slots.value+"T00:00");
    for(let time of times){
        let found = false;
        existingAppointments.forEach((appointment)=>{
            let existingTime = appointment.dataset.time;
            let existingDate = new Date(appointment.dataset.date);
            
            // console.log(`ExistingTime is -${existingTime}    userTime is-${time}`);
            // console.log(`ExistingDate is -${existingDate}`);
            // console.log(`userDate is-${userDate}`);
            let existingYear = existingDate.getFullYear();
            let existingMonth = existingDate.getMonth();
            let existingDay = existingDate.getDate();
        
            let userYear = userDate.getFullYear();
            let userMonth = userDate.getMonth();
            let userDay = userDate.getDate();
        
            // Compare the year, month, day and time to check for appointment conflicts
            if (userYear === existingYear && userMonth === existingMonth && 
                userDay === existingDay && time == existingTime) {
                found = true;
                return;
            }
        })
        if(!found){
            let clicked = 0;
            const slot = document.createElement("div");
            slot.id = "timeSlot";
            slot.innerHTML = time;
            slot.style.border = "2px solid white";
            slot.style.borderRadius = "10px";
            slot.style.padding = "3px";
            slot.style.color = "white";
            timeSlotContainer.appendChild(slot);

            //Add hover effect
            slot.addEventListener("mouseover",()=>{
                slot.style.backgroundColor = "white";
                slot.style.color = "rgba(17, 24, 39, 1)";
            })
            slot.addEventListener("mouseout",()=>{
                if (clicked == 0){
                    slot.style.backgroundColor = "rgba(17, 24, 39, 1)";
                    slot.style.color = "white"; 
                }
                
            })  
            slot.addEventListener("click", () => {
                // If the clicked slot is already selected, deselect it
                // if (slot === selectedSlot) {
                //     slot.style.backgroundColor = "rgba(17, 24, 39, 1)";
                //     slot.style.color = "white";
                //     selectedSlot = null; // Update selectedSlot to indicate no slot is selected
                //     timeInput.value = ""; // Clear the time input value
                // } else {
                //     // Deselect previously selected slot, if any
                //     if (selectedSlot) {
                //         selectedSlot.style.backgroundColor = "rgba(17, 24, 39, 1)";
                //         selectedSlot.style.color = "white";
                //     }
            
                    // Select the clicked slot
                    slot.style.backgroundColor = "white";
                    slot.style.color = "rgba(17, 24, 39, 1)";
            
                    // Update the selected slot reference
                    selectedSlot = slot;
                    clicked++;
                    // Update the time input value
                    timeInput.value = time;
                    console.log(timeInput.value);
               // }
            })
        }
        
    }
}

const tabs = document.getElementsByClassName("tab");
const next = document.querySelector("#next");
const previous = document.querySelector("#previous");
const steps = document.getElementsByClassName("step");
let currentTab = 0;
showTab(currentTab);
//Function only shows the specific form page
function showTab(n){
    console.log(tabs.length);
    for(let i = 0; i<tabs.length ; i++){
        tabs[i].style.display = "none";
    }
    tabs[n].style.display = "block";
    //Disable previous button on the right time
    if(n == 0){
        previous.style.display = "none";
    }
    else{
        previous.style.display = "inline";
    }
    //Disable next button on the right time
    if(n==(tabs.length-1)){
        next.style.display = "none";
        submit.style.display = "inline";
    }
    else{
        next.style.display = "inline";
        submit.style.display = "none";
    }
    //Call the function that displays the correct steps of the form
    fixStepIndicator(n);
}

function fixStepIndicator(n){
    for(let i = 0; i<steps.length; i++){
        steps[i].className = steps[i].className.replace(" active", "");
    }
    //... and adds the "active" class to the current step:
    steps[n].className += " active";
}


next.addEventListener("click",()=>{
    if(validateForm){
        currentTab = currentTab + 1;
        showTab(currentTab);
    }
    
})
previous.addEventListener("click",()=>{
    currentTab = currentTab - 1;
    showTab(currentTab);
})

});


























