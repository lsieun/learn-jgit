package lsieun.git.plumbing;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GitHeadTest extends GitPlumingBaseTest {
    @Test
    void testReadHead() throws IOException {
        // HEAD --> refs/heads/main --> SHA

        // 第 1 种方式
        ObjectId head1 = repo.resolve("HEAD");

        // 第 2 种方式
        Ref ref = repo.findRef("refs/heads/main");
        ObjectId head2 = ref.getObjectId();

        // 两者的结果是一样的
        assertEquals(head1.getName(), head2.getName());
    }

    @Test
    void testWhetherPush() throws IOException {
        String branch = "main";
        ObjectId localId = repo.resolve("refs/heads/" + branch);
        ObjectId remoteId = repo.resolve("refs/remotes/origin/" + branch);

        // 计算本地是否领先于远程
        try (RevWalk revWalk = new RevWalk(repo)) {
            RevCommit localCommit = revWalk.parseCommit(localId);
            RevCommit remoteCommit = revWalk.parseCommit(remoteId);

            // 判断是否是祖先关系：如果remoteCommit是localCommit的祖先，说明需要push
            if (revWalk.isMergedInto(remoteCommit, localCommit) && !localId.equals(remoteId)) {
                System.out.println("检测到本地有新提交，需要Push...");
            } else {
                System.out.println("本地分支无需Push (无需同步或无需快进)。");
            }
        }
    }
}
