package com.allen.george.artificiallife.data;

import com.allen.george.artificiallife.ga.ConditionNode;
import com.allen.george.artificiallife.ga.TerminalNode;
import com.allen.george.artificiallife.ga.Tree;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by George on 14/12/2014.
 */
public class GenerationReader implements Runnable{

    private Thread readingThread;
    private String filePath;
    private World world;

    private ArrayList<GenerationObject> generations = new ArrayList<GenerationObject>();



    public GenerationReader(String filePath, World world){
        this.filePath = filePath;
        this.world = world;
        this.readingThread = new Thread(this, "Generation Reader");
        readingThread.start();
    }

    private void readGenerations(){
        try{
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            //System.out.println("Root Element: " + doc.getDocumentElement().getNodeName());

            NodeList nodeList = doc.getElementsByTagName("Generation");

            for(int i = 0; i < nodeList.getLength(); i ++){
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //GENERATION
                    Element element = (Element)node;
                    int generationId = Integer.valueOf(element.getAttribute("id"));

                    //LIFE FORMS
                    NodeList lList = element.getElementsByTagName("LifeForm");
                    ArrayList<LifeForm> lifeForms = new ArrayList<LifeForm>();
                    for(int j = 0; j < lList.getLength(); j ++){
                        Node lNode = lList.item(j);
                        LifeForm lifeForm;
                        if (lNode.getNodeType() == Node.ELEMENT_NODE) {
                           Element lElement = (Element)lNode;

                           //Positions
                            int positionX = Integer.valueOf(lElement.getElementsByTagName("PositionX").item(0).getTextContent());
                            int positionY = Integer.valueOf(lElement.getElementsByTagName("PositionY").item(0).getTextContent());

                           //Fitness
                            double fitness = Double.valueOf(lElement.getElementsByTagName("Fitness").item(0).getTextContent());

                           //Tree
                           Node tree = lElement.getElementsByTagName("Tree").item(0);
                           Element tElement = (Element)tree;
                          //Root
                           Node root = tElement.getElementsByTagName("Root").item(0);
                           Element rElement = (Element)root;

                            //Populate Root
                            com.allen.george.artificiallife.ga.Node rootNode = null;
                            int rootFunction = Integer.valueOf(rElement.getAttribute("id"));
                            if (rootFunction <  com.allen.george.artificiallife.ga.Node.NUM_CONDITIONS){
                                rootNode = new ConditionNode(rootFunction);
                            }
                            else if (rootFunction <  com.allen.george.artificiallife.ga.Node.NUM_TERMINALS +  com.allen.george.artificiallife.ga.Node.NUM_ACTIONS +  com.allen.george.artificiallife.ga.Node.NUM_CONDITIONS){
                                rootFunction = rootFunction - com.allen.george.artificiallife.ga.Node.NUM_CONDITIONS;
                                rootNode = new TerminalNode(rootFunction);
                            }
                           createTree(rElement, rootNode);

                            Tree res = new Tree(rootNode, null);

                            lifeForm = new LifeForm(positionX, positionY, res ,fitness, world);

                            lifeForms.add(lifeForm);
                        }
                    }

                    generations.add(new GenerationObject(generationId, lifeForms));
                }
            }

            JOptionPane.showMessageDialog(null, "Simulation loaded with: " + generations.size() + " generations",
                    "Complete!", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e){
            e.printStackTrace();
            System.err.println("Error: could not read generations file");
        }
    }

    private void createTree(Element e, com.allen.george.artificiallife.ga.Node n){
        NodeList list = e.getChildNodes();
        for(int i = 0; i < list.getLength(); i ++){
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element ee = (Element)node;
                int func = Integer.valueOf(ee.getAttribute("id"));
                com.allen.george.artificiallife.ga.Node child = null;
                if(node.getNodeName().equals("LeftChild")){
                    if (func <  com.allen.george.artificiallife.ga.Node.NUM_CONDITIONS){
                        child = new ConditionNode(func);
                    }
                    else if (func <  com.allen.george.artificiallife.ga.Node.NUM_TERMINALS +  com.allen.george.artificiallife.ga.Node.NUM_ACTIONS +  com.allen.george.artificiallife.ga.Node.NUM_CONDITIONS){
                        func = func - com.allen.george.artificiallife.ga.Node.NUM_CONDITIONS;
                        child = new TerminalNode(func);
                    }
                    n.setLeftChild(child);
                }
                if(node.getNodeName().equals("RightChild")){
                    if (func <  com.allen.george.artificiallife.ga.Node.NUM_CONDITIONS){
                        child = new ConditionNode(func);
                    }
                    else if (func <  com.allen.george.artificiallife.ga.Node.NUM_TERMINALS +  com.allen.george.artificiallife.ga.Node.NUM_ACTIONS +  com.allen.george.artificiallife.ga.Node.NUM_CONDITIONS){
                        func = func - com.allen.george.artificiallife.ga.Node.NUM_CONDITIONS;
                        child = new TerminalNode(func);
                    }
                    n.setRightChild(child);
                }
                createTree(ee, child);
            }
        }


    }


    public void run(){
        readGenerations();
    }

    public ArrayList<GenerationObject> getGenerations() {
        return generations;
    }

    public void setGenerations(ArrayList<GenerationObject> generations) {
        this.generations = generations;
    }
}
