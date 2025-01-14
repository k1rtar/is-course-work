package com.algoforge.testrunner.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    /**
     * Создаёт временную директорию (пример: /tmp/judge-123456)
     */
    public static File createTempDirectory(String prefix) throws IOException {
        return Files.createTempDirectory(prefix).toFile();
    }

    /**
     * Рекурсивно удаляет директорию со всеми файлами.
     */
    public static void deleteDirectory(File directory) {
        if (!directory.exists()) {
            return;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteDirectory(f);
                } else {
                    //noinspection ResultOfMethodCallIgnored
                    f.delete();
                }
            }
        }
        //noinspection ResultOfMethodCallIgnored
        directory.delete();
    }
}
