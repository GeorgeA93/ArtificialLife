package com.allen.george.geneticx.crossover;


import com.allen.george.artificiallife.ga.Node;
import com.allen.george.artificiallife.ga.TerminalNode;
import com.allen.george.artificiallife.ga.Tree;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.utils.SimulationSettings;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by George on 01/12/2014.
 */
public class TreeCrossoverMethod extends CrossoverMethod {


    @Override
    public ArrayList<LifeForm> crossOverParents(LifeForm a, LifeForm b) {
        ArrayList<LifeForm> children = new ArrayList<LifeForm>();

        Tree treeOne = Tree.copy(a.getTree());
        Tree treeTwo = Tree.copy(b.getTree());

        if(areTreesTheSame(treeOne, treeTwo)){
            treeOne.getRoot().mutate(new Random());
        } else {
            if (treeOne.getDepth() != 1 && treeTwo.getDepth() != 1) {
                crossover(treeOne, treeTwo);
            }

            if (treeOne.getDepth() > SimulationSettings.MAX_TREE_DEPTH || treeOne.getDepth() <= 2) {
                treeOne = getCopyOfRandomParent(a.getTree(), b.getTree());
            }

            if (treeTwo.getDepth() > SimulationSettings.MAX_TREE_DEPTH || treeTwo.getDepth() <= 2) {
                treeTwo = getCopyOfRandomParent(a.getTree(), b.getTree());
            }
        }

        a.setTree(treeOne);
        b.setTree(treeTwo);
        children.add(a);
        children.add(b);

        return children;
    }


    private boolean areTreesTheSame(Tree treeOne, Tree treeTwo){
        int count = 0;
        ArrayList<Node> p1Nodes = treeOne.getNodes();
        ArrayList<Node> p2Nodes = treeTwo.getNodes();
        if(p1Nodes.size() != p2Nodes.size()){
            return false;
        } else {
            for(int i = 0; i < p1Nodes.size(); i ++){
                Node node1 = p1Nodes.get(i);
                Node node2 = p2Nodes.get(i);
                if(node1.getFunction() == node2.getFunction()){
                    count ++;
                }
            }
            if(count == p1Nodes.size()){
                return true;
            } else {
                return false;
            }
        }
    }

    private static Tree getCopyOfRandomParent(Tree treeOne, Tree treeTwo) {
        double probability = Math.random();

        if (probability < 0.5) {
            return Tree.copy(treeOne);
        }
        else {
            return Tree.copy(treeTwo);
        }
    }

    public static void crossover(Tree t1, Tree t2){
        ArrayList<Node> p1Nodes = t1.getNodes();
        ArrayList<Node> p2Nodes = t2.getNodes();

        Random random = new Random();
        int indexp1 = 0, indexp2 = 0;
        boolean rootNodes = true;
        while (rootNodes) {
            indexp1 = random.nextInt(p1Nodes.size());
            indexp2 = random.nextInt(p2Nodes.size());

            rootNodes = areRootNodes(p1Nodes, p2Nodes, indexp1, indexp2);
        }


        Node n1 = p1Nodes.get(indexp1);
        Node n2 = p2Nodes.get(indexp2);

        Tree.swap(t1, t2, n1, n2);
    }

    public static boolean areRootNodes(ArrayList<Node> p1Nodes,ArrayList<Node> p2Nodes, int indexp1, int indexp2) {
        boolean areRootNodes = true;

        if ((p1Nodes.get(indexp1).getParent() != null) || p2Nodes.get(indexp2).getParent() != null) {
            if(!(p1Nodes.get(indexp1) instanceof TerminalNode) && !(p2Nodes.get(indexp2) instanceof TerminalNode)){
                areRootNodes = false;
            }
        }

        return areRootNodes;
    }


}
