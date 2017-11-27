package controllers;

import bean.AdminSearchInfo;
import io.ebean.PagedList;
import models.AdminEntity;
import models.AuthorisedUser;
import models.filter.AuthorisedUserFilter;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;
import repository.AdminRepository;
import service.AuthoriseService;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.concurrent.CompletionStage;

public class ApplicationController extends Controller {

    private final AdminRepository adminRepository;
    private final FormFactory formFactory;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    AuthoriseService authoriseService;

    @Inject
    public ApplicationController(FormFactory formFactory,
                                 AdminRepository adminRepository,
                                 HttpExecutionContext httpExecutionContext) {
        this.formFactory = formFactory;
        this.httpExecutionContext = httpExecutionContext;
        this.adminRepository = adminRepository;
    }

    public Result javascriptRoutes() {
        return ok(
                JavaScriptReverseRouter.create("jsRoutes",
                        routes.javascript.ApplicationController.doAjaxRequestAdminList()
                )
        ).as("text/javascript");
    }

    public CompletionStage<Result> searchAdminList() {
        Form<AdminEntity> form = formFactory.form(AdminEntity.class).bindFromRequest();
        AdminSearchInfo searchInfo = AdminSearchInfo.parse(form);

        Logger.of("application").debug("FILTER: " + searchInfo.toJsonString());

        return adminRepository.searchAdmin(searchInfo, 0)
                .thenApplyAsync(e -> ok(views.html.admin.adminList.render(form, e, searchInfo.toJsonString()))
                        , httpExecutionContext.current());
    }

    public CompletionStage<Result> doAjaxRequestAdminList(){
        String filter = request().getQueryString("filter");
        AdminSearchInfo searchInfo = Json.fromJson(Json.parse(filter), AdminSearchInfo.class);
        Logger.of("application").debug("FILTER: " + searchInfo.toJsonString());

        String page = request().getQueryString("page");
        Logger.of("application").debug("PAGE: " + page);

        return adminRepository.searchAdmin(searchInfo, Integer.parseInt(page))
                .thenApplyAsync(e -> ok(views.html.admin.list_panel.render(e, searchInfo.toJsonString()))
                        , httpExecutionContext.current());
    }
         public Result user() {

        Form <AuthorisedUserFilter> userForm = formFactory.form(AuthorisedUserFilter.class).bindFromRequest();
        AuthorisedUserFilter userFilter = userForm.bindFromRequest().get();
                 //just for test
             userFilter.setRoleId(Arrays.asList(1, 2));
        PagedList<AuthorisedUser> userList = authoriseService.filter(userFilter);
             return ok(views.html.user.list.render(userList,userForm));
    }
}
            
