//Popup logout window
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('logoutBtn').addEventListener('click', function (event) {
        event.preventDefault();

        if (confirm('Are you sure you want to log out?')) {
            window.location.href = 'home-page.html';
        }
    });
});

//Change between thumbnail view and list view
 const thumbnailViewBtn = document.getElementById('thumbnailViewBtn');
 const listViewBtn = document.getElementById('listViewBtn');
 const thumbnailView = document.getElementById('thumbnailView');
 const listView = document.getElementById('listView');

 // Add event listeners to buttons
 thumbnailViewBtn.addEventListener('click', () => {
     thumbnailView.style.display = 'block';
     listView.style.display = 'none';
 });

 listViewBtn.addEventListener('click', () => {
     thumbnailView.style.display = 'none';
     listView.style.display = 'block';
 });
