#!/usr/bin/env bash
cd src
git clean -xdf
cd ..
mkdir cs6301
cp -R ./src/cs6301/g16 ./cs6301/
cp ./README.md ./cs6301/
zip -r -X g16-sp2.zip ./cs6301
rm -rf ./cs6301
