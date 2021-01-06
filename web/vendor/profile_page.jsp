<%@ page import="models.User" %>
<%@ page import="models.Post" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
  <head>
    <%@include file="includes/head.jsp"%>
  </head>
  <body>

  <%
    User user = (User) request.getAttribute("user");
    User profileOwner = (User) request.getAttribute("profile_owner");
    boolean isOwner = (boolean) request.getAttribute("is_owner");
    List<Post> posts = (List<Post>) request.getAttribute("posts");
    SimpleDateFormat dateFormat = new SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault());
    boolean isFriends = (boolean) request.getAttribute("is_friends");
  %>

  <%@include file="includes/navbar.jsp"%>

    <div class="container mt-3 mb-3">
      <div class="row">
        <div class="col-3">
          <div class="d-flex flex-column">
            <div class="row">
              <div class="col">
                <%
                  if (profileOwner.getPictureUrl() == null) {
                %>
                <img src="/vendor/images/fodera.png" style="max-width: 255px; max-height: 255px;">
                <%
                  } else {
                %>
                <img src="<%=profileOwner.getPictureUrl()%>" style="max-width: 255px; max-height: 255px;">
                <%
                  }
                %>
              </div>
            </div>
            <div class="row mt-3">
              <div class="col">
                <div class="border px-4 py-2 font-weight-bold"><%=profileOwner.getFullName()%>, <%=profileOwner.getAge()%> years</div>
              </div>
              <div class="w-100"></div>
              <div class="col">
                <%
                  if (isOwner) {
                %>
                <a href="/profile?id=<%=user.getId()%>" class="text-decoration-none"><div class="border px-4 py-2 text-info font-weight-bold">My Profile</div></a>
                <%
                  } else if (isFriends) {
                %>
                  <a href="/remove_from_friends?id=<%=profileOwner.getId()%>" class="text-decoration-none"><div class="border px-4 py-2 text-info font-weight-bold">Remove From Friends</div></a>
                <%
                  } else {
                %>
                  <a href="/add_to_friends?id=<%=profileOwner.getId()%>" class="text-decoration-none"><div class="border px-4 py-2 text-info font-weight-bold">Add To Friends</div></a>
                <%
                  }
                %>
              </div>
              <div class="w-100"></div>
              <div class="col">
                <%
                  if (isOwner) {
                %>
                <a href="/profile?id=<%=user.getId()%>" class="text-decoration-none"><div class="border px-4 py-2 text-info font-weight-bold">Settings</div></a>
                <%
                } else {
                %>
                <a href="/chat?id=<%=profileOwner.getId()%>" class="text-decoration-none"><div class="border px-4 py-2 text-info font-weight-bold">Send Message</div></a>
                <%
                  }
                %>
              </div>
              <div class="w-100"></div>
              <div class="col">
                <a href="/logout" class="text-decoration-none"><div class="border px-4 py-2 text-danger font-weight-bold">Logout</div></a>
              </div>
            </div>
          </div>
        </div>
        <%
          if (isOwner) {
        %>
        <div class="col-6">
          <div class="d-flex flex-column">
            <div class="row">
              <div class="col">
                <h3>Edit Profile</h3>
              </div>
            </div>
            <div class="row">
              <div class="col">
                <form action="/update_general_information" method="post">
                  <div class="form-group">
                    <label for="inputEmail">Email address</label>
                    <input type="email" class="form-control" id="inputEmail" aria-describedby="emailHelp" name="email" value="<%=profileOwner.getEmail()%>" readonly>
                  </div>
                  <div class="form-group">
                    <label for="inputFullName">Full Name</label>
                    <input type="text" class="form-control" id="inputFullName" aria-describedby="fullNameHelp" name="full_name" value="<%=profileOwner.getFullName()%>">
                    <small id="fullNameHelp" class="form-text text-muted">Change your full name</small>
                  </div>
                  <div class="form-group">
                    <label for="inputBirthdate">Birthdate</label>
                    <input type="date" class="form-control" id="inputBirthdate" aria-describedby="birthdateHelp" name="birthdate" value="<%=profileOwner.getBirthdate()%>">
                    <small id="birthdateHelp" class="form-text text-muted">Change your birthdate</small>
                  </div>
                  <button type="submit" class="btn btn-primary">Update Profile</button>
                </form>
              </div>
            </div>
            <div class="row"><div class="col"><hr></div></div>
            <div class="row mt-3">
              <div class="col">
                <h3>Edit Picture</h3>
              </div>
            </div>
            <div class="row">
              <div class="col">
                <form action="/update_profile_picture" method="post">
                  <div class="form-group">
                    <label for="inputProfilePicture">URL</label>
                    <input type="text" class="form-control" id="inputProfilePicture" aria-describedby="profilePictureHelp" name="picture_url" value="<%=profileOwner.getPictureUrl()%>">
                    <small id="profilePictureHelp" class="form-text text-muted">Input url of your profile photo</small>
                  </div>
                  <button type="submit" class="btn btn-primary">Update URL</button>
                </form>
              </div>
            </div>
            <div class="row"><div class="col"><hr></div></div>
            <div class="row mt-3">
              <div class="col">
                <h3>Update Password</h3>
              </div>
            </div>
            <div class="row">
              <div class="col">
                <form action="/update_password" method="post">
                  <div class="form-group">
                    <label for="exampleOldPassword1">Old Password</label>
                    <input type="password" class="form-control" id="exampleOldPassword1" aria-describedby="oldPasswordHelp" name="old_password" required onchange="checkPasswordsForUpdate()">
                    <small id="oldPasswordHelp" class="form-text text-muted">Input your old password</small>
                  </div>
                  <div class="form-group">
                    <label for="exampleNewPassword1">New Password</label>
                    <input type="password" class="form-control" id="exampleNewPassword1" aria-describedby="newPasswordHelp" name="new_password" required onchange="checkPasswordsForUpdate()">
                    <small id="newPasswordHelp" class="form-text text-muted">Input your new password</small>
                  </div>
                  <div class="form-group">
                    <label for="exampleReNewPassword1">Re-New Password</label>
                    <input type="password" class="form-control" id="exampleReNewPassword1" aria-describedby="reNewPasswordHelp" name="re_new_password" required onchange="checkPasswordsForUpdate()">
                    <small id="reNewPasswordHelp" class="form-text text-muted">Re-input your new password again</small>
                  </div>
                  <button type="submit" class="btn btn-primary" id="updatePasswordButton">Update Password</button>
                </form>
              </div>
            </div>
          </div>
        </div>
        <%
          } else {
        %>
        <div class="col-6">
          <div class="d-flex flex-column">
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
        <%
          }
        %>
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
    <%
        }
      }
    %>

  <%@include file="includes/footer.jsp"%>
  </body>
</html>