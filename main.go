package main

import "fmt"

func main() {
	// fmt.Println("test")

	path := "C:\\Users\\Public\\Documents\\key.key"
	result := CheckFile(path)
	fmt.Println("result is %v", result)
}
