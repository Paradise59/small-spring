package com.small.springframework.core.io;

import cn.hutool.core.lang.Assert;
import com.small.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class ClassPathResource implements  Resource {
        private final String path;

        private ClassLoader classLoader;

        public ClassPathResource(String path) {
                this(path, null);
        }
        public ClassPathResource(String path, ClassLoader classLoader) {
                Assert.notNull(path, "Path must not be null");
                this.path = path;
                this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
        }

        @Override
        public InputStream getInputStream() throws IOException {
                InputStream resourceAsStream = classLoader.getResourceAsStream(path);
                if( ! Optional.ofNullable(resourceAsStream).isPresent()){
                       throw new IOException(this.path + " cannot be opened because it does not exist");
                }
                return resourceAsStream;
        }
}
