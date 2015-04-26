package com.allen.george.artificiallife.ga;


import com.allen.george.artificiallife.simulation.life.LifeForm;

public class FourChildLogicalNode extends Node{
	
	public FourChildLogicalNode(int function) {
		this.setFunction(function);
		this.setNodeType(NodeType.FOUR_CHILD_LOGIC);
		this.setNumberOfChildren(4);
		//this.setChildren(new Node[4]);
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
