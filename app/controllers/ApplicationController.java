package controllers;

import bean.AdminSearchInfo;
import com.fasterxml.jackson.databind.JsonNode;
import io.ebean.PagedList;
import models.AdminEntity;
import org.slf4j.Marker;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.filters.csrf.AddCSRFToken;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;
import repository.AdminRepository;
import repository.ComputerRepository;
import router.Routes;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class ApplicationController extends Controller {

    private final AdminRepository adminRepository;
    private final FormFactory formFactory;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public ApplicationController(FormFactory formFactory,
                                 AdminRepository adminRepository,
                                 HttpExecutionContext httpExecutionContext) {
        this.formFactory = formFactory;
        this.httpExecutionContext = httpExecutionContext;
        this.adminRepository = adminRepository;
    }

    public Result index() {
        return ok("OK");
    }

    public Result javascriptRoutes() {
        return ok(
                JavaScriptReverseRouter.create("jsRoutes",
                        routes.javascript.ApplicationController.searchAdminListByPage()
                )
        ).as("text/javascript");
    }

    public Result viewAdminList() {
        return searchAdminList(formFactory.form(AdminEntity.class), new AdminSearchInfo());
    }

    public Result searchAdminList() {
        Form<AdminEntity> form = formFactory.form(AdminEntity.class).bindFromRequest();
        return searchAdminList(form, AdminSearchInfo.parse(form));
    }

    private Result searchAdminList(Form<AdminEntity> form, AdminSearchInfo searchInfo) {
        PagedList<AdminEntity> pagedList = null;
        try {
            pagedList = adminRepository.searchAdmin(searchInfo).toCompletableFuture().get();
        } catch (Exception e) {
            Logger.of("application").debug(Marker.ANY_MARKER, "Load Admin list error", e);
        }
        return ok(views.html.admin.adminList.render(form, pagedList, searchInfo.getFirstRow(), searchInfo.toString()));
    }

    public Result searchAdminListByPage(){
        Map<String, String[]> parameters = request().body().asFormUrlEncoded();
        String var1 = parameters.get("firstRow")[0];
        Logger.of("application").debug("VAL1: " + var1);
        return ok("");
    }

}
            
