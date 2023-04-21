/*
 * AdministratorDashboardRepository.java
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

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	//	@Query("select count(t) from Tutorial t where t.nature =:nature and t.assistant.id =:id")
	//	Integer numTutorialByNature(Nature nature, int id);

	@Query("select avg(select sum(t.estimatedTime) from Tutorial t where t.assistant.id = a.id) from Assistant a ")
	Double averageTimeTutorialPerAssistant();
	//	@Query("select stddev(select sum(t.estimatedTime) from Tutorial t where t.assistant.id = a.id) from Assistant a")
	//Double desviationTimeTutorialPerAssistant();
	@Query("select max(select sum(t.estimatedTime) from Tutorial t where t.assistant.id = a.id) from Assistant a")
	Double maxTimeTutorialPerAssistant();
	@Query("select min(select sum(t.estimatedTime) from Tutorial t where t.assistant.id = a.id) from Assistant a")
	Double minTimeTutorialPerAssistant();
}
