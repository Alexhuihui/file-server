package top.alexmmd.service;

import org.springframework.data.domain.Pageable;
import top.alexmmd.domain.RespEntity;

/**
 * @author 汪永晖
 */
public interface FileService {

    RespEntity listFileByPage(Pageable pageable);
}
