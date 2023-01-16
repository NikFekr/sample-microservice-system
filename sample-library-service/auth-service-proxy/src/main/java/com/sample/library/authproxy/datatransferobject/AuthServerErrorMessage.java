package com.sample.library.authproxy.datatransferobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Esmaeil NikFekr on 6/7/21.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class AuthServerErrorMessage {

    private String timestamp;
    private String status;
    @JsonProperty("error")
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
    private String trace;
    private String message;
    private String path;
}
