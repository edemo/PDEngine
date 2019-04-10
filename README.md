Universal preferential voting control server
features:
- REST API
- Condorcet - Shulze method support
- ballot managment
- ADA login support (optional)
- liquid democracy support
- voks acredited support
- voks weight support
- secure system
- flexibility confuguration


This is the voting engine core.

You can find it in the big picture of things here:
http://adadocs.demokracia.rulez.org/adadocs/edemo/master/KDEA/index.html#701bb9c7-9372-4759-bc67-51dc4355db49

The design documentation is here:
http://adadocs.demokracia.rulez.org/PDEngine/edemo/master/index.html

# For developers

!!! even the unit tests use hardware random generator (/dev/random). install the haveged package,
otherwise tests will take ages!!!

Quality metrics gathered by sonar: https://sonarqube.com/dashboard?id=org.rulez.demokracia:PDEngine

- Coding rules are mandatory. They are here: https://adadocs.demokracia.rulez.org/zenta-tools/magwas/master/zenta-tools/codingrules.html

- We are using git-flow. You issue pull requests to the develop branch.

- We are doing TDD. You will have tests. Enough in quality and quantity.

- We use kanban. The table is here: https://huboard.com/edemo/PDEngine/

- You can pick any issues not yet assigned. Those we alredy understand are in "Ready".

- If you don't understand an issue, ask in comment and tag @magwas.

The issues we plan to do in the actual week are moved to "in this sprint", and ordered according to priority.

The ones we are working on are in "Working". Those should have the one working with it assigned.

If you are ready with the issue, move it to "Review". Issue the pull request to edemo/develop. You can ask for a reviewer.
Have the issue # in the merge title/a commit title, so merging will move the issue to done.

In the sprint demo we

 - look at each issue in done and review, and archive those which are okay in the test system.

 - move around other issues randomly :)

When you code, please keep these in mind:

- You are expected to implement the specification. No more, no less. You are not expected to be a telepath.
  - If a detail is not defined in the model, then feel free to make the decision
    e.g. we said you have to check the string for length, but did not specify minimum and maximum length.
   - If in doubt, feel free to ask. And feel free to discard suggestions. The only thing matters is the specification.
    Well, sometimes the specification WILL be wrong. The architect is also human. More or less.
  - Yes, you are actually helping the team by not trying to read minds. This way the architect will have faster and
   more direct feedback on the quality of his work. You help him to improve, and save a lot of time for your older self and teammates.

- If coding to the specification breaks other tests, then amend them with possible renaming or delete them.
  - if the other test tests something unrelated (e.g. you implement validation, and the other test breaks the validation
   rules in the setup part), then the best way is to fix the setup.
  - if it is the same functionality than your test (e.g. the specification has actually changed, or the architect was sloppy
    and have given conflicting rules), then modify the assertions or delete the the test. At your discretion.
   The related rule of thumb: a test is useful if it enhances the test coverage. In some cases testing the exact same thing
   in the code tests totally unrelated aspects of the specification. Remember: test case names are sentences of the documentation.
   If none of the above stands, the test is just hindering us. If only the second stands, sometimes it is still better to delete the test.
  - the name of the test should reflect what the test actually tests. at all times. so if that is changed, then change the name
 
- Make sure that you test the specification (if it is possible), and not some implementation detail of it.
  One of the most common problems with tests is that they pin down some implementation detail, and when that is changed,
  a lot of tests should be rewritten.

- Keep tests as clean as production code. For example in the case above, it is much easier to rewrite one method setting up or asserting
  something for 42 tests than rewrite 42 tests individually.

If you use Eclipse, you need the following installed:
m2e-wtp - JAX-RS configurator for WTP
m2e-wtp - Maven integration for WTP
