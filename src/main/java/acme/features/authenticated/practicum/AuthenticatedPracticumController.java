
package acme.features.authenticated.practicum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedPracticumController extends AbstractController<Authenticated, Practicum> {

	//Internal state ----------------------------------
	@Autowired
	protected AuthenticatedPracticumListService	listService;

	@Autowired
	protected AuthenticatedPracticumShowService	showService;

	//Contructors ---------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-all", "list", this.listService);
		super.addBasicCommand("show", this.showService);

	}
}
