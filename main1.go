package main

import (
	"fmt"
	"math"
	"math/rand"
	"strconv"
	"strings"
	"time"
	"unicode/utf8"
)

func TestCode() {
	// fmt.Println("test")

	// path := "C:\\Users\\Public\\Documents\\key.key"
	// result := CheckFile(path)
	// fmt.Println("result is %v", result)
	// result1 := Encrypt("test")
	// fmt.Println("result is %v", result1)
	// Test()

	fmt.Println("you find youself in a dimly lit cavern")
	var command = "walk outside"
	var exit = strings.Contains(command, "outside")
	fmt.Println("you leave the cave:", exit)
	var value = rand.Intn(10) + 1
	if value < 10 {
		fmt.Println("value is less 10")
	} else {
		fmt.Println("value is more than 10")
	}

	var year = 2100
	var leap = year%400 == 0 || (year%4 == 0 && year%100 != 0)
	if leap {
		fmt.Println("look before you leap!")
	} else {
		fmt.Println("keep you feet on the ground.")
	}

	command = "go ahead"
	switch command {
	case "man":
		fmt.Println("man")
	case "go ahead":
		fmt.Println("go ahead")
		fallthrough
	default:
		fmt.Println("default")
	}

	var count = 10
	for count > 0 {
		fmt.Println(count)
		time.Sleep(time.Second)
		count--
	}
	count = 3
	for {
		if count < 0 {
			break
		}
		fmt.Println(count)
		time.Sleep(time.Second)
		count--
	}
	fmt.Println("Lift off!")

	for i := 0; i < 10; i++ {
		fmt.Println("test", i)
		time.Sleep(time.Microsecond * 10)
	}

}
func TestCode1() {
	pigyBank := 0.1
	pigyBank += 0.2
	fmt.Println(pigyBank == 0.3)
	result := math.Abs(pigyBank-0.3) < 0.0001
	fmt.Println("result = ", result)
	fmt.Printf("type of reuslt is %T", result)

}

func TestCode2() {
	var red uint8 = 255
	red++
	fmt.Println(red)

	var number int8 = 127
	number++
	fmt.Println(number)
	fmt.Println("this is a \n aaa")
	fmt.Println(`this is a \n aaa`)
}

func TestCode3() {
	message := "this go 测试"
	// for i := 0; i < len(message); i++ {
	// 	fmt.Printf("%c\n", message[i])
	// }
	rune_length := utf8.RuneCountInString(message)
	fmt.Println(rune_length)
	// 返回第一个rune值和占的字符大小
	c, size := utf8.DecodeRuneInString(message)
	fmt.Printf("%c, %v", c, size)
	// for i := 0; i < rune_length; i++ {
	// 	fmt.Printf("%c\n", message[i])
	// }
}

func TestCode4() {
	message := "this go 测试"
	for i, c := range message {
		fmt.Printf("%v %c \n", i, c)
	}
	var value float64 = 3.1415
	intValue := int64(value)
	fmt.Println(intValue)
}

func TestCode5() {
	str := "Lauch " + strconv.Itoa(3) + " second"
	fmt.Println(str)
	str = fmt.Sprintf("Lauch %v second", 5)
	fmt.Println(str)
}

// type celsius float64
// type kelvin float64

// func kelvinToCelsius(k kelvin) celsius {
// 	return celsius(k - 273.15)
// }

// celsius是关联了kelvin类型的一个方法
// 标准类型是能进行关联
// k是类型接收者， 每个方法可以有多个参数， 但只能有一个接收者
// func (k kelvin) celsius() celsius {
// 	return celsius(k - 273.15)
// }

// func TestCode6() {
// 	var k kelvin = 294.0
// 	var c celsius
// 	c = kelvinToCelsius(k)
// 	c = k.celsius()

// 	fmt.Println(c)
// }
