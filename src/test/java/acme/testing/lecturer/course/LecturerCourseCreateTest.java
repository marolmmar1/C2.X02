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

package acme.testing.lecturer.course;

import acme.testing.TestHarness;

public class LecturerCourseCreateTest extends TestHarness {

	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/lecturer/course/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void test100Positive(final int courseIndex, final String code, final String title, final String price, final String abstracts, final String link, final String nature) {
	//
	//		super.signIn("lecturer2", "lecturer2");
	//
	//		super.clickOnMenu("Lecturer", "List of courses");
	//		super.checkListingExists();
	//
	//		super.clickOnButton("Create course");
	//		super.fillInputBoxIn("code", code);
	//		super.fillInputBoxIn("title", title);
	//		super.fillInputBoxIn("price", price);
	//		super.fillInputBoxIn("abstracts", abstracts);
	//		super.fillInputBoxIn("link", link);
	//		super.clickOnSubmit("Create course");
	//
	//		super.clickOnMenu("Lecturer", "List of courses");
	//		super.checkListingExists();
	//		super.sortListing(0, "asc");
	//		super.checkColumnHasValue(courseIndex, 0, code);
	//		super.checkColumnHasValue(courseIndex, 1, title);
	//		super.checkColumnHasValue(courseIndex, 2, abstracts);
	//
	//		super.clickOnListingRecord(courseIndex);
	//		super.checkFormExists();
	//		super.checkInputBoxHasValue("code", code);
	//		super.checkInputBoxHasValue("title", title);
	//		super.checkInputBoxHasValue("price", price);
	//		super.checkInputBoxHasValue("abstracts", abstracts);
	//		super.checkInputBoxHasValue("link", link);
	//		super.checkInputBoxHasValue("nature", nature);
	//
	//		super.clickOnButton("Lectures");
	//		super.checkListingExists();
	//		super.checkListingEmpty();
	//
	//		super.signOut();
	//	}
	//
	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/lecturer/course/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void test200Negative(final int courseIndex, final String code, final String title, final String price, final String abstracts, final String link, final String nature) {
	//
	//		super.signIn("lecturer2", "lecturer2");
	//
	//		super.clickOnMenu("Lecturer", "List of courses");
	//		super.clickOnButton("Create course");
	//		super.checkFormExists();
	//
	//		super.fillInputBoxIn("code", code);
	//		super.fillInputBoxIn("title", title);
	//		super.fillInputBoxIn("price", price);
	//		super.fillInputBoxIn("abstracts", abstracts);
	//		super.fillInputBoxIn("link", link);
	//		super.clickOnSubmit("Create course");
	//		super.checkErrorsExist();
	//
	//		super.signOut();
	//	}
	//
	//	@Test
	//	public void test300Hacking() {
	//
	//		super.checkLinkExists("Sign in");
	//		super.request("/lecturer/course/create");
	//		super.checkPanicExists();
	//
	//		super.signIn("administrator", "administrator");
	//		super.request("/lecturer/course/create");
	//		super.checkPanicExists();
	//		super.signOut();
	//
	//		super.signIn("student1", "student1");
	//		super.request("/lecturer/course/create");
	//		super.checkPanicExists();
	//		super.signOut();
	//
	//		super.signIn("company1", "company1");
	//		super.request("/lecturer/course/create");
	//		super.checkPanicExists();
	//		super.signOut();
	//
	//		super.signIn("assistant1", "assistant1");
	//		super.request("/lecturer/course/create");
	//		super.checkPanicExists();
	//		super.signOut();
	//
	//		super.signIn("auditor1", "auditor1");
	//		super.request("/lecturer/course/create");
	//		super.checkPanicExists();
	//		super.signOut();
	//	}

}
