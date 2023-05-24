/*
 * EmployerJobUpdateTest.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.company.practicum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumTestRepository repository;

	// Test methods ------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumIndex, final String code, final String title, final String abstracts, final String goals, final String course, final String estimatedTime) {

		super.signIn("company2", "company2");

		super.clickOnMenu("Company", "Practicum List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(practicumIndex);
		super.checkFormExists();
		super.fillInputBoxIn("Code", code);
		super.fillInputBoxIn("Title", title);
		super.fillInputBoxIn("Abstract", abstracts);
		super.fillInputBoxIn("Goals", goals);
		super.fillInputBoxIn("Course", course);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(practicumIndex, 0, code);
		super.checkColumnHasValue(practicumIndex, 1, title);

		super.clickOnListingRecord(practicumIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("Code", code);
		super.checkInputBoxHasValue("Title", title);
		super.checkInputBoxHasValue("Abstract", abstracts);
		super.checkInputBoxHasValue("Goals", goals);
		super.checkInputBoxHasValue("Course", course);
		super.checkInputBoxHasValue("EstimatedTime", estimatedTime);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int practicumIndex, final String code, final String title, final String abstracts, final String goals, final String course, final String estimatedTime) {

		super.signIn("company2", "company2");

		super.clickOnMenu("Company", "Practicum List");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(practicumIndex);
		super.checkFormExists();
		super.fillInputBoxIn("Code", code);
		super.fillInputBoxIn("Title", title);
		super.fillInputBoxIn("Abstract", abstracts);
		super.fillInputBoxIn("Goals", goals);
		super.fillInputBoxIn("Course", course);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<Practicum> practicums;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("compnay4");
		for (final Practicum p : practicums) {
			param = String.format("id=%d", p.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student2", "student2");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant2", "assistant2");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company1", "company1");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor2", "auditor2");
			super.request("/company/practicum/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
