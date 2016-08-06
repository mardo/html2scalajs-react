sbt clean fastOptJS
cp target/scala-2.11/classes/index.html .
cp target/scala-2.11/*.js .
echo "Remember to fix js paths in index.html"
