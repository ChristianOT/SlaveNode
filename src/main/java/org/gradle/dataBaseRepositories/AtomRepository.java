package org.gradle.dataBaseRepositories;

import java.util.List;

import org.gradle.domain.Atom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Repository Interfacer for the domain model {@link org.gradle.domain.Atom}
 * 
 * @author Christian Ouali Turki
 *
 */
@Service
public interface AtomRepository extends CrudRepository<Atom, String>{

}
