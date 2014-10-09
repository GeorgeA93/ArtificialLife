package com.allen.george.artificiallife.ga.Behaviour;

import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 07/10/2014.
 */
public class BehaviourTree {

    private LifeForm lifeForm;
    private BehaviourTreeNode rootNode;

    public BehaviourTree(LifeForm lifeForm, BehaviourTreeNode rootNode){
        this.lifeForm = lifeForm;
        this.rootNode = rootNode;
       // this.init();
    }

    public void init(){

        rootNode = new BehaviourTreeNode(BehaviourTreeNodeType.CONDTION, 5); //HAS ENERGY

        rootNode.setLeftChild(new BehaviourTreeNode(BehaviourTreeNodeType.CONDTION, 4)); //NEEDS TO GO TO DEN

        rootNode.getLeftChild().setLeftChild(new BehaviourTreeNode(BehaviourTreeNodeType.TERMINAL, 2)); //MOVE TO DEN

        rootNode.getLeftChild().setRightChild(new BehaviourTreeNode(BehaviourTreeNodeType.CONDTION, 0)); //IS HUNGRY

        rootNode.getLeftChild().getRightChild().setLeftChild(new BehaviourTreeNode(BehaviourTreeNodeType.CONDTION, 2)); //CAN SMELL FOOD

        rootNode.getLeftChild().getRightChild().setRightChild(new BehaviourTreeNode(BehaviourTreeNodeType.CONDTION, 1)); //IS THIRSTY

        rootNode.getLeftChild().getRightChild().getLeftChild().setLeftChild(new BehaviourTreeNode(BehaviourTreeNodeType.TERMINAL, 0)); //move food

        rootNode.getLeftChild().getRightChild().getLeftChild().setRightChild(new BehaviourTreeNode(BehaviourTreeNodeType.TERMINAL, 3)); //move random

        rootNode.getLeftChild().getRightChild().getRightChild().setLeftChild(new BehaviourTreeNode(BehaviourTreeNodeType.CONDTION, 3)); //Can see water

        rootNode.getLeftChild().getRightChild().getRightChild().setRightChild(new BehaviourTreeNode(BehaviourTreeNodeType.TERMINAL, 3)); //move random

        rootNode.getLeftChild().getRightChild().getRightChild().getLeftChild().setLeftChild(new BehaviourTreeNode(BehaviourTreeNodeType.TERMINAL, 2)); //move water

        rootNode.getLeftChild().getRightChild().getRightChild().getLeftChild().setRightChild(new BehaviourTreeNode(BehaviourTreeNodeType.TERMINAL, 3)); //move random

    }

    public void runRootNode(){
        runRootNode(this.rootNode);
    }

    private void runRootNode(BehaviourTreeNode node){
        if(node == null) return;

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

    private void runAsAction(BehaviourTreeNode node){

    }

    private void runAsCondition(BehaviourTreeNode node){
        if (node.runFunction(lifeForm))
            runRootNode(node.getLeftChild());
        else
            runRootNode(node.getRightChild());
    }

    private void runAsTerminal(BehaviourTreeNode node){
        node.runFunction(lifeForm);
    }

    private void runAsSelector(BehaviourTreeNode node){
        node.getLeftChild().runFunction(lifeForm);
        node.getRightChild().runFunction(lifeForm);
    }

    public LifeForm getLifeForm() {
        return lifeForm;
    }

    public void setLifeForm(LifeForm lifeForm) {
        this.lifeForm = lifeForm;
    }

    public BehaviourTreeNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(BehaviourTreeNode rootNode) {
        this.rootNode = rootNode;
    }
}
