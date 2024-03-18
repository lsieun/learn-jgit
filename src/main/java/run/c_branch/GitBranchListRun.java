package run.c_branch;

import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GitBranchListRun {
    public static void main(String[] args) {
        File localRepo = PathManager.getLocalRepo();

        try (
                Git git = Git.open(localRepo)
        ) {
            ListBranchCommand cmd = git.branchList();
            List<Ref> list = cmd.call();
            list.forEach(item -> {
                String name = item.getName();
                System.out.println(name);
            });
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
    }
}
