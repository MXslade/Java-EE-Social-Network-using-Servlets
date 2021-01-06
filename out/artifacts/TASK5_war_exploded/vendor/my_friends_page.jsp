<%@ page import="models.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="models.Friend" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="includes/head.jsp"%>
</head>
<body>

<%
    User user = (User) request.getAttribute("user");
    List<Friend> friends = (List<Friend>) request.getAttribute("friends");
    List<User> friendRequestSenders = (List<User>) request.getAttribute("friend_request_senders");
    SimpleDateFormat dateFormat = new SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault());
%>

<%@include file="includes/navbar.jsp"%>

<div class="container mt-3">
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
                <div class="row mb-3">
                    <div class="col p-0">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex">
                                    <form action="/search_users" method="get" class="w-100">
                                        <div class="form-group">
                                            <input type="text" name="search_text" class="form-control" onchange="searchForUsers()" onkeypress="searchForUsers()" id="search_input" placeholder="Search Friends">
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="d-flex flex-column" id="location_of_friends_or_found_users">
                <%
                    if (friendRequestSenders != null && friendRequestSenders.size() > 0) {
                %>
                <div class="row mb-3">
                    <div class="col p-0">
                        <div class="card">
                            <div class="card-header">
                                <strong>You have <%=friendRequestSenders.size()%> new request(s)</strong>
                            </div>
                            <div class="card-body">
                                <%
                                    for (User sender : friendRequestSenders) {
                                %>
                                <div class="row mb-3">
                                    <div class="col-3">
                                        <%
                                            if (sender.getPictureUrl() != null) {
                                        %>
                                        <img src="<%=sender.getPictureUrl()%>" class="w-100">
                                        <%
                                            }
                                            else {
                                                out.print("<img src=\"/vendor/images/fodera.png\" class=\"w-100\">");
                                            }
                                        %>
                                    </div>
                                    <div class="col-9">
                                        <h5 class="card-title"><%=sender.getFullName()%></h5>
                                        <p class="card-text"><%=sender.getAge()%> years old</p>
                                        <a class="btn btn-primary" href="/confirm_friend_request?id=<%=sender.getId()%>">Confirm</a>
                                        <a class="btn btn-primary" href="/reject_friend_request?id=<%=sender.getId()%>">Reject</a>
                                    </div>
                                </div>
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                    }
                %>

                <%
                    if (friends != null) {
                        for (Friend friend : friends) {
                %>
                <div class="row mb-3 friends">
                    <div class="col p-0">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-3">
                                        <%
                                            if (friend.getSecondary().getPictureUrl() != null) {
                                        %>
                                            <img src="<%=friend.getSecondary().getPictureUrl()%>" class="w-100">
                                        <%
                                            }
                                            else {
                                                out.print("<img src=\"/vendor/images/fodera.png\" class=\"w-100\">");
                                            }
                                        %>
                                    </div>
                                    <div class="col-9">
                                        <a class="text-decoration-none text-dark" href="/profile?id=<%=friend.getSecondary().getId()%>"><h5 class="card-title"><%=friend.getSecondary().getFullName()%></h5></a>
                                        <p class="card-text"><%=friend.getSecondary().getAge()%> years old</p>
                                        <a class="card-link btn btn-outline-primary" href="/chat?id=<%=friend.getSecondary().getId()%>">Send Message</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                        }
                    }
                %>
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