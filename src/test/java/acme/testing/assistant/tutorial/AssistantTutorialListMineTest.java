///*
// * EmployerJobListMineTest.java
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
//package acme.testing.assistant.tutorial;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//
//import acme.testing.TestHarness;
//
//public class AssistantTutorialListMineTest extends TestHarness {
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/assistant/tutorial/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test100Positive(final int tutorialRecordIndex, final String code, final String title, final String estimatedTime) {
//
//		super.signIn("assistant4", "assistant4");
//
//		super.clickOnMenu("Assistant", "Tutorial List");
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//
//		super.checkColumnHasValue(tutorialRecordIndex, 0, code);
//		super.checkColumnHasValue(tutorialRecordIndex, 1, title);
//		super.checkColumnHasValue(tutorialRecordIndex, 2, estimatedTime);
//
//		super.signOut();
//	}
//
//	@Test
//	public void test200Negative() {
//
//	}
//
//	@Test
//	public void test300Hacking() {
//
//		super.checkLinkExists("Sign in");
//		super.request("/assistant/tutorial/list-all");
//		super.checkPanicExists();
//
//		super.signIn("administrator", "administrator");
//		super.request("/assistant/tutorial/list-all");
//		super.checkPanicExists();
//		super.signOut();
//
//		super.signIn("lecturer1", "lecturer1");
//		super.request("/assistant/tutorial/list-all");
//		super.checkPanicExists();
//		super.signOut();
//
//		super.signIn("student1", "student1");
//		super.request("/assistant/tutorial/list-all");
//		super.checkPanicExists();
//		super.signOut();
//
//		super.signIn("company1", "company1");
//		super.request("/assistant/tutorial/list-all");
//		super.checkPanicExists();
//		super.signOut();
//
//		super.signIn("auditor1", "auditor1");
//		super.request("/assistant/tutorial/list-all");
//		super.checkPanicExists();
//		super.signOut();
//
//	}
//
//}
