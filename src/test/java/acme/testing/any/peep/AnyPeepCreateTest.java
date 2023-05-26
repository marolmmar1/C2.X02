///*
// * EmployerJobCreateTest.java
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
//package acme.testing.any.peep;
//
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//
//import acme.testing.TestHarness;
//
//public class AnyPeepCreateTest extends TestHarness {
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/any/peep/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test100Positive(final int peepIndex, final String instantiation, final String title, final String nick, final String email, final String message, final String link) {
//
//		super.signIn("student1", "student1");
//
//		super.clickOnMenu("Account", "Peep List");
//		super.checkListingExists();
//
//		super.clickOnButton("Publish");
//		super.fillInputBoxIn("title", title);
//		super.fillInputBoxIn("nick", nick);
//		super.fillInputBoxIn("email", email);
//		super.fillInputBoxIn("message", message);
//		super.fillInputBoxIn("link", link);
//		super.clickOnSubmit("Publish");
//
//		super.signOut();
//	}
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/any/peep/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test200Negative(final int peepIndex, final String title, final String nick, final String message, final String email, final String link) {
//
//		super.clickOnMenu("Anonymous", "Peep List");
//		super.clickOnButton("Publish");
//		super.checkFormExists();
//
//		super.fillInputBoxIn("title", title);
//		super.fillInputBoxIn("nick", nick);
//		super.fillInputBoxIn("email", email);
//		super.fillInputBoxIn("message", message);
//		super.fillInputBoxIn("link", link);
//		super.clickOnSubmit("Publish");
//		super.checkErrorsExist();
//
//	}
//
//}
