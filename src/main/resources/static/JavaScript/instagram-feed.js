// Replace with your Instagram Access Token
const accessToken = 'IGQWRNZA04xUjRDRFJQd2d3LTd3M25pMXh5cXNmT1lYbElJVlM5ZAzZAEWGhpUERWZAUIzOGo1VTNDOFdvajlFRllVT1dqclVDTGxsMTJfM3Q0NldBdm1URVNreXpMcXNtSlZAZANS10SW90VUZAJbC1CZAEo1eWdxNUUtZAEUZD';

// Function to fetch and display Instagram feed
function getInstagramFeed() {
    fetch(`https://graph.instagram.com/v12.0/me/media?fields=id,caption,media_type,media_url,permalink,timestamp&access_token=${accessToken}`)
        .then(response => response.json())
        .then(data => {
            const feedContainer = document.getElementById('instagram-feed');

            data.data.forEach(post => {
                let postLink = document.createElement('a');
                postLink.classList.add("post-container")
                postLink.href = post.permalink;
                postLink.target = '_blank';

                let postImage = document.createElement('img');
                postImage.classList.add("post-image");
                postImage.src = post.media_url;
                postImage.alt = post.caption;

                let postText = document.createElement('p');
                postText.innerHTML = post.caption;
                postText.classList.add("post-text");
                postLink.appendChild(postImage);
                postLink.appendChild(postText);
                feedContainer.appendChild(postLink);
            });
        })
        .catch(error => console.error(error));
}

// Call the function to fetch and display the Instagram feed
getInstagramFeed();
