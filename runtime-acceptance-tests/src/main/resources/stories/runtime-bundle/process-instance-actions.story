Meta:

Narrative:
As a user
I want to perform operations on process instances

Scenario: cancel a process instance
Given the user is authenticated as a testuser
When the user starts a process with variables
And cancel the process
Then the process instance is cancelled

Scenario: try activate a cancelled process instance
Given the user is authenticated as a testuser
And any suspended process instance
When the user cancel the process
Then the process cannot be activated anymore

Scenario: show a process instance diagram
Given the user is authenticated as a testuser
When the user starts a process with variables
And open the process diagram
Then the diagram is shown

Scenario: show diagram for a process instance without graphic info
Given the user is authenticated as a testuser
When the user starts a process without graphic info
And open the process diagram
Then no diagram is shown

Scenario: complete a process instance that uses a connector
Given the user is authenticated as a testuser
When the user starts a connector process
Then the status of the process is changed to completed
And a variable was created with name var1

Scenario: complete a process instance that sends one inbound connector matching variable(one excluded), and receives one matching(one excluded) from the connector
Given the user is authenticated as a testuser
When the user starts a connector process with matching input-variable-name-1 and not matching variable-not-mapped variables
Then the status of the process is changed to completed
And the connector receives the matching input-variable-name-1 variable
And a list of input-variable-name-1,variable-not-mapped,output-variable-name-1 variables was created and output-no-match was not created