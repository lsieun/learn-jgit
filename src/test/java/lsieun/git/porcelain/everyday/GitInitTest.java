package lsieun.git.porcelain.everyday;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class GitInitTest {
    @Test
    void testInitRepo() throws GitAPIException, IOException {
        File gitDir = new File("D:/tmp/lab");

        // create the directory
        try (Git git = Git.init().setDirectory(gitDir).call()) {
            Repository repository = git.getRepository();
            System.out.println("Create Empty Repository: " + repository.getDirectory());

            // 存在的问题是
            // 本地 Git 仓库，默认的 branch 是 master，
            // 在 GitHub 仓库，默认的 branch 是 main
            String branch = repository.getBranch();
            System.out.println("branch = " + branch);
        }
    }

    @Test
    void testInitRepoWithMainBranch01() throws GitAPIException, IOException {
        File gitDir = new File("D:/tmp/lab");

        try (Git git = Git.init().setDirectory(gitDir).call()) {
            // 获取仓库的 HEAD 引用并将其修改为指向 main
            git.getRepository()
                    .updateRef(Constants.HEAD)
                    .link("refs/heads/main");

            System.out.println("Now on branch: " + git.getRepository().getBranch()); // 输出 main
        }
    }

    @Test
    void testInitRepoWithMainBranch02() throws GitAPIException, IOException {
        File gitDir = new File("D:/tmp/lab");

        try (Git git = Git.init()
                .setDirectory(gitDir)
                .setInitialBranch("main") // 直接指定初始分支为 main
                .call()) {

            System.out.println("Now on branch: " + git.getRepository().getBranch()); // 输出 main
        }
    }
}
