///*
// * EmployerJobShowTest.java
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
//package acme.testing.lecturer.course;
//
//import java.util.Collection;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import acme.entities.Course;
//import acme.testing.TestHarness;
//
//public class LecturerCourseShowTest extends TestHarness {
//
//	// Internal state ---------------------------------------------------------
//
//	@Autowired
//	protected LecturerCourseRepositoryTest repository;
//
//	// Test data --------------------------------------------------------------
//
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/lecturer/course/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test100Positive(final int courseIndex, final String code, final String title, final String price, final String abstracts, final String link, final String nature) {
//
//		super.signIn("lecturer3", "lecturer3");
//
//		super.clickOnMenu("Lecturer", "List of courses");
//		super.sortListing(0, "asc");
//		super.clickOnListingRecord(courseIndex);
//		super.checkFormExists();
//
//		super.checkInputBoxHasValue("code", code);
//		super.checkInputBoxHasValue("title", title);
//		super.checkInputBoxHasValue("price", price);
//		super.checkInputBoxHasValue("abstracts", abstracts);
//		super.checkInputBoxHasValue("link", link);
//		super.checkInputBoxHasValue("nature", nature);
//
//		super.signOut();
//	}
//
//	@Test
//	public void test300Hacking() {
//
//		Collection<Course> courses;
//		String param;
//
//		courses = this.repository.findManyCoursesByLecturerUsername("lecturer3");
//		for (final Course course : courses)
//			if (course.isDraftMode()) {
//				param = String.format("id=%d", course.getId());
//
//				super.checkLinkExists("Sign in");
//				super.request("/lecturer/course/show", param);
//				super.checkPanicExists();
//
//				super.signIn("administrator", "administrator");
//				super.request("/lecturer/course/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("assistant5", "assistant5");
//				super.request("/lecturer/course/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("lecturer1", "lecturer1");
//				super.request("/lecturer/course/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("student1", "student1");
//				super.request("/lecturer/course/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("company1", "company1");
//				super.request("/lecturer/course/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("auditor1", "auditor1");
//				super.request("/lecturer/course/show", param);
//				super.checkPanicExists();
//				super.signOut();
//			}
//	}
//
//}
