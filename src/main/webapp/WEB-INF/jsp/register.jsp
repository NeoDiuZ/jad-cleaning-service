<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
    <h2>Register</h2>
    <form action="/auth/register" method="post">
        <label>Email:</label><input type="email" name="email" required /><br>
        <label>Password:</label><input type="password" name="passwordHash" required /><br>
        <button type="submit">Register</button>
    </form>
    <p>${message}</p>
</body>
</html>