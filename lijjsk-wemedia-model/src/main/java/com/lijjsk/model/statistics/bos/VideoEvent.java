package com.lijjsk.model.statistics.bos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @JsonProperty("videoId")
    private Integer videoId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("createdTime")
    private Date createdTime;
}
