package models

type Album struct {
	Creator   string `json:"creator"`
	AlbumName string `json:"albumName"`
}

type Image struct {
	AlbumName string `json:"albumName"`
	ImageName string `json:"imageName"`
	ImageData []byte `json:"imageData"`
}

type Request struct {
	Command string `json:"command"`
	Data    []byte `json:"data"`
}
