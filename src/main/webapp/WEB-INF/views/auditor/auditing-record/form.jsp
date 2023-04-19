<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:input-textbox code="auditor.auditingRecords.form.label.subject" path="subject"/>
	<acme:input-textbox code="auditor.auditingRecords.form.label.assessment" path="assessment"/>
	<acme:input-select code="auditor.auditingRecords.form.label.markType" path="markType" choices="${markTypes}"/>
	<acme:input-moment code="auditor.auditingRecords.form.label.initialPeriod" path="initialPeriod"/>	
	<acme:input-moment code="auditor.auditingRecords.form.label.finalPeriod" path="finalPeriod"/>
	<acme:input-url code="auditor.auditingRecords.form.label.link" path="link"/>
	
	<jstl:choose>
	<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditingRecords.form.button.create" action="/auditor/auditing-record/create?auditId=${auditId}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="auditor.auditingRecords.form.button.update" action="/auditor/auditing-record/update"/>
			<acme:submit code="auditor.auditingRecords.form.button.delete" action="/auditor/auditing-record/delete"/>
		</jstl:when>		
	</jstl:choose>	
	
	

</acme:form>
