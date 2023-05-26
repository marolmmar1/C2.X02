
package acme.features.student.lecture;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l inner join CourseLecture cl on cl.lecture = l inner join Course c on  c = cl.course where cl.course.id = :courseId")
	List<Lecture> findAllLecturesByCourseId(Integer courseId);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findLectureById(int id);

}
