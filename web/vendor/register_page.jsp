<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://kit.fontawesome.com/5b1bb6ee02.js" crossorigin="anonymous"></script>
  </head>
  <body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <div class="container">
        <a class="navbar-brand" href="/">
          <img src="/vendor/images/icon.webp" width="30" height="30" class="d-inline-block align-top" alt="" loading="lazy">
          ARALASU.KZ
        </a>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <a class="nav-link" href="/register"><i class="fas fa-user-plus"></i> Register <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/login"><i class="fas fa-sign-in-alt"></i> Login</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#"><i class="far fa-question-circle"></i> FAQ</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#" tabindex="-1" aria-disabled="true"><i class="fas fa-info-circle"></i> About Aralasu</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="row justify-content-center mt-4">
      <div class="col-5">
        <div class="d-flex justify-content-center">
          <h3>Create new Account</h3>
        </div>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col-5">
        <form action="/register" method="post">
          <div class="form-group">
            <label for="exampleInputEmail1">Email address</label>
            <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="email" required>
            <small id="emailHelp" class="form-text text-muted">Input your email</small>
          </div>
          <div class="d-none" id="password_warning" role="alert">
            PASSWORDS ARE NOT SAME!
          </div>
          <div class="form-group">
            <label for="exampleInputPassword1">Password</label>
            <input type="password" class="form-control" id="exampleInputPassword1" aria-describedby="passwordHelp" name="password" required onchange="isPasswordOk()">
            <small id="passwordHelp" class="form-text text-muted">Input your password</small>
          </div>
          <div class="form-group">
            <label for="exampleReInputPassword1">Re-Password</label>
            <input type="password" class="form-control" id="exampleReInputPassword1" aria-describedby="rePasswordHelp" name="re_password" required onchange="isPasswordOk()">
            <small id="rePasswordHelp" class="form-text text-muted">Re-input your password</small>
          </div>
          <div class="form-group">
            <label for="exampleFullName1">Full Name</label>
            <input type="text" class="form-control" id="exampleFullName1" aria-describedby="fullNameHelp" name="full_name" required>
            <small id="fullNameHelp" class="form-text text-muted">Input your full name</small>
          </div>
          <div class="form-group">
            <label for="exampleBirthdate1">Birthdate</label>
            <input type="date" class="form-control" id="exampleBirthdate1" aria-describedby="birthdateHelp" name="birthdate" required>
            <small id="birthdateHelp" class="form-text text-muted">Input your birthdate</small>
          </div>
          <button type="submit" class="btn btn-primary" id="submit_button">Sign up</button>
        </form>
      </div>
    </div>

    <div class="fixed-bottom text-white bg-dark">
      <div class="row justify-content-center">
        <div class="col-5">
          <div class="d-flex justify-content-center">
            <p class="mt-4 mb-4">Copyright © aralasu.kz 2020</p>
          </div>
        </div>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <script src="/vendor/js/main.js"></script>
  </body>
</html>