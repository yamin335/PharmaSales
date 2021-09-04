package com.fantasyapps.darmahealthcare.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("employee_id")
    @Expose
    private String employeeId;
    @SerializedName("token")
    @Expose
    private Object token;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("national_id")
    @Expose
    private String nationalId;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("workstation")
    @Expose
    private String workstation;
    @SerializedName("driving_license")
    @Expose
    private String drivingLicense;
    @SerializedName("immigration_status")
    @Expose
    private String immigrationStatus;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("status")
    @Expose
    private long status;
    @SerializedName("joined_date")
    @Expose
    private String joinedDate;
    @SerializedName("confirmation_date")
    @Expose
    private String confirmationDate;
    @SerializedName("employment_status_id")
    @Expose
    private long employmentStatusId;
    @SerializedName("department_id")
    @Expose
    private long departmentId;
    @SerializedName("supervisor_id")
    @Expose
    private long supervisorId;
    @SerializedName("off_day_id")
    @Expose
    private long offDayId;
    @SerializedName("leavecategory_id")
    @Expose
    private long leavecategoryId;
    @SerializedName("salary_setting_id")
    @Expose
    private long salarySettingId;
    @SerializedName("employee_type_id")
    @Expose
    private long employeeTypeId;
    @SerializedName("employee_role_id")
    @Expose
    private long employeeRoleId;
    @SerializedName("present_address_id")
    @Expose
    private long presentAddressId;
    @SerializedName("permanent_address_id")
    @Expose
    private long permanentAddressId;
    @SerializedName("branch_id")
    @Expose
    private int branchId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getImmigrationStatus() {
        return immigrationStatus;
    }

    public void setImmigrationStatus(String immigrationStatus) {
        this.immigrationStatus = immigrationStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(String confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public long getEmploymentStatusId() {
        return employmentStatusId;
    }

    public void setEmploymentStatusId(long employmentStatusId) {
        this.employmentStatusId = employmentStatusId;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public long getOffDayId() {
        return offDayId;
    }

    public void setOffDayId(long offDayId) {
        this.offDayId = offDayId;
    }

    public long getLeavecategoryId() {
        return leavecategoryId;
    }

    public void setLeavecategoryId(long leavecategoryId) {
        this.leavecategoryId = leavecategoryId;
    }

    public long getSalarySettingId() {
        return salarySettingId;
    }

    public void setSalarySettingId(long salarySettingId) {
        this.salarySettingId = salarySettingId;
    }

    public long getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(long employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    public long getEmployeeRoleId() {
        return employeeRoleId;
    }

    public void setEmployeeRoleId(long employeeRoleId) {
        this.employeeRoleId = employeeRoleId;
    }

    public long getPresentAddressId() {
        return presentAddressId;
    }

    public void setPresentAddressId(long presentAddressId) {
        this.presentAddressId = presentAddressId;
    }

    public long getPermanentAddressId() {
        return permanentAddressId;
    }

    public void setPermanentAddressId(long permanentAddressId) {
        this.permanentAddressId = permanentAddressId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}