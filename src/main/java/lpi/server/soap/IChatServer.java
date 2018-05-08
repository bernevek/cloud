
package lpi.server.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 */
@WebService(name = "IChatServer", targetNamespace = "http://soap.server.lpi/")
@XmlSeeAlso({
        ObjectFactory.class
})
public interface IChatServer {


    /**
     *
     */
    @WebMethod
    @RequestWrapper(localName = "ping", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.Ping")
    @ResponseWrapper(localName = "pingResponse", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.PingResponse")
    @Action(input = "http://soap.server.lpi/IChatServer/pingRequest", output = "http://soap.server.lpi/IChatServer/pingResponse")
    public void ping();

    /**
     * @param text
     * @return returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "echo", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.Echo")
    @ResponseWrapper(localName = "echoResponse", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.EchoResponse")
    @Action(input = "http://soap.server.lpi/IChatServer/echoRequest", output = "http://soap.server.lpi/IChatServer/echoResponse")
    public String echo(
            @WebParam(name = "text", targetNamespace = "")
                    String text);

    /**
     * @param password
     * @param login
     * @return returns java.lang.String
     * @throws LoginFault
     * @throws ServerFault
     * @throws ArgumentFault
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "login", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.LoginResponse")
    @Action(input = "http://soap.server.lpi/IChatServer/loginRequest", output = "http://soap.server.lpi/IChatServer/loginResponse", fault = {
            @FaultAction(className = LoginFault.class, value = "http://soap.server.lpi/IChatServer/login/Fault/LoginException"),
            @FaultAction(className = ArgumentFault.class, value = "http://soap.server.lpi/IChatServer/login/Fault/ArgumentException"),
            @FaultAction(className = ServerFault.class, value = "http://soap.server.lpi/IChatServer/login/Fault/ServerException")
    })
    public String login(
            @WebParam(name = "login", targetNamespace = "")
                    String login,
            @WebParam(name = "password", targetNamespace = "")
                    String password)
            throws ArgumentFault, LoginFault, ServerFault
    ;

    /**
     * @param sessionId
     * @return returns java.util.List<java.lang.String>
     * @throws ServerFault
     * @throws ArgumentFault
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "listUsers", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.ListUsers")
    @ResponseWrapper(localName = "listUsersResponse", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.ListUsersResponse")
    @Action(input = "http://soap.server.lpi/IChatServer/listUsersRequest", output = "http://soap.server.lpi/IChatServer/listUsersResponse", fault = {
            @FaultAction(className = ArgumentFault.class, value = "http://soap.server.lpi/IChatServer/listUsers/Fault/ArgumentException"),
            @FaultAction(className = ServerFault.class, value = "http://soap.server.lpi/IChatServer/listUsers/Fault/ServerException")
    })
    public List<String> listUsers(
            @WebParam(name = "sessionId", targetNamespace = "")
                    String sessionId)
            throws ArgumentFault, ServerFault
    ;

    /**
     * @param sessionId
     * @param message
     * @throws ServerFault
     * @throws ArgumentFault
     */
    @WebMethod
    @RequestWrapper(localName = "sendMessage", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.SendMessage")
    @ResponseWrapper(localName = "sendMessageResponse", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.SendMessageResponse")
    @Action(input = "http://soap.server.lpi/IChatServer/sendMessageRequest", output = "http://soap.server.lpi/IChatServer/sendMessageResponse", fault = {
            @FaultAction(className = ArgumentFault.class, value = "http://soap.server.lpi/IChatServer/sendMessage/Fault/ArgumentException"),
            @FaultAction(className = ServerFault.class, value = "http://soap.server.lpi/IChatServer/sendMessage/Fault/ServerException")
    })
    public void sendMessage(
            @WebParam(name = "sessionId", targetNamespace = "")
                    String sessionId,
            @WebParam(name = "message", targetNamespace = "")
                    Message message)
            throws ArgumentFault, ServerFault
    ;

    /**
     * @param sessionId
     * @return returns lpi.server.soap.Message
     * @throws ServerFault
     * @throws ArgumentFault
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "receiveMessage", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.ReceiveMessage")
    @ResponseWrapper(localName = "receiveMessageResponse", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.ReceiveMessageResponse")
    @Action(input = "http://soap.server.lpi/IChatServer/receiveMessageRequest", output = "http://soap.server.lpi/IChatServer/receiveMessageResponse", fault = {
            @FaultAction(className = ArgumentFault.class, value = "http://soap.server.lpi/IChatServer/receiveMessage/Fault/ArgumentException"),
            @FaultAction(className = ServerFault.class, value = "http://soap.server.lpi/IChatServer/receiveMessage/Fault/ServerException")
    })
    public Message receiveMessage(
            @WebParam(name = "sessionId", targetNamespace = "")
                    String sessionId)
            throws ArgumentFault, ServerFault
    ;

    /**
     * @param file
     * @param sessionId
     * @throws ServerFault
     * @throws ArgumentFault
     */
    @WebMethod
    @RequestWrapper(localName = "sendFile", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.SendFile")
    @ResponseWrapper(localName = "sendFileResponse", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.SendFileResponse")
    @Action(input = "http://soap.server.lpi/IChatServer/sendFileRequest", output = "http://soap.server.lpi/IChatServer/sendFileResponse", fault = {
            @FaultAction(className = ArgumentFault.class, value = "http://soap.server.lpi/IChatServer/sendFile/Fault/ArgumentException"),
            @FaultAction(className = ServerFault.class, value = "http://soap.server.lpi/IChatServer/sendFile/Fault/ServerException")
    })
    public void sendFile(
            @WebParam(name = "sessionId", targetNamespace = "")
                    String sessionId,
            @WebParam(name = "file", targetNamespace = "")
                    FileInfo file)
            throws ArgumentFault, ServerFault
    ;

    /**
     * @param sessionId
     * @return returns lpi.server.soap.FileInfo
     * @throws ServerFault
     * @throws ArgumentFault
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "receiveFile", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.ReceiveFile")
    @ResponseWrapper(localName = "receiveFileResponse", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.ReceiveFileResponse")
    @Action(input = "http://soap.server.lpi/IChatServer/receiveFileRequest", output = "http://soap.server.lpi/IChatServer/receiveFileResponse", fault = {
            @FaultAction(className = ArgumentFault.class, value = "http://soap.server.lpi/IChatServer/receiveFile/Fault/ArgumentException"),
            @FaultAction(className = ServerFault.class, value = "http://soap.server.lpi/IChatServer/receiveFile/Fault/ServerException")
    })
    public FileInfo receiveFile(
            @WebParam(name = "sessionId", targetNamespace = "")
                    String sessionId)
            throws ArgumentFault, ServerFault
    ;

    /**
     * @param sessionId
     * @throws ServerFault
     * @throws ArgumentFault
     */
    @WebMethod
    @RequestWrapper(localName = "exit", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.Exit")
    @ResponseWrapper(localName = "exitResponse", targetNamespace = "http://soap.server.lpi/", className = "lpi.server.soap.ExitResponse")
    @Action(input = "http://soap.server.lpi/IChatServer/exitRequest", output = "http://soap.server.lpi/IChatServer/exitResponse", fault = {
            @FaultAction(className = ArgumentFault.class, value = "http://soap.server.lpi/IChatServer/exit/Fault/ArgumentException"),
            @FaultAction(className = ServerFault.class, value = "http://soap.server.lpi/IChatServer/exit/Fault/ServerException")
    })
    public void exit(
            @WebParam(name = "sessionId", targetNamespace = "")
                    String sessionId)
            throws ArgumentFault, ServerFault
    ;

}
