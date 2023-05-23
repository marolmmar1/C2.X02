/*
 * EmployerJobShowTest.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.any.peep;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Peep;
import acme.testing.TestHarness;

public class AnyPeepShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyPeepTestRepository repository;

	// Test data --------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int peepIndex, final String instantiation, final String title, final String nick, final String message, final String email, final String link) {

		super.signIn("any4", "any4");

		super.clickOnMenu("Any", "Peep List");
		super.sortListing(0, "asc");
		super.clickOnListingRecord(peepIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("instantiation", instantiation);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("nick", nick);
		super.checkInputBoxHasValue("message", message);
		super.checkInputBoxHasValue("email", email);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Peep> peeps;
		String param;

		peeps = this.repository.findManyPeeps();
		for (final Peep peep : peeps) {

			param = String.format("id=%d", peep.getId());

			super.checkLinkExists("Sign in");
			super.request("/any/peep/show", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/any/peep/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("any5", "any5");
			super.request("/any/peep/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/any/peep/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/any/peep/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/any/peep/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/any/peep/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/any/peep/show", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
