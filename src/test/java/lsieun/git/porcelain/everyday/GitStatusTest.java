package lsieun.git.porcelain.everyday;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.Set;

class GitStatusTest {
    @Test
    void testGitStatus() throws IOException, GitAPIException {
        // 替换为你的仓库路径
        Path repoPath = Path.of(".")
                .toAbsolutePath()
                .normalize();

        File dir =  repoPath.toFile();

        try (Git git = Git.open(dir)) {
            // 获取仓库状态
            Status status = git.status().call();


            StringBuilder sb = new StringBuilder();
            Formatter fm = new Formatter(sb);
            fm.format("- isClean(): %b%n", status.isClean());
            fm.format("- hasUncommittedChanges(): %b%n", status.hasUncommittedChanges());
            formatSet(fm, "getAdded", status.getAdded());
            formatSet(fm, "getChanged", status.getChanged());
            formatSet(fm, "getRemoved", status.getRemoved());
            formatSet(fm, "getMissing", status.getMissing());
            formatSet(fm, "getModified", status.getModified());
            formatSet(fm, "getUntracked", status.getUntracked());
            formatSet(fm, "getUntrackedFolders", status.getUntrackedFolders());
            formatSet(fm, "getConflicting", status.getConflicting());
            formatSet(fm, "getIgnoredNotInIndex", status.getIgnoredNotInIndex());
            formatSet(fm, "getUncommittedChanges", status.getUncommittedChanges());

            System.out.println(sb);
        }
    }

    private void formatSet(Formatter fm, String methodName, Set<String> set) {
        fm.format("- %s()%n", methodName);
        if(set != null && !set.isEmpty()) {
            for(String s : set) {
                fm.format("    - %s%n", s);
            }
        }
    }

    @Test
    void testGitStatus2() throws IOException, GitAPIException {
//        boolean hasChanges = !status.getModified().isEmpty() || !status.getAdded().isEmpty() || !status.getUntracked().isEmpty();
//
//        if (hasChanges) {
//            System.out.println("有修改或新增文件，需要更新提交。");
//
//            // 输出具体文件列表
//            if (!status.getModified().isEmpty()) {
//                System.out.println("修改的文件:");
//                status.getModified().forEach(System.out::println);
//            }
//
//            if (!status.getAdded().isEmpty()) {
//                System.out.println("已添加的文件:");
//                status.getAdded().forEach(System.out::println);
//            }
//
//            if (!status.getUntracked().isEmpty()) {
//                System.out.println("未跟踪的新文件:");
//                status.getUntracked().forEach(System.out::println);
//            }
//
//        } else {
//            System.out.println("无需任何操作，仓库没有修改或新增文件。");
//        }
    }
}
