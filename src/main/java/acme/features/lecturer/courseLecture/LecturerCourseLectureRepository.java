
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.CourseLecture;
import acme.entities.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseLectureRepository extends AbstractRepository {

	@Query("select cl from CourseLecture cl where cl.id = :id")
	CourseLecture findOneLectureCourseById(int id);

	@Query("select l from Lecturer l where l.id = :id")
	Lecturer findOneLecturerById(int id);

	@Query("select c from Course c where c.lecturer = :lecturer")
	Collection<Course> findManyCoursesByLecturer(Lecturer lecturer);

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select l from Lecture l inner join CourseLecture cl on l = cl.lecture inner join Course c on cl.course = c where c.id = :masterId")
	Collection<Lecture> findManyLecturesByMasterId(int masterId);

	@Query("select cl from CourseLecture cl where cl.lecture = :lecture and cl.course = :course")
	CourseLecture findOneCourseLectureByLectureAndCourse(Lecture lecture, Course course);
}
