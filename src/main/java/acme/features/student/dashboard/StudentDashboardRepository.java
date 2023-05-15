/*
 * AuthenticatedConsumerController.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentDashboardRepository extends AbstractRepository {

	@Query("select count(a) from Activity a where a.activityType = 'THEORY'")
	Integer totalNumOfTheoryActivities();

	@Query("select count(a) from Activity a where a.activityType = 'HANDS_ON'")
	Integer totalNumOfHandsonActivities();

	@Query("select avg(TIME_TO_SEC(TIMEDIFF(a.startTimePeriod, a.endTimePeriod)) / 3600) from Activity a where a.enrolment.student.id = :id")
	Double averageTimeOfActivityWorkbook(int id);

	@Query("select stddev(TIME_TO_SEC(TIMEDIFF(a.startTimePeriod, a.endTimePeriod)) / 3600) from Activity a where a.enrolment.student.id = :id")
	Double deviationTimeOfActivityWorkbook(int id);

	@Query("select min(TIME_TO_SEC(TIMEDIFF(a.startTimePeriod, a.endTimePeriod)) / 3600) from Activity a where a.enrolment.student.id = :id")
	Double minimumTimeOfActivityWorkbook(int id);

	@Query("select max(TIME_TO_SEC(TIMEDIFF(a.startTimePeriod, a.endTimePeriod)) / 3600) from Activity a where a.enrolment.student.id = :id")
	Double maximumTimeOfActivityWorkbook(int id);

	@Query("select avg(select sum(l.estimatedTime) from CourseOfLecture cl join cl.lecture l where cl.course=c) from Enrolment e join e.course c where e.student.id= :id")
	Double averageestimatedTimeAOfCoursesEnrolled(int id);

	@Query("select stddev((select sum(l.estimatedTime) from CourseOfLecture cl join cl.lecture l where cl.course=c)) from Enrolment e join e.course c where e.student.id= :id")
	Double deviationestimatedTimeAOfCoursesEnrolled(int id);

	@Query("select min(select sum(l.estimatedTime) from CourseOfLecture cl join cl.lecture l where cl.course=c) from Enrolment e join e.course c where e.student.id= :id")
	Double minimumestimatedTimeAOfCoursesEnrolled(int id);

	@Query("select max(select sum(l.estimatedTime) from CourseOfLecture cl join cl.lecture l where cl.course=c) from Enrolment e join e.course c where e.student.id= :id")
	Double maximumestimatedTimeAOfCoursesEnrolled(int id);
}