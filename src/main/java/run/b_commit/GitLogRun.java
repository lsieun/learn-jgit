package run.b_commit;

import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.util.Formatter;

public class GitLogRun {
    public static void main(String[] args) {
        File localRepo = PathManager.getLocalRepo();

        try (
                Git git = Git.open(localRepo)
        ) {
            Iterable<RevCommit> commits = git.log().call();
            for (RevCommit commit : commits) {
                StringBuilder sb = new StringBuilder();
                Formatter fm = new Formatter(sb);
                fm.format("%s %s%n", Constants.typeString(commit.getType()), commit.getName());
                fm.format("Message: %s%n", commit.getShortMessage());

                String str = sb.toString();
                System.out.println(str);
            }
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
    }
}
