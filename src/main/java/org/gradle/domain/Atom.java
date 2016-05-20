package org.gradle.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Atom {

	@GraphId
	private Long id;
	
	
	private String index;
	
	private String element;

	
	private String groupPDBx;
	
	private String labelCompId;
	
	private String chain;

	
	private Double x;
	
	private Double y;
	
	private Double z;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getGroupPDBx() {
		return groupPDBx;
	}

	public void setGroupPDBx(String groupPDBx) {
		this.groupPDBx = groupPDBx;
	}

	public String getLabelCompId() {
		return labelCompId;
	}

	public void setLabelCompId(String labelCompId) {
		this.labelCompId = labelCompId;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getZ() {
		return z;
	}

	public void setZ(Double z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Atom [index=" + index + ", element=" + element + ", groupPDBx=" + groupPDBx + ", labelCompId="
				+ labelCompId + ", chain=" + chain + ", x=" + x + ", y=" + y + ", z=" + z + "]";
	}

}
