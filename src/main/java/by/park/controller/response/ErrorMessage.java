package by.park.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private String message;

    private Throwable throwable;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
