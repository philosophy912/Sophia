package main

import (
	"fmt"
	"strings"
	"time"
)

func KKK1() {
	answer := 42
	fmt.Println(&answer)

	address := &answer
	fmt.Println(*address)

	canada := "canada"
	var home *string
	fmt.Printf("%T\n", home)
	home = &canada
	fmt.Println(*home)

	var admin *string

	a := "aaa"
	admin = &a
	fmt.Println(*admin)

	b := "bbb"
	admin = &b
	fmt.Println(*admin) // 此时输出的是bbb

	b = "ccc"
	fmt.Println(*admin) // 此时输出的是ccc

	*admin = "ddd"
	fmt.Println(b) // 此时输出的是ddd

	copyAdmin := admin
	*copyAdmin = "eee"
	fmt.Println(b) //此时输出的是eee

	fmt.Println(admin == copyAdmin) // 指向了同一个内存地址，所以想等

	c := "ccc"
	admin = &c
	fmt.Println(admin == copyAdmin) // 由于指向的内存地址不同了，此时不相等

	d := *copyAdmin
	*copyAdmin = "fff"
	fmt.Println(d) // 此时输出的值为eee
	fmt.Println(b) // 此时输出的值为fff

}

func KKK2() {
	type person struct {
		name, superpower string
		age              int
	}

	timmy := &person{
		name: "Timmy",
		age:  10,
	}

	timmy.superpower = "flying" // 结构体解引用为可选的
	(*timmy).age = 11
	fmt.Printf("%+v \n", timmy)
}

func KKK3() {
	superpowers := &[3]string{"a", "b", "c"}
	fmt.Println(superpowers[0])
	fmt.Println(superpowers[1:3])
}

type person struct {
	name, superpower string
	age              int
}

func birthday(p *person) {
	p.age++
}

func (p *person) birthday() {
	p.age++
}

func KKK4() {
	rebecca := person{
		name:       "Rebecca",
		age:        15,
		superpower: "imagination",
	}
	birthday(&rebecca)
	fmt.Printf("%+v\n", rebecca)

	terry := &person{
		name: "Terry",
		age:  15,
	}
	terry.birthday()
	fmt.Printf("%+v\n", terry)

	nathan := person{
		name: "Nathan",
		age:  15,
	}
	nathan.birthday()
	fmt.Printf("%+v\n", nathan)

	const layout = "Mon, Jan 2, 2006"
	day := time.Now()
	tommorrow := day.Add(24 * time.Hour)

	fmt.Println(day.Format(layout))
	fmt.Println(tommorrow.Format(layout))
}

type stats struct {
	level             int
	endurance, health int
}

func levelUp(s *stats) {
	s.level++
	s.endurance = 42 + (14 * s.level)
	s.health = 5 * s.endurance
}

type character struct {
	name  string
	stats stats
}

func KKK5() {
	player := character{name: "Matthias"}
	levelUp(&player.stats)
	fmt.Printf("%+v\n", player.stats)
}

func reset(board *[8][8]rune) {
	board[0][0] = 'r'
}
func KKK6() {
	var board [8][8]rune
	reset(&board)

	fmt.Println(board[0][0])
}

func reclassify(planets *[]string) {
	*planets = (*planets)[0:8]
}

func KKK8() {
	plantes := []string{
		"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
	}
	reclassify(&plantes)
	fmt.Println(plantes)
}

type taker interface {
	talk() string
}

func shoult(t taker) {
	louder := strings.ToUpper(t.talk())
	fmt.Println(louder)
}

type martains struct{}

func (m martains) talk() string {
	return "neck neck"
}

type lasers int

func KKK9() {
	shoult(martains{})
	shoult(&martains{})
}

func (l *lasers) talk() string {
	return strings.Repeat("pew ", int(*l))
}

func main() {
	pew := lasers(2)
	shoult(&pew)
	// shoult(pew) // 此时会报错， lasers指针满足了这个接口，而lasers本身没有满足这个接口
}
