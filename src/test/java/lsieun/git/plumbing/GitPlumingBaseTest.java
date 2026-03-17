package lsieun.git.plumbing;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Path;

public class GitPlumingBaseTest {
    protected Repository repo;

    @BeforeEach
    void setUp() throws IOException {
        Path localRepo = Path.of(".");
        repo = new FileRepositoryBuilder()
                .setGitDir(localRepo.resolve(".git").toFile())
                .build();
    }

    @AfterEach
    void tearDown() {
        repo.close();
    }
}
