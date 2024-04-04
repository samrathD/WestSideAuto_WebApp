// Replace with your Instagram Access Token
const accessToken = 'IGQWRNZA04xUjRDRFJQd2d3LTd3M25pMXh5cXNmT1lYbElJVlM5ZAzZAEWGhpUERWZAUIzOGo1VTNDOFdvajlFRllVT1dqclVDTGxsMTJfM3Q0NldBdm1URVNreXpMcXNtSlZAZANS10SW90VUZAJbC1CZAEo1eWdxNUUtZAEUZD';

// Function to fetch and display Instagram feed in carousel format
function getInstagramFeed() {
    fetch(`https://graph.instagram.com/v12.0/me/media?fields=id,caption,media_type,media_url,permalink,timestamp&access_token=${accessToken}`)
        .then(response => response.json())
        .then(data => {
            const carouselInner = document.querySelector('.carousel-inner');
            carouselInner.innerHTML = ''; // Clear existing items

            // Loop through each post in the Instagram data
            for (let i = 0; i < data.data.length; i += 3) {
                // Create Bootstrap row element for each set of three cards
                let row = document.createElement('div');
                row.classList.add('row');

                // Loop through the next three posts or less
                for (let j = i; j < i + 3 && j < data.data.length; j++) {
                    // Create Bootstrap column for each card
                    let col = document.createElement('div');
                    col.classList.add('col-md-4');

                    // Create Bootstrap card element
                    let card = document.createElement('div');
                    card.classList.add('card', 'service-card');

                    // Apply the hover effect
                    card.addEventListener('mouseover', function() {
                        this.style.transform = 'translateY(-5px)';
                        this.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.1)';
                    });

                    card.addEventListener('mouseout', function() {
                        this.style.transform = 'translateY(0)';
                        this.style.boxShadow = 'none';
                    });

                    // Set custom width for cards
                    card.style.maxWidth = '300px'; // Adjust as needed

                    // Create anchor tag for the image or video linking to the post
                    let postLink = document.createElement('a');
                    postLink.href = data.data[j].permalink;
                    postLink.target = '_blank'; // Open link in a new tab
                    card.appendChild(postLink);

                    // Check if media type is video
                    if (data.data[j].media_type === 'VIDEO') {
                        // Create video element
                        let video = document.createElement('video');
                        video.src = data.data[j].media_url;
                        video.controls = true; // Show video controls
                        video.style.width = '100%'; // Adjust width
                        video.style.height = '200px'; // Set height to match images
                        video.style.objectFit = 'cover'; // Ensure the video covers the entire space
                        video.style.borderRadius = '10px'; // Set border radius to match image cards
                        postLink.appendChild(video); // Append video to anchor tag
                    } else {
                        // Create card image
                        let cardImg = document.createElement('img');
                        cardImg.classList.add('card-img-top');
                        cardImg.src = data.data[j].media_url;
                        cardImg.alt = data.data[j].caption;

                        // Set dimensions for images
                        cardImg.style.height = '200px'; // Adjust as needed
                        cardImg.style.objectFit = 'cover'; // Ensure the image covers the entire space

                        postLink.appendChild(cardImg); // Append image to anchor tag
                    }

                    // Create card body
                    let cardBody = document.createElement('div');
                    cardBody.classList.add('card-body');

                    // Create icons container
                    let iconsContainer = document.createElement('div');
                    iconsContainer.classList.add('icons-container', 'd-flex', 'justify-content-start'); // Adjusted to justify content to start

                    // Add Font Awesome outlined icons for heart, comment, share, and send
                    let heartIcon = document.createElement('i');
                    heartIcon.classList.add('far', 'fa-heart', 'mr-2'); // Use 'far' class for outlined heart icon
                    iconsContainer.appendChild(heartIcon);

                    let commentIcon = document.createElement('i');
                    commentIcon.classList.add('far', 'fa-comment', 'mr-2'); // Use 'far' class for outlined comment icon
                    iconsContainer.appendChild(commentIcon);

                    let sendIcon = document.createElement('i');
                    sendIcon.classList.add('far', 'fa-paper-plane'); // Use 'far' class for outlined send icon
                    iconsContainer.appendChild(sendIcon);

                    cardBody.appendChild(iconsContainer);

                    card.appendChild(cardBody);

                    // Append card to column
                    col.appendChild(card);

                    // Append column to row
                    row.appendChild(col);
                }

                // Append row to carousel inner
                let carouselItem = document.createElement('div');
                carouselItem.classList.add('carousel-item');
                if (i === 0) {
                    carouselItem.classList.add('active');
                }
                carouselItem.appendChild(row);
                carouselInner.appendChild(carouselItem);
            }
        })
        .catch(error => console.error(error));
}

// Call the getInstagramFeed function once the script has loaded
getInstagramFeed();
