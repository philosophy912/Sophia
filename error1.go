package main

import (
	"errors"
	"fmt"
	"io"
	"io/ioutil"
	"os"
	"strings"
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

// error的切片
type SudoKuError []error

// 满足了error的接口
func (se SudoKuError) Error() string {
	var s []string
	for _, err := range se {
		s = append(s, err.Error())
	}
	return strings.Join(s, ", ")
}

func (g *Grid) Set(row, column int, digit int8) error {
	var errs SudoKuError

	if !inBounds(row, column) {
		errs = append(errs, ErrBounds)
	}
	if !validDigit(digit) {
		errs = append(errs, ErrDigit)
	}
	if len(errs) > 0 {
		return errs
	}

	g[row][column] = digit
	return nil
}

func validDigit(digit int8) bool {
	return false
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

func PPP() {
	var g Grid
	err := g.Set(12, 0, 15)
	if err != nil {
		if errs, ok := err.(SudoKuError); ok {
			fmt.Printf("An error occurred: %v.\n", len(errs))
			for _, e : = range errs {
				fmt.Printf("- %v\n", e)
			}
		}
		os.Exit(1)
	}
}
