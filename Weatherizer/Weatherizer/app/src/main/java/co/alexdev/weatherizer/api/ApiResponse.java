package co.alexdev.weatherizer.api;

import java.io.IOException;

import androidx.annotation.Nullable;
import retrofit2.Response;

public class ApiResponse<T> {

    public final int code;
    @Nullable
    public final T body;
    @Nullable
    public final String errorMessage;

    public ApiResponse(Throwable error) {
        this.code = 500;
        this.body = null;
        this.errorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {

        code = response.code();

        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String message = null;

            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {

                }
            }

            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }

            errorMessage = message;
            body = null;
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }
}