package dataStructure.tree.binaryTree.define_traverseBinaryTree;

//链表二叉树存储结构

//结点最多有两个孩子，为他设计一个数据域和两个指针域：二叉链表

class TreeNode<E>{

    E data;
    TreeNode<E> lchild,rchild;

    public TreeNode(E data) {
        this.data = data;
        this.lchild=null;
        this.rchild=null;
    }

}


public class LinkBinaryTree_Traverse<E> {

    private TreeNode<E> root;

    public LinkBinaryTree_Traverse() {
        root=null;
    }


//    前序遍历
    public void preOrder(){
        preOrderTraverse(root);
    }

    private void preOrderTraverse(TreeNode<E> node) {
        if(node==null) return;
        System.out.println(node.data);
        preOrderTraverse(node.lchild);
        preOrderTraverse(node.rchild);
    }


//    中序遍历
    public void inOrder(){
        inOrderTraverse(root);
    }

    private void inOrderTraverse(TreeNode<E> node) {
        if(node==null) return;
        inOrderTraverse(node.lchild);
        System.out.println(node.data);
        inOrderTraverse(node.rchild);
    }

//    后序遍历
    public void postOrder(){
        postOrderTraverse(root);
    }

    private void postOrderTraverse(TreeNode<E> node) {
        if(node==null) return ;
        postOrderTraverse(node.lchild);
        postOrderTraverse(node.rchild);
        System.out.println(node.data);
    }


//    二叉树的建立略

    public static void main(String[] args) {

        LinkBinaryTree_Traverse<String> bTree=new LinkBinaryTree_Traverse<>();

        bTree.root=new TreeNode("Test");
        bTree.root.lchild=new TreeNode("B");
        bTree.root.rchild=new TreeNode("C");
        bTree.root.lchild.rchild=new TreeNode("D");

        System.out.println("前序遍历");
        bTree.preOrder();

        System.out.println("中序遍历");
        bTree.inOrder();

        System.out.println("后序遍历");
        bTree.postOrder();
    }
}
