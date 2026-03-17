package lsieun.git.porcelain.everyday;

import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import java.io.*;
import java.net.*;
import java.util.*;

public class JGitConfigManager {

    private static JGitConfigManager instance;
    private final String proxyHost = "127.0.0.1";
    private final int proxyPort = 9910;

    private JGitConfigManager() {}

    public static synchronized JGitConfigManager getInstance() {
        if (instance == null) {
            instance = new JGitConfigManager();
        }
        return instance;
    }

    /**
     * 初始化环境：配置代理并加载系统凭据
     * @param protocol 协议 (如 "https")
     * @param host 托管平台 (如 "github.com")
     */
    public void init(String protocol, String host) {
        setupProxy(host);
        loadSystemCredentials(protocol, host);
        System.out.println(">>> JGit 环境初始化完成 (代理: " + proxyPort + ")");
    }

    // 1. 配置全局代理选择器
    private void setupProxy(String targetHost) {
        ProxySelector.setDefault(new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                // 仅当访问目标 Git 平台时才走代理
                if (uri.getHost().equalsIgnoreCase(targetHost)) {
                    return Collections.singletonList(new Proxy(Proxy.Type.HTTP, 
                            new InetSocketAddress(proxyHost, proxyPort)));
                }
                return Collections.singletonList(Proxy.NO_PROXY);
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                System.err.println("代理连接失败: " + ioe.getMessage());
            }
        });
    }

    // 2. 调用系统命令获取凭据并设为 JGit 默认值
    private void loadSystemCredentials(String protocol, String host) {
        try {
            Process process = new ProcessBuilder("git", "credential", "fill").start();
            
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                writer.write("protocol=" + protocol + "\n");
                writer.write("host=" + host + "\n\n");
                writer.flush();
            }

            Map<String, String> result = new HashMap<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) result.put(parts[0], parts[1]);
                }
            }

            String user = result.get("username");
            String pass = result.get("password");

            if (user != null && pass != null) {
                CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider(user, pass));
                System.out.println(">>> 已成功载入系统凭据: " + user);
            } else {
                System.err.println(">>> 警告：未发现匹配的系统凭据，后续操作可能需要手动登录。");
            }
        } catch (IOException e) {
            System.err.println(">>> 提取系统凭据异常: " + e.getMessage());
        }
    }
}
