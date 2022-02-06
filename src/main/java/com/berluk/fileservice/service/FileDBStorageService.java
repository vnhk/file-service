package com.berluk.fileservice.service;

import com.berluk.fileservice.model.Metadata;
import com.berluk.fileservice.model.FileMetadata;
import com.berluk.fileservice.repository.MetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileDBStorageService {
    private final MetadataRepository fileEntityRepository;

    public Metadata store(LocalDateTime createDate, FileMetadata metadata, String originalFilename) {
        Metadata fileEntity = new Metadata();
        fileEntity.setCreateDate(createDate);
        fileEntity.setFilename(originalFilename);
        fileEntity.setDocumentId(metadata.getDocumentId());
        fileEntity.setUserName(metadata.getUserName());

        return fileEntityRepository.save(fileEntity);
    }

    public List<Metadata> getFiles(Long documentId) {
        return fileEntityRepository.findAllByDocumentId(documentId);
    }

    public void delete(Long documentId, String filename) {
        fileEntityRepository.deleteByDocumentIdAndFilename(documentId, filename);
    }

    public void deleteAll(Long documentId) {
        fileEntityRepository.deleteByDocumentId(documentId);
    }
}
