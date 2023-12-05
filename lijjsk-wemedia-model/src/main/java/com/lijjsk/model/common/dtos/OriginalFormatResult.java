package com.lijjsk.model.common.dtos;

import lombok.Data;

@Data
public class OriginalFormatResult {
    private String originalFormat;
    private byte[] fileContent;

    public OriginalFormatResult(String originalFormat, byte[] fileContent) {
        this.originalFormat = originalFormat;
        this.fileContent = fileContent;
    }
}