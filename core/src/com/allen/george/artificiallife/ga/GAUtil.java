package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.conditions.*;
import com.allen.george.artificiallife.ga.Behaviour.nodes.terminals.moveRandomDirectionNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.terminals.moveToDenByPathNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.terminals.moveToFoodByPathNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.terminals.moveToWaterByPathNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.tree.BehaviourTree;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.geneticx.Gene;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by George Allen on 11/16/2014.
 */
public class GAUtil {

    public static BehaviourTree generateTreeFromGenes(Gene[] genes, LifeForm lifeForm){
        ArrayList<BehaviourTreeNode> nodes = new ArrayList<BehaviourTreeNode>();
        for(int i = 0; i < genes.length; i ++){
            nodes.add(BehaviourTreeNode.createFromGene(genes[i]));
        }

        int index = 1;
        nodes.get(0).setDepth(0);
        for (int n = 0; n < Math.pow(2, 4 - 1) - 1;  n++){
            nodes.get(n).setLeftChild(nodes.get(index));
            nodes.get(index).setDepth(nodes.get(n).getDepth() + 1);
            index++;
            nodes.get(n).setRightChild(nodes.get(index));
            nodes.get(index).setDepth(nodes.get(n).getDepth() + 1);
            index++;
            System.out.println(nodes.get(n).getFunctionName());
            System.out.println(nodes.get(n).getLeftChild().getFunctionName());
            System.out.println(nodes.get(n).getRightChild().getFunctionName());
            System.out.println();

        }

        System.out.println();
        System.out.println();
        System.out.println();

        BehaviourTree res = new BehaviourTree(lifeForm, nodes.get(0));
       res.markUnreachableNodes();

        return res;
    }

    public static <T extends Comparable<?>> void printNode(Node<T> root) {
        int maxLevel = GAUtil.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printNodeInternal(
            List<Node<T>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || GAUtil.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        GAUtil.printWhitespaces(firstSpaces);

        List<Node<T>> newNodes = new ArrayList<Node<T>>();
        for (Node<T> node : nodes) {
            if (node != null) {
                System.out.print(node.getFunctionName());
                newNodes.add(node.getLeftChild());
                newNodes.add(node.getRightChild());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            GAUtil.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                GAUtil.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    GAUtil.printWhitespaces(endgeLines + endgeLines + i
                            + 1);
                    continue;
                }

                if (nodes.get(j).getLeftChild() != null)
                    System.out.print("/");
                else
                    GAUtil.printWhitespaces(1);

                GAUtil.printWhitespaces(i + i - 1);

                if (nodes.get(j).getRightChild() != null)
                    System.out.print("\\");
                else
                    GAUtil.printWhitespaces(1);

                GAUtil.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(Node<T> node) {
        if (node == null)
            return 0;

        return Math.max(GAUtil.maxLevel(node.getLeftChild()),
                GAUtil.maxLevel(node.getRightChild())) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }


    private static BehaviourTree test(LifeForm lifeForm){
        BehaviourTreeNode hasEnergyNode = new hasEnergyNode();
        BehaviourTreeNode needsToGoToDenNode = new needsToGoToDenNode();
        BehaviourTreeNode moveToDenByPath = new moveToDenByPathNode();
        BehaviourTreeNode isHungryNode = new isHungryNode();
        BehaviourTreeNode canSmellFoodNode = new canSmellFoodNode();
        BehaviourTreeNode moveToFoodByPathNode = new moveToFoodByPathNode();
        BehaviourTreeNode moveRandomDirectionNode = new moveRandomDirectionNode();
        BehaviourTreeNode isThirstyNode = new isThirstyNode();
        BehaviourTreeNode canSeeWaterNode = new canSeeWaterNode();
        BehaviourTreeNode moveToWaterByPathNode = new moveToWaterByPathNode();

        hasEnergyNode.setRightChild(needsToGoToDenNode);
        hasEnergyNode.setLeftChild(needsToGoToDenNode);
        hasEnergyNode.getLeftChild().setLeftChild(moveToDenByPath);
        hasEnergyNode.getLeftChild().setRightChild(isHungryNode);
        hasEnergyNode.getLeftChild().getRightChild().setLeftChild(canSmellFoodNode);
        hasEnergyNode.getLeftChild().getRightChild().setRightChild(isThirstyNode);
        hasEnergyNode.getLeftChild().getRightChild().getLeftChild().setLeftChild(moveToFoodByPathNode);
        hasEnergyNode.getLeftChild().getRightChild().getLeftChild().setRightChild(moveRandomDirectionNode);
        hasEnergyNode.getLeftChild().getRightChild().getRightChild().setLeftChild(canSeeWaterNode);
        hasEnergyNode.getLeftChild().getRightChild().getRightChild().setRightChild(moveRandomDirectionNode);
        hasEnergyNode.getLeftChild().getRightChild().getRightChild().getLeftChild().setLeftChild(moveToWaterByPathNode);
        hasEnergyNode.getLeftChild().getRightChild().getRightChild().getLeftChild().setRightChild(moveRandomDirectionNode);


        BehaviourTree res = new BehaviourTree(lifeForm, hasEnergyNode);
        res.markUnreachableNodes();

        return res;
    }


}
