package com.daitangroup.test.bdd;

import com.daitangroup.controllers.MessengerBackendControllerLevel3;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class UserNotFoundSteps {

    @Autowired
    private MessengerBackendControllerLevel3 msgCtrl3;

    private String userId;
    private ResponseEntity responseEntity;

    @Given("a $userId non-existent user id in database")
    public void givenANonExistentUserIdInDatabase(String userId) {
        this.userId = userId;
    }

    @When("I look for the this non existent user id in database")
    public void whenILookForThisNonExistentUserInDatabase() {
        this.responseEntity = this.msgCtrl3.readUserById(this.userId);

    }

    @Then("messenger responds: 404 not found")
    public void thenMessengerResponds404NotFound() {
        assertEquals(this.responseEntity.getStatusCode(), NOT_FOUND);
    }
}