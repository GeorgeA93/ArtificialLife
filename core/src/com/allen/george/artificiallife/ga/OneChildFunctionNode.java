package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;

public class OneChildFunctionNode extends Node{
	
	public OneChildFunctionNode(int function){
        this.setFunction(function);
        this.setNodeType(NodeType.ONE_CHILD_FUNCTION);
        this.setNumberOfChildren(1);
       // this.setChildren(new Node[1]);
    }

    public boolean runFunction(LifeForm lifeForm){
        return true;
    }
	
	public Node copy() {
        Node copy = new OneChildFunctionNode(getFunction());

        /*
        if(getLeftChild() != null){
            copy.setLeftChild(this.getLeftChild().copy());
        } else {
            copy.setLeftChild(null);
        }
        if(getRightChild() != null){
            copy.setRightChild(this.getRightChild().copy());
        } else {
            copy.setRightChild(null);
        }
        */
       // copy.setChildren(this.getChildren());



        return copy;
    }

}
