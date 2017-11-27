package models.filter;

import io.ebean.PagedList;
import models.filter.query.annotation.GroupFilter;
import models.filter.query.annotation.GroupsFilter;
import models.filter.query.annotation.QueryFilter;
import models.filter.query.enu.Operator;

import java.util.List;
import java.util.concurrent.Future;

@GroupsFilter({@GroupFilter(name = "role", opatator = Operator.OR), @GroupFilter(name = "user")})
public class AuthorisedUserFilter extends PagedListImp {
    @QueryFilter(value = "userName", groupName = "user")
    private String userName;

    @QueryFilter(value = "roles.name", groupName = "role",operator = "eq")
    private String roleName;

    @QueryFilter(value = "roles.id", groupName = "role")
    private List<Integer> roleId;

    public List<Integer> getRoleId() {
        return roleId;
    }

    public void setRoleId(List<Integer> roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
