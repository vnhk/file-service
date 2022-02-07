package com.berluk.fileservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Metadata {
    @Id
    @GeneratedValue()
    private Long id;
    private String filename;
    private Long documentId;
    private LocalDateTime createDate;
    private String userName;
    private boolean deleted;
}
