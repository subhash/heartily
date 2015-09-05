## Heartily

* Various attempts at analyzing workout data
* Initialize a Datomic instance with data loaded from GPX files and generate notebook-style worksheet
    * [Frozen view of generate worksheet](http://viewer.gorilla-repl.org/view.html?source=github&user=subhash&repo=heartily&path=resources/worksheets/sample.clj)
* Adapt GPX files and Strava API to import workout data to Google Fit
    * [Hosted app] (https://floating-hamlet-5403.herokuapp.com/#)


## Generating a new worksheet

* `git clone git@github.com:subhash/heartily.git`
* `lein gorilla` from within the heartily dir
* You will see a line like this: `Running at http://127.0.0.1:62354/worksheet.html`. Go to that URL in the browser
* This worksheet can be evaluated all at once by pressing "Ctrl+Shift+Enter" or one expression at a time by clicking on the expression and pressing "Shift+Enter"
* For viewing all Gorilla commands, press "Ctrl+G"
