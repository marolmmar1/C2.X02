/*
 * AuthenticatedUserAccountController.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.userAccount;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.framework.components.accounts.UserAccount;
import acme.framework.controllers.AbstractController;
import acme.roles.Auditor;

@Controller
public class AuditorUserAccountController extends AbstractController<Auditor, UserAccount> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorUserAccountListAllService	listAllService;

	@Autowired
	protected AuditorUserAccountUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("list-all", "list", this.listAllService);
	}

}
