package run.z_task;

import lsieun.utils.BlobUtils;
import lsieun.utils.PathManager;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;

public class FirstTask {
    public static void main(String[] args) {
        File localRepo = PathManager.getLocalRepo();

        try (
                Git git = Git.open(localRepo)
        ) {
            commit(git, 0, 5);
            branch(git, 2, "my-feature-branch");
            commit(git, 6, 9);
//            branch(git, 7, "dev");
            commit(git, 10, 13);
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
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

    private static void branch(Git git, int index, String branchName) throws GitAPIException {
        String targetMsg = "C" + index;
        RevCommit targetCommit = null;

        Iterable<RevCommit> commits = git.log().call();
        for (RevCommit commit : commits) {
            String shortMessage = commit.getShortMessage();
            if (shortMessage.equals(targetMsg)) {
                targetCommit = commit;
                break;
            }
        }

        if (targetCommit == null) {
            throw new IllegalArgumentException("index = " + index);
        }

        {
            CreateBranchCommand cmd = git.branchCreate();
            cmd.setName(branchName)
                    .setStartPoint(targetCommit)
                    .call();
        }

        {
            CheckoutCommand cmd = git.checkout();
            cmd.setName(branchName).call();
        }
    }

}
