rm -rf dist/*
cp -r target/scala-*/*.js dist
mkdir dist/classes
cp -r target/scala-*/classes/index.html dist/classes
