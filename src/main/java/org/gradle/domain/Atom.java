package org.gradle.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * This domain model is adjusted for an atom of a pdbml file. Is set as a neo4j
 * entity.
 * 
 * @author Christian Ouali Turki
 *
 */
@NodeEntity
public class Atom {

	@GraphId
	private Long id;

	private String element;

	private Double x;

	private Double y;

	private Double z;

	/*
	 * the atom id in the pdbml file
	 */
	private String index;

	/*
	 * either ATOM (belongs to protein) or HETATM (ligand or solvent)
	 */
	private String groupPDBx;

	/*
	 * label of the component (e.g. amino acid or HOH)
	 */
	private String labelCompId;

	/*
	 * multiple chains can occur in a protein
	 */
	private String chain;

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
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

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
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

	@Override
	public String toString() {
		return "Atom [index=" + index + ", element=" + element + ", groupPDBx=" + groupPDBx + ", labelCompId="
				+ labelCompId + ", chain=" + chain + ", x=" + x + ", y=" + y + ", z=" + z + "]";
	}

}
