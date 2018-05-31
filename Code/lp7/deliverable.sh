#!/usr/bin/env bash
cd src
git clean -xdf
cd ..
mkdir -p cs6301/g16
cp -R ./src/cs6301/g16/*.java ./cs6301/g16
cp ./README.md ./cs6301/g16/
zip -r -X g16-lp7.zip ./cs6301
rm -rf ./cs6301
