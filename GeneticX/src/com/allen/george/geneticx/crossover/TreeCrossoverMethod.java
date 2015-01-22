package com.allen.george.geneticx.crossover;

import com.allen.george.artificiallife.ga.ConditionNode;
import com.allen.george.artificiallife.ga.Node;
import com.allen.george.artificiallife.ga.TerminalNode;
import com.allen.george.artificiallife.ga.Tree;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.geneticx.Chromosome;
import com.allen.george.geneticx.Gene;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by George on 01/12/2014.
 */
public class TreeCrossoverMethod extends CrossoverMethod {


    @Override
    public ArrayList<LifeForm> crossOverParents(LifeForm a, LifeForm b) {
        ArrayList<LifeForm> children = new ArrayList<LifeForm>();

        Tree treeOne = Tree.copy(a.getTest());
        Tree treeTwo = Tree.copy(b.getTest());

        if (treeOne.getDepth() != 1 && treeTwo.getDepth() != 1) {
            crossover(treeOne, treeTwo);
        }

        if (treeOne.getDepth() > 98 || treeOne.getDepth() <= 2) { //MAYBE REMOVE THE LESS THAN PART
            treeOne = getCopyOfRandomParent(a.getTest(), b.getTest());
        }

        if (treeTwo.getDepth() > 98 || treeTwo.getDepth() <= 2) {
            treeTwo = getCopyOfRandomParent(a.getTest(), b.getTest());
        }

        a.setTest(treeOne);
        b.setTest(treeTwo);
      //  lifeForm1.setChromosome(Tree.convertToChromosome(treeOne));
       // lifeForm2.setChromosome(Tree.convertToChromosome(treeTwo));
        children.add(a);
        children.add(b);

        return children;
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
