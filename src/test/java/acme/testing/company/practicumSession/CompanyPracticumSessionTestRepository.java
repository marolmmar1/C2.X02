
package acme.testing.company.practicumSession;
/*
 * 
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Practicum;
import acme.entities.PracticumSession;
import acme.framework.repositories.AbstractRepository;

public interface CompanyPracticumSessionTestRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.company.userAccount.username = :username")
	Collection<Practicum> findManyPracticumsByCompanyUsername(String username);

	@Query("select ps from PracticumSession ps where ps.practicum.company.userAccount.username = :username")
	Collection<PracticumSession> findManyPracticumSessionByCompanyUsername(String username);

}
