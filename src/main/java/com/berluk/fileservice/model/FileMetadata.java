package com.berluk.fileservice.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class FileMetadata {
    @NotEmpty(message = "Document id cannot be empty!")
    private Long documentId;
    @NotEmpty(message = "User name cannot be empty!")
    private String userName;
    @NotEmpty(message = "File cannot be empty!")
    private MultipartFile file;
}
