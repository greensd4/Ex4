package models

import (
	"debug/dwarf"
	"net"
	"os"
)

type client struct {
	connection   net.Conn
	network    string
	address   string
}

func create_connection(client client) (*client, error) {
	servAddr := "127.0.0.1:8081"
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
	client.connection = conn
	return &client, err



}

func send_data(client *client, data string) (bool2 bool, error) {
	a, err = client.connection.Write([]byte(data))
	if a != nil {
		println("Write to server failed:", err.Error())
		os.Exit(1)
	}

	println("write to server = ", strEcho)

	reply := make([]byte, 1024)

	_, err = conn.Read(reply)
	if err != nil {
		println("Write to server failed:", err.Error())
		os.Exit(1)
	}

	println("reply from server=", string(reply))

	conn.Close()
}
