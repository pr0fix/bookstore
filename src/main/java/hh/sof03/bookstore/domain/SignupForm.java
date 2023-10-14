package hh.sof03.bookstore.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SignupForm {

    @NotEmpty(message = "Username cannot be empty.")
    @Size(min = 5, max = 30, message = "Username length must be between 5 and 30 letters.")
    private String username = "";

    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = 8, max = 30, message = "Password length must be between 8 and 30 letters.")
    private String password = "";

    @NotEmpty(message = "Re-type password cannot be empty.")
    @Size(min = 8, max = 30, message = "Password length must be between 8 and 30 letters.")
    private String passwordCheck = "";

    @NotEmpty(message = "Email cannot be empty.")
    @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "The email you used was not valid.")
    private String email = "";

    @NotEmpty
    private String role = "USER";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
