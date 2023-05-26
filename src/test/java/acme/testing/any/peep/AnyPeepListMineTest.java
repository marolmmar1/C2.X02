/*
 * 
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
	public void test100Positive(final int peepIndex, final String instantiation, final String title, final String nick) {

		super.clickOnMenu("Anonymous", "Peep List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(peepIndex, 0, instantiation);
		super.checkColumnHasValue(peepIndex, 1, title);
		super.checkColumnHasValue(peepIndex, 2, nick);

	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {

	}

}
