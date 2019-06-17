package main

import (
	"bufio"
	"encoding/json"
	"fmt"
	"net"
	"net/http"
	"os"
)

type Album struct {
	Creator string      `json:"creator"`
	AlbumName string     `json:"albumName"`
}

type Image struct {
	AlbumName string    `json:"albumName"`
	ImageName string    `json:"imageName"`
	ImageData []byte	`json:"imageData"`
}

type Request struct {
	Command string `json:"command"`
	Data []byte     `json:"data"`
}

var servAddr = "localhost:23456"


func add_new_Image_handler(writer http.ResponseWriter, request *http.Request) {
	decoder := json.NewDecoder(request.Body)
	var img Image
	err := decoder.Decode(&img)
	if err != nil {
		panic(err)
	}
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
	req := new (Request)
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
	fmt.Print("Message from server: "+message)
	writer.Write([]byte (message))
}

func create_new_album_handler(writer http.ResponseWriter, request *http.Request, ) {
	decoder := json.NewDecoder(request.Body)
	var album Album
	err := decoder.Decode(&album)
	if err != nil {
		panic(err)
	}
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
	req := new (Request)
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
	fmt.Print("Message from server: "+message)
	writer.Write([]byte (message))
}

func get_image_from_album_handler(writer http.ResponseWriter, request *http.Request) {
	decoder := json.NewDecoder(request.Body)
	var img Image
	err := decoder.Decode(&img)
	if err != nil {
		panic(err)
	}
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
	req := new (Request)
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
	fmt.Print("Message from server: "+message)
	writer.Write([]byte(message))
}

func main() {
	http.HandleFunc("/addNewImage", add_new_Image_handler)
	http.HandleFunc("/createNewAlbum", create_new_album_handler)
	http.HandleFunc("/getImageFromAlbum", get_image_from_album_handler)

	fmt.Println("Start C2G Server..")
	if err := http.ListenAndServe(":3000", nil); err != nil {
		panic(err)
	}
}