///*
// * EmployerJobUpdateTest.java
// *
// * Copyright (C) 2012-2023 Rafael Corchuelo.
// *
// * In keeping with the traditional purpose of furthering education and research, it is
// * the policy of the copyright owner to permit non-commercial use and redistribution of
// * this software. It has been tested carefully, but it is not guaranteed for any particular
// * purposes. The copyright owner does not offer any warranties or representations, nor do
// * they accept any liabilities with respect to them.
// */
//
//package acme.testing.assistant.tutorialSession;
//
//import java.util.Collection;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import acme.entities.Tutorial;
//import acme.testing.TestHarness;
//
//public class AssistantTutorialSessionUpdateTest extends TestHarness {
//
//	// Internal state ---------------------------------------------------------
//
//	@Autowired
//	protected AssistantTutorialSessionTestRepository repository;
//
//	// Test methods ------------------------------------------------------------
//
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/assistant/tutorial-session/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test100Positive(final int tutorialRecordIndex, final int tutorialSessionRecordIndex, final String title, final String abstracts, final String nature, final String inicialPeriod, final String finalPeriod, final String link) {
//
//		super.signIn("assistant3", "assistant3");
//
//		super.clickOnMenu("Assistant", "Tutorial List");
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//
//		super.clickOnListingRecord(tutorialRecordIndex);
//		super.clickOnButton("Tutorial Sessions");
//
//		super.clickOnListingRecord(tutorialSessionRecordIndex);
//		super.checkFormExists();
//
//		super.fillInputBoxIn("title", title);
//		super.fillInputBoxIn("abstracts", abstracts);
//		super.fillInputBoxIn("nature", nature);
//		super.fillInputBoxIn("inicialPeriod", inicialPeriod);
//		super.fillInputBoxIn("finalPeriod", finalPeriod);
//		super.fillInputBoxIn("link", link);
//		super.clickOnSubmit("Update");
//
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//		super.checkColumnHasValue(tutorialSessionRecordIndex, 0, title);
//
//		super.clickOnListingRecord(tutorialSessionRecordIndex);
//		super.checkFormExists();
//		super.checkInputBoxHasValue("title", title);
//		super.checkInputBoxHasValue("abstracts", abstracts);
//		super.checkInputBoxHasValue("nature", nature);
//		super.checkInputBoxHasValue("inicialPeriod", inicialPeriod);
//		super.checkInputBoxHasValue("finalPeriod", finalPeriod);
//		super.checkInputBoxHasValue("link", link);
//
//		super.signOut();
//	}
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/assistant/tutorial-session/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test200Negative(final int tutorialRecordIndex, final int tutorialSessionRecordIndex, final String title, final String abstracts, final String nature, final String inicialPeriod, final String finalPeriod, final String link) {
//
//		super.signIn("assistant3", "assistant3");
//
//		super.clickOnMenu("Assistant", "Tutorial List");
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//
//		super.clickOnListingRecord(tutorialRecordIndex);
//		super.clickOnButton("Tutorial Sessions");
//
//		super.clickOnListingRecord(tutorialSessionRecordIndex);
//		super.checkFormExists();
//
//		super.fillInputBoxIn("title", title);
//		super.fillInputBoxIn("abstracts", abstracts);
//		super.fillInputBoxIn("nature", nature);
//		super.fillInputBoxIn("inicialPeriod", inicialPeriod);
//		super.fillInputBoxIn("finalPeriod", finalPeriod);
//		super.fillInputBoxIn("link", link);
//
//		super.clickOnSubmit("Update");
//
//		super.checkErrorsExist();
//
//		super.signOut();
//	}
//
//	@Test
//	public void test300Hacking() {
//		// HINT: this test tries to update a job with a role other than "Employer",
//		// HINT+ or using an employer who is not the owner.
//
//		final Collection<Tutorial> tutorials;
//		String param;
//
//		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");
//		for (final Tutorial tutorial : tutorials) {
//			param = String.format("id=%d", tutorial.getId());
//
//			super.checkLinkExists("Sign in");
//			super.request("/assistant/tutorial-session/update", param);
//			super.checkPanicExists();
//
//			super.signIn("administrator", "administrator");
//			super.request("/assistant/tutorial-session/update", param);
//			super.checkPanicExists();
//			super.signOut();
//
//			super.signIn("assistant2", "assistant2");
//			super.request("/assistant/tutorial-session/update", param);
//			super.checkPanicExists();
//			super.signOut();
//
//			super.signIn("auditor1", "auditor1");
//			super.request("/assistant/tutorial-session/update", param);
//			super.checkPanicExists();
//			super.signOut();
//
//			super.signIn("lecturer1", "lecturer1");
//			super.request("/assistant/tutorial-session/update", param);
//			super.checkPanicExists();
//			super.signOut();
//
//			super.signIn("student1", "student1");
//			super.request("/assistant/tutorial-session/update", param);
//			super.checkPanicExists();
//			super.signOut();
//
//			super.signIn("company1", "company1");
//			super.request("/assistant/tutorial-session/update", param);
//			super.checkPanicExists();
//			super.signOut();
//
//			super.signIn("auditor1", "auditor1");
//			super.request("/assistant/tutorial-session/update", param);
//			super.checkPanicExists();
//			super.signOut();
//		}
//	}
//
//}
