
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.Practicum;
import acme.entities.PracticumSessions;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.company.id =: companyId")
	Collection<Practicum> findPracticumByCompany(int companyId);

	@Query("select p from Practicum p where p.id =: practicumId")
	Practicum findPracticumById(int practicumId);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select ps from PracticumSessions ps where ps.practicum.id =: id")
	Collection<PracticumSessions> findPracticumSessionsByPracticumId(int id);

	@Query("select c from Company c where c.id =: id")
	Company findCompanyById(int id);

	@Query("select c from Course c where c.id =: id")
	Course findCourseById(int id);

	@Query("select p from Practicum p where p.code = :code")
	Practicum findPracticumByCode(String code);

}
