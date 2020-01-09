package top.alexmmd.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.alexmmd.domain.File;
import top.alexmmd.domain.PagePackage;
import top.alexmmd.domain.RespEntity;
import top.alexmmd.repository.FileRepository;
import top.alexmmd.service.FileService;
import top.alexmmd.util.MD5Util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

/**
 * File 服务
 *
 * @author 汪永晖
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * 展示最新的10条数据
     *
     * @return
     */
    @Override
    public RespEntity listFileByPage() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("uploadDate"));
        Page<File> filePage = fileRepository.findAll(pageable);
        log.info("<file-server>: select from file -> {}", filePage);

        List<File> fileList = filePage.getContent();

        return new RespEntity(100, "成功查出最新的10条数据", fileList);
    }

    /**
     * 存储文件
     *
     * @param f
     * @return
     */
    @Override
    public File saveFile(File f) {
        log.info("<file-server>: insert into file -> {}", f);
        return fileRepository.save(f);
    }

    /**
     * 分页查询
     *
     * @param pagePackage
     * @return
     */
    @Override
    public List<File> listFilesByPage(PagePackage pagePackage) {

        Pageable pageable = PageRequest.of(pagePackage.getPage(), pagePackage.getSize());
        Page<File> filePage = fileRepository.findAll(pageable);

        log.info("<file-server>: select from file by pagePackage -> {}", filePage);

        return filePage.getContent();
    }

    /**
     * 获取文件
     *
     * @param id
     * @return
     */
    @Override
    public Optional<File> getFileById(String id) {

        return fileRepository.findById(id);
    }

}
