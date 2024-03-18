package lsieun.git.ref;

import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

import java.io.File;

public class A_ReadHEAD {
    public static void main(String[] args) throws Exception {
        File gitDir = PathManager.getLocalRepo();

        try (
                Git git = Git.open(gitDir)
        ) {
            Repository repository = git.getRepository();
            System.out.println("Repo Dir: " + repository.getDirectory());
            ObjectId head = repository.resolve("HEAD");
            System.out.println(head.getName());
        }
    }
}
