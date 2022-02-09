package main

import (
	"fmt"
	"sort"
)

func III1() {
	var nowhere *int
	fmt.Println(nowhere)

	fmt.Println(*nowhere)
}

type person1 struct {
	age int
}

// 虽然指针是nil，接收者其实就是参数
func (p *person1) birthday() {
	if p != nil {
		p.age++
	} else {
		return
	}

}

func III2() {
	var nobody *person1
	fmt.Println(nobody)

	nobody.birthday()

}

func II32() {
	var fn func(a, b int) int
	fmt.Println(fn == nil)
}

func sortStrings(s []string, less func(i, j int) bool) {
	if less == nil {
		less = func(i, j int) bool {
			return s[i] < s[j]
		}
	}
	sort.Slice(s, less)
}

func II323() {
	food := []string{"onion", "carrot", "celery"}
	sortStrings(food, nil)
	fmt.Println(food)
}

func II3234() {
	var soup []string
	fmt.Println(soup == nil)

	for _, ingredient := range soup {
		fmt.Println(ingredient)
	}

	fmt.Println(len(soup))

	soup = append(soup, "onion", "carrot", "celery")
	fmt.Println(soup)
}

func II53234() {
	var soup map[string]int
	fmt.Println(soup == nil)

	measurement, ok := soup["onion"]
	if ok {
		fmt.Println(measurement)
	}

	for ingredient, measurement := range soup {
		fmt.Println(ingredient, measurement)
	}
}

func II53234111() {
	var v interface{}
	fmt.Printf("%T %v %v\n", v, v, v == nil)

	var p *int
	v = p
	fmt.Printf("%T %v %v\n", v, v, v == nil)
}

type number struct {
	value int
	valid bool
}

func newNumber(v int) number {
	return number{value: v, valid: true}
}

func (n number) string() string {
	if !n.valid {
		return "not set"
	}
	return fmt.Sprintf("%d", n.value)
}

func II532341111() {
	n := newNumber(42)
	fmt.Println(n)

	e := number{}
	fmt.Println(e)
}
