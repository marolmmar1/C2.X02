
package acme.testing.company.practicumSession;
/*
 * EmployerDutyCreateTest.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Practicum;
import acme.testing.TestHarness;
import acme.testing.company.practicum.CompanyPracticumTestRepository;

class AssistantTutorialSessionCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final int practicumSessionRecordIndex, final String title, final String abstracts, final String inicialPeriod, final String finalPeriod, final String link) {

		super.signIn("company2", "company2");

		super.clickOnMenu("Company", "Practicum List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(practicumRecordIndex);
		super.clickOnButton("Practicum Session");

		super.clickOnButton("Create Practicum Session");
		super.fillInputBoxIn("Title", title);
		super.fillInputBoxIn("Abstracts", abstracts);
		super.fillInputBoxIn("Initial Period", inicialPeriod);
		super.fillInputBoxIn("Final Period", finalPeriod);
		super.fillInputBoxIn("Link", link);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(practicumSessionRecordIndex, 0, title);

		super.clickOnListingRecord(practicumSessionRecordIndex);
		super.checkInputBoxHasValue("Title", title);
		super.checkInputBoxHasValue("Abstracts", abstracts);
		super.checkInputBoxHasValue("Initial Period", inicialPeriod);
		super.checkInputBoxHasValue("Final Period", finalPeriod);
		super.checkInputBoxHasValue("Link", link);
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int practicumRecordIndex, final int practicumSessionRecordIndex, final String title, final String abstracts, final String inicialPeriod, final String finalPeriod, final String link) {

		super.signIn("company2", "company2");

		super.clickOnMenu("Company", "Practicum List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(4);
		super.clickOnButton("Practicum Session");

		super.clickOnButton("Create");
		super.fillInputBoxIn("Title", title);
		super.fillInputBoxIn("Abstracts", abstracts);
		super.fillInputBoxIn("Initial Period", inicialPeriod);
		super.fillInputBoxIn("Final Period", finalPeriod);
		super.fillInputBoxIn("Link", link);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		final Collection<Practicum> practicums;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum p : practicums) {
			param = String.format("practicumId=%d", p.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant1", "assistant1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {

		final Collection<Practicum> practicums;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum p : practicums)
			if (!p.isDraftMode()) {
				param = String.format("practicum=%d", p.getId());
				super.request("/company/practicum-session/create", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {

		final Collection<Practicum> practicums;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("company3", "company3");
		practicums = this.repository.findManyPracticumsByCompanyUsername("company1");
		for (final Practicum p : practicums) {
			param = String.format("practicumId=%d", p.getId());
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
		}
	}

}
