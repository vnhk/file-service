package com.berluk.fileservice.repository;

import com.berluk.fileservice.model.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Long> {
    List<Metadata> findAllByDocumentId(Long documentId);

    void deleteByDocumentIdAndFilename(Long documentId, String filename);

    void deleteByDocumentId(Long documentId);
}
