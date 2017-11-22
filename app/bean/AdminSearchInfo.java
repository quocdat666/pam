package bean;

import models.AdminEntity;
import play.data.Form;

public class AdminSearchInfo extends PagingInfo {

    public static final int MAX_RECORD_PER_PAGE = 4;

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
        //BeanUtils.copyProperties(info, form.get());

        info.setAdminId(form.get().getAdminId());
        info.setAdminType(form.get().getAdminType());
        info.setCompanyName( form.get().getCompanyName());
        info.setName(form.get().getName());
        info.setEmail(form.get().getEmail());
        info.setActiveStatus(form.get().getActiveStatus());
        info.setBranch(form.get().getBranch());
        info.setUsername(form.get().getUsername());
        info.setNameKana(form.get().getNameKana());

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

}
