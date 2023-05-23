package acme.testing.student.activity;


import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Lecture;
import acme.roles.Student;
import acme.testing.TestHarness;

public class StudentActivityShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepositoryTest repository;

	// Test data --------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int activityIndex, final String title, final String abstracts,
			final String inicialPeriod, final String finalPeriod, final String nature, final String link) {
		super.signIn("student3", "student3");

		super.clickOnMenu("Student", "List of activities");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(activityIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstracts", abstracts);
		super.checkInputBoxHasValue("inicialPeriod", inicialPeriod);
		super.checkInputBoxHasValue("finalPeriod", finalPeriod);
		super.checkInputBoxHasValue("nature", nature);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Student> activities;
		String param;

		activities = this.repository.findManyActivitiesByStudentUsername("student3");
		for (final Student activity : activities) {
			
				param = String.format("id=%d", activity.getId());

				super.checkLinkExists("Sign in");
				super.request("/student/activity/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/student/activity/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant5", "assistant5");
				super.request("/student/activity/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/student/activity/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/student/activity/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/student/activity/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/student/activity/show", param);
				super.checkPanicExists();
				super.signOut();
		}
	}

}
