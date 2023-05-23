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

package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.entities.Course;
import acme.testing.TestHarness;

public class StudentEnrolmentCreateTest extends TestHarness {

		@ParameterizedTest
		@CsvFileSource(resources = "/student/enrolment/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
		public void test100Positive(final int enrolmentIndex, final String code, final String course, final String motivation,final String goals, 
				final String holderName, final String lowerNibble, final String creditCard, final String cvc, final String expiryDate) {
			
			super.signIn("student2", "student2");
	
			super.clickOnMenu("Student", "List of enrolments");
			super.checkListingExists();
	
			super.clickOnButton("Create enrolment");
			super.fillInputBoxIn("code", code);
			super.fillInputBoxIn("motivation", motivation);
			super.fillInputBoxIn("goals", goals);
			super.fillInputBoxIn("holderName", holderName);
			super.fillInputBoxIn("lowerNibble", lowerNibble);
			super.fillInputBoxIn("creditCard", creditCard);
			super.fillInputBoxIn("cvc", cvc);
			super.fillInputBoxIn("expiryDate", expiryDate);
			super.clickOnSubmit("Create ");
			
			
			super.clickOnMenu("Student", "List of enrolments");
			super.checkListingExists();
			super.sortListing(0, "asc");
			super.checkColumnHasValue(enrolmentIndex, 0, code);
			super.checkColumnHasValue(enrolmentIndex, 1, course);
	
			super.clickOnListingRecord(enrolmentIndex);
			super.checkFormExists();
			super.checkInputBoxHasValue("code", code);
			super.checkInputBoxHasValue("motivation", motivation);
			super.checkInputBoxHasValue("goals", goals);
			super.checkInputBoxHasValue("holderName", holderName);
			super.checkInputBoxHasValue("lowerNibble", lowerNibble);
			super.checkInputBoxHasValue("creditCard", creditCard);
			super.checkInputBoxHasValue("cvc", cvc);
			super.checkInputBoxHasValue("expiryDate", expiryDate);

		
			super.clickOnButton("Activities");
			super.checkListingExists();
			super.checkListingEmpty();
	
			super.signOut();
		}
	
		@ParameterizedTest
		@CsvFileSource(resources = "/student/enrolment/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
		public void test200Negative(final int enrolmentIndex, final String code, final Course course, final String motivation,final String goals, 
				final String holderName, final String lowerNibble, final String creditCard, final String cvc, final String expiryDate) {
	
			super.signIn("student2", "student2");
	
			super.clickOnMenu("Student", "List of enrolments");
			super.clickOnButton("Create ");
			super.checkFormExists();
	
			super.fillInputBoxIn("code", code);
			super.fillInputBoxIn("motivation", motivation);
			super.fillInputBoxIn("goals", goals);
			super.fillInputBoxIn("holderName", holderName);
			super.fillInputBoxIn("lowerNibble", lowerNibble);
			super.fillInputBoxIn("creditCard", creditCard);
			super.fillInputBoxIn("cvc", cvc);
			super.fillInputBoxIn("expiryDate", expiryDate);
			super.clickOnSubmit("Create ");
			super.checkErrorsExist();
	
			super.signOut();
		}
	
		@Test
		public void test300Hacking() {
	
			super.checkLinkExists("Sign in");
			super.request("/student/enrolment/create");
			super.checkPanicExists();
	
			super.signIn("administrator", "administrator");
			super.request("/student/enrolment/create");
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("lecturer1", "lecturer1");
			super.request("/lecturer/course/create");
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("company1", "company1");
			super.request("/student/enrolment/create");
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("assistant1", "assistant1");
			super.request("/student/enrolment/create");
			super.checkPanicExists();
			super.signOut();
	
			super.signIn("auditor1", "auditor1");
			super.request("/student/enrolment/create");
			super.checkPanicExists();
			super.signOut();
		}

}
