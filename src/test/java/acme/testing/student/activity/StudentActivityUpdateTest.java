
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Activity;
import acme.testing.TestHarness;

public class StudentActivityUpdateTest extends TestHarness {

//	 Internal state ---------------------------------------------------------
	
	@Autowired
	protected StudentActivityRepositoryTest repository;
	
// Test methods ------------------------------------------------------------
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentRecordIndex, final int activityRecordIndex,final String title, final String abstracts, final String startDate, final String endDate, final String nature, final String link) {

		super.signIn("student3", "student3");
	
		super.clickOnMenu("Student", "Enrolment List");
		super.checkListingExists();
		super.sortListing(0, "asc");
	
		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");
	
		super.clickOnListingRecord(activityRecordIndex);
		super.checkFormExists();
	
		super.fillInputBoxIn("Title", title);
		super.fillInputBoxIn("Abstract", abstracts);
		super.fillInputBoxIn("Start date", startDate);
		super.fillInputBoxIn("End date", endDate);
		super.fillInputBoxIn("Nature", nature);
		super.fillInputBoxIn("Link", link);
		
		super.clickOnSubmit("Update");
	
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(activityRecordIndex, 0, title);
	
		super.clickOnListingRecord(activityRecordIndex);
		super.checkFormExists();
		
		super.checkInputBoxHasValue("Title", title);
		super.checkInputBoxHasValue("Abstract", abstracts);
		super.checkInputBoxHasValue("Start date", startDate);
		super.checkInputBoxHasValue("End date", endDate);
		super.checkInputBoxHasValue("Nature", nature);
		super.checkInputBoxHasValue("Link", link);
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int enrolmentRecordIndex, final int activityRecordIndex,final String title, final String abstracts, final String inicialPeriod, final String finalPeriod, final String nature, final String link) {

		super.signIn("student3", "student3");
	
		super.clickOnMenu("Student", "Enrolment List");
		super.checkListingExists();
		super.sortListing(0, "asc");
	
		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");
	
		super.clickOnListingRecord(activityRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("Title", title);
		super.fillInputBoxIn("Abstract", abstracts);
		super.fillInputBoxIn("Start date", startDate);
		super.fillInputBoxIn("End date", endDate);
		super.fillInputBoxIn("Nature", nature);
		super.fillInputBoxIn("Link", link);
	
		super.clickOnSubmit("Update");
	
		super.checkErrorsExist();
	
		super.signOut();
	}
	
	@Test
	public void test300Hacking() {
			// HINT: this test tries to update a job with a role other than "Employer",
			// HINT+ or using an employer who is not the owner.
	
		final Collection<Enrolment> enrolments;
		String param;
	
		enrolments = this.repository.findManyEnrolmentsByStudentUsername("assistant1");
		for (final Enrolment enrolment : enrolments) {
			param = String.format("id=%d", enrolment.getId());
	
			super.checkLinkExists("Sign in");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
	
			super.signIn("administrator", "administrator");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("student2", "student2");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("auditor1", "auditor1");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("lecturer1", "lecturer1");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("assistant1", "assistant1");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("company1", "company1");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("auditor1", "auditor1");
			super.request("/student/activity/update", param);
			super.checkPanicExists();
			super.signOut();
			
		}
	
	}
	
}