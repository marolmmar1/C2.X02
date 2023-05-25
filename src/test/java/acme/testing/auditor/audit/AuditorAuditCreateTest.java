///// *
//// * EmployerJobCreateTest.java
//// *
//// * Copyright (C) 2012-2023 Rafael Corchuelo.
//// *
//// * In keeping with the traditional purpose of furthering education and research, it is
//// * the policy of the copyright owner to permit non-commercial use and redistribution of
//// * this software. It has been tested carefully, but it is not guaranteed for any particular
//// * purposes. The copyright owner does not offer any warranties or representations, nor do
//// * they accept any liabilities with respect to them.
//// */
////
//
//package acme.testing.auditor.audit;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//
//import acme.testing.TestHarness;
//
//public class AuditorAuditCreateTest extends TestHarness {
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/auditor/audit/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test100Positive(final int auditIndex, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String course, final String modeMark) {
//
//		super.signIn("auditor13", "auditor13");
//
//		super.clickOnMenu("Auditor", "Audit List");
//		super.checkListingExists();
//
//		super.clickOnButton("Create");
//		super.fillInputBoxIn("code", code);
//		super.fillInputBoxIn("conclusion", conclusion);
//		super.fillInputBoxIn("strongPoints", strongPoints);
//		super.fillInputBoxIn("weakPoints", weakPoints);
//		super.fillInputBoxIn("course", course);
//		super.clickOnSubmit("Create");
//
//		super.clickOnMenu("Auditor", "Audit List");
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//		super.checkColumnHasValue(auditIndex, 0, code);
//		super.checkColumnHasValue(auditIndex, 1, conclusion);
//		super.checkColumnHasValue(auditIndex, 2, modeMark);
//
//		super.clickOnListingRecord(auditIndex);
//		super.checkFormExists();
//		super.checkInputBoxHasValue("code", code);
//		super.checkInputBoxHasValue("conclusion", conclusion);
//		super.checkInputBoxHasValue("strongPoints", strongPoints);
//		super.checkInputBoxHasValue("weakPoints", weakPoints);
//		super.checkInputBoxHasValue("course", course);
//		super.checkInputBoxHasValue("modeMark", modeMark);
//
//		super.clickOnButton("Audit Records");
//		super.checkListingExists();
//		super.checkListingEmpty();
//
//		super.signOut();
//	}
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/auditor/audit/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test200Negative(final int auditIndex, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String course, final String modeMark) {
//
//		super.signIn("auditor13", "auditor13");
//
//		super.clickOnMenu("Auditor", "Audit List");
//		super.clickOnButton("Create");
//		super.checkFormExists();
//
//		super.fillInputBoxIn("code", code);
//		super.fillInputBoxIn("conclusion", conclusion);
//		super.fillInputBoxIn("strongPoints", strongPoints);
//		super.fillInputBoxIn("weakPoints", weakPoints);
//		super.fillInputBoxIn("course", course);
//		super.clickOnSubmit("Create");
//		super.checkErrorsExist();
//
//		super.signOut();
//	}
//
//	@Test
//	public void test300Hacking() {
//
//		super.checkLinkExists("Sign in");
//		super.request("/auditor/audit/create");
//		super.checkPanicExists();
//
//		super.signIn("administrator", "administrator");
//		super.request("/auditor/audit/create");
//		super.checkPanicExists();
//		super.signOut();
//
//		super.signIn("student1", "student1");
//		super.request("/auditor/audit/create");
//		super.checkPanicExists();
//		super.signOut();
//
//		super.signIn("company1", "company1");
//		super.request("/auditor/audit/create");
//		super.checkPanicExists();
//		super.signOut();
//
//		super.signIn("lecturer1", "lecturer1");
//		super.request("/auditor/audit/create");
//		super.checkPanicExists();
//		super.signOut();
//
//		super.signIn("assistant1", "assistant1");
//		super.request("/auditor/audit/create");
//		super.checkPanicExists();
//		super.signOut();
//	}
//
//}
