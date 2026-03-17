package lsieun.git.porcelain.everyday;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GitCredentialRetriever {
    public static Map<String, String> getCredentials(String protocol, String host) throws IOException {
        // 1. 启动 git credential fill 命令
        ProcessBuilder pb = new ProcessBuilder("git", "credential", "fill");
        Process process = pb.start();

        // 2. 向 stdin 写入查询信息（必须以换行符分隔，最后加一个空行结束输入）
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write("protocol=" + protocol + "\n");
            writer.write("host=" + host + "\n");
            writer.write("\n"); // 关键：空行表示输入结束
            writer.flush();
        }

        // 3. 从 stdout 读取返回结果
        Map<String, String> result = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    result.put(parts[0], parts[1]);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            // 示例：获取 GitHub 的凭据
            Map<String, String> creds = getCredentials("https", "github.com");
            System.out.println("Username: " + creds.get("username"));
            System.out.println("Password/Token: " + creds.get("password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
