package com.berluk.fileservice.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UploadResponse {
    private String filename;
    private Long metadataId;
    private LocalDateTime createDate;
}
