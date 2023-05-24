package acme.testing.student.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentActivityListMineTest extends TestHarness {

// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepositoryTest repository;

// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentRecordIndex, final String code, final int activityRecordIndex, final String title) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolment List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(tutorialRecordIndex, 0, code);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.checkInputBoxHasValue("code", code);
		super.clickOnButton("Activities");

		super.checkListingExists();
		super.checkColumnHasValue(activityRecordIndex, 0, title);
		super.clickOnListingRecord(activityRecordIndex);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {
		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final  Enrolment enrolment : enrolments)
			if (enrolment.isDraftMode()) {
				param = String.format("id=%d", activity.getId());

				super.checkLinkExists("Sign in");
				super.request("/student/activity/list", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/student/activity/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/student/activity/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/student/activity/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student2", "student2");
				super.request("/student/activity/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/student/activity/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/student/activity/list", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}