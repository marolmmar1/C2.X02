/*
 * WorkerJobRepository.java
 * 
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 * 
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.lecturer.lecture;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.CourseLecture;
import acme.entities.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select c from Course c where c.code = :code")
	Optional<Course> findCourseByCode(String code);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findLecturerById(int id);

	@Query("select cl from CourseLecture cl where cl.lecture.id =: lectureId")
	CourseLecture findCourseLectureByLectureId(int lectureId);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select l from Lecturer l")
	Collection<Lecturer> findAllLecturers();

	@Query("select cl from CourseLecture cl where cl.course.id = :id")
	Collection<CourseLecture> findManyCourseLectureByCourseId(int id);

	@Query("SELECT cl.lecture.nature, COUNT(cl.lecture) FROM CourseLecture cl WHERE cl.course.id = :id GROUP BY cl.lecture.nature")
	Collection<Tuple> countLecturesGroupByType(int id);
}
