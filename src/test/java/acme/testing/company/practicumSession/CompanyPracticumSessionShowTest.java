
package acme.testing.company.practicumSession;
/// *
// * EmployerApplicationShowTest.java
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

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumIndex, final String code, final int practicumSessionRecordIndex, final String title, final String abstracts, final String inicialPeriod, final String finalPeriod, final String link) {

		super.signIn("company2", "company2");

		super.clickOnMenu("Company", "Practicum List");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(practicumIndex);
		super.clickOnButton("Practicum Session");
		super.checkListingExists();
		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("Title", title);
		super.checkInputBoxHasValue("Abstracts", abstracts);
		super.checkInputBoxHasValue("Initial Period", inicialPeriod);
		super.checkInputBoxHasValue("Final Period", finalPeriod);
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

		Collection<PracticumSession> practicumSessions;
		String param;

		super.signIn("company2", "company2");
		practicumSessions = this.repository.findManyPracticumSessionByCompanyUsername("company2");
		for (final PracticumSession ps : practicumSessions)
			if (ps.getPracticum().isDraftMode()) {
				param = String.format("id=%d", ps.getPracticum().getId());

				super.checkLinkExists("Sign in");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
