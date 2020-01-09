package top.alexmmd.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import top.alexmmd.domain.File;
import top.alexmmd.domain.PagePackage;
import top.alexmmd.domain.RespEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author 汪永晖
 */
public interface FileService {

    /**
     * 展示最新的10条数据
     *
     * @return
     */
    RespEntity listFileByPage();

    /**
     * 存储文件
     *
     * @param f
     * @return
     */
    File saveFile(File f);

    /**
     * 分页查询
     *
     * @param pagePackage
     * @return
     */
    List<File> listFilesByPage(PagePackage pagePackage);

    /**
     * 获取文件
     *
     * @param id
     * @return
     */
    Optional<File> getFileById(String id);
}
