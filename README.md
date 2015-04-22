## Usage

* `git clone git@github.com:subhash/heartily.git`
* `lein repl` from within the heartily dir
* In the REPL, load a GPX file giving its full path
```clojure
heartily.core=> (load-gpx "/Users/subhash/Downloads/trekking.xml")
```
* All the tracks and track-points are dumped to the console for testing
