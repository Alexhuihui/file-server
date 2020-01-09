package top.alexmmd.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import top.alexmmd.domain.File;
import top.alexmmd.domain.PagePackage;
import top.alexmmd.domain.RespEntity;
import top.alexmmd.service.FileService;
import top.alexmmd.util.MD5Util;

/**
 * @author 汪永晖
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class FileController {

    private final FileService fileService;

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;


    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 展示最新的10条数据
     *
     * @return
     */
    @GetMapping("/")
    public RespEntity index() {
        return fileService.listFileByPage();
    }

    /**
     * 分页查询文件
     *
     * @return
     */
    @GetMapping("/selectFiles")
    public List<File> listFilesByPage(@RequestBody PagePackage pagePackage) {
        return fileService.listFilesByPage(pagePackage);
    }

    /**
     * 上传接口
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        File returnFile = null;
        try {
            // 把 MultipartFile 对象转换成 File 对象
            File f = new File();
            f.setName(file.getOriginalFilename());
            f.setContentType(file.getContentType());
            f.setSize(file.getSize());
            f.setContent(new Binary(file.getBytes()));
            f.setMd5(MD5Util.getMD5(file.getInputStream()));

            log.info("<file-server>: upload file -> {}", file.getOriginalFilename() + file.getContentType() + file.getSize() + file.getBytes());
            log.info("<file-server>: upload file -> {}", f);

            returnFile = fileService.saveFile(f);
            String path = serverAddress + ":" + serverPort + "/view/" + returnFile.getId();
            return ResponseEntity.status(HttpStatus.OK).body(path);

        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    /**
     * 上传接口
     *
     * @param files
     * @return
     */
    @PostMapping("/uploads")
    public RespEntity handleFileUpload(@RequestParam("files") MultipartFile[] files) {

        try {
            for (MultipartFile file : files) {
                File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(), new Binary(file.getBytes()));
                f.setMd5(MD5Util.getMD5(file.getInputStream()));

                log.info("<file-server>: upload file -> {}", file.getOriginalFilename() + file.getContentType() + file.getSize() + file.getBytes());
                log.info("<file-server>: upload file -> {}", f);

                fileService.saveFile(f);
            }
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return new RespEntity(500, "批量上传文件失败");
        }

        return new RespEntity(100, "批量上传文件成功");
    }

    /**
     * 在线显示文件
     *
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<Object> serveFileOnline(@PathVariable String id) {

        Optional<File> file = fileService.getFileById(id);

        if (file.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.get().getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.get().getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
                    .body(file.get().getContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }

    }

    /**
     * 获取文件片信息
     *
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/files/{id}")
    public ResponseEntity<Object> serveFile(@PathVariable String id) throws UnsupportedEncodingException {

        Optional<File> file = fileService.getFileById(id);

        if (file.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + new String(file.get().getName().getBytes("utf-8"), "ISO-8859-1"))
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
                    .body(file.get().getContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }

    }
}
