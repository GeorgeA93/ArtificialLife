package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.utils.SimulationSettings;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by George on 01/12/2014.
 */
public class HolderForGATest {

    public static ArrayList<LifeForm> selection(ArrayList<LifeForm> population){
        ArrayList<LifeForm> newPopulation = new ArrayList<LifeForm>(100);

        int numberOfSurvivors = getNumberOfSurvivors();

        int index = 0;
        while (index < numberOfSurvivors) {

            newPopulation.add(population.get(index));

            ++index;
        }

        return newPopulation;
    }

    private static int getNumberOfSurvivors() {
        double survivalProbability = 0.2;
        double populationSize = 100;

        int numberOfSurvivors = (int) Math.ceil(survivalProbability * populationSize);

        return numberOfSurvivors;
    }

    public static ArrayList<LifeForm> crossoverTrees(LifeForm[] parentsA, LifeForm[] parentsB, ArrayList<LifeForm> population)  {
        ArrayList<LifeForm> children = new ArrayList<LifeForm>();
       // int numOfPairsForCrossover = (int) Math.floor((0.8 * population.size())/2);;

        Random randomGenerator = new Random();

        for (int i = 0; i < parentsA.length; ++i) {

            LifeForm lifeForm1 = parentsA[i];
            LifeForm lifeForm2 = parentsB[i];
            Tree treeOne = Tree.copy(lifeForm1.getTest());
            Tree treeTwo = Tree.copy(lifeForm2.getTest());

         //   System.out.println("ORIGINAL TREE ONE");
         //   GAUtil.printNode(treeOne.getRoot());
          //  System.out.println("ORIGINAL TREE TWO");
          //  GAUtil.printNode(treeTwo.getRoot());

            if (treeOne.getDepth() != 1 && treeTwo.getDepth() != 1) {
                crossover(treeOne, treeTwo);
            }

            if (treeOne.getDepth() > 98 || treeOne.getDepth() <= 2) { //MAYBE REMOVE THE LESS THAN PART
               treeOne = getCopyOfRandomParent(lifeForm1.getTest(), lifeForm2.getTest());
            }

            if (treeTwo.getDepth() > 98 || treeTwo.getDepth() <= 2) {
               treeTwo = getCopyOfRandomParent(lifeForm1.getTest(), lifeForm2.getTest());
            }

            //System.out.println("CROSSED OVER TREE ONE");
           // GAUtil.printNode(treeOne.getRoot());
            //System.out.println("CROSSED OVER TREE TWO");
           // GAUtil.printNode(treeTwo.getRoot());

            lifeForm1.setTest(treeOne);
            lifeForm2.setTest(treeTwo);
            lifeForm1.setChromosome(Tree.convertToChromosome(treeOne));
            lifeForm2.setChromosome(Tree.convertToChromosome(treeTwo));
            children.add(lifeForm1);
            children.add(lifeForm2);
        }

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

    public static void mutateTrees(ArrayList<LifeForm> lifeForms){

        int size = lifeForms.size();
        if(size < 0) return;

        Random random = new Random();
        int mutateNum = (int) Math.ceil(SimulationSettings.MUTATION_RATE * size);

        System.out.println(mutateNum);

        for(int i = 0; i < mutateNum; i ++){
          //  int il = random.nextInt(size);
           // Tree tree = lifeForms.get(il).getTest();

           // mutate(tree);

            int il = random.nextInt(size);

            Tree t = lifeForms.get(il).getTest();

            ArrayList<Node> nodes = t.getConditionNodes();

            int i2 = random.nextInt(nodes.size());

            nodes.set(i2, nodes.get(i2).mutate(random));
        }

    }

    public static void mutate(Tree tree){
        if(tree.getDepth() > 1){
            ArrayList<Node> conditionNodes = tree.getConditionNodes();
            ArrayList<Node> terminalNodes = tree.getTerminalNodes();

            double random = Math.random();
            if(random <= 0.5){
                mutateConditionNode(conditionNodes);
            } else {
                mutateTerminalNode(terminalNodes);
            }
        } else {
            ArrayList<Node> terminalNodes = tree.getTerminalNodes();
            mutateTerminalNode(terminalNodes);
        }
    }

    public static void mutateTerminalNode(ArrayList<Node> nodes) {
       // if(nodes.size() <= 0 )return;
        Random random = new Random();

        int nodeIndex = random.nextInt(nodes.size());

        Node mutationNode = nodes.get(nodeIndex);

        Node node = null;
        do {
            node = new TerminalNode(random.nextInt(3)); //4
        } while (node.getFunction() == mutationNode.getFunction());

        mutationNode.setFunction(node.getFunction());
    }

    public static void mutateConditionNode(ArrayList<Node> nodes) {
        Random random = new Random();
        int nodeIndex = random.nextInt(nodes.size());

        Node mutationNode = nodes.get(nodeIndex);

        Node node = null;
        do {
            node = new ConditionNode(random.nextInt(4)); // 6
        } while (node.getFunction() == mutationNode.getFunction());

        mutationNode.setFunction(node.getFunction());
    }

}
