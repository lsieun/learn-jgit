package lsieun.git.porcelain.everyday;

import lsieun.utils.BlobUtils;
import lsieun.utils.FileUtils;
import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;

import java.io.File;

public class GitAdd {
    public static void main(String[] args) throws Exception {
        File gitDir = PathManager.getLocalRepo();

        try (
                Git git = Git.open(gitDir)
        ) {
            String filename = "animal/lion.txt";
            BlobUtils.writeFileContent(filename, "this is a lovely lion");
            git.add().addFilepattern(filename).call();
        }
    }
}
