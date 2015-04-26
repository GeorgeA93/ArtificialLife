package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;

public class ThreeChildLogicalNode extends Node{
	
	public ThreeChildLogicalNode(int function) {
		this.setFunction(function);
		this.setNodeType(NodeType.THREE_CHILD_LOGIC);
		this.setNumberOfChildren(3);
		//this.setChildren(new Node[3]);
	}

    public boolean runFunction(LifeForm lifeForm){
        return true;
    }

	@Override
	public Node copy() {
		Node copy = new FourChildLogicalNode(getFunction());

		// copy.setChildren(this.getChildren());

		return copy;
	}

}
