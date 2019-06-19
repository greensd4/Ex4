package main

import (
	"Ex4/REST_API/models"
	"bufio"
	"encoding/json"
	"fmt"
	"net"
	"os"
)

var servAddr = "localhost:23456"

func add_new_Image(img models.Image) (retMessage string) {
	tcpAddr, err := net.ResolveTCPAddr("tcp", servAddr)
	if err != nil {
		println("ResolveTCPAddr failed:", err.Error())
		os.Exit(1)
	}
	conn, err := net.DialTCP("tcp", nil, tcpAddr)
	if err != nil {
		println("Dial failed:", err.Error())
		os.Exit(1)
	}
	req := new(models.Request)
	req.Command = "ADD_PHOTO_TO_ALBUM"
	req.Data, err = json.Marshal(img)
	if err != nil {
		panic(err)
	}
	b, _ := json.Marshal(req)
	_, err = conn.Write(b)
	if err != nil {
		panic(err)
	}
	message, _ := bufio.NewReader(conn).ReadString('\n')
	fmt.Print("Message from server: " + message)
	return message
}

func create_new_album(album models.Album) (retMessage string) {
	tcpAddr, err := net.ResolveTCPAddr("tcp", servAddr)
	if err != nil {
		println("ResolveTCPAddr failed:", err.Error())
		os.Exit(1)
	}
	conn, err := net.DialTCP("tcp", nil, tcpAddr)
	if err != nil {
		println("Dial failed:", err.Error())
		os.Exit(1)
	}
	req := new(models.Request)
	req.Command = "CREATE_NEW_ALBUM"
	req.Data, err = json.Marshal(album)
	if err != nil {
		panic(err)
	}
	b, _ := json.Marshal(req)
	_, err = conn.Write(b)
	if err != nil {
		panic(err)
	}
	message, _ := bufio.NewReader(conn).ReadString('\n')
	fmt.Print("Message from server: " + message)
	return message
}

func get_image_from_album(img models.Image) (retMessage string) {
	tcpAddr, err := net.ResolveTCPAddr("tcp", servAddr)
	if err != nil {
		println("ResolveTCPAddr failed:", err.Error())
		os.Exit(1)
	}
	conn, err := net.DialTCP("tcp", nil, tcpAddr)
	if err != nil {
		println("Dial failed:", err.Error())
		os.Exit(1)
	}
	req := new(models.Request)
	req.Command = "GET_PHOTO_FROM_ALBUM"
	req.Data, err = json.Marshal(img)
	if err != nil {
		panic(err)
	}
	b, _ := json.Marshal(req)
	_, err = conn.Write(b)
	if err != nil {
		panic(err)
	}
	message, _ := bufio.NewReader(conn).ReadString('\n')
	fmt.Print("Message from server: " + message)
	return message
}
