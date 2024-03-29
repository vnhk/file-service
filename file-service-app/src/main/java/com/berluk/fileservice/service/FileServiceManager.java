package com.berluk.fileservice.service;

import com.berluk.fileservice.model.FileDownloadException;
import com.berluk.fileservice.model.Metadata;
import com.berluk.fileservice.model.UploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileServiceManager {
    private final FileDBStorageService fileDBStorageService;
    private final FileDiskStorageService fileDiskStorageService;

    public UploadResponse save(MultipartFile file, Long documentId) {
        UploadResponse uploadResponse = new UploadResponse();
        uploadResponse.setFilename(file.getOriginalFilename());
        String filename = fileDiskStorageService.store(file, documentId);
        LocalDateTime createDate = LocalDateTime.now();
        uploadResponse.setCreateDate(createDate);

        Metadata stored = fileDBStorageService.store(createDate, documentId, filename);

        uploadResponse.setMetadataId(stored.getId());

        return uploadResponse;
    }

    public List<Metadata> getFiles(Long documentId) {
        return fileDBStorageService.getFiles(documentId);
    }

    public Path getFile(Long documentId, String filename) {
        try {
            Optional<Path> file = fileDiskStorageService.getFile(documentId, filename);
            if (file.isEmpty()) {
                throw new FileDownloadException("Cannot find file " + filename);
            } else {
                return file.get();
            }
        } catch (Exception e) {
            throw new FileDownloadException("Cannot get file " + filename);
        }
    }

    public void delete(Long documentId, String filename) {
        fileDBStorageService.delete(documentId, filename);
        fileDiskStorageService.delete(documentId, filename);
    }

    public void deleteAll(Long documentId) {
        fileDiskStorageService.deleteAll(documentId);
        fileDBStorageService.deleteAll(documentId);
    }

    public Path doBackup() throws IOException, InterruptedException {
        return fileDiskStorageService.doBackup();
    }
}
