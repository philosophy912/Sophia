package main

import (
	"fmt"
	"math/rand"
)

func Test() {
	var num = rand.Intn(10) + 1
	fmt.Println(num)
	num = rand.Intn(10) + 1
	fmt.Println(num)
}
