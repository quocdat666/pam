package repository;

import bean.AdminSearchInfo;
import io.ebean.*;
import models.AdminEntity;
import models.AuthorisedUser;
import org.apache.commons.lang3.StringUtils;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 *
 */
public class AdminRepository {

    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public AdminRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext executionContext) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    public CompletionStage<PagedList<AdminEntity>> searchAdmin(AdminSearchInfo searchInfo, int page) {
        return supplyAsync(() -> createAdminEntityQuery(searchInfo, page).findPagedList(),
                executionContext);

        ebeanServer.findNative(ABC.class, "").findPagedList();
    }

    private Query<AdminEntity> createAdminEntityQuery(AdminSearchInfo searchInfo, int page) {
        Query<AdminEntity> adminEntityQuery = ebeanServer.find(AdminEntity.class);
        ExpressionList<AdminEntity> expressionList = createAdminEntityExpressionList(searchInfo, adminEntityQuery);
        return expressionList.orderBy("adminId")
                .setMaxRows(AdminSearchInfo.MAX_RECORD_PER_PAGE)
                .setFirstRow(page * AdminSearchInfo.MAX_RECORD_PER_PAGE);
    }

    private ExpressionList<AdminEntity> createAdminEntityExpressionList(AdminSearchInfo searchInfo, Query<AdminEntity> adminEntityQuery) {
        ExpressionList<AdminEntity> expressionList = adminEntityQuery.where();

        String adminId = searchInfo.getAdminId();
        if(!StringUtils.isEmpty(adminId)){
            expressionList.eq("adminId", adminId);
        }

        String adminType = searchInfo.getAdminType();
        if(!StringUtils.isEmpty(adminType)){
            expressionList.eq("adminType", adminType);
        }

        String companyName = searchInfo.getCompanyName();
        if(!StringUtils.isEmpty(companyName)){
            expressionList.eq("companyName", companyName);
        }

        String name = searchInfo.getName();
        if(!StringUtils.isEmpty(name)){
            expressionList.eq("name", name);
        }

        String email = searchInfo.getEmail();
        if(!StringUtils.isEmpty(email)){
            expressionList.eq("email", email);
        }

        String activeStatus = searchInfo.getActiveStatus();
        if(!StringUtils.isEmpty(activeStatus)){
            expressionList.eq("activeStatus", activeStatus);
        }

        String branch = searchInfo.getBranch();
        if(!StringUtils.isEmpty(branch)){
            expressionList.eq("branch", branch);
        }

        String username = searchInfo.getUsername();
        if(!StringUtils.isEmpty(username)){
            expressionList.eq("username", username);
        }

        String nameKana = searchInfo.getNameKana();
        if(!StringUtils.isEmpty(nameKana)){
            expressionList.eq("nameKana", nameKana);
        }

        return expressionList;
    }

}
