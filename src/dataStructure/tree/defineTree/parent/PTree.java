package dataStructure.tree.defineTree.parent;

//双亲表示法


import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class PNode{

    char data;
    int parent;

    public PNode(char data, int parent) {
        this.data = data;
        this.parent = parent;
    }

    public PNode(char data) {
        this.data = data;
    }
}

public class PTree{

    PNode nodes[]=new PNode[15];

    int n;      //结点在数组的位置

    PNode rootNode;

    public PTree() {
        n=0;
    }

    int m;
    public PNode createTree(){

//        存放结点的data
        Queue<PNode> q=new LinkedList<>();

//        存放结点在数组的位置，以便孩子结点容易获取parent域
        Queue<Integer> q2=new LinkedList<>();

        Scanner sc=new Scanner(System.in);
        System.out.println("请输入根节点");
        String a=sc.next();
//        把字符串转化成字符数组
        char b[]=a.toCharArray();

        if(b[0] !='#'){     //根节点存在
            rootNode=new PNode(b[0],-1);
            nodes[0]=rootNode;
            q.add(rootNode);
            q2.add(n);
            n++;

            while(!q.isEmpty() && n<nodes.length){
                PNode p=q.remove();
                System.out.println("请输入"+p.data+"的所有孩子，若没有输入#");
                int par=q2.remove();        //该结点的父亲在数组中的位置
                String c=sc.next();
                char d[]=c.toCharArray();

                if(d[0] != '#'){
                    for(int i=0;i<d.length;i++){
                        nodes[n]=new PNode(d[i],par);
                        q.add(nodes[n]);
                        q2.add(n);
                        n++;
                        m=n;
                    }

                    if(n>nodes.length){
                        increaseSpace(nodes.length+15);
                    }
                }
            }
        }
        else    rootNode=null;

        return rootNode;
    }

    public void increaseSpace(int newSpace) {
        int size=nodes.length;
        PNode b[]=new PNode[newSpace];
        for(int i=0;i<size;i++){
            nodes[i]=b[i];
        }
    }

//    public int depth(){
//        int max=0,height,p=0;
//
//    }

}
