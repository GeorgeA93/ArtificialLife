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
    }

    public void runRootNode(){
        runRootNode(this.rootNode);
    }

    private void runRootNode(BehaviourTreeNode node){


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

    public void markUnreachableNodes(){
        markUnReachableNodes(rootNode);
    }

    private void markUnReachableNodes(BehaviourTreeNode root) {
        if (root.getBehaviourTreeNodeType() == BehaviourTreeNodeType.TERMINAL) {
            if (root.getLeftChild() != null)
                root.getLeftChild().markUnReachable();
            if (root.getRightChild() != null)
                root.getRightChild().markUnReachable();
        } else if (root.getBehaviourTreeNodeType() == BehaviourTreeNodeType.ACTION) {
            if (root.getRightChild() != null)
                root.getRightChild().markUnReachable();
        }

        if (root.getLeftChild() != null)
            markUnReachableNodes(root.getLeftChild());
        if (root.getRightChild() != null)
            markUnReachableNodes(root.getRightChild());
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
