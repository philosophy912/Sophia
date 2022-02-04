package main

import (
	"encoding/json"
	"fmt"
	"math"
	"os"
)

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

func newLocation(lat, long coordinate) location {
	return location{lat.decimal(), long.decimal()}
}

func KC3() {
	lat := coordinate{d: 4, m: 35, s: 22.2, h: 'S'}
	long := coordinate{137, 26, 30.12, 'E'}
	fmt.Println(lat.decimal(), long.decimal())

	coordinate1 := newLocation(lat, long)
	fmt.Printf("%+v", coordinate1)
}

type world struct {
	redius float64
}

func (w world) distance(p1, p2 location) float64 {
	s1, c1 := math.Sincos(rad(p1.Lat))
	s2, c2 := math.Sincos(rad((p2.Lat)))
	clong := math.Cos(p1.Long - p2.Long)
	return w.redius * math.Acos(s1*s2*c1*c2*clong)
}

func rad(deg float64) float64 {
	return deg * math.Pi / 180
}

func KC4() {
	var mars = world{redius: 3389.5}
	spirit := location{-14.5684, 175.472646}
	opporitunity := location{-1.9462, 354.4734}
	dist := mars.distance(spirit, opporitunity)
	fmt.Println(dist)

}

type report struct {
	sol         // 表示火星上的日期
	temperature // 表示温度
	location    // 表示位置
}

type temperature struct {
	high, low celsius
}

type location struct {
	Lat  float64
	Long float64 `json:"longlong"`
}
type sol int

//声明摄氏度
type celsius float64

func (t temperature) average() celsius {
	return (t.high + t.low) / 2
}

// 转发的使用，类似于继承
func (r report) average() celsius {
	return r.temperature.average()
}

func (s sol) days(s2 sol) int {
	days := int(s2 - s)
	if days < 0 {
		days = -days
	}
	return days
}

func (l location) days(l2 location) int {
	return 5
}

func KC6() {
	bradbury := location{-4.5895, 137.4417}
	t := temperature{high: -1.0, low: -78.0}
	fmt.Println(t.average())
	report := report{
		sol:         15,
		temperature: t,
		location:    bradbury,
	}
	fmt.Println(report.temperature.average())
	fmt.Println(report.average())
	fmt.Printf("%+v\n", report)
	fmt.Println(report.temperature.high)

}
