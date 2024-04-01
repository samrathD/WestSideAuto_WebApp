// Replace with your Instagram Access Token
const accessToken = 'IGQWRNZA04xUjRDRFJQd2d3LTd3M25pMXh5cXNmT1lYbElJVlM5ZAzZAEWGhpUERWZAUIzOGo1VTNDOFdvajlFRllVT1dqclVDTGxsMTJfM3Q0NldBdm1URVNreXpMcXNtSlZAZANS10SW90VUZAJbC1CZAEo1eWdxNUUtZAEUZD';

// Function to fetch and display Instagram feed
function getInstagramFeed() {
    fetch(`https://graph.instagram.com/v12.0/me/media?fields=id,caption,media_type,media_url,permalink,timestamp&access_token=${accessToken}`)
        .then(response => response.json())
        .then(data => {
            const feedContainer = document.getElementById('instagram-feed');

            data.data.forEach(post => {
                let postContainer = document.createElement('div');
                postContainer.classList.add("post-container")
                //postLink.href = post.permalink;
                //postLink.target = '_blank';

                let postImage = document.createElement('img');
                postImage.classList.add("post-image");
                postImage.src = post.media_url;
                postImage.alt = post.caption;

                let postTime = document.createElement('p');
                postTime.classList.add("post-time");
                var readableDate = new Date(post.timestamp);
                readableDate.toLocaleDateString('en-US');
                postTime.innerHTML = readableDate.toDateString();

                let postText = document.createElement('p');
                postText.innerHTML = post.caption;
                postText.classList.add("post-text");

                postContainer.appendChild(postImage);
                postContainer.appendChild(postTime);
                postContainer.appendChild(postText);
                feedContainer.appendChild(postContainer);
            });
        })
        .catch(error => console.error(error));
}

function getInstagramHeader(){
  fetch(`https://graph.instagram.com/me?fields=id,username&access_token=${accessToken}`)
    .then(response => response.json())
    .then (data => {
      const header = document.getElementById("instagram-header");

      let username = document.getElementById("instagram-name");
      username.innerHTML = "@" + data.username;

    })
    .catch(error => consoler.error(error));
}

// Call the function to fetch and display the Instagram feed
getInstagramFeed();
getInstagramHeader();
