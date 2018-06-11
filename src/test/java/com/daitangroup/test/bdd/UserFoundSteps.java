package com.daitangroup.test.bdd;

import com.daitangroup.controllers.MessengerBackendControllerLevel3;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.http.HttpStatus.OK;

@Component
public class UserFoundSteps {

    @Autowired
    private MessengerBackendControllerLevel3 msgCtrl3;

    private String userId;
    private ResponseEntity responseEntity;

    @Given("a valid $userId user id in database")
    public void givenAvalidUserIdInDatabase(String userId) {
        this.userId = userId;
    }

    @When("I look for the this user id in database")
    public void whenILookForThisUserIdInDatabase() {
        this.responseEntity = msgCtrl3.readUserById(this.userId);

    }

    @Then("messenger responds: 200 ok")
    public void thenMessengerResponds200Ok() {
        assertEquals(responseEntity.getStatusCode(), OK);
    }
}