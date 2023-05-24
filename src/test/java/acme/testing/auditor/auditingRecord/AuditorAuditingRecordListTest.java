///*
// * EmployerDutyListTest.java
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
//package acme.testing.auditor.auditingRecord;
//
//import java.util.Collection;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import acme.entities.Audit;
//import acme.testing.TestHarness;
//
//class AuditorAuditingRecordListTest extends TestHarness {
//
//	// Internal state ---------------------------------------------------------
//
//	@Autowired
//	protected AuditorAuditingRecordTestRepository repository;
//
//	// Test methods -----------------------------------------------------------
//
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/auditor/auditing-record/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test100Positive(final int auditRecordIndex, final String code, final int auditingRecordRecordIndex, final String subject) {
//
//		super.signIn("auditor1", "auditor1");
//
//		super.clickOnMenu("Auditor", "Audit List");
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//
//		super.checkColumnHasValue(auditRecordIndex, 0, code);
//		super.clickOnListingRecord(auditRecordIndex);
//		super.checkInputBoxHasValue("code", code);
//		super.clickOnButton("Audit Records");
//
//		super.checkListingExists();
//		super.checkColumnHasValue(auditingRecordRecordIndex, 0, subject);
//		super.clickOnListingRecord(auditingRecordRecordIndex);
//
//		super.signOut();
//	}
//
//	@Test
//	public void test200Negative() {
//		// HINT: there's no negative test case for this listing, since it doesn't
//		// HINT+ involve filling in any forms.
//	}
//
//	@Test
//	public void test300Hacking() {
//		Collection<Audit> audits;
//		String param;
//
//		audits = this.repository.findManyAuditsByAuditorUsername("auditor1");
//		for (final Audit audit : audits)
//			if (audit.isDraftMode()) {
//				param = String.format("id=%d", audit.getId());
//
//				super.checkLinkExists("Sign in");
//				super.request("/auditor/auditing-record/list", param);
//				super.checkPanicExists();
//
//				super.signIn("administrator", "administrator");
//				super.request("/auditor/auditing-record/list", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("auditor2", "auditor2");
//				super.request("/auditor/auditing-record/list", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("lecturer1", "lecturer1");
//				super.request("/auditor/auditing-record/list", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("student1", "student1");
//				super.request("/auditor/auditing-record/list", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("company1", "company1");
//				super.request("/auditor/auditing-record/list", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("assistant1", "assistant1");
//				super.request("/auditor/auditing-record/list", param);
//				super.checkPanicExists();
//				super.signOut();
//			}
//	}
//
//}
