package edu.neu.spring.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * classpath下资源的抽象和访问
 * @author yato
 */
public class ClassPathResource implements Resource {

    private final String path;

    public ClassPathResource(String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(this.path);
        if (inputStream == null) {
            throw new FileNotFoundException(this.path + " does not exist");
        }
        return inputStream;
    }
}
