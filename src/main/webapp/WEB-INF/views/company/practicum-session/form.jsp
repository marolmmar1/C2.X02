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
	
	<acme:input-textbox code="company.practicumSession.form.label.title" path="title"/>
	<acme:input-textarea code="company.practicumSession.form.label.abstracts" path="abstracts"/>	
	<acme:input-moment code="company.practicumSession.form.label.inicialPeriod" path="inicialPeriod"/>
	<acme:input-moment code="company.practicumSession.form.label.finalPeriod" path="finalPeriod"/>
	<acme:input-url code="company.practicumSession.form.label.link" path="link"/>
	<acme:input-checkbox code="company.practicumSession.form.label.exceptional" path="exceptional" readonly="true"/>
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
				<acme:submit code="company.practicumSession.form.button.create" action="/company/practicum-session/create?practicumId=${practicumId}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')&& draftMode == true}">
			<acme:submit code="company.practicumSession.form.button.update" action="/company/practicum-session/update"/>
			<acme:submit code="company.practicumSession.form.button.delete" action="/company/practicum-session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create-exceptional'}">
			<acme:submit code="company.practicumSession.form.button.create-exceptional" action="/company/practicum-session/create-exceptional?practicumId=${practicumId}"/>	
		</jstl:when>
	</jstl:choose>
	
</acme:form>

