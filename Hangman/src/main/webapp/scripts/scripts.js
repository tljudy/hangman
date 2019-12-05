'use strict'

const puzzle = document.querySelector('#word');
const input = document.getElementById('guessInput');
const inputBtn = document.getElementById('makeGuessButton');
const login = document.getElementById('id01');
const signUp = document.getElementById('id02');

// Called by keypress event listener below
function makeGuess(e) {
	const letter = String.fromCharCode(e.charCode);
	if (puzzle) {
		if (login == null && signUp == null || login.style.display === "none" &&
				signUp.style.display === "none") {
			input.value = letter;
			inputBtn.click();
		}
	}
}

window.addEventListener('keypress', makeGuess)
