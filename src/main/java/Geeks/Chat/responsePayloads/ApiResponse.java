package Geeks.Chat.responsePayloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String message;

    public ApiResponse(String message){
        this.message=message;
    }
}
