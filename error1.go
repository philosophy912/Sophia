package main

import (
	"errors"
	"fmt"
	"io"
	"io/ioutil"
	"os"
)

func OOO1() {
	files, err := ioutil.ReadDir(".")
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
	for _, file := range files {
		fmt.Println(file.Name())
	}
}

func proverbs(name string) error {
	f, err := os.Create(name)
	if err != nil {
		return err
	}
	defer f.Close()

	sw := safeWriter{w: f}
	sw.writeln("Errors are value")
	sw.writeln("Don't just check errors, handle them gracefully.")
	return sw.err
}

func OOO2() {
	err := proverbs("proverbs.txt")
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
}

type safeWriter struct {
	w   io.Writer
	err error
}

func (sw *safeWriter) writeln(s string) {
	if sw.err != nil {
		return
	}
	_, sw.err = fmt.Fprintln(sw.w, s)
}

const rows, columns = 9, 9

// 二维数组
type Grid [rows][columns]int8

var (
	ErrBounds = errors.New("out of bounds")

	ErrDigit = errors.New("invalid digit")
)

func (g *Grid) Set(row, column int, digit int8) error {
	if !inBounds(row, column) {
		return ErrBounds
	}
	g[row][column] = digit
	return nil
}

func inBounds(row, column int) bool {
	if row < 0 || row >= rows {
		return false
	}
	if column < 0 || column >= columns {
		return false
	}
	return true
}

func main() {
	var g Grid
	err := g.Set(10, 90, 5)
	if err != nil {
		switch err {
		case ErrBounds, ErrDigit:
			fmt.Printf("An error occurred: %v.\n", err)
		default:
			fmt.Println(err)
		}
		os.Exit(1)
	}
}
