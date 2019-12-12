'use strict'

const puzzle = document.querySelector('#word');
const input = document.getElementById('guessInput');
const inputBtn = document.getElementById('makeGuessButton');
const login = document.getElementById('id01');
const signUp = document.getElementById('id02');
let re = new RegExp('[a-zA-Z]{1}');

// Called by keypress event listener below
function makeGuess(e) {
	const letter = String.fromCharCode(e.charCode);
	if (puzzle.innerText != '') {
		if (re.test(letter)) {
			if ((login == null && signUp == null)
					|| (login.style.display == '' && signUp.style.display == '')
					|| (login.style.display === "none" && signUp.style.display === "none")) {

				input.value = letter;
				inputBtn.click();
			}
		}
	}
}

document.getElementsByClassName("tablink")[0].click();

function openTab(evt, tabName) {
  var i, x, tablinks;
  x = document.getElementsByClassName("tab");
  for (i = 0; i < x.length; i++) {
    x[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablink");
  for (i = 0; i < x.length; i++) {
    tablinks[i].classList.remove("w3-light-grey");
  }
  document.getElementById(tabName).style.display = "block";
  evt.currentTarget.classList.add("w3-light-grey");
}

window.addEventListener('keypress', makeGuess)
