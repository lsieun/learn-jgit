package lsieun.git.plumbing;

import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import java.io.File;

public class LookUp {
    public static void main(String[] args) throws Exception {
        File gitDir = PathManager.getLocalRepo();

        try (
                Git git = Git.open(gitDir);
                Repository repository = git.getRepository();
                RevWalk walk = new RevWalk(repository);
        ) {
            ObjectId id = repository.resolve("d191e48359f");
//            RevObject gitObj = walk.parseAny(id);
//            String sha1 = gitObj.getName();
//            String type;
//            switch (gitObj.getType()) {
//                case Constants.OBJ_COMMIT:
//                    type = "commit";
//                    break;
//                case Constants.OBJ_TREE:
//                    type = "tree";
//                    break;
//                case Constants.OBJ_BLOB:
//                    type = "blob";
//                    break;
//                case Constants.OBJ_TAG:
//                    type = "tag";
//                    break;
//                default:
//                    throw new IllegalArgumentException("" + gitObj.getType());
//            }
//            System.out.println(sha1 + " " + type);

            RevCommit commit = walk.parseCommit(id);
               // and using commit's tree find the path
               RevTree tree = commit.getTree();
               System.out.println("Having tree: " + tree);

               // now try to find a specific file
               try (TreeWalk treeWalk = new TreeWalk(repository)) {
                   treeWalk.addTree(tree);
                   treeWalk.setRecursive(true);
//                   treeWalk.setFilter(PathFilter.create("README.md"));
                   if (!treeWalk.next()) {
                       throw new IllegalStateException("Did not find expected file 'README.md'");
                   }

                   ObjectId objectId = treeWalk.getObjectId(0);
                   ObjectLoader loader = repository.open(objectId);

                   // and then one can the loader to read the file
                   loader.copyTo(System.out);
               }


            walk.dispose();
        }
    }
}
