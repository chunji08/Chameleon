package com.orasi.bluesource.projectsPage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.orasi.DriverManager;
import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;

import ru.yandex.qatools.allure.annotations.Step;

public class ManageProjectModal {
    private OrasiDriver driver;

    @FindBy(id = "modal_label_1")
    private Label lblAddProjectPopup;
    @FindBy(id = "project_name")
    private Textbox txtName;
    @FindBy(id = "project_client_partner_id")
    private Listbox lstClientPartner;
    @FindBy(id = "add-team-lead")
    private Button btnAddTeamLead;
    @FindBy(id = "project_leads")
    private List<Listbox> lstTeamLeads;
    @FindBy(id = "project_status")
    private Listbox lstStatus;
    @FindBy(id = "project_start_date")
    private Textbox txtProjectStartDate;
    @FindBy(id = "project_end_date")
    private Textbox txtProjectEndDate;
    @FindBy(name = "commit")
    private Button btnSave;

    // *********************
    // ** Build page area **
    // *********************
    public ManageProjectModal() {
        this.driver = DriverManager.getDriver();
        ;
        ElementFactory.initElements(driver, this);
    }

    public boolean pageLoaded() {
        return driver.page().isElementLoaded(this.getClass(), driver, txtName);
    }

    // *****************************************
    // ***Page Interactions ***
    // *****************************************

    // adds a new project on the projects page
    @Step("And I add a new Project")
    public void addProject(String projectName, String clientPartner, ArrayList<String> teamLeads, String status, String startDate, String endDate) {
        lblAddProjectPopup.syncVisible();
        // Fill in the details

        txtName.set(projectName);
        lstClientPartner.select(clientPartner);
        if (teamLeads.size() > 0) {
            for (String teamLead : teamLeads) {
                btnAddTeamLead.jsClick();
                ElementFactory.initElements(driver, this);
                Listbox box = lstTeamLeads.get(lstTeamLeads.size() - 1);
                box.select(teamLead);
            }
        }
        lstStatus.select(status);
        txtProjectStartDate.safeSet(startDate);
        txtProjectEndDate.safeSet(endDate);

        // submit
        btnSave.syncEnabled();
        btnSave.click();

    }

    public void addProject(Project project) {
        addProject(project.getProjectName(), project.getClientPartner(), project.getTeamLeads(), project.getStatus(), project.getStartDate(), project.getEndDate());
    }

    @Step("And I modify the Project Details")
    public void modifyProject(String projectName, String clientPartner, ArrayList<String> teamLeads, ArrayList<String> teamLeadsToRemove, String status, String startDate, String endDate) {
        lblAddProjectPopup.syncEnabled();
        if (!txtName.getText().equalsIgnoreCase(projectName))
            txtName.set(clientPartner);
        if (!lstClientPartner.getFirstSelectedOption().getText().equalsIgnoreCase(clientPartner))
            lstClientPartner.select(clientPartner);
        if (teamLeads.size() > 0) {
            boolean leadFound = false;
            for (String teamLead : teamLeads) {
                for (int x = 0; x < lstTeamLeads.size(); x++) {
                    Listbox box = lstTeamLeads.get(x);
                    if (box.getFirstSelectedOption().getText().equals(teamLead)) {
                        leadFound = true;
                        break;
                    }
                }
                if (!leadFound) {
                    btnAddTeamLead.jsClick();
                    // te.initializePage(this.getClass());
                    Listbox box = lstTeamLeads.get(lstTeamLeads.size() - 1);
                    box.select(teamLead);
                    leadFound = false;
                }
            }
        }
        if (teamLeadsToRemove.size() > 0) {
            for (String teamLead : teamLeadsToRemove) {
                for (int x = 0; x < lstTeamLeads.size(); x++) {
                    Listbox box = lstTeamLeads.get(x);
                    if (box.getFirstSelectedOption().getText().equals(teamLead)) {
                        box.findElement(By.xpath("../span")).click();
                    }
                }
            }
        }
        if (!lstStatus.getFirstSelectedOption().getText().equalsIgnoreCase(status))
            lstStatus.select(status);
        if (!txtProjectStartDate.getText().equalsIgnoreCase(startDate))
            txtProjectStartDate.set(startDate);
        if (!txtProjectEndDate.getText().equalsIgnoreCase(endDate))
            txtProjectEndDate.set(endDate);

        // submit
        btnSave.syncEnabled();
        btnSave.click();
    }

    public void modifyProject(Project project) {
        modifyProject(project.getProjectName(), project.getClientPartner(), project.getTeamLeads(), project.getTeamLeadsToRemove(), project.getStatus(), project.getStartDate(), project.getEndDate());
    }

    @Step("Then the Client Partner \"{0}\" is availible for selection")
    public boolean validateClientPartnerIsAvailible(String name) {
        return isOptionAvailible(lstClientPartner, name);
    }

    @Step("Then the Client Partner \"{0}\" is not availible for selection")
    public boolean validateClientPartnerIsNotAvailible(String name) {
        return !isOptionAvailible(lstClientPartner, name);
    }

    @Step("Then the Team Lead \"{0}\" is availible for selection")
    public boolean validateTeamLeadIsAvailible(String name) {
        btnAddTeamLead.click();
        Listbox box = lstTeamLeads.get(0);
        return isOptionAvailible(box, name);
    }

    @Step("Then the Team Lead \"{0}\" is not availible for selection")
    public boolean validateTeamLeadIsNotAvailible(String name) {
        btnAddTeamLead.click();
        Listbox box = lstTeamLeads.get(0);
        return !isOptionAvailible(box, name);
    }

    private boolean isOptionAvailible(Listbox listbox, String name) {
        List<WebElement> allOptions = listbox.getOptions();
        for (WebElement option : allOptions) {
            if (option.getText().equals(name))
                return true;
        }
        return false;
    }
}
