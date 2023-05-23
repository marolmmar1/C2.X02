
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Course;
import acme.entities.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepositoryTest repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentIndex, final String code, final Course course, final String motivation, final String goals, final String holderName, final String lowerNibble, final String creditCard, final String cvc,
		final String expiryDate) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "Enrolment List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(enrolmentIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("holderName", holderName);
		super.fillInputBoxIn("lowerNibble", lowerNibble);
		super.fillInputBoxIn("creditCard", creditCard);
		super.fillInputBoxIn("cvc", cvc);
		super.fillInputBoxIn("expiryDate", expiryDate);

		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(enrolmentIndex, 0, code);
		super.checkColumnHasValue(enrolmentIndex, 1, course.getTitle());

		super.clickOnListingRecord(enrolmentIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("holderName", holderName);
		super.checkInputBoxHasValue("lowerNibble", lowerNibble);
		super.checkInputBoxHasValue("creditCard", creditCard);
		super.checkInputBoxHasValue("cvc", cvc);
		super.checkInputBoxHasValue("expiryDate", expiryDate);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int enrolmentIndex, final String code, final Course course, final String motivation, final String goals, final String holderName, final String lowerNibble, final String creditCard, final String cvc,
		final String expiryDate) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List of enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(enrolmentIndex);
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("holderName", holderName);
		super.fillInputBoxIn("lowerNibble", lowerNibble);
		super.fillInputBoxIn("creditCard", creditCard);
		super.fillInputBoxIn("cvc", cvc);
		super.fillInputBoxIn("expiryDate", expiryDate);

		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findManyEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments) {
			param = String.format("id=%d", enrolment.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant2", "assistant2");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor2", "auditor2");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
