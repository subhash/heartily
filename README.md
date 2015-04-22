## Usage

* `git clone git@github.com:subhash/heartily.git`
* `lein gorilla` from within the heartily dir
* You will see a line like this: `Running at http://127.0.0.1:62354/worksheet.html`. Go to that URL in the browser
* The browser will have a namespace definition like this in an editable box:
```
(ns amiable-darkness
  (:require [gorilla-plot.core :as plot]))
```
* Click on the editable box and press "Shift+Enter". The definition will execute
* In the next editable boxes that pop, issue the follow commands one by one and press "Shift+Enter" to execute and a graph will emerge
```clojure
(def w1 (load-workout "/Users/subhash/Downloads/trekking.xml"))
(def w2 (load-workout "/Users/subhash/Downloads/trekking.xml"))
(def hr1 (heart-rates w1))
(def hr2 (reverse (heart-rates w1)))
(plot/compose (plot/list-plot hr1 :joined true :color "red") (plot/list-plot hr2 :joined true))
```

