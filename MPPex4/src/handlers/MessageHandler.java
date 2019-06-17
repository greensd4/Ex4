package handlers;

import common.Commands;
import models.Request;
import models.Response;

public class MessageHandler {

    private CreateNewAlbumHandler createNewAlbumHandler = new CreateNewAlbumHandler();
    private  AddImageToAlbumHandler addImageToAlbumHandler = new AddImageToAlbumHandler();
    private GetImageHandler getGetImageHandler = new GetImageHandler();


    public Response messageReceived(String xmlMessage){
        System.out.println("message received " + xmlMessage);
        Request message = new Request();
        Commands cmd = message.getCommand();
        Response res = null;
        switch (cmd){
            case ADD_PHOTO_TO_ALBUM:
                res = addImageToAlbumHandler.handle(message);
                break;
            case CREATE_NEW_ALBUM:
                res = createNewAlbumHandler.handle(message);
                break;
            case GET_PHOTO_FROM_ALBUM:
                res = getGetImageHandler.handle(message);
                break;
            default:
                break;
        }
        return res;
    }
}
