package lsieun.git.porcelain.repo;

import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;

import java.io.File;

public class A_CreateEmptyRepo {
    public static void main(String[] args) throws Exception {
        File gitDir = PathManager.getLocalRepo();

        // create the directory
        try (Git git = Git.init().setDirectory(gitDir).call()) {
            System.out.println("Create Empty Repository: " + git.getRepository().getDirectory());
        }
    }
}
