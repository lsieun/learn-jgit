package lsieun.git.porcelain.everyday;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import java.io.*;
import java.net.*;
import java.util.*;

public class JGitProxyWithSystemCredentials {

    public static void main(String[] args) {
        String localPath = "C:/your/repo/path";
        String remoteHost = "github.com";
        String protocol = "https";

        try (Git git = Git.open(new File(localPath))) {
            // 1. 从系统凭据管理器获取用户名和密码
            Map<String, String> creds = fetchSystemCredentials(protocol, remoteHost);
            String user = creds.get("username");
            String pass = creds.get("password");

            if (user == null || pass == null) {
                throw new Exception("无法从系统获取 Git 凭据，请检查是否已在 CMD 中登录。");
            }

            // 2. 设置本地 9910 端口代理 (全局)
            ProxySelector.setDefault(new ProxySelector() {
                @Override
                public List<Proxy> select(URI uri) {
                    if (uri.getHost().equalsIgnoreCase(remoteHost)) {
                        return Collections.singletonList(new Proxy(Proxy.Type.HTTP, 
                                new InetSocketAddress("127.0.0.1", 9910)));
                    }
                    return Collections.singletonList(Proxy.NO_PROXY);
                }
                @Override
                public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {}
            });

            // 3. 执行 Push 并应用凭据
            PushCommand push = git.push();
            push.setCredentialsProvider(new UsernamePasswordCredentialsProvider(user, pass));
            
            System.out.println("正在通过代理 127.0.0.1:9910 推送...");
            push.call();
            System.out.println("推送成功！");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用系统 git credential fill 获取凭据
     */
    private static Map<String, String> fetchSystemCredentials(String protocol, String host) throws IOException {
        Process process = new ProcessBuilder("git", "credential", "fill").start();
        
        // 写入查询条件
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write("protocol=" + protocol + "\n");
            writer.write("host=" + host + "\n\n"); // 必须以空行结束
            writer.flush();
        }

        // 解析返回结果
        Map<String, String> result = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) result.put(parts[0], parts[1]);
            }
        }
        return result;
    }
}
