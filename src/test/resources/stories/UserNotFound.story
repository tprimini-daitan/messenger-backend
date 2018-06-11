Meta:

Narrative:
As a user
I want to perform an action
So I can achieve a goal

Scenario: when a user does not exist in database and he is requested, messenger would respond 'not found'

Given a 123456 non-existent user id in database
When I look for the this non existent user id in database
Then messenger responds: 404 not found