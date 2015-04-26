package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.geneticx.Gene;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by George on 01/12/2014.
 */
public abstract class Node<T> {

    public static int NUM_CONDITIONS = 4;
    public static int NUM_ACTIONS = 0;
    public static int NUM_TERMINALS = 4;

    private int function;
    private NodeType behaviourTreeNodeType;

    private Node parent = null;
    private Node leftChild = null;
    private Node rightChild = null;
    private static ArrayList<String> nodes = new ArrayList<String>();
    private int numberOfChildren = 0;

    public Node(){

    }

    public Node(NodeType behaviourTreeNodeType, int function){
        this.behaviourTreeNodeType = behaviourTreeNodeType;
        this.function = function;
    }

    public abstract boolean runFunction(LifeForm lifeForm);
    public abstract Node copy();


    public int getFunction() {
        return function;
    }

    public int getFunctionForXML(){
        if(this instanceof ConditionNode){
           return function;
        } else if (this instanceof TerminalNode) {
           return function + Node.NUM_CONDITIONS;
        }
        return 999;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public NodeType getBehaviourTreeNodeType() {
        return behaviourTreeNodeType;
    }

    public void setNodeType(NodeType behaviourTreeNodeType) {
        this.behaviourTreeNodeType = behaviourTreeNodeType;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        if(leftChild != null){
            leftChild.setParent(this);
        }
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        if(rightChild != null){
            rightChild.setParent(this);
        }
        this.rightChild = rightChild;
    }

    public void printTree(OutputStreamWriter out) throws IOException {
        if (rightChild != null) {
            rightChild.printTree(out, true, "");
        }
        printNodeValue(out);
        if (leftChild != null) {
            leftChild.printTree(out, false, "");
        }
    }
    private void printNodeValue(OutputStreamWriter out) throws IOException {
        out.write(getFunctionName());
        out.write('\n');
    }
    // use string and not stringbuffer on purpose as we need to change the indent at each recursion
    private void printTree(OutputStreamWriter out, boolean isRight, String indent) throws IOException {
        if (rightChild != null) {
            rightChild.printTree(out, true, indent + (isRight ? "        " : " |      "));
        }
        out.write(indent);
        if (isRight) {
            out.write(" /");
        } else {
            out.write(" \\");
        }
        out.write("----- ");
        printNodeValue(out);
        if (leftChild != null) {
            leftChild.printTree(out, false, indent + (isRight ? " |      " : "        "));
        }
    }

    public ArrayList<Node> getNodesAsList(ArrayList<Node> nodeArrayList){
        if(getLeftChild() != null){
            nodeArrayList = getLeftChild().getNodesAsList(nodeArrayList);
        }

        if(getRightChild() != null){
            nodeArrayList = getRightChild().getNodesAsList(nodeArrayList);
        }

        nodeArrayList.add(this);

        return nodeArrayList;
    }

    public ArrayList<Node> getTerminalNodesAsList(ArrayList<Node> nodeArrayList){
        if(getLeftChild() != null){
            nodeArrayList = getLeftChild().getTerminalNodesAsList(nodeArrayList);
        }

        if(getRightChild() != null){
            nodeArrayList = getRightChild().getTerminalNodesAsList(nodeArrayList);
        }

        if(this instanceof TerminalNode){
            nodeArrayList.add(this);
        }

        return nodeArrayList;
    }

    public ArrayList<Node> getConditionNodesAsList(ArrayList<Node> nodeArrayList){
        if(getLeftChild() != null){
            nodeArrayList = getLeftChild().getConditionNodesAsList(nodeArrayList);
        }

        if(getRightChild() != null){
            nodeArrayList = getRightChild().getConditionNodesAsList(nodeArrayList);
        }

        if(this instanceof ConditionNode){
            nodeArrayList.add(this);
        }

        return nodeArrayList;
    }

    public int depth(){
        if(getLeftChild() != null && getRightChild() != null){
            return 1 + Math.max(getLeftChild().depth(), getRightChild().depth());
        } else {
            return 1;
        }
    }

    public static void swap(Node one, Node two){
        Node parentOne = one.getParent();
        Node parentTwo = two.getParent();

        if(parentOne != null){
            if(parentOne.getLeftChild() != null && parentOne.getLeftChild() == one){
                parentOne.setLeftChild(two);
            } else {
                if(parentOne.getRightChild() != null && parentOne.getRightChild() == one){
                    parentOne.setRightChild(two);
                }
            }
        }

        if(parentTwo != null){
            if(parentTwo.getLeftChild() != null && parentTwo.getLeftChild() == two){
                parentTwo.setLeftChild(one);
            } else {
                if(parentTwo.getRightChild() != null && parentTwo.getRightChild() == two){
                    parentTwo.setRightChild(two);
                }
            }
        }
    }

    public static void copyTo(Node dst, Node src){
        dst.setFunction(src.getFunction());
        dst.setLeftChild(src.getLeftChild());
        dst.setRightChild(src.getRightChild());
    }


    public Node mutate(Random generator) {

        ConditionNode mutated = new ConditionNode(generator.nextInt(NUM_CONDITIONS));
        if(mutated.getFunction() == 0){
            mutated.setLeftChild(new TerminalNode(3));
        } else if(mutated.getFunction() == 1){
            mutated.setLeftChild(new TerminalNode(0));
        } else if(mutated.getFunction() == 2){
            mutated.setLeftChild(new TerminalNode(1));
        } else if(mutated.getFunction() == 3){
            mutated.setLeftChild(new TerminalNode(2));
        }

        return mutated;
    }



    public String getFunctionName() {

        if (behaviourTreeNodeType == NodeType.CONDTION) {
            if (function == 0)
                return "canSeeWater?";
            else if (function == 1)
                return "canSmellFood?";
            else if (function == 2)
                return "canFindDen?";
            else if (function == 3)
                return "walkabout?";
        }  else if (behaviourTreeNodeType == NodeType.TERMINAL) {
            if (function == 0)
                return "moveToFoodByPath";
            else if (function == 1)
                return "moveToDenByPath";
            else if (function == 2)
                return "moveRandomDirection";
            else if (function == 3){
                return "moveToWaterByPath";
            }
        }

        return "NULL";
    }

    public static Node createFromGene(Gene g){
        int val = g.getAllele();
        if (val < NUM_CONDITIONS){
            return new ConditionNode(val);
        }
        if (val < NUM_TERMINALS + NUM_ACTIONS + NUM_CONDITIONS){
            val = val - NUM_CONDITIONS;
            return new TerminalNode(val);
        }
        return null;
    }
}
