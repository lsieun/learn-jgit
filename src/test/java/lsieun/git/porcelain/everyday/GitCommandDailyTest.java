package lsieun.git.porcelain.everyday;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Test;

import java.io.File;

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

}
