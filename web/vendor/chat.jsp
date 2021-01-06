<%@ page import="models.User" %>
<%@ page import="models.Post" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="models.Chat" %>
<%@ page import="models.Message" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="includes/head.jsp"%>
</head>
<body>

<%
    User user = (User) request.getAttribute("user");
    User user2 = (User) request.getAttribute("user2");
    Chat chat = (Chat) request.getAttribute("chat");
    List<Message> messages = (List<Message>) request.getAttribute("messages");
    SimpleDateFormat dateFormat = new SimpleDateFormat("mm:HH dd.MM.yyyy", Locale.getDefault());
%>

<%@include file="includes/navbar.jsp"%>

<div class="container mt-3 mb-3">
    <div class="row">
        <div class="col-3">
            <div class="d-flex flex-column">
                <div class="row">
                    <div class="col">
                        <%
                            if (user.getPictureUrl() == null) {
                        %>
                        <img src="/vendor/images/fodera.png" style="max-width: 255px; max-height: 255px;">
                        <%
                        } else {
                        %>
                        <img src="<%=user.getPictureUrl()%>" style="max-width: 255px; max-height: 255px;">
                        <%
                            }
                        %>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col">
                        <div class="border px-4 py-2 font-weight-bold"><%=user.getFullName()%>, <%=user.getAge()%> years</div>
                    </div>
                    <div class="w-100"></div>
                    <div class="col">
                        <a href="/profile?id=<%=user.getId()%>" class="text-decoration-none"><div class="border px-4 py-2 text-info font-weight-bold">My Profile</div></a>
                    </div>
                    <div class="w-100"></div>
                    <div class="col">
                        <div class="border px-4 py-2 text-info font-weight-bold">Settings</div>
                    </div>
                    <div class="w-100"></div>
                    <div class="col">
                        <a href="/logout" class="text-decoration-none"><div class="border px-4 py-2 text-danger font-weight-bold">Logout</div></a>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="d-flex flex-column">
                <div class="d-flex flex-column mb-3" style="height: 600px; overflow-y: scroll;">
                    <%
                        if (messages != null) {
                            for (Message message : messages) {
                    %>
                    <div class="row mb-2">
                        <div class="col-2 p-0">
                            <%
                                if (message.getSender().getPictureUrl() == null) {
                                    out.print("<img src=\"/vendor/images/fodera.png\" class=\"w-100 rounded-circle\">");
                                } else {
                                    out.print("<img src=\"" + message.getSender().getPictureUrl() + "\" class=\"w-100\">");
                                }
                            %>
                        </div>
                        <div class="col-8">
                            <h6><%=message.getSender().getFullName()%></h6>
                            <p><%=message.getText()%></p>
                        </div>
                        <div class="col-2">
                            <p class="text-secondary" style="font-size: small"><%=dateFormat.format(message.getSentDate())%></p>
                        </div>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>
                <div class="d-flex p-2 border">
                    <form action="/send_message" method="post" class="w-100">
                        <div class="row">
                            <div class="col-9">
                                <input type="text" class="form-control" name="message_text" required placeholder="Send Message">
                            </div>
                            <input type="hidden" name="receiver_id" value="<%=user2.getId()%>">
                            <input type="hidden" name="chat_id" value="<%=chat.getId()%>">
                            <div class="col-3">
                                <input type="submit" class="btn btn-primary w-100" value="Send">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-3">
            <div class="d-flex flex-column">
                <div class="row">
                    <div class="col">
                        <div class="card">
                            <div class="card-header bg-info text-white font-weight-bold">
                                Latest birthdays
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item border-0">Gafar Serikkaliev, tomorrow</li>
                                <li class="list-group-item border-0">Faraby Nursultan, 2 October</li>
                                <li class="list-group-item border-0">Dias Abylkassym</li>
                                <li class="list-group-item border-0">Yernay Yersayin</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="row mt-4">
                    <div class="col">
                        <div class="card">
                            <div class="card-header bg-info text-white font-weight-bold">
                                My Games
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item border-0 text-primary font-weight-bold">FOOTBALL ONLINE</li>
                                <li class="list-group-item border-0 text-primary font-weight-bold">PING PONG ONLINE</li>
                                <li class="list-group-item border-0 text-primary font-weight-bold">CHESS MASTERS</li>
                                <li class="list-group-item border-0 text-primary font-weight-bold">RACES ONLINE</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="includes/footer.jsp"%>
</body>
</html>