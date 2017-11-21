package bean;

import models.AdminEntity;
import org.apache.commons.lang3.StringUtils;
import play.data.Form;
import play.libs.Json;

public class AdminSearchInfo extends PagingInfo {

    public static final int MAX_RECORD_PER_PAGE = 3;

    private String adminId;
    private String adminType;
    private String companyName;
    private String name;
    private String email;
    private String activeStatus;
    private String branch;
    private String username;
    private String nameKana;

    public AdminSearchInfo(){
        super(MAX_RECORD_PER_PAGE);
    }

    public static AdminSearchInfo parse(Form<AdminEntity> form){
        AdminSearchInfo info = new AdminSearchInfo();

        String adminId = form.get().getAdminId();
        if(!StringUtils.isBlank(adminId)){
            info.setAdminId(adminId);
        }

        String adminType = form.get().getAdminType();
        if(!StringUtils.isBlank(adminType)){
            info.setAdminType(adminType);
        }

        String companyName = form.get().getCompanyName();
        if(!StringUtils.isBlank(companyName)){
            info.setCompanyName(companyName);
        }

        String name = form.get().getName();
        if(!StringUtils.isBlank(name)){
            info.setName(name);
        }

        String email = form.get().getEmail();
        if(!StringUtils.isBlank(email)){
            info.setEmail(email);
        }

        String activeStatus = form.get().getActiveStatus();
        if(!StringUtils.isBlank(activeStatus)){
            info.setActiveStatus(activeStatus);
        }

        String branch = form.get().getBranch();
        if(!StringUtils.isBlank(branch)){
            info.setBranch(branch);
        }

        String username = form.get().getUsername();
        if(!StringUtils.isBlank(username)){
            info.setUsername(username);
        }

        String nameKana = form.get().getNameKana();
        if(!StringUtils.isBlank(nameKana)){
            info.setNameKana(nameKana);
        }

        return info;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminType() {
        return adminType;
    }

    public void setAdminType(String adminType) {
        this.adminType = adminType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNameKana() {
        return nameKana;
    }

    public void setNameKana(String nameKana) {
        this.nameKana = nameKana;
    }

    public String toString(){
        return Json.toJson(this).textValue();
    }

}
