
package acme.features.any.peep;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;

@Controller
public class AnyPeepController extends AbstractController<Any, Peep> {

	@Autowired
	protected AnyPeepListAllService	listService;

	@Autowired
	protected AnyPeepShowService	showService;

	@Autowired
	protected AnyPeepCreateService	createService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-all", "list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
	}
}
