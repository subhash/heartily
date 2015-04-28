## Heartily

This is a library to support notebook-style analysis of workout data. We initialize a Datomic instance with data loaded from GPX files and provide a few primitive functions to wean health-related information from workout data.

## Usage

* `git clone git@github.com:subhash/heartily.git`
* `lein gorilla` from within the heartily dir
* You will see a line like this: `Running at http://127.0.0.1:62354/worksheet.html`. Go to that URL in the browser
* This worksheet can be evaluated all at once by pressing "Ctrl+Shift+Enter" or one expression at a time by clicking on the expression and pressing "Shift+Enter"
* For viewing all Gorilla commands, press "Ctrl+G"
* For a frozen view of the worksheet, click [here](http://viewer.gorilla-repl.org/view.html?source=github&user=subhash&repo=heartily&path=resources/worksheets/sample.clj)
