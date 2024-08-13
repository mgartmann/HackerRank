'use strict';

import { WriteStream, createWriteStream } from "fs";

process.stdin.resume();
process.stdin.setEncoding('utf-8');
if (process.stdin.isTTY)
  process.stdin.setRawMode(true); 

let inputString: string = '';
let inputLines: string[] = [];
let currentLine: number = 0;

process.stdin.on('data', function(inputStdin: string): void {
    inputString += inputStdin;
});

process.stdin.on('end', function(): void {
    inputLines = inputString.split('\n');
    inputString = '';

    main();
});


function readLine(): string {
    return inputLines[currentLine++];
}

/*
 * Complete the 'diagonalDifference' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts 2D_INTEGER_ARRAY arr as parameter.
 */

function diagonalDifference(arr: number[][]): number {
    let primaryDiagonal = 0, secondaryDiagonal = 0;
    let lastIndex = arr.length - 1;
    for (let i = 0; i < arr.length; i++) {
        primaryDiagonal += arr[i][i];
        secondaryDiagonal += arr[i][lastIndex - i];
    }
    return Math.abs(primaryDiagonal - secondaryDiagonal);
}

function main() {
    const n: number = parseInt(readLine().trim(), 10);

    let arr: number[][] = Array(n);

    for (let i: number = 0; i < n; i++) {
        arr[i] = readLine().replace(/\s+$/g, '').split(' ').map(arrTemp => parseInt(arrTemp, 10));
    }

    const result: number = diagonalDifference(arr);

    console.log(result);
}
