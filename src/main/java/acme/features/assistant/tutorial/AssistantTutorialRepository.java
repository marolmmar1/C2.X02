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

package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id= :id")
	Tutorial findOneTutorialById(int id);

	@Query("select a from Assistant a where a.id= :id")
	Assistant findOneAssistantById(int id);

	@Query("select c from Course c where c.id= :id")
	Course findOneCourseById(int id);

	@Query("select t from Tutorial t where t.assistant.id = :id")
	Collection<Tutorial> findAllTutorialByAssistantId(int id);

	@Query("select ts from TutorialSession ts where ts.tutorial.id = :id")
	Collection<TutorialSession> findManyTutorialSessionsByTutorialId(int id);

	@Query("select t from Tutorial t where t.code = :code")
	Tutorial findOneTutorialByCode(String code);

	@Query("select c from Course c where c.draftMode =:bo")
	Collection<Course> findAllCourse(boolean bo);

}
