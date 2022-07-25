package com.berluk.fileservice.controller;

import com.berluk.fileservice.model.FileDelete;
import com.berluk.fileservice.model.Metadata;
import com.berluk.fileservice.model.UploadResponse;
import com.berluk.fileservice.service.FileServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final FileServiceManager fileServiceManager;

    @PostMapping(path = "/document/{id}/upload-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
        return new ResponseEntity<>(fileServiceManager.save(file, id), HttpStatus.OK);
    }

    @GetMapping("/get-metadata")
    public ResponseEntity<List<Metadata>> getFiles(@RequestParam Long documentId) {
        return new ResponseEntity<>(fileServiceManager.getFiles(documentId), HttpStatus.OK);
    }

    @PostMapping(path = "/delete", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> delete(@RequestBody FileDelete fileDelete) {
        fileServiceManager.delete(fileDelete.getDocumentId(), fileDelete.getFilename());
        return new ResponseEntity<>("File has been deleted!", HttpStatus.OK);
    }

    @PostMapping("/delete-all")
    public ResponseEntity<String> deleteAll(@RequestParam Long documentId) {
        fileServiceManager.deleteAll(documentId);
        return new ResponseEntity<>("Files have been deleted!", HttpStatus.OK);
    }

    @GetMapping("/download")
    public void downloadFile(@RequestParam Long documentId, @RequestParam String filename, OutputStream out, HttpServletResponse response) throws IOException {
        response.addHeader("Accept-Ranges", "bytes");
        Path file = fileServiceManager.getFile(documentId, filename);
        Files.copy(file, out);
    }

    @GetMapping("/backup/download")
    public void downloadBackupFile(OutputStream out, HttpServletResponse response) throws IOException, InterruptedException {
        response.addHeader("Accept-Ranges", "bytes");
        Path file = fileServiceManager.doBackup();
        Files.copy(file, out);
    }
}