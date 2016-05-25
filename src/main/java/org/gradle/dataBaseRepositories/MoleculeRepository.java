package org.gradle.dataBaseRepositories;

import org.gradle.domain.Molecule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Repository Interfacer for the domain model {@link org.gradle.domain.Molecule}
 * 
 * @author Christian Ouali Turki
 *
 */
@Service
public interface MoleculeRepository extends CrudRepository<Molecule, String>{

}
