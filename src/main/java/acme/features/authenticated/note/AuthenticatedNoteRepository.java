/*
 * AuthenticatedAnnouncementRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.note;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.Note;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedNoteRepository extends AbstractRepository {

	@Query("select b from Note b where b.id = :id")
	Note findOneNoteById(int id);

	@Query("select b from Note b ")
	Collection<Note> findAllNote();

	@Query("select b from Note b where b.instantiation >= :oneMonthAgo")
	Collection<Note> findAllNoteLessThanAMonthOlder(@Param("oneMonthAgo") Date oneMonthAgo);

}
