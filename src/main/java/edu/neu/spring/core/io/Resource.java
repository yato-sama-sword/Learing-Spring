package edu.neu.spring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源抽象和访问接口
 * @author yato
 */
public interface Resource {
    /**
     * 获取InputStream
     * @return InputStream
     * @throws IOException IO异常
     */
    InputStream getInputStream() throws IOException;
}
