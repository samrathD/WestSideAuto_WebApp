// get the element with the appointment input
let slots = document.querySelector("#slots");
let submit = document.querySelector("#submit");
let valid = document.querySelector("#valid");
// let invalid = document.querySelector("#invalid");

console.log(valid);

//Set the minimum booking date and time
const today = new Date();
const year = today.getFullYear();
const month = (today.getMonth() + 1).toString().padStart(2, '0');
const day = (today.getDate()+1).toString().padStart(2, '0');
let min = year+"-"+month+"-"+day+"T09:30";
slots.min = min;

// slots.max = "0000-00-00T17:00"
console.log(slots.max);
console.log(slots.min);

const dayNames = ["Sunday", "Monday", "Tuesday" , "Wednesday"];
slots.onchange = function(){
    let appointment = new Date(slots.value);
    let min_date = new Date(min);
    let max_date = new Date("2024-12-31T17:00");

    console.log(`min hours ${min_date.getHours()}`);
    console.log(`Appointment hours ${appointment.getHours()}`);
    console.log(`max hours ${max_date.getHours()}`);
    console.log(`Appointment mins ${appointment.getMinutes()}`);
    console.log(`min Minutes ${min_date.getMinutes()}`);
    console.log(`max Minutes ${max_date.getMinutes()}`);
    

    //checking if the date is valid 
    let day_num = appointment.getDay();
    console.log(day_num);

    //day_num = 0 means Sunday and day_num = 6 means Saturday
    //If appointment is on Saturnday or Sunday show status as invalid
    if(day_num == 0 || day_num == 6 ){
        console.log("Invalid date");
        submit.disabled = true;
        valid.style.display= "inline";
        valid.style.color = "red";
        valid.innerHTML = "✖ Invalid Date"
    }
    //Check hours
    else if(appointment.getHours()<min_date.getHours()||
    appointment.getHours()>max_date.getHours()){
        console.log("Invalid Time hours");
        submit.disabled = true;
        valid.style.display= "inline";
        valid.style.color = "red";
        valid.innerHTML = "✖ Invalid Date"
    }
    //If hours are satisfied but not minutes
    else if((appointment.getHours()==min_date.getHours()&&
            appointment.getMinutes()<min_date.getMinutes()) ||
            (appointment.getHours()==max_date.getHours()&&
            appointment.getMinutes()>max_date.getMinutes())){
        console.log("Invalid Time minutes");
        submit.disabled = true;
        valid.style.display= "inline";
        valid.style.color = "red";
        valid.innerHTML = "✖ Invalid Date"
    }
    else{
        console.log("Valid Time");
        submit.disabled = false;
        valid.style.display= "inline";
        valid.style.color = "green";
        valid.innerHTML = "✓ Valid Date and Time"
    }

} 

























