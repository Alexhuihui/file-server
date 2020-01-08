package top.alexmmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import top.alexmmd.service.FileService;

/**
 * @author 汪永晖
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {

    private final FileService fileService;

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;


    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


}
