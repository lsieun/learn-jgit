package run;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public class HelloWorld {
    public static void main(String[] args) throws GitAPIException {
        String remoteRepo = "https://github.com/eclipse/jgit.git";
        String localRepo = "D:\\git-tmp\\jgit";
        Git git = Git.cloneRepository()
                .setURI(remoteRepo)
                .setDirectory(new File(localRepo))
                .call();
        git.close();
    }
}
