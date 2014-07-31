package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 19/07/2014.
 */
public class Node {

    private Node parent;
    private Node leftChild;
    private Node rightChild;
    private NodeType nodeType;
    private int action;

    public Node(NodeType nodeType, int action){
        this.nodeType = nodeType;
        this.action = action;
    }

    public boolean runAction(LifeForm lifeForm){
        if(nodeType == NodeType.ACTION){

        } else if(nodeType == NodeType.DECISION){
            if(action == 0){
                return lifeForm.canSmellFood(20);
            }
            if(action == 1){
                return lifeForm.canSmellFood(20);
            }
           /* if(action == 4){
                return lifeForm.isFoodRight();
            }
            if(action == 3){
                return lifeForm.isFoodLeft();
            }
            if(action == 2){
                return lifeForm.isFoodUp();
            }
            if(action == 5){
                return lifeForm.isFoodDown();
            }
            */
        } else if(nodeType == NodeType.TERMINAL){
            if(action == 0){
                lifeForm.moveUp();
            }
            if(action == 1){
                lifeForm.moveLeft();
            }
            if(action == 2){
                lifeForm.moveDown();
            }
            if(action == 3){
                lifeForm.moveRight();
            }
        }

        return  true;
    }


    public Node getParent() {
        return parent;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public int getAction() {
        return action;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
