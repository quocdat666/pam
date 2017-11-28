package repository;

import bean.AdminSearchInfo;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.PagedList;
import models.AdminEntity;
import play.db.ebean.EbeanConfig;
import utils.ExpressionBuildTool;
import utils.Constants;

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
        return supplyAsync(() -> fetchAdminPagedList(searchInfo, page), executionContext);
    }

    private PagedList<AdminEntity> fetchAdminPagedList(AdminSearchInfo searchInfo, int page) {
        return ExpressionBuildTool.buildExpressions(ebeanServer.find(AdminEntity.class).where(), searchInfo)
                .orderBy("adminId")
                .setMaxRows(Constants.MAX_RECORD_PER_PAGE)
                .setFirstRow(page * Constants.MAX_RECORD_PER_PAGE)
                .findPagedList();
    }

}
