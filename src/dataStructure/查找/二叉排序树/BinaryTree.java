package dataStructure.查找.二叉排序树;

class Node{

    public int data;

    public Node left,right;

    public Node(int data){

        this.data=data;

        this.left=null;
        this.right=null;
    }
}

public class BinaryTree {

    private Node root;

    public BinaryTree() {
        this.root = null;
    }

//    查找,递归
    public boolean serach_1(int key){
        return searching_1(key,root);
    }

    private boolean searching_1(int key, Node node) {

        if(node==null){

            return false;
        }

        if(node.data==key){

            return true;

        }else if(node.data<key){

            return searching_1(key,node.left);

        }else{

            return searching_1(key,node.right);
        }
    }

//    非递归查找
    public boolean serach_2(int key){

        Node p=root;

        while(p != null){

            if(p.data>key){

                p=p.left;
            }else if(p.data<key){

                p=p.left;

            }else{

                return true;
            }
        }

        return false;
    }


    //    将data插入到二叉排序树中
    public void insert(int data){

        Node newNode=new Node(data);

        if(root==null){

            root=newNode;

        }else{

            Node current=root;

            Node parent;

            while(true){        //寻找插入的位置

                parent=current;

                if(data<current.data){

                    current=current.left;

                    if(current==null){

                        parent.left=newNode;

                        return ;
                    }

                }else{

                    current=current.right;

                    if(current==null){

                        parent.right=newNode;

                        return ;
                    }
                }
            }
        }
    }

//    删除：先找到删除节点位置及其父节点。
    public boolean delete(int key){

        if(root==null) {

            System.out.println("tree empty");
            return false;
        }

        Node parent=null;
        Node current=root;

        while(current != null){

            if(current.data>key){

                parent=current;
                current=current.left;

            }else if(current.data<key){

                parent=current;
                current=current.right;

            }else{

                delete(current,parent);
                System.out.println("delete yes");
                return true;
            }
        }

        System.out.println("no that data");

        return false;
    }

    private void delete(Node current, Node parent) {

        if(current.left==null){

            if(current==root){

                root=root.right;

                current=null;
            }else{

                if(parent.data>current.data){

                    parent.left=current.right;

                    current=null;
                }else{

                    parent.right=current.right;

                    current=null;
                }
            }
        }else if(current.right==null){

            if(current==root ){

                root=root.left;

                current=null;
            }else{

                if(parent.data>current.data){

                    parent.left=current.left;
                    current=null;
                }else{

                    parent.right=current.left;
                    current=null;
                }
            }

        }else{      //左右子树都不空

            Node q,s;

            q=current;
            s=current.left;

            while(s.right != null){

                q=s;
                s=s.right;
            }

            current.data=s.data;

            if(q != current){

                q.right=s.left;
            }else{
                q.left=s.left;
            }

            s=null;
        }
    }


    //    构建二叉树
    public void buildTree(int[] data){

        for(int i=0;i<data.length;i++){

            insert(data[i]);
        }
    }

//    中序遍历递归实现:从跟结点开始，不是先访问跟结点，中序遍历跟结点的左子树，然后访问跟结点，
//    最后遍历右子树
    private void inOrder(Node localRoot){

        if(localRoot != null){

            inOrder(localRoot.left);

            System.out.println(localRoot.data+" ");

            inOrder(localRoot.right);
        }
    }

    public void inOrder(){
        inOrder(root);
    }
}

class Main{

    public static void main(String[] args) {

        BinaryTree binaryTree=new BinaryTree();

        int arr[]={
                62,88,58,47,35,73,51,99,37,93
        };

        binaryTree.buildTree(arr);

        binaryTree.inOrder();

        System.out.println(binaryTree.serach_1(100));

        binaryTree.delete(37);

        System.out.println();

        binaryTree.inOrder();

    }
}
