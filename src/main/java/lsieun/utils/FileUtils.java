package lsieun.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtils {
    public static final int BUFFER_SIZE = 16 * 1024;

    public static byte[] readBytes(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File Not Exist: " + filepath);
        }

        try (
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
        ) {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            byte[] buf = new byte[BUFFER_SIZE];
            int len;
            while ((len = bis.read(buf)) != -1) {
                bao.write(buf, 0, len);
            }
            return bao.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void writeBytes(String filepath, byte[] bytes) {
        File file = new File(filepath);
        File dirFile = file.getParentFile();
        mkdirs(dirFile);

        try (OutputStream out = new FileOutputStream(filepath);
             BufferedOutputStream buff = new BufferedOutputStream(out)) {
            buff.write(bytes);
            buff.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mkdirs(File dirFile) {
        boolean file_exists = dirFile.exists();

        if (file_exists && dirFile.isDirectory()) {
            return;
        }

        if (file_exists && dirFile.isFile()) {
            throw new RuntimeException("Not A Directory: " + dirFile);
        }

        if (!file_exists) {
            boolean flag = dirFile.mkdirs();
            if (!flag) {
                throw new RuntimeException("Create Directory Failed: " + dirFile.getAbsolutePath());
            }
        }
    }

    public static List<String> readLines(String filepath) {
        return readLines(filepath, "UTF8");
    }

    public static List<String> readLines(String filepath, String charsetName) {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File Not Exist: " + filepath);
        }

        try (
                InputStream in = new FileInputStream(file);
                Reader reader = new InputStreamReader(in, charsetName);
                BufferedReader bufferReader = new BufferedReader(reader);
        ) {
            List<String> list = new ArrayList<>();
            String line;
            while ((line = bufferReader.readLine()) != null) {
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeLines(String filepath, List<String> lines) {
        if (lines == null || lines.size() < 1) return;

        File file = new File(filepath);
        File dirFile = file.getParentFile();
        mkdirs(dirFile);

        try (
                OutputStream out = new FileOutputStream(file);
                Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
        ) {
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<File> findAllFileInDirectory(String dirPath, boolean recursive) {
        File file = new File(dirPath);
        if (!file.exists()) {
            return Collections.emptyList();
        }

        if (file.isFile()) {
            return Collections.singletonList(file);
        }

        List<File> dirList = new ArrayList<>();
        if (file.isDirectory()) {
            dirList.add(file);
        }

        List<File> fileList = new ArrayList<>();
        if (file.isFile()) {
            fileList.add(file);
        }
        if (!recursive) {
            return fileList;
        }

        for (int i = 0; i < dirList.size(); i++) {
            File dirFile = dirList.get(i);
            File[] files = dirFile.listFiles();
            if (files == null) {
                continue;
            }

            for (File f : files) {
                if (f.isDirectory()) {
                    dirList.add(f);
                } else if (f.isFile()) {
                    fileList.add(f);
                } else {
                    System.out.println("file: " + f.getAbsolutePath());
                }
            }
        }

        return fileList;
    }

    public static void delete(String dirPath) {
        deleteRecursive(new File(dirPath));
    }

    public static void deleteRecursive(File file) {
        if (!file.exists()) {
            return;
        }

        if (file.isFile()) {
            boolean flag = file.delete();
            if (!flag) {
                String msg = String.format("delete file failed: %s", file);
                System.out.println(msg);
            }
            return;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteRecursive(f);
                }
            }

            boolean flag = file.delete();
            if (!flag) {
                String msg = String.format("delete file failed: %s", file);
                System.out.println(msg);
            }
        }
    }
}
