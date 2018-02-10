package com.orasi.bluesource.employeesPage;

import org.openqa.selenium.support.FindBy;

import com.orasi.DriverManager;
import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;

import ru.yandex.qatools.allure.annotations.Step;

public class ManageEmployeeModal {

    private OrasiDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();

    // All the page elements:

    @FindBy(id = "modal_label_1")
    private Label lblAddEmployeePopup;
    @FindBy(id = "employee_username")
    private Textbox txtUsername;
    @FindBy(id = "employee_first_name")
    private Textbox txtFirstName;
    @FindBy(id = "employee_last_name")
    private Textbox txtLastName;
    @FindBy(id = "employee_title_id")
    private Listbox lstTitle;
    @FindBy(id = "employee_role")
    private Listbox lstRole;
    @FindBy(id = "employee_manager_id")
    private Listbox lstManager;
    @FindBy(id = "employee_status")
    private Listbox lstStatus;
    @FindBy(id = "employee_location")
    private Listbox lstLocation;
    @FindBy(id = "employee_start_date")
    private Textbox txtStartDate;
    @FindBy(id = "employee_cell_phone")
    private Textbox txtCellPhone;
    @FindBy(id = "employee_office_phone")
    private Textbox txtOfficePhone;
    @FindBy(id = "employee_email")
    private Textbox txtEmail;
    @FindBy(id = "employee_im_name")
    private Textbox txtImName;
    @FindBy(id = "employee_im_client")
    private Listbox lstImClient;
    @FindBy(id = "employee_department_id")
    private Listbox lstDept;
    @FindBy(name = "commit")
    private Button btnSave;

    // *********************
    // ** Build page area **
    // *********************
    public ManageEmployeeModal() {
        this.driver = DriverManager.getDriver();
        ElementFactory.initElements(driver, this);
    }

    public boolean pageLoaded() {
        return driver.page().isElementLoaded(this.getClass(), txtUsername);
    }

    // *****************************************
    // ***Page Interactions ***
    // *****************************************

    // adds a new employee on the new employee page
    @Step("And I add a new Employee")
    public void addEmployee(String username, String firstName, String lastName, String title, String role, String manager,
            String status, String location, String startDate, String cellPhone, String officePhone,
            String email, String imName, String imClient, String dept) {
        lblAddEmployeePopup.syncEnabled();

        // Fill in the details
        txtUsername.set(username);
        txtFirstName.set(firstName);
        txtLastName.set(lastName);
        lstTitle.select(title);
        lstRole.select(role);
        lstManager.select(manager);
        lstStatus.select(status);
        lstLocation.select(location);
        txtStartDate.jsSet(startDate);
        txtCellPhone.set(cellPhone);
        txtOfficePhone.set(officePhone);
        txtEmail.set(email);
        txtImName.set(imName);
        lstImClient.select(imClient);
        lstDept.select(dept);

        // submit
        btnSave.syncEnabled();
        btnSave.submit();
    }

    public void addEmployee(Employee employee) {
        addEmployee(employee.getUsername(), employee.getFirstName(), employee.getLastName(), employee.getTitle(), employee.getRole(), employee.getManager(),
                employee.getStatus(), employee.getLocation(), employee.getStartDate(), employee.getCellPhone(), employee.getOfficePhone(), employee.getEmail(),
                employee.getImName(), employee.getImClient(), employee.getDepartment());
    }

    @Step("And I modify the Employee Details")
    public void modifyEmployee(String username, String firstName, String lastName, String title, String role, String manager,
            String status, String location, String startDate, String cellPhone, String officePhone,
            String email, String imName, String imClient, String dept) {
        lblAddEmployeePopup.syncEnabled();

        if (!txtUsername.getText().equalsIgnoreCase(username)) {
            txtUsername.set(username);
        }
        if (!txtFirstName.getText().equalsIgnoreCase(firstName)) {
            txtFirstName.set(firstName);
        }
        if (!txtLastName.getText().equalsIgnoreCase(lastName)) {
            txtLastName.set(lastName);
        }
        if (!lstTitle.getFirstSelectedOption().getText().equalsIgnoreCase(title)) {
            lstTitle.select(title);
        }
        if (!lstRole.getFirstSelectedOption().getText().equalsIgnoreCase(role)) {
            lstRole.select(role);
        }
        if (!lstManager.getFirstSelectedOption().getText().equalsIgnoreCase(manager)) {
            lstManager.select(manager);
        }
        if (!lstStatus.getFirstSelectedOption().getText().equalsIgnoreCase(status)) {
            lstStatus.select(status);
        }
        if (!lstLocation.getFirstSelectedOption().getText().equalsIgnoreCase(location)) {
            lstLocation.select(location);
        }
        if (!txtStartDate.getText().equalsIgnoreCase(startDate)) {
            txtStartDate.safeSet(startDate);
        }
        if (!txtCellPhone.getText().equalsIgnoreCase(cellPhone)) {
            txtCellPhone.set(cellPhone);
        }
        if (!txtOfficePhone.getText().equalsIgnoreCase(officePhone)) {
            txtOfficePhone.set(officePhone);
        }
        if (!txtEmail.getText().equalsIgnoreCase(email)) {
            txtEmail.set(email);
        }
        if (!txtImName.getText().equalsIgnoreCase(imName)) {
            txtImName.set(imName);
        }
        if (!lstImClient.getFirstSelectedOption().getText().equalsIgnoreCase(imClient)) {
            lstImClient.select(imClient);
        }
        if (!lstDept.getFirstSelectedOption().getText().equalsIgnoreCase(dept)) {
            lstDept.select(dept);
        }

        // submit
        btnSave.syncEnabled();
        btnSave.submit();

    }

    public void modifyEmployee(Employee employee) {
        modifyEmployee(employee.getUsername(), employee.getFirstName(), employee.getLastName(), employee.getTitle(), employee.getRole(), employee.getManager(),
                employee.getStatus(), employee.getLocation(), employee.getStartDate(), employee.getCellPhone(), employee.getOfficePhone(), employee.getEmail(),
                employee.getImName(), employee.getImClient(), employee.getDepartment());
    }
}
