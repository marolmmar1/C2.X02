/*
 * Dashboard.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Double						numPrincipals;
	Double						ratioEmailPeeps;
	Double						ratioLinkPeeps;
	Double						ratioCriticalBulletin;
	Double						ratioNonCriticalBulletin;
	Double						averageBudgetPerOffers;
	Double						minimumBudgetPerOffers;
	Double						maximumBudgetPerOffers;
	Double						standardDeviationBudgetPerOffers;
	Double						averageTotalNumberPerNotes;
	Double						minimumTotalNumberPerNotes;
	Double						maximumTotalNumberPerNotes;
	Double						standardDeviationTotalNumberPerNotes;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
