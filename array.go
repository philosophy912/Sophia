package main

import (
	"fmt"
	"math"
	"strings"
)

func TC1() {
	array := [...]string{"a", "b", "c", "d", "e"}
	copyArray := array
	array[3] = "ddd"
	fmt.Println(array)
	fmt.Println(copyArray)
}

func hyperspace(worlds []string) {
	for i := range worlds {
		worlds[i] = strings.TrimSpace(worlds[i])
	}
}

func TC2() {
	plants := []string{"   aaa  ", "   bbb", "ccc   "}
	hyperspace(plants)
	fmt.Println(strings.Join(plants, ","))
	array := []string{"a", "b", "c", "d", "e"}
	fmt.Println(len(array), cap(array))
}

func TC3() {
	temperature := map[string]int{
		"Earth": 15,
		"Mars":  -65,
	}
	temp := temperature["Earth"]
	fmt.Printf("temp %v \n", temp)

	temperature["Earth"] = 16
	temperature["Venus"] = 464

	fmt.Println(temperature)

	moon := temperature["Moon"]
	fmt.Println(moon)

	if moon, ok := temperature["Moon"]; ok {
		fmt.Println(moon)
	} else {
		fmt.Println("where is moon")
	}
}

func TC4() {
	temperature := []float64{1, 2, 3, 4, 5, 6, 7, 8, 5, 3, 1}
	frequency := make(map[float64]int)
	for _, t := range temperature {
		frequency[t]++
	}
	// 遍历map
	for t, num := range frequency {
		fmt.Printf("%f occures %d times\n", t, num)
	}
}

func TC5() {
	temperature := []float64{-28.0, 32.0, -31.0, -29.0, -23.0, -29.0, -28.0, -33.0}
	groups := make(map[float64][]float64) // key是int类型，value是切片slice []float64
	for _, t := range temperature {
		g := math.Trunc(t/10) * 10
		groups[g] = append(groups[g], t)
	}

	for g, temperatures := range groups {
		fmt.Printf("%v: %v\n", g, temperatures)
	}
}
