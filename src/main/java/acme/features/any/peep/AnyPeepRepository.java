
package acme.features.any.peep;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Peep;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPeepRepository extends AbstractRepository {

	@Query("SELECT p FROM Peep p")
	List<Peep> findAllPeeps();

	@Query("SELECT p FROM Peep p WHERE p.id = :id")
	Peep findOnePeepById(int id);

	@Query("select ua from UserAccount ua where ua.id = :idPrincipal")
	UserAccount findUserAccountById(int idPrincipal);

}
