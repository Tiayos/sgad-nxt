package ec.edu.ups.sgadnuxt.exception.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatusCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseCustom implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("status") // 400
    private int status;

    @JsonProperty("error") // Bad Reques
    private HttpStatusCode error;

    @JsonProperty("message") // 400
    private String message;

    @JsonProperty("path") // 400
    private String path;

    @JsonProperty("details")
    private Map<String, String> details;

    public ErrorResponseCustom(
            LocalDateTime timestamp,
            HttpStatusCode error,
            String detailError,
            String path,
            Map<String, String> details) {
        this.timestamp = timestamp;
        this.status = error.value();
        this.error = error;
        this.path = path;

        this.message = detailError;
        this.details = details;

    }

    public ErrorResponseCustom(
            LocalDateTime timestamp,
            HttpStatusCode error,
            String detailError,
            String path) {
        this.timestamp = timestamp;
        this.error = error;
        this.status = error.value();
        this.message = detailError;
        this.path = path;

    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatusCode getError() {
        return this.error;
    }

    public void setError(HttpStatusCode error) {
        this.error = error;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getDetails() {
        return this.details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

}