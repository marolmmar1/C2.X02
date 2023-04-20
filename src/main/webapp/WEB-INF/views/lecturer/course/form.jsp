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

<jslt:set var=draftMode" value"${draftMode }"/>

<acme:form readonly="${draftMode == false }">

	<acme:input-textbox code="lecturer.course.form.label.code" path="code"/>	
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.course.form.label.abstracts" path="abstracts"/>
	<acme:input-money code="lecturer.course.form.label.price" path="price"/>
	<acme:input-textbox code="lecturer.course.form.label.nature" path="nature"/>	
	<acme:input-url code="lecturer.course.form.label.link" path="link"/>
	
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' ||_command == 'update' || _command == 'delete'}">
			<acme:input-checkbox code="lecturer.course.form.label.draftMode" path="draftMode"/>			
			<acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update"/>
			<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
			<acme:button code="lecturer.lecture.list.button" action="/lecturer/lecture/list?id=${id }"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>

