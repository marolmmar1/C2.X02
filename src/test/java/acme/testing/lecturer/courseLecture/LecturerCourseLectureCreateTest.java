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

package acme.testing.lecturer.courseLecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseLectureCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course-lecture/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int lectureIndex, final int courseIndex, final String course) {

		super.signIn("lecturer17", "lecturer17");

		super.clickOnMenu("Lecturer", "List of courses");

		super.clickOnListingRecord(courseIndex);
		super.clickOnButton("Lectures");
		super.checkListingEmpty();

		super.clickOnMenu("Lecturer", "List of lectures");

		super.checkListingExists();
		super.clickOnListingRecord(lectureIndex);
		super.clickOnButton("Add to course");
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Add");

		super.clickOnMenu("Lecturer", "List of courses");

		super.clickOnListingRecord(courseIndex);
		super.clickOnButton("Lectures");
		super.checkNotListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course-lecture/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int lectureIndex, final int courseIndex, final String course) {

		super.signIn("lecturer16", "lecturer16");

		super.clickOnMenu("Lecturer", "List of courses");

		super.clickOnListingRecord(courseIndex);
		super.clickOnButton("Lectures");
		super.checkNotListingEmpty();

		super.clickOnMenu("Lecturer", "List of lectures");

		super.checkListingExists();
		super.clickOnListingRecord(lectureIndex);
		super.clickOnButton("Add to course");
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Add");
		super.checkErrorsExist();

		super.clickOnMenu("Lecturer", "List of courses");

		super.clickOnListingRecord(courseIndex);
		super.clickOnButton("Lectures");
		super.checkNotListingEmpty();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();
	}

}
