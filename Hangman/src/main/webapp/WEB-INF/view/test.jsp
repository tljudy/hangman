<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>

<html lang="en">
<head>
<title>Hangman</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/style.css">
</head>
<body style="background-color: #A7CCED; color: #545E75">
	​
	<nav class="navbar navbar-inverse" style="background-color: #304D6D;">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Hangman: The Game</a>
			</div>
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#">Account</a></li>
				<li><a href="#">Preferences</a></li>
				<li><a href="#">Leaderboard</a></li>
			</ul>
				<div class="nav navbar-right">
					<c:choose>
						<c:when test="${ empty user }">
							<div class="dropdown">
								<a class="glyphicon glyphicon-user dropdown-toggle" id="login"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false"> Login</a>
								<div class="dropdown-menu">
									<form action="Login.do" method="POST" class="px-4 py-3" >
										<h3>Login</h3>
										<hr>
										<div class="input-group border">
											<div class="loginFail">${loginFail }</div>
											<label for="username">Username</label> <input
												name="username" type="text" class="form-control"
												aria-label="Text input with radio button"> <br>
											<label for="password">Password</label> <input
												name="password" type="password" class="form-control"
												aria-label="Text input with radio button"> <br>
										</div>
										<div class="pad center">
											<button type="submit" class="btn btn-primary">Submit</button>
										</div>		
									</form>
								</div>
							</div>
							<div class="dropdown">
								<a class="glyphicon glyphicon-user dropdown-toggle" id="signUp"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false"> Sign-Up</a>
								<div class="dropdown-menu">
									<form action="CreateAccount.do" method="POST" class="px-4 py-3" modelAttribute="userDTO">
										<h3>Create New Account</h3>
										<hr>
										<div class="input-group border">
											<label for="username">Username</label> <input
												name="username" type="text" class="form-control"
												aria-label="Text input with radio button"> <br>
											<label for="password">Password</label> <input
												name="password" type="password" class="form-control"
												aria-label="Text input with radio button"> <br>
										</div>
												
										<div class="input-group border">
											<h4>Select and answer at least one
													Secret Question</h4>
										
											<label for="questions1">Question 1</label>
											<select name="questions1" class="form-control"
												aria-label="Text input with radio button">
												<c:forEach var="question" items="${ questions }">
													<option value="${ question.id }">${ question.question }</option>
												</c:forEach>
											</select> 
											<input name="answer1" type="text" class="form-control"
												aria-label="Text input with radio button"> <br>
												
											<label for="questions2">Question 2</label>
											<select name="questions2" class="form-control"
												aria-label="Text input with radio button">
												<c:forEach var="question" items="${ questions }">
													<option value="${ question.id }">${ question.question }</option>
												</c:forEach>
											</select> 
											<input name="answer2" type="text" class="form-control"
												aria-label="Text input with radio button"> <br>
												
											<label for="questions3">Question 3</label>
											<select name="questions3" class="form-control"
												aria-label="Text input with radio button">
												<c:forEach var="question" items="${ questions }">
													<option value="${ question.id }">${ question.question }</option>
												</c:forEach>
											</select> 
											<input name="answer3" type="text" class="form-control"
												aria-label="Text input with radio button"><br>
										</div>
										<div class="pad center">
											<button type="submit" class="btn btn-primary">Submit</button>
										</div>		
									</form>
								</div>
							</div>
						</c:when>

						<c:otherwise>
							<li><a href="#"><span class="glyphicon glyphicon-user"></span> ${ user.username }</a></li>
							<li><a href="logout.do"><span class="glyphicon glyphicon-user"></span> Logout</a></li>
						</c:otherwise>
					</c:choose>
				</div>
		</div>
	</nav>

	<div class="container">


		<!--  
    		THIS IS JUST AN EXAMPLE 
    -->

		<form action="getWord.do" method="GET">
			<div class="col-sm-4">
				<button name="getWord" type="submit" class="btn btn-primary btn-sm"
					style="margin-top: 20px">Click for a test word</button>
			</div>
		</form>


		<div class="row">

			<div class="col-sm-12">
				<h3>Word Hint:</h3>


				<!--  
    		THIS IS JUST AN EXAMPLE 
    -->
				<c:if test="${ not empty word }">

					<p>
						<h1 style="font: underline">Word: ${word.word }</h1>
						<ul>
							<li>ID: ${word.id }</li>
							<li>Difficulty: ${word.difficulty }</li>
							<li>Syllables: ${word.syllables }</li>
						</ul>
					</p>

					<c:if test="${ not empty word.definitions }">

						<p>
							<c:forEach var="def" items="${word.definitions }">

								<h4>Definition</h4>
								<ul>
									<li>ID: ${def.id}</li>
									<li>Part of Speech: ${def.partOfSpeech }</li>
									<li>Definition: ${def.definition}</li>
								</ul>

								<c:if test="${not empty def.examples }">

									<c:forEach var="ex" items="${def.examples }">

										<div style="margin-left: 50px">
											<h4>Example</h4>
											<ul>
												<li>ID: ${ex.id }</li>
												<li>Example: ${ex.sentence }</li>
											</ul>
										</div>

									</c:forEach>
								</c:if>
							</c:forEach>

						</p>

					</c:if>

				</c:if>







			</div>

			<div class="col-sm-8" style="border: 1px solid black">
				<svg width="500" height="500">
                <circle cx="250" cy="250" r="40" stroke="black"
						stroke-width="3" fill="black" />
               </svg>
			</div>

			<div class="col-sm-4" style="border: 1px solid black">
				<h3>Guessed letters</h3>
				<textarea class="form-control" rows="2" id="comment"></textarea>
			</div>








		</div>
		<div class="row">
			<div class="col-sm-12">
				<div class="form-group">
					<label for="usr">Answer:</label> <input type="text"
						class="form-control" id="usr">
				</div>
			</div>
		</div>

		<%-- 
<!--        	Just some test/sample data/functionality.  This is from a "getAllUsers" method in the UserDAOImpl class,
       		which was called from the MainController class.  This routes to the "index.jsp" from MainController 
       		because its index() method is configured to handle all default "/" or "home.do" URLs
       			- "localhost:8675" or "localhost:8675/home.do" -->

			<c:if test="${not empty users}">
		   		<c:forEach var="user" items="${users}">
		   			<div>
		   				<ul>
		   					<li>Username: ${user.username}</li>
		   					<li>Password: ${user.password}</li>
		   					<li>Total Points: ${user.totalPoints}</li>
		   					<li>Preferred Model Color: 
   					  
				<!--    Basically if/else statements   --> 
  					
		   						<c:choose>
		   							<c:when test="${user.preferredModelColor == null}">
		   								NULL, so the default value should be used
	   								</c:when>
	   								<c:otherwise>
	   									${user.preferredModelColor}
	   								</c:otherwise>
   								</c:choose>
		   					</li>
		   					<li>Preferred Difficulty: 
		   						<c:choose>
		   							<c:when test="${user.preferredDifficulty == null}">
		   								NULL, so the default value should be used
	   								</c:when>
	   								<c:otherwise>
	   									${user.preferredDifficulty}
	   								</c:otherwise>
   								</c:choose>
		   					</li>
		   				</ul>
		   			</div>
		   		</c:forEach>
			</c:if>  
--%>
	</div>



</body>
</html>