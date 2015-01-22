package com.allen.george.artificiallife.ga.Behaviour.nodes.tree;



import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNodeType;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.geneticx.Gene;

import java.util.*;

/**
 * Created by George on 07/10/2014.
 */
public class BehaviourTree {

    private LifeForm lifeForm;
    private BehaviourTreeNode rootNode;
    private int unreachableNodeCount = 0;

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
        lifeForm.currentNode = node.getFunctionName();
        if (node.runFunction(lifeForm))
            runRootNode(node.getLeftChild());
        else
            runRootNode(node.getRightChild());
    }

    private void runAsTerminal(BehaviourTreeNode node){
        lifeForm.currentNode = node.getFunctionName();
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
            if (root.getLeftChild() != null){
                unreachableNodeCount += 1;
                root.getLeftChild().markUnReachable();

            }
            if (root.getRightChild() != null){
                unreachableNodeCount += 1;
                root.getRightChild().markUnReachable();
            }
        } else if (root.getBehaviourTreeNodeType() == BehaviourTreeNodeType.ACTION) {
            if (root.getRightChild() != null){
                unreachableNodeCount += 1;
                root.getRightChild().markUnReachable();
            }
        }

        if (root.getLeftChild() != null)
            markUnReachableNodes(root.getLeftChild());
        if (root.getRightChild() != null)
            markUnReachableNodes(root.getRightChild());
    }



    public Gene[] convertTreeToGenes(){
        List<BehaviourTreeNode> nodes = convertToNodes();


        Gene[] result = new Gene[nodes.size()];

        for(int i = 0; i < nodes.size(); i ++){
            if(nodes.get(i).getBehaviourTreeNodeType() == BehaviourTreeNodeType.CONDTION){
                result[i] = new Gene(nodes.get(i).getFunction() );
            } else {
                result[i] = new Gene(nodes.get(i).getFunction() + BehaviourTreeNode.NUM_CONDITIONS);
            }
        }

        return result;
    }

    public BehaviourTreeNode getRandomNode(){
        List<BehaviourTreeNode> nodes = convertToNodes();
        int length = nodes.size();
        Random random = new Random();

        while(length > 1){
            BehaviourTreeNode node = nodes.get(random.nextInt(length));
            if(node.isReachable()){
                return node;
            } else {
                length /= 2;
            }
        }

        return nodes.get(0);
    }

    private List<BehaviourTreeNode> convertToNodes(){
       // updateNodeDepths(rootNode, 0);
        List<BehaviourTreeNode> nodes = new ArrayList<BehaviourTreeNode>();
        nodes.add(rootNode);
        nodes.addAll(BehaviourTree.convertToNodes(rootNode));
        return nodes;
    }

    private static List<BehaviourTreeNode> convertToNodes(BehaviourTreeNode root) {
        if (root == null || root.isReachable() == false)
            return Arrays.asList(new BehaviourTreeNode[]{});

        List<BehaviourTreeNode> nodes = new ArrayList<BehaviourTreeNode>();
        if (root.getLeftChild() != null)
            nodes.add(root.getLeftChild());
        if (root.getRightChild() != null)
            nodes.add(root.getRightChild());
        if (root.getLeftChild() != null)
            nodes.addAll(convertToNodes(root.getLeftChild()));
        if (root.getRightChild() != null)
            nodes.addAll(convertToNodes(root.getRightChild()));

        //Sort nodes by depth to get Level traversal


        return nodes;
    }

    private void updateNodeDepths(BehaviourTreeNode root, int depth) {
        root.setDepth(depth);
        if (root.getLeftChild() != null)
            updateNodeDepths(root.getLeftChild(), depth+1);
        if (root.getRightChild() != null)
            updateNodeDepths(root.getRightChild(), depth+1);
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

    public int getUnreachableNodeCount() {
        return unreachableNodeCount;
    }
}
