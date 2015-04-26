package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;

public class StatusNode extends Node { //TERMINAL

	public StatusNode(int function) {
		this.setFunction(function);
		this.setNodeType(NodeType.STATUS);
		this.setNumberOfChildren(0);
	//	this.setChildren(new Node[] { null, null });
	}

    public boolean runFunction(LifeForm lifeForm){
        return true;
    }

	@Override
	public Node copy() {
		Node copy = new StatusNode(getFunction());
		//copy.setLeftChild(null);
		//copy.setRightChild(null);
		//copy.setChildren(new Node[0]);

		return copy;
	}

}
