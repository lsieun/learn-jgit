package lsieun.git.porcelain.everyday;

import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;

import java.io.File;

public class GitCommit {
    public static void main(String[] args) throws Exception {
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
}
