package edu.neu.spring.core.io;

import edu.neu.spring.utils.ClassUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * classpath下资源的抽象和访问
 * @author yato
 */
public class ClassPathResource implements Resource {

    private final String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this.path = path;
        this.classLoader = ClassUtil.getDefaultClassLoader();
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtil.getDefaultClassLoader());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        // 通过ClassLoader读取ClassPath下的文件信息
        InputStream inputStream = classLoader.getResourceAsStream(path);
        if (inputStream == null) {
            throw new FileNotFoundException(this.path + " does not exist");
        }
        return inputStream;
    }
}
