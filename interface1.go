package main

import (
	"fmt"
	"strings"
	"time"
)

type talker interface {
	talk() string
}

type martian struct{}

func (m martian) talk() string {
	return "nack nack"
}

type laser int

func (l laser) talk() string {
	return strings.Repeat("pew ", int(l))
}

func shout(t talker) {
	louder := strings.ToUpper(t.talk())
	fmt.Println(louder)
}

type starships struct {
	laser
}

func KK1() {
	shout(martian{})
	shout(laser(3))

	// t = martian{}
	// fmt.Println(t.talk())

	// t := laser(3)
	// fmt.Println(t.talk())
}

func KK2() {
	s := starships{laser(3)}
	fmt.Println(s.talk())
	shout(s)
}

type stardater interface {
	YearDay() int
	Hour() int
}

func stardate(t stardater) float64 {
	doy := float64(t.YearDay())
	h := float64(t.Hour()) / 24.0
	return 1000 + doy + h
}

func KK3() {
	day := time.Date(2012, 8, 6, 5, 17, 0, 0, time.UTC)
	fmt.Printf("%.1f Curiosity has landed\n", stardate(day))
}
