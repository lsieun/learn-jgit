package lsieun.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Formatter;

public class GitUtils {
    public static void addAndCommit(Git git, String filename, String content, String author, String committer) throws GitAPIException {
        BlobUtils.writeFileContent(filename, content);
        git.add().addFilepattern(filename).call();

        git.commit().setMessage("add " + filename + " file")
                .setAuthor(author, getEmail(author))
                .setCommitter(committer, getEmail(committer))
                .call();
    }

    public static void printLog(Git git) throws GitAPIException {
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

    public static String getEmail(String name) {
        return String.format("%s@abc.com", name);
    }

    public static boolean isGitRepository(Path dirPath) {
        File gitDir = dirPath.resolve(".git")
                .toAbsolutePath()
                .normalize().toFile();

        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try (Repository repo = builder.setGitDir(gitDir)
                .readEnvironment() // 扫描环境变量
                .findGitDir()     // 查找.git目录
                .build()) {

            // 如果build()没有抛出异常，并且getDirectory()不为空，则是仓库
            return repo.getDirectory() != null && repo.getDirectory().exists();
        } catch (IOException e) {
            // 异常说明不是仓库或无法访问
            return false;
        }
    }
}
