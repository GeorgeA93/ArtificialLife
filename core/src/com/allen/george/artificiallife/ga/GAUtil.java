package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.ga.Behaviour.BehaviourTree;
import com.allen.george.artificiallife.ga.Behaviour.BehaviourTreeNode;
import com.allen.george.artificiallife.ga.Behaviour.BehaviourTreeNodeType;
import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.util.ArrayList;

/**
 * Created by George on 09/10/2014.
 */
public class GAUtil {

    public static Chromosome generateInitialChromsome(int totalGenes){
        Chromosome res = null;
        Gene[] genes = new Gene[totalGenes];
        genes[0] = new Gene(5);
        for(int i = 1; i < totalGenes; i ++){
            genes[i] = new Gene((int)(Math.random() * ((9) + 1)));
        }
        res = new Chromosome(genes);
        return res;
    }

    public static BehaviourTree generateTreeFromGenes(Gene[] genes, LifeForm lifeForm){
        ArrayList<BehaviourTreeNode> nodes = new ArrayList<BehaviourTreeNode>();
        for(int i = 0; i < genes.length; i ++){
            nodes.add(BehaviourTreeNode.createFromGene(genes[i]));
        }

        int index = 1;
        for (int n = 0; n < Math.pow(2, 4 - 1) - 1;  n++){
            nodes.get(n).setLeftChild(nodes.get(index));
            index++;
            nodes.get(n).setRightChild(nodes.get(index));
            index++;
           System.out.println(nodes.get(n).getFunctionName());
           System.out.println(nodes.get(n).getLeftChild().getFunctionName());
           System.out.println(nodes.get(n).getRightChild().getFunctionName());
        }

        BehaviourTree res = new BehaviourTree(lifeForm, nodes.get(0));

        return res;
    }

}
