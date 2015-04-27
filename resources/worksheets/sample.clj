;; gorilla-repl.fileformat = 1

;; **
;;; # Gorilla REPL
;;; 
;;; Welcome to gorilla :-)
;;; 
;;; Shift + enter evaluates code. Hit ctrl+g twice in quick succession or click the menu icon (upper-right corner) for more commands ...
;;; 
;;; It's a good habit to run each worksheet in its own namespace: feel free to use the declaration we've provided below if you'd like.
;; **

;; @@
(ns stunning-hurricane
  (:require [gorilla-plot.core :as plot]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(use 'heartily.core)

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def hr1 (heart-beats-for "trek1"))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;stunning-hurricane/hr1</span>","value":"#'stunning-hurricane/hr1"}
;; <=

;; @@
(def hr2 (heart-beats-for "trek2"))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;stunning-hurricane/hr2</span>","value":"#'stunning-hurricane/hr2"}
;; <=

;; @@
(plot/list-plot hr1 :joined true :color "green")

;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"bf67a12b-c5fa-4ebc-b47e-51ee65ca5bb3","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"bf67a12b-c5fa-4ebc-b47e-51ee65ca5bb3","field":"data.y"}}],"marks":[{"type":"line","from":{"data":"bf67a12b-c5fa-4ebc-b47e-51ee65ca5bb3"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"green"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"data":[{"name":"bf67a12b-c5fa-4ebc-b47e-51ee65ca5bb3","values":[{"x":0,"y":105},{"x":1,"y":84},{"x":2,"y":87},{"x":3,"y":120},{"x":4,"y":116},{"x":5,"y":117},{"x":6,"y":118},{"x":7,"y":113},{"x":8,"y":108},{"x":9,"y":106},{"x":10,"y":109},{"x":11,"y":111},{"x":12,"y":113},{"x":13,"y":105},{"x":14,"y":113},{"x":15,"y":110},{"x":16,"y":109},{"x":17,"y":112},{"x":18,"y":103},{"x":19,"y":103},{"x":20,"y":105},{"x":21,"y":106},{"x":22,"y":107},{"x":23,"y":116},{"x":24,"y":123},{"x":25,"y":131},{"x":26,"y":138},{"x":27,"y":128},{"x":28,"y":128},{"x":29,"y":129},{"x":30,"y":128},{"x":31,"y":120},{"x":32,"y":119},{"x":33,"y":120},{"x":34,"y":123},{"x":35,"y":127},{"x":36,"y":124},{"x":37,"y":120},{"x":38,"y":117},{"x":39,"y":127},{"x":40,"y":134},{"x":41,"y":124},{"x":42,"y":125},{"x":43,"y":110},{"x":44,"y":113},{"x":45,"y":112},{"x":46,"y":112},{"x":47,"y":122},{"x":48,"y":110},{"x":49,"y":104},{"x":50,"y":112},{"x":51,"y":113},{"x":52,"y":108},{"x":53,"y":117},{"x":54,"y":116},{"x":55,"y":116},{"x":56,"y":131},{"x":57,"y":102},{"x":58,"y":112},{"x":59,"y":106},{"x":60,"y":105},{"x":61,"y":108},{"x":62,"y":105},{"x":63,"y":111},{"x":64,"y":105},{"x":65,"y":106},{"x":66,"y":108},{"x":67,"y":109},{"x":68,"y":105},{"x":69,"y":105},{"x":70,"y":106},{"x":71,"y":111},{"x":72,"y":111},{"x":73,"y":111},{"x":74,"y":114},{"x":75,"y":108},{"x":76,"y":109},{"x":77,"y":110},{"x":78,"y":107},{"x":79,"y":111},{"x":80,"y":113},{"x":81,"y":114},{"x":82,"y":103},{"x":83,"y":121},{"x":84,"y":107},{"x":85,"y":102},{"x":86,"y":105},{"x":87,"y":106},{"x":88,"y":105},{"x":89,"y":107},{"x":90,"y":104},{"x":91,"y":110},{"x":92,"y":115},{"x":93,"y":107},{"x":94,"y":104},{"x":95,"y":105},{"x":96,"y":110},{"x":97,"y":104},{"x":98,"y":102}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"bf67a12b-c5fa-4ebc-b47e-51ee65ca5bb3\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"bf67a12b-c5fa-4ebc-b47e-51ee65ca5bb3\", :field \"data.y\"}}], :marks [{:type \"line\", :from {:data \"bf67a12b-c5fa-4ebc-b47e-51ee65ca5bb3\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"green\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :data [{:name \"bf67a12b-c5fa-4ebc-b47e-51ee65ca5bb3\", :values ({:x 0, :y 105} {:x 1, :y 84} {:x 2, :y 87} {:x 3, :y 120} {:x 4, :y 116} {:x 5, :y 117} {:x 6, :y 118} {:x 7, :y 113} {:x 8, :y 108} {:x 9, :y 106} {:x 10, :y 109} {:x 11, :y 111} {:x 12, :y 113} {:x 13, :y 105} {:x 14, :y 113} {:x 15, :y 110} {:x 16, :y 109} {:x 17, :y 112} {:x 18, :y 103} {:x 19, :y 103} {:x 20, :y 105} {:x 21, :y 106} {:x 22, :y 107} {:x 23, :y 116} {:x 24, :y 123} {:x 25, :y 131} {:x 26, :y 138} {:x 27, :y 128} {:x 28, :y 128} {:x 29, :y 129} {:x 30, :y 128} {:x 31, :y 120} {:x 32, :y 119} {:x 33, :y 120} {:x 34, :y 123} {:x 35, :y 127} {:x 36, :y 124} {:x 37, :y 120} {:x 38, :y 117} {:x 39, :y 127} {:x 40, :y 134} {:x 41, :y 124} {:x 42, :y 125} {:x 43, :y 110} {:x 44, :y 113} {:x 45, :y 112} {:x 46, :y 112} {:x 47, :y 122} {:x 48, :y 110} {:x 49, :y 104} {:x 50, :y 112} {:x 51, :y 113} {:x 52, :y 108} {:x 53, :y 117} {:x 54, :y 116} {:x 55, :y 116} {:x 56, :y 131} {:x 57, :y 102} {:x 58, :y 112} {:x 59, :y 106} {:x 60, :y 105} {:x 61, :y 108} {:x 62, :y 105} {:x 63, :y 111} {:x 64, :y 105} {:x 65, :y 106} {:x 66, :y 108} {:x 67, :y 109} {:x 68, :y 105} {:x 69, :y 105} {:x 70, :y 106} {:x 71, :y 111} {:x 72, :y 111} {:x 73, :y 111} {:x 74, :y 114} {:x 75, :y 108} {:x 76, :y 109} {:x 77, :y 110} {:x 78, :y 107} {:x 79, :y 111} {:x 80, :y 113} {:x 81, :y 114} {:x 82, :y 103} {:x 83, :y 121} {:x 84, :y 107} {:x 85, :y 102} {:x 86, :y 105} {:x 87, :y 106} {:x 88, :y 105} {:x 89, :y 107} {:x 90, :y 104} {:x 91, :y 110} {:x 92, :y 115} {:x 93, :y 107} {:x 94, :y 104} {:x 95, :y 105} {:x 96, :y 110} {:x 97, :y 104} {:x 98, :y 102})}], :width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}}}"}
;; <=

;; @@
(plot/list-plot hr2 :joined true :color "red")

;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"d53f5288-9b9a-4302-8dd1-618947b42995","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"d53f5288-9b9a-4302-8dd1-618947b42995","field":"data.y"}}],"marks":[{"type":"line","from":{"data":"d53f5288-9b9a-4302-8dd1-618947b42995"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"red"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"data":[{"name":"d53f5288-9b9a-4302-8dd1-618947b42995","values":[{"x":0,"y":113},{"x":1,"y":113},{"x":2,"y":116},{"x":3,"y":129},{"x":4,"y":118},{"x":5,"y":131},{"x":6,"y":120},{"x":7,"y":132},{"x":8,"y":135},{"x":9,"y":138},{"x":10,"y":128},{"x":11,"y":131},{"x":12,"y":131},{"x":13,"y":143},{"x":14,"y":142},{"x":15,"y":134},{"x":16,"y":118},{"x":17,"y":132},{"x":18,"y":136},{"x":19,"y":146},{"x":20,"y":146},{"x":21,"y":146},{"x":22,"y":147},{"x":23,"y":145},{"x":24,"y":144},{"x":25,"y":139},{"x":26,"y":140},{"x":27,"y":143},{"x":28,"y":140},{"x":29,"y":147},{"x":30,"y":144},{"x":31,"y":152},{"x":32,"y":154},{"x":33,"y":136},{"x":34,"y":125},{"x":35,"y":126},{"x":36,"y":124},{"x":37,"y":122},{"x":38,"y":134},{"x":39,"y":134},{"x":40,"y":137},{"x":41,"y":123},{"x":42,"y":137},{"x":43,"y":140},{"x":44,"y":147},{"x":45,"y":154},{"x":46,"y":147},{"x":47,"y":140},{"x":48,"y":127},{"x":49,"y":149},{"x":50,"y":152},{"x":51,"y":134},{"x":52,"y":143},{"x":53,"y":139},{"x":54,"y":157},{"x":55,"y":151},{"x":56,"y":158},{"x":57,"y":150},{"x":58,"y":145},{"x":59,"y":149},{"x":60,"y":148},{"x":61,"y":130},{"x":62,"y":136},{"x":63,"y":151},{"x":64,"y":144},{"x":65,"y":147},{"x":66,"y":139},{"x":67,"y":135},{"x":68,"y":140},{"x":69,"y":150},{"x":70,"y":150},{"x":71,"y":143},{"x":72,"y":139},{"x":73,"y":158},{"x":74,"y":159},{"x":75,"y":161},{"x":76,"y":160},{"x":77,"y":162},{"x":78,"y":165},{"x":79,"y":164},{"x":80,"y":162},{"x":81,"y":164},{"x":82,"y":157},{"x":83,"y":162},{"x":84,"y":164},{"x":85,"y":158},{"x":86,"y":141},{"x":87,"y":129},{"x":88,"y":131},{"x":89,"y":135},{"x":90,"y":147},{"x":91,"y":150},{"x":92,"y":134},{"x":93,"y":136},{"x":94,"y":137},{"x":95,"y":131},{"x":96,"y":128},{"x":97,"y":123},{"x":98,"y":123},{"x":99,"y":118},{"x":100,"y":121},{"x":101,"y":131},{"x":102,"y":143},{"x":103,"y":119}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"d53f5288-9b9a-4302-8dd1-618947b42995\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"d53f5288-9b9a-4302-8dd1-618947b42995\", :field \"data.y\"}}], :marks [{:type \"line\", :from {:data \"d53f5288-9b9a-4302-8dd1-618947b42995\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"red\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :data [{:name \"d53f5288-9b9a-4302-8dd1-618947b42995\", :values ({:x 0, :y 113} {:x 1, :y 113} {:x 2, :y 116} {:x 3, :y 129} {:x 4, :y 118} {:x 5, :y 131} {:x 6, :y 120} {:x 7, :y 132} {:x 8, :y 135} {:x 9, :y 138} {:x 10, :y 128} {:x 11, :y 131} {:x 12, :y 131} {:x 13, :y 143} {:x 14, :y 142} {:x 15, :y 134} {:x 16, :y 118} {:x 17, :y 132} {:x 18, :y 136} {:x 19, :y 146} {:x 20, :y 146} {:x 21, :y 146} {:x 22, :y 147} {:x 23, :y 145} {:x 24, :y 144} {:x 25, :y 139} {:x 26, :y 140} {:x 27, :y 143} {:x 28, :y 140} {:x 29, :y 147} {:x 30, :y 144} {:x 31, :y 152} {:x 32, :y 154} {:x 33, :y 136} {:x 34, :y 125} {:x 35, :y 126} {:x 36, :y 124} {:x 37, :y 122} {:x 38, :y 134} {:x 39, :y 134} {:x 40, :y 137} {:x 41, :y 123} {:x 42, :y 137} {:x 43, :y 140} {:x 44, :y 147} {:x 45, :y 154} {:x 46, :y 147} {:x 47, :y 140} {:x 48, :y 127} {:x 49, :y 149} {:x 50, :y 152} {:x 51, :y 134} {:x 52, :y 143} {:x 53, :y 139} {:x 54, :y 157} {:x 55, :y 151} {:x 56, :y 158} {:x 57, :y 150} {:x 58, :y 145} {:x 59, :y 149} {:x 60, :y 148} {:x 61, :y 130} {:x 62, :y 136} {:x 63, :y 151} {:x 64, :y 144} {:x 65, :y 147} {:x 66, :y 139} {:x 67, :y 135} {:x 68, :y 140} {:x 69, :y 150} {:x 70, :y 150} {:x 71, :y 143} {:x 72, :y 139} {:x 73, :y 158} {:x 74, :y 159} {:x 75, :y 161} {:x 76, :y 160} {:x 77, :y 162} {:x 78, :y 165} {:x 79, :y 164} {:x 80, :y 162} {:x 81, :y 164} {:x 82, :y 157} {:x 83, :y 162} {:x 84, :y 164} {:x 85, :y 158} {:x 86, :y 141} {:x 87, :y 129} {:x 88, :y 131} {:x 89, :y 135} {:x 90, :y 147} {:x 91, :y 150} {:x 92, :y 134} {:x 93, :y 136} {:x 94, :y 137} {:x 95, :y 131} {:x 96, :y 128} {:x 97, :y 123} {:x 98, :y 123} {:x 99, :y 118} {:x 100, :y 121} {:x 101, :y 131} {:x 102, :y 143} {:x 103, :y 119})}], :width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}}}"}
;; <=

;; @@

;; @@
