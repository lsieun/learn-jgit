package lsieun.git.porcelain.repo;

import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.util.Formatter;

public class B_CloneRemoteRepo {
    public static void main(String[] args) throws Exception {
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
}
