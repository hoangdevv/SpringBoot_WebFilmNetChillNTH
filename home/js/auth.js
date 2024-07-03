function loginUser() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    var loginRequest = {
        email: email,
        password: password
    };

    fetch('http://localhost:8989/users/login-user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginRequest),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Login successful:', data);
            localStorage.setItem('jwtToken', data.token);
            localStorage.setItem('userId', data.id);
            localStorage.setItem('userEmail', data.email);
            localStorage.setItem('userRoles', JSON.stringify(data.roles));
            window.location.href = '/home/index.html';
        })
        .catch(error => {
            console.error('Login error:', error.message);
            document.getElementById('errorText').innerText = 'Login failed: ' + error.message;
        });
}