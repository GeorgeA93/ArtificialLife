package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 19/07/2014.
 */
public class BehaviourTree {

    private Node rootNode;
    private LifeForm lifeForm;

    public BehaviourTree(LifeForm lifeForm){
        this.lifeForm = lifeForm;

        rootNode = new Node(NodeType.DECISION, 0);
        rootNode.setRightChild(new Node(NodeType.DECISION, 2));
        rootNode.setLeftChild(new Node(NodeType.TERMINAL, 1));

        rootNode.getRightChild().setLeftChild(new Node(NodeType.DECISION, 3));
        rootNode.getRightChild().setRightChild(new Node(NodeType.TERMINAL, 0));

        rootNode.getRightChild().getLeftChild().setLeftChild(new Node(NodeType.DECISION, 4));
        rootNode.getRightChild().getLeftChild().setRightChild(new Node(NodeType.TERMINAL, 1));

        rootNode.getRightChild().getLeftChild().getLeftChild().setRightChild(new Node(NodeType.TERMINAL, 3));
        rootNode.getRightChild().getLeftChild().getLeftChild().setLeftChild(new Node(NodeType.DECISION, 5));

        rootNode.getRightChild().getLeftChild().getLeftChild().getLeftChild().setLeftChild(new Node(NodeType.TERMINAL, 1));
        rootNode.getRightChild().getLeftChild().getLeftChild().getLeftChild().setRightChild(new Node(NodeType.TERMINAL, 2));

    }

    public void evaluate(){
        evaluate(rootNode);
    }



    private void evaluate(Node node) {

        if(node == null)return;

        if(node.getNodeType() == NodeType.ACTION){
            evaluateAction(node);
        }
        else if(node.getNodeType() == NodeType.DECISION){
            evaluateDecision(node);
        }
        else if(node.getNodeType() == NodeType.TERMINAL){
            evaulateTerminal(node);
        }
    }

    private void evaluateDecision(Node node){
        if(node.runAction(lifeForm)){
            evaluate(node.getRightChild());
        } else {
            evaluate(node.getLeftChild());
        }
    }

    private void evaluateAction(Node node){
        node.runAction(lifeForm);
        evaluate(node.getLeftChild());
    }

    private void evaulateTerminal(Node node){
        node.runAction(lifeForm);
    }

}
