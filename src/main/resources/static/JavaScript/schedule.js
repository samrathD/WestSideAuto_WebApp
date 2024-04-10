

document.addEventListener("DOMContentLoaded", function () {
    // Define a mapping of services to their respective costs
    const serviceCosts = {
        "Starlight Headliner": "$299 - $1299",
        "Ambient Lighting": "$280-$380",
        "Vinyl Wrap": "$500",
        "Stereo Upgrade": "$350-$480",
        "Backup Camera": "$130",
        "Body Kit": "$1500-$3000"
        // Add more services and costs as needed
    };

    // Function to calculate and display the estimated cost
    function calculateEstimatedCost() {
        const service = document.getElementById("service").value;
        const estimatedCost = serviceCosts[service] || 0;
        const make = document.getElementById("make").innerHTML;
        console.log(make);
        const model = document.getElementById("carModel").value;
        const year = document.getElementById("year").value;

        const estimateDetailsContainer = document.getElementById("estimateDetails");
        estimateDetailsContainer.innerHTML = `
            <p>Service: ${service}</p>
            
            <p>Estimated Cost: ${estimatedCost}</p>`;

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
    let nameInput = document.querySelector("#name");

    let emailInput = document.querySelector("#email");
    let descriptionInput = document.querySelector("#description");
    let phoneInput = document.querySelector("#phoneNumber");
    let serviceInput = document.querySelector("#service");
    let carMake = document.querySelector("#make");
    let carModel = document.querySelector("#carModel");
    let carYear = document.querySelector("#year");
    //let submit = document.querySelector("#submit");
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
        //validateForm();
        validateDate();
    }

    // descriptionInput.oninput = function() {
    //     validateForm();
    // }

    function validateDate(){
        let appointment = new Date(slots.value);
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

        //Remove the old time slots in order to avoide the overlapping of times
        if(deleteSlots.length > 0){
            deleteSlots.forEach((slot)=>{
                slot.remove();
            })
        }

        //Display all the time slots
        displayTimeSlots();

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

    function handelMouseOver(slot){
        slot.style.backgroundColor = "black";
        slot.style.color = "white";
        console.log("Mouse over");
    }
    function handelMouseOut(slot){
        slot.style.backgroundColor = "white";
        slot.style.color = "black";
    }

    //A function to display all the time slots
    function displayTimeSlots(){
        let userDate = new Date(slots.value+"T00:00");
        for(let time of times){
            let found = false;
            let count = 0;//Counter to keep track of same appointments
            
            existingAppointments.forEach((appointment)=>{
                let existingTime = appointment.dataset.time;
                let existingDate = new Date(appointment.dataset.date);
                
                let existingYear = existingDate.getFullYear();
                let existingMonth = existingDate.getMonth();
                let existingDay = existingDate.getDate();
            
                let userYear = userDate.getFullYear();
                let userMonth = userDate.getMonth();
                let userDay = userDate.getDate();
            
                // Compare the year, month, day and time to check for appointment conflicts
                if (userYear === existingYear && userMonth === existingMonth && 
                    userDay === existingDay && time == existingTime) {
                    count++;
                    //Two people can book appointment at the same time
                    if(count == 2){
                        found = true;
                        return; 
                    }
                    
                }
            })

            if(!found){
                //If the appointment does not exist display the time slot
                let clicked = false;
                const slot = document.createElement("div");
                slot.id = "timeSlot";
                slot.className = "hoverEffect";
                slot.innerHTML = time;
                slot.style.cursor = "pointer";
                slot.style.borderRadius = "10px";
                slot.style.padding = "3px";
                timeSlotContainer.appendChild(slot);

                slot.addEventListener("click", () => {
                    // If a slot is already selected, deselect it
                    if(selectedSlot!=null){
                        let allSlots = document.querySelectorAll("#timeSlot");
                        allSlots.forEach((slot)=>{
                            slot.classList.remove("hoverAfter");
                            slot.classList.add("hoverEffect");
                        })
                    }
                    //Remove the hover effect from the timeslots
                    slot.classList.remove("hoverEffect");
                    slot.classList.add("hoverAfter");
                    console.log(slot.class);
                    // Update the selected slot reference
                    selectedSlot = slot;
                    clicked = true;
                    console.log(clicked);
                    // Update the time input value
                    timeInput.value = time;
                    console.log(timeInput.value);
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
        console.log(`length of the tabs is ${tabs.length}`);
        for(let i = 0; i<tabs.length ; i++){
            tabs[i].style.display = "none";
        }
        tabs[n].style.display = "block";
        console.log(`Showing tab ${n}`);
        //Disable previous button on the right time
        if(n == 0){
            previous.style.display = "none";
        }
        else{
            previous.style.display = "inline";
        }
    // Disable next button on the right time
        if(n==(tabs.length)-1){
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


    let validateMsg = document.querySelector("#validate");
    let emailError = document.getElementById("emailError");
    let phoneError = document.getElementById("phoneError");

    //Helper function to check if all the form feilds are filled
    function validateTab1(){
        //Check if slot is empty
        if(slots.value == "" || timeInput.value == "" || nameInput.value == "" || 
        emailInput.value == "" || phoneInput.value == "" || serviceInput.value == "Service"){
            
            validateMsg.style.display = "flex";
            return false;
            
        }

        //Check if email is inputted correctly
        if (!emailInput.validity.valid){
            emailError.style.display = 'block';
            return false;
        }

        emailError.style.display = "none";
        validateMsg.style.display = "none";
        
        return true;
        
    }

    //Helper function to check if all the form feilds are filled
    function validateTab2(){
        if(carMake.value == "" || carModel.value == ""|| carYear.value == ""){
            validateMsg.style.display = "flex";
            return false;
        }
        return true;
    }



    // next.addEventListener("click",()=>{
    // //Check if all form feilds are filled
    //     if(currentTab == 0 ){
    //         if(validateTab1()){            
    //             currentTab = currentTab + 1;
    //             showTab(currentTab);
    //             return;
    //         }
    //     }
    // //Check if all form feilds are filled
    //     if(currentTab == 1){
    //         if(validateTab2()){
    //             currentTab = currentTab + 1;
    //             showTab(currentTab);
    //             return;
    //         }
    //     }
    //     // if(currentTab == 2){
    //     //     currentTab = currentTab + 1;
    //     //     showTab(currentTab);
    //     //     return;
    //     // }
    // })
    next.addEventListener("click",()=>{
    //Check if all form feilds are filled
        if(currentTab == 0 ){
            if(validateTab1()){  
                currentTab = currentTab + 1;
                showTab(currentTab);
                return;
            }
        }
    //Check if all form feilds are filled
        if(currentTab == 1){
            console.log("hello");
            if(validateTab2()){
                currentTab = currentTab + 1;
                console.log(currentTab);
                showTab(currentTab);
                return;
            }
        }
    })

    previous.addEventListener("click",()=>{
        currentTab = currentTab - 1;
        showTab(currentTab);
    })

});


























