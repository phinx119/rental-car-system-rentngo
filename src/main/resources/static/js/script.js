document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('logoutBtn').addEventListener('click', function (event) {
        event.preventDefault();

        if (confirm('Are you sure you want to log out?')) {
            window.location.href = 'login.html';
        }
    });
});

