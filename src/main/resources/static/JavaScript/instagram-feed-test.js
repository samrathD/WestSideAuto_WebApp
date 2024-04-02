// Replace with your Instagram Access Token
const accessToken = 'IGQWRNZA04xUjRDRFJQd2d3LTd3M25pMXh5cXNmT1lYbElJVlM5ZAzZAEWGhpUERWZAUIzOGo1VTNDOFdvajlFRllVT1dqclVDTGxsMTJfM3Q0NldBdm1URVNreXpMcXNtSlZAZANS10SW90VUZAJbC1CZAEo1eWdxNUUtZAEUZD';

// Function to fetch and display Instagram feed in carousel format
function getInstagramFeed() {
    fetch(`https://graph.instagram.com/v12.0/me/media?fields=id,caption,media_type,media_url,permalink,timestamp&access_token=${accessToken}`)
        .then(response => response.json())
        .then(data => {
            const carouselInner = document.querySelector('.carousel-inner');
            carouselInner.innerHTML = ''; // Clear existing items

            // Split data into groups of three
            for (let i = 0; i < data.data.length; i += 3) {
                let carouselItem = document.createElement('div');
                carouselItem.classList.add('carousel-item');
                if (i === 0) {
                    carouselItem.classList.add('active');
                }

                let row = document.createElement('div');
                row.classList.add('row');

                // Loop through the next three images or less
                for (let j = i; j < i + 3 && j < data.data.length; j++) {
                    let col = document.createElement('div');
                    col.classList.add('col-md-4');

                    let postImage = document.createElement('img');
                    postImage.classList.add('d-block', 'w-100', 'img-fluid');
                    postImage.style.height = '300px'; // Adjust as needed
                    postImage.src = data.data[j].media_url;
                    postImage.alt = data.data[j].caption;

                    col.appendChild(postImage);
                    row.appendChild(col);
                }

                carouselItem.appendChild(row);
                carouselInner.appendChild(carouselItem);
            }

            // Reset the active item
            document.querySelector('.carousel-item').classList.add('active');
        })
        .catch(error => console.error(error));
}

// Call the getInstagramFeed function once the script has loaded
getInstagramFeed();