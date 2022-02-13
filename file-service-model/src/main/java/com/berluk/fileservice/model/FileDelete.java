package com.berluk.fileservice.model;

import lombok.Data;

@Data
public class FileDelete {
    private long documentId;
    private String filename;
}
