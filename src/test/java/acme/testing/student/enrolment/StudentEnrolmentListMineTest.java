package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentEnrolmentListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentRecordIndex, final String code, final String course) {
		
		
		super.signIn("student3", "student3");

		super.clickOnMenu("Student", "List of enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(enrolmentRecordIndex, 0, code);
		super.checkColumnHasValue(enrolmentRecordIndex, 1, course);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature since it's a listing that
		// HINT+ doesn't involve entering any data into any forms.
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/student/enrolment/list-all");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/enrolment/list-all");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/student/enrolment/list-all");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/student/enrolment/list-all");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/student/enrolment/list-all");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/student/enrolment/list-all");
		super.checkPanicExists();
		super.signOut();
	}

}
