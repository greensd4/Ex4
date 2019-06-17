# Ex4

#API

#localhost:23456/createAlbum
#Body:
      { 
        "Creator": string,
        "AlbumName": string
      }
#localhost:23456/addImage
#Body:
      { 
        "ImageName": string,
        "AlbumName": string,
        "ImageData": byte[]
      }

#localhost:23456/getImage
#Body:
      { 
        "ImageName": string,
        "AlbumName": string,
        "ImageData": byte[]
      }