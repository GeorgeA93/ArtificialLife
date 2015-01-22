package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNodeType;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.geneticx.Chromosome;
import com.allen.george.geneticx.Gene;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by George on 01/12/2014.
 */
public class Tree {


    private LifeForm lifeForm;
    private Node root = null;

    public Tree(Tree tree, LifeForm lifeForm, int flag){
        this.root = tree.getRoot();
        this.lifeForm = lifeForm;
    }

    public Tree(Node root, LifeForm lifeForm){
        this.root = root;
        this.lifeForm = lifeForm;
    }

    public boolean isTreeEmpty(){
        return root == null;
    }

    public int getDepth(){
        return depth(root);
    }

    private int depth(Node root){
        if(root == null){
            return 0;
        } else {
            return root.depth();
        }
    }

    public String printPostOrder(){
        String nodes = "";
        if(root != null){
            nodes += root.printPostOrder();
            System.out.println("");
        } else {
            System.out.println("The Tree Is Empty");
        }
        return nodes;
    }

    public void printInOrder(){
        if(root != null){
            root.printInOrder();
            System.out.println("");
        } else  {
            System.out.println("The Tree Is Empty");
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public LifeForm getLifeForm() {
        return lifeForm;
    }

    public void setLifeForm(LifeForm lifeForm) {
        this.lifeForm = lifeForm;
    }

    public void runRootNode(){
        runRootNode(root);
    }

    private void runRootNode(Node node){
        if(node == null) return;

        // System.out.println(node.getFunctionName());

        if(node.getBehaviourTreeNodeType() == BehaviourTreeNodeType.ACTION){
            runAsAction(node);
        } else if(node.getBehaviourTreeNodeType() == BehaviourTreeNodeType.CONDTION){
            runAsCondition(node);
        } else if (node.getBehaviourTreeNodeType() == BehaviourTreeNodeType.TERMINAL){
            runAsTerminal(node);
        } else if(node.getBehaviourTreeNodeType() == BehaviourTreeNodeType.SELECTOR){
            runAsSelector(node);
        }
    }

    private void runAsAction(Node node){

    }

    private void runAsCondition(Node node){
        if (node.runFunction(lifeForm))
            runRootNode(node.getLeftChild());
        else
            runRootNode(node.getRightChild());
    }

    private void runAsTerminal(Node node){
     //   lifeForm.currentNode = node.getFunctionName();
        node.runFunction(lifeForm);

        if(node.getParent().getRightChild() != node){
            runRootNode(node.getParent().getRightChild()); //run the next question
        }


    }

    private void runAsSelector(Node node){
        node.getLeftChild().runFunction(lifeForm);
        node.getRightChild().runFunction(lifeForm);
    }

    public ArrayList<Node> getNodes(){
        ArrayList<Node> nodeArrayList = new ArrayList<Node>();

        nodeArrayList = this.root.getNodesAsList(nodeArrayList);

        return nodeArrayList;
    }

    public ArrayList<Node> getConditionNodes(){
        ArrayList<Node> nodeArrayList = new ArrayList<Node>();

        nodeArrayList = this.root.getConditionNodesAsList(nodeArrayList);

        return nodeArrayList;
    }

    public ArrayList<Node> getTerminalNodes(){
        ArrayList<Node> nodeArrayList = new ArrayList<Node>();

        nodeArrayList = this.root.getTerminalNodesAsList(nodeArrayList);

        return nodeArrayList;
    }

    public boolean exists(Node node){
        ArrayList<Node> nodeArrayList = getNodes();
        for(Node n : nodeArrayList){
            if(node.getFunctionName().equals(n.getFunctionName())){
                return true;
            }
        }
        return false;
    }

    public void replace(Node one, Node two){
        if(root != null){
            if(root == one){
                setRoot(two);
            } else {
                Node parentOne = one.getParent();
                if(parentOne != null){
                    if(parentOne.getLeftChild() != null && parentOne.getLeftChild() == one){
                        parentOne.setLeftChild(two);
                    } else if(parentOne.getRightChild() != null && parentOne.getRightChild() == one){
                        parentOne.setRightChild(two);
                    }
                }
            }
        }
    }

    public static void swap(Tree treeOne, Tree treeTwo, Node nodeOne, Node nodeTwo){
        Node parentNodeOne = nodeOne.getParent();
        Node parentNodeTwo = nodeTwo.getParent();

        if (parentNodeOne != null) {
            if (parentNodeOne.getLeftChild() != null && parentNodeOne.getLeftChild() == nodeOne) {
                parentNodeOne.setLeftChild(nodeTwo);
            } else {
                if (parentNodeOne.getRightChild() != null && parentNodeOne.getRightChild() == nodeOne)
                    parentNodeOne.setRightChild(nodeTwo);
            }
        } else {
            treeOne.setRoot(nodeTwo);
        }

        if (parentNodeTwo != null) {
            if (parentNodeTwo.getLeftChild() != null && parentNodeTwo.getLeftChild() == nodeTwo) {
                parentNodeTwo.setLeftChild(nodeOne);
            } else {
                if (parentNodeTwo.getRightChild() != null && parentNodeTwo.getRightChild() == nodeTwo) {
                    parentNodeTwo.setRightChild(nodeOne);
                }
            }
        } else {
            treeTwo.setRoot(nodeOne);
        }
    }

    public static Tree copy(Tree tree){
        Node root = tree.getRoot();
        LifeForm lifeForm = tree.getLifeForm();
        if(root == null){
            return new Tree(null, null);
        } else {
            return new Tree(root.copy(), lifeForm);
        }
    }


    public static Tree generateTree(int maxDepth, LifeForm life) {
        Node root ;

        root = generateFullTreeConstrained(maxDepth, null, 2);

        return new Tree(root, life);
    }


    public static Tree generateTreeFromGenes(Gene[] genes, LifeForm lifeForm){
        ArrayList<Node> nodes = new ArrayList<Node>();
        for(int i = 0; i < genes.length; i ++){
            nodes.add(Node.createFromGene(genes[i]));
        }

        int index = 1;
        for (int n = 0; n < Math.pow(2, 4 - 1) - 1;  n++){
            nodes.get(n).setLeftChild(nodes.get(index));
            index++;
            nodes.get(n).setRightChild(nodes.get(index));
            index++;
           // System.out.println(nodes.get(n).getFunctionName());
          //  System.out.println(nodes.get(n).getLeftChild().getFunctionName());
           // System.out.println(nodes.get(n).getRightChild().getFunctionName());
          //  System.out.println();

        }

       // System.out.println();
       // System.out.println();
       // System.out.println();

        Tree res = new Tree(nodes.get(0), lifeForm);

        GAUtil.printNode(res.getRoot());

        return res;
    }

    private static Node generateFullTree(int maxDepth) {
        Node root;
        Random random = new Random();
        if (maxDepth > 1) {
            //condition
            root = generateConditionNode(random.nextInt(6)); //4
        } else {
            //terminal
            root =  generateTerminalNode(random.nextInt(4)); //3
        }

        for(int i = 0; i < root.getNumberOfChildren(); i++)  {
            if (i == 0) {
                root.setLeftChild(generateFullTree(maxDepth - 1));
            } else {
                root.setRightChild(generateFullTree(maxDepth - 1));
            }
        }

        return root;
    }

    static int left = 0;
    static int right = 1;

    private static Node generateFullTreeConstrained(int maxDepth, Node parent, int leftOrRight){
        Node root;
        Random random = new Random();
        if (maxDepth > 0) {
            //condition
            root = generateConditionNode(random.nextInt(4)); //4
            if(parent!= null){
                root.setParent(parent);
                if(leftOrRight == left){
                    if(root.getParent().getFunction() == 0){
                        root = new TerminalNode(random.nextInt(4));
                    } else if (root.getParent().getFunction() == 1){
                        root = new TerminalNode(random.nextInt(4));
                    } else if (root.getParent().getFunction() == 2){
                        root = new TerminalNode(random.nextInt(4));
                    }  else if (root.getParent().getFunction() == 3){
                        root = new TerminalNode(random.nextInt(4));
                    }
                }
            }
        } else {
            root = generateTerminalNode(random.nextInt(4)); //4
            if(parent!= null){
                root.setParent(parent);
                if(leftOrRight == left){
                    if(root.getParent().getFunction() == 0){
                        root = new TerminalNode(random.nextInt(4));
                    } else if (root.getParent().getFunction() == 1){
                        root = new TerminalNode(random.nextInt(4));
                    } else if (root.getParent().getFunction() == 2){
                        root = new TerminalNode(random.nextInt(4));
                    }  else if (root.getParent().getFunction() == 3){
                        root = new TerminalNode(random.nextInt(4));
                    }
                }
            }
            root.setNumberOfChildren(0);
        }

        for(int i = 0; i < root.getNumberOfChildren(); i++)  {
            if (i == 0) {
                root.setLeftChild(generateFullTreeConstrained(maxDepth - 1, root, left));
            } else {
                root.setRightChild(generateFullTreeConstrained(maxDepth - 1, root, right));
            }
        }

        return root;
    }

    private static Node generateTerminalNode(int val) {
        Random random = new Random();
        return new TerminalNode(val);
    }

    private static Node generateConditionNode(int val) {
        Random random = new Random();
        return new ConditionNode(val);
    }

    public static Chromosome convertToChromosome(Tree t){
        ArrayList<Node> nodes = t.getNodes();
        Gene[] genes = new Gene[nodes.size()];

        for(int i = 0; i < nodes.size(); i ++){
            Node node = nodes.get(i);
            if(node instanceof ConditionNode){
                genes[i] = new Gene(node.getFunction());
            } else if (node instanceof TerminalNode) {
                genes[i] = new Gene(node.getFunction() + Node.NUM_CONDITIONS);
            }
        }

        Chromosome res = new Chromosome(genes);
        return res;
    }

}
