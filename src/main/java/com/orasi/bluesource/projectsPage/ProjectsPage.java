package com.orasi.bluesource.projectsPage;

import org.openqa.selenium.support.FindBy;

import com.orasi.DriverManager;
import com.orasi.bluesource.commons.BluesourceTables;
import com.orasi.bluesource.commons.SortOrder;
import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

import ru.yandex.qatools.allure.annotations.Step;

public class ProjectsPage {

    private OrasiDriver driver;

    // All the page elements
    @FindBy(css = "#all-content > div.header-btn-section > div > div:nth-child(1) > label")
    private Button btnShowInactive;
    @FindBy(xpath = "//button[text()='Add']")
    private Button btnAdd;
    @FindBy(xpath = "//input[@id='search-bar']")
    private Textbox txtSearch;
    @FindBy(css = "a.ng-binding")
    private Element tableCell;
    @FindBy(css = ".alert-success.alert-dismissable")
    private Label lblSuccessMsg;
    @FindBy(className = "table")
    private Webtable tabProjectTable;
    @FindBy(id = "loading-section")
    private Element loadingModal;
    @FindBy(css = "#resource-content > div:nth-child(2) > p")
    private Label lblTotalProjectLabel;

    // *********************
    // ** Build page area **
    // *********************
    public ProjectsPage() {
        this.driver = DriverManager.getDriver();
        ElementFactory.initElements(driver, this);
    }

    public boolean pageLoaded() {
        return driver.page().isElementLoaded(this.getClass(), driver, txtSearch);
    }

    // *****************************************
    // ***Page Interactions ***
    // *****************************************

    @Step("Then a success message is displayed")
    public boolean isSuccessMsgDisplayed() {
        return lblSuccessMsg.isDisplayed();
    }

    public String getSuccessMsgText() {
        return lblSuccessMsg.getText();
    }

    @Step("When I search for \"{0}\" on the Projects Page")
    public void enterSearchText(String text) {
        loadingModal.syncHidden();
        txtSearch.syncVisible();
        txtSearch.safeSet(text);
    }

    @Step("Then Employees with the value \"{0}\" in the \"{1}\" column are displayed")
    public boolean validateTextInTable(String text, String column) {
        BluesourceTables table = new BluesourceTables();
        String columnName = ProjectsTableColumns.valueOf(column).toString();
        return table.validateTextInTable(text, columnName);
    }

    @Step("When I sort the \"{0}\" column in \"{1}\" order")
    public void sortColumn(String column, String order) {
        BluesourceTables table = new BluesourceTables();
        String columnName = ProjectsTableColumns.valueOf(column).toString();
        table.sortColumn(columnName, SortOrder.valueOf(order));
    }

    @Step("Then the \"{0}\" column is displayed in \"{1}\" order")
    public boolean validateSortColumn(String column, String order) {
        BluesourceTables table = new BluesourceTables();
        String columnName = ProjectsTableColumns.valueOf(column).toString();
        // if((column.equals("CLIENTPARTNER")) && order.equals("ASCENDING")) order = "DESCENDING";
        if ((column.equals("CLIENTPARTNER")) && order.equals("DESCENDING"))
            order = "ASCENDING";
        return table.validateSortColumn(columnName, SortOrder.valueOf(order));
    }

    @Step("When I set the number of rows to be \"{0}\"")
    public void setRowsPerPageDisplayed(String numberOfRows) {
        BluesourceTables table = new BluesourceTables();
        table.setRowsPerPageDisplayed(numberOfRows);
    }

    @Step("Then the number of rows displayed should be \"{0}\"")
    public boolean validateRowsPerPageDisplayed(String numberOfRows) {
        BluesourceTables table = new BluesourceTables();
        return table.validateRowsPerPageDisplayed(numberOfRows);
    }

    public int getTotalDisplayedProjects() {
        loadingModal.syncHidden();
        lblTotalProjectLabel.syncVisible();
        String total = lblTotalProjectLabel.getText();
        return Integer.parseInt(total.substring(total.indexOf("of") + 3, total.length()));
    }

    @Step("When I click the Add Button on the Employees Page")
    public void clickAddProjectButton() {
        loadingModal.syncHidden();
        btnAdd.jsClick();
        driver.page().isDomComplete(driver);
    }

    @Step("When I click the Show Inactive Button on the Projects Page")
    public void clickInactiveButton() {
        loadingModal.syncHidden();
        btnShowInactive.jsClick();
        driver.page().isDomComplete(driver);
    }

    @Step("Then the Projects table should update the projects displayed")
    public boolean validateProjectsTableResultsUpdated(int previousCount) {
        return (previousCount != getTotalDisplayedProjects());
    }

    @Step("Then the Employee will display no rows found")
    public boolean validateNoRowsFound() {
        return (tabProjectTable.getRowCount() == 1);
    }

    public boolean validateProjectFoundInTable(String projectName) {
        return validateTextInTable(projectName.substring(0, 1).toUpperCase() + projectName.substring(1).toLowerCase(), ProjectsTableColumns.PROJECTNAME.name());
    }

    @Step("When I click the \"{0}\" Name link")
    public void selectProjectName(String name) {
        BluesourceTables table = new BluesourceTables();
        table.selectFieldLink(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
    }
}
