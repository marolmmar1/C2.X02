///// *
//// * EmployerApplicationShowTest.java
//// *
//// * Copyright (C) 2012-2023 Rafael Corchuelo.
//// *
//// * In keeping with the traditional purpose of furthering education and research, it is
//// * the policy of the copyright owner to permit non-commercial use and redistribution of
//// * this software. It has been tested carefully, but it is not guaranteed for any particular
//// * purposes. The copyright owner does not offer any warranties or representations, nor do
//// * they accept any liabilities with respect to them.
//// */
////
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
//import acme.entities.TutorialSession;
//import acme.testing.TestHarness;
//
//public class AssistantTutorialSessionShowTest extends TestHarness {
//
//	// Internal state ---------------------------------------------------------
//
//	@Autowired
//	protected AssistantTutorialSessionTestRepository repository;
//
//	// Test methods -----------------------------------------------------------
//
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/assistant/tutorial-session/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test100Positive(final int tutorialIndex, final String code, final int tutorialSessionRecordIndex, final String title, final String abstracts, final String nature, final String inicialPeriod, final String finalPeriod, final String link) {
//
//		super.signIn("assistant2", "assistant2");
//
//		super.clickOnMenu("Assistant", "Tutorial List");
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//		super.clickOnListingRecord(tutorialIndex);
//		super.clickOnButton("Tutorial Sessions");
//		super.checkListingExists();
//		super.clickOnListingRecord(tutorialSessionRecordIndex);
//		super.checkFormExists();
//
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
//	@Test
//	public void test200Negative() {
//		// HINT: there's no negative test case for this listing, since it doesn't
//		// HINT+ involve filling in any forms.
//	}
//
//	@Test
//	public void test300Hacking() {
//
//		Collection<TutorialSession> tutorialSessions;
//		String param;
//
//		super.signIn("assistant1", "assistant1");
//		tutorialSessions = this.repository.findManyTutorialSessionByAssistantUsername("assistant1");
//		for (final TutorialSession tutorialSession : tutorialSessions)
//			if (tutorialSession.getTutorial().isDraftMode()) {
//				param = String.format("id=%d", tutorialSession.getTutorial().getId());
//
//				super.checkLinkExists("Sign in");
//				super.request("/assistant/tutorial-session/show", param);
//				super.checkPanicExists();
//
//				super.signIn("administrator", "administrator");
//				super.request("/assistant/tutorial-session/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("assistant2", "assistant2");
//				super.request("/assistant/tutorial-session/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("lecturer1", "lecturer1");
//				super.request("/assistant/tutorial-session/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("student1", "student1");
//				super.request("/assistant/tutorial-session/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("company1", "company1");
//				super.request("/assistant/tutorial-session/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("auditor1", "auditor1");
//				super.request("/assistant/tutorial-session/show", param);
//				super.checkPanicExists();
//				super.signOut();
//			}
//	}
//
//}
