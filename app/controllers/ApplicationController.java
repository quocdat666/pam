package controllers;

import bean.AdminSearchInfo;
import io.ebean.PagedList;
import models.AdminEntity;
import org.slf4j.Marker;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.routing.JavaScriptReverseRouter;
import repository.AdminRepository;

import javax.inject.Inject;
import java.util.Map;

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
                        routes.javascript.ApplicationController.doAjaxRequestAdminList()
                )
        ).as("text/javascript");
    }

    public Result viewAdminList() {
        Form<AdminEntity> form = formFactory.form(AdminEntity.class);
        AdminSearchInfo searchInfo = new AdminSearchInfo();
        return ok(views.html.admin.adminList.render(form, fetchAdminList(searchInfo), searchInfo));
    }

    public Result searchAdminList() {
        Form<AdminEntity> form = formFactory.form(AdminEntity.class).bindFromRequest();
        AdminSearchInfo searchInfo = AdminSearchInfo.parse(form);
        return ok(views.html.admin.adminList.render(form, fetchAdminList(searchInfo), searchInfo));
    }

    public Result doAjaxRequestAdminList(){
        Map<String, String[]> parameters = request().body().asFormUrlEncoded();
        String filter = parameters.get("filter")[0];
        AdminSearchInfo searchInfo = Json.fromJson(Json.parse(filter), AdminSearchInfo.class);
        Logger.of("application").debug("AAA: " + searchInfo.toJsonString());

        return ok(views.html.admin.list_panel.render(fetchAdminList(searchInfo), searchInfo));
    }

    private PagedList<AdminEntity> fetchAdminList(AdminSearchInfo searchInfo) {
        PagedList<AdminEntity> pagedList = null;
        try {
            pagedList = adminRepository.searchAdmin(searchInfo).toCompletableFuture().get();
            searchInfo.setTotalRow(pagedList.getTotalCount());
            searchInfo.setPageSize(pagedList.getList().size());
        } catch (Exception e) {
            Logger.of("application").debug(Marker.ANY_MARKER, "Load Admin list error", e);
        }
        return pagedList;
    }

}
            
