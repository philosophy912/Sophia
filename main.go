package main

import (
	"fmt"
	"math/rand"
	"time"
)

type kelvin float64

// 声明函数类型
type sensor func() kelvin

func fakeSensor() kelvin {
	return kelvin(rand.Intn(151) + 150)
}

func realSenor() kelvin {
	return 0
}

func Test1() {
	sensor := fakeSensor
	fmt.Println(sensor())

	sensor = realSenor
	fmt.Println(sensor())

}

// sensor func() kelvin 表示sensor是函数名， 无参函数，返回类型kelvin
func measureTemperature(samples int, s sensor) {
	for i := 0; i < samples; i++ {
		k := s()
		fmt.Printf("%v K\n", k)
		time.Sleep(time.Second)
	}
}

// 匿名函数
var f = func() {
	fmt.Println("匿名函数")
}

func Test2() {
	measureTemperature(3, fakeSensor)
	f()
	f1 := func(message string) {
		fmt.Println("匿名函数1" + message)
	}
	f1("111")
	func() {
		fmt.Println("匿名函数2")
	}()

}

func calibrate(s sensor, offset kelvin) sensor {
	return func() kelvin {
		return s() + offset
	}
}

func Test3() {
	sensor := calibrate(realSenor, 5)
	fmt.Println(sensor())
}
func Test4() {
	var k kelvin = 294.0
	sensor := func() kelvin {
		return k
	}
	fmt.Println(sensor())
	k++
	fmt.Println(sensor())
}
