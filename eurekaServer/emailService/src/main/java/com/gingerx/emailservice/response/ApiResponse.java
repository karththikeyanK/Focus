package com.eimsky.ruleengine.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    public static final String SUCCESS = "SUCCESS";
    public static final String WARNING = "WARNING";
    public static final String ERROR = "ERROR";

    private String status;
    private String msg;
    private T data;

}
