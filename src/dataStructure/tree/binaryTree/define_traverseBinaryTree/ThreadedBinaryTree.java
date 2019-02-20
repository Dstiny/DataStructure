package dataStructure.tree.binaryTree.define_traverseBinaryTree;

//线索二叉树


class Node<E>{
    E data;
    Node lchild,rchild;
    boolean ltag,rtag;

    public Node(E data) {
        this.data = data;
        this.ltag=false;
        this.rtag=false;
        this.lchild=null;
        this.rchild=null;
    }
}

public class ThreadedBinaryTree<E> {

    Node<E> root;

    boolean link=false,thread=true;

    public ThreadedBinaryTree() {
        root=null;
    }

    /**
     * 中序索引化二叉树
     * 即在遍历时时候找到空指针进行修改
     */
    Node<E> pre;            //始终指向刚刚访问的结点

    public void inThreading(){
        inThreading(root);
    }
    private void inThreading(Node<E> p) {
        if(p!=null){

            inThreading(p.lchild);

            if(p.lchild==null){
                p.ltag=thread;
                p.lchild=pre;
            }

            if(pre!=null && pre.rchild==null){
                pre.rtag=thread;
                pre.rchild=p;
            }

            pre=p;
            inThreading(p.rchild);
        }

        return ;
    }

    /**
     * 中序遍历二叉线索链表表示的二叉树（后继方式）
     * 先找到最左子节点
     */

    public void inOrderTraverse(){
        Node<E> p=root;
        while(p!=null){

            while(p.ltag==link)
                p = p.lchild;
            System.out.println(p.data);

            while(p.rtag==thread) {
                p = p.rchild;
                System.out.println(p.data);
            }
            p=p.rchild;
        }

        System.out.print("、");
    }



    public static void main(String[] args) {

        ThreadedBinaryTree<String> aTree=new ThreadedBinaryTree<>();
        aTree.root=new Node("Test");
        aTree.root.lchild=new Node("B");
        aTree.root.lchild.lchild=new Node("C");
        aTree.root.rchild=new Node("D");
        aTree.root.rchild.lchild=new Node("E");
        aTree.root.rchild.rchild=new Node("F");

        aTree.inThreading();
        aTree.inOrderTraverse();
    }


}
