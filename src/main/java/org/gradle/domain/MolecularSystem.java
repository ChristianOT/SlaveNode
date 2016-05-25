package org.gradle.domain;

import java.util.HashSet;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * This domain model is adjusted the molecular system of a pdbml file. Is set as
 * a neo4j entity.
 * 
 * @author Christian Ouali Turki
 *
 */
@NodeEntity
public class MolecularSystem {

	@GraphId
	private Long id;

	private String nameOfSystem;
	public Integer numberOfMolecules;
	public Integer numberOfAtoms;

	/*
	 * Set of molecules. @Relationship enables the relation between two domain models.
	 */
	@Relationship(type = "MOLECULE", direction = Relationship.UNDIRECTED)
	public HashSet<Molecule> molecules = new HashSet<Molecule>();

	public String getNameOfSystem() {
		return nameOfSystem;
	}

	public void setNameOfSystem(String nameOfSystem) {
		this.nameOfSystem = nameOfSystem;
	}

	public Integer getNumberOfMolecules() {
		return numberOfMolecules;
	}

	public void setNumberOfMolecules(Integer numberOfMolecules) {
		this.numberOfMolecules = numberOfMolecules;
	}

	public Integer getNumberOfAtoms() {
		return numberOfAtoms;
	}

	public void setNumberOfAtoms(Integer numberOfAtoms) {
		this.numberOfAtoms = numberOfAtoms;
	}

	public HashSet<Molecule> getMolecules() {
		return molecules;
	}

	public void setMolecules(HashSet<Molecule> molecules) {
		this.molecules = molecules;
	}

	@Override
	public String toString() {
		return "MolecularSystem [nameOfSystem=" + nameOfSystem + ", numberOfMolecules=" + numberOfMolecules
				+ ", numberOfAtoms=" + numberOfAtoms + ", molecules=" + molecules + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
