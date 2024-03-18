package lsieun.drawing;


import lsieun.drawing.canvas.Canvas;
import lsieun.drawing.theme.tree.Tree;

public class GitConcept {
    public static void main(String[] args) {
        Tree tree = Tree.valueOf("Git Concept");

        Tree child1 = Tree.valueOf("Repository (.git)");
        Tree child2 = Tree.valueOf("Working Directory");
        tree.addChild(child1);
        tree.addChild(child2);


        Tree child11 = Tree.valueOf("Git Objects");
        Tree child12 = Tree.valueOf("Index Area");
        child1.addChild(child11);
        child1.addChild(child12);

        Tree child121 = Tree.valueOf("git ls-files -s");
        child12.addChild(child121);

        Tree child111 = Tree.valueOf("tag");
        Tree child112 = Tree.valueOf("commit");
        Tree child113 = Tree.valueOf("tree");
        Tree child114 = Tree.valueOf("blob");

        child11.addChild(child111);
        child11.addChild(child112);
        child11.addChild(child113);
        child11.addChild(child114);

        Tree child1111 = Tree.valueOf("lightweight");
        Tree child1112 = Tree.valueOf("annotated");
        child111.addChild(child1111);
        child111.addChild(child1112);

        Canvas canvas = new Canvas();
        canvas.draw(0, 0, tree);
        System.out.println(canvas);
        System.out.println();
    }
}
