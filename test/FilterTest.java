import io.ebean.ExpressionList;
import io.ebean.Finder;
import io.ebean.PagedList;
import models.AuthorisedUser;
import models.filter.AuthorisedUserFilter;
import models.filter.utils.FilterUtils;
import org.junit.Assert;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;
import service.AuthoriseService;

import java.util.Arrays;

public class FilterTest extends WithApplication {
    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void buildFilter() {
        AuthorisedUserFilter userFilter = new AuthorisedUserFilter();
        userFilter.setUserName("dattq");
        userFilter.setRoleName("foo");
        userFilter.setRoleId(Arrays.asList(1,2));
        userFilter.setPageSize(1);
        try {
            ExpressionList<AuthorisedUser> exs = FilterUtils.buildQuery(userFilter, new Finder<>(AuthorisedUser.class));
            Assert.assertNotNull(exs);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.assertTrue(false);

        }
    }

    @Test
    public void simpleSearch() {
        AuthoriseService authoriseService = app.injector().instanceOf(AuthoriseService.class);
        AuthorisedUserFilter userFilter = new AuthorisedUserFilter();
        userFilter.setUserName("dattq");
        userFilter.setRoleName("foo");
        userFilter.setRoleId(Arrays.asList(1,2));
        userFilter.setPageSize(1);
        PagedList<AuthorisedUser> userList = authoriseService.filter(userFilter);
        Assert.assertNotNull(userList.getList());
    }
}
