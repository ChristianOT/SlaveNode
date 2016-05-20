package org.gradle.dataBaseRepositories;

import java.util.List;

import org.gradle.domain.Atom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface AtomRepository extends CrudRepository<Atom, String>{
	
	//List<Atom> findByGroupPDBx(String groupPDBx);
	//List<Atom> findByLabelCompId(String labelCompId);
	//List<Atom> findByChain(String chain);
}
