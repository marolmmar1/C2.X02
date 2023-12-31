
package acme.features.company.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.forms.CompanyDashboard;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanyDashboardController extends AbstractController<Company, CompanyDashboard> {

	@Autowired
	protected CompanyDashboardShowService service;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.service);
	}

}
