package run;

import lsieun.utils.GitUtils;
import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;

import java.io.File;

public class DistributedDevelopment {
    public static void main(String[] args) throws Exception {
        File gitDir = PathManager.getLocalRepo();

        try (
                Git git = Git.open(gitDir)
        ) {
            GitUtils.addAndCommit(git, "mouse/hello-mouse.txt", "this is a mouse", "jerry", "tom");
            GitUtils.addAndCommit(git, "cat/hello-cat.txt", "this is a cat", "lucy", "lily");
            GitUtils.addAndCommit(git, "dog/hello-dog.txt", "this is a dog", "zhangsan", "lisi");
            GitUtils.printLog(git);
        }
    }


}
