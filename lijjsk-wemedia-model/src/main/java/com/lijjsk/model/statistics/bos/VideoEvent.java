package com.lijjsk.model.statistics.bos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoEvent {
    @JsonProperty("videoId")
    private Integer videoId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("createdTime")
    private Date createdTime;
}
