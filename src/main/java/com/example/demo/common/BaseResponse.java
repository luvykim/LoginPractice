package com.example.demo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({"stat", "err", "desc"})
@Getter
@Setter
public class BaseResponse {
    @JsonProperty("stat")
    private String stat = "ok";

    // @JsonInclude(Include.NON_NULL) : 적용 시, null일 때 아예 빠져버림(포함되지 않음)
    @JsonProperty("err")
    @JsonInclude(Include.NON_NULL)
    private ErrorResponse err;

    @JsonProperty("data")
    @JsonInclude(Include.NON_NULL)
    private Object data;

    public BaseResponse() {}

    public BaseResponse(Object data) {
        this.data = data;
    }
}
