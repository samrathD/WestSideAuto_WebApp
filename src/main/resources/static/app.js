// Initialize Google API client
gapi.load('client:auth2', initAuth);

function initAuth() {
    gapi.auth2.init({
        clientId: '693240202893-lhpmmkfjnplviqpt80ltkloh7onils8r.apps.googleusercontent.com',
        scope: 'https://www.googleapis.com/auth/calendar.events'
    }).then(function() {
        // Listen for sign-in state changes
        updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
        gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);

        // Set up sign-in and sign-out buttons
        document.getElementById('signin-button').onclick = handleSignIn;
        document.getElementById('signout-button').onclick = handleSignOut;
    });
}

function updateSigninStatus(isSignedIn) {
    if (isSignedIn) {
        document.getElementById('auth-buttons').style.display = 'none';
        document.getElementById('booking-form').style.display = 'block';
    } else {
        document.getElementById('auth-buttons').style.display = 'block';
        document.getElementById('booking-form').style.display = 'none';
    }
}

function handleSignIn() {
    gapi.auth2.getAuthInstance().signIn().then(function() {
        console.log('User signed in.');
    });
}

function handleSignOut() {
    gapi.auth2.getAuthInstance().signOut().then(function() {
        console.log('User signed out.');
    });
}

// Function to get the access token
function getAccessToken() {
    return gapi.auth2.getAuthInstance().currentUser.get().getAuthResponse().access_token;
}

// Function to create a new event on Google Calendar
function createEvent(summary, location, startDateTime, endDateTime) {
    const accessToken = getAccessToken();
    const apiUrl = 'https://www.googleapis.com/calendar/v3/calendars/primary/events';

    const eventData = {
        summary: summary,
        location: location,
        start: {
            dateTime: startDateTime,
            timeZone: 'America/Vancouver' // Update time zone here
        },
        end: {
            dateTime: endDateTime,
            timeZone: 'America/Vancouver' // Update time zone here
        }
    };

    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + accessToken,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(eventData)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Event created:', data);
        alert('Appointment booked successfully!');
    })
    .catch(error => {
        console.error('Error creating event:', error);
        alert('Failed to book appointment. Please try again later.');
    });
}

// Form submission handler
document.getElementById('booking-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(this);
    const name = formData.get('name');
    const email = formData.get('email');
    const date = formData.get('date');
    const time = formData.get('time');

    const startDateTime = `${date}T${time}`;
    const endDateTime = ''; // You need to calculate the end time based on your appointment duration

    // Call function to create event
    createEvent('Appointment', 'Location', startDateTime, endDateTime);
});
