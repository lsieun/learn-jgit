package lsieun.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BlobUtils {
    public static void writeFileContent(String filename, String content) {
        String localRepoPath = PathManager.getLocalRepoPath();
        String filepath = localRepoPath + File.separator + filename;
        List<String> lines = new ArrayList<>();
        lines.add(content);
        FileUtils.writeLines(filepath, lines);
    }

    public static void main(String[] args) {
        writeFileContent("good/hello.txt", "Good");
    }
}
