package main

import (
	"Ex4/REST_API/models"
	"encoding/json"
	"fmt"
	"net/http"
)

func add_new_Image_handler(writer http.ResponseWriter, request *http.Request) {
	decoder := json.NewDecoder(request.Body)
	var img models.Image
	err := decoder.Decode(&img)
	if err != nil {
		panic(err)
	}
	ret := add_new_Image(img)
	writer.Write([]byte(ret))
}

func create_new_album_handler(writer http.ResponseWriter, request *http.Request) {
	decoder := json.NewDecoder(request.Body)
	var album models.Album
	err := decoder.Decode(&album)
	if err != nil {
		panic(err)
	}
	ret := create_new_album(album)
	writer.Write([]byte(ret))
}

func get_image_from_album_handler(writer http.ResponseWriter, request *http.Request) {
	decoder := json.NewDecoder(request.Body)
	var img models.Image
	err := decoder.Decode(&img)
	if err != nil {
		panic(err)
	}
	ret := get_image_from_album(img)
	writer.Write([]byte(ret))
}

func main() {
	http.HandleFunc("/createAlbum", create_new_album_handler)
	http.HandleFunc("/addImage", add_new_Image_handler)
	http.HandleFunc("/getImage", get_image_from_album_handler)

	fmt.Println("Start C2G Server..")
	if err := http.ListenAndServe(":30000", nil); err != nil {
		panic(err)
	}
}
