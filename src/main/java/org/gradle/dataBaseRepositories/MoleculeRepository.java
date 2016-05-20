package org.gradle.dataBaseRepositories;

import java.util.List;

import org.gradle.domain.Molecule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface MoleculeRepository extends CrudRepository<Molecule, String>{

	//List<Molecule> findByFileName(String string);

}
