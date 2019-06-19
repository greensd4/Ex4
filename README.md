### Ex4

# API

## localhost:30000/createAlbum
## Body:
      { 
        "Creator": string,
        "AlbumName": string
      }
## localhost:30000/addImage
### Body:
      { 
        "ImageName": string,
        "AlbumName": string,
        "ImageData": byte[]
      }

## localhost:30000/getImage
### Body:
      { 
        "ImageName": string,
        "AlbumName": string,
        "ImageData": byte[]
      }
