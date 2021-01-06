<%@ page import="models.User" %>
<%@ page import="models.Post" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="includes/head.jsp"%>
</head>
<body>

<%
    User user = (User) request.getAttribute("user");
    List<Post> posts = (List<Post>) request.getAttribute("posts");
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
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addPostModal">Add New</button>
                </div>
                <%
                    if (posts != null) {
                        for (Post post : posts) {
                %>
                <div class="row mb-3">
                    <div class="col p-0">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title"><%=post.getTitle()%></h5>
                                <p class="card-text"><%=post.getShortContent()%></p>
                                <button type="button" class="card-link btn btn-outline-primary" data-toggle="modal" data-target="#moreInfo<%=post.getId()%>">More -></button>
                                <button type="button" class="card-link btn btn-outline-success" data-toggle="modal" data-target="#editPost<%=post.getId()%>">Edit -></button>
                            </div>
                            <div class="card-footer">
                                <span class="text-secondary">Posted on <%=dateFormat.format(post.getDate().getTime())%> by</span> <a href="/profile?id=<%=post.getAuthor().getId()%>" class="text-decoration-none"><span class="text-primary font-weight-bold"><%=post.getAuthor().getFullName()%></span></a>
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

<div class="modal fade" id="addPostModal" tabindex="-1" aria-labelledby="addPostModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addPostModalLabel">Add New Post</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="/add_post" method="post" id="addPostForm" novalidate>
                    <div class="form-group">
                        <label for="titleInput" class="col-form-label">Title:</label>
                        <input type="text" class="form-control" id="titleInput" name="title" required>
                    </div>
                    <div class="form-group">
                        <label for="shortContentInput" class="col-form-label">Short Content:</label>
                        <textarea class="form-control" id="shortContentInput" name="short_content" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="contentInput" class="col-form-label">Content:</label>
                        <textarea class="form-control" id="contentInput" name="content" required></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary" form="addPostForm">Add</button>
            </div>
        </div>
    </div>
</div>

<%
    if (posts != null) {
        for (Post post : posts) {
%>
<div class="modal fade" id="moreInfo<%=post.getId()%>" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel"><%=post.getTitle()%></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p><span class="text-secondary">Posted on <%=dateFormat.format(post.getDate().getTime())%> by</span> <a href="/profile?id=<%=post.getAuthor().getId()%>" class="text-decoration-none"><span class="text-primary font-weight-bold"><%=post.getAuthor().getFullName()%></span></a></p>
                <p><%=post.getShortContent()%></p>
                <p><%=post.getContent()%></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="editPost<%=post.getId()%>" tabindex="-1" aria-labelledby="editModalLabel<%=post.getId()%>" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editModalLabel<%=post.getId()%>">New message</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="/edit_post" method="post" id="editPostForm<%=post.getId()%>" novalidate>
                    <div class="form-group">
                        <label for="post_title_<%=post.getId()%>" class="col-form-label">Title:</label>
                        <input type="text" class="form-control" id="post_title_<%=post.getId()%>" name="title" value="<%=post.getTitle()%>">
                    </div>
                    <div class="form-group">
                        <label for="post_short_content_<%=post.getId()%>" class="col-form-label">Short Content:</label>
                        <textarea class="form-control" id="post_short_content_<%=post.getId()%>" name="short_content"><%=post.getShortContent()%></textarea>
                    </div>
                    <div class="form-group">
                        <label for="post_content_<%=post.getId()%>" class="col-form-label">Content:</label>
                        <textarea class="form-control" id="post_content_<%=post.getId()%>" name="content"><%=post.getContent()%></textarea>
                    </div>
                    <input type="hidden" name="post_id" value="<%=post.getId()%>">
                </form>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary" form="editPostForm<%=post.getId()%>">Edit</button>
                <form action="/delete_post" method="post">
                    <input type="hidden" name="post_id" value="<%=post.getId()%>">
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%
        }
    }
%>

<%@include file="includes/footer.jsp"%>
</body>
</html>