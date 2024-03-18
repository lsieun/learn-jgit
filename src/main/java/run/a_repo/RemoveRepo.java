package run.a_repo;

import lsieun.utils.FileUtils;
import lsieun.utils.PathManager;

public class RemoveRepo {
    public static void main(String[] args) {
        String localRepoPath = PathManager.getLocalRepoPath();
        FileUtils.delete(localRepoPath);
    }
}
