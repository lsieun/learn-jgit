package run.z_task;

import lsieun.utils.BlobUtils;
import lsieun.utils.PathManager;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SecondTask {
    public static void main(String[] args) {
        File localRepo = PathManager.getLocalRepo();

        String[] commands = {
                "master:1,5",
                "dev:6,10",
                "my-feature-branch:11,15",
                "master:16,20",
        };

        try (
                Git git = Git.open(localRepo)
        ) {
            initCommit(git);

            for (String cmd : commands) {
                branchAndCommit(git, cmd);
            }
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    private static void branchAndCommit(Git git, String command) throws GitAPIException {
        String[] array1 = command.split(":");
        String branchName = array1[0];

        String[] array2 = array1[1].split(",");

        int startIndex = Integer.parseInt(array2[0]);
        int stopIndex = Integer.parseInt(array2[1]);

        branchAndCommit(git, branchName, startIndex, stopIndex);
    }

    private static void branchAndCommit(Git git, String branchName, int startIndex, int stopIndex) throws GitAPIException {
        branch(git, branchName);

        commit(git, startIndex, stopIndex);
    }

    private static void branch(Git git, String branchName) throws GitAPIException {
        boolean create = true;
        {
            ListBranchCommand cmd = git.branchList();
            List<Ref> list = cmd.call();
            for (Ref ref : list) {
                String name = ref.getName();
                int index = name.lastIndexOf("/");
                String shortName = name.substring(index + 1);
                if (shortName.equals(branchName)) {
                    create = false;
                    break;
                }
            }
        }

        {
            CheckoutCommand cmd = git.checkout();
            cmd.setName(branchName).setCreateBranch(create).call();
        }
    }

    private static void commit(Git git, int startIndex, int stopIndex) throws GitAPIException {
        for (int i = startIndex; i <= stopIndex; i++) {
            String filename = String.format("C%d.txt", i);
            String content = "content" + i;
            BlobUtils.writeFileContent(filename, content);
            git.add().addFilepattern(filename).call();

            String msg = "C" + i;
            git.commit().setMessage(msg)
                    .setAuthor("tom", "tom@abc.com")
                    .setCommitter("jerry", "jerry@abc.com")
                    .call();
        }
    }

    private static void initCommit(Git git) throws GitAPIException {
        String filename = "README.md";
        String content = "Hello Git Repository";
        BlobUtils.writeFileContent(filename, content);
        git.add().addFilepattern(filename).call();

        String msg = "Initial Commit";
        git.commit().setMessage(msg)
                .setAuthor("tom", "tom@abc.com")
                .setCommitter("jerry", "jerry@abc.com")
                .call();
    }
}
