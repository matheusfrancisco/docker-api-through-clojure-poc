#!/bin/bash
clojure -M ./solution.clj <in.txt >result-clj.txt

diff result-clj.txt out.txt >diff.txt

cd - >/dev/null
