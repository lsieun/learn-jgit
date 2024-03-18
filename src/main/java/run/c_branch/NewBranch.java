package run.c_branch;

import lsieun.utils.PathManager;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.io.IOException;

public class NewBranch {
    public static void main(String[] args) {
        File localRepo = PathManager.getLocalRepo();

        try (
                Git git = Git.open(localRepo);
                Repository repository = git.getRepository();
        ) {
            String branchName = "my-feature-branch2";

            {
                CreateBranchCommand cmd = git.branchCreate();
                cmd.setName(branchName).call();
            }

            {
                CheckoutCommand cmd = git.checkout();
                cmd.setName(branchName).call();
            }

            {
                String branch = repository.getBranch();
                System.out.println("branch = " + branch);
            }
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
    }
}
