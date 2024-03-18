package lsieun.drawing;

import lsieun.drawing.theme.tree.Tree;
import lsieun.utils.PathManager;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.*;

import java.io.File;

public class GitObjectTree {
    public static void main(String[] args) throws Exception {
        File gitDir = PathManager.getLocalRepo();

        try (
                Git git = Git.open(gitDir);
                Repository repository = git.getRepository();
                RevWalk walk = new RevWalk(repository);
        ) {
            ObjectId id = repository.resolve("d191e48359f");
            RevObject gitObj = walk.parseAny(id);
            String sha1 = gitObj.getName();
            String type;
            switch (gitObj.getType()) {
                case Constants.OBJ_COMMIT:
                    type = "commit";
                    break;
                case Constants.OBJ_TREE:
                    type = "tree";
                    break;
                case Constants.OBJ_BLOB:
                    type = "blob";
                    break;
                case Constants.OBJ_TAG:
                    type = "tag";
                    break;
                default:
                    throw new IllegalArgumentException("" + gitObj.getType());
            }
            System.out.println(sha1 + " " + type);
            walk.dispose();
        }
    }

    public static Tree getTree(RevTag tag) {
        String title = String.format("tag: %s", getShortName(tag.name()));
        Tree tree = Tree.valueOf(title);

        RevObject obj = tag.getObject();
        return tree;
    }

    public static Tree getTree(RevCommit commit) {
        String title = getTitle(commit);
        Tree parent = Tree.valueOf(title);

        parent.addChild(getTree(commit.getTree()));
        return parent;
    }

    public static Tree getTree(RevTree tree) {
        String title = getTitle(tree);
        Tree parent = Tree.valueOf(title);

        return parent;
    }

    public static String getTitle(RevObject obj) {
        int type = obj.getType();
        String prefix;
        switch (type) {
            case Constants.OBJ_COMMIT:
                prefix = "commit";
                break;
            case Constants.OBJ_TREE:
                prefix = "tree";
                break;
            case Constants.OBJ_BLOB:
                prefix = "blob";
                break;
            case Constants.OBJ_TAG:
                prefix = "tag";
                break;
            default:
                throw new IllegalArgumentException("unknown type: " + type);
        }
        return String.format("%s: %s", prefix, getShortName(obj.name()));
    }

    public static String getShortName(String sha1) {
        return sha1.substring(0, 6);
    }
}
