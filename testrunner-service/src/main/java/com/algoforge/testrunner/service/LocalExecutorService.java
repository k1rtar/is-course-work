package com.algoforge.testrunner.service;

import com.algoforge.common.model.Language;
import com.algoforge.testrunner.util.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class LocalExecutorService {

    /**
     * Выполняем код внутри текущей системы (текущего контейнера):
     *  1) Создаём временный файл с исходным кодом
     *  2) Компилим (если C++ или Java)
     *  3) Запускаем процесс (подкладываем inputData в stdin)
     *  4) Считываем stdout
     *
     * @param language         язык программирования (python, java, cpp)
     * @param code             исходный код
     * @param inputData        входные данные для stdin
     * @param timeLimitMillis  ограничение по времени (пока игнорируем или делаем Thread timeout)
     * @param memoryLimitBytes ограничение по памяти (в примере не реализовано)
     * @return stdout
     * @throws Exception если не удалось скомпилировать/запустить
     */
    public String executeCode(Language language,
                              String code,
                              String inputData,
                              long timeLimitMillis,
                              long memoryLimitBytes) throws Exception {

        // 1) Создаём уникальную директорию во временной папке, куда положим исходники
        File tempDir = FileUtils.createTempDirectory("judge");
        try {
            // 2) Записываем исходный код в файл
            File sourceFile = new File(tempDir, getSourceFileName(language));
            try (FileWriter writer = new FileWriter(sourceFile)) {
                writer.write(code);
            }

            // 3) Если язык требует компиляции, компилим
            if (language == Language.CPP) {
                compileCpp(sourceFile);
                // результат: исполняемый файл (./solution)
            } else if (language == Language.JAVA) {
                compileJava(sourceFile);
                // результат: Solution.class
            }
            
            // 4) Запускаем с помощью ProcessBuilder
            String[] cmd = getRunCommand(language, sourceFile.getName());
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.directory(tempDir);
            pb.redirectErrorStream(true); // чтобы stderr шел в stdout

            Process process = pb.start();

            // подаём inputData в stdin процесса
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(process.getOutputStream()))) {
                writer.write(inputData == null ? "" : inputData);
                writer.flush();
            }

            // считываем stdout
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            // ждём завершения процесса
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // возможно, была ошибка (например, runtime error)
                throw new RuntimeException("Process exited with code " + exitCode 
                    + ". Output: " + output);
            }

            // возвращаем результат (убираем лишние переносы)
            return output.toString().trim();

        } finally {
            // Удаляем временные файлы
            FileUtils.deleteDirectory(tempDir);
        }
    }

    private String getSourceFileName(Language language) {
        return switch (language) {
            case Language.PYTHON -> "solution.py";
            case Language.JAVA -> "Solution.java";
            case Language.CPP -> "solution.cpp";
            default -> "solution.txt";
        };
    }

    /**
     * Команда для запуска скомпилированной/интерпретируемой программы
     */
    private String[] getRunCommand(Language language, String sourceFileName) {
        return switch (language) {
            case Language.PYTHON ->
                // python solution.py
                new String[]{"python3", sourceFileName};
            case Language.JAVA ->
                // java Solution
                new String[]{"java", getJavaClassName(sourceFileName)};
            case Language.CPP ->
                // ./solution
                new String[]{"./solution"};
            default ->
                // просто cat файла, чтобы посмотреть, что там внутри :)
                new String[]{"cat", sourceFileName};
        };
    }

    private String getJavaClassName(String fileName) {
        if (fileName.endsWith(".java")) {
            return fileName.substring(0, fileName.length() - 5);
        }
        return "Solution";
    }

    private void compileCpp(File sourceFile) throws IOException, InterruptedException {
        // g++ solution.cpp -o solution
        ProcessBuilder pb = new ProcessBuilder("g++", sourceFile.getName(), "-o", "solution");
        pb.directory(sourceFile.getParentFile());
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            // соберём stderr
            String err = readStream(process.getErrorStream());
            throw new RuntimeException("C++ compilation failed. " + err);
        }
    }

    private void compileJava(File sourceFile) throws IOException, InterruptedException {
        // javac Solution.java
        ProcessBuilder pb = new ProcessBuilder("javac", sourceFile.getName());
        pb.directory(sourceFile.getParentFile());
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            // соберём stderr
            String err = readStream(process.getErrorStream());
            throw new RuntimeException("Java compilation failed. " + err);
        }
    }

    private String readStream(InputStream inputStream) throws IOException {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }
}
