package lsieun.git.porcelain.everyday;

import lsieun.utils.BlobUtils;
import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class GitCommandDailyTest {

    @Test
    void testGitClone() throws GitAPIException {
        String remoteRepo = "https://github.com/lsieun/learn-jgit";
        String localRepo = "D:\\tmp\\learn-jgit";
        Git git = Git.cloneRepository()
                .setURI(remoteRepo)
                .setDirectory(new File(localRepo))
                .call();
        git.close();
    }

    @Test
    void testGitAdd() throws IOException, GitAPIException {
        File gitDir = PathManager.getLocalRepo();

        try (
                Git git = Git.open(gitDir)
        ) {
            String filename = "animal/lion.txt";
            BlobUtils.writeFileContent(filename, "this is a lovely lion");
            git.add().addFilepattern(filename).call();
        }
    }

    @Test
    void testGitCommit() throws IOException, GitAPIException {
        File gitDir = PathManager.getLocalRepo();

        try (
                Git git = Git.open(gitDir)
        ) {
//            git.commit().setMessage("add cat.txt").call();
            git.commit().setMessage("add lion.txt")
                    .setCommitter("zhangsan", "zhangsan@gmail.com")
                    .setAuthor("lisi", "lisi@gmail.com")
                    .call();
        }
    }

    @Test
    void testGitPush() throws IOException, GitAPIException {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "9910"); // 替换为你的本地端口
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "9910");
        System.setProperty("socksProxyHost", "127.0.0.1");
        System.setProperty("socksProxyPort", "9910"); // 常见 SOCKS5 默认端口


        File gitDir = new File("D:\\git-repo\\lsieun.github.io");
        try (
                Git git = Git.open(gitDir)
        ) {
            Iterable<PushResult> results = git.push().call();

            // 打印结果
            for (PushResult result : results) {
                for (RemoteRefUpdate refUpdate : result.getRemoteUpdates()) {
                    System.out.println("Ref: " + refUpdate.getRemoteName() + ", 状态: " + refUpdate.getStatus());
                    if (refUpdate.getMessage() != null) {
                        System.out.println("消息: " + refUpdate.getMessage());
                    }
                }
            }
        }
    }
}
