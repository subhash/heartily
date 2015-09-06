## Heartily

* Various attempts at analyzing workout data
* Initialize a Datomic instance with data loaded from GPX files and generate notebook-style worksheet
    * [Frozen view of generate worksheet](http://viewer.gorilla-repl.org/view.html?source=github&user=subhash&repo=heartily&path=resources/worksheets/sample.clj)
* Adapt GPX files and Strava API to import workout data to Google Fit
    * [Hosted app] (https://floating-hamlet-5403.herokuapp.com/#)


## Generating a new worksheet

* `git clone git@github.com:subhash/heartily.git`
* `git checkout dee8c0afda4e678af33436b8659d911f99a51d`
* `lein gorilla` from within the heartily dir
* You will see a line like this: `Running at http://127.0.0.1:62354/worksheet.html`. Go to that URL in the browser
* The browser will have a namespace definition like this in an editable box:
```
(ns amiable-darkness
  (:require [gorilla-plot.core :as plot]))
```
* Click on the editable box and press "Shift+Enter". The definition will execute
* In the next editable boxes that pop, issue the follow commands and press "Shift+Enter" to execute and a graph will emerge
```clojure
(use 'heartily.core)
(def w1 (load-workout "/Users/subhash/Downloads/trekking.xml"))
(def w2 (load-workout "/Users/subhash/Downloads/trekking.xml"))
(def hr1 (heart-rates w1))
(def hr2 (reverse (heart-rates w1)))
(plot/compose (plot/list-plot hr1 :joined true :color "red") (plot/list-plot hr2 :joined true))
```
* [Frozen view of generate worksheet](http://viewer.gorilla-repl.org/view.html?source=github&user=subhash&repo=heartily&path=resources/worksheets/sample.clj)
* This worksheet can be edited and evaluated all at once by pressing "Ctrl+Shift+Enter" or one expression at a time by clicking on the expression and pressing "Shift+Enter"
* For viewing all Gorilla commands, press "Ctrl+G"
