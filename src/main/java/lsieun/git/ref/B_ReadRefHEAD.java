package lsieun.git.ref;

import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

import java.io.File;

public class B_ReadRefHEAD {
    public static void main(String[] args) throws Exception {
        File gitDir = PathManager.getLocalRepo();

        try (
                Git git = Git.open(gitDir)
        ) {
            Repository repository = git.getRepository();
            System.out.println("Repo Dir: " + repository.getDirectory());
            Ref head = repository.findRef("refs/heads/main");
            System.out.println(head);
        }
    }
}
