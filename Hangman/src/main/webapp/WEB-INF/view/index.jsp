<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix= "spring" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Hangman: The Game</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
  <link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-blue-grey.css">
  <link rel="stylesheet" href="css/styles.css"> 

</head>
<body class="w3-text-theme w3-white">

<div class="w3-container w3-theme-d5 w3-center " id="header"><!--start header-->
<a href="home.do"><img src="images/logowhite.png" class="logo" title="Home" style="width: 20%; height: auto;"></a> 
 

	<!-- start login section-->
	<div id="log" class="w3-modal">
	    <div class="w3-modal-content w3-card-4 w3-animate-zoom w3-theme-l5" style="max-width:600px">
	        <header class="w3-container w3-theme-d5 w3-center ">
	            <h2>Log In</h2>
	          </header>
	        <form class="w3-container" action="Login.do" method="POST">
	          <div class="w3-section">
	          <label><b>Username</b></label>
	          <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Enter Username" name="username" >
	          <label><b>Password</b></label>
	          <input class="w3-input w3-border w3-margin-bottom" type="password" placeholder="Enter Password" name="password">
	          </div>
	          <div class="w3-section">
	          <button class="w3-button w3-block w3-theme w3-section w3-padding" type="submit">Login</button>
	          </div>
	        </form><!--end login form element-->
	
	        <!--login cancel button-->
	        <div class="w3-container w3-border-top w3-padding-16 w3-theme-l4">
	            <button onclick="document.getElementById('log').style.display='none'" type="button" class="w3-button w3-round-large w3-red">Cancel</button>
	        </div><!--end login cancel button-->
	  </div>
	</div> <!--end login section-->
              
	<div id="sign" class="w3-modal "> <!--start sign up button-->
      <div class="w3-modal-content w3-card-4 w3-animate-zoom w3-theme-l5" style="max-width:600px">
          <header class="w3-container w3-theme-d5 w3-center ">
              <h2>Sign Up</h2>
            </header>
        <form class="w3-container" action="#default">
          <div class="w3-section ">
          <label><b>Username</b></label>
          <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Enter a new Username" name="username" required>
          <label><b>Password</b></label>
          <input class="w3-input w3-border w3-margin-bottom" type="password" placeholder="Enter a new Password" name="password" required>
          </div>

          <div class="w3-section">                
            <button class="w3-button w3-block w3-theme w3-section w3-padding" type="submit">Sign up</button>
          </div>
        </form>

        <div class="w3-container w3-border-top w3-padding-16 w3-theme-l4">
          <button onclick="document.getElementById('sign').style.display='none'" type="button" class="w3-button w3-round-large w3-red">Cancel</button>
        </div>

      </div>
    </div> <!-- end sign up button-->
</div> <!--end header-->
  
<div class="w3-bar w3-theme">
	<c:if test="${not empty user }">
	    <button onclick="document.getElementById('acc').style.display='block'"
    	class="w3-bar-item w3-button w3-mobile"  >Account</button>
    </c:if>

    	<button onclick="document.getElementById('ldr').style.display='block'"
    	class="w3-bar-item w3-button w3-mobile " >Leaderboards</button>
	
	<c:if test="${not empty user }">
    	<button onclick="document.getElementById('pref').style.display='block'"
    	class="w3-bar-item w3-button w3-mobile " >Preferences</button>
    </c:if>

	<c:choose>
		<c:when test="${empty user }">
    		<button onclick="document.getElementById('log').style.display='block'" class=" w3-bar-item w3-button w3-right m3-mobile  ">Login</button>
    		<button onclick="document.getElementById('sign').style.display='block'" class="w3-bar-item w3-button w3-right m3-mobile">Sign Up</button>
		</c:when>
	
		<c:otherwise>
			<div class="user-acc-options">
				<a class="w3-bar-item w3-button w3-right m3-mobile" href="logout.do">Logout</a>
				<a class="w3-bar-item w3-button w3-right m3-mobile">${ user.username } (${user.totalPoints})</a>
			</div>
		</c:otherwise>
	</c:choose>
	
	        <div id="acc" class="w3-modal" >
	          <div class="w3-container">
      <div class="w3-modal-content w3-card-4 w3-animate-zoom w3-theme-l5" style="max-width:600px">

          <header class="w3-container w3-theme-d5 w3-center ">
              <h2>Account Settings</h2>
            </header>

            <div class="w3-bar w3-theme">
                <button class="tablink w3-bar-item  w3-button w3-mobile" style="width:50%" onclick="openTab(event, 'accop')">Account</button>
                <button class="tablink w3-bar-item  w3-button w3-mobile" style="width:50%" onclick="openTab(event, 'gmhist')">Game History</button>
              </div>

          <div id="accop" class="w3-container w3-padding w3-theme-l5 tab ">
              <label>Username: ${user.username} </label>
              <div class="w3-container w3-padding">
                  <form action="resetAccount.do" method="GET">
                    	<button class="w3-button w3-block w3-yellow w3-padding" type="submit">Reset Account</button>
                  </form>
              </div>

          </div>
          <div id="gmhist" class="w3-container tab">
              
              <table class="w3-table w3-bordered w3-center">
                  <tr>
                      <th>Word</th>
                      <th>Points</th>
                      <th>Difficulty</th>
                      <th>Date</th>
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
        
            <div class="w3-container w3-border-top w3-padding-16 w3-theme-l4">
                <button onclick="document.getElementById('acc').style.display='none'" type="button" class="w3-button w3-theme">Return to game</button>
               
              </div>
            </div>
          </div>
      </div> 	
</div>            
	          
	                


<div id="ldr" class="w3-modal" >
      <div class="w3-modal-content w3-card-4 w3-animate-zoom w3-theme-l5" style="max-width: 600px;">
        <header class="w3-container w3-theme-d5 w3-center ">
          <h2>Leaderboards</h2>
        </header>

        <div class="w3-bar w3-theme">
          <button class="tablink w3-bar-item  w3-button w3-mobile" style="width:50%" onclick="openTab(event, 'Overall')">Overall</button>
          <button class="tablink w3-bar-item  w3-button w3-mobile" style="width:50%" onclick="openTab(event, 'Last24')">Last 24 Hours</button>
        </div>
          	<div id="Overall" class="w3-container w3-padding tab ">
              <table class="w3-table w3-bordered w3-center">
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
             </div>
           <div id="Last24" class="w3-container w3-padding tab ">
          		<table class="w3-table w3-bordered w3-center">
          			<tr>
                  		<th>Username</th>
                		<th>Points</th>
                    </tr>
                    <c:forEach var="leader" items="${leadersLast24 }">
	               		<tr>
	               			<td>${leader.key}</td>
	               			<td>${leader.value }</td>
	               		</tr>
                    </c:forEach>
              
             	</table>
      		</div>
              
                  <div class="w3-container w3-padding-16 w3-light-grey">
                        <button onclick="document.getElementById('ldr').style.display='none'" type="button" class="w3-button w3-grey">Return to game</button>
                  </div>
       </div>
</div>

    <div id="pref" class="w3-modal">
      <div class="w3-modal-content w3-card-4 w3-animate-zoom w3-theme-l5" style="max-width: 600px;">

          <header class="w3-container w3-theme-d5 w3-center ">
              <h2>Preferences</h2>
            </header>
              <div class="w3-container" action="#default">
                  <div class="w3-section">
                      <form action="updatePreferences.do" method="PUT" >
                      
						    <p>
						        Preferred Model Color:
					        	<c:choose>
					        		<c:when test="${not empty user.preferredModelColor }" ><input name="preferredModelColor" type="hidden" id="color_value" value="${user.preferredModelColor}"></c:when>
					        		<c:otherwise><input name="preferredModelColor" type="hidden" id="color_value" value="000000"></c:otherwise>
				        		</c:choose>
						        <input class="jscolor {valueElement: 'color_value'}" size="10" disabled>
						    </p>
						    <p>
						        Preferred Difficulty:
				       			<select name="preferredDifficulty">
				       				<option value="easy" ${ user.preferredDifficulty == 'easy' ? 'selected' : ''}>Easy</option>
				       				<option value="medium" ${ user.preferredDifficulty == 'medium' ? 'selected' : ''}>Medium</option>
				       				<option value="hard" ${ user.preferredDifficulty == 'hard' ? 'selected' : ''}>Hard</option>
				       			</select>
						    </p>
						    
						    <button class="w3-button w3-round w3-khaki w3-padding-small" type="submit">Submit</button>
					  </form>
              	  </div>
                    <div class="w3-section">
                        
                    </div>
                <div class="w3-container w3-border-top w3-padding-16 w3-theme-l4">
                    <button onclick="document.getElementById('pref').style.display='none'" type="button" class="w3-button w3-theme">Return to game</button>              
                  </div>
                </div>
              </div>
          </div>
        
    
       <div class="w3-container w3-padding w3-margin w3-theme-l3 w3-round-large"> 
			<div class="w3-container">
				<div class="w3-row">
					<div class="w3-left">
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
	      
					<div class="w3-right">
				       <c:choose>
			           		<c:when test="${empty user }">
				           			<form action="">
					       				<button id="buyHintButton" class="w3-tiny w3-button w3-round-large w3-right w3-theme w3-padding" type="submit" disabled>Log on to purchase hints</button>
				   					</form>
			           		</c:when>	
			           		<c:otherwise>
				           		<form action="buyHint.do" method="GET" >
				           			<c:choose>
				           				<c:when test="${hintsAvailable > hintsPurchased && not empty word}">
				       						<button id="buyHintButton" class="w3-button w3-round-large w3-right w3-theme w3-padding" type="submit">Buy a hint! (50 Points)</button>
			       						</c:when>
			       						<c:otherwise>
				       						<button id="buyHintButton" class="w3-button w3-round-large w3-right w3-theme w3-padding" disabled style="opacity: 1">No hints available</button>
			       						</c:otherwise>
			      						</c:choose>
			      					</form>
			           		</c:otherwise>
			           </c:choose>
		   				</div>
	           		</div>
           </div>
             <!-- Hiding this since JS is watching for key presses.  Still calling click() on this button though -->
		      <div id="guessContainer" hidden="true">
		      	  <form action="guess.do" method="POST">
			      	  <input id="guessInput" type="text" name="guess">
					  <label for="guess">Enter your guess here!</label>	
					  <button id="makeGuessButton" class="w3-button w3-round w3-tiny w3-khaki w3-padding-small" type="submit">Guess!</button>       	  
		      	  </form>
		      </div>
      </div>
   
   	      <div class="w3-row-padding w3-padding">
       		<div class="w3-col s6 w3-theme-l3 w3-round-large " >  
        		<c:if test="${not empty wordString }"> 
	        		<div class="w3-bar w3-theme-d4 w3-padding w3-margin-top w3-round-large w3-center">
			          ${guessesRemaining } guesses left!
			        </div>
		         </c:if>
      

	          <svg width="500" height="500" xmlns="http://www.w3.org/2000/svg" stroke="null">
	            <!-- Created with Method Draw - http://github.com/duopixel/Method-Draw/ -->
	            <c:choose>
		            <c:when test="${not empty user.preferredModelColor }"><g id="char" fill="#<c:out value="${user.preferredModelColor }"></c:out>" stroke="#000"></c:when>
		            <c:otherwise><g id="char" fill="#000" stroke="#000"></c:otherwise>
	            </c:choose>
	             <title stroke="null">Layer 1</title>
	             <rect id="base"   height="21" width="359" y="438.55" x="69.5" stroke-width="1.5"  <c:choose><c:when test="${character[0] eq 1}">opacity="100%"</c:when><c:otherwise>opacity="0%"</c:otherwise></c:choose> />
	             <rect id="beam"   height="330" width="23" y="107.55" x="179.5" stroke-width="1.5"  <c:choose><c:when test="${character[1] eq 2}">opacity="100%"</c:when><c:otherwise>opacity="0%"</c:otherwise></c:choose> />
	             <rect id="brace"  height="19" width="162" y="107.55" x="203.5" stroke-width="1.5"  <c:choose><c:when test="${character[2] eq 3}">opacity="100%"</c:when><c:otherwise>opacity="0%"</c:otherwise></c:choose> />
	             <rect id="rope" height="41" width="6" y="126.55" x="326.5" fill-opacity="null" stroke-opacity="null" stroke-width="1.5"  <c:choose><c:when test="${character[3] eq 4}">opacity="100%"</c:when><c:otherwise>opacity="0%"</c:otherwise></c:choose> />
	             <ellipse id="head" ry="33.000002" rx="31.500001" id="svg_6" cy="198.549998" cx="328.999999" fill-opacity="null" stroke-opacity="null" stroke-width="1.5"  <c:choose><c:when test="${character[4] eq 5}">opacity="100%"</c:when><c:otherwise>opacity="0%"</c:otherwise></c:choose> />
	             <rect id="body" id="svg_7" height="82.999998" width="10" y="230.550001" x="322.5" fill-opacity="null" stroke-opacity="null" stroke-width="1.5"  <c:choose><c:when test="${character[5] eq 6}">opacity="100%"</c:when><c:otherwise>opacity="0%"</c:otherwise></c:choose> />
	             <rect id="lhand" transform="rotate(-53 311.744873046875,259.2345275878906) "  height="7.201357" width="44.803193" y="255.633834" x="289.343251" fill-opacity="null" stroke-opacity="null" stroke-width="1.5"  <c:choose><c:when test="${character[6] eq 7}">opacity="100%"</c:when><c:otherwise>opacity="0%"</c:otherwise></c:choose> />
	             <rect id="rhand" transform="rotate(53 343.74487304687506,259.2345275878906) "  height="7.201357" width="44.803193" y="255.633834" x="321.343251" fill-opacity="null" stroke-opacity="null" stroke-width="1.5"  <c:choose><c:when test="${character[7] eq 8}">opacity="100%"</c:when><c:otherwise>opacity="0%"</c:otherwise></c:choose> />
	             <rect id="lfoot" transform="rotate(-53 311.7448425292969,327.23449707031256) "  height="7.201357" width="44.803193" y="323.633834" x="289.343251" fill-opacity="null" stroke-opacity="null" stroke-width="1.5"  <c:choose><c:when test="${character[8] eq 9}">opacity="100%"</c:when><c:otherwise>opacity="0%"</c:otherwise></c:choose> />
	             <rect id="rfoot" transform="rotate(53 343.744873046875,328.2345275878906) "  height="7.201357" width="44.803193" y="324.633834" x="321.343251" fill-opacity="null" stroke-opacity="null" stroke-width="1.5"  <c:choose><c:when test="${character[9] eq 10}">opacity="100%"</c:when><c:otherwise>opacity="0%"</c:otherwise></c:choose> />
	            </g>
	           </svg>
	           
				<c:if test="${not empty wordString }"> 
			       <div class="w3-bar w3-theme-d4 w3-padding w3-margin-top w3-round-large w3-center" id="wordContainer">
				          <span id="word">${wordString }</span>
			       </div>
		       </c:if>
	       	</div>	
      
	      <div class="w3-col s6 w3-theme-l3 w3-round-large w3-leftbar w3-topbar w3-bottombar w3-rightbar w3-border-white" style="transform: translateY(-5px)">
	          <div class="w3-container w3-padding " >      
	               <h3>Guessed letters: </h3>
	               <h5>
		              	<c:forEach var="letter" items="${guesses }">
		              		<span class="letter" 
			              		<c:if test="${fn:containsIgnoreCase(wordString, letter) }">style="color: green"</c:if>
			              		<c:if test="${not fn:containsIgnoreCase(wordString, letter) }">style="color: red"</c:if>
		              		>${letter }</span>
		              	</c:forEach>
					</h5>		              	
	          </div>
	      </div>
	      <div class="w3-rest w3-theme-l3 w3-padding w3-round-large w3-leftbar w3-topbar w3-bottombar w3-rightbar w3-border-white" style="transform: translateY(-10px); min-height:438px"> 
          	<div>
                 <h3>Messages:</h3>
                 <div id="messages">
	      			<c:forEach var="message" items="${messages }">
	      				<div>${message }</div>
	      			</c:forEach>
	      		</div>
            </div>
          </div>
    </div>

    <div class="w3-container w3-theme ">
        <p><b>CMSC 495</b></p>  
          
      </div>
  


<script src="scripts/scripts.js"></script>
<script src="scripts/jscolor.js"></script>

</body>
</html>
