package main

import (
	"encoding/json"
	"fmt"
	"os"
)

type location struct {
	Lat  float64
	Long float64 `json:"longlong"`
}

func calcSize(location1 location, location2 location) float64 {
	return (location1.Lat - location2.Lat) * (location1.Long - location2.Long)
}

func KC1() {
	var spirit location
	spirit.Lat = 100
	spirit.Long = 1000
	fmt.Println(spirit)
	var opportunity location
	opportunity.Lat = 110
	opportunity.Long = 1100
	fmt.Println(opportunity)
	fmt.Println(calcSize(spirit, opportunity))

}

func exitInError(err error) {
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
}

func KC2() {
	struct1 := location{1, 2}
	bytes, err := json.Marshal(struct1)
	exitInError(err)
	fmt.Println(string(bytes))
}

type coordinate struct {
	d, m, s float64
	h       rune
}

func (c coordinate) decimal() float64 {
	sign := 1.0
	switch c.h {
	case 'S', 'W', 's', 'w':
		sign = -1
	}
	return sign + (c.d + c.m/60 + c.s/3600)
}
