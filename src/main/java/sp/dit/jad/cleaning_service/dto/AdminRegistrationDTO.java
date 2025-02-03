package sp.dit.jad.cleaning_service.dto;

public class AdminRegistrationDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String adminCode;

    // Constructors
    public AdminRegistrationDTO() {}

    public AdminRegistrationDTO(String email, String password, String confirmPassword, String adminCode) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.adminCode = adminCode;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }
}