///*
// * EmployerJobUpdateTest.java
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
//package acme.testing.lecturer.lecture;
//
//import java.util.Collection;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import acme.entities.Lecture;
//import acme.testing.TestHarness;
//
//public class LecturerLectureUpdateTest extends TestHarness {
//
//	// Internal state ---------------------------------------------------------
//
//	@Autowired
//	protected LecturerLectureRepositoryTest repository;
//
//	// Test methods ------------------------------------------------------------
//
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/lecturer/lecture/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test100Positive(final int lectureIndex, final String title, final String abstracts, final String estimatedTime, final String body, final String nature, final String link) {
//
//		super.signIn("lecturer10", "lecturer10");
//
//		super.clickOnMenu("Lecturer", "List of lectures");
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//
//		super.clickOnListingRecord(lectureIndex);
//		super.checkFormExists();
//		super.fillInputBoxIn("title", title);
//		super.fillInputBoxIn("abstracts", abstracts);
//		super.fillInputBoxIn("estimatedTime", estimatedTime);
//		super.fillInputBoxIn("body", body);
//		super.fillInputBoxIn("nature", nature);
//		super.fillInputBoxIn("link", link);
//		super.clickOnSubmit("Update");
//
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//		super.checkColumnHasValue(lectureIndex, 0, title);
//		super.checkColumnHasValue(lectureIndex, 1, abstracts);
//		super.checkColumnHasValue(lectureIndex, 2, estimatedTime);
//
//		super.clickOnListingRecord(lectureIndex);
//		super.checkFormExists();
//		super.checkInputBoxHasValue("title", title);
//		super.checkInputBoxHasValue("abstracts", abstracts);
//		super.checkInputBoxHasValue("estimatedTime", estimatedTime);
//		super.checkInputBoxHasValue("body", body);
//		super.checkInputBoxHasValue("nature", nature);
//		super.checkInputBoxHasValue("link", link);
//
//		super.signOut();
//	}
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/lecturer/lecture/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test200Negative(final int lectureIndex, final String title, final String abstracts, final String estimatedTime, final String body, final String nature, final String link) {
//
//		super.signIn("lecturer10", "lecturer10");
//
//		super.clickOnMenu("Lecturer", "List of lectures");
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//
//		super.clickOnListingRecord(lectureIndex);
//		super.checkFormExists();
//		super.fillInputBoxIn("title", title);
//		super.fillInputBoxIn("abstracts", abstracts);
//		super.fillInputBoxIn("estimatedTime", estimatedTime);
//		super.fillInputBoxIn("body", body);
//		super.fillInputBoxIn("nature", nature);
//		super.fillInputBoxIn("link", link);
//		super.clickOnSubmit("Update");
//
//		super.checkErrorsExist();
//
//		super.signOut();
//	}
//
//	@Test
//	public void test300Hacking() {
//		Collection<Lecture> lectures;
//		String param;
//		lectures = this.repository.findManyLecturesByLecturerUsername("lecturer10");
//		for (final Lecture lecture : lectures) {
//			param = String.format("id=%d", lecture.getId());
//
//			super.checkLinkExists("Sign in");
//			super.request("/lecturer/lecture/update", param);
//			super.checkPanicExists();
//
//			super.signIn("administrator", "administrator");
//			super.request("/lecturer/lecture/update", param);
//			super.checkPanicExists();
//			super.signOut();
//
//			super.signIn("lecturer2", "lecturer2");
//			super.request("/lecturer/lecture/update", param);
//			super.checkPanicExists();
//			super.signOut();
//
//			super.signIn("student2", "student2");
//			super.request("/lecturer/lecture/update", param);
//			super.checkPanicExists();
//			super.signOut();
//
//			super.signIn("assistant2", "assistant2");
//			super.request("/lecturer/lecture/update", param);
//			super.checkPanicExists();
//			super.signOut();
//
//			super.signIn("company2", "company2");
//			super.request("/lecturer/lecture/update", param);
//			super.checkPanicExists();
//			super.signOut();
//
//			super.signIn("auditor2", "auditor2");
//			super.request("/lecturer/lecture/update", param);
//			super.checkPanicExists();
//			super.signOut();
//		}
//	}
//
//}
