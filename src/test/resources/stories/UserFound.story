Meta:

Narrative:
As a user
I want to perform an action
So I can achieve a goal

Scenario: when a user exists in database and he is requested, the requested user must be returned

Given a valid 5b15286608b1961d27bf08ba user id in database
When I look for the this user id in database
Then messenger responds: 200 ok