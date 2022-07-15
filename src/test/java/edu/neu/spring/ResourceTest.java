package edu.neu.spring;


import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import edu.neu.spring.core.io.DefaultResourceLoader;
import edu.neu.spring.core.io.FileSystemResource;
import edu.neu.spring.core.io.Resource;
import edu.neu.spring.core.io.UrlResource;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ResourceTest {
    @Test
    public void testResource() throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

        //加载classpath下的资源
        Resource resource = resourceLoader.getResource("classpath:test.txt");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        Assert.equals(content, "test");


        //加载文件系统资源
        resource = resourceLoader.getResource("src/test/resources/test.txt");
        Assert.isTrue(resource instanceof FileSystemResource);
        inputStream = resource.getInputStream();
        content = IoUtil.readUtf8(inputStream);
        Assert.equals(content, "test");

        //加载url资源
        resource = resourceLoader.getResource("https://github.com/yato-sama-sword/dailyNote/blob/main/README.md");
        Assert.isTrue(resource instanceof UrlResource);
        inputStream = resource.getInputStream();
        content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }
}
