package com.berluk.fileservice.service;

import com.berluk.fileservice.model.FileDeleteException;
import com.berluk.fileservice.model.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FileDiskStorageService {
    @Value("${file.service.storage.folder}")
    private String FOLDER;

    public String store(MultipartFile file, Long documentId) {
        String fileName = getFileName(file.getOriginalFilename(), documentId);
        try {
            String destination = getDestination(fileName, documentId);
            File fileTmp = new File(destination);
            File directory = new File(FOLDER + File.separator + documentId + File.separator);
            directory.mkdirs();
            file.transferTo(fileTmp);
        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }
        return fileName;
    }


    public Optional<Path> getFile(Long documentId, String filename) {
        File file = new File(getDestination(filename, documentId));

        Path path = Paths.get(file.getAbsolutePath());

        return Optional.of(path);
    }

    private String getDestination(String filename, Long documentId) {
        return FOLDER + File.separator + documentId + File.separator + filename;
    }

    private String getFileName(String fileName, Long documentId) {
        String extension = FilenameUtils.getExtension(fileName);
        String tempFileName = fileName;
        boolean fileExist = isFileWithTheName(fileName, documentId);
        int i = 1;
        while (fileExist) {
            tempFileName = fileName.substring(0, fileName.indexOf(extension) - 1) + "(" + i++ + ")." + extension;
            fileExist = isFileWithTheName(tempFileName, documentId);
        }

        return tempFileName;
    }

    private boolean isFileWithTheName(String fileName, Long documentId) {
        return new File(getDestination(fileName, documentId)).exists();
    }

    public void delete(Long documentId, String filename) {
        String destination = getDestination(filename, documentId);
        File file = new File(destination);
        if (!file.delete()) {
            throw new FileDeleteException("File cannot be deleted!");
        }
    }

    public void deleteAll(Long documentId) {
        try {
            String destination = getDestination("", documentId);
            File file = new File(destination);
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            throw new FileDeleteException("Files cannot be deleted!");
        }
    }
}
