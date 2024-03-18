package run.a_repo;

import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public class InitRepo {
    public static void main(String[] args) throws GitAPIException {
        File localRepo = PathManager.getLocalRepo();
        Git git = Git.init().setDirectory(localRepo).call();
        git.close();
    }
}
