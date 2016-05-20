package org.gradle.domain;

import java.util.HashSet;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


@NodeEntity
public class Molecule {

	@GraphId
	private Long id;
	
	private String fileName;
	public Integer atomCount = 0;
	
	@Relationship(type="ATOM",direction=Relationship.UNDIRECTED)//@RelatedTo(type="ATOM", direction=Direction.BOTH)
	public /*@Fetch */HashSet<Atom> atoms = new HashSet<Atom>();

	public Integer getAtomCount() {
		return atomCount;
	}

	public void setAtomCount(Integer atomCount) {
		this.atomCount = atomCount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public HashSet<Atom> getAtoms() {
		return atoms;
	}

	public void setAtoms(HashSet<Atom> atoms) {
		this.atoms = atoms;
	}

	@Override
	public String toString() {
		return "Molecule [atomCount=" + atomCount + ", fileName=" + fileName + ", atoms=" + atoms + "]";
	}

}
