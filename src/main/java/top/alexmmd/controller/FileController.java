package top.alexmmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.alexmmd.domain.RespEntity;
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

    /**
     * 展示最新的20条数据
     *
     * @return
     */
    @GetMapping("/")
    public RespEntity index() {
        Pageable pageable = PageRequest.of(0, 10);
        return fileService.listFileByPage(pageable);
    }

}
