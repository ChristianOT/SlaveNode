package org.gradle.dataBaseRepositories;

import java.util.List;

import org.gradle.domain.MolecularSystem;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface MolecularSystemRepository extends CrudRepository<MolecularSystem, Long>{

	//List<MolecularSystem> findByNameOfSystem(String nameOfSystem);

}
