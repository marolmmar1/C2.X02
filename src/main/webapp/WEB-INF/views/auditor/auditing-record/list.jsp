<%--
- list.jsp
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

<acme:list>
	<acme:list-column code="auditor.auditingRecords.list.label.subject" path="subject" width="20%"/>
	<acme:list-column code="auditor.auditingRecords.list.label.exceptional" path="exceptional" width="20%"/>
	<acme:list-column code="auditor.auditingRecords.list.label.markType" path="markType" width="20%"/>
	<jstl:choose>	 
		<jstl:when test="${_command == 'show'}">
			<acme:button code="auditor.audit.form.button.auditingRecords" action="/auditor/auditing-record/list?auditId=${id}"/>
		</jstl:when>
	</jstl:choose>			

</acme:list>
<acme:button test="${showCreate}" code="auditor.auditingRecords.create.button.auditingRecords" action="/auditor/auditing-record/create?auditId=${auditId}"/>
<acme:button test="${!showCreate}" code="auditor.auditingRecords.list.button.create-exceptional" action="/auditor/auditing-record/create-exceptional?auditId=${auditId}"/>

