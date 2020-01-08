package top.alexmmd.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import top.alexmmd.domain.File;

/**
 * File 存储哭
 *
 * @author 汪永晖
 */
public interface FileRepository extends MongoRepository<File, String> {
}
