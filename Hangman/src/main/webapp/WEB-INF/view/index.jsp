<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Hangman: The Game</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
  <link rel="stylesheet" href="css/styles.css"> 
</head>
<body >
<header class="main-header">
 <a href="/"><img src="images/logowhite.png" class="logo" title="Home"></a> 
 
<c:choose>
	 <c:when test="${ empty user }">
		<div class="user-acc-options">
		    <button onclick="document.getElementById('id01').style.display='block'" class="w3-button w3-black w3-round w3-large">Login</button>
		    <button onclick="document.getElementById('id02').style.display='block'" class="w3-button w3-black w3-round w3-large">Sign Up</button>
		    <!-- start login button-->
		    <div id="id01" class="w3-modal">
		        <div class="w3-modal-content w3-card-4 w3-animate-zoom" style="max-width:600px">
		    
		          <div class="w3-center"><br>
		            <span onclick="document.getElementById('id01').style.display='none'" class="w3-button w3-xlarge w3-hover-red w3-display-topright" title="Close Modal">&times;</span>
		          </div>
		    
		          <form class="w3-container" action="Login.do" method="POST">
		            <div class="w3-section">
		              <label><b>Username</b></label>
		              <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Enter Username" name="username" required>
		              <label><b>Password</b></label>
		              <input class="w3-input w3-border" type="password" placeholder="Enter Password" name="password" required>
		              <button class="w3-button w3-block w3-black w3-section w3-padding" type="submit">Login</button>
		              
		            </div>
		          </form>
		    
		          <div class="w3-container w3-border-top w3-padding-16 w3-light-grey">
		            <button onclick="document.getElementById('id01').style.display='none'" type="button" class="w3-button w3-red">Cancel</button>
		           
		          </div>
		    
		        </div>
		      </div> 
		      <!-- end login button-->
		      <!--start sign in button-->
		      <div id="id02" class="w3-modal">
		          <div class="w3-modal-content w3-card-4 w3-animate-zoom" style="max-width:600px">
		      
		            <div class="w3-center"><br>
		              <span onclick="document.getElementById('id02').style.display='none'" class="w3-button w3-xlarge w3-hover-red w3-display-topright" title="Close Modal">&times;</span>
		            </div>
		      
		            <form class="w3-container" action="CreateAccount.do" method="POST" modelAttribute="userDTO">
						<h3>Create New Account</h3>
						<hr>
		            
		              <div class="w3-section">
		                <label><b>Username</b></label>
		                <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Enter Username" name="username" required>
		                <label><b>Password</b></label>
		                <input class="w3-input w3-border" type="password" placeholder="Enter Password" name="password" required>
		                	<hr>
							<h4>Select and answer at least one Secret Question</h4>
											
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
		                <button class="w3-button w3-block w3-black w3-section w3-padding" type="submit">Sign up</button>
		                
		              </div>
		            </form>
		      
		            <div class="w3-container w3-border-top w3-padding-16 w3-light-grey">
		              <button onclick="document.getElementById('id02').style.display='none'" type="button" class="w3-button w3-red">Cancel</button>
		             
		            </div>
		      
	          </div>
        </div> 
        <!-- end sign up button-->
	</c:when>
	
	<c:otherwise>
		<div class="user-acc-options">
			<a class="w3-button w3-black w3-round w3-large">${ user.username } (${user.totalPoints})</a>
			<a class="w3-button w3-black w3-round w3-large" href="logout.do">Logout</a>
		</div>
	</c:otherwise>
</c:choose>
  
<nav>   
	<c:if test="${not empty user }">
		<div class="w3-bar w3-dark-black w3-large">
	        <button onclick="document.getElementById('acc').style.display='block'"
	        class="w3-button w3-mobile" style="width:33%" >Account</button>
	        <div id="acc" class="w3-modal" >
	          <div class="w3-container">
	          <div class="w3-modal-content">
	              <div class="w3-section">
	                  <label>Username: ${user.username }</label>
	                </div>
	              <div class="w3-section">
	                  <label>Game History:</label>
	                  <table class="w3-table w3-bordered">
	                      <tr>
	                          <th>Word</th>
	                          <th>Points</th>
	                          <th>Difficulty</th>
	                          <th>Date<th>
                  		  </tr>
	                      <c:forEach var="game" items="${history }">
	                      		<tr>
	                      			<td>${game.word.word}</td>
	                      			<td>${game.pointsAwarded }</td>
	                      			<td>${game.word.difficulty }</td>
	                      			<td>${game.gameDate }</td>
	                      		</tr>
	                      </c:forEach>
	                  </table>
	              </div>
	            
	          
	                
	                <div class="w3-section">
	                    <button class="w3-button w3-yellow">Reset Account</button>
	                    <button class="w3-button w3-red">Delete Account</button>
	                </div>
	                <div class="w3-container w3-padding-16 w3-light-grey">
	                    <button onclick="document.getElementById('acc').style.display='none'" type="button" class="w3-button w3-grey">Return to game</button>
	                   
	                  </div>
	                </div>
	              </div>
	          </div>
	          
	        </div>

	</c:if>
          <button onclick="document.getElementById('ldr').style.display='block'"
        class="w3-button w3-mobile" style="width:33%">Leaderboards</button>
        <div id="ldr" class="w3-modal" >
          <div class="w3-modal-content">
              <table class="w3-table w3-bordered">
                  <tr>
                      <th>Username</th>
                      <th>Points</th>
                  </tr>
                  <c:forEach var="leader" items="${leaders }">
                  	<tr>
                  		<td>${leader.username }</td>
                  		<td>${leader.totalPoints }</td>
                  	</tr>
                  </c:forEach>
                    
              </table>
              <div class="w3-container w3-padding-16 w3-light-grey">
                  <button onclick="document.getElementById('ldr').style.display='none'" type="button" class="w3-button w3-grey">Return to game</button>
                 
                </div>
              </div>
              
          </div>

	<c:if test="${not empty user }">
        <button onclick="document.getElementById('pref').style.display='block'"
        class="w3-button w3-mobile" style="width:33%">Preferences</button>
        <div id="pref" class="w3-modal">
          <div class="w3-modal-content">
              <div class="w3-container" action="#default">
                  <div class="w3-section">
                      <input class="w3-check" type="checkbox">
                      <label>Hints</label>
                    </div>
                    <div class="w3-section">
                        <label><b>Select number of guesses allowed:</b></label>
                        <select class="w3-select w3-border w3-margin-bottom" name="option">
                            <option value="" disabled selected>10</option>
                            <option value="1">9</option>
                            <option value="2">8</option>
                            <option value="3">7</option>
                            <option value="4">6</option>
                            <option value="5">5</option>
                        </select>
                    </div>
                    <div class="w3-container w3-padding-16 w3-light-grey">
                        <button onclick="document.getElementById('pref').style.display='none'" type="button" class="w3-button w3-grey">Return to game</button>
                       
                      </div>
                </div>
              </div>
          </div>
        
       </c:if>
      </div>
</nav>
</header>
    
    
   
       <section> 
	       <div id="hint" style="border: 1px solid black" class="hintContainer">
	      	 <div>
	       		<c:choose>
	       		<%-- User is not logged in -- display difficulty selection only --%> 
		       		<c:when test="${empty user }">
		       			<form action="newGame.do" method="GET" >
			       			<label for="difficulty">Choose Difficulty</label>
			       			<select name="difficulty">
			       				<option value="easy" ${ difficulty == 'easy' ? 'selected' : ''}>Easy</option>
			       				<option value="medium" ${ difficulty == 'medium' ? 'selected' : ''}>Medium</option>
			       				<option value="hard" ${ difficulty == 'hard' ? 'selected' : ''}>Hard</option>
			       			</select>
		       				<button id="newGameButton" class="w3-button w3-round w3-tiny w3-khaki w3-padding-small" type="submit">New Game!</button>
		       			</form>
		       		</c:when>
	       		<%--  User is logged in -- Show 'buy hint' button  --%>
		       		<c:otherwise>
		               <form action="newGame.do" method="GET" >
			       			<label for="difficulty">Choose Difficulty</label>
			       			<select name="difficulty">
			       				<option value="easy" ${ user.preferredDifficulty == 'easy'  ? 'selected' : ''}>Easy</option>
			       				<option value="medium" ${ user.preferredDifficulty == 'medium' ? 'selected' : ''}>Medium</option>
			       				<option value="hard" ${ user.preferredDifficulty == 'hard' ? 'selected' : ''}>Hard</option>
			       			</select>
		       				<button id="newGameButton" class="w3-button w3-round w3-tiny w3-khaki w3-padding-small" type="submit">New Game!</button>
		       			</form>
		       		
		       		
		       		</c:otherwise>
	            </c:choose>
	           </div>
	           
	           <!-- "Buy Hint" button -->
	           <div>
		           <c:choose>
		           		<c:when test="${empty user }">
		           			<form action="">
			       				<button id="buyHintButton" class="w3-button w3-round w3-tiny w3-khaki w3-padding-small" type="submit" disabled>Log on to purchase hints</button>
	       					</form>
		           		</c:when>	
		           		<c:otherwise>
			           		<form action="buyHint.do" method="GET" >
			           			<c:choose>
			           				<c:when test="${hintsAvailable > hintsPurchased && not empty word}">
			       						<button id="buyHintButton" class="w3-button w3-round w3-tiny w3-khaki w3-padding-small" type="submit">Buy a hint! (50 Points)</button>
		       						</c:when>
		       						<c:otherwise>
			       						<button id="buyHintButton" class="w3-button w3-round w3-tiny w3-khaki w3-padding-small" disabled>No hints available</button>
		       						</c:otherwise>
	       						</c:choose>
	       					</form>
		           		</c:otherwise>
		           </c:choose>
	           </div>
	           
	           
	            <!-- Hiding this since JS is watching for key presses.  Still calling click() on this button though -->
		      <div id="guessContainer" hidden="true";>
		      	  <form action="guess.do" method="POST">
			      	  <input id="guessInput" type="text" name="guess">
					  <label for="guess">Enter your guess here!</label>	
					  <button id="makeGuessButton" class="w3-button w3-round w3-tiny w3-khaki w3-padding-small" type="submit">Guess!</button>       	  
		      	  </form>
		      </div>
	      </div>
      </section>

       <div class="flex-container">  
	       <section id="game">
		       <div id="game-svg">
	               <svg>
	                <circle cx="290" cy="125" r="40" stroke="black" stroke-width="3" fill="black" />
	               </svg>
		       </div>
		       <div id="wordContainer">
			          <span id="word">${wordString }</span>
		       </div>
	      </section>
      <section id="guess-container" class="container">
	      <div id="guesses">      
	              <h3>Guessed letters</h3>
	              <div>
		              	<c:forEach var="letter" items="${guesses }">
		              		<span class="letter" 
			              		<c:if test="${fn:containsIgnoreCase(wordString, letter) }">style="color: green"</c:if>
			              		<c:if test="${not fn:containsIgnoreCase(wordString, letter) }">style="color: red"</c:if>
		              		>${letter }</span>
		              	</c:forEach>
	              </div>
	              
	      </div>
	      <div id = "guessesRemaining">
	        <c:if test="${not empty wordString }">
	      		<h5>${guessesRemaining } guesses left!</h5>
      		</c:if>
	      		
	      </div>
	      <div id="messagesContainer"> 
	      		<h3>Messages</h3>
	      		<div id="messages">
	      			<c:forEach var="message" items="${messages }">
	      				<div>${message }</div>
	      			</c:forEach>
	      		</div>
	      </div>
      </section>
      
      </div>

      <section id="answer-container">
       <div id="answer" style="border: 1px solid black">
               <div class="form-group">
                   <label for="usr">Answer:</label>
                   <input type="text" class="form-control" id="usr">
               </div>
       </div>  
      </section>
      
     
    

    <footer>
      CMSC 495 <a href="about">About</a>
    </footer>
  


<script src="scripts/scripts.js"></script>

</body>
</html>
