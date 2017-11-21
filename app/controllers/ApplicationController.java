package controllers;

import models.AdminEntity;
import play.Logger;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import repository.CompanyRepository;
import repository.ComputerRepository;
import router.Routes;

import javax.inject.Inject;

/**
 * Manage a database of computers
 */
public class ApplicationController extends Controller {

    private final ComputerRepository computerRepository;
    private final CompanyRepository companyRepository;
    private final FormFactory formFactory;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public ApplicationController(FormFactory formFactory,
                                 ComputerRepository computerRepository,
                                 CompanyRepository companyRepository,
                                 HttpExecutionContext httpExecutionContext) {
        this.computerRepository = computerRepository;
        this.formFactory = formFactory;
        this.companyRepository = companyRepository;
        this.httpExecutionContext = httpExecutionContext;
    }

    public Result index() {
        return ok("OK");
    }

    public Result allAdminList() {

        //TODO: load data
        return ok(views.html.admin.adminList.render(null, 200, 1, 150));
    }

    public Result searchAdminList() {
        //TODO
        return ok(views.html.admin.adminList.render(null, 200, 1, 150));
    }

    public Result responseAjax() {
        //TODO
        return ok(views.html.admin.list_panel.render(null, 200, 1, 150, "abc"));
    }


   /* *//**
     * This result directly redirect to application home.
     *//*
    private Result GO_HOME = Results.redirect(
            ApplicationController.list(0, "name", "asc", "")
    );

    *//**
     * Handle default path requests, redirect to computers list
     *//*
    public Result index() {
        return GO_HOME;
    }

    *//**
     * Display the paginated list of computers.
     *
     * @param page   Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order  Sort order (either asc or desc)
     * @param filter Filter applied on computer names
     *//*
    public CompletionStage<Result> list(int page, String sortBy, String order, String filter) {
        // Run a db operation in another thread (using DatabaseExecutionContext)
        return computerRepository.page(page, 10, sortBy, order, filter).thenApplyAsync(list -> {
            // This is the HTTP rendering thread context
            return ok(views.html.computer.list.render(list, sortBy, order, filter));
        }, httpExecutionContext.current());
    }

    *//**
     * Display the 'edit form' of a existing Computer.
     *
     * @param id Id of the computer to edit
     *//*
    public CompletionStage<Result> edit(Long id) {

        // Run a db operation in another thread (using DatabaseExecutionContext)
        CompletionStage<Map<String, String>> companiesFuture = companyRepository.options();

        // Run the lookup also in another thread, then combine the results:
        return computerRepository.lookup(id).thenCombineAsync(companiesFuture, (computerOptional, companies) -> {
            // This is the HTTP rendering thread context
            Computer c = computerOptional.get();
            Form<Computer> computerForm = formFactory.form(Computer.class).fill(c);
            return ok(views.html.computer.editForm.render(id, computerForm, companies));
        }, httpExecutionContext.current());
    }

    *//**
     * Handle the 'edit form' submission
     *
     * @param id Id of the computer to edit
     *//*
    public CompletionStage<Result> update(Long id) throws PersistenceException {
        Form<Computer> computerForm = formFactory.form(Computer.class).bindFromRequest();
        if (computerForm.hasErrors()) {
            // Run companies db operation and then render the failure case
            return companyRepository.options().thenApplyAsync(companies -> {
                // This is the HTTP rendering thread context
                return badRequest(views.html.computer.editForm.render(id, computerForm, companies));
            }, httpExecutionContext.current());
        } else {
            Computer newComputerData = computerForm.get();
            // Run update operation and then flash and then redirect
            return computerRepository.update(id, newComputerData).thenApplyAsync(data -> {
                // This is the HTTP rendering thread context
                flash("success", "Computer " + newComputerData.getName() + " has been updated");
                return GO_HOME;
            }, httpExecutionContext.current());
        }
    }

    *//**
     * Display the 'new computer form'.
     *//*
    public CompletionStage<Result> create() {
        Form<Computer> computerForm = formFactory.form(Computer.class);
        // Run companies db operation and then render the form
        return companyRepository.options().thenApplyAsync((Map<String, String> companies) -> {
            // This is the HTTP rendering thread context
            return ok(views.html.computer.createForm.render(computerForm, companies));
        }, httpExecutionContext.current());
    }

    *//**
     * Handle the 'new computer form' submission
     *//*
    public CompletionStage<Result> save() {
        Form<Computer> computerForm = formFactory.form(Computer.class).bindFromRequest();
        if (computerForm.hasErrors()) {
            // Run companies db operation and then render the form
            return companyRepository.options().thenApplyAsync(companies -> {
                // This is the HTTP rendering thread context
                return badRequest(views.html.computer.createForm.render(computerForm, companies));
            }, httpExecutionContext.current());
        }

        Computer computer = computerForm.get();
        // Run insert db operation, then redirect
        return computerRepository.insert(computer).thenApplyAsync(data -> {
            // This is the HTTP rendering thread context
            flash("success", "Computer " + computer.getName() + " has been created");
            return GO_HOME;
        }, httpExecutionContext.current());
    }

    *//**
     * Handle computer deletion
     *//*
    public CompletionStage<Result> delete(Long id) {
        // Run delete db operation, then redirect
        return computerRepository.delete(id).thenApplyAsync(v -> {
            // This is the HTTP rendering thread context
            flash("success", "Computer has been deleted");
            return GO_HOME;
        }, httpExecutionContext.current());
    }
*/
}
            
