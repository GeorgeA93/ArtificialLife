package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.utils.SimulationSettings;
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

        if(node.getBehaviourTreeNodeType() == NodeType.CONDTION){
            runAsCondition(node);
        } else if (node.getBehaviourTreeNodeType() == NodeType.TERMINAL){
            runAsTerminal(node);
        }
    }


    private void runAsCondition(Node node){
        if (node.runFunction(lifeForm))
            runRootNode(node.getLeftChild());
        else
            runRootNode(node.getRightChild());
    }

    private void runAsTerminal(Node node){
        node.runFunction(lifeForm);

        if(node.getParent().getRightChild() != node){
            runRootNode(node.getParent().getRightChild()); //run the next question
        }


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

        if(SimulationSettings.CONSTRAIN_TREE){
            root = generateFullTreeConstrained(maxDepth, null, 2);
        } else {
            root = generateFullTreeRandom(maxDepth, null, 2);
        }



        return new Tree(root, life);
    }


    static int left = 0;
    static int right = 1;

    private static Node generateFullTreeRandom(int maxDepth, Node parent, int leftOrRight){
        Node root;
        Random random = new Random();
        if (maxDepth > 0) {
            //condition
            root = generateConditionNode(random.nextInt(Node.NUM_CONDITIONS)); //4
            if(parent!= null){
                root.setParent(parent);
                if(leftOrRight == left){
                    root = new TerminalNode(random.nextInt(Node.NUM_TERMINALS));
                }
            }
        } else {
            root = generateTerminalNode(random.nextInt(Node.NUM_CONDITIONS)); //4
            if(parent!= null){
                root.setParent(parent);
                if(leftOrRight == left){
                    root = new TerminalNode(random.nextInt(Node.NUM_TERMINALS));
                }
            }
            root.setNumberOfChildren(0);
        }

        for(int i = 0; i < root.getNumberOfChildren(); i++)  {
            if (i == 0) {
                root.setLeftChild(generateFullTreeRandom(maxDepth - 1, root, left));
            } else {
                root.setRightChild(generateFullTreeRandom(maxDepth - 1, root, right));
            }
        }

        return root;
    }

    private static Node generateFullTreeConstrained(int maxDepth, Node parent, int leftOrRight){
        Node root;
        Random random = new Random();
        if (maxDepth > 0) {
            //condition
            root = generateConditionNode(random.nextInt(Node.NUM_CONDITIONS));
            if(parent!= null){
                root.setParent(parent);
                if(leftOrRight == left){
                    if(root.getParent().getFunction() == 0){
                        root = new TerminalNode(3);
                    } else if (root.getParent().getFunction() == 1){
                        root = new TerminalNode(0);
                    } else if (root.getParent().getFunction() == 2){
                        root = new TerminalNode(1);
                    }  else if (root.getParent().getFunction() == 3){
                        root = new TerminalNode(2);
                    }
                }
            }
        } else {
            root = generateTerminalNode(random.nextInt(Node.NUM_TERMINALS));
            if(parent!= null){
                root.setParent(parent);
                if(leftOrRight == left){
                    if(root.getParent().getFunction() == 0){
                        root = new TerminalNode(3);
                    } else if (root.getParent().getFunction() == 1){
                        root = new TerminalNode(0);
                    } else if (root.getParent().getFunction() == 2){
                        root = new TerminalNode(1);
                    }  else if (root.getParent().getFunction() == 3){
                        root = new TerminalNode(2);
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
        return new TerminalNode(val);
    }

    private static Node generateConditionNode(int val) {
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
