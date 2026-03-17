package lsieun.git.porcelain.everyday;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.HttpTransport;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.HttpSupport;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;

public class JGitProxyPush {
    public static void main(String[] args) {
        String localPath = "D:\\git-repo\\learn-java-text";
//        String remoteUrl = "https://github.com";
        String username = "";
        String password = "";

        // 1. 设置全局代理选择器（影响该进程内所有的 HTTP 连接）
        ProxySelector.setDefault(new ProxySelector() {
            @Override
            public List<Proxy> select(URI uri) {
                // 仅对特定的 git 远程地址应用代理
                if (uri.getHost().contains("github.com")) {
                    return Collections.singletonList(new Proxy(Proxy.Type.HTTP, 
                            new InetSocketAddress("127.0.0.1", 9910)));
                }
                return Collections.singletonList(Proxy.NO_PROXY);
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, java.io.IOException ioe) {
                ioe.printStackTrace();
            }
        });

        try (Git git = Git.open(new File(localPath))) {
            // 2. 执行 Push 操作
            PushCommand pushCommand = git.push();

            // 设置身份验证
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
            
            // 如果你不想使用全局 ProxySelector，可以使用 TransportConfigCallback
            pushCommand.setTransportConfigCallback(transport -> {
                if (transport instanceof HttpTransport) {
                    // 某些版本的 JGit 允许在这里进一步微调连接参数
                    // 但通常 ProxySelector 已经足够处理 9910 端口的转发
                }
            });

            pushCommand.call();
            System.out.println("Push successful with proxy!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
