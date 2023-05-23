package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentFinaliseTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepositoryTest repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalise-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentIndex, final String code) {

		super.signIn("student5", "student5");

		super.clickOnMenu("Enrolmentr", "List of enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(enrolmentIndex, 0, code);

		super.clickOnListingRecord(enrolmentIndex);
		super.checkFormExists();
		super.clickOnSubmit("Finalise");
		super.checkNotErrorsExist();

		super.signOut();
	}

	// HINT: there is no case in which you can not finalise a enrolment once filled

		@Test
		public void test300Hacking() {
	
			Collection<Enrolment> enrolments;
			String params;
	
			enrolments = this.repository.findManyEnrolmentsByStudentUsername("student2");
			for (final Enrolment enrolment : enrolments)
				if (enrolment.isDraftMode()) {
					params = String.format("id=%d", enrolment.getId());
	
					super.checkLinkExists("Sign in");
					super.request("/student/enrolment/finalise", params);
					super.checkPanicExists();
	
					super.signIn("administrator", "administrator");
					super.request("/student/enrolment/finalise", params);
					super.checkPanicExists();
					super.signOut();
	
					super.signIn("assistant1", "assistant1");
					super.request("/student/enrolment/finalise", params);
					super.checkPanicExists();
					super.signOut();
	
					super.signIn("lecturer1", "lecturer1");
					super.request("/student/enrolment/finalise", params);
					super.checkPanicExists();
					super.signOut();
	
					super.signIn("company1", "company1");
					super.request("/student/enrolment/finalise", params);
					super.checkPanicExists();
					super.signOut();
	
					super.signIn("auditor1", "auditor1");
					super.request("/student/enrolment/finalise", params);
					super.checkPanicExists();
					super.signOut();
				}
		}
	
		@Test
		public void test301Hacking() {
	
			Collection<Enrolment> enrolments;
			String params;
	
			super.signIn("student1", "student1");
			enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
			for (final Enrolment enrolment : enrolments)
				if (!enrolment.isDraftMode()) {
					params = String.format("id=%d", enrolment.getId());
					super.request("/student/enrolment/finalise", params);
				}
			super.signOut();
		}
	
		@Test
		public void test302Hacking() {
	
			Collection<Enrolment> enrolments;
			String params;
	
			super.signIn("student1", "student1");
			enrolments = this.repository.findManyEnrolmentsByStudentUsername("student2");
			for (final Enrolment enrolment : enrolments) {
				params = String.format("id=%d", enrolment.getId());
				super.request("/student/enrolment/finalise", params);
	
			}
			super.signOut();
		}

}
