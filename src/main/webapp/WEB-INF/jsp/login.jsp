<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
    <h2>Login</h2>
    <form action="/auth/login" method="post">
        <label>Email:</label><input type="email" name="email" required /><br>
        <label>Password:</label><input type="password" name="password" required /><br>
        <button type="submit">Login</button>
    </form>
    <p>${error}</p>
</body>
</html>