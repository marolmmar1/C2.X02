/*
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.assistant.dashboard;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Tutorial;
import acme.entities.TutorialSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select t from Tutorial t")
	List<Tutorial> findAllTutorials();

	@Query("select t from Tutorial t where t.assistant.userAccount.id = :id and t.draftMode = false")
	List<Tutorial> findTutorialsByAssistantId(int id);

	@Query("select t from Tutorial t where t.code = :code")
	Tutorial findTutorialByCode(String code);

	@Query("select s from TutorialSession s where s.id = :id")
	TutorialSession findTutorialSessionById(int id);

	@Query("select s from TutorialSession s where s.tutorial.draftMode = false")
	List<TutorialSession> findAllTutorialSessions();

	@Query("select s from TutorialSession s where s.tutorial.assistant.userAccount.id = :id and s.tutorial.draftMode = false")
	List<TutorialSession> findTutorialSessionsByAssistantId(int id);

	@Query("select s from TutorialSession s where s.tutorial.id = :id")
	List<TutorialSession> findTutorialSessionsByTutorialId(int id);

	@Query("select a from Assistant a where a.id = :id")
	Assistant findAssistantById(int id);

	@Query("select a from Assistant a where a.userAccount.id = :id")
	Assistant findAssistantByUserAccountId(int id);

	@Query("select c from Course c")
	List<Course> findAllCourses();

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	// ######################################
	@Query("select count(t) FROM Tutorial t where t.assistant.userAccount.id = :id and t.draftMode = false")
	Integer findNumberOfTutorialsByAssistantId(int id);

	@Query("select count(s) FROM TutorialSession s where s.tutorial.assistant.userAccount.id = :id and s.tutorial.draftMode = false")
	Integer findNumberOfTutorialSessionsByAssistantId(int id);

	@Query("select t.estimatedTime FROM Tutorial t where t.assistant.userAccount.id = :id and t.draftMode = false")
	List<Double> findAllTutorialTimesByAssistantId(int id);

	default List<Double> findTutorialTimesByAssistantId(final int id) {
		final List<Tutorial> tutorials = this.findTutorialsByAssistantId(id);

		return tutorials.stream().mapToDouble(t -> this.findTutorialSessionsByTutorialId(t.getId()).stream().mapToDouble(s -> TimeUnit.MILLISECONDS.toHours(s.getFinalPeriod().getTime() - s.getInicialPeriod().getTime())).sum()).boxed()
			.collect(Collectors.toList());
	}

	default Double findAverageTutorialTimeByAssistantId(final int id) {
		return this.findTutorialTimesByAssistantId(id).stream().mapToDouble(x -> x).average().orElse(0.0);
	}

	default Double findDeviationTutorialTimeByAssistantId(final int id) {
		final double avg = this.findAverageTutorialTimeByAssistantId(id);
		return Math.sqrt(this.findTutorialTimesByAssistantId(id).stream().mapToDouble(x -> x).map(x -> x - avg).map(x -> x * x).average().orElse(0.0));
	}

	default Double findMaxTutorialTimeByAssistantId(final int id) {
		return this.findTutorialTimesByAssistantId(id).stream().mapToDouble(x -> x).max().orElse(0.0);
	}

	default Double findMinTutorialTimeByAssistantId(final int id) {
		return this.findTutorialTimesByAssistantId(id).stream().mapToDouble(x -> x).min().orElse(0.0);
	}

	default Double findAverageTutorialSessionTimeByAssistantId(final int id) {
		final List<TutorialSession> sessions = this.findTutorialSessionsByAssistantId(id);
		return sessions.stream().mapToDouble(s -> TimeUnit.MILLISECONDS.toHours(s.getFinalPeriod().getTime() - s.getInicialPeriod().getTime())).average().orElse(0.0);
	}

	default Double findMaxTutorialSessionTimeByAssistantId(final int id) {
		final List<TutorialSession> sessions = this.findTutorialSessionsByAssistantId(id);
		return sessions.stream().mapToDouble(s -> TimeUnit.MILLISECONDS.toHours(s.getFinalPeriod().getTime() - s.getInicialPeriod().getTime())).max().orElse(0.0);
	}

	default Double findMinTutorialSessionTimeByAssistantId(final int id) {
		final List<TutorialSession> sessions = this.findTutorialSessionsByAssistantId(id);
		return sessions.stream().mapToDouble(s -> TimeUnit.MILLISECONDS.toHours(s.getFinalPeriod().getTime() - s.getInicialPeriod().getTime())).min().orElse(0.0);
	}

	default Double findDeviationTutorialSessionTimeByAssistantId(final int id) {
		final List<TutorialSession> sessions = this.findTutorialSessionsByAssistantId(id);
		final double avg = this.findAverageTutorialSessionTimeByAssistantId(id);
		return Math.sqrt(sessions.stream().mapToDouble(s -> TimeUnit.MILLISECONDS.toHours(s.getFinalPeriod().getTime() - s.getInicialPeriod().getTime())).map(x -> x - avg).map(x -> x * x).average().orElse(0.0));
	}

}
