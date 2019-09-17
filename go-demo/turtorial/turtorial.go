package main

import (
	"fmt"
	"os"
	"runtime"
)

const Pi = 3.141592653589793

const Billion = 1e9 // float constant
const hardEight = (1 << 100) >> 97
const beef, two, veg = "eat", 2, "veg"
const (
    Unknown = 0
    Female = 1
    Male = 2
)

const (
    a = iota
    b = iota
    c = iota
)

const (
    Sunday = iota
    Monday
    Tuesday
    Wednesday
    Thursday
    Friday
    Saturday
)


type Color int

const (
    RED Color = iota // 0
    ORANGE // 1
    YELLOW // 2
    GREEN // ..
    BLUE
    INDIGO
    VIOLET // 6
)

var (
	HOME = os.Getenv("HOME")
	USER = os.Getenv("USER")
	GOROOT = os.Getenv("GOROOT")
)

func main() {
	fmt.Println("==== start ====")
	fmt.Printf("pi=%f\n", Pi)
	fmt.Println(ORANGE)
	fmt.Println(Saturday)
	print(HOME, USER, GOROOT, "\n")
	a := 1
	println(a)
	var goos string = runtime.GOOS
    fmt.Printf("The operating system is: %s\n", goos)
    path := os.Getenv("PATH")
    fmt.Printf("Path is %s\n", path)
}

