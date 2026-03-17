package lsieun.git.porcelain.repo;

import lsieun.utils.GitUtils;
import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Formatter;

class GitRepoTest {
    @Test
    void testIsGitRepo() {
        String repoPath = "D:\\git-repo\\learn-mcp-java"; // 替换为你的仓库路径
        Path dirPath = Path.of(repoPath)
                .toAbsolutePath()
                .normalize();
        boolean flag = GitUtils.isGitRepository(dirPath);
        String info = String.format("[%s] %s", flag, dirPath.toUri());
        System.out.println(info);
    }



    @Test
    void testCloneRemoteRepo() throws GitAPIException {
        String remoteGitURL = PathManager.getRemoteRepoURL();
        File gitDir = PathManager.getLocalRepo();
        try (
                Git git = Git.cloneRepository()
                        .setURI(remoteGitURL)
                        .setDirectory(gitDir)
                        .call()
        ) {
            StringBuilder sb = new StringBuilder();
            Formatter fm = new Formatter(sb);
            fm.format("$ git clone %s%n", remoteGitURL);
            fm.format("$ cd %s%n", git.getRepository().getDirectory().getParent());
            String message = sb.toString();
            System.out.println(message);
        }
    }

    @Test
    void testOpenExistingRepo() {
        File gitDir = PathManager.getLocalRepo();

        try (
                Git git = Git.open(gitDir)
        ) {
            Repository repository = git.getRepository();
            System.out.println("Repo Dir: " + repository.getDirectory());
            ObjectId head = repository.resolve("HEAD");
            System.out.println(head.getName());
            Status status = git.status().call();
            System.out.println(status);
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
    }
}
