/*
 * EmployerJobListMineTest.java
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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepListMineTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int peepIndex, final String instantiation, final String title, final String nick, 
			final String message, final String email, final String link) {

		super.signIn("any4", "any4");

		super.clickOnMenu("Any", "Peep List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(peepRecordIndex, 0, title);
		super.checkColumnHasValue(peepRecordIndex, 1, nick);
		super.checkColumnHasValue(peepRecordIndex, 2, message);

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/any/peep/list-all");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/any/peep/list-all");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/any/peep/list-all");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/any/peep/list-all");
		super.checkPanicExists();
		super.signOut();
		
		super.signIn("assistant1", "assistant1");
		super.request("/any/peep/list-all");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/any/peep/list-all");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/any/peep/list-all");
		super.checkPanicExists();
		super.signOut();

	}

}
