package com.berluk.fileservice.controller;

import com.berluk.fileservice.model.FileMetadata;
import com.berluk.fileservice.model.Metadata;
import com.berluk.fileservice.model.UploadResponse;
import com.berluk.fileservice.service.FileServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FileUploadController {

    private final FileServiceManager fileServiceManager;

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UploadResponse> upload(@ModelAttribute @Valid FileMetadata metadata) {
        return new ResponseEntity<>(fileServiceManager.save(metadata.getFile(), metadata), HttpStatus.OK);
    }

    @GetMapping("/get-metadata")
    public ResponseEntity<List<Metadata>> getFiles(@RequestParam Long documentId) {
        return new ResponseEntity<>(fileServiceManager.getFiles(documentId), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long documentId, @RequestParam String filename) {
        fileServiceManager.delete(documentId, filename);
        return new ResponseEntity<>("File has been deleted!", HttpStatus.OK);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAll(@RequestParam Long documentId) {
        fileServiceManager.deleteAll(documentId);
        return new ResponseEntity<>("Files have been deleted!", HttpStatus.OK);
    }

    @GetMapping("/download")
    public void downloadFile(@RequestParam Long documentId, @RequestParam String filename, OutputStream out) throws IOException {
        Path file = fileServiceManager.getFile(documentId, filename);
        Files.copy(file, out);
    }

//    @GetMapping("/download")
//    public ResponseEntity<Resource> downloadFile(@RequestParam Long documentId, @RequestParam String filename) {
//        ByteArrayResource file = fileServiceManager.getFile(documentId, filename);
//
//        HttpHeaders header = new HttpHeaders();
//        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
//        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        header.add("Pragma", "no-cache");
//        header.add("Expires", "0");
//
//        return ResponseEntity.ok()
//                .headers(header)
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(file);
//    }

}