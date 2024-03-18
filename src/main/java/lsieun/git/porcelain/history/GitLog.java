package lsieun.git.porcelain.history;

import lsieun.utils.DateUtils;
import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.util.Formatter;

public class GitLog {
    public static void main(String[] args) throws Exception {
        File gitDir = PathManager.getLocalRepo();

        try (
                Git git = Git.open(gitDir)
        ) {
            Iterable<RevCommit> commits = git.log().call();
            for (RevCommit commit : commits) {
                PersonIdent author = commit.getAuthorIdent();
                PersonIdent committer = commit.getCommitterIdent();

                StringBuilder sb = new StringBuilder();
                Formatter fm = new Formatter(sb);
                fm.format("%s %s%n", Constants.typeString(commit.getType()), commit.getName());
                fm.format("Author: %s <%s>%n", author.getName(), author.getEmailAddress());
                fm.format("Committer: %s <%s>%n", committer.getName(), committer.getEmailAddress());
                fm.format("Date: %s %n", DateUtils.fromUnixTimestamp(commit.getCommitTime()));
                fm.format("Message: %s%n", commit.getShortMessage());

                String str = sb.toString();
                System.out.println(str);
            }
        }
    }
}
