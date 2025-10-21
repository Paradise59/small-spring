package com.small.springframework.core.io;

import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemResource implements Resource {
        private final File file;

        @Getter
        private final String path;

        public FileSystemResource(File file) {
                this.file = file;
                this.path = file.getPath();
        }

        public FileSystemResource(String path) {
                this.file = new File(path);
                this.path = path;
        }

        @Override
        public InputStream getInputStream() throws IOException {
                return new FileInputStream(this.file);
        }

}
