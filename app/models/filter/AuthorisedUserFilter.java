package models.filter;

import models.filter.query.annotation.GroupExpression;
import models.filter.query.annotation.GroupsExpression;
import models.filter.query.annotation.Expression;
import models.filter.query.enu.Operator;

import java.util.List;

@GroupsExpression({@GroupExpression(name = "role", opatator = Operator.OR), @GroupExpression(name = "user")})
public class AuthorisedUserFilter extends PagedListImp {
    @Expression(value = "userName", groupName = "user")
    private String userName;

    @Expression(value = "roles.name", groupName = "role",operator = "eq")
    private String roleName;

    @Expression(value = "roles.id", groupName = "role")
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
