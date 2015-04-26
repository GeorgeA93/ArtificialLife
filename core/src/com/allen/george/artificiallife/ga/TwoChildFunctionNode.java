package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;

public class TwoChildFunctionNode extends Node{ //NON TERMINAL
	
	public TwoChildFunctionNode(int function){
        this.setFunction(function);
        this.setNodeType(NodeType.TWO_CHILD_FUNCTION);
        this.setNumberOfChildren(2);
       // this.setChildren(new Node[2]);
    }

    public boolean runFunction(LifeForm lifeForm){
        return true;
    }
	
	public Node copy() {
        Node copy = new TwoChildFunctionNode(getFunction());

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
