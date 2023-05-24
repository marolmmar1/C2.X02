
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Activity;
import acme.testing.TestHarness;

public class StudentActivityShowTest extends TestHarness {

// Internal state ---------------------------------------------------------
	
	@Autowired
	protected StudentActivityRepositoryTest repository;
	
// Test methods -----------------------------------------------------------
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int enrolmentRecordIndex, final int activityRecordIndex,final String title, final String abstracts, final String startDate, final String endDate, final String nature, final String link) {

		super.signIn("student2", "student2");
	
		super.clickOnMenu("Student", "Enrolment");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(enrolmentRecordIndex);
		super.clickOnButton("Activities");
		super.checkListingExists();
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

		@Test
		public void test200Negative() {
			// HINT: there's no negative test case for this listing, since it doesn't
			// HINT+ involve filling in any forms.
		}
	
		@Test
		public void test300Hacking() {
	
			Collection<Activity> activities;
			String param;
	
			super.signIn("student1", "student1");
			activities = this.repository.findManyActivitiesByStudentUsername("student1");
			for (final Activity activity : activities)
				if (activity.getActivity().isDraftMode()) {
					param = String.format("id=%d", activity.getActivity().getId());
	
					super.checkLinkExists("Sign in");
					super.request("/student/activity/show", param);
					super.checkPanicExists();
	
					super.signIn("administrator", "administrator");
					super.request("/student/activity/show", param);
					super.checkPanicExists();
					super.signOut();
	
					super.signIn("student2", "student2");
					super.request("/student/activity/show", param);
					super.checkPanicExists();
					super.signOut();
	
					super.signIn("lecturer1", "lecturer1");
					super.request("/student/activity/show", param);
					super.checkPanicExists();
					super.signOut();
	
					super.signIn("assistant1", "assistant1");
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