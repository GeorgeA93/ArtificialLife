package com.allen.george.artificiallife.data;

import com.allen.george.artificiallife.ga.Node;
import com.allen.george.artificiallife.ga.Tree;
import com.allen.george.artificiallife.simulation.life.LifeForm;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by George on 14/12/2014.
 */
public class GenerationWriter implements Runnable{

    private Thread writingThread;
    private ArrayList<LifeForm> lifeForms;
    private int generation;
    private String filePath = "";

    public GenerationWriter(){
        //this.lifeForms = lifeForms;
       // this.generation = generation;
        //this.filePath = filePath;
       // this.start();
    }

    public void start(ArrayList<LifeForm> lifeForms, int generation, String filePath){
        this.lifeForms = lifeForms;
        this.generation = generation;
        this.filePath = filePath;
        this.writingThread = new Thread(this, "Generation Writer");
        this.writingThread.start();
    }

    private void cleanUp(){
        this.lifeForms = null;
        this.writingThread = null;
    }

    private void addNewGeneration(File file){

        try{

            DocumentBuilderFactory docFactory =DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            org.w3c.dom.Node simulation = doc.getElementsByTagName("Simulation").item(0);

            //generation
            Element gen = doc.createElement("Generation");
            simulation.appendChild(gen);
            Attr attr = doc.createAttribute("id");
            attr.setValue(String.valueOf(this.generation));
            gen.setAttributeNode(attr);

            //Lifeforms
            int count = 0;
            for(LifeForm lifeForm : lifeForms){
                Element lf = doc.createElement("LifeForm");
                gen.appendChild(lf);
                Attr a = doc.createAttribute("id");
                a.setValue(String.valueOf(count));
                lf.setAttributeNode(a);

                //positions
                Element positionX = doc.createElement("PositionX");
                positionX.appendChild(doc.createTextNode(String.valueOf(lifeForm.positionX)));
                lf.appendChild(positionX);
                Element positionY = doc.createElement("PositionY");
                positionY.appendChild(doc.createTextNode(String.valueOf(lifeForm.positionY)));
                lf.appendChild(positionY);

                //fitness
                Element fitness = doc.createElement("Fitness");
                fitness.appendChild(doc.createTextNode(String.valueOf(lifeForm.getFitness())));
                lf.appendChild(fitness);

                //tree start
                Element treeStart = doc.createElement("Tree");
                lf.appendChild(treeStart);

                //tree contents
                Tree lfTree = lifeForm.getTest();
                Node root = lfTree.getRoot();
                writeTree(root, "Root", treeStart, doc);

                count ++;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 4);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

        } catch (Exception e){
            e.printStackTrace();
            System.err.println("Error: Could not write Generation to file");
        }
        this.cleanUp();
    }

    private void writeDataNew(){
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Simulation");
            doc.appendChild(rootElement);

            //generation
            Element gen = doc.createElement("Generation");
            rootElement.appendChild(gen);
            Attr attr = doc.createAttribute("id");
            attr.setValue(String.valueOf(this.generation));
            gen.setAttributeNode(attr);

            //Lifeforms
            int count = 0;
            for(LifeForm lifeForm : lifeForms){
                Element lf = doc.createElement("LifeForm");
                gen.appendChild(lf);
                Attr a = doc.createAttribute("id");
                a.setValue(String.valueOf(count));
                lf.setAttributeNode(a);

                //positions
                Element positionX = doc.createElement("PositionX");
                positionX.appendChild(doc.createTextNode(String.valueOf(lifeForm.positionX)));
                lf.appendChild(positionX);
                Element positionY = doc.createElement("PositionY");
                positionY.appendChild(doc.createTextNode(String.valueOf(lifeForm.positionY)));
                lf.appendChild(positionY);

                //fitness
                Element fitness = doc.createElement("Fitness");
                fitness.appendChild(doc.createTextNode(String.valueOf(lifeForm.getFitness())));
                lf.appendChild(fitness);

                //tree start
                Element treeStart = doc.createElement("Tree");
                lf.appendChild(treeStart);

                //tree contents
                Tree lfTree = lifeForm.getTest();
                Node root = lfTree.getRoot();
                writeTree(root, "Root", treeStart, doc);

                count ++;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 4);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));

            transformer.transform(source, result);

        } catch (Exception e){
            e.printStackTrace();
            System.err.println("Error: Could not write Generation to file");
        }
        this.cleanUp();
    }

    private void writeTree(Node node, String text, Element parent, Document doc){
        Element treeNode = doc.createElement(text);
        parent.appendChild(treeNode);

        Attr attr = doc.createAttribute("id");
        attr.setValue(String.valueOf(node.getFunctionForXML()));
        treeNode.setAttributeNode(attr);

        if(node.getLeftChild() != null){
            writeTree(node.getLeftChild(), "LeftChild", treeNode, doc);
        }
        if(node.getRightChild() != null){
            writeTree(node.getRightChild(), "RightChild", treeNode, doc);
        }
    }

    public void run(){
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()){
            this.addNewGeneration(f);
        } else {
            this.writeDataNew();
        }
    }



}
