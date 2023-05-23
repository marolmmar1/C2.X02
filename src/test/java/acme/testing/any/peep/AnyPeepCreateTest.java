/*
 * EmployerJobCreateTest.java
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

public class AnyPeepCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int peepIndex, final String instantiation, final String title, final String nick, final String message, final String email, final String link) {

		super.signIn("any6", "any6");

		super.clickOnMenu("Any", "Peep List");
		super.checkListingExists();

		super.clickOnButton("Publish");
		super.fillInputBoxIn("instantiation", instantiation);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Publish");

		super.clickOnMenu("Any", "Peep List");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(peepIndex, 0, title);
		super.checkColumnHasValue(peepIndex, 1, nick);
		super.checkColumnHasValue(peepIndex, 2, message);

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

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int peepIndex, final String instantiation, final String title, final String nick, final String message, final String email, final String link) {

		super.signIn("any6", "any6");

		super.clickOnMenu("Any", "Peep List");
		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("instantiation", instantiation);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Publish");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/any/peep/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/any/peep/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/any/peep/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/any/peep/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/any/peep/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/any/peep/create");
		super.checkPanicExists();
		super.signOut();
	}

}
