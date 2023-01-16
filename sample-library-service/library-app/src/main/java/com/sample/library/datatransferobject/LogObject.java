package com.sample.library.datatransferobject;

import lombok.Builder;
import lombok.Data;

/**
 * @author Esmaeil NikFekr on 2/23/21.
 */
@Builder
@Data
public class LogObject {
    private String key;
    private Long start;
    private String user;
    private String clazz;
    private String method;
    private String parameters;
    private String returnValue;
    private Long end;
    private Long duration;

    public String toString() {
        return "duration='" + this.duration + '\'' +
                ", start='" + this.start + '\'' +
                ", user='" + this.user + '\'' +
                ", clazz='" + this.clazz + '\'' +
                ", method='" + this.method + '\'' +
                ", parameters='" + this.parameters + '\'' +
                ", returnValue='" + this.returnValue + '\'' +
                ", end='" + this.end + '\'' +
                ", key='" + this.key + '\'';
    }
}
