package lsieun.drawing;


import lsieun.drawing.canvas.Canvas;
import lsieun.drawing.theme.tree.Tree;

public class GitPlumbingCommands {
    public static void main(String[] args) {
        Tree tree = Tree.valueOf("Git plumbing commands");

        Tree child1 = Tree.valueOf("git cat-file -p");
        Tree child2 = Tree.valueOf("git ls-files -s");
        Tree child3 = Tree.valueOf("git rev-parse");
        Tree child4 = Tree.valueOf("git hash-object");
        Tree child5 = Tree.valueOf("git write-tree");
        Tree child6 = Tree.valueOf("git commit-tree");
        tree.addChild(child1);
        tree.addChild(child2);
        tree.addChild(child3);
        tree.addChild(child4);
        tree.addChild(child5);
        tree.addChild(child6);

        Tree child11 = Tree.valueOf("commit");
        Tree child12 = Tree.valueOf("tree");
        Tree child13 = Tree.valueOf("blob");
        Tree child14 = Tree.valueOf("tag");
        child1.addChild(child11);
        child1.addChild(child12);
        child1.addChild(child13);
        child1.addChild(child14);

        Tree child21 = Tree.valueOf("Index");
        child2.addChild(child21);

        Tree child31 = Tree.valueOf("refs");
        Tree child32 = Tree.valueOf("short SHA1");
        child3.addChild(child31);
        child3.addChild(child32);

        Tree child41 = Tree.valueOf("lightweight");
        Tree child42 = Tree.valueOf("annotated");
        child4.addChild(child41);
        child4.addChild(child42);

        Canvas canvas = new Canvas();
        canvas.draw(0, 0, tree);
        System.out.println(canvas);
        System.out.println();
    }
}
