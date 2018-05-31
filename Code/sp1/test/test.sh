#!/bin/bash
types=("phantom" "Generic merge sort." "Int merge sort." "Generic n square sort.")
for type in 1 2 3
do
    echo ${types[$type]}
    echo ${types[$type]} >> result.txt
    for size in {1..16}
    do
        echo -n $size ''
        echo "Array size: $size million. " >> result.txt
        java -Xms1024m -Xmx1024m -Xss1024m -ea cs6301.g16.Sort $type $size >> result.txt
    done
    echo '' >> result.txt
    echo ''
done
java