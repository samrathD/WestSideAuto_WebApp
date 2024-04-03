// function deleteAppointmentFromList(button) {
    
//     var appointmentName = button.dataset.appointmentName;
//     var appointmentEmail = button.dataset.appointmentEmail;

//     if (confirm("Are you sure you want to delete this appointment?")) {
//         fetch('/appointments/deleteFromList', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/x-www-form-urlencoded'
//             },
//             body: new URLSearchParams({
                
//                 name: appointmentName,
//                 email: appointmentEmail
//             })
//         })
//         .then(response => {
//             if (response.ok) {
//                 button.closest('.appointment-card').remove();
//                 alert("Appointment deleted successfully.");
//             } else {
//                 alert("Failed to delete appointment.");
//             }
//         })
//         .catch(error => {
//             console.error('Error:', error);
//         });
//     }
// }
//Accessing the button
let updateUsers = document.querySelectorAll(".update");
let deleteUsers = document.querySelectorAll(".delete");
let add = document.querySelector(".add");

// update.addEventListener("click",()=>{
//     console.log("Hello");
// })

for(let user of updateUsers){
    user.addEventListener("click",()=>{
        //Access the id of the student
        let uid = user.id;
        console.log(uid);
        //Direct user to the page that updates the student's information
        let updateEndpoint = `/appointments/update/${uid}`;
        window.location.href = updateEndpoint;
    })
}
for(let user of deleteUsers){
    user.addEventListener("click",()=>{
        //Access the id of the student
        let uid = user.id;
        //Direct user to the page that delets the student's information
        let deleteEndpoint = `/appointments/delete/${uid}`;
        window.location.href = deleteEndpoint;
    })
}

// add.addEventListener("click",()=>{
//     let addEndpoint = `/add.html`;
//     window.location.href = addEndpoint;
// })