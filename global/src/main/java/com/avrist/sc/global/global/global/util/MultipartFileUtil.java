package com.avrist.sc.global.global.global.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@UtilityClass
public class MultipartFileUtil {
    public static boolean multipartFilesIsEmpty(List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) {
            return true;
        }

        return multipartFiles.stream().allMatch(MultipartFile::isEmpty);
    }

    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        String filename = isNotEmpty(multipartFile.getOriginalFilename()) ? multipartFile.getOriginalFilename() : "";
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File tempFile = File.createTempFile("temp-", "-" + filename, tempDir);

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }

        return tempFile;
    }
}
