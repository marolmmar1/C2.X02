package acme.testing.student.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentActivityCreateTest extends TestHarness {

		@ParameterizedTest
		@CsvFileSource(resources = "/student/activity/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
		public void test100Positive(final int activityIndex, final String title, final String abstracts,
				final String inicialPeriod, final String finalPeriod, final String nature, final String link) {
	
			super.signIn("student2", "student2");
	
			super.clickOnMenu("Student", "List of activitys");
			super.checkListingExists();
	
			super.clickOnButton("Create activity");
			super.fillInputBoxIn("title", title);
			super.fillInputBoxIn("abstracts", abstracts);
			super.fillInputBoxIn("inicialPeriod", inicialPeriod);
			super.fillInputBoxIn("finalPeriod", finalPeriod);
			super.fillInputBoxIn("nature", nature);
			super.fillInputBoxIn("link", link);
			super.clickOnSubmit("Create activity");
	
			super.clickOnMenu("Student", "List of activitys");
			super.checkListingExists();
			super.sortListing(0, "asc");
			super.checkColumnHasValue(activityIndex, 0, title);
			super.checkColumnHasValue(activityIndex, 1, nature);
	
			super.clickOnListingRecord(activityIndex);
			super.checkFormExists();
			super.checkInputBoxHasValue("title", title);
			super.checkInputBoxHasValue("abstracts", abstracts);
			super.checkInputBoxHasValue("inicialPeriod", inicialPeriod);
			super.checkInputBoxHasValue("finalPeriod", finalPeriod);
			super.checkInputBoxHasValue("nature", nature);
			super.checkInputBoxHasValue("link", link);
	
			super.checkFormExists();
	
			super.signOut();
		}
	
		@ParameterizedTest
		@CsvFileSource(resources = "/student/activity/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
		public void test200Negative(final int activityIndex, final String title, final String abstracts,
				final String inicialPeriod, final String finalPeriod, final String nature, final String link) {
			super.signIn("student2", "student2");
	
			super.clickOnMenu("Student", "List of activitys");
			super.clickOnButton("Create ");
			super.checkFormExists();
	
			super.fillInputBoxIn("title", title);
			super.fillInputBoxIn("abstracts", abstracts);
			super.fillInputBoxIn("inicialPeriod", inicialPeriod);
			super.fillInputBoxIn("finalPeriod", finalPeriod);
			super.fillInputBoxIn("nature", nature);
			super.fillInputBoxIn("link", link);
			super.clickOnSubmit("Create ");
			super.checkErrorsExist();
	
			super.signOut();
		}
	
		@Test
		public void test300Hacking() {
	
			super.checkLinkExists("Sign in");
			super.request("/student/activity/create");
			super.checkPanicExists();
	
			super.signIn("administrator", "administrator");
			super.request("/student/activity/create");
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("lecturer1", "lecturer1");
			super.request("/student/activity/create");
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("company1", "company1");
			super.request("/student/activity/create");
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("assistant1", "assistant1");
			super.request("/student/activity/create");
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("auditor1", "auditor1");
			super.request("/student/activity/create");
			super.checkPanicExists();
			super.signOut();
		}

}
