package Geeks.Chat.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private boolean success;
    private String message;
    private String username;

    public LoginResponse(boolean b, String successfullyLoggedIn, String username) {
    }

    public LoginResponse(String failedToLogIn, String successfullyLoggedIn) {
    }
}
