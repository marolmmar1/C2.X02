
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Enrolment;
import acme.testing.TestHarness;

public class StudentActivityCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepositoryTest repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Positive(final int enrolmentRecordIndex, final int activityRecordIndex, final String title, final String abstracts, final String inicialPeriod, final String finalPeriod, final String nature, final String link) {

		super.signIn("student4", "student4");

		super.clickOnMenu("Student", "Enrolment List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");

		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstracts", abstracts);
		super.fillInputBoxIn("inicialPeriod", inicialPeriod);
		super.fillInputBoxIn("finalPeriod", finalPeriod);
		super.fillInputBoxIn("nature", nature);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		//		super.sortListing(0, "asc");
		//		super.checkColumnHasValue(activityRecordIndex, 0, title);
		//
		//		super.clickOnListingRecord(activityRecordIndex);
		//		super.checkFormExists();
		//		super.checkInputBoxHasValue("title", title);
		//		super.checkInputBoxHasValue("abstracts", abstracts);
		//		super.checkInputBoxHasValue("inicialPeriod", inicialPeriod);
		//		super.checkInputBoxHasValue("finalPeriod", finalPeriod);
		//		super.checkInputBoxHasValue("nature", nature);
		//		super.checkInputBoxHasValue("link", link);
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Negative(final int enrolmentRecordIndex, final int activityRecordIndex, final String title, final String abstracts, final String inicialPeriod, final String finalPeriod, final String nature, final String link) {

		super.signIn("student4", "student4");

		super.clickOnMenu("Student", "Enrolment List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");

		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstracts", abstracts);
		super.fillInputBoxIn("inicialPeriod", inicialPeriod);
		super.fillInputBoxIn("finalPeriod", finalPeriod);
		super.fillInputBoxIn("nature", nature);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		final Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student3");
		for (final Enrolment enrolment : enrolments) {
			param = String.format("EnrolmentId=%d", enrolment.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activity/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/student/activity/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/student/activity/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/student/activity/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/student/activity/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/student/activity/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {

		final Collection<Enrolment> enrolments;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("student1", "student1");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments)
			if (!enrolment.isDraftMode()) {
				param = String.format("masterId=%d", enrolment.getId());
				super.request("/student/activity/create", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {

		final Collection<Enrolment> enrolments;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("student4", "student4");
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student5");
		for (final Enrolment enrolment : enrolments) {
			param = String.format("masterId=%d", enrolment.getId());
			super.request("/student/activity/create", param);
			super.checkPanicExists();
		}
	}

}
