///// *
//// * EmployerApplicationShowTest.java
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
//package acme.testing.auditor.auditingRecord;
//
//import java.util.Collection;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import acme.entities.AuditingRecord;
//import acme.testing.TestHarness;
//
//public class AuditorAuditingRecordShowTest extends TestHarness {
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
//	@CsvFileSource(resources = "/auditor/auditing-record/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test100Positive(final int auditIndex, final String code, final int auditingRecordRecordIndex, final String subject, final String assessment, final String markType, final String initialPeriod, final String finalPeriod, final String link) {
//
//		super.signIn("auditor1", "auditor1");
//
//		super.clickOnMenu("Auditor", "Audit List");
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//		super.clickOnListingRecord(auditIndex);
//		super.clickOnButton("Audit Records");
//		super.checkListingExists();
//		super.clickOnListingRecord(auditingRecordRecordIndex);
//		super.checkFormExists();
//
//		super.checkInputBoxHasValue("subject", subject);
//		super.checkInputBoxHasValue("assessment", assessment);
//		super.checkInputBoxHasValue("markType", markType);
//		super.checkInputBoxHasValue("initialPeriod", initialPeriod);
//		super.checkInputBoxHasValue("finalPeriod", finalPeriod);
//		super.checkInputBoxHasValue("link", link);
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
//
//		Collection<AuditingRecord> auditingRecords;
//		String param;
//
//		super.signIn("auditor2", "auditor2");
//		auditingRecords = this.repository.findManyAuditingRecordByAuditorUsername("auditor2");
//		super.signOut();
//		for (final AuditingRecord auditingRecord : auditingRecords)
//			if (auditingRecord.getAudit().isDraftMode()) {
//				param = String.format("id=%d", auditingRecord.getAudit().getId());
//
//				super.checkLinkExists("Sign in");
//				super.request("/auditor/auditing-record/show", param);
//				super.checkPanicExists();
//
//				super.signIn("administrator", "administrator");
//				super.request("/auditor/auditing-record/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("auditor1", "auditor1");
//				super.request("/auditor/auditing-record/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("lecturer1", "lecturer1");
//				super.request("/auditor/auditing-record/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("student1", "student1");
//				super.request("/auditor/auditing-record/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("company1", "company1");
//				super.request("/auditor/auditing-record/show", param);
//				super.checkPanicExists();
//				super.signOut();
//
//				super.signIn("assistant1", "assistant1");
//				super.request("/auditor/auditing-record/show", param);
//				super.checkPanicExists();
//				super.signOut();
//			}
//	}
//
//}
