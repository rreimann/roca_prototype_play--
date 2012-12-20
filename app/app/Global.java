package app;

import models.Issue;
import models.User;
import play.Application;
import play.GlobalSettings;
import repository.Repository;
import tools.IssueGenerator;

import java.util.List;


/**
 * Play Global Objekt zum bootstrapping der Anwendung. Kuemmert sich aktuell vor
 * allem um das Initialisieren des Repositories.
 */
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        super.onStart(app);
        IssueGenerator issueGenerator = IssueGenerator.getInstance();
        List<User> users = issueGenerator.createUsers();
        Repository.getInstance().saveAllUser(users);
        List<Issue> issues = issueGenerator.createRandomIssues(200, users);
        Repository.getInstance().saveAllIssues(issues);
    }
}
